(ns kepler.systems.game
  (:require [kepler.systems
             [connection :refer [connection-system]]
             [coordinate :refer [coordinate-system]]
             [cosmic-ray :refer [cosmic-ray-system]]
             [dead-bot-kicker :refer [dead-bot-kicker-system]]
             [decomposition :refer [decomposition-system]]
             [inbox-zero :refer [inbox-zero-system]]
             [inventory :refer [inventory-system]]
             [locomotion :refer [locomotion-system]]
             [state-broadcaster :refer [state-broadcaster-system]]]))

(def DefaultState '())

(defn game-system
  [state action]
  (-> (or state DefaultState)
      
      ;; remote connections
      (connection-system action)
      ;; (bot-entry-position-system action)

      ;; actions
      (locomotion-system action)
      (inventory-system action)
      ;; (speech-system action)
      ;; (fab-system action)
      ;; (energy-converter-system)

      ;; environment
      (cosmic-ray-system action)
      (coordinate-system action)

      ;; (collision-system action)

      ;; death
      (dead-bot-kicker-system action)
      (decomposition-system action)

      ;; comms
      (state-broadcaster-system action)
      
      ;; clenup
      (inbox-zero-system action)
      ))

