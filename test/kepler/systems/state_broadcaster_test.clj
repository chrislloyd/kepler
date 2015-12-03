(ns kepler.systems.state-broadcaster-test
  (:require [clojure.core.async :refer [<!! chan]]
            [clojure.test :refer :all]
            [kepler.systems.state-broadcaster :refer :all]
            [kepler.actions :refer [tick]]
            [kepler.components :refer [remote-control]]
            [kepler.components :refer [health]]
            [kepler.components :refer [position]]))

(deftest state-broadcaster-system-test
  (testing "tick action"
    (let [c (chan)
          state [{:entity 1 :type remote-control :val c}
                 {:entity 1 :type health :val 100}
                 {:entity 1 :type position :val {:x 0 :y 0}}]
          action (tick)]
      (is (= (do
               (state-broadcaster-system state action)
               (<!! c))
             {:type :tick
              :state {:tick (:tick action)
                      :me 1
                      :entities '({:id 1
                                   :life 100
                                   :pos {:x 0 :y 0}})}})))))
