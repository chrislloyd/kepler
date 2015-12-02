(ns kepler.systems.state-broadcaster-test
  (:require [clojure.core.async :refer [<!! chan]]
            [clojure.test :refer :all]
            [kepler.systems.state-broadcaster :refer :all]
            [kepler.actions :refer [tick]]))

(deftest state-broadcaster-system-test
  (testing "tick action"
    (let [c (chan)
          state [{:entity 1 :type :uplink :val c}
                 {:entity 1 :type :life :val 100}
                 {:entity 1 :type :pos :val {:x 0 :y 0}}]
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
