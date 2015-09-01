(ns kepler.component-test
  (:require [clojure.test :refer :all]
            [kepler.component :refer :all]))

(def component (new-component :test :ok))

(deftest new-component-test
  (is (= (type component) kepler.component.Component))
  (is (= (:tag component) :test))
  (is (= (:data component) :ok)))

(deftest add-component-test
  (is (= (add-component '() 1 component)
         '[[1 :test :ok]])))

(deftest remove-component-test
  (is (= (remove-component '([1 :test :ok]) 1 :test)
         '())))

(deftest update-component-test
  (is (= (update-component '([1 :test :fail]) 1 :test component)
         '([1 :test :ok]))))
