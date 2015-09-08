(ns kepler.pt)

(defn pt [x y]
  {:x x :y y})

(defn distance [a b]
  (Math/sqrt (+ (Math/pow (- (:x b) (:x a)) 2)
                (Math/pow (- (:y b) (:y a)) 2))))

(defn- rad->deg [d]
  (* (/ 180 Math/PI) d))

(defn angle [a b]
  (mod (+ (rad->deg (Math/atan2 (- (:x a) (:x b))
                                (- (:y a) (:y b))))
          180)
       360))
