(ns kepler.systems.deathly-sweeper
  "On tick, cleans up uplink connections from dead bots. Leaves bot to decay."
  (:require [clojure.core.async :refer [>!!]]))

(defn- dead?
  [[_ tag val]]
  (and (= tag :life) (<= val 0.0)))

(defn- kickable-uplink? [dead-entities [entity type _]]
  (and (contains? dead-entities entity)
       (= type :uplink)))

(defn- sweep-dead-uplink [[_ _ ch]]
  (>!! ch {:type :kick :msg "dead"}))

(defn deathly-sweeper-system [state {:keys [type] :as action}]
  (if (= type :tick)
    (let [entities (->> state
                        (filter dead?)
                        (map first)
                        (set))]
      (do
        (pmap sweep-dead-uplink
              (filter (partial kickable-uplink? entities)
                      state))
        state))
    state))
