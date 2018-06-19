(ns {{name}}.client
  (:require [fulcro.client :as fc]
            [fulcro.i18n :as i18n]))

(defn message-format [{:keys [::i18n/localized-format-string ::i18n/locale ::i18n/format-options]}]
  (let [locale-str (name locale)
        ; comes from js file included in HTML. Use shadow-cljs instead of figwheel to make this cleaner
        formatter  (js/IntlMessageFormat. localized-format-string locale-str)]
    (.format formatter (clj->js format-options))))

(defonce app (atom (fc/new-fulcro-client
                     :reconciler-options {:shared    {::i18n/message-formatter message-format}
                                          :render-mode :keyframe ; Good for beginners. Remove to optimize UI refresh
                                          :shared-fn ::i18n/current-locale})))
