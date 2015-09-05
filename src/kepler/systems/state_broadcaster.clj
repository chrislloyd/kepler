(ns kepler.systems.state-broadcaster
  (:require [clojure.core.async :refer [>!!]]
            [kepler.component :refer [get-component]]))

(def attribute-whitelist #{:pos :life :inbox :rot :inventory})

(defn- distance [{x1 :x y1 :y} {x2 :x y2 :y}]
  (Math/sqrt (+ (Math/pow (- x2 x1) 2)
                (Math/pow (- y2 y1) 2))))

(def s [
        {:entity 1 :type :pos :val {:x 0 :y 0}}
        {:entity 2 :type :pos :val {:x 5 :y 5}}
        {:entity 3 :type :pos :val {:x 100 :y 100}}
])

(defn- nearby-entities [state me]
  (let [entity-pos (:val (get-component state me :pos))]
    (->> state
         (filter (fn [{:keys [entity type val]}]
                   (and (not (= entity me))
                        (= type :pos)
                        (<= (distance entity-pos val) 20))))
         (map :entity)
         (set))))

(defn- entity->payload [state entity]
  (reduce (fn [o {:keys [type val]}] (assoc o type val))
          {:entity entity}
          (filter #(and (= (:entity %) entity)
                        (contains? attribute-whitelist (:type %)))
                  state)))

(defn- payload [state entity tick]
  {:tick tick
   :me (entity->payload state entity)
   :sensors (map (partial entity->payload state)
                 (nearby-entities state entity))})

(defn- send-payload-downlink [chan payload]
  (let [event {:type :tick :payload payload}]
    (>!! chan event)))

(defn state-broadcaster-system [state {:keys [type] :as action}]
  (if (= type :tick)
    (do
      ;; If this operation was done serially it would be a potential attack vector. The first bot could be slow to accept the connection holding up all the other connections. This could still possibly be the case because `pmap` blocks on the return value, however it just slows the world. A potential optimization could be doing the send in a go block with a non-blocking put.
      (pmap
       (fn [{:keys [entity val]}]
         (send-payload-downlink val
                                (payload state entity (:tick action))))
       (filter #(= (:type %) :uplink) state))
      state)
    state))
