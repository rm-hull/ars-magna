(ns ars-magna.dict
  (:require
    [clojure.set :refer [difference]]
    [clojure.string :refer [split-lines lower-case]]
    [ars-magna.letter-frequencies :refer [rarest-letter]]))

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

(defn words-of-size
  ([index n] (words-of-size index n 0))
  ([index n min-size]
    (if (< n min-size)
      nil
      (lazy-cat
        (index n)
        (words-of-size index (dec n) min-size)))))

(defn can-make? [set target]
  (if (empty? target)
    true
    (let [m (first target)]
      (if (contains? set m)
        (can-make?
          (difference set #{m})
          (rest target))
        false))))

(defn find-in [index word min-size lang]
  (let [rarest-letter (rarest-letter word lang)
        all-letters (set word)
        pred (fn [word]
               (and
                 (contains? (set word) rarest-letter)
                 (can-make? all-letters word)))]
    (->>
      (words-of-size index (count word) min-size)
      (filter pred))))

(defn remaining-chars [word1 word2]
  (difference
    (set word1)
    (set word2)))

