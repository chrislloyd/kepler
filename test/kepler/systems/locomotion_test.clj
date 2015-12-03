(ns kepler.systems.locomotion-test
  (:require [clojure.test :refer :all]
            [kepler
             [actions :refer [move]]
             [components :refer [position]]]
            [kepler.systems.locomotion :refer :all]))

(deftest test-locomotion-system
  (testing "any action"
    (is (= (locomotion-system '() {}) '())))

  (testing "move up action"
    (let [action (move 1 "up")
          state [{:entity 1 :type position :val {:x 0 :y 0}}]]
      (is (= (locomotion-system state action)
             [{:entity 1 :type position :val {:x 0 :y 1}}]))))

  (testing "move down action"
    (let [action (move 1 "down")
          state [{:entity 1 :type position :val {:x 0 :y 0}}]]
      (is (= (locomotion-system state action)
             [{:entity 1 :type position :val {:x 0 :y -1}}]))))

  (testing "move left action"
    (let [action (move 1 "left")
          state [{:entity 1 :type position :val {:x 0 :y 0}}]]
      (is (= (locomotion-system state action)
             [{:entity 1 :type position :val {:x -1 :y 0}}]))))

  (testing "move right action"
    (let [action (move 1 "right")
          state [{:entity 1 :type position :val {:x 0 :y 0}}]]
      (is (= (locomotion-system state action)
             [{:entity 1 :type position :val {:x 1 :y 0}}])))))
