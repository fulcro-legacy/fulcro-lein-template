(ns leiningen.new.fulcro
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "fulcro"))

(defn fulcro
  "Generates a simple Fulcro template project"
  [name]
  (let [data {:name      name
              :sanitized (name-to-path name)}]
    (main/info "Generating Fulcro project.")
    (->files data
      ["src/{{sanitized}}/foo.clj" (render "foo.clj" data)]
      [".gitignore" (render "gitignore" data)]
      ["i18n/es.po" (render "i18n/es.po" data)]
      ["i18n/messages.pot" (render "i18n/messages.pot" data)]
      ["Makefile" (render "Makefile" data)]
      ["package.json" (render "package.json" data)]
      ["project.clj" (render "project.clj" data)]
      ["README.md" (render "README.md" data)]
      ["resources/public/cards.html" (render "resources/public/cards.html" data)]
      ["resources/public/index.html" (render "resources/public/index.html" data)]
      ["script/figwheel.clj" (render "script/figwheel.clj" data)]
      ["src/cards/{{sanitized}}/cards.cljs" (render "src/cards/fulcro_template/cards.cljs" data)]
      ["src/cards/{{sanitized}}/intro.cljs" (render "src/cards/fulcro_template/intro.cljs" data)]
      ["src/dev/cljs" (render "src/dev/cljs" data)]
      ["src/dev/cljs/user.cljs" (render "src/dev/cljs/user.cljs" data)]
      ["src/dev/user.clj" (render "src/dev/user.clj" data)]
      ["src/main/config" (render "src/main/config" data)]
      ["src/main/config/defaults.edn" (render "src/main/config/defaults.edn" data)]
      ["src/main/config/dev.edn" (render "src/main/config/dev.edn" data)]
      ["src/main/config/prod.edn" (render "src/main/config/prod.edn" data)]
      ["src/main/{{sanitized}}/api/mutations.clj" (render "src/main/fulcro_template/api/mutations.clj" data)]
      ["src/main/{{sanitized}}/api/mutations.cljs" (render "src/main/fulcro_template/api/mutations.cljs" data)]
      ["src/main/{{sanitized}}/api/read.clj" (render "src/main/fulcro_template/api/read.clj" data)]
      ["src/main/{{sanitized}}/client.cljs" (render "src/main/fulcro_template/client.cljs" data)]
      ["src/main/{{sanitized}}/client_main.cljs" (render "src/main/fulcro_template/client_main.cljs" data)]
      ["src/main/{{sanitized}}/server.clj" (render "src/main/fulcro_template/server.clj" data)]
      ["src/main/{{sanitized}}/server_main.clj" (render "src/main/fulcro_template/server_main.clj" data)]
      ["src/main/{{sanitized}}/ui/components.cljc" (render "src/main/fulcro_template/ui/components.cljc" data)]
      ["src/main/{{sanitized}}/ui/root.cljc" (render "src/main/fulcro_template/ui/root.cljc" data)]
      ["src/main/translations/es.cljc" (render "src/main/translations/es.cljc" data)]
      ["src/test/{{sanitized}}/CI_runner.cljs" (render "src/test/fulcro_template/CI_runner.cljs" data)]
      ["src/test/{{sanitized}}/client_test_main.cljs" (render "src/test/fulcro_template/client_test_main.cljs" data)]
      ["src/test/{{sanitized}}/sample_spec.cljc" (render "src/test/fulcro_template/sample_spec.cljc" data)]
      ["src/test/{{sanitized}}/tests_to_run.cljs" (render "src/test/fulcro_template/tests_to_run.cljs" data)])))
