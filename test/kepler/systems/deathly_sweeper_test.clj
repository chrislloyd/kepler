(ns kepler.systems.deathly-sweeper-test
  (:require [clojure.core.async :refer [<!! chan]]
            [clojure.test :refer :all]
            [kepler.systems.deathly-sweeper :refer :all]))

(defn state-fixture [ch]
  '([1 :life 0.0] [1 :uplink ch] [2 :life 100.0]))

(def tick-action {:type :tick})


(deftest deathly-sweeper-system-test
  (testing "any action"
    (is (= (deathly-sweeper-system '() {})
           '())))

  (testing "tick action"
        (let [c (chan)
          state (conj '() [1 :life 0] [1 :uplink c] [2 :life 100])
          action {:type :tick}
          subject (deathly-sweeper-system state action)]
      (is (= subject state))
      (is (= (<!! c) {:type :kick :msg "dead"})))))
