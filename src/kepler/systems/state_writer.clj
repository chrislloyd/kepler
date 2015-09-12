(ns kepler.systems.state-writer
  (:require [cheshire.core :as json]
            [kepler.component :refer [by-entity]]))

(defn entity->payload [state  entity]
  (reduce (fn [o {:keys [type val]}] (assoc o type val))
          {:id entity}
          (eduction (by-entity entity) state)))

(defn- payload [state tick]
  {:tick tick
   :entities (map (partial entity->payload state)
                  (set (map :entity state)))})

(defn state-writer-system [state action]
  (if (= (:type action) :tick)
    (do
      (let [tick (:tick action)
            entities-without-chans (filter #(not (= (:type %) :uplink)) state)
            state-to-serialize (payload entities-without-chans tick)]
        (spit "rc-state-output/state.json" (json/encode state-to-serialize))
        state))
    state))
