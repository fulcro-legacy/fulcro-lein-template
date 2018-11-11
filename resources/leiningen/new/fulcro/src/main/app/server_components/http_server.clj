(ns {{name}}.server-components.http-server
  (:require
    [{{name}}.server-components.config :refer [config]]
    [{{name}}.server-components.middleware :refer [middleware]]
    [mount.core :refer [defstate]]
    [org.httpkit.server :as http-kit]))

(defstate http-server
  :start (http-kit/run-server middleware (:http-kit config))
  :stop (http-server))
