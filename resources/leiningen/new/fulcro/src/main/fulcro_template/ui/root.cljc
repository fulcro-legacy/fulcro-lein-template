(ns fulcro-template.ui.root
  (:require
    [fulcro.client.core :as fc]
    [fulcro.client.mutations :as m]
    [fulcro.client.logging :as log]
    [fulcro.client.data-fetch :as df]
    translations.es                                         ; preload translations by requiring their namespace. See Makefile for extraction/generation
    [om.dom :as dom]
    [fulcro-template.api.mutations :as api]
    [om.next :as om :refer [defui]]
    [fulcro.i18n :refer [tr trf]]))

(defui ^:once Main
  static fc/InitialAppState
  (initial-state [c p] {:main/meaning-of-life "unknown" :ui/ping-number 0})
  static om/Ident
  (ident [this props] [:ui-components/by-id :main])
  static om/IQuery
  (query [this] [:ui/ping-number :main/meaning-of-life])
  Object
  (render [this]
    (let [{:keys [ui/ping-number main/meaning-of-life]} (om/props this)
          fetch-state (get meaning-of-life :ui/fetch-state nil)
          meaning     (cond
                        fetch-state (tr "(Deep Thought Hums)")
                        (or (string? meaning-of-life) (pos? meaning-of-life)) meaning-of-life
                        :otherwise "")]
      (dom/div nil
        (dom/button #js {:onClick (fn [e]
                                    (m/set-integer! this :ui/ping-number :value (inc ping-number))
                                    (om/transact! this `[(api/ping {:x ~ping-number})]))} (tr "Ping the server!"))
        (dom/span nil
          (trf "The meaning of life is {meaning}." :meaning meaning))
        (dom/button #js {:onClick (fn [e]
                                    (df/load this :meaning-of-life nil
                                      {:target [:ui-components/by-id :main :main/meaning-of-life]}))} (tr "Ask Deep Thought server about the meaning of life."))))))

(def ui-main (om/factory Main))

(defui ^:once LocaleSelector
  static fc/InitialAppState
  (initial-state [c p] {:available-locales {"en" "English" "es" "Spanish"}})
  static om/Ident
  (ident [this props] [:ui-components/by-id :locale-selector])
  static om/IQuery
  ; the weird-looking query here pulls data from the root node (where the current locale is stored) with a "link" query
  (query [this] [[:ui/locale '_] :available-locales])
  Object
  (render [this]
    (let [{:keys [ui/locale available-locales]} (om/props this)]
      (dom/div nil "Locale:" (map (fn [[k v]]
                                    (dom/a #js {:onClick #(om/transact! this `[(m/change-locale {:lang ~k})])} v)) available-locales)))))

(def ui-locale (om/factory LocaleSelector))

(defui ^:once Root
  static fc/InitialAppState
  (initial-state [c p] {:ui/main (fc/get-initial-state Main nil) :ui/locale-selector (fc/get-initial-state LocaleSelector {})})
  static om/IQuery
  (query [this] [:ui/locale :ui/react-key {:ui/main (om/get-query Main)} {:ui/locale-selector (om/get-query LocaleSelector)}])
  Object
  (render [this]
    (let [{:keys [ui/react-key ui/main ui/locale-selector] :or {react-key "ROOT"}} (om/props this)]
      (dom/div #js {:key react-key}
        (ui-locale locale-selector)
        (ui-main main)))))
