(ns kepler.components.life-test
  (:require [clojure.test :refer :all]
            [kepler.components.life :refer :all]))

(deftest hurt-test
  (is (= (hurt 50 100)
         50))
  (is (= (hurt 150 100)
         -50)))

(deftest heal-test
  (is (= (heal 100 0)
         100)))

(deftest is-dead?-test
  (is (is-dead? 0))
  (is (not (is-dead? 1))))
