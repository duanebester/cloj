(ns cloj.system.sysinfo
  (:use [clojure.tools.logging :only (info error)]))

(import
[java.lang Runtime System]
[java.io File])

(defn ^{:doc "get-sysinfo-map will return important system properties"}
  get-sysinfo-map
  []
  (merge
  {"Processor-Count" (.availableProcessors (Runtime/getRuntime)),
   "OS-Name"         (System/getProperty "os.name"),
   "OS-Arch"         (System/getProperty "os.arch"),
   "User-Name"       (System/getProperty "user.name"),
   "User-Home"       (System/getProperty "user.home"),
   "Java-Version"    (System/getProperty "java.version")}
   ;; Disk Space:
  (apply #(hash-map (str "Free Space (GB) " (.getAbsolutePath %))
                    ;; (1024 * 1024) = MB, (1024 * 1024 * 1024) = GB
                    (float (/ (.getFreeSpace %) (* 1024 1024 1024))))
  #_(File/listRoots)
  (File. (System/getProperty "user.home")))))

;; We merge the System getProps with some root file sizes
#_(merge
  {"Processor-Count" (.availableProcessors (Runtime/getRuntime)),
         "OS-Name"         (System/getProperty "os.name"),
         "OS-Arch"         (System/getProperty "os.arch"),
         "User-Name"       (System/getProperty "user.name"),
         "User-Home"       (System/getProperty "user.home"),
         "Java-Version"    (System/getProperty "java.version")}
  (apply #(hash-map 
                  (str "Disk " (.getAbsolutePath %))
                  (str "Free-Space " 
                    (float 
                      (/ (.getFreeSpace %) (* 1024 1024 1024))) " GB"))
  (File/listRoots)))

