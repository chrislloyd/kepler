(ns kepler.item.lazer-test
  (:require [clojure.test :refer :all]
            [kepler.item :refer [use-item]]
            [kepler.item.lazer :refer :all]))

(deftest use-item-test
  (let [item (new-lazer 100 10)
        state [{:entity "attacker" :type :pos :val {:x 0 :y 0}}
               {:entity "attacker" :type :rotation :val 45}
               {:entity "victim" :type :pos :val {:x 10 :y 10}}]]
    (is (= (use-item item "attacker" (conj state
                                           {:entity "victim" :type :life :val 100}))
           (conj state
                 {:entity "victim" :type :life :val 10})))))
