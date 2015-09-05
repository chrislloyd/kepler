(ns kepler.command-test
  (:require [clojure.test :refer :all]
            [kepler.command :refer :all]))

(deftest valiate-command-test
  (is (validate-command ["MOVE", "→"]))
  (is (validate-command ["TURN", 360]))
  (is (validate-command ["USE", "abcd-1234"])))

