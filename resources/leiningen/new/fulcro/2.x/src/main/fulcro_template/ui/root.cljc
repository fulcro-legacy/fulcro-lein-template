(ns {{name}}.ui.root
  (:require
    [fulcro.client.core :as fc]
    [fulcro.client.mutations :as m]
    [fulcro.client.logging :as log]
    [fulcro.client.data-fetch :as df]
    translations.es                                         ; preload translations by requiring their namespace. See Makefile for extraction/generation
    [fulcro.client.dom :as dom]
    [{{name}}.api.mutations :as api]
    [fulcro.client.primitives :as prim :refer [defui]]
    [fulcro.i18n :refer [tr trf]]))

(defui ^:once Main
  static fc/InitialAppState
  (initial-state [c p] {:main/meaning-of-life "unknown" :ui/ping-number 0})
  static prim/Ident
  (ident [this props] [:ui-components/by-id :main])
  static prim/IQuery
  (query [this] [:ui/ping-number :main/meaning-of-life])
  Object
  (render [this]
    (let [{:keys [ui/ping-number main/meaning-of-life]} (prim/props this)
          fetch-state (get meaning-of-life :ui/fetch-state nil)
          meaning     (cond
                        fetch-state (tr "(Deep Thought Hums)")
                        (or (string? meaning-of-life) (pos? meaning-of-life)) meaning-of-life
                        :otherwise "")]
      (dom/div nil
        (dom/button #js {:onClick (fn [e]
                                    (m/set-integer! this :ui/ping-number :value (inc ping-number))
                                    (prim/transact! this `[(api/ping {:x ~ping-number})]))} (tr "Ping the server!"))
        (dom/span nil
          (trf "The meaning of life is {meaning}." :meaning meaning))
        (dom/button #js {:onClick (fn [e]
                                    (df/load this :meaning-of-life nil
                                      {:target [:ui-components/by-id :main :main/meaning-of-life]}))} (tr "Ask Deep Thought server about the meaning of life."))))))

(def ui-main (prim/factory Main))

(defui ^:once LocaleSelector
  static fc/InitialAppState
  (initial-state [c p] {:available-locales {"en" "English" "es" "Spanish"}})
  static prim/Ident
  (ident [this props] [:ui-components/by-id :locale-selector])
  static prim/IQuery
  ; the weird-looking query here pulls data from the root node (where the current locale is stored) with a "link" query
  (query [this] [[:ui/locale '_] :available-locales])
  Object
  (render [this]
    (let [{:keys [ui/locale available-locales]} (prim/props this)]
      (dom/div nil "Locale:" (map (fn [[k v]]
                                    (dom/a #js {:href    "#"
                                                :style   #js {:padding-right "5px"}
                                                :onClick #(prim/transact! this `[(m/change-locale {:lang ~k})])} v)) available-locales)))))

(def ui-locale (prim/factory LocaleSelector))

(defui ^:once Root
  static fc/InitialAppState
  (initial-state [c p] {:ui/main (fc/get-initial-state Main nil) :ui/locale-selector (fc/get-initial-state LocaleSelector {})})
  static prim/IQuery
  (query [this] [:ui/locale :ui/react-key {:ui/main (prim/get-query Main)} {:ui/locale-selector (prim/get-query LocaleSelector)}])
  Object
  (render [this]
    (let [{:keys [ui/react-key ui/main ui/locale-selector] :or {react-key "ROOT"}} (prim/props this)]
      (dom/div #js {:key react-key}
        (ui-locale locale-selector)
        (ui-main main)))))
