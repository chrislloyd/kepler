(ns kepler.systems.lazer
  (:require [kepler.component :refer [get-component]]
            [kepler.component :refer [update-component-val]]
            [kepler.component :refer [by-component]]
            [kepler.component.life :refer [hurt]]
            [kepler.pt :refer [distance]]
            [kepler.pt :refer [angle]]))

(def DAMAGE 10)
(def SPREAD 5)
(def RANGE 20)

(defn- pt-in-range? [attacker-pt attacker-dir target-pt]
  (and (<= (Math/abs (- (angle attacker-pt target-pt) attacker-dir))
           SPREAD)
       (<= (distance attacker-pt target-pt)
           RANGE)))

(defn- find-victims [entity state]
  (let [pos-component (get-component state entity :pos)
        dir-component (get-component state entity :rot)]
    (if (and pos-component dir-component)
      (let [pos (:val pos-component)
            dir (:val dir-component)]
        (set (eduction (comp (filter #(not (= (:entity %) entity)))
                             (by-component :pos)
                             (filter (fn [component]
                                       (pt-in-range? pos
                                                     dir
                                                     (:val component))))
                             (map :entity)) state)))
      #{})))

(defn- deal-damage-to-victims [victims state]
  (map (fn [component]
         (if (and (contains? victims (:entity component))
                  (= (:type component) :life))
           (update-component-val (partial hurt DAMAGE) component)
           component))
       state))

(defn lazer-system [state action]
  (case (:type action)
    :shoot (let [entity (:entity action)
                 victims (find-victims entity state)]
              (deal-damage-to-victims victims state))
    state))
