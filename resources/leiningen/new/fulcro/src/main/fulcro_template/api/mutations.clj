(ns {{name}}.api.mutations
  (:require
    [taoensso.timbre :as timbre]
    [fulcro.server :as core :refer [defmutation]]))


(defmutation ping
  "Server mutation for ping, which just prints incoming parameters to the server log."
  [params]
  (action [env]
    (timbre/info "PING with parameters: " params)))
