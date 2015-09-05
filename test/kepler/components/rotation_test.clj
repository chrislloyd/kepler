(ns kepler.components.rotation-test
  (:require [clojure.test :refer :all]
            [kepler.components.rotation :refer :all]))

(deftest turn-test
  (is (= (turn 360 0) 0))
  (is (= (turn 90 0) 90))
  (is (= (turn 450 0) (turn 90 0))))
