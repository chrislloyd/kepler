(ns kepler.systems.dead-bot-kicker
  "On tick, cleans up uplink connections from dead bots. Leaves bot to decay."
  (:require [clojure.core.async :refer [>!!]]
            [kepler.components.life :refer [is-dead?]]))

(defn- dead-component?
  [{:keys [type val]}]
  (and (= type :life) (is-dead? val)))

(defn- kickable-uplink? [dead-entities {:keys [entity type]}]
  (and (contains? dead-entities entity)
       (= type :uplink)))

(defn- sweep-dead-uplink [{:keys [val]}]
  (>!! val {:type :kick :msg "dead"}))

(defn dead-bot-kicker-system [state {:keys [type] :as action}]
  (if (= type :tick)
    (let [entities (->> state
                        (filter dead-component?)
                        (map :entity)
                        (set))]
      (do
        (pmap sweep-dead-uplink
              (filter (partial kickable-uplink? entities)
                      state))
        state))
    state))
