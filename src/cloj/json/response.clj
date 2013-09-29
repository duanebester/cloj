(ns cloj.json.response
(:require [clojure.data.json :refer [write-str]]))


(def ^{:const true} json-header {"Content-Type" "application/json; charset=utf-8"})

(defn jsonRes [content]
  {:status 200
   :headers json-header
   :body (write-str content)})