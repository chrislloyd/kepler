(ns kepler.systems.repair-test
  (:require [clojure.test :refer :all]
            [kepler
             [actions :refer [repair]]
             [components :refer [health]]]
            [kepler.systems.repair :refer :all]))

(deftest repair-system-test
  (let [state [{:entity 1 :type health :val 1}]
        action (repair 1)]
    (is (= (repair-system state action)
           [{:entity 1 :type health :val 2}]))))
