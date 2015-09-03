(ns kepler.systems.game
  (:require [kepler.systems
             [connection :refer [connection-system]]
             [cosmic-ray :refer [cosmic-ray-system]]
             [dead-bot-kicker :refer [dead-bot-kicker-system]]
             [debug :refer [debug-system]]
             [decomposition :refer [decomposition-system]]
             [locomotion :refer [locomotion-system]]
             [state-broadcaster :refer [state-broadcaster-system]]
             [inbox-zero :refer [inbox-zero-system]]]))

(def DefaultState '())

(defn game-system
  [state action]
  (-> (or state DefaultState)
      
      ;; remote connections
      (connection-system action)

      ;; actions
      (locomotion-system action)
      ;; (speech-system action)
      ;; (inventory-system action)
      ;; (fab-system action)
      ;; (energy-converter-system)

      ;; environment
      (cosmic-ray-system action)

      ;; death
      (dead-bot-kicker-system action)
      (decomposition-system action)

      ;; comms
      (state-broadcaster-system action)
      
      ;; clenup
      (inbox-zero-system action)

      (debug-system action)))

