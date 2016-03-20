(ns ars-magna.letter-frequencies-test
  (:require
    [clojure.test :refer :all]
    [ars-magna.letter-frequencies :refer :all]))

(deftest check-letter-ordering
  (is (= "etaoinshrdlcumwfgypbvkjxqz" ;; From wikipedia
         (apply str (reverse (by-ascending-frequency (letter-frequencies :en-GB)))))))

(deftest check-rarest-letter
  (is (= \p (rarest-letter "compute")))
  (is (nil? (rarest-letter "compute" :ru-RU)))
  (is (nil? (rarest-letter nil))))
