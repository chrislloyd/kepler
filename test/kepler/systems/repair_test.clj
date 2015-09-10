(ns kepler.systems.repair-test
  (:require [clojure.test :refer :all]
            [kepler.systems.repair :refer :all]))

(deftest repair-system-test
  (let [state [{:entity 1 :type :life :val 1}]
        action {:type :repair :entity 1}]
    (is (= (repair-system state action)
           [{:entity 1 :type :life :val 2}]))))
