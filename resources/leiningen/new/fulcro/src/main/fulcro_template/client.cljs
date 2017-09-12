(ns fulcro-template.client
  (:require [om.next :as om]
            [fulcro.client.core :as fc]))

(defonce app (atom (fc/new-fulcro-client)))
