(ns kepler.component.position-test
  (:require [clojure.test :refer :all]
            [kepler.component.position :refer :all]))

(deftest move-up-test
  (is (= (move-up {:x 0 :y 0})
         {:x 0 :y 1})))
