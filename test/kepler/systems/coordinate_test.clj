(ns kepler.systems.coordinate-test
  (:require [clojure.test :refer :all]
            [kepler.systems.coordinate :refer :all]
            [kepler.actions :refer [tick]]))

(deftest coordinate-system-test
  (testing "any action"
    (is (= (coordinate-system '() {})
           '())))

  (testing "tick action"
    (let [state '()
          action (tick)]
      (is (= (coordinate-system state action) state)))

    (let [state '({:entity 1 :type :pos :val {:x 1000 :y 1000}})
          action (tick)]
      (is (= (coordinate-system state action)
             '({:entity 1 :type :pos :val {:x 0 :y 0}}))))))
