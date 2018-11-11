(ns app.workspaces
  (:require
    [nubank.workspaces.core :as ws]
    [app.demo-ws]))

(defonce init (ws/mount))
