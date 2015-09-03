(ns kepler.systems.cosmic-ray
  (:require [kepler.component :refer [update-component-val]]
            [kepler.components.life :refer [hurt life-component]]))

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
