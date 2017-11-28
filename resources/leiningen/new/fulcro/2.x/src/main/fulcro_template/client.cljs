(ns {{name}}.client
  (:require [fulcro.client.core :as fc]))

(defonce app (atom (fc/new-fulcro-client)))
