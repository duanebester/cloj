(ns cloj.html.layout
  (:use     [hiccup.page :only [include-css html5]]
  			[compojure.core]
  			[cloj.css.main :as style])
  (:require [garden.core :refer [css]]
            [hiccup.page :refer [html5]]))

(defn base [& content]
  (html5
    [:head
     [:title "Cloj"]
     [:style (css style/fixed)]
     [:script {:type "text/javascript" :src "/js/main.js"}]
     ;;[:script {:type "text/javascript"} "goog.require('hello');"]
     ]
    [:body
      (center
       (top [:h1 "Grid System"])
       (main [:h2 "Main"] content)
       (sidebar [:h2 "Sidebar"]))]))
