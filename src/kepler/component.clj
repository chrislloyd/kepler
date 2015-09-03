(ns kepler.component)

(defrecord Component [entity
                      type
                      val])

;;; Keyword -> Any -> Entity -> Component
(defn component [type val entity]
  (Component. entity type val))

;;; Fn -> Component -> Component
(defn update-component-val [fn component]
  (update component :val fn))


;;; Entity -> Keyword -> Component -> Boolean
(defn same-component? [entity type component]
  (and
   (= entity (:entity component))
   (= type (:type component))))

;;; State -> Entity -> Keyword -> Component
(defn get-component
  [state entity type]
  (first (filter (partial same-component? entity type) state)))

;;; State -> Entity -> Keyword -> State
(defn remove-component
  "Removes any instances of a component from the list of components"
  [state entity type]
  (filter (complement (partial same-component? entity type)) state))


