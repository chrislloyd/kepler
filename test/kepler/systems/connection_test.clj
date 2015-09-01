(ns kepler.systems.connection-test
  (:require [clojure.test :refer :all]
            [kepler.systems.connection :refer :all]))

(def add-bot-action {:type :add-bot :entity 1 :chan "chan"})
(def remove-bot-action {:type :remove-bot :entity 1})

(deftest connection-system-test
  (testing "add bot action"
    (is (=
         (connection-system '() add-bot-action)
         '([1 :pos {:x 0 :y 0}] [1 :life 100] [1 :uplink "chan"]))))

  (testing "remove bot action"
    (is (= (connection-system '([1 :life 100] [1 :uplink "chan"])
                              remove-bot-action)
           '([1 :life 100])))))
