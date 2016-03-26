(ns ars-magna.solver
  (:require
    [clojure.string :as s]
    [ars-magna.dict :refer :all]))

(defn multi-word [index word min-size prefix]
  (if (empty? word)
    (s/trim prefix)
    (flatten
      (for [w (find-in index word min-size :en-GB)]
        (multi-word
          index
          (remaining-chars word w)
          min-size
          (str w " " prefix))))))

