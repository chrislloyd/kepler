(ns kepler.component-test
  (:require [clojure.test :refer :all]
            [kepler.component :refer :all]))

(deftest component-test
  (let [c (component :health 100 1)]
    (is (= (type c) kepler.component.Component))))

(deftest update-component-val-test
  (let [c (component :foo "foo" 1)
        update-fn (constantly "bar")]
    (is (= (update-component-val update-fn c)
           (component :foo "bar" 1)))))

(deftest get-component-test
  (let [needle (component :foo "foo" 1)
        haystack (-> '()
                  (conj needle)
                  (conj (component :bar "bar" 1))
                  (conj (component :foo "foo" 2)))]
    (is (= (get-component haystack 1 :foo)
           needle))))

(deftest remove-component-test
  (let [c (component :foo "foo" 1)
        empty-state []
        state (conj empty-state c)]
      (is (= (remove-component state 1 :foo)
             empty-state))))
