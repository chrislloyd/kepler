(ns kepler.systems.locomotion
  (:require [kepler
             [component :refer [update-component-val]]
             [components :refer [position rotation]]
             [pt :refer [pt]]]))

(defn- move-up [{:keys [x y]}]
  (pt x (+ y 1)))

(defn- move-down [{:keys [x y]}]
  (pt x (- y 1)))

(defn- move-left [{:keys [x y]}]
  (pt (- x 1) y))

(defn- move-right [{:keys [x y]}]
  (pt (+ x 1) y))

(defn- turn [dr r]
  (mod (+ r dr) 360))

(defn- move-position [dir c]
  (let [fn (case dir
             "up" move-up
             "down" move-down
             "left" move-left
             "right" move-right)]
    (update-component-val fn c)))

(defn- move-entity [state entity dir]
  (map (fn [c]
         (if (and (= (:entity c) entity) (= (:type c) position))
           (move-position dir c)
           c))
       state))

(defn- turn-entity [state entity dr]
  (map (fn [c]
         (if (and (= (:entity c) entity) (= (:type c) rotation))
           (update-component-val (partial turn dr) c)
           c))
       state))

(defn locomotion-system [state {:keys [type] :as action}]
  (case type
    :move (move-entity state (:entity action) (:dir action))
    :turn (turn-entity state (:entity action) (:dr action))
    state))
