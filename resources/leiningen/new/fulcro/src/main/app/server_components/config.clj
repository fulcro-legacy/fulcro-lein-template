(ns app.server-components.config
  (:require
    [mount.core :refer [defstate args]]
    [fulcro.server :as server]
    [taoensso.timbre :as log]))


(defn configure-logging! [config]
  (let [{:keys [taoensso.timbre/logging-config]} config]
    (fulcro.logging/set-logger!
      (fn [{:keys [file line]} level & args]
        (log/log! level :p [args] {:?ns-str file :?line line})))
    (log/info "Configuring Timbre with " logging-config)
    (log/merge-config! logging-config)))


(defstate config
  :start (let [{:keys [config] :or {config "config/dev.edn"}} (args)
               configuration (server/load-config {:config-path config})]
           (log/info "Loaded config" config)
           (configure-logging! configuration)
           configuration))

