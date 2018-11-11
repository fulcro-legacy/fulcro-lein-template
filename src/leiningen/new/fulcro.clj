(ns leiningen.new.fulcro
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files sanitize]]
            [leiningen.core.main :as main]
            [clojure.set :as set]
            [clojure.string :as str]))

(def render (renderer "fulcro"))

(defn paths [render data]
  [[".gitignore" (render "gitignore" data)]
   ["karma.conf.js" (render "karma.conf.js" data)]
   ["Makefile" (render "Makefile" data)]
   ["package.json" (render "package.json" data)]
   ["project.clj" (render "project.clj" data)]
   ["README.adoc" (render "README.adoc" data)]
   ["shadow-cljs.edn" (render "shadow-cljs.edn" data)]

   ["resources/public/workspaces.html" (render "resources/public/workspaces.html" data)]
   ["resources/public/js/test/index.html" (render "resources/public/js/test/index.html" data)]

   ["src/workspaces/{{sanitized}}/workspaces.cljs" (render "src/workspaces/app/workspaces.cljs" data)]
   ["src/workspaces/{{sanitized}}/demo_ws.cljs" (render "src/workspaces/app/demo_ws.cljs" data)]

   ["src/dev/user.clj" (render "src/dev/user.clj" data)]
   ["src/main/config/defaults.edn" (render "src/main/config/defaults.edn" data)]
   ["src/main/config/dev.edn" (render "src/main/config/dev.edn" data)]
   ["src/main/config/prod.edn" (render "src/main/config/prod.edn" data)]
   ["src/main/{{sanitized}}/api/mutations.clj" (render "src/main/app/api/mutations.clj" data)]
   ["src/main/{{sanitized}}/api/mutations.cljs" (render "src/main/app/api/mutations.cljs" data)]
   ["src/main/{{sanitized}}/api/read.clj" (render "src/main/app/api/read.clj" data)]
   ["src/main/{{sanitized}}/client.cljs" (render "src/main/app/client.cljs" data)]
   ["src/main/{{sanitized}}/development_preload.cljs" (render "src/main/app/development_preload.cljs" data)]
   ["src/main/{{sanitized}}/server.clj" (render "src/main/app/server.clj" data)]
   ["src/main/{{sanitized}}/server_main.clj" (render "src/main/app/server_main.clj" data)]
   ["src/main/{{sanitized}}/ui/components.cljc" (render "src/main/app/ui/components.cljc" data)]
   ["src/main/{{sanitized}}/ui/root.cljc" (render "src/main/app/ui/root.cljc" data)]
   ["src/test/{{sanitized}}/client_test_main.cljs" (render "src/test/app/client_test_main.cljs" data)]
   ["src/test/{{sanitized}}/sample_spec.cljc" (render "src/test/app/sample_spec.cljc" data)]])

(defn fulcro
  "Generates a simple Fulcro template project"
  [name & add-ons]
  (let [data     {:name      name
                  :js-name   (sanitize name)
                  :sanitized (name-to-path name)}
        base-dir "base/"
        render   (fn [filename data] (render (str base-dir filename) data))
        files    (paths render data)]
    (main/info "Generating Fulcro project.")
    (apply ->files data files)))




