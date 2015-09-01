(ns kepler.system
  (:require [kepler.systems.connection :refer [connection-system]]
            [kepler.systems.deathly-sweeper :refer [deathly-sweeper-system]]
            [kepler.systems.cosmic-rays :refer [cosmic-rays-system]]
            [kepler.systems.downlink :refer [downlink-system]]
            [kepler.systems.locomotion :refer [locomotion-system]]))

(def DefaultState '())

(defn debug-system [state action]
  (do
    (prn state)
    state))

(defn game-system [state action]
  (-> (or state DefaultState)
      (connection-system action)

      ;; actions
      (locomotion-system action)

      ;; environment
      (cosmic-rays-system action)

      ;; death
      (deathly-sweeper-system action)

      ;; comms
      (downlink-system action)
      (debug-system action)))

; MovementActionDispatcher
;   dispatch (world action) ->
;     update position component for (entity action) case (move -x (pos action))
;
;
;  Locomotion System
;   Find all entities



; Action Applicator
; Take the actions from the action channel
; - validate format of each action
; - validate the energy required for each action
; - add action to entity

; Locomotion System
; Looks for move actions and then updates a bots positions (both GO &
; TURN commands)
; Deduct energy


; Death system
; Search for bots with <= 0 health.
; Remove them from the game

; Speech system
; Takes speech action from bots, places it in surrounding entities
; message inbox.
; Deduct energy from speaker

; Connection terminator system
; Look at connections
; Disconnect remote connection if no entity exists


; TODO Inventory system
; looks up TAKE, DROP actions
; take -> if bot is on any items, add item to inventory (FUTURE limit
;         inventory). remove item from game world.
; drop -> fail if item already in game world. (? think about more)
;         look up item in inventory. if exists, remove item from
;         inventory and return to game world (add x,y).
; deduct energy from bot

; Energy converter system
; Look for X action
; Take item from inventory, remove entity from game.
; look up energy value, add energy to bot

; Fab system
; Look up fab action
; Look up recipe (energy, matter) for item. if recipe doesn't exist, fail. if bot doesn't have energy or matter to complete, fail. if no inventory space exists, fail.
; deduct energy
; remove mass from inventory
; add fab'd item to inventory


; // 2. Action system
; //
; // Initialize empty list of action channels
; // Pop off   actions from incoming message channel
; //
; //     {:entity "player-abcd-1234" :tick "tick-abcd-1234" :action ["MOVE" 1]}
; //
; // Validate tick corresponds to current tick-id (else drop action)
; // Validate action using prismatic schema (else drop action)
; // Apply action to state
; // Store action as "last-action" component on entity
