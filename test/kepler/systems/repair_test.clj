(ns kepler.systems.repair-test
  (:require [clojure.test :refer :all]
            [kepler.systems.repair :refer :all]
            [kepler.actions :refer [repair]]))

(deftest repair-system-test
  (let [state [{:entity 1 :type :life :val 1}]
        action (repair 1)]
    (is (= (repair-system state action)
           [{:entity 1 :type :life :val 2}]))))
