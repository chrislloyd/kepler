(ns kepler.handler
  (:require [kepler.ecs :as ecs]
            [chord.http-kit :refer [wrap-websocket-handler]]
            [clojure.core.async :refer [<! >! >!! go] :as async]))

(defn- ws-handler [out {:keys [ws-channel] :as req}]
  (let [entity (ecs/new-entity)
        in (async/chan)]
    (go
      (>!! out {:type :join :entity entity :chan in})
      (loop []
        (async/alt!
          ws-channel ([message]
                      (if message
                        (do
                          (>!! out {:type :action
                                    :entity entity
                                    :action message})
                          (recur))

                        (>!! out {:type :leave
                                  :entity entity})))
          in ([event]
              (case (:type event)
                :tick (do
                        (>!! ws-channel (:payload event))
                        (recur))
                :kick (>!! ws-channel event))))))))

(defn new-handler [ch opts]
  (wrap-websocket-handler (partial ws-handler ch) opts))
