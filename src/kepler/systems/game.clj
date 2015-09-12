(ns kepler.systems.game
  (:require [kepler.systems
             [connection :refer [connection-system]]
             [coordinate :refer [coordinate-system]]
             [cosmic-ray :refer [cosmic-ray-system]]
             [dead-bot-kicker :refer [dead-bot-kicker-system]]
             [decomposition :refer [decomposition-system]]
             [inventory :refer [inventory-system]]
             [locomotion :refer [locomotion-system]]
             [repair :refer [repair-system]]
             [state-broadcaster :refer [state-broadcaster-system]]
             [state-writer :refer [state-writer-system]]]))

(def DefaultState '())

(defn game-system
  [state action]
  (-> (or state DefaultState)
      
      ;; remote connections
      (connection-system action)

      ;; actions
      (locomotion-system action)
      (inventory-system action)
      (repair-system action)

      ;; environment
      (cosmic-ray-system action)
      (coordinate-system action)

      ;; death
      (dead-bot-kicker-system action)
      (decomposition-system action)

      ;; comms
      (state-broadcaster-system action)      
      (state-writer-system action)))
