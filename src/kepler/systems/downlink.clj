(ns kepler.systems.downlink
  (:require [clojure.core.async :refer [>!!]]))

(defn- payload [state entity]
  ["tick", entity])

(defn- send-payload-downlink [chan payload]
  (let [event {:type :tick :payload payload}]
    (>!! chan event)))

(defn downlink-system [state {:keys [type]}]
  (if (= type :tick)
    (do
      ;; If this operation was done serially it would be a potential attack vector. The first bot could be slow to accept the connection holding up all the other connections. This could still possibly be the case because `pmap` blocks on the return value, however it just slows the world. A potential optimization could be doing the send in a go block with a non-blocking put.
      (pmap
       (fn [[entity _ val]]
         (send-payload-downlink val (payload state entity)))
       (filter #(= (second %) :uplink) state))
      state)
    state))
