(ns kepler.pt-test
  (:require [clojure.test :refer :all]
            [kepler.pt :refer :all]))

(deftest distance-test
  (is (= (distance (pt 0 0) (pt 3 4))
         5.0)))

(deftest angle-test
  (is (= (angle (pt 0 0) (pt 2 2))
         45.0)))

