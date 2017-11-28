(ns {{name}}.api.mutations
  (:require
    [fulcro.client.mutations :refer [defmutation]]
    [fulcro.client.logging :as log]))

(defmutation ping
  "A full-stack mutation for pinging the server. Also shows the ping in the browser log."
  [params]
  (action [env]
    (log/info "Ping!"))
  (remote [env] true))

