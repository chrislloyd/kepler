(ns kepler.item.lazer-test
  (:require [clojure.test :refer :all]
            [kepler.item.lazer :refer :all]
            [kepler.item :refer [use-item]]))

(deftest pt-in-range-test
  (let [weapon (new-weapon {:range 10 :spread 5})]
    (is (pt-in-range? weapon {:x 0 :y 0} 0 {:x 0 :y 10}))
    (is (not (pt-in-range? weapon {:x 0 :y 0} 0 {:x 0 :y 11})))
    (is (not (pt-in-range? weapon {:x 0 :y 0} 90 {:x 0 :y 10})))))

(deftest deal-damage-to-victims-test
  (let [weapon (new-weapon {:damage 10})
        victims #{"victim"}]
    (is (= (deal-damage-to-victims weapon
                                   victims
                                   [{:entity "victim"
                                     :type :life
                                     :val 100}])
           [{:entity "victim" :type :life :val 90}]))))

(deftest use-item-test
  (let [item (new-weapon {:damage 50
                          :spread 5
                          :range 100})
        state '({:entity "attacker" :type :pos :val {:x 0 :y 0}}
                {:entity "attacker" :type :rot :val 45}
                {:entity "victim" :type :pos :val {:x 10 :y 10}})]
    (is (= (use-item item "attacker" (conj state
                                           {:entity "victim" :type :life :val 100}))
           (conj state
                 {:entity "victim" :type :life :val 50})))))
