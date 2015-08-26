(ns kepler.assembly
  (:require [kepler.component :as c]
            [kepler.ecs :as ecs]))

(defn new-tile
  [x y color]
  (ecs/new-assembly (ecs/new-entity)
   (c/position-component x y)
   (c/colorable-component color)))

(defn new-bot [id chan]
  (ecs/new-assembly id
   (c/chan-component chan)))
