(ns kepler.systems.decomposition-test
  (:require [clojure.test :refer :all]
            [kepler.systems.decomposition :refer :all]))

(deftest decomposition-system-test
  (testing "tick action"
    (let [state []
          action {:type :tick}]
      (is (= (decomposition-system state action)
             state))
      
      
      (let [state [{:entity 1 :type :life :val 0}
                   {:entity 1 :type :foo :val "foo"}
                   {:entity 2 :type :life :val 1}]]
        (is (= (decomposition-system state action)
               [{:entity 2 :type :life :val 1}]))))))
