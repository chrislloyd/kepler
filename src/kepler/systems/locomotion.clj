(ns kepler.systems.locomotion
  (:require [kepler.component :refer [same-component? update-component-val]]
            [kepler.components.position
             :refer
             [move-down move-left move-right move-up position-component]]))

(defn- move-position [dir c]
  (let [fn (case dir
             "↑" move-up
             "←" move-left
             "↓" move-down
             "→" move-right
             identity)]
    (update-component-val fn c)))

(defn locomotion-system [state {action-type :type :as action}]
  (let [{:keys [cmd entity]} action
        [type dir] cmd
        pos-type (:type (position-component nil nil))]
    (if (and (= action-type :cmd) (= type "MOVE"))
      (map (fn [c]
             (if (same-component? entity pos-type c)
               (move-position dir c)
               c))
           state)
      state)))
