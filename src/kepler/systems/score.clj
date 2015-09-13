(ns kepler.systems.score
  (:require [kepler.component :refer [update-component-val]]))

(defn score-system [state action]
  (map (fn [component]
         (if (= (:type component) :score)
           (update-component-val inc component)
           component))
       state))
