(ns cloj.templating.cljs.sockets
(:require
   [cljs.core.async :refer [chan <! >! put!]]
   [cljs.reader :as reader]
   [cloj.templating.cljs.util :refer [log]]
   [dommy.utils :as utils]
   [dommy.core :as dommy])
(:use-macros
    [dommy.macros :only [node sel sel1]])
(:require-macros
   [cljs.core.async.macros :refer [go]]))

(def send (chan))
(def receive (chan))
(def alert-view (chan))

(def ws-url "ws://localhost:1338/async")
(def ws (new js/WebSocket ws-url))

;; Lifted almost completely from dnolen: 
;;  https://github.com/swannodette/async-tests/blob/master/src/async_test/utils/helpers.cljs#L103
(defn event-chan
  [c el type]
  (let [writer #(put! c %)]
    (dommy/listen! el type writer)
    {:chan c
     :unsubscribe #(dommy/unlisten! el type writer)}))

#_(defn make-sender []
  (event-chan send (sel1 :#websocket) :click)
  (go
   (while true
     (let [evt  (<! send)]
         (.send ws (clj->js {:msg "test" :name "duane"}))))))

(defn make-sender []
  (event-chan send (sel1 :#websocket) :click)
  (go
   (while true
     (let [evt  (<! send)
           name "Duane"
           msg  "Message"]
       (when (= (.-type evt) "click")
         (log (str (JSON/stringify (clj->js {:msg msg :name name})) "\n"))
         (.send ws {:msg msg :name name}))))))


(defn messages []
  (sel1 :#foo))

(defn add-message []
  (go
   (while true
     (let [msg            (<! receive)
           raw-data       (.-data msg)
           data           (reader/read-string raw-data)]
       (dommy/append! (sel1 :#foo) [:li (str data)])
       (log "Top: " (.-scrollTop (sel1 :#foo)))
       (log "Height: " (.-scrollHeight (sel1 :#foo)))
       (set! (.-scrollTop (sel1 :#foo)) (.-scrollHeight (sel1 :#foo)))
       ))))

#_(defn highlight-new-message []
  (go
   (while true
     (let [alert-msg (<! alert-view)]
       (js/setTimeout
        #(dom/remove-class! alert-msg "new")
        200)))))

(defn make-receiver []
  (set! (.-onmessage ws) (fn [msg] (put! receive msg)))
  (add-message)
  #_(highlight-new-message))

(defn init! []
  (make-sender)
  (make-receiver))

(defn ^:export init []
	(set! (.-onload js/window) init!))
