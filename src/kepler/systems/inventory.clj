(ns kepler.systems.inventory
  (:require [kepler.component :refer [get-component]]
            [kepler.item.lazer :refer [new-weapon]]
            [kepler.item :refer [use-item]]))

;;; incoming action
;;; {:type :use, :entity "abc-123", :item "abc-123"}

(def tech-tree {"lazer" (new-weapon {:damage 100
                                     :spread 5
                                     :range 20})})

(defn- item-in-inventory? [state entity item]
  (when-let [inventory-component (get-component state entity :inventory)]
    (contains? (:val inventory-component) item)))

(defn- entity-use-item [state {:keys [entity item]}]
  (if (item-in-inventory? state entity item)
    (if-let [item-component (get-component state item :item)]
      (let [item (get tech-tree (:val item-component))]
        (use-item item entity state))
      state)
    state))

(defn inventory-system [state action]
  (case (:type action)
    :use (entity-use-item state action)
    state))

