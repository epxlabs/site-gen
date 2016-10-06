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
  [:body :div#home] (html/append (s/slider request)
                                 ;; Need to pass the request to header for the logo
                                 (s/header request)
                                 (s/index)
                                 #_(s/who-we-are)
                                 #_(s/call-to-action)
                                 #_(s/what-we-do)
                                 #_(s/our-blog)
                                 #_(s/contact-us-form)
                                 (s/contact-us)
                                 ;; Need to pass the request to footer for the logo
                                 (s/footer request)))
