(ns cloj.system.sysinfo
  (:use [clojure.tools.logging :only (info error)]))

(import
[java.lang Runtime System]
[java.io File])

(defn get-sysinfo-map
  []
  {:a "A" :b "B"})


#_(defn get-sysinfo-map
  []
  {"Processor Count" (.availableProcessors (Runtime/getRuntime))
         "OS Name"         (System/getProperty "os.name")
         "OS Arch"         (System/getProperty "os.arch")
         "User Name"       (System/getProperty "user.name")
         "Java Version"    (System/getProperty "java.version")}
        #_(for [disk (File/listRoots)]
          [(str "Disk " (.getAbsolutePath disk))
           (float (/ (.getFreeSpace disk) (* 1024 1024 1024)))]))