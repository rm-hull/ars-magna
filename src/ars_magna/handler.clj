(ns ars-magna.handler
  (:require
    [clojure.string :as s]
    [compojure.handler :as handler]
    [compojure.route :as route]
    [compojure.core :refer [defroutes GET POST]]
    [ring.logger.timbre :as logger.timbre]
    [metrics.ring.expose :refer [expose-metrics-as-json]]
    [metrics.ring.instrument :refer [instrument]]
    [ars-magna.json :refer :all]
    [ars-magna.dict :refer :all]
    [ars-magna.solver :refer :all]))

(defn clean [word]
  (->
    word
    s/lower-case
    (s/replace #"\W" "")))

(defn min-size [req default]
  (Integer/parseInt
    (or
      (get-in req [:params :min])
      (str default))))

(defroutes app-routes
  (let [dict (load-word-list :en-GB)
        word-length-index (partition-by-word-length dict)
        sorted-letter-index (partition-by-letters dict)]

    (GET "/multi-word/:word" [word :as req]
      (json-exception-handler
        (to-json identity
          (sort
            (multi-word
              word-length-index
              (clean word)
              (min-size req 3))))))

    (GET "/longest/:word" [word :as req]
      (json-exception-handler
        (to-json identity
          (sort-by
            (juxt (comp - count) identity)
            (longest
              sorted-letter-index
              (clean word)
              (min-size req 4))))))))

(def app
    (->
      app-routes
      (logger.timbre/wrap-with-logger)
      (expose-metrics-as-json)
      (instrument)
      (handler/api)))
