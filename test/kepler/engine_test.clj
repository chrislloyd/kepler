(ns kepler.engine-test
  (:require [clojure.test :refer :all]
            [kepler.engine :refer :all]))

(deftest new-engine-test
  (is (= (type (new-engine identity)) kepler.engine.Engine)))

