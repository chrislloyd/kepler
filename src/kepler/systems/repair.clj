(ns kepler.systems.repair
  (:require [kepler.component :refer [update-component-val]]
            [kepler.component.life :refer [heal]]))

(defn repair-system [state action]
  (if (= (:type action) :repair)
    (map (fn [component]
           (if (and (= (:entity component) (:entity action))
                    (= (:type component) :life))
             (update-component-val (partial heal 1) component)
             component))
         state)
    state))
