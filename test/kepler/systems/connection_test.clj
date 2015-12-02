(ns kepler.systems.connection-test
  (:require [clojure.test :refer :all]
            [kepler.systems.connection :refer :all]
            [kepler.actions :refer [add-bot remove-bot]]))

(deftest connection-system-test
  (testing "add bot action"
    (is (not (empty? (connection-system [] (add-bot 1 "chan"))))))

  (testing "remove bot action"
    (is (= (connection-system [{:entity 1 :type :life :val 100}
                               {:entity 1 :type :uplink :val "chan"}]
                              (remove-bot 1))
           []))))
