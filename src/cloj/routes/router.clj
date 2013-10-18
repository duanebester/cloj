(ns cloj.routes.router
  (:require [cloj.controllers.app :as app])
  (:use     [compojure.route :only [files resources not-found]]
            [compojure.handler :only [site]] ; form query params decode; cookie; session, etc
            [compojure.core :only [defroutes GET POST DELETE ANY context]]
            [org.httpkit.server]))


(defroutes all-routes
  (GET "/" [] app/show-landing-page)
  (GET "/ws" [] app/chat-handler)     ;; websocket
  (GET "/api" [] app/poll-mesg)     ;; websocket
  (GET "/async" [] app/async-handler) ;; asynchronous(long polling)
  ;;(context "/user/:id" []
  ;;         (GET / [] get-user-by-id)
  ;;         (POST / [] update-userinfo))
  (resources "/js/" {:root "js"}) ;; static file url prefix /static, in `public` folder
  #_(route/not-found "<p>Page not found.</p>")) ;; all other, return 404
