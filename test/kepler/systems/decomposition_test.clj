(ns kepler.systems.decomposition-test
  (:require [clojure.test :refer :all]
            [kepler
             [actions :refer [tick]]
             [components :refer [health]]]
            [kepler.systems.decomposition :refer :all]))

(deftest decomposition-system-test
  (testing "tick action"
    (let [state []
          action (tick)]
      (is (= (decomposition-system state action)
             state))


      (let [state [{:entity 1 :type health :val 0}
                   {:entity 2 :type health :val 1}]]
        (is (= (decomposition-system state action)
               [{:entity 2 :type health :val 1}]))))))
