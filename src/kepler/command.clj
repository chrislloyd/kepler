(ns kepler.command
  (:require [schema.core :as s]))

(def MaxMsgSize 140)

(def RestCommand
  {:cmd (s/eq "rest")})

(def MoveCommand
  {:cmd (s/eq "move")
   :dir (s/enum "←" "↓" "↑" "→")})

(def TurnCommand
  {:cmd (s/eq "turn")
   :deg s/Int})

(def SayCommand
  {:cmd (s/eq "say")
   :msg (s/both s/Str (s/pred #(<= (count %) MaxMsgSize)))})

(def TakeCommand
  {:cmd (s/eq "take")})

(def DropCommand
  {:cmd (s/eq "drop")
   :item s/Int})

(def ExoCommand
  {:cmd (s/eq "exo")
   :item s/Int})

(def UseCommand
  {:cmd (s/eq "use")
   :item s/Int})

(def FabCommand
  {:cmd (s/eq "fab")
   :schema s/Str})

(s/defschema Command
  "A schema for commands that players can send to their bots"
  (s/conditional
   #(= (:cmd %) "rest") RestCommand
   #(= (:cmd %) "move") MoveCommand
   #(= (:cmd %) "turn") TurnCommand
   #(= (:cmd %) "say")  SayCommand
   #(= (:cmd %) "take") TakeCommand
   #(= (:cmd %) "drop") DropCommand
   #(= (:cmd %) "exo")  ExoCommand
   #(= (:cmd %) "use")  UseCommand
   #(= (:cmd %) "fab")  FabCommand))

(defn check-command
  "Checks an command"
  [command]
  (s/check Command command))

(defn validate-command
  "Validates an command"
  [command]
  (s/validate Command command))
