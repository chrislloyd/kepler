(ns kepler.systems.lazer
  (:require [kepler.component :refer [get-component]]
            [kepler.item.lazer :refer [new-weapon]]
            [kepler.item :refer [use-item]]))

(def lazer (new-weapon {:damage 10
                        :spread 5
                        :range  20}))

(defn lazer-system [state action]
  (case (:type action)
    :shoot (use-item lazer (:entity action) state)
    state))
