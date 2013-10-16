(ns cloj.css.main
  (:use compojure.core)
  (:refer-clojure :exclude [+ - * /])
  (:require [garden.units :as gu :refer [px em]]
            [garden.color :as gc :refer [hsl rgb]]
            [garden.arithemetic :refer [+ - * /]]
            [garden.def :refer [defrule]]
            [garden.core :refer [css]]
            [hiccup.page :refer [html5]]))
 
(def ^{:doc "The famous \"micro\" clearfix."}
  clearfix
  ["&"
   {:*zoom 1}
   ["&:before" "&:after"
    {:content "\"\""
     :display "table"}]
   ["&:after"
    {:clear "both"}]])
 
;; In Sass configuration is achieved through the use of mutable
;; variables. In Clojure we can define our default configuration as an
;; immutable *value*. Later we can refer to this value when building
;; out or grid system. Note how we can use Clojure's tagged literals
;; to make our stylesheet code look more like CSS. 
 
(def grid-defaults
  {:column-width (px 60)
   :gutter-width (px 20)
   :columns 12})
 
;; This function has been ported directly from the SCSS code.
 
;; SEE: https://github.com/twigkit/semantic.gs/blob/master/stylesheets/scss/grid.scss#L11-L13
(defn grid-system-width
  [{:keys [column-width columns gutter-width]}]
  (+ (* column-width columns)
     (* gutter-width columns)))
 
;; Since we aren't using mutable values to configure our grid system
;; and it's behavior, we need to apply a functional approach. Instead
;; of functions which simply respond to mutable values in the
;; surrounding code, we use higher order functions to create an
;; "instance" of a grid system based upon it's input values.
 
;; There are four key functions which are commonly used when working
;; with grid systems: `row`, `column`, `push`, and `pull`. The
;; following higher order functions assist in creating them. Each one
;; accepts grid configuration via a map and returns an instance of one
;; of these key functions.
 
;; https://github.com/twigkit/semantic.gs/blob/master/stylesheets/scss/grid.scss#L46-L53
(defn row-fn
  "Creates a row function based on a grid configuration."
  [{:keys [total-width grid-width gutter-width]}]
  (fn []
    ["&"
     clearfix
     {:display "block"
      :width (* total-width
                (/ (+ gutter-width grid-width)
                   grid-width))
      :margin [0 (* total-width
                    (- (/ (* 0.5 gutter-width)
                          grid-width)))]}]))
 
;; https://github.com/twigkit/semantic.gs/blob/master/stylesheets/scss/grid.scss#L54-L61
(defn column-fn
  "Creates a column function based on a grid configuration."
  [{:keys [total-width grid-width column-width gutter-width]}]
  (fn [n]
    {:display "inline"
     :float "left"
     :width (* total-width
               (/ (- (* (+ gutter-width
                           column-width)
                        n)
                     gutter-width)
                  grid-width))
     :margin [0 (* total-width
                   (/ (* 0.5 gutter-width)
                      grid-width))]}))
 
(defn offset-margin
  "Computes the offset amount for push and pull functions."
  [{:keys [total-width grid-width column-width gutter-width]} amount]
  (+ (* total-width
        (/ (* (+ gutter-width
                 column-width)
              amount)
           grid-width))
     (* total-width
        (/ (* 0.5 gutter-width)
           grid-width))))
 
;; Lastly, we need a function for tying everything together.
 
(defn create-grid
  ([]
     ;; Make use of the default grid settings.
     (create-grid grid-defaults))
  ([grid-opts]
     (let [;; Ensure we have everything we need to generate the key
           ;; functions mentioned above.
           opts (merge grid-defaults grid-opts)
           {:keys [column-width gutter-width total-width]} opts
           ;; Calculate the grid with.
           grid-width (grid-system-width grid-opts)
           opts (assoc opts
                  :grid-width grid-width
                  :total-width (or total-width grid-width))
           ;; Partially evaluate `offset-margin` with our grid options
           ;; for our custom `push` and `pull` functions.
           offset (partial offset-margin opts)]
       ;; Return a map representing an instance of our grid system.
       {:row (row-fn opts)
        :column (column-fn opts)
        :push (fn [amount]
                {:margin-left (offset amount)})
        :pull (fn [amount]
                {:margin-right (offset amount)})})))
 
;; With our grid factory in place let's put together a demo. For
;; demonstration purposes we'll take the easy route and build the
;; "fixed" grid (http://semantic.gs/examples/fixed/fixed.html).
 
;; I like to create this alias because it better expresses the intent
;; in the context of gardening.
(def styles list)
 
;; Define a few rule functions to make the code more expressive and
;; readable.
 
(defrule center :div.center)
(defrule top :section#top)
(defrule main :section#main)
(defrule sidebar :section#sidebar)
(defrule headings :h1 :h2 :h3)
 
;; I like this approach as well.
(def center-text {:text-align "center"})
 
;; Define our "fixed" grid CSS.
(def fixed
  ;; Create a standard grid and bind the key values.

    (styles
     ["*" "*:after" "*:before"
      {:box-sizing "border-box"}]

     [:input#terminal
      {
       :padding [(px 8) (px 16)]
       :border-radius (px 4)
       :border [(px 1) "solid"]
       :font-size (px 16)
       :font-weight "lighter"
       :width (gu/percent 100)
       :color (rgb [90 90 90])
        }
        [:&:focus
        {
         :outline "none"
          }]]

     [:button
      {:padding [(px 8) (px 16)]
       :background (rgb [54 126 173])
       :margin [(px 5) (px 10) (px 10) (px 0)]
       :border-radius (px 4)
       :text-align "center"
       :border [(px 1) "solid" "transparent"]
       :font-size (px 16)
       :font-weight "lighter"
       :color (rgb [250 250 250])}]

     [:ul.bar
      {:background (rgb [245 245 245])
       :min-height (px 400)
       :max-height (px 400)
       :overflow-y "auto"
       :padding (px 10)
       :border-radius (px 6)
       :border [(px 1) "solid" "transparent"]
       :font-size (px 11)
       :font-weight "lighter"}
       [:li 
        {
         :padding (px 5)
         :list-style "none"
          }
          [:&:before
          {
            :content "'#'"
            }]]]
 
     [:body
      {:width (gu/percent 100)
       :background (rgb [253 254 255])
       :font-family [["Georgia" :sans-serif]]
       :padding 0
       :margin 0}
      clearfix]
 
     (headings
      {:font-weight "normal"})
 
     (center
      {:width (gu/percent 100)
       :margin [0 "auto"]
       :overflow "hidden"})
 
     (top
      center-text
      {:width (gu/percent 100)
       :margin (em 0)
       :color (rgb [176 176 176])
       :padding (px 5)
       :background (rgb [54 54 54])})
 
     (main
      {:margin [(px 20) "auto"]
       :color (hsl [0 0 30])
       :float "none"
       :padding (px 40)})
 
     (sidebar
      center-text
      {:color (hsl [0 0 40])
       :background (hsl [0 0 80])
       :padding (px 20)})))