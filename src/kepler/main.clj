(ns kepler.main
  (:gen-class)
  (:require [kepler.assembly :refer :all]
            [kepler.ecs :as ecs]
            [kepler.engine :as engine]
            [kepler.systems :as systems]
            [kepler.handler :refer [new-handler]]
            [yoyo :as y]
            [yoyo.http-kit :as http-kit]
            [clojure.core.async :as async]))

(def Components [])

(def Systems [systems/event-system
              systems/broadcast-system])

; channels

; Game in channel (join, leave, action)
; Entity out channel (tick, tick)

(defn with-ws-server [out {:keys [port]} f]
  (let [handler (new-handler out {:format :json})]
    (http-kit/with-webserver {:port port :handler handler} f)))

(defn with-engine [in state f]
  (let [engine (engine/go-run in state)]
    (try
      (f state)
      (finally
        (async/close! in)))))

(defn make-system [latch]
  (let [ch (async/chan)]
    (y/ylet [ws-server (with-ws-server ch {:port 5000})
             game-engine (with-engine ch {:running? true
                                          :systems Systems
                                          :components Components
                                          :events []})]
            (latch))
    (async/close! ch)))

(defn -main [& args]
  (y/set-system-fn! 'kepler.main/make-system)
  (y/start!))
