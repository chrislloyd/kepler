(ns kepler.systems.inventory-test
  (:require [clojure.test :refer :all]
            [kepler.systems.inventory :refer :all]))

(deftest inventory-system-test
  (testing "tick action"
    (let [state []
          action {:type :tick}]
      (is (= (inventory-system state action)
             state))))

  (testing "shoot action"
    (let [state '({:entity 1 :type :pos :val {:x 0 :y 0}}
                  {:entity 1 :type :rot :val {:x 0 :y 0}}

                  {:entity 2 :type :pos :val {:x 10 :y 10}}
                  {:entity 2 :type :life :val 100})
          action {:type :shoot
                  :entity 1}]
      (is (= (inventory-system state action)
             '({:entity 1 :type :pos :val {:x 0 :y 0}}
               {:entity 1 :type :rot :val 45}
               
               {:entity 2 :type :pos :val {:x 10 :y 10}}
               {:entity 2 :type :life :val 90}))))))
