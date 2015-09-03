(ns kepler.systems.decomposition
  (:require [kepler.components.life :refer [is-dead?]]))

(defn decomposition-system [state action]
  (if (= (:type action) :tick)
    (let [dead-entities (->> state
                             (filter
                              (fn [{:keys [type val]}]
                                (and
                                 (= type :life)
                                 (is-dead? val))))
                             (map :entity)
                             (set))]
      (filter (fn [{:keys [entity]}]
                (not (contains? dead-entities entity)))
              state))
    state))
