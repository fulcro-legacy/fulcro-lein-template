(ns {{name}}.workspaces
  (:require
    [nubank.workspaces.core :as ws]
    [{{name}}.demo-ws]))

(defonce init (ws/mount))
