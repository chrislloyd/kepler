(ns kepler.systems.locomotion-test
  (:require [clojure.test :refer :all]
            [kepler.systems.locomotion :refer :all]
            [kepler.components.position :refer [position-component]]))

(deftest test-locomotion-system
  (testing "any action"
    (is (= (locomotion-system '() {}) '())))

  (testing "move up action"
    (let [action {:type :cmd
                  :entity 1
                  :cmd ["MOVE" "↑"]}
          state [(position-component {:x 0 :y 0} 1)]]
      (is (= (locomotion-system state action)
             [(position-component {:x 0 :y 1} 1)]))))

  (testing "move down action"
    (let [action {:type :cmd
                  :entity 1
                  :cmd ["MOVE" "↓"]}
          state [(position-component {:x 0 :y 0} 1)]]
      (is (= (locomotion-system state action)
             [(position-component {:x 0 :y -1} 1)]))))

  (testing "move left action"
    (let [action {:type :cmd
                  :entity 1
                  :cmd ["MOVE" "←"]}
          state [(position-component {:x 0 :y 0} 1)]]
      (is (= (locomotion-system state action)
             [(position-component {:x -1 :y 0} 1)]))))

  (testing "move right action"
    (let [action {:type :cmd
                  :entity 1
                  :cmd ["MOVE" "→"]}
          state [(position-component {:x 0 :y 0} 1)]]
      (is (= (locomotion-system state action)
             [(position-component {:x 1 :y 0} 1)])))))

