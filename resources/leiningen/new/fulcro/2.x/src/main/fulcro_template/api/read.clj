(ns {{name}}.api.read
  (:require
    [fulcro.server :refer [defquery-entity defquery-root]]
    [fulcro.i18n :as i18n]
    [taoensso.timbre :as timbre]))

;; Server queries can go here

{{#demo?}}
(defquery-entity :meaning/by-id
  "Returns the meaning of life."
  (value [{:keys [query]} id params]
    (let [meanings {:life       42
                    :universe   42
                    :everything 42}]
      (timbre/info "Thinking about the meaning of " query "...hmmm...")
      (Thread/sleep 3000)
      (select-keys meanings query))))

 ; locale serving from PO files
(defquery-root ::i18n/translations
  (value [env {:keys [locale]}]
    (if-let [translations (i18n/load-locale "i18n" locale)]
      translations
      nil)))
{{/demo?}}
