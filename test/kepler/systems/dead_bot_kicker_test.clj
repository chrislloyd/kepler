(ns kepler.systems.dead-bot-kicker-test
  (:require [clojure.core.async :refer [<!! chan]]
            [clojure.test :refer :all]
            [kepler
             [actions :refer [tick]]
             [components :refer [health remote-control]]]
            [kepler.systems.dead-bot-kicker :refer :all]))

(deftest dead-bot-kicker-system-test
  (testing "any action"
    (is (= (dead-bot-kicker-system '() {})
           '())))

  (testing "tick action"
        (let [c (chan)
              state (-> []
                        (conj {:entity 1 :type health :val 0})
                        (conj {:entity 1 :type remote-control :val c})
                        (conj {:entity 2 :type health :val 100}))
              action (tick)
              subject (dead-bot-kicker-system state action)]
         (is (= subject
                (-> []
                    (conj {:entity 2 :type health :val 100}))))
         (is (= (<!! c) {:type :kick :msg "dead"})))))
