(ns core
  (:require [quil.core :as q :include-macros true]
            [dynamic])
  (:gen-class))

(q/defsketch example
  :title "Sketch"
  :setup dynamic/setup
  :draw dynamic/draw
  :size [800 800])

(defn refresh
  []
  (use :reload 'dynamic)
  (.loop example))
