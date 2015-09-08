(ns kepler.systems.locomotion
  (:require [kepler.component.position :refer [move-down move-left move-right move-up]]
            [kepler.component :refer [update-component-val]]
            [kepler.component.rotation :refer [turn]]))

(defn- move-position [dir c]
  (let [fn (case dir
             "↑" move-up
             "←" move-left
             "↓" move-down
             "→" move-right)]
    (update-component-val fn c)))

(defn- move-entity [state entity dir]
  (map (fn [c]
         (if (and (= (:entity c) entity) (= (:type c) :pos))
           (move-position dir c)
           c))
       state))

(defn- turn-entity [state entity dr]
  (map (fn [c]
         (if (and (= (:entity c) entity) (= (:type c) :rot))
           (update-component-val (partial turn dr) c)
           c))
       state))

(defn locomotion-system [state {:keys [type] :as action}]
  (case type
    :move (move-entity state (:entity action) (:dir action))
    :turn (turn-entity state (:entity action) (:dr action))
    state))
