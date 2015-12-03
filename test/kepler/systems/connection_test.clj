(ns kepler.systems.connection-test
  (:require [clojure.test :refer :all]
            [kepler
             [actions :refer [add-bot remove-bot]]
             [components :refer [health remote-control]]]
            [kepler.systems.connection :refer :all]))

(deftest connection-system-test
  (testing "add bot action"
    (is (not (empty? (connection-system [] (add-bot 1 "chan"))))))

  (testing "remove bot action"
    (is (= (connection-system [{:entity 1 :type health :val 100}
                               {:entity 1 :type remote-control :val "chan"}]
                              (remove-bot 1))
           []))))
