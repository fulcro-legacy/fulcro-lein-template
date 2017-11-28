(ns {{name}}.client-test-main
  (:require {{name}}.tests-to-run
            [fulcro-spec.selectors :as sel]
            [fulcro-spec.suite :as suite]))

(enable-console-print!)

(suite/def-test-suite client-tests {:ns-regex #"{{name}}..*-spec"}
  {:default   #{::sel/none :focused}
   :available #{:focused}})

