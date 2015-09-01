(ns kepler.systems.locomotion-test
  (:require [clojure.test :refer :all]
            [kepler.systems.locomotion :refer :all]))

(deftest test-locomotion-system
  (testing "any action"
    (is (= (locomotion-system '() {}) '())))

  (testing "move up action"
    (let [action {:type :cmd
                  :entity 1
                  :cmd ["MOVE" "↑"]}
          state '([1 :pos {:x 0 :y 0}])]
      (is (= (locomotion-system state action)
             '([1 :pos {:x 0 :y 1}])))))

  (testing "move down action"
    (let [action {:type :cmd
                  :entity 1
                  :cmd ["MOVE" "↓"]}
          state '([1 :pos {:x 0 :y 0}])]
      (is (= (locomotion-system state action)
             '([1 :pos {:x 0 :y -1}])))))

  (testing "move left action"
    (let [action {:type :cmd
                  :entity 1
                  :cmd ["MOVE" "←"]}
          state '([1 :pos {:x 0 :y 0}])]
      (is (= (locomotion-system state action)
             '([1 :pos {:x -1 :y 0}])))))

  (testing "move right action"
    (let [action {:type :cmd
                  :entity 1
                  :cmd ["MOVE" "→"]}
          state '([1 :pos {:x 0 :y 0}])]
      (is (= (locomotion-system state action)
             '([1 :pos {:x 1 :y 0}]))))))

