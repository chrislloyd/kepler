(ns kepler.systems.state-broadcaster
  (:require [clojure.core.async :refer [>!!]]))

(def attribute-whitelist #{:pos :life :inbox})

(defn- entity->payload [entity state]
  (reduce (fn [o {:keys [type val]}] (assoc o type val))
          {:entity entity}
          (filter #(and (= (:entity %) entity)
                        (contains? attribute-whitelist (:type %)))
                  state)))

(defn- payload [state entity tick]
  {:tick tick
   :me (entity->payload entity state)
   :sensors []})

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
