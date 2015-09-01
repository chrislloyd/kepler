(ns kepler.systems.command)

(defn command-system [state action]
  (if (= (:type action) :cmd)
    (case (get-in action [:cmd :cmd]))
    state))
