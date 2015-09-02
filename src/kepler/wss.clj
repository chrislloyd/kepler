(ns kepler.wss
  (:require [chord.http-kit :refer [wrap-websocket-handler]]
            [clojure.core.async :refer [>!! alt! close! chan go]]
            [kepler.command :refer [check-command]]
            [kepler.entity :refer [new-entity]]))

(defn- add-bot-action [entity chan]
  {:type :add-bot
   :entity entity
   :chan chan})

(defn- remove-bot-action [entity]
  {:type :remove-bot
   :entity entity})

(defn- cmd-action [entity cmd]
  {:type (if (nil? (check-command cmd)) :cmd :bad-cmd)
   :entity entity
   :cmd cmd})

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
                        (>!! ws-channel (:payload event))
                        (recur))
                :kick (do
                        (>!! ws-channel event)
                        (dispatch {:type :remove-bot
                                   :entity entity})
                        (close! ws-channel)))))))))

(defn new-ws-handler [{:keys [dispatcher] :as opts}]
  (wrap-websocket-handler (partial handler dispatcher) opts))

