(ns kepler.clock
  (:require [clojure.core.async :refer [<!! go-loop timeout]]
            [kepler.actions :refer [tick]]))

(defrecord Clock [period
                  dispatcher
                  running?])

(defn new-clock [{:keys [period dispatcher]}]
  (Clock. period dispatcher (atom false)))

(defn- dispatch-tick [clock]
  ((:dispatcher clock) (tick)))

(defn start-clock [clock]
  (do
    (swap! (:running? clock) (constantly true))
    (go-loop [clock clock]
        (when (and @(:running? clock) (dispatch-tick clock))
          (<!! (timeout (:period clock)))
          (recur clock)))))

(defn stop-clock [clock]
  (swap! (:running? clock) (constantly false)))

(defn with-clock [opts f]
  (let [clock (new-clock opts)]
    (start-clock clock)
    (try
      (f clock)
      (finally
        (stop-clock clock)))))
