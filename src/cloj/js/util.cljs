(ns cloj.js.util)

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
(defn log [& logs]
	(.log js/console (str logs)))

(defn json-parse
  "Returns ClojureScript data for the given JSON string."
  [line]
  (js->clj (JSON/parse line)))