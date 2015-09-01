(ns kepler.main
  (:gen-class)
  (:require [kepler.engine :refer [with-engine dispatch-action]]
            [kepler.clock :refer [with-clock]]
            [kepler.wss :refer [with-wss]]
            [kepler.system :refer [game-system]]
            [yoyo :refer [ylet set-system-fn! start!]]
            [clojure.core.async :refer [chan close!]]))

(defn make-system [latch]
  (ylet [engine (with-engine game-system)
         :let [dispatcher (partial dispatch-action engine)]
         clock  (with-clock {:period 300
                             :dispatcher dispatcher})
         wss (with-wss {:port 5000
                        :dispatcher dispatcher})]
        (latch)))

(defn -main [& args]
  (set-system-fn! 'kepler.main/make-system)
  (start!))
