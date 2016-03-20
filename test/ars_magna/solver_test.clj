(ns ars-magna.solver-test
  (:require
    [clojure.test :refer :all]
    [ars-magna.dict :refer :all]
    [ars-magna.solver :refer :all]))

(deftest check-solver
  (let [dict (load-word-list :en-GB)
        index (partition-by-word-length dict)]
    (is (= (sort (search index "compute" 3 nil))
           ["come put" "compute" "cote ump" "cut mope" "cut poem"
            "cute mop" "met coup" "mote cup" "mute cop" "tome cup"]))))
