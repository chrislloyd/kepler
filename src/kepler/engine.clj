(ns kepler.engine
  (:require [clojure.core.async :refer [<!!] :as async]))

(def TICK_PERIOD 3000)

; (defn new-game
;   [systems components]
;   {:running true :systems systems :components components})

(defn- tick
  "Applies the game's systems to it's current state"
  [game]
  (reduce #(%2 %1) game (:systems game)))

(defn- collect-values-from-chan-for-period
  "Takes all the values from a channel within a certain period. Returns a
  vector of results. Blocks for period."
  [ch period]
  (let [t (async/timeout period)]
    (async/go-loop [col []]
      (async/alt!
        t ([]
           col)
        ch ([v]
            (when v
              (recur (conj col v))))))))


(defn go-run [ch state]
  (async/go-loop [state state t 0]
    (when-let [events (<!!
                       (collect-values-from-chan-for-period ch TICK_PERIOD))]
      (let [new-state (tick (assoc state :events events))]
        (if (:running? new-state)
          (recur new-state (inc t))
          new-state)))))
