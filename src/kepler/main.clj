(ns kepler.main
  (:gen-class)
  (:require [kepler
             [clock :refer [with-clock]]
             [engine :refer [dispatch-action with-engine]]
             [ws-handler :refer [new-ws-handler]]]
            [kepler.systems.game :refer [game-system]]
            [yoyo.http-kit :refer [with-server]]
            [yoyo :refer [set-system-fn! start! ylet]]))

(defn- make-system [latch]
  (ylet [:let [port (Integer/parseInt (System/getenv "PORT"))]
         engine (with-engine game-system)
         :let [dispatcher (partial dispatch-action engine)
               ws-handler (new-ws-handler {:dispatcher dispatcher
                                           :format :json})]
         clock  (with-clock {:period 300
                             :dispatcher dispatcher})
         wss (with-server {:handler ws-handler
                           :clock clock
                           :server-opts {:port port}})]
        (latch)))

(defn -main [& args]
  (set-system-fn! 'kepler.main/make-system)
  (start!))
