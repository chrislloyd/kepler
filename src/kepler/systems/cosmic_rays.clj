(ns kepler.systems.cosmic-rays
  (:require [kepler.components :refer [hurt-life]]))

(def RadiationDecay 1)

(defn decay-life [[_ tag :as component]]
  (if (= tag :life)
    (update component 2 (partial hurt-life RadiationDecay))
    component))

(defn cosmic-rays-system [state {:keys [type]}]
  (if (= type :tick)
    (map decay-life state)
    state))
