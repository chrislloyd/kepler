(ns kepler.components
  (:require [kepler.component :refer [new-component]]))

(defn Life [initial-life]
  (new-component :life (int initial-life)))

(defn hurt-life [x life]
  (max 0 (- life x)))

(defn heal-life [x life]
  (+ life x))


(defn Uplink [chan]
  (new-component :uplink chan))

; TODO Direction component
; {:direction 0-360}

; TODO Health component
; {:health 100}

; TODO Battery component (max hardcoded)
; {:energy 100000}

; TODO Comms component
; {:comms-inbox ["HELLO WORLD"]}

; TODO Inventory component
; {:inventory #{} (set of entity ids with max of N items)
