(ns kepler.entity)

(defn new-entity
  "Generates a new entity"
  []
  (java.util.UUID/randomUUID))
