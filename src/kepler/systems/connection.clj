(ns kepler.systems.connection
  (:require [kepler
             [component :refer [remove-component]]
             [entity :refer [new-entity]]
             [pt :refer [pt]]]
            [kepler.component
             [battery :refer [battery-component]]
             [inventory :refer [inventory-component]]
             [item :refer [item-component]]
             [life :refer [life-component]]
             [name :refer [name-component]]
             [position :refer [position-component]]
             [remote-control :refer [remote-control-component]]
             [rotation :refer [rotation-component]]]))

(defn entry-pt []
  (pt (rand 20) (rand 20)))

(defn- add-bot [state {:keys [entity chan]}]
  (let [lazer-entity (new-entity)]
    (-> state
        (conj (item-component "lazer" lazer-entity))

        (conj (remote-control-component chan entity))
        (conj (name-component nil entity))
        (conj (life-component 100 entity))
        (conj (battery-component 100 entity))
        (conj (position-component (entry-pt) entity))
        (conj (rotation-component 0.0 entity))
        (conj (inventory-component #{lazer-entity} entity)))))

;;; use-drill :: State -> Item -> Entity -> State
;;; (defn use-drill [state])

(defn- remove-bot [state {:keys [entity]}]
  (remove-component state entity :uplink))

(defn connection-system [state {:keys [type] :as action}]
  (case type
    :add-bot (add-bot state action)
    :remove-bot (remove-bot state action)
    state))
