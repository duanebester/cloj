(ns cloj.system.sysinfo
  (:use [clojure.tools.logging :only (info error)]))

(import
[java.lang Runtime System]
[java.io File])

(defn get-sysinfo-map
  []
  (concat
  {"Processor-Count" (.availableProcessors (Runtime/getRuntime)),
         "OS-Name"         (System/getProperty "os.name"),
         "OS-Arch"         (System/getProperty "os.arch"),
         "User-Name"       (System/getProperty "user.name"),
         "User-Home"       (System/getProperty "user.home"),
         "Java-Version"    (System/getProperty "java.version")}
  (map #(hash-map (str "Disk " (.getAbsolutePath %))
                  (str "Free-Space " (float (/ (.getFreeSpace %) (* 1024 1024 1024))) " GB"))
  (File/listRoots))))

