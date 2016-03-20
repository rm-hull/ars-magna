(ns ars-magna.dict-test
  (:require
    [clojure.test :refer :all]
    [ars-magna.dict :refer :all]))

(deftest check-load-word-list
  (is (= 99171 (count (load-word-list :en-GB)))))

(deftest check-partitioning
  (let [index (partition-by-word-length
           ["hello" "hat" "gloves" "time" "normally"
            "at" "banana" "leaf" "lead" "and" "you"])]
    (is (nil? (index 0)))
    (is (nil? (index 1)))
    (is (= (index 2) ["at"]))
    (is (= (index 3) ["you" "and" "hat"]))
    (is (= (index 4) ["lead" "leaf" "time"]))
    (is (= (index 5) ["hello"]))2
    (is (= (index 6) ["banana" "gloves"]))
    (is (nil? (index 7)))
    (is (= (index 8) ["normally"]))))



