(ns kepler.command
  (:require [schema.core :as s]))

;; (def MaxMsgSize 140)

(def RepairCommand
  [(s/one (s/eq "REPAIR") "type")])

(def MoveCommand
  [(s/one (s/eq "MOVE") "type") (s/one (s/enum "up" "down" "left" "right") "dir")])

(def TurnCommand
  [(s/one (s/eq "TURN") "type") (s/one s/Num "dr")])

(def UseCommand
  [(s/one (s/eq "USE") "type") (s/one s/Str "item")])

(def NameCommand
  [(s/one (s/eq "NAME") "type") (s/both s/Str (s/pred #(<= (count %) 140)))])

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
   #(= (first %) "REPAIR") RepairCommand
   #(= (first %) "MOVE") MoveCommand
   #(= (first %) "TURN") TurnCommand
   #(= (first %) "USE") UseCommand
   #(= (first %) "NAME") NameCommand))

(defn check-command
  "Checks an command"
  [command]
  (s/check Command command))

(defn validate-command
  "Validates an command"
  [command]
  (s/validate Command command))
