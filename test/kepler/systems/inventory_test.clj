(ns kepler.systems.inventory-test
  (:require [clojure.test :refer :all]
            [kepler.systems.inventory :refer :all]))

(deftest inventory-system-test
  (testing "tick action"
    (let [state []
          action {:type :tick}]
      (is (= (inventory-system state action)
             state)))))

