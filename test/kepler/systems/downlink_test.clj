(ns kepler.systems.downlink-test
  (:require [clojure.test :refer :all]
            [clojure.core.async :refer (<!! chan go)]
            [kepler.systems.downlink :refer :all]))

(deftest downlink-system-test
  (testing "tick action"
    (let [c (chan)
          state (conj '() [1 :uplink c])]
      (do
        (downlink-system state {:type :tick})
        (is (= (<!! c) {:type :tick :payload ["tick" 1]}))))))

