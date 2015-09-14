(ns kepler.command
  (:require [schema.core :as s]))

(def RepairCommand
  [(s/one (s/eq "REPAIR") "type")])

(def MoveCommand
  [(s/one (s/eq "MOVE") "type") (s/one (s/enum "up" "down" "left" "right") "dir")])

(def TurnCommand
  [(s/one (s/eq "TURN") "type") (s/one s/Num "dr")])

(def ShootCommand
  [(s/one (s/eq "SHOOT") "type")])

(def NameCommand
  [(s/one (s/eq "NAME") "type") (s/both s/Str (s/pred #(<= (count %) 140)))])

(def FlairCommand
  [(s/one (s/eq "FLAIR") "type")
   (s/one s/Int "r")
   (s/one s/Int "g")
   (s/one s/Int "b")])


(s/defschema Command
  "A schema for commands that players can send to their bots"
  (s/conditional
   ;; #(= (first %) "REPAIR") RepairCommand
   #(= (first %) "MOVE") MoveCommand
   #(= (first %) "TURN") TurnCommand
   #(= (first %) "SHOOT") ShootCommand
   #(= (first %) "NAME") NameCommand
   #(= (first %) "FLAIR") FlairCommand))

(defn check-command
  "Checks an command"
  [command]
  (s/check Command command))

(defn validate-command
  "Validates an command"
  [command]
  (s/validate Command command))
