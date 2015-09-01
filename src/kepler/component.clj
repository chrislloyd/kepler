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

(defn find-component
  [state entity tag]
  (first (filter (fn [[e t]]
                   (and (= e entity) (= t tag)))
                 state)))

(defn update-component-val
  "Updates a component record in place. Useful for setting component values."
  [state entity tag fn]
  (let [[_ t old-val :as old-component] (find-component state entity tag)
        new-val (fn old-val)
        new-component (new-component t new-val)]
    (-> state
        (remove-component entity tag)
        (add-component entity new-component))))
