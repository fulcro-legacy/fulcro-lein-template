(ns {{name}}.ui.root
  (:require
    [fulcro.client.mutations :as m]
    [fulcro.client.data-fetch :as df]
    translations.es                                         ; preload translations by requiring their namespace. See Makefile for extraction/generation
    [fulcro.client.dom :as dom]
    [{{name}}.api.mutations :as api]
    [fulcro.client.primitives :as prim :refer [defsc]]
    [fulcro.i18n :refer [tr trf]]))

;; The main UI of your application

{{#demo?}}
(defn meaning-render [component load-markers which-meaning known-meaning]
  (let [load-marker (get load-markers which-meaning)
        meaning     (cond
                      load-marker (tr "(Deep Thought Hums...)")
                      (or (string? known-meaning) (pos? known-meaning)) known-meaning
                      :otherwise "")]
    (dom/div nil
      (dom/span nil (trf "The meaning of {what} is {meaning}." :what (name which-meaning) :meaning meaning))
      (dom/button #js {:onClick (fn [e]
                                  (df/load-field component which-meaning
                                    ; put the load marker in the df/marker-table at a key like :life
                                    :marker which-meaning))} (tr "Ask Deep Thought.")))))

(defsc Meaning [this {:keys [ui/ping-number life universe everything] :as props}]
  {:initial-state {:life "unknown" :universe "unknown" :everything "unknown" :ui/ping-number 0}
   :ident         (fn [] [:meaning/by-id :truth])
   :query         [:ui/ping-number :life :universe :everything [df/marker-table '_]]}
  (let [marker-table (get props df/marker-table)]
    (dom/div nil
      (dom/div nil
        (dom/button #js {:onClick (fn [e]
                                    (m/set-integer! this :ui/ping-number :value (inc ping-number))
                                    (prim/transact! this `[(api/ping {:x ~ping-number})]))} (tr "Ping the server!")))
      (meaning-render this marker-table :life life)
      (meaning-render this marker-table :universe universe)
      (meaning-render this marker-table :everything everything))))

(def ui-meaning (prim/factory Meaning))

(defsc LocaleSelector [this {:keys [ui/locale available-locales]}]
  {:initial-state (fn [p] {:available-locales {"en" "English" "es" "Spanish"}})
   :ident         (fn [] [:ui-components/by-id :locale-selector])
   ; the weird-looking query here pulls data from the root node (where the current locale is stored) with a "link" query
   :query         [[:ui/locale '_] :available-locales]}
  (dom/div nil "Locale:" (map-indexed (fn [index [k v]]
                                        (dom/a #js {:href    "#"
                                                    :key     index
                                                    :style   #js {:paddingRight "5px"}
                                                    :onClick #(prim/transact! this `[(m/change-locale {:lang ~k})])} v)) available-locales)))

(def ui-locale (prim/factory LocaleSelector))

(defsc Root [this {:keys [root/meaning ui/locale-selector] :or {react-key "ROOT"}}]
  {:initial-state (fn [p] {:root/meaning       (prim/get-initial-state Meaning nil)
                           :ui/locale-selector (prim/get-initial-state LocaleSelector {})})
   :query         [:ui/locale
                   {:root/meaning (prim/get-query Meaning)}
                   {:ui/locale-selector (prim/get-query LocaleSelector)}]}
  (dom/div nil
    (ui-locale locale-selector)
    (ui-meaning meaning)))
{{/demo?}}
{{#nodemo?}}
(defsc Root [this props]
  (dom/div nil "TODO"))
{{/nodemo?}}
