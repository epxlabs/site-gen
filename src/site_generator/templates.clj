(ns site-generator.templates
  (:require [net.cgrand.enlive-html :as html]
            [optimus.link :as link]))

;; Config to be moved to DB
(def config {:title "EPX Labs, Inc."})

(defn get-bundle-paths [request attr paths]
  (html/clone-for
   [path (link/bundle-paths request paths)]
   (html/set-attr attr path)))

(html/deftemplate home-page "templates/application.html"
  [request]
  [:head :title] (html/content (:title config))
  [:head [:link html/last-of-type]] (get-bundle-paths request :href ["app.css"])
  [:body [:script html/last-of-type]] (get-bundle-paths request :src ["app.js"]))
