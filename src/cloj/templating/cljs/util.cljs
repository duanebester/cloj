(ns cloj.templating.js.util
(:require [goog.dom :as gdom]))

(defn length [nodes] (. nodes -length))

(defn item [nodes n] (.item nodes n))

(defn as-seq [nodes]
  (for [i (range (length nodes))] (item nodes i)))

(defn by-id [id]
  (.getElementById js/document (name id)))

(defn by-tag [tag]
  (as-seq
    (.getElementsByTagName js/document (name tag))))

(defn html [dom] (. dom -innerHTML))

(defn set-html! [dom content]
  (set! (. dom -innerHTML) content))

(defn append-child! [dom content]
  (gdom/appendChild dom content))

(defn log [& logs]
	(.log js/console (str logs)))