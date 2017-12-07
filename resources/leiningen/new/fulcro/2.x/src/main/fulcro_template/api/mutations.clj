(ns {{name}}.api.mutations
  (:require
    [taoensso.timbre :as timbre]
    [fulcro.server :refer [defmutation]]))

(defmutation ping
  "Server mutation for ping, which just prints incoming parameters to the server log."
  [params]
  (action [env]
    (timbre/info "Deep Thought giggles at your simple parameters: " params)))
