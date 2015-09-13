(ns kepler.item.lazer
  (:require [kepler.component.life :refer [hurt]]
            [kepler.component :refer [by-component]]
            [kepler
             [component :refer [get-component update-component-val]]
             [item :refer [Item]]]
            [kepler.pt :refer [angle]]
            [kepler.pt :refer [distance]]))

(defn pt-in-range? [weapon attacker-pt attacker-dir target-pt]
  (and (<= (Math/abs (- (angle attacker-pt target-pt) attacker-dir))
           (:spread weapon))
       (<= (distance attacker-pt target-pt)
           (:range weapon))))

(defn- find-victims [weapon entity state]
  (let [pos-component (get-component state entity :pos)
        dir-component (get-component state entity :rot)]
    (if (and pos-component dir-component)
      (let [pos (:val pos-component)
            dir (:val dir-component)]
        (set (eduction (comp (filter #(not (= (:entity %) entity)))
                             (by-component :pos)
                             (filter (fn [component]
                                       (pt-in-range? weapon
                                                     pos
                                                     dir
                                                     (:val component))))
                             (map :entity)) state)))
      #{})))

(defn deal-damage-to-victims [weapon victims state]
  (map (fn [component]
         (if (and (contains? victims (:entity component))
                  (= (:type component) :life))
           (update-component-val (partial hurt (:damage weapon)) component)
           component))
       state))

(defrecord Weapon [damage spread range]
  Item
  (use-item [this entity state]
            (let [victims (find-victims this entity state)]
              (deal-damage-to-victims this victims state))))

(defn new-weapon [args]
  (map->Weapon args))
