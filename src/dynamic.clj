(ns dynamic
  (:require [clojure.pprint :as pp]
            [quil.core :as q]))

(def colours
  
  ; Use saturation and brightness = 90.
  {:yellow-green-brown {:bg-hue-low 60
                        :bg-hue-high 90
                        :shape-fill 30}
   
  ; Use saturation = 6, brightness = 100.
   :floral-white {:bg-hue-low 160
                  :bg-hue-high 280
                  :shape-fill 40}
   
   ; Use saturation = 32, brightness = 100.
   :navajo-white {:bg-hue-low 276
                  :bg-hue-high 156
                  :shape-fill 36}
   
   ; Use saturation = 2, brightness = 100.
   :snow {:bg-hue-low 0
          :bg-hue-high 120
          :shape-fill 240}})

(defn setup
  []
  (q/color-mode :hsb 360 100 100 1.0))

(defn settings
  []
  (q/smooth 8))

(defn paint-gradient
  [{hue-low :bg-hue-low hue-high :bg-hue-high}]
  (q/no-stroke)
  (doseq [y (range 0 (q/width) 5)]
    (q/fill (q/map-range y 0 (q/width) hue-low hue-high)
            6
            100)
    (q/rect 0 y (q/width) 5)))

(defn paint-shape
  [{hue :shape-fill} origin-x origin-y]
  (q/stroke hue 0 5) ; Mute the hue for the shape's outline.
  (q/no-fill) ; And actually, don't fill it.
  (q/begin-shape)
  (q/vertex origin-x origin-y)
  (dotimes [_ 4]
    (q/bezier-vertex (rand-int (q/width)) (rand-int (q/height))
                     (rand-int (q/width)) (rand-int (q/height))
                     (rand-int (q/width)) (rand-int (q/height))))
  (q/bezier-vertex (rand-int (q/width)) (rand-int (q/height))
                   (rand-int (q/width)) (rand-int (q/height))
                   origin-x origin-y)
  (q/end-shape))

(defn paint-shapes
  [hue]
  (let [origin-x (rand-int (q/width))
        origin-y (rand-int (q/height))]
    (dotimes [_ 3]
      (paint-shape hue origin-x origin-y))))

(defn- save-to-disk
  []
  (q/save (pp/cl-format nil
                        "sketches/~d-~2,'0d-~2,'0d-~2,'0d-~2,'0d-~2,'0d.jpeg"
                        (q/year) (q/month) (q/day) (q/hour) (q/minute) (q/seconds))))

(defn draw
  []
  (q/no-loop)
  (let [colour (:floral-white colours)]
    (paint-gradient colour)
    (paint-shapes colour))
  (save-to-disk))
