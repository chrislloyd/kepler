(defproject kepler "0.1.0-SNAPSHOT"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[jarohen/chord "0.7.0-SNAPSHOT"]
                 [jarohen/yoyo "0.0.5"]
                 [jarohen/yoyo.http-kit "0.0.4"]
                 [org.clojure/clojure "1.7.0"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [prismatic/schema "1.0.0"]]
  :main ^:skip-aot kepler.main
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
