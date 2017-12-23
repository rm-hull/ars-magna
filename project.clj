(defproject rm-hull/ars-magna "0.2.0"
  :description "An anagram solver in Clojure"
  :url "https://github.com/rm-hull/ars-magna"
  :license {
    :name "The MIT License (MIT)"
    :url "http://opensource.org/licenses/MIT"}
  :dependencies [
    [org.clojure/data.json "0.2.6"]
    [org.clojure/data.csv "0.1.4"]
    [com.taoensso/timbre "4.10.0"]
    [compojure "1.6.0"]
    [ring "1.6.3"]
    [hiccup "1.0.5"]
    [ring-logger-timbre "0.7.6"]
    [metrics-clojure-ring "2.10.0"]
    [org.clojure/math.combinatorics "0.1.4"]]
  :scm {:url "git@github.com:rm-hull/ars-magna.git"}
  :ring {
    :handler ars-magna.handler/app }
  :source-paths ["src"]
  :resouce-paths ["resouces"]
  :jar-exclusions [#"(?:^|/).git"]
  :uberjar-exclusions [#"\.SF" #"\.RSA" #"\.DSA"]
  :codox {
    :sources ["src"]
    :output-dir "doc/api"
    :src-dir-uri "http://github.com/rm-hull/ars-magna/blob/master/"
    :src-linenum-anchor-prefix "L" }
  :min-lein-version "2.8.1"
  :profiles {
    :uberjar {:aot :all}
    :dev {
      :global-vars {*warn-on-reflection* true}
      :dependencies [
        [org.clojure/clojure "1.9.0"]
        [org.clojure/test.check "0.9.0"]]
      :plugins [
        [codox "0.10.3"]
        [lein-ring "0.12.2"]
        [lein-cljfmt "0.5.7"]
        [lein-cloverage "1.0.10"]]}})
