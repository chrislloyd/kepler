(ns kepler.component
  (:require [kepler.ecs :as ecs]))

(defn health-component
  [initial-health]
  (ecs/build-component :health initial-health))

(defn position-component
  [x y]
  (ecs/build-component :position {:x x
                                  :y y}))
(defn colorable-component
  [color]
  (ecs/build-component
    :color color))

(defn chan-component [chan]
  (ecs/build-component :chan chan))

; TODO Direction component
; {:direction 0-360}

; TODO Action cache component
; {:action ["MOVE" 1]}

; TODO Health component
; {:health 100}

; TODO Battery component (max hardcoded)
; {:energy 100000}

; TODO Comms component
; {:comms-inbox ["HELLO WORLD"]}

; TODO Inventory component
; {:inventory #{} (set of entity ids with max of N items)
