(ns {{name}}.ui.root
  (:require
    [fulcro.client.mutations :as m]
    [fulcro.client.data-fetch :as df]
    #?(:cljs [fulcro.client.dom :as dom] :clj [fulcro.client.dom-server :as dom])
    [{{name}}.api.mutations :as api]
    [fulcro.client.primitives :as prim :refer [defsc]]
    [fulcro.i18n :as i18n :refer [tr trf]]))

;; The main UI of your application

{{#demo?}}
(defn meaning-render [component load-markers which-meaning known-meaning]
  (let [load-marker (get load-markers which-meaning)
        meaning     (cond
                      load-marker (tr "(Deep Thought Hums...)")
                      (or (string? known-meaning) (pos? known-meaning)) known-meaning
                      :otherwise "")]
    (dom/div
      (dom/span (trf "The meaning of {what} is {meaning}." :what (name which-meaning) :meaning meaning))
      (dom/button {:onClick (fn [e]
                              (df/load-field component which-meaning
                                ; put the load marker in the df/marker-table at a key like :life
                                :marker which-meaning))} (tr "Ask Deep Thought.")))))

(defsc Meaning [this {:keys [ui/ping-number life universe everything] :as props}]
  {:initial-state {:life "unknown" :universe "unknown" :everything "unknown" :ui/ping-number 0}
   :ident         (fn [] [:meaning/by-id :truth])
   :query         [:ui/ping-number :life :universe :everything [df/marker-table '_]]}
  (let [marker-table (get props df/marker-table)]
    (dom/div
      (dom/div
        (dom/button {:onClick (fn [e]
                                (m/set-integer! this :ui/ping-number :value (inc ping-number))
                                (prim/transact! this `[(api/ping {:x ~ping-number})]))} (tr "Ping the server!")))
      (meaning-render this marker-table :life life)
      (meaning-render this marker-table :universe universe)
      (meaning-render this marker-table :everything everything))))

(def ui-meaning (prim/factory Meaning))

(defsc Root [this {:keys [root/meaning ui/locale-selector]}]
  {:initial-state {:root/meaning         {}
                   ::i18n/current-locale {:locale :en :name "English"}
                   :ui/locale-selector   {:locales [{:locale :en :name "English" :translations {}} {:locale :es :name "Espanol"}]}}
   :query         [{::i18n/current-locale (prim/get-query i18n/Locale)}
                   {:ui/locale-selector (prim/get-query i18n/LocaleSelector)}
                   {:root/meaning (prim/get-query Meaning)}]}
  (dom/div
    (i18n/ui-locale-selector locale-selector)
    (ui-meaning meaning)))
{{/demo?}}
{{#nodemo?}}
(defsc Root [this props]
  (dom/div "TODO"))
{{/nodemo?}}
