(ns core
  (:require [quil.core :as q :include-macros true]
            [dynamic])
  (:gen-class))

(q/defsketch example
  :title "Sketch"
  :setup dynamic/setup
  :setttings dynamic/settings
  :draw dynamic/draw
  :size [1500 1500])

(defn refresh
  []
  (use :reload 'dynamic)
  (.loop example))
