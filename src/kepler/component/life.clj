(ns kepler.component.life
  (:require [kepler.component :refer [component]]))

(def MAX_LIFE 100)

(defn life-component [life entity]
  (component :life life entity))

(defn hurt [damage life]
  (- life damage))

(defn heal [health life]
  (min (+ life health) MAX_LIFE))

(defn is-dead? [life]
  (<= life 0))
