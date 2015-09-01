(ns kepler.clock-test
  (:require [clojure.test :refer :all]
            [kepler.clock :refer :all]))

(deftest new-clock-test
  (let [clock (new-clock {:period 300 :dispatcher identity})]
    (is (= (type clock)
           kepler.clock.Clock))

    (is (not @(:running? clock)))))

(deftest start-clock-test)

(deftest stop-clock-test)
