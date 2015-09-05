(ns kepler.item)

(defprotocol Item
  (use-item [this entity state]))

