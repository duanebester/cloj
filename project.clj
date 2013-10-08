(defproject cloj "0.1.0-SNAPSHOT"
  :description "Web Application Made In Clojure"
  :url "http://duanebester.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
        				 [com.taoensso/carmine "2.2.0"]             ;; Redis
        				 [http-kit "2.1.10"]                        ;; Super Fast Http
        				 [compojure "1.1.5"]                        ;; Compojure for Routing
        				 [hiccup "1.0.4"]                           ;; Html from Clojure
        				 [garden "0.1.0-beta6"]                     ;; CSS from Clojure
                 [org.clojure/core.match "0.2.0-rc5"]
                 [org.clojure/core.logic "0.8.4"]
                 [org.clojure/core.async "0.1.0-SNAPSHOT"]
                 [org.clojure/data.json "0.2.3"]
                 [org.clojure/tools.logging "0.2.6"]        ;; Logging
                 [clj-ssh "0.5.6"]                          ;; SSH
                 ;;[clj-logging-config "1.9.10"]              ;; Use this to set up logging formatting
                 [cljs-ajax "0.2.0"]
                 [prismatic/dommy "0.1.1"]
                 [org.clojure/clojurescript "0.0-1859"]
        				 ]

  ;; Plugin to compile ClojureScript to JavaScript
  :plugins [[lein-cljsbuild "0.3.2"]]
  
  ;; Three builds - dev, production, pre-production
  :cljsbuild {
              :builds [{:source-paths ["src/cloj/cljs"]
              :compiler {:output-to "resources/js/main.js"
                         :optimizations :whitespace
                         :pretty-print true}}]
              ; :builds
              ; {:dev
              ;  {:source-paths ["src/cloj/js/script"]
              ;   :compiler {:output-to "static/js/main_dev.js"
              ;              :optimizations :whitespace
              ;              :pretty-print true}}
               
              ;  :prod
              ;  {:source-paths ["src/cloj/js/script"]
              ;   :compiler {:output-to "static/js/main.js"
              ;              :pretty-print false
              ;              :optimizations :advanced}}
               
              ;  :pre-prod
              ;  {:source-paths ["src/cloj/js/script"]
                
              ;   :compiler {:output-to "static/js/main_pre.js"
              ;              :optimizations :simple
              ;              :pretty-print false
              ;              }}}
              }

  ;; Repos
  :repositories {"sonatype-oss-public" "https://oss.sonatype.org/content/repositories/snapshots/"}

  ;; Main function is in cloj.core namespace
  :main cloj.core)