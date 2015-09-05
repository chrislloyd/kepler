(ns kepler.component.item
  (:require [kepler.component :refer [component]]))

(defn item-component [schema entity]
  (component :item schema entity))
