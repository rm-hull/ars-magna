(ns ars-magna.solver
  (:require
   [clojure.string :as s]
   [clojure.math.combinatorics :as c]
   [ars-magna.dict :refer :all]))

(defn multi-word
  ([index word min-size]
   (multi-word index word min-size nil))

  ([index word min-size prefix]
   (if (empty? word)
     (s/trim prefix)
     (flatten
      (for [w (find-in index word min-size :en-GB)]
        (multi-word
         index
         (remaining-chars word w)
         min-size
         (str w " " prefix)))))))

(defn longest [index word min-size]
  (->>
   (range min-size (inc (count word)))
   (mapcat #(map sort (c/combinations word %)))
   (mapcat index)))
