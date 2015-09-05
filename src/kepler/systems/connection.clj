(ns kepler.systems.connection
  (:require [kepler
             [component :refer [remove-component]]
             [entity :refer [new-entity]]]
            [kepler.components
             [life :refer [life-component]]
             [position :refer [position-component]]
             [remote-control :refer [remote-control-component]]
             [rotation :refer [rotation-component]]]))

(defn- add-bot [state {:keys [entity chan]}]
  (let [drill-entity (new-entity)]
     (-> state
         ;; (conj (usable-component "drill" drill-entity))

         (conj (remote-control-component chan entity))
         (conj (life-component 100 entity))
         (conj (position-component {:x 0 :y 0} entity))
         (conj (rotation-component 0 entity)))))

;;; use-drill :: State -> Item -> Entity -> State
;;; (defn use-drill [state])


(defn- remove-bot [state {:keys [entity]}]
  (let [rc-type (:type (remote-control-component nil nil))]
      (remove-component state entity rc-type)))

(defn connection-system [state {:keys [type] :as action}]
  (case type
    :add-bot (add-bot state action)
    :remove-bot (remove-bot state action)
    state))
