(ns {{name}}.api.read
  (:require
    [fulcro.server :refer [defquery-entity defquery-root]]
    [taoensso.timbre :as timbre]))

(defquery-root :meaning-of-life
  "Returns the meaning of life."
  (value [env params]
    (timbre/info "Thinking about the meaning...hmmm...")
    (Thread/sleep 3000)
    42))
