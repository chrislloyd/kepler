(ns kepler.systems.coordinate
  "The coordinate system makes sure that x & y positions are wrapped around the circumferences of the mass (planet or asteroid). The mass' radius (in meters) can be configured.

  I should note a slight scientific inaccuracy. A defining feature of a planet is that it is spherical. Depending on it's mass, typically celestial bodies less than 200km in radius don't have enough self gravity to form a sphere. This is the bodies' 'Potato Radius'[1]. The coordinate system treats all celestial bodies as spherical, even if they have tiny radii. Lucky, I guess.

  The system also approximates some math so that the coordinates can be Integers.

  [1] http://arxiv.org/ftp/arxiv/papers/1004/1004.1091.pdf"
  (:require [kepler.component :refer [update-component-val]]
            [kepler.component.position :refer [wrap]]))

(def lim 50)

(defn- wrap-position-component [component]
  (if (= (:type component) :pos)
    (update-component-val (partial wrap lim) component)
    component))

(defn coordinate-system [state action]
  (if (= (:type action) :tick)
    (map wrap-position-component state)
    state))
