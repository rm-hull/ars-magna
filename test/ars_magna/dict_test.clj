(ns ars-magna.dict-test
  (:require
    [clojure.test :refer :all]
    [ars-magna.dict :refer :all]))

(def test-dict
  ["hello" "hat" "gloves" "time" "normally"
   "at" "banana" "leaf" "lead" "and" "you"])

(deftest check-load-word-list
  (is (= 99171 (count (load-word-list :en-GB)))))

(deftest check-partitioning
  (let [index (partition-by-word-length test-dict)]
    (is (nil? (index 0)))
    (is (nil? (index 1)))
    (is (= (index 2) ["at"]))
    (is (= (index 3) ["you" "and" "hat"]))
    (is (= (index 4) ["lead" "leaf" "time"]))
    (is (= (index 5) ["hello"]))
    (is (= (index 6) ["banana" "gloves"]))
    (is (nil? (index 7)))
    (is (= (index 8) ["normally"]))))


(deftest check-words-of-size
  (let [index (partition-by-word-length test-dict)]
    (is (= ["normally" "banana" "gloves" "hello"] (words-of-size index 12 5)))
    (is (= ["normally" "banana" "gloves" "hello" "lead"
            "leaf" "time" "you" "and" "hat" "at"] (words-of-size index 12)))
    (is (= ["lead" "leaf" "time" "you" "and" "hat" "at"] (words-of-size index 4)))
    (is (= ["you" "and" "hat"] (words-of-size index 3 3)))
    (is (= ["you" "and" "hat" "at"] (words-of-size index 3)))
    (is (= ["at"] (words-of-size index 2)))
    (is (empty? (words-of-size index 1)))
    (is (empty? (words-of-size index 0)))))

(deftest check-can-make?
  (is (true? (can-make? (set "compute") "cute")))
  (is (true? (can-make? (set "compute") "pout")))
  (is (true? (can-make? (set "compute") "mute")))
  (is (true? (can-make? (set "compute") "pot")))
  (is (true? (can-make? (set "compute") "top")))
  (is (false? (can-make? (set "compute") "poot"))))

(deftest check-find-in
  (let [index (partition-by-word-length test-dict)]
    (is (= ["hat"] (find-in index "that" 0 :en-GB)))))

(deftest check-remaining-chars
  (is (= (set "mute") (remaining-chars "compute" "cop"))))
