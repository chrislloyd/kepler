(ns kepler.actions)

(defmacro defaction
  [name args body]
  `(defn ~name ~args (assoc ~body :type ~(keyword name))))

; --

(defaction tick []
  {:tick (java.util.UUID/randomUUID)})

; --

(defaction add-bot [entity chan]
  {:entity entity
   :chan chan})

(defaction remove-bot [entity]
  {:entity entity})

(defaction invalid-cmd [entity cmd]
  {:entity entity
   :cmd cmd})

; --

(defaction repair [entity]
  {:entity entity})

(defaction move [entity dir]
  {:entity entity
   :dir dir})

(defaction turn [entity dr]
  {:entity entity
   :dr dr})

(defaction shoot [entity]
  {:entity entity})

(defaction rename [entity new-name]
  {:entity entity
   :name new-name})
