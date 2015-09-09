(ns kepler.component.position
  (:require [kepler.component :refer [component]]
            [kepler.pt :refer [pt]]))

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

(defn wrap [lim {:keys [x y]}]
  (pt (- (mod (+ x lim) (* lim 2)) lim)
      (- (mod (+ y lim) (* lim 2)) lim)))
