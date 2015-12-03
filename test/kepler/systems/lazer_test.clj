(ns kepler.systems.lazer-test
  (:require [clojure.test :refer :all]
            [kepler
             [actions :refer [shoot tick]]
             [components :refer [health position rotation]]]
            [kepler.systems.lazer :refer :all]))

(deftest lazer-system-test
  (testing "tick action"
    (let [state []
          action (tick)]
      (is (= (lazer-system state action)
             state))))

  (testing "shoot action"
    (let [state [{:entity 1 :type position :val {:x 0 :y 0}}
                 {:entity 1 :type rotation :val 45}

                 {:entity 2 :type position :val {:x 10 :y 10}}
                 {:entity 2 :type health :val 100}]
          action (shoot 1)]
      (is (= (lazer-system state action)
             [{:entity 1 :type position :val {:x 0 :y 0}}
              {:entity 1 :type rotation :val 45}

              {:entity 2 :type position :val {:x 10 :y 10}}
              {:entity 2 :type health :val 90}])))))
