(ns kepler.systems.connection-test
  (:require [clojure.test :refer :all]
            [kepler.systems.connection :refer :all]))

(def add-bot-action {:type :add-bot :entity 1 :chan "chan"})
(def remove-bot-action {:type :remove-bot :entity 1})

(deftest connection-system-test
  (testing "add bot action"
    (is (not (empty? (connection-system [] add-bot-action)))))

  (testing "remove bot action"
    (is (= (connection-system [{:entity 1 :type :life :val 100}
                               {:entity 1 :type :uplink :val "chan"}]
                              remove-bot-action)
           []))))
