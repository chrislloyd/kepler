(ns kepler.systems
  (:require
   [kepler.ecs :as ecs]
   [kepler.assembly :as assembly]
   [clojure.pprint]
   [clojure.core.async :as async]
   [schema.core :as s]))

;; Debug System

(defn debug-system
  "Simply prints out the current state"
  [game]
  (do
    (clojure.pprint/pprint game)
    game))

; --

(defn join-bot [c id chan]
  (ecs/add-assembly c (assembly/new-bot id chan)))

(defn leave-bot [c id]
  ;; TODO set health to 0 and remove chan rather just removing entity
  (ecs/remove-entity c id))


;; Event System
;; Takes events from the websockets channels, dispatches each event.
;; Action events are sent through to the action system.

(defn- dispatch-event [c {:keys [type entity] :as event}]
  (case type
    :join  (join-bot c entity (:chan event))
    :leave (leave-bot c entity)
    c))

(defn event-system
  "Dispatches events from the inbound websockets channel. Clears out 
  events too."
  [{:keys [events] :as game}]
  (-> game
      (update :components #(reduce dispatch-event % events))
      (assoc :events [])))


;; Broadcast system

(defn- entity-tick-payload [game entity]
  ["tick", entity])

(defn- put-tick-payload [game {:keys [data entity] :as component}]
  (let [payload (entity-tick-payload game entity)
        event {:type :tick :payload payload}]
    (async/>!! (:data component) event)))

(defn broadcast-system
  "Run after all other systems, this sends the state of the world to
  all the bots"
  [{:keys [components] :as game}]
  (do
    (mapv
     (partial put-tick-payload game)
     (ecs/fetch-components components :chan))
    game))

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


; Movement actions

(def Rest
  [(s/one (s/eq "REST") "action")])
; Charges player energy

(def Move
  [(s/one (s/eq "MOVE") :action)
   (s/one (s/enum "←" "↓" "↑" "→") :direction)])
;  Moves bot position

(def Turn
  [(s/one (s/eq "TURN") :action)
   (s/one s/Int :angle)])
; Turns bot's angle


; Environment Actions

(def MaxMessageSize 140)

(def Say
 [(s/one (s/eq "SAY") :action)
  (s/one (s/both s/Str (s/pred #(<= (count %) MaxMessageSize))) :message)])
; Communicates with any other entities in range


; Inventory actions

(def Take
  [(s/one (s/eq "TAKE") :action)])
; Take an object that bot is standing on and place in inventory

(def Use
  [(s/one (s/eq "USE") :action)
   (s/one s/Int :item)])
; Use an object (every object can be used, though it may be a no-op)

(def Drop
  [(s/one (s/eq "DROP") :action)
   (s/one s/Int :item)])
; drops an inventory item


; Crafting actions

(def Fab
  [(s/one (s/eq "FAB") :action)
   (s/one s/Str :item)])

(def Energize
  [(s/one (s/eq "↯") :action)
  (s/one s/Str :item)])



; Crafting actions


; (def Grab)

; Short range communication for the robots

  ; USE
  ; TAKE
  ; DROP
  ;
  ; SAY msg

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
