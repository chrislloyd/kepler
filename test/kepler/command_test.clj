(ns kepler.command-test
  (:require [clojure.test :refer :all]
            [kepler.command :refer :all]))

(deftest valiate-command-test
  (is (validate-command ["MOVE", "up"]))
  (is (validate-command ["MOVE", "down"]))
  (is (validate-command ["MOVE", "left"]))
  (is (validate-command ["MOVE", "right"]))
  (is (validate-command ["TURN", 360]))
  (is (validate-command ["SHOOT"]))
  (is (validate-command ["NAME", "chrislloyd"]))
  (is (validate-command ["FLAIR", 255, 255, 255])))

