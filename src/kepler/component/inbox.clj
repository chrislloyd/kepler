(ns kepler.component.inbox
  (:require [kepler.component :refer [component]]))

(defn inbox-component [entity]
  (component :inbox [] entity))

(defn send-msg [msg inbox]
  (conj inbox msg))

(defn zero-inbox [inbox]
  (empty inbox))
