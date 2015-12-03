(ns kepler.systems.dead-bot-kicker
  "On tick, cleans up uplink connections from dead bots. Leaves bot to decay."
  (:require [clojure.core.async :refer [>!!]]
            [kepler
             [components :refer [health remote-control]]
             [util :refer [async-pmap]]]))

(defn- dead-component?
  [{:keys [type val]}]
  (and (= type health) (<= val 0)))

(defn- kickable-uplink? [dead-entities {:keys [entity type]}]
  (and (contains? dead-entities entity)
       (= type remote-control)))

(defn- sweep-dead-uplink [{:keys [val]}]
  (>!! val {:type :kick :msg "dead"}))

(defn dead-bot-kicker-system [state {:keys [type] :as action}]
  (if (= type :tick)
    (let [entities (->> state
                        (filter dead-component?)
                        (map :entity)
                        (set))]
      (do
        (async-pmap sweep-dead-uplink
              (filter (partial kickable-uplink? entities)
                      state))
        (filter (fn [component]
                  (not (contains? entities (:entity component))))
                state)))
    state))
