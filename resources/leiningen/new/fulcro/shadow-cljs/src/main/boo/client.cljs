(ns boo.client
  (:require [fulcro.client :as fc]
            [boo.ui.root :as root]))

(defonce app (atom (fc/new-fulcro-client)))

(defn mount [] (reset! app (fc/mount @app root/Root "app")))
