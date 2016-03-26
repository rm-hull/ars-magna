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

(defroutes app-routes
  (let [dict (load-word-list :en-GB)
        index (partition-by-word-length dict)]
    (GET "/multi-word" [word :as req]
      (json-exception-handler
        (to-json identity
          (let [min-size (Integer/parseInt (or (get-in req [:params :min]) "3"))]
            (sort
              (multi-word index (clean word) min-size nil))))))))

(def app
    (->
      app-routes
      (logger.timbre/wrap-with-logger)
      (expose-metrics-as-json)
      (instrument)
      (handler/api)))
