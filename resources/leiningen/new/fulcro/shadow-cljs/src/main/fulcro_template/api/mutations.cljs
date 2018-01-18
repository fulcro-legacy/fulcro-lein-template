(ns fulcro-template.api.mutations
  (:require
    [fulcro.client.mutations :refer [defmutation]]
    [fulcro.client.logging :as log]))

;; Place your client mutations here

(defmutation ping
  "A full-stack mutation for pinging the server. Also shows the ping in the browser log."
  [params]
  (action [env]
    (log/info "Ping!"))
  (remote [env] true))
