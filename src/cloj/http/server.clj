(ns cloj.http.server
  (:require [cloj.routes.router :as routes])
  (:gen-class)
  (:use     [compojure.handler :only [site]] 
            [org.httpkit.server]))

(defn start-server [port]
	(run-server (site #'routes/all-routes) {:port 1338})
	(println "Server started on port 1338"))