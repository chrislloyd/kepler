(ns kepler.main
  (:gen-class)
  (:require [kepler.clock :refer [with-clock]]
            [kepler.engine :refer [with-engine dispatch-action]]
            [kepler.system :refer [game-system]]
            [kepler.wss :refer [new-ws-handler]]
            [yoyo.http-kit :refer [with-server]]
            [yoyo :refer [ylet set-system-fn! start!]]))

(defn- make-system [latch]
  (ylet [engine (with-engine game-system)
         :let [dispatcher (partial dispatch-action engine)
               ws-handler (new-ws-handler {:dispatcher dispatcher
                                           :format :json})]
         clock  (with-clock {:period 300
                             :dispatcher dispatcher})
         wss (with-server {:handler ws-handler
                           :server-opts {:port 5000}})]
        (latch)))

(defn -main [& args]
  (set-system-fn! 'kepler.main/make-system)
  (start!))
