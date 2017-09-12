(ns cljs.user
  (:require
    [fulcro.client.core :as fc]
    [om.next :as om]

    [fulcro-template.client :as core]
    [fulcro-template.ui.root :as root]

    [cljs.pprint :refer [pprint]]
    [fulcro.client.logging :as log]))

(enable-console-print!)

(log/set-level :all)

(defn mount []
  (reset! core/app (fc/mount @core/app root/Root "app")))

(mount)

(defn app-state [] @(:reconciler @core/app))

(defn log-app-state [& keywords]
  (pprint (let [app-state (app-state)]
            (if (= 0 (count keywords))
              app-state
              (select-keys app-state keywords)))))
