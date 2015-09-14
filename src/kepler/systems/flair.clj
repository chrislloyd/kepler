(ns kepler.systems.flair
  (:require [kepler.component :refer [update-component-val]]))

(defn flair-system [state action]
  (if (= (:type action) :flair)
    (map (fn [component]
           (if (and (= (:entity component) (:entity action))
                    (= (:type component) :flair))
             (let [flair {:r (:r action)
                          :g (:g action)
                          :b (:b action)}]
               (update-component-val (constantly flair)
                                     component))
             component))
         state)
    state))
