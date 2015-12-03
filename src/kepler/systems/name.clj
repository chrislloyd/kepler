(ns kepler.systems.name
  (:require [kepler
             [component :refer [update-component-val]]
             [components :refer [name]]]))

(defn name-system [state action]
  (if (= (:type action) :name)
    (map (fn [component]
           (if (and (= (:entity action) (:entity component))
                    (= (:type component) name))
             (update-component-val (constantly (:name action))
                                   component)
             component))
         state)
    state))
