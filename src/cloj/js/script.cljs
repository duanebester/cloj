(ns cloj.js.script
(:require [ajax.core :refer [GET POST]]
		  [goog.net.XhrIo :as xhr]
		  [domina :as d]
           [domina.events :as events]
		  [cloj.js.util :refer [log by-id set-html! json-parse]]))

(defn receive [event]
  #_(log event)
  #_(log (str event))
  (js/alert event)
  (let [resp (js->clj event)]
    (log (get-in resp ["test"]))
  #_(log (clj->js event))
  #_(log "js -> clj" (str (js->clj event)))
  #_(log "clj -> js" (str (clj->js event)))))

 
(defn handler [response]
  	(log (js->clj response))
  	(log (clj->js response))
  	(log (json-parse response)))

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
	#_(set! (.-onclick (by-id "ping")) myalert)
	(set! (.-onclick (by-id "ping")) ping-server))