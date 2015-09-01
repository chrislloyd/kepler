(ns kepler.systems.cosmic-rays-test
  (:require [clojure.test :refer :all]
            [kepler.systems.cosmic-rays :refer :all]))

(deftest cosmic-rays-system-test
  (testing "any action"
    (is (= (cosmic-rays-system '() {})
           '())))

  (testing "tick action"
    (is (= (cosmic-rays-system '([1 :life 100]) {:type :tick})
           '([1 :life 99])))))
