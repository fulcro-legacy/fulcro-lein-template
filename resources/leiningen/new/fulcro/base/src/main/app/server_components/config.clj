(ns app.server-components.config
  (:require
    [mount.core :refer [defstate args]]
    [fulcro.server :as server]
    [taoensso.timbre :as log]))

(defstate config
  :start (let [{:keys [config] :or {config "config/dev.edn"}} (args)]
           (log/info "Loading config" config)
           (server/load-config {:config-path config})))

