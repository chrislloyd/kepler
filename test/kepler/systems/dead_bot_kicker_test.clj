(ns kepler.systems.dead-bot-kicker-test
  (:require [clojure.core.async :refer [<!! chan]]
            [clojure.test :refer :all]
            [kepler.systems.dead-bot-kicker :refer :all]))

(defn state-fixture [ch]
  '([1 :life 0.0] [1 :uplink ch] [2 :life 100.0]))

(def tick-action {:type :tick})


(deftest dead-bot-kicker-system-test
  (testing "any action"
    (is (= (dead-bot-kicker-system '() {})
           '())))

  (testing "tick action"
        (let [c (chan)
          state (-> []
                    (conj {:entity 1 :type :life :val 0})
                    (conj {:entity 1 :type :uplink :val c})
                    (conj {:entity 2 :type :life :val 100}))
          action {:type :tick}
          subject (dead-bot-kicker-system state action)]
      (is (= subject state))
      (is (= (<!! c) {:type :kick :msg "dead"})))))
