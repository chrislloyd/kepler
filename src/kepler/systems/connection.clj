(ns kepler.systems.connection
  (:require [kepler.component :refer [add-component remove-component]]
            [kepler.components :refer [Uplink Life]]))

(defn- add-bot [state {:keys [entity chan]}]
  (-> state
      (add-component entity (Uplink chan))
      (add-component entity (Life 100.0))))

(defn- remove-bot [state {:keys [entity]}]
  (-> state
      (remove-component entity :uplink)))

(defn connection-system [state {:keys [type] :as action}]
  (case type
    :add-bot (add-bot state action)
    :remove-bot (remove-bot state action)
    state))
