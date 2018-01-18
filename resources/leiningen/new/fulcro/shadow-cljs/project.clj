(defproject fulcro-template "0.1.0-SNAPSHOT"
  :description "My Cool Project"
  :license {:name "MIT" :url "https://opensource.org/licenses/MIT"}
  :min-lein-version "2.7.0"

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [fulcrologic/fulcro "2.1.0"]
                 [fulcrologic/fulcro-spec "2.0.0-beta3" :scope "test" :exclusions [fulcrologic/fulcro]]]

  :uberjar-name "fulcro-template.jar"

  :source-paths ["src/dev" "src/main" "src/cards" "src/test"]
  :test-paths ["src/test"]

  :clean-targets ^{:protect false} ["target" "out" "resources/public/js/cards" "resources/public/js/main" "resources/public/js/test/js" ]

  :aliases {"dev"  ["run" "-m" "shadow.cljs.devtools.cli" "watch" "app" "test" "cards"]
            "i18n" ["run" "-m" "shadow.cljs.devtools.cli" "compile" "i18n"]
            "ci"   ["run" "-m" "shadow.cljs.devtools.cli" "compile" "ci-tests"]}

  :test-refresh {:report       fulcro-spec.reporters.terminal/fulcro-report
                 :with-repl    true
                 :changes-only true}

  :profiles {:uberjar    {:main           fulcro-template.server-main
                          :aot            :all
                          :jar-exclusions [#"public/js/prod" #"com/google.*js$"]
                          :prep-tasks     ["clean" ["clean"]
                                           "compile" ["with-profile" "production" "cljsbuild" "once" "production"]]}
             :production {}
             :dev        {:jvm-opts     ["-XX:-OmitStackTraceInFastThrow" "-client" "-XX:+TieredCompilation" "-XX:TieredStopAtLevel=1"
                                         "-Xmx1g" "-XX:+UseConcMarkSweepGC" "-XX:+CMSClassUnloadingEnabled" "-Xverify:none"]

                          :plugins      [[com.jakemccrary/lein-test-refresh "0.17.0"]]

                          :dependencies [[binaryage/devtools "0.9.7"]
                                         [thheller/shadow-cljs "2.0.131"]
                                         [fulcrologic/fulcro-inspect "2.0.0-alpha4"]
                                         [org.clojure/tools.namespace "0.3.0-alpha4"]
                                         [org.clojure/tools.nrepl "0.2.13"]
                                         [com.cemerick/piggieback "0.2.2"]
                                         [devcards "0.2.4" :exclusions [cljsjs/react cljsjs/react-dom]]]
                          :repl-options {:init-ns          user
                                         :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}})
