(ns cloj.html.layout
  (:use [hiccup.page :only [include-css html5]]
  			[compojure.core]
        [clojure.data.json :only [json-str]]
  			[cloj.css.main :as style])
  (:require [garden.core :refer [css]]
            [hiccup.page :refer [html5]]))

(defn base [& content]
  (html5
    [:head
     [:title "Cloj"]
     [:style (css style/fixed)]
     [:script {:type "text/javascript" :src "/js/main.js"}]
     ]
    [:body
      [:div.center
       [:section#top [:h1 "Cloj"]]
       [:section#main [:h2 "Main"] content]
       #_(sidebar [:h2 "Sidebar"])]
      [:script {:type "text/javascript"} "cloj.js.script.init();"]]))

(def ^{:const true} json-header {"Content-Type" "application/json; charset=utf-8"})

(defn jsonRes [& content]
  {:status 200 :headers json-header :body (json-str content)})
