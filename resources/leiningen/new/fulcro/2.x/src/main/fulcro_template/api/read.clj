(ns {{name}}.api.read
  (:require
    [fulcro.server :refer [defquery-entity]]
    [taoensso.timbre :as timbre]))

(defquery-entity :meaning/by-id
  "Returns the meaning of life."
  (value [{:keys [query]} id params]
    (let [meanings {:life       42
                    :universe   42
                    :everything 42}]
      (timbre/info "Thinking about the meaning of " query "...hmmm...")
      (Thread/sleep 3000)
      (select-keys meanings query))))
