(ns kepler.systems.game
  (:require [kepler.component :refer [update-component-val]]
            [kepler.systems
             [connection :refer [connection-system]]
             [coordinate :refer [coordinate-system]]
             [dead-bot-kicker :refer [dead-bot-kicker-system]]
             [decomposition :refer [decomposition-system]]
             [flair :refer [flair-system]]
             [inventory :refer [inventory-system]]
             [locomotion :refer [locomotion-system]]
             [name :refer [name-system]]
             [repair :refer [repair-system]]
             [score :refer [score-system]]
             [state-broadcaster :refer [state-broadcaster-system]]
             [state-writer :refer [state-writer-system]]]))

(def DefaultState '())

(defn shooting-toggler-thingy-system [state action]
  (case (:type action)
    :shoot (map (fn [component]
                (if (= (:type component) :shooting)
                  (update-component-val (constantly true) component)
                  component))
              state)
    :tick (map (fn [component]
                 (if (= (:type component) :shooting)
                   (update-component-val (constantly false) component)
                   component))
               state)
    state))


(defn game-system
  [state action]
  (-> (or state DefaultState)
      
      ;; remote connections
      (connection-system action)

      ;; actions
      (locomotion-system action)
      (inventory-system action)
      (repair-system action)
      (name-system action)
      (flair-system action)

      ;; environment
      ;; (cosmic-ray-system action)
      (coordinate-system action)

      ;; death
      (dead-bot-kicker-system action)
      (decomposition-system action)
      
      ;; scoring
      (score-system action)

      ;; comms
      (state-broadcaster-system action)      
      (state-writer-system action)

      (shooting-toggler-thingy-system action)))
