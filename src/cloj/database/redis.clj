(ns cloj.database.redis
	(:refer-clojure :exclude [get set])
	(:use     [clojure.tools.logging :only (info error)])
	(:require [taoensso.carmine :as car :refer (wcar)]))

(def server1-conn 
  {:pool {:max-active 8} 
   :spec {:host "127.0.0.1" :port 6379 :timeout 4000}})

(defmacro wcar* [& body] `(car/wcar server1-conn ~@body))

(defn set 
	[key value]
	(info (str "Storing Key: " key " Value: " value))
	(wcar* (car/set key value)))

(defn get 
	[key]
	(info (str "Getting Key: " key))
	(wcar* (car/get key)))