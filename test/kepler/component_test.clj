(ns kepler.component-test
  (:require [clojure.test :refer :all]
            [kepler.component :refer :all]))

(deftest new-component-test
  (let [component (new-component :test :ok)]
    (is (= (type component) kepler.component.Component))
    (is (= (:tag component) :test))
    (is (= (:data component) :ok))))

(deftest add-component-test
  (let [component (new-component :test :ok)]
    (is (= (add-component '() 1 component)
           '[[1 :test :ok]]))))

(deftest remove-component-test
  (is (= (remove-component '([1 :test :ok]) 1 :test)
         '())))

(deftest find-component-test
  (let [state '([1 :test :ok] [1 :test :fail])]
    (is (= (find-component state 1 :test)
           [1 :test :ok]))))

(deftest update-component-val-test
  (let [state '([1 :test :fail])]
    (is (= (update-component-val state 1 :test (constantly :ok))
           '([1 :test :ok])))))
