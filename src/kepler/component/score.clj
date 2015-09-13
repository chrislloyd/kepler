(ns kepler.component.score
  (:require [kepler.component :refer [component]]))

(defn score-component [initial-score entity]
  (component :score initial-score entity))
