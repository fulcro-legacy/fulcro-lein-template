(ns leiningen.new.fulcro
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]
            [clojure.set :as set]
            [clojure.string :as str]))

(def render (renderer "fulcro"))

(defn prompt [question legal-responses]
  (println question)
  (loop [answer (str/trim-newline (read-line))]
    (if (contains? (set legal-responses) answer)
      answer
      (do
        (println question)
        (recur (str/trim-newline (read-line)))))))

(defn cljsbuild-paths [render data]
  [[".gitignore" (render "gitignore" data)]
   ["Makefile" (render "Makefile" data)]
   ["package.json" (render "package.json" data)]
   ["project.clj" (render "project.clj" data)]
   ["README.md" (render "README.md" data)]
   ["resources/i18n/es.po" (render "resources/i18n/es.po" data)]
   ["resources/i18n/messages.pot" (render "resources/i18n/messages.pot" data)]
   ["resources/public/cards.html" (render "resources/public/cards.html" data)]
   ["resources/public/index.html" (render "resources/public/index.html" data)]
   ["script/figwheel.clj" (render "script/figwheel.clj" data)]
   ["src/cards/{{sanitized}}/cards.cljs" (render "src/cards/fulcro_template/cards.cljs" data)]
   ["src/cards/{{sanitized}}/intro.cljs" (render "src/cards/fulcro_template/intro.cljs" data)]
   ["src/dev/cljs/user.cljs" (render "src/dev/cljs/user.cljs" data)]
   ["src/dev/user.clj" (render "src/dev/user.clj" data)]
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
   ["src/test/{{sanitized}}/CI_runner.cljs" (render "src/test/fulcro_template/CI_runner.cljs" data)]
   ["src/test/{{sanitized}}/client_test_main.cljs" (render "src/test/fulcro_template/client_test_main.cljs" data)]
   ["src/test/{{sanitized}}/sample_spec.cljc" (render "src/test/fulcro_template/sample_spec.cljc" data)]
   ["src/test/{{sanitized}}/tests_to_run.cljs" (render "src/test/fulcro_template/tests_to_run.cljs" data)]])

(defn shadowcljs-paths [render data]
  [[".gitignore" (render "gitignore" data)]
   ["karma.conf.js" (render "karma.conf.js" data)]
   ["Makefile" (render "Makefile" data)]
   ["package.json" (render "package.json" data)]
   ["project.clj" (render "project.clj" data)]
   ["README.md" (render "README.md" data)]
   ["resources/i18n/es.po" (render "resources/i18n/es.po" data)]
   ["resources/i18n/messages.pot" (render "resources/i18n/messages.pot" data)]
   ["resources/public/cards.html" (render "resources/public/cards.html" data)]
   ["resources/public/index.html" (render "resources/public/index.html" data)]
   ["resources/public/js/test/index.html" (render "resources/public/js/test/index.html" data)]
   ["shadow-cljs.edn" (render "shadow-cljs.edn" data)]
   ["src/cards/{{sanitized}}/cards.cljs" (render "src/cards/fulcro_template/cards.cljs" data)]
   ["src/cards/{{sanitized}}/intro.cljs" (render "src/cards/fulcro_template/intro.cljs" data)]
   ["src/dev/user.clj" (render "src/dev/user.clj" data)]
   ["src/main/config/defaults.edn" (render "src/main/config/defaults.edn" data)]
   ["src/main/config/dev.edn" (render "src/main/config/dev.edn" data)]
   ["src/main/config/prod.edn" (render "src/main/config/prod.edn" data)]
   ["src/main/{{sanitized}}/api/mutations.clj" (render "src/main/fulcro_template/api/mutations.clj" data)]
   ["src/main/{{sanitized}}/api/mutations.cljs" (render "src/main/fulcro_template/api/mutations.cljs" data)]
   ["src/main/{{sanitized}}/api/read.clj" (render "src/main/fulcro_template/api/read.clj" data)]
   ["src/main/{{sanitized}}/client.cljs" (render "src/main/fulcro_template/client.cljs" data)]
   ["src/main/{{sanitized}}/development_preload.cljs" (render "src/main/fulcro_template/development_preload.cljs" data)]
   ["src/main/{{sanitized}}/server.clj" (render "src/main/fulcro_template/server.clj" data)]
   ["src/main/{{sanitized}}/server_main.clj" (render "src/main/fulcro_template/server_main.clj" data)]
   ["src/main/{{sanitized}}/ui/components.cljc" (render "src/main/fulcro_template/ui/components.cljc" data)]
   ["src/main/{{sanitized}}/ui/root.cljc" (render "src/main/fulcro_template/ui/root.cljc" data)]
   ["src/test/{{sanitized}}/client_test_main.cljs" (render "src/test/fulcro_template/client_test_main.cljs" data)]
   ["src/test/{{sanitized}}/sample_spec.cljc" (render "src/test/fulcro_template/sample_spec.cljc" data)]])

(defn usage []
  (main/info "Usage: lein new fulcro app-name [-h|demo|nodemo|figwheel|shadow-cljs ...]")
  (main/info "       lein new fulcro -h")
  (main/info " -h          : This help")
  (main/info " demo        : Include demo code")
  (main/info " nodemo      : No demo code (it will ask if you don't specify this)")
  (main/info " shadow-cljs : Use shadow-cljs instead of figwheel (better if using js libraries from npm).")
  (main/info " figwheel    : (default) Generate a Fulcro 2.x app that uses figwheel and stock Clojurescript compiler."))

(defn fulcro
  "Generates a simple Fulcro template project"
  [name & add-ons]
  (let [add-ons       (set add-ons)
        help-options  #{"-h" "--help" "help"}
        legal-options (set/union help-options #{"demo" "nodemo" "figwheel" "shadow-cljs"})
        bad-options   (set/difference add-ons legal-options)
        error?        (seq bad-options)
        help?         (or
                        error?
                        (seq (set/intersection help-options add-ons)))]
    (when error?
      (main/warn "Illegal argument(s): " (str/join "," bad-options)))
    (when help?
      (usage)
      (main/exit 0))
    (let [demo-options     #{"demo" "nodemo"}
          demo-specified?  (-> demo-options (set/intersection add-ons) seq boolean)
          demo-prompt-yes? (when-not demo-specified? (= "y" (prompt "Do you want demo content? [y/n] " #{"y" "n"})))
          shadowcljs?      (contains? add-ons "shadow-cljs")
          demo?            (or demo-prompt-yes? (contains? add-ons "demo"))]
      (let [data     {:name      name
                      :demo?     demo?
                      :nodemo?   (not demo?)
                      :sanitized (name-to-path name)}
            base-dir (if shadowcljs?
                       "shadow-cljs/"
                       "2.x/")
            render   (fn [filename data] (render (str base-dir filename) data))
            files    (if shadowcljs?
                       (shadowcljs-paths render data)
                       (cljsbuild-paths render data))]
        (main/info "Generating Fulcro project with options " add-ons)
        (apply ->files data files)))))




