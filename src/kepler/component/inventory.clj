(ns kepler.component.inventory
  (:require [kepler.component :refer [component]]))

(defn inventory-component [items entity]
  (component :inventory items entity))
