(ns kepler.util
  (:require [clojure.core.async :refer [<!! >! chan go]]))

(defn async-pmap [f col]
  (let [chans (repeatedly (count col) chan)]
    (doseq [[c e] (map vector chans col)]
      (go (>! c (f e))))
    (map <!! chans)))

