(ns kepler.systems.coordinate-test
  (:require [clojure.test :refer :all]
            [kepler.systems.coordinate :refer :all]))

(deftest coordinate-system-test
  (testing "any action"
    (is (= (coordinate-system '() {})
           '())))
  
  (testing "tick action"
    (let [state '([1 :pos {:x 0 :y 0}])
          action {:type :tick}]
      (is (= (coordinate-system state action) state)))
    
    (let [state '([1 :pos {:x 1000 :y 1000}])
          action {:type :tick}]
      (is (= (coordinate-system state action)
             '([1 :pos {:x 0 :y 0}]))))))
