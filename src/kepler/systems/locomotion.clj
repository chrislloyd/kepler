(ns kepler.systems.locomotion
  (:require [kepler.component :refer [same-component? update-component-val]]
            [kepler.components.position
             :refer
             [move-down move-left move-right move-up position-component]]
            [kepler.components.rotation :refer [turn]]))

(defn- move-position [dir c]
  (let [fn (case dir
             "↑" move-up
             "←" move-left
             "↓" move-down
             "→" move-right)]
    (update-component-val fn c)))

(defn- move-cmd [state entity dir]
  (map (fn [c]
         (if (same-component? entity :pos c)
           (move-position dir c)
           c))
       state))

(defn- turn-cmd [state entity dr]
  (map (fn [c]
         (if (same-component? entity :rot c)
           (update-component-val (partial turn dr) c)
           c))
       state))

(defn locomotion-system [state {:keys [type] :as action}]
  (let [{:keys [cmd entity]} action
        [cmd-name arg] cmd]
    (if (and (= type :cmd))
      (do
        (prn "YAY")
        (case cmd-name
         "MOVE" (move-cmd state entity arg)
         "TURN" (turn-cmd state entity arg)))
      state)))
