(ns ars-magna.dict
  (:require
    [clojure.string :as s]
    [clojure.set :refer [difference]]
    [ars-magna.letter-frequencies :refer [rarest-letter]]))

(defn load-word-list [lang]
  (->>
    (str "data/" (name lang) "/words")
    slurp
    s/split-lines
    ;(map s/lower-case)
    ))

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

(defn can-make? [^String source target]
  (if (empty? target)
    true
    (let [c (str (first target))]
      (if (.contains source c)
        (can-make?
          (s/replace-first source c "")
          (rest target))
        false))))

(defn find-in [index word min-size lang]
  (let [rarest-letter (str (rarest-letter word lang))
        pred (fn [^String w]
               (and
                 (.contains w rarest-letter)
                 (can-make? word w)))]
    (->>
      (words-of-size index (count word) min-size)
      (filter pred))))

(defn remaining-chars [word1 word2]
  (if (empty? word2)
    word1
    (recur
      (s/replace-first word1 (first word2) "")
      (rest word2))))
