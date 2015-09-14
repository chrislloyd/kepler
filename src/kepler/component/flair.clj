(ns kepler.component.flair
  (:require [kepler.component :refer [component]]))

(defn flair-component [entity]
  (component :flair nil entity))
