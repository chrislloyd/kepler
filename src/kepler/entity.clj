(ns kepler.entity)

(defn new-entity
  "Generates a new entity"
  []
  (str (java.util.UUID/randomUUID)))
