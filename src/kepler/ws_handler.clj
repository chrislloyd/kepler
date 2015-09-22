(ns kepler.ws-handler
  (:require [chord.http-kit :refer [wrap-websocket-handler]]
            [clojure.core.async :refer [>!! alt! chan close! go]]
            [compojure.core :refer [routes GET OPTIONS]]
            [kepler.actions :refer [add-bot-action]]
            [cheshire.core :as json]
            [kepler.actions :refer [bad-cmd-action]]
            [ring.util.response :refer [response]]
            [kepler.actions :refer [flair-action]]
            [kepler
             [actions :refer [move-action name-action shoot-action turn-action]]
             [command :refer [check-command]]
             [entity :refer [new-entity]]]
            [kepler.actions :refer [remove-bot-action]]))

(defn- cmd-action [entity cmd]
  (if (nil? (check-command cmd))
    (let [[name arg] cmd]
      (case name
        "MOVE" (move-action entity arg)
        "TURN" (turn-action entity arg)
        "SHOOT" (shoot-action entity)
        "NAME" (name-action entity arg)
        "FLAIR" (flair-action entity
                              (nth cmd 1)
                              (nth cmd 2)
                              (nth cmd 3))))
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

(defn- wrap-docs-redirect-handler [handler {:keys [docs-url]}]
  (fn [req]
    (if-not (:websocket? req)
      {:status 303
       :headers {"Location" docs-url}}
      (handler req))))

(defn new-ws-handler [{:keys [dispatcher] :as opts}]
  (-> handler
      (partial dispatcher)
      (wrap-websocket-handler opts)
      (wrap-docs-redirect-handler opts)))
