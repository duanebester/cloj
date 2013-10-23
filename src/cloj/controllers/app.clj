(ns cloj.controllers.app
  (:require [cloj.database.redis :as redis]
            [cloj.templating.html.layout :as layout])
  (:use [org.httpkit.server]
        [cloj.templating.json.response :only [jsonRes]]
        [clojure.tools.logging :only (info error)]
        [hiccup.core]
        [clojure.pprint :only (pprint)]))

(defn- now [] (quot (System/currentTimeMillis) 1000))

(def channels (atom []))

(defn poll-mesg [req]
  (info "/api")
  (jsonRes {:test "value"}))

(defn show-landing-page [req]
  (info "/")
  #_(redis/set "foo" "bar")
  #_(println (redis/get "foo"))
  (layout/base [:ul {:id "foo" :class "bar"}
                    (for [x (range 1 4)]
                    [:li x])]
                [:input {:id "terminal" :type "text"}]
                [:button {:id "ping"} "Ping"]
                [:button {:id "websocket"} "Socket"]))


#_(defn update-userinfo [req]          ;; ordinary clojure function
  (let [user-id (-> req :params :id)    ; param from uri
        password (-> req :params :password)] ; form param
    ....
    ))

(defn chat-handler [req]
  (with-channel req channel              ; get the channel
    ;; communicate with client using method defined above
    (on-close channel (fn [status]
                        (println "channel closed")))
    (if (websocket? channel)
      (println "WebSocket channel")
      (println "HTTP channel"))
    (on-receive channel (fn [data]       ; data received from client
           ;; An optional param can pass to send!: close-after-send?
           ;; When unspecified, `close-after-send?` defaults to true for HTTP channels
           ;; and false for WebSocket.  (send! channel data close-after-send?)
                          (send! channel data))))) ; data is sent directly to the client

(defn async-handler [request]
  (with-channel request channel
    (info "/async")
    (on-close channel (fn [status] (println "channel closed: " status)))
    (on-receive channel (fn [data] ;; echo it back
                          (send! channel (str (jsonRes {:data "Duane2"})))
                          (info "Sent WebSocket Data.")))))

