(ns site-generator.templates
  (:require [net.cgrand.enlive-html :as html]
            [optimus.link :as link]
            [site-generator.snippets :as s]))

;; Config to be moved to DB
(def config {:title "EPX Labs, Inc."})

(defn get-bundle-paths [request attr paths]
  (html/clone-for
   [path (link/bundle-paths request paths)]
   (html/set-attr attr path)))

(html/deftemplate home-page "templates/application.html"
  [request]
  [:head :title] (html/content (:title config))
  ;; Add the bundled assets to the HTML file so they are pulled in
  [:head [:link html/last-of-type]] (get-bundle-paths request :href ["app.css"])
  [:body [:script html/last-of-type]] (get-bundle-paths request :src ["app.js"])
  ;; Add the different sections to the HTML
  [:body :div.body] (html/prepend (s/header))
  [:body :div#home] (html/append (s/slider)
                                 (s/call-to-action)
                                 (s/who-we-are)
                                 (s/what-we-do)
                                 (s/our-blog)
                                 (s/contact-us-form)))
