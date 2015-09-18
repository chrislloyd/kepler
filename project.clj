(defproject kepler "0.1.0-SNAPSHOT"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[compojure "1.3.4"]
                 [jarohen/chord "0.7.0-SNAPSHOT"]
                 [jarohen/yoyo "0.0.5"]
                 [jarohen/yoyo.http-kit "0.0.4"]
                 [org.clojure/clojure "1.7.0"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.clojure/core.typed "0.3.11"]
                 [prismatic/schema "1.0.0"]
                 [ring/ring-core "1.3.2"]]
  :main ^:skip-aot kepler.main
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :min-lein-version "2.5.2")
