(ns kepler.component)

(defrecord Component [tag
                      data])

(defn new-component
  [tag data]
  (Component. tag data))

(defn add-component
  "Adds a component to the list of components"
  [state entity {:keys [tag data]}]
  (conj state [entity tag data]))

(defn remove-component
  "Removes any instances of a component from the list of components"
  [state entity tag]
  (filter
   (fn [[e t]]
     (not (and (= e entity) (= t tag))))
   state))

(defn update-component
  "Updates a component record in place. Useful for setting component values."
  [state entity tag new-component]
  (-> state
      (remove-component entity tag)
      (add-component entity new-component)))
