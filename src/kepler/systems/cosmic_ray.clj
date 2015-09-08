(ns kepler.systems.cosmic-ray
  (:require [kepler.component.life :refer [hurt life-component]]
            [kepler.component :refer [update-component-val]]))

(def radiation-decay (partial hurt 1))

(defn cosmic-ray-system [state {:keys [type]}]
  (if (= type :tick)
    (let [life-type (:type (life-component nil nil))]
      (map (fn [c]
             (if (= (:type c) life-type)
               (update-component-val radiation-decay c)
               c))
           state))
    state))
