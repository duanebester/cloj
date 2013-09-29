(ns cloj.html.layout
  (:use [hiccup.page :only [include-css html5]]
  			[compojure.core]
  			[cloj.css.main :as style])
  (:require [garden.core :refer [css]]
            [hiccup.page :refer [html5]]
            [clojure.data.json :as json]))

(defn base [& content]
  (html5
    [:head
     [:title "Cloj"]
     [:style (css style/fixed)]
     ]
    [:body
      [:div.center
       [:section#top [:h1 "Cloj"]]
       [:section#main [:h2 "Main"] content]
       #_(sidebar [:h2 "Sidebar"])]
      [:script {:type "text/javascript" :src "/js/main.js"}]
      [:script {:type "text/javascript"} "cloj.js.script.init();"]]))

#_(def ^{:const true} json-header {"Content-Type" "application/json; charset=utf-8"})

#_(defn jsonRes [& content]
  {:status 200 :headers json-header :body (json/write-str content)})
