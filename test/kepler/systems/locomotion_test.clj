(ns kepler.systems.locomotion-test
  (:require [clojure.test :refer :all]
            [kepler.component.position :refer [position-component]]
            [kepler.systems.locomotion :refer :all]
            [kepler.actions :refer [move]]))

(deftest test-locomotion-system
  (testing "any action"
    (is (= (locomotion-system '() {}) '())))

  (testing "move up action"
    (let [action (move 1 "up")
          state [(position-component {:x 0 :y 0} 1)]]
      (is (= (locomotion-system state action)
             [(position-component {:x 0 :y 1} 1)]))))

  (testing "move down action"
    (let [action (move 1 "down")
          state [(position-component {:x 0 :y 0} 1)]]
      (is (= (locomotion-system state action)
             [(position-component {:x 0 :y -1} 1)]))))

  (testing "move left action"
    (let [action (move 1 "left")
          state [(position-component {:x 0 :y 0} 1)]]
      (is (= (locomotion-system state action)
             [(position-component {:x -1 :y 0} 1)]))))

  (testing "move right action"
    (let [action (move 1 "right")
          state [(position-component {:x 0 :y 0} 1)]]
      (is (= (locomotion-system state action)
             [(position-component {:x 1 :y 0} 1)])))))
