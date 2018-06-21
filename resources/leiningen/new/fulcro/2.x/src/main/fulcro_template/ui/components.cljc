(ns {{name}}.ui.components
  (:require
    [fulcro.client.primitives :as prim :refer [defsc]]
    #?(:cljs [fulcro.client.dom :as dom] :clj [fulcro.client.dom-server :as dom])))

;; A good place to put reusable components
(defsc PlaceholderImage [this {:keys [w h label]}]
  (let [label (or label (str w "x" h))]
    (dom/svg {:width w :height h}
      (dom/rect {:width w :height h :style {:fill        "rgb(200,200,200)"
                                            :strokeWidth 2
                                            :stroke      "black"}})
      (dom/text {:textAnchor "middle" :x (/ w 2) :y (/ h 2)} label))))

(def ui-placeholder (prim/factory PlaceholderImage))
