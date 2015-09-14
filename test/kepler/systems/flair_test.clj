(ns kepler.systems.flair-test
  (:require [clojure.test :refer :all]
            [kepler.actions :refer [flair-action]]
            [kepler.systems.flair :refer :all]))

(deftest flair-system-test
  (is (= (flair-system [{:entity 1 :type :flair :val nil}]
                       (flair-action 1 255 255 255))
         [{:entity 1 :type :flair :val {:r 255
                                        :g 255
                                        :b 255}}])))
