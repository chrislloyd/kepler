(ns kepler.ws-handler
  (:require [chord.http-kit :refer [wrap-websocket-handler]]
            [clojure.core.async :refer [>!! alt! close! chan go]]
            [kepler.command :refer [check-command]]
            [kepler.entity :refer [new-entity]]))

(defn- add-bot-action [entity chan]
  {:type :add-bot :entity entity :chan chan})

(defn- remove-bot-action [entity]
  {:type :remove-bot :entity entity})

(defn- repair-action [entity]
  {:type :repair :entity entity})

(defn- move-action [entity dir]
  {:type :move :entity entity :dir dir})

(defn- turn-action [entity dr]
  {:type :turn :entity entity :dr dr})

(defn- use-action [entity item]
  {:type :use :entity entity :item item})

(defn- bad-cmd-action [entity cmd]
  {:type :bad-cmd :entity entity :cmd cmd})

(defn- name-action [entity new-name]
  {:type :name :entity entity :name new-name})

(defn- cmd-action [entity cmd]
  (if (nil? (check-command cmd))
    (let [[name arg] cmd]
      (case name
        "REPAIR" (repair-action entity)
        "MOVE" (move-action entity arg)
        "TURN" (turn-action entity arg)
        "USE" (use-action entity arg)
        "NAME" (name-action entity arg)))
    (bad-cmd-action entity cmd)))

(defn- handler [dispatch {:keys [ws-channel] :as req}]
  (let [entity (new-entity)
        uplink (chan)]
    (go
      (dispatch (add-bot-action entity uplink))
      (loop []
        (alt!
          ws-channel ([message]
                      (if message
                        (do
                          (dispatch (cmd-action entity (:message message)))
                          (recur))
                        (dispatch (remove-bot-action entity))))
          uplink ([event]
              (case (:type event)
                :tick (do
                        (>!! ws-channel event )
                        (recur))
                :kick (do
                        (>!! ws-channel event)
                        (dispatch {:type :remove-bot
                                   :entity entity})
                        (close! ws-channel)))))))))

(defn new-ws-handler [{:keys [dispatcher] :as opts}]
  (wrap-websocket-handler (partial handler dispatcher) opts))

