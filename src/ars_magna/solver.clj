(ns ars-magna.solver
  (:require
   [clojure.string :as s]
   [clojure.math.combinatorics :as c]
   [task-scheduler.core :refer :all]
   [ars-magna.dict :refer :all]))

(defn multi-word
  ([index word min-size]
   (multi-word index word min-size nil))

  ([index word min-size prefix]
   (if (empty? word)
     (s/trim prefix)
     (flatten
          (map join
           (for [w (find-in index word min-size :en-GB)]
             (fork
              (multi-word
               index
               (remaining-chars word w)
               min-size
               (str w " " prefix)))))))))

(defn longest [index word min-size]
  (->>
   (range min-size (inc (count word)))
   (mapcat #(map sort (c/combinations word %)))
   (mapcat index)))

(defn- case-insensitive [re] (str "(?i)" re))

(defn- assemble-regex [word]
  (->
   word
   (s/replace "?" ".")
   (s/replace "*" ".+")
   case-insensitive
   re-pattern))

(defn wildcard [dict word]
  (println word)
  (println (assemble-regex word))
  (filter (partial re-matches (assemble-regex word)) dict))
