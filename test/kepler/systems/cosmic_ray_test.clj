(ns kepler.systems.cosmic-ray-test
  (:require [clojure.test :refer :all]
            [kepler.systems.cosmic-ray :refer :all]))

(deftest cosmic-rays-system-test
  (testing "any action"
    (is (= (cosmic-ray-system [] {})
           [])))

  (testing "tick action"
    (is (= (cosmic-ray-system [{:entity 1 :type :life :val 100}] {:type :tick})
           [{:entity 1 :type :life :val 99}]))))
