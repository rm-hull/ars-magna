(ns ars-magna.dict
  (:require
    [clojure.string :refer [split-lines lower-case]]))

(defn load-word-list [lang]
  (->>
    (str "data/" (name lang) "/words")
    slurp
    split-lines
    (map lower-case)))

(defn partition-by-word-length [dict]
  (reduce
    (fn [acc word]
      (update acc (count word) conj word))
    (sorted-map)
    dict))
