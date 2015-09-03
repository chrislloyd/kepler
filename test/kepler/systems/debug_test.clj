(ns kepler.systems.debug-test
  (:require [clojure.test :refer :all]
            [kepler.systems.debug :refer :all]))

(deftest debug-system-test
  (is (= (debug-system '() {}) '())))
