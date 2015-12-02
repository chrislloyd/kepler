(ns kepler.ws-handler
  (:require [chord.http-kit :refer [wrap-websocket-handler]]
            [clojure.core.async :refer [>!! alt! chan close! go]]
            [compojure.core :refer [routes GET OPTIONS]]
            [cheshire.core :as json]
            [ring.util.response :refer [response]]
            [kepler
             [actions :refer [add-bot remove-bot invalid-cmd move rename shoot turn]]
             [command :refer [check-command]]
             [entity :refer [new-entity]]]))

(defn- cmd [entity cmd]
  (if (nil? (check-command cmd))
    (let [[name arg] cmd]
      (case name
        "MOVE" (move entity arg)
        "TURN" (turn entity arg)
        "SHOOT" (shoot entity)
        "NAME" (name entity arg)))
    (invalid-cmd entity cmd)))

(defn- handler [dispatch {:keys [ws-channel] :as req}]
  (let [entity (new-entity)
        uplink (chan)]
    (go
      (dispatch (add-bot entity uplink))
      (loop []
        (alt!
          ws-channel ([message]
                      (if message
                        (do
                          (dispatch (cmd entity (:message message)))
                          (recur))
                        (dispatch (remove-bot entity))))
          uplink ([event]
                  (case (:type event)
                    :tick (do
                            (>!! ws-channel event)
                            (recur))
                    :kick (do
                            (>!! ws-channel event)
                            (dispatch (remove-bot entity))
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
