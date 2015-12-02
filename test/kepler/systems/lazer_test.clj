(ns kepler.systems.lazer-test
  (:require [clojure.test :refer :all]
            [kepler.systems.lazer :refer :all]
            [kepler.actions :refer [tick shoot]]))

(deftest lazer-system-test
  (testing "tick action"
    (let [state []
          action (tick)]
      (is (= (lazer-system state action)
             state))))

  (testing "shoot action"
    (let [state '({:entity 1 :type :pos :val {:x 0 :y 0}}
                  {:entity 1 :type :rot :val 45}

                  {:entity 2 :type :pos :val {:x 10 :y 10}}
                  {:entity 2 :type :life :val 100})
          action (shoot 1)]
      (is (= (lazer-system state action)
             '({:entity 1 :type :pos :val {:x 0 :y 0}}
               {:entity 1 :type :rot :val 45}

               {:entity 2 :type :pos :val {:x 10 :y 10}}
               {:entity 2 :type :life :val 90}))))))
