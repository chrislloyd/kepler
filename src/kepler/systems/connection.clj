(ns kepler.systems.connection
  (:require [kepler component
             [components :refer [health name position remote-control rotation]]
             [entity :refer [new-entity]]
             [pt :refer [pt]]]))

(defn entry-pt []
  (pt (+ -50 (int (Math/floor (rand 100))))
      (+ -50 (int (Math/floor (rand 100))))))

(defn- add-bot [state {:keys [entity chan]}]
  (let [lazer-entity (new-entity)]
    (-> state
        (conj {:entity entity :type remote-control :value chan})
        (conj {:entity entity :type name :value nil})
        (conj {:entity entity :type health :value 100})
        (conj {:entity entity :type position :value (entry-pt)})
        (conj {:entity entity :type rotation :value 0.0}))))

(defn- remove-bot [state {:keys [entity]}]
  (filter (fn [component]
            (not (= (:entity component) entity)))
          state))

(defn connection-system [state {:keys [type] :as action}]
  (case type
    :add-bot (add-bot state action)
    :remove-bot (remove-bot state action)
    state))
