(ns kepler.command
  (:require [schema.core :as s]))

;; (def MaxMsgSize 140)

(def MoveCommand
  [(s/one (s/eq "MOVE") "type") (s/one (s/enum "←" "↓" "↑" "→") "dir")])

(def TurnCommand
  [(s/one (s/eq "TURN") "type") (s/one s/Int "deg")])

;; (def SayCommand
;;   {:cmd (s/eq "say")
;;    :msg (s/both s/Str (s/pred #(<= (count %) MaxMsgSize)))})

;; (def TakeCommand
;;   {:cmd (s/eq "take")})

;; (def DropCommand
;;   {:cmd (s/eq "drop")
;;    :item s/Int})

;; (def ExoCommand
;;   {:cmd (s/eq "exo")
;;    :item s/Int})

;; (def UseCommand
;;   {:cmd (s/eq "use")
;;    :item s/Int})

;; (def FabCommand
;;   {:cmd (s/eq "fab")
;;    :schema s/Str})

(s/defschema Command
  "A schema for commands that players can send to their bots"
  (s/conditional
   #(= (first %) "MOVE") MoveCommand
   #(= (first %) "TURN") TurnCommand))

(defn check-command
  "Checks an command"
  [command]
  (s/check Command command))

(defn validate-command
  "Validates an command"
  [command]
  (s/validate Command command))
