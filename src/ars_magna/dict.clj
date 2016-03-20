(ns ars-magna.dict
  (:require
    [clojure.string :refer [split-lines lower-case]]))

(defn load-word-list [lang]
  (->>
    (str "data/" (name lang) "/words")
    slurp
    split-lines
    (map lower-case)))

