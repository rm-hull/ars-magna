(ns ars-magna.solver-test
  (:require
    [clojure.test :refer :all]
    [ars-magna.dict :refer :all]
    [ars-magna.solver :refer :all]))

(deftest check-multi-word-solver
  (let [dict (load-word-list :en-GB)
        index (partition-by-word-length dict)]
    (is (= (sort (multi-word index "compute" 3))
           ["come put" "compute" "cote ump" "cut mope" "cut poem"
            "cute mop" "met coup" "mote cup" "mute cop" "tome cup"]))))

(deftest check-longest-solver
  (let [dict (load-word-list :en-GB)
        index (partition-by-letters dict)]
    (is (= (longest index "compute" 5)
           ["comet" "coupe" "tempo" "compute"]))))
