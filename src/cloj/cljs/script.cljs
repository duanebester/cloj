(ns cloj.cljs.script
(:require [ajax.core :refer [GET POST]]
		  [dommy.utils :as utils]
      [dommy.core :as dommy]
		  [cloj.js.util :refer [log]]
      [cljs.core.async :refer [chan <! >! put!]]
      [cljs.reader :as reader])
(:use-macros
    [dommy.macros :only [node sel sel1]])
(:require-macros
   [cljs.core.async.macros :refer [go]]))

(defn receive [event]
  (let [resp (js->clj event)]
    (dommy/append! (sel1 :#foo) [:li (get-in resp ["test"])])
    (set! (.-scrollTop (sel1 :#foo)) (.-scrollHeight (sel1 :#foo)))))

(defn error-handler [event]
  (log (str "Something went wrong: " event)))
 
(defn ping-server [e]
 (GET "/api" {:handler receive :error-handler error-handler})
 (.preventDefault e))

(defn myalert [e]
  (.alert js/window (str "Hello!"))
  (log "something happened")
  (.preventDefault e))
 
(defn ^:export init []
	#_(set! (.-onclick (sel1 :#ping)) myalert)
	(set! (.-onclick (sel1 :#ping)) ping-server))