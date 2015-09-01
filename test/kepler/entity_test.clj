(ns kepler.entity-test
  (:require [clojure.test :refer :all]
            [kepler.entity :refer :all]))

(deftest new-entity-test
  (is (= (type (new-entity))
         java.util.UUID)))
