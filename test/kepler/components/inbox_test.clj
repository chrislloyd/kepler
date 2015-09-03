(ns kepler.components.inbox-test
  (:require [clojure.test :refer :all]
            [kepler.components.inbox :refer :all]))

(deftest send-msg-test
  (is (some #{"Hello world"}
            (send-msg "Hello world" []))))

(deftest zero-inbox-test
  (is (empty? (zero-inbox ["a" "b" "c"]))))

