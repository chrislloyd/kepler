(ns kepler.systems.locomotion
  (:require [kepler.component :refer [update-component-val]]
            [kepler.components :refer [pos-move-up
                                       pos-move-left
                                       pos-move-down
                                       pos-move-right]]))

(defn locomotion-system [state {action-type :type :as action}]
  (let [{:keys [cmd entity]} action
        [type dir] cmd]
    (if (and (= action-type :cmd) (= type "MOVE"))
      (update-component-val state entity :pos (case dir
                                                "↑" pos-move-up
                                                "←" pos-move-left
                                                "↓" pos-move-down
                                                "→" pos-move-right
                                                identity))
      state)))
