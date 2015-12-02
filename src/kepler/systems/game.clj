(ns kepler.systems.game
  (:require [kepler.systems
             [connection :refer [connection-system]]
             [coordinate :refer [coordinate-system]]
             [dead-bot-kicker :refer [dead-bot-kicker-system]]
             [decomposition :refer [decomposition-system]]
             [lazer :refer [lazer-system]]
             [locomotion :refer [locomotion-system]]
             [name :refer [name-system]]
             [repair :refer [repair-system]]
             [state-broadcaster :refer [state-broadcaster-system]]]))

(def DefaultState '())

(defn game-system
  [state action]
  (-> (or state DefaultState)

      ;; remote connections
      (connection-system action)

      ;; actions
      (locomotion-system action)
      (lazer-system action)
      (repair-system action)
      (name-system action)

      ;; environment
      (coordinate-system action)

      ;; death
      (dead-bot-kicker-system action)
      (decomposition-system action)

      ;; comms
      (state-broadcaster-system action)))
