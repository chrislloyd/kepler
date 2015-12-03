(ns kepler.systems.decomposition
  (:require [kepler.components :refer [health]]))

(defn decomposition-system [state action]
  (if (= (:type action) :tick)
    (let [dead-entities (->> state
                             (filter
                              (fn [{:keys [type val]}]
                                (and
                                 (= type health)
                                 (<= val 0))))
                             (map :entity)
                             (set))]
      (filter (fn [{:keys [entity]}]
                (not (contains? dead-entities entity)))
              state))
    state))
