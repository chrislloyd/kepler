(ns kepler.systems.connection
  (:require [kepler.component :refer [remove-component]]
            [kepler.components.life :refer [life-component]]
            [kepler.components.position :refer [position-component]]
            [kepler.components.remote-control :refer [remote-control-component]]))

(defn- add-bot [state {:keys [entity chan]}]
  (-> state
      (conj (remote-control-component chan entity))
      (conj (life-component 100 entity))
      (conj (position-component {:x 0 :y 0} entity))))

(defn- remove-bot [state {:keys [entity]}]
  (let [rc-type (:type (remote-control-component nil nil))]
      (remove-component state entity rc-type)))

(defn connection-system [state {:keys [type] :as action}]
  (case type
    :add-bot (add-bot state action)
    :remove-bot (remove-bot state action)
    state))
