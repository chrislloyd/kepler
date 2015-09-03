(ns kepler.systems.debug)

(defn debug-system [state action]
  (do
    (prn state)
    state))
