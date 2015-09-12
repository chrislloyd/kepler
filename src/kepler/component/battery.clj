(ns kepler.component.battery
  (:require [kepler.component :refer [component]]))

(defn battery-component [energy entity]
  (component :energy energy entity))

(defn charge [amount energy]
  (min (+ energy amount) 100))

(defn deplete [amount energy]
  (- energy amount))
