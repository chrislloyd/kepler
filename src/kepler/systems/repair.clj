(ns kepler.systems.repair
  (:require [kepler.component :refer [update-component-val]]))

(def MAX_LIFE 100)

(defn- heal [health life]
  (min (+ life health) MAX_LIFE))

(defn repair-system [state action]
  (if (= (:type action) :repair)
    (map (fn [component]
           (if (and (= (:entity component) (:entity action))
                    (= (:type component) :life))
             (update-component-val (partial heal 1) component)
             component))
         state)
    state))
