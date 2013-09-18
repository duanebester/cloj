(ns cloj.core
  (:require [cloj.http.server :as cs])
  (:gen-class))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (cs/start-server 1337))