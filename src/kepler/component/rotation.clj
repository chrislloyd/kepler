(ns kepler.component.rotation
  (:require [kepler.component :refer [component]]))

(defn rotation-component [r entity]
  (component :rot (mod r 360) entity))

(defn turn [dr r]
  (mod (+ r dr) 360))
