(ns kepler.ecs)


;; Entities

(defn new-entity
  "Generates a new entity"
  []
  (java.util.UUID/randomUUID))


;; Components

(defn build-component
  [type data]
  {:type type :data data})

(defn new-component-record
  "Creates a new component record"
  [entity {:keys [type data]}]
  {:entity entity :type type :data data})


;; Assemblies

(defn new-assembly
  "Returns a list of components assigned to the same entity id"
  [entity & components]
  {:entity entity
   :components (map
                (partial new-component-record entity)
                components)})


;; Systems

(defn add-component
  "Adds a component to the list of components"
  [components component]
  (conj components component))

(defn remove-component
  "Removes any instances of a component from the list of components"
  [components entity type]
   (filterv (fn [{:keys [e t]}]
              (and (= entity e) (= type t))) components))

(defn update-component
  "Updates a component record in place. Useful for update component values."
  [components {:keys [entity type] :as component}]
  (-> components
      (remove-component entity type)
      (add-component component)))

(defn fetch-components
  ""
  [components type]
  (filterv #(= (:type %) type) components))

(defn remove-entity
  "Removes all records for an entity"
  [c entity]
  (filterv (complement #(= (:entity %) entity)) c))

(defn add-assembly
  "Adds an assembly to the list of component records. Doesn't check for existing "
  [c {:keys [entity components] :as assembly}]
  (vec (-> c
           (remove-entity entity)
           (concat components))))
