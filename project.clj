(defproject kepler "0.1.0-SNAPSHOT"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
    [org.clojure/clojure "1.7.0"]
    [org.clojure/core.async "0.1.346.0-17112a-alpha"]
    [jarohen/chord "0.6.0"]

    [prismatic/schema "0.4.4"]

    [org.clojure/tools.reader "0.9.2"]

   [ring/ring-core "1.3.2"]
   [compojure "1.3.4"]
   [hiccup "1.0.5"]
   [ring-middleware-format "0.5.0"]
   [ring-basic-authentication "1.0.5"]

   [jarohen/yoyo "0.0.5"]
   [jarohen/yoyo.system "0.0.1-20150704.122931-4"]
   [jarohen/yoyo.cljs "0.0.3"]
   [jarohen/yoyo.http-kit "0.0.2"]
   [jarohen/embed-nrepl "0.1.1"]
   [danlentz/clj-uuid "0.1.6"]


    ]
  :main ^:skip-aot kepler.main
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
