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

(defn by-entity [entity]
  (filter (fn [component] (= (:entity component) entity))))

(defn by-component [type]
  (filter (fn [component] (= (:type component) type))))

(defn by-component-entity [entity type]
  (comp (by-entity entity) (by-component type)))


;;; State -> Entity -> Keyword -> Component
(defn get-component
  [state entity type]
  (first (eduction (by-component-entity entity type) state)))

;;; State -> Entity -> Keyword -> State
(defn remove-component
  "Removes any instances of a component from the list of components"
  [state entity type]
  (filter #(not (and (= (:entity %) entity) (= (:type %) type))) state))

