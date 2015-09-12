(ns kepler.component.name
  (:require [kepler.component :refer [component]]))

(defn name-component [name entity]
  (component :name name entity))
