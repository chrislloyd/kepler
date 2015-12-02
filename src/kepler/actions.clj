(ns kepler.actions)

(defn add-bot-action [entity chan]
  {:type :add-bot
   :entity entity
   :chan chan})

(defn remove-bot-action [entity]
  {:type :remove-bot
   :entity entity})

(defn repair-action [entity]
  {:type :repair
   :entity entity})

(defn move-action [entity dir]
  {:type :move
   :entity entity
   :dir dir})

(defn turn-action [entity dr]
  {:type :turn
   :entity entity
   :dr dr})

(defn shoot-action [entity]
  {:type :shoot
   :entity entity})

(defn bad-cmd-action [entity cmd]
  {:type :bad-cmd
   :entity entity
   :cmd cmd})

(defn name-action [entity new-name]
  {:type :name
   :entity entity
   :name new-name})

(defn tick-action [id]
  {:type :tick
   :tick id})
