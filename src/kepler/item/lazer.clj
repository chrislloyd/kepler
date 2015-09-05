(ns kepler.item.lazer
  (:require [kepler.item :refer [Item]]))


;; def attack_from(attacker)
;;     damage = 0
;;     actors.select {|a| attacker.can_attack?(a)}.each do |victim|
;;       victim.hurt(attacker.damage)
;;       damage += attacker.damage
;;     end
;;     damage
;;   end

;; def distance(x,y)
;;   Math.hypot(x-self.x, y-self.y)
;; end

;; def distance_to(actor)
;;   distance(actor.x, actor.y) rescue 0
;; end

;; def direction_to(actor)
;;   (Math.atan2(x - actor.x, y - actor.y).to_deg + 180) % 360 rescue 0
;; end


(defn- in-cone? ())

(defn- can-attack? [attacker victim]
  ;; self != victim && !victim.dead? && in_cone?(victim, 2, range)
  false)

;; def in_cone?(obj, alpha, r)
;;     (direction_to(obj)-dir).abs < alpha && distance_to(obj) <= r
;;   end


(deftype Lazer [energy damage]
  Item
  (use-item [this entity state]
            (do
              (prn "MAJOR LAZER!")
              state)))

(defn new-lazer [energy damage]
  (Lazer. energy damage))
