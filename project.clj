(defproject site-generator "0.1.0-SNAPSHOT"
  :description "Generates epxlabs.com"
  :url "https://github.com/epxlabs/site-generator"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-aws-s3 "0.3.10" :exclusions [joda-time]]
                 [clygments "0.1.1"]
                 [enlive "1.1.6"]
                 [me.raynes/cegdown "0.1.1"]
                 [optimus "0.18.5"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-devel "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [stasis "2.3.0"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler site-generator.core/app}
  :aliases {"export-site" ["run" "-m" "site-generator.core/export"]})
