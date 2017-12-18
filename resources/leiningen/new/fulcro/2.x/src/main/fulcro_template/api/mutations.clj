(ns {{name}}.api.mutations
  (:require
    [taoensso.timbre :as timbre]
    [fulcro.server :refer [defmutation]]))

;; Place your server mutations here

{{#demo?}}
(defmutation ping
  "Server mutation for ping, which just prints incoming parameters to the server log."
  [params]
  (action [env]
    (timbre/info "Deep Thought giggles at your simple parameters: " params)))
{{/demo?}}
