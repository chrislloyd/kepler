(ns kepler.systems.connection
  (:require [kepler component
             [entity :refer [new-entity]]
             [pt :refer [pt]]]
            [kepler.component
             [life :refer [life-component]]
             [name :refer [name-component]]
             [position :refer [position-component]]
             [remote-control :refer [remote-control-component]]
             [rotation :refer [rotation-component]]]))

(defn entry-pt []
  (pt (+ -50 (int (Math/floor (rand 100))))
      (+ -50 (int (Math/floor (rand 100))))))

(defn- add-bot [state {:keys [entity chan]}]
  (let [lazer-entity (new-entity)]
    (-> state
        (conj (remote-control-component chan entity))
        (conj (name-component nil entity))
        (conj (life-component 100 entity))
        (conj (position-component (entry-pt) entity))
        (conj (rotation-component 0.0 entity)))))

(defn- remove-bot [state {:keys [entity]}]
  (filter (fn [component]
            (not (= (:entity component) entity)))
          state))

(defn connection-system [state {:keys [type] :as action}]
  (case type
    :add-bot (add-bot state action)
    :remove-bot (remove-bot state action)
    state))
