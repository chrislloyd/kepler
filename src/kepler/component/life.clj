(ns kepler.component.life
  (:require [kepler.component :refer [component]]))

(defn life-component [life entity]
  (component :life life entity))

(defn hurt [damage life]
  (- life damage))

(defn heal [health life]
  (+ life health))

(defn is-dead? [life]
  (<= life 0))
