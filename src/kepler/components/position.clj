(ns kepler.components.position
  (:require [kepler.component :refer [component]]))

(defn- pt [x y]
  {:x x :y y})

(defn position-component [pos entity]
  (component :pos pos entity))

(defn move-up [{:keys [x y]}]
  (pt x (+ y 1)))

(defn move-down [{:keys [x y]}]
  (pt x (- y 1)))

(defn move-left [{:keys [x y]}]
  (pt (- x 1) y))

(defn move-right [{:keys [x y]}]
  (pt (+ x 1) y))
