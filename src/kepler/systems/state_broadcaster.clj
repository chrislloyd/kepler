(ns kepler.systems.state-broadcaster
  (:require [clojure.core.async :refer [>!! chan]]
            [clojure.set :refer [union]]
            [kepler
             [component :refer [by-component by-entity get-component]]
             [util :refer [async-pmap]]]))

(defn- components-for-serialization [entity viewer]
  #{:pos :life :rot :name :flair})

(defn- distance [{x1 :x y1 :y} {x2 :x y2 :y}]
  (Math/sqrt (+ (Math/pow (- x2 x1) 2)
                (Math/pow (- y2 y1) 2))))

(defn- by-radius [c r]
  (comp (by-component :pos)
        (filter (fn [component]
                  (<= (distance c (:val component)) r)))))

(defn- nearby-entities [state me]
  (let [entity-pos (:val (get-component state me :pos))]
    (->> state
         (eduction (comp (by-radius entity-pos 20)
                         (map :entity)))
         (set))))

(defn- entity->payload [state viewer entity]
  (reduce (fn [o {:keys [type val]}] (assoc o type val))
          {:id entity}
          (eduction (comp (by-entity entity)
                          (filter #(contains? (components-for-serialization
                                               entity
                                               viewer)
                                              (:type %))))
                    state)))

(defn- payload [state entity tick]
  {:tick tick
   :me entity
   :entities (map (partial entity->payload state entity)
                  (nearby-entities state entity))})

(defn- send-payload-downlink [chan state]
  (>!! chan {:type :tick
             :state state}))

(defn state-broadcaster-system [state {:keys [type] :as action}]
  (if (= type :tick)
    (do
      ;; If this operation was done serially it would be a potential attack vector. The first bot could be slow to accept the connection holding up all the other connections. This could still possibly be the case because `async-pmap` blocks on the return value, however it just slows the world. A potential optimization could be doing the send in a go block with a non-blocking put.
      (let [uplinks (filter #(= (:type %) :uplink) state)
            tick (:tick action)]
        (async-pmap
         (fn [{:keys [entity val]}]
           (send-payload-downlink val
                                  (payload state entity tick)))
         uplinks))
      state)
    state))
