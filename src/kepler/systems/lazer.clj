(ns kepler.systems.lazer
  (:require [kepler.component :refer [get-component]]
            [kepler.component :refer [update-component-val]]
            [kepler.component :refer [by-component]]
            [kepler.pt :refer [distance]]
            [kepler.pt :refer [angle]]
            [kepler.components :refer [health]]
            [kepler.components :refer [position]]
            [kepler.components :refer [rotation]]
            [kepler.components :refer [position]]))

(def DAMAGE 10)
(def SPREAD 5)
(def RANGE 20)

(defn- hurt [damage life]
  (- life damage))

(defn- pt-in-range? [attacker-pt attacker-dir target-pt]
  (and (<= (Math/abs (- (angle attacker-pt target-pt) attacker-dir))
           SPREAD)
       (<= (distance attacker-pt target-pt)
           RANGE)))

(defn- find-victims [entity state]
  (let [pos-component (get-component state entity position)
        dir-component (get-component state entity rotation)]
    (if (and pos-component dir-component)
      (let [pos (:val pos-component)
            dir (:val dir-component)]
        (set (eduction (comp (filter #(not (= (:entity %) entity)))
                             (by-component position)
                             (filter (fn [component]
                                       (pt-in-range? pos
                                                     dir
                                                     (:val component))))
                             (map :entity)) state)))
      #{})))

(defn- deal-damage-to-victims [victims state]
  (map (fn [component]
         (if (and (contains? victims (:entity component))
                  (= (:type component) health))
           (update-component-val (partial hurt DAMAGE) component)
           component))
       state))

(defn lazer-system [state action]
  (case (:type action)
    :shoot (let [entity (:entity action)
                 victims (find-victims entity state)]
              (deal-damage-to-victims victims state))
    state))
