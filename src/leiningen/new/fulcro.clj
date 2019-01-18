(ns leiningen.new.fulcro
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files sanitize raw-resourcer]]
            [leiningen.core.main :as main]
            [clojure.set :as set]
            [clojure.string :as str]))

(def render (renderer "fulcro"))
(def raw (raw-resourcer "fulcro"))

(defn paths [render data]
  [[".gitignore" (render "gitignore" data)]
   ["karma.conf.js" (render "karma.conf.js" data)]
   ["Makefile" (render "Makefile" data)]
   ["deps.edn" (render "deps.edn" data)]
   ["tests.edn" (render "tests.edn" data)]
   ["package.json" (render "package.json" data)]
   ["project.clj" (render "project.clj" data)]
   ["README.adoc" (render "README.adoc" data)]
   ["shadow-cljs.edn" (render "shadow-cljs.edn" data)]

   ["resources/public/workspaces/index.html" (render "resources/public/workspaces/index.html" data)]
   ["resources/public/favicon.ico" (raw "resources/public/favicon.ico")]

   ["src/workspaces/{{sanitized}}/demo_ws.cljs" (render "src/workspaces/app/demo_ws.cljs" data)]

   ["src/dev/user.clj" (render "src/dev/user.clj" data)]

   ["src/main/config/defaults.edn" (render "src/main/config/defaults.edn" data)]
   ["src/main/config/dev.edn" (render "src/main/config/dev.edn" data)]
   ["src/main/config/prod.edn" (render "src/main/config/prod.edn" data)]

   ["src/main/{{sanitized}}/server_components/config.clj" (render "src/main/app/server_components/config.clj" data)]
   ["src/main/{{sanitized}}/server_components/http_server.clj" (render "src/main/app/server_components/http_server.clj" data)]
   ["src/main/{{sanitized}}/server_components/middleware.clj" (render "src/main/app/server_components/middleware.clj" data)]
   ["src/main/{{sanitized}}/server_components/pathom.clj" (render "src/main/app/server_components/pathom.clj" data)]
   ["src/main/{{sanitized}}/server_components/pathom_wrappers.clj" (render "src/main/app/server_components/pathom_wrappers.clj" data)]
   ["src/main/{{sanitized}}/model/user.clj" (render "src/main/app/model/user.clj" data)]
   ["src/main/{{sanitized}}/model/user.cljs" (render "src/main/app/model/user.cljs" data)]

   ["src/main/{{sanitized}}/client.cljs" (render "src/main/app/client.cljs" data)]
   ["src/main/{{sanitized}}/development_preload.cljs" (render "src/main/app/development_preload.cljs" data)]
   ["src/main/{{sanitized}}/server_main.clj" (render "src/main/app/server_main.clj" data)]

   ["src/main/{{sanitized}}/ui/components.cljs" (render "src/main/app/ui/components.cljs" data)]
   ["src/main/{{sanitized}}/ui/root.cljs" (render "src/main/app/ui/root.cljs" data)]

   ["src/test/{{sanitized}}/sample_test.cljc" (render "src/test/app/sample_test.cljc" data)]])

(defn fulcro
  "Generates a simple Fulcro template project"
  [name & add-ons]
  (let [data     {:name      name
                  :js-name   (sanitize name)
                  :sanitized (name-to-path name)}
        files    (paths render data)]
    (main/info "Generating Fulcro project.")
    (apply ->files data files)))




