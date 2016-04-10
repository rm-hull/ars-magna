(defproject rm-hull/ars-magna "0.2.0"
  :description "An anagram solver in Clojure"
  :url "https://github.com/rm-hull/ars-magna"
  :license {
    :name "The MIT License (MIT)"
    :url "http://opensource.org/licenses/MIT"}
  :dependencies [
    [org.clojure/clojure "1.8.0"]
    [org.clojure/data.json "0.2.6"]
    [org.clojure/data.csv "0.1.3"]
    [com.taoensso/timbre "4.3.1"]
    [compojure "1.5.0"]
    [ring "1.4.0"]
    [hiccup "1.0.5"]
    [ring-logger-timbre "0.7.5"]
    [metrics-clojure-ring "2.6.1"]
    [org.clojure/math.combinatorics "0.1.1"]]
  :scm {:url "git@github.com:rm-hull/ars-magna.git"}
  :ring {
    :handler ars-magna.handler/app }
  :plugins [
    [lein-ring "0.9.7"]
    [codox "0.9.1"] ]
  :source-paths ["src"]
  :resouce-paths ["resouces"]
  :jar-exclusions [#"(?:^|/).git"]
  :uberjar-exclusions [#"\.SF" #"\.RSA" #"\.DSA"]
  :codox {
    :sources ["src"]
    :output-dir "doc/api"
    :src-dir-uri "http://github.com/rm-hull/ars-magna/blob/master/"
    :src-linenum-anchor-prefix "L" }
  :min-lein-version "2.6.1"
  :profiles {
    :uberjar {:aot :all}
    :dev {
      :global-vars {*warn-on-reflection* true}
      :dependencies [
        [org.clojure/test.check "0.9.0"]]
      :plugins [
        [lein-cloverage "1.0.6"]]}})
