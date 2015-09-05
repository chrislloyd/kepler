(ns kepler.component.remote-control
  (:require [kepler.component :refer [component]]))

(defn remote-control-component [chan entity]
  (component :uplink chan entity))
