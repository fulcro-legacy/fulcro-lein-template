(defproject {{name}} "0.1.0-SNAPSHOT"
  :description "My Cool Project"
  :license {:name "MIT" :url "https://opensource.org/licenses/MIT"}
  :min-lein-version "2.7.0"

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [fulcrologic/fulcro "2.3.1" :exclusions [org.clojure/clojurescript]]
                 [fulcrologic/fulcro-spec "2.0.3-1" :scope "test" :exclusions [fulcrologic/fulcro]]]

  :uberjar-name "{{sanitized}}.jar"

  :source-paths ["src/main"]
  :test-paths ["src/test"]

  :test-refresh {:report       fulcro-spec.reporters.terminal/fulcro-report
                 :with-repl    true
                 :changes-only true}

  :profiles {:uberjar    {:main           {{name}}.server-main
                          :aot            :all
                          :jar-exclusions [#"public/js/test" #"public/js/cards" #"public/cards.html"]
                          :prep-tasks     ["clean" ["clean"]
                                           "compile" ["with-profile" "cljs" "run" "-m" "shadow.cljs.devtools.cli" "release" "main"]]}
             :production {}
             :cljs       {:source-paths ["src/main" "src/test" "src/cards"]
                          :dependencies [[binaryage/devtools "0.9.9"]
                                         [thheller/shadow-cljs "2.2.6"]
                                         [org.clojure/core.async "0.3.465"]
                                         [fulcrologic/fulcro-inspect "2.0.0"]
                                         [devcards "0.2.4" :exclusions [cljsjs/react cljsjs/react-dom]]]}
             :dev        {:source-paths ["src/dev" "src/main" "src/cards"]
                          :jvm-opts     ["-XX:-OmitStackTraceInFastThrow" "-client" "-XX:+TieredCompilation" "-XX:TieredStopAtLevel=1"
                                         "-Xmx1g" "-XX:+UseConcMarkSweepGC" "-XX:+CMSClassUnloadingEnabled" "-Xverify:none"]

                          :plugins      [[com.jakemccrary/lein-test-refresh "0.21.1"]]

                          :dependencies [[org.clojure/tools.namespace "0.3.0-alpha4"]
                                         [org.clojure/tools.nrepl "0.2.13"]
                                         [com.cemerick/piggieback "0.2.2"]]
                          :repl-options {:init-ns          user
                                         :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}})
