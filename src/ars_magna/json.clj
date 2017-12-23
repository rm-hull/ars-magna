(ns ars-magna.json
  (:require
   [ring.util.response :refer [response status content-type charset]]
   [clojure.data.json :as json]
   [taoensso.timbre :as timbre]))

(defn- value-writer [key value]
  (if (instance? java.util.Date value)
    (str value)
    value))

(defn to-json [f & q]
  (->
   (apply f q)
   (json/write-str :value-fn value-writer :key-fn name)
   (response)
   (content-type "application/json")
   (charset "UTF-8")))

(defn json-exception-handler* [f]
  (try
    (f)
    (catch IllegalArgumentException ile
      (->
       (to-json identity {:error (.getMessage ile)})
       (status 400)))
    (catch Exception e
      (do
        (timbre/error e)
        (->
         (to-json identity {:error (.toString e)})
         (status 500))))))

(defmacro json-exception-handler [& body]
  `(json-exception-handler* (^:once fn* [] ~@body)))
