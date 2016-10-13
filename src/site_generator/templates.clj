(ns site-generator.templates
  (:require [net.cgrand.enlive-html :as html]
            [optimus.link :as link]
            [site-generator.active-config :as ac]
            [site-generator.snippets :as s]))

;; Config to be moved to DB
(def config {:title "EPX Labs, Inc."})

(defn get-bundle-paths [request attr paths]
  (html/clone-for
   [path (link/bundle-paths request paths)]
   (html/set-attr attr path)))

;; Creates the Google Analytics js script with the tracking code for the active stage
(def google-analytics-script
  (format "
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

  ga('create', '%s', 'auto');
  ga('send', 'pageview');" ac/google-analytics-token))

(html/deftemplate home-page "templates/application.html"
  [request]
  [:head :title] (html/content (:title config))
  ;; Add the bundled assets to the HTML file so they are pulled in
  [:head [:link html/first-of-type]] (html/set-attr :href (link/file-path request "/img/epx-favicon.png"))
  [:head [:link html/last-of-type]] (get-bundle-paths request :href ["app.css"])
  [:body [:script html/first-of-type]] (get-bundle-paths request :src ["app.js"])
  [:body [:script html/last-of-type]] (html/content google-analytics-script)
  ;; Add the different sections to the HTML
  [:body :div#home] (html/append (s/slider request)
                                 ;; Need to pass the request to header for the logo
                                 (s/header request "home")
                                 (s/main)
                                 #_(s/who-we-are)
                                 #_(s/call-to-action)
                                 #_(s/what-we-do)
                                 #_(s/our-blog)
                                 #_(s/contact-us-form)
                                 (s/contact-us)
                                 ;; Need to pass the request to footer for the logo
                                 (s/footer request)))

(html/deftemplate devops "templates/application.html"
  [request]
  [:head :title] (html/content (:title config))
  ;; Add the bundled assets to the HTML file so they are pulled in
  [:head [:link html/first-of-type]] (html/set-attr :href (link/file-path request "/img/epx-favicon.png"))
  [:head [:link html/last-of-type]] (get-bundle-paths request :href ["app.css"])
  [:body [:script html/first-of-type]] (get-bundle-paths request :src ["app.js"])
  [:body [:script html/last-of-type]] (html/content google-analytics-script)
  [:body :div#home] (html/append (s/header request "devops")
                                 (s/devops)
                                 (s/contact-us)
                                 (s/footer request)))


(html/deftemplate serverless "templates/application.html"
  [request]
  [:head :title] (html/content (:title config))
  ;; Add the bundled assets to the HTML file so they are pulled in
  [:head [:link html/first-of-type]] (html/set-attr :href (link/file-path request "/img/epx-favicon.png"))
  [:head [:link html/last-of-type]] (get-bundle-paths request :href ["app.css"])
  [:body [:script html/first-of-type]] (get-bundle-paths request :src ["app.js"])
  [:body [:script html/last-of-type]] (html/content google-analytics-script)
  [:body :div#home] (html/append (s/header request "serverless")
                                 (s/serverless)
                                 (s/contact-us)
                                 (s/footer request)))


(html/deftemplate clojure "templates/application.html"
  [request]
  [:head :title] (html/content (:title config))
  ;; Add the bundled assets to the HTML file so they are pulled in
  [:head [:link html/first-of-type]] (html/set-attr :href (link/file-path request "/img/epx-favicon.png"))
  [:head [:link html/last-of-type]] (get-bundle-paths request :href ["app.css"])
  [:body [:script html/first-of-type]] (get-bundle-paths request :src ["app.js"])
  [:body [:script html/last-of-type]] (html/content google-analytics-script)
  [:body :div#home] (html/append (s/header request "clojure")
                                 (s/clojure)
                                 (s/contact-us)
                                 (s/footer request)))

(html/deftemplate about-us "templates/application.html"
  [request]
  [:head :title] (html/content (:title config))
  ;; Add the bundled assets to the HTML file so they are pulled in
  [:head [:link html/first-of-type]] (html/set-attr :href (link/file-path request "/img/epx-favicon.png"))
  [:head [:link html/last-of-type]] (get-bundle-paths request :href ["app.css"])
  [:body [:script html/first-of-type]] (get-bundle-paths request :src ["app.js"])
  [:body [:script html/last-of-type]] (html/content google-analytics-script)
  [:body :div#home] (html/append (s/header request "who-we-are")
                                 (s/about-us)
                                 (s/contact-us)
                                 (s/footer request)))
