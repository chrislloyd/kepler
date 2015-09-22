(ns kepler.systems.lazer
  (:require [kepler.component :refer [get-component]]
            [kepler.item.lazer :refer [new-weapon]]
            [kepler.item :refer [use-item]]))

;;; incoming action
;;; {:type :use
;;;  :entity "abc-123"}

(def lazer (new-weapon {:damage 10
                        :spread 5
                        :range  20}))

(defn shoot [state action]
  (let [entity (:entity action)]
    (use-item lazer entity state)))

(defn lazer-system [state action]
  (case (:type action)
    :shoot (shoot state action)
    state))
