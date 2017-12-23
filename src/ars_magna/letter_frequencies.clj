(ns ars-magna.letter-frequencies)

; From: https://en.wikipedia.org/wiki/Letter_frequency#Relative_frequencies_of_letters_in_the_English_language

(def letter-frequencies
  {:en-GB {\a  0.08167
           \b  0.01492
           \c  0.02782
           \d  0.04253
           \e  0.12702
           \f  0.02228
           \g  0.02015
           \h  0.06094
           \i  0.06966
           \j  0.00153
           \k  0.00772
           \l  0.04025
           \m  0.02406
           \n  0.06749
           \o  0.07507
           \p  0.01929
           \q  0.00095
           \r  0.05987
           \s  0.06327
           \t  0.09056
           \u  0.02758
           \v  0.00978
           \w  0.02361
           \x  0.00150
           \y  0.01974
           \z  0.00074}})

(defn by-ascending-frequency [letter-freq]
  (->>
   letter-freq
   (sort-by second)
   (map first)))

(defn rarest-letter
  ([word] (rarest-letter word :en-GB))
  ([word lang]
   (let [letters (set word)]
     (->>
      (get letter-frequencies lang)
      (filter #(letters (first %)))
      by-ascending-frequency
      first))))
