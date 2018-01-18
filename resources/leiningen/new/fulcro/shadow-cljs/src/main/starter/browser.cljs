(ns starter.browser
  (:require [{{name}}.client :as client]
            [fulcro.client.logging :as log]))


(defn start []
  (log/set-level :all)
  (client/mount))

(defn ^:export init []
  (client/mount))

(client/mount)
