(ns cloj.js.script
(:require [ajax.core :refer [GET POST]]
		  [cloj.js.util :refer [log by-id]]))
 
(defn handler [response]
  (log response))

(defn error-handler []
  (log "Something went wrong"))
 
(defn ping-server [e]
 (GET "/api" {:handler handler :error-handler error-handler})
 (log "//api" "two?")
 (.preventDefault e))

(defn myalert [e]
  (.alert js/window (str "Hello!"))
  (log "something happened")
  (.preventDefault e))
 
(defn ^:export init []
	#_(set! (.-onclick (by-id "ping")) myalert)
	(set! (.-onclick (by-id "ping")) ping-server))