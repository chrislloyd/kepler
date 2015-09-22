(ns kepler.systems.coordinate-test
  (:require [clojure.test :refer :all]
            [kepler.systems.coordinate :refer :all]))

(deftest coordinate-system-test
  (testing "any action"
    (is (= (coordinate-system '() {})
           '())))

  (testing "tick action"
    (let [state '()
          action {:type :tick}]
      (is (= (coordinate-system state action) state)))

    (let [state '({:entity 1 :type :pos :val {:x 1000 :y 1000}})
          action {:type :tick}]
      (is (= (coordinate-system state action)
             '({:entity 1 :type :pos :val {:x 0 :y 0}}))))))
