(ns kepler.component.shooting
  (:require [kepler.component :refer [component]]))

(defn shooting-component [entity]
  (component :shooting false entity))
