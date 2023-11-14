(defproject rm-hull/ars-magna "0.2.1"
  :description "An anagram solver in Clojure"
  :url "https://github.com/rm-hull/ars-magna"
  :license {
    :name "The MIT License (MIT)"
    :url "http://opensource.org/licenses/MIT"}
  :dependencies [
    [org.clojure/clojure "1.11.1"]
    [org.clojure/data.json "2.4.0"]
    [org.clojure/data.csv "1.0.1"]
    [com.taoensso/timbre "6.3.1"]
    [compojure "1.7.0"]
    [ring "1.10.0"]
    [ring-logger-timbre "0.7.6"]
    [metrics-clojure-ring "2.10.0"]
    [org.clojure/math.combinatorics "0.2.0"]]
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
        [org.clojure/test.check "1.1.1"]]
      :plugins [
        [codox "0.10.8"]
        [lein-ring "0.12.6"]
        [lein-cljfmt "0.9.2"]
        [lein-cloverage "1.2.4"]]}})
