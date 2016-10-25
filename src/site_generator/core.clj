(ns site-generator.core
  (:require [optimus.assets :as assets]
            [optimus.export :as export]
            [optimus.optimizations :as op]
            [optimus.prime :as optimus]
            [optimus.strategies :as strategies]
            [ring.middleware.content-type :as ct]
            [site-generator.templates :as t]
            [stasis.core :as s]))

;; Config to be moved to DB
(def config {:export-dir "resources/exported_site"})


(defn stringify-page [page context]
  (apply str (page context)))

(defn get-pages []
  ;; merge-page-sources is a convenience to identify conflicts
  (s/merge-page-sources
   {:pages {"/" (fn [context] (apply str (t/home-page context)))
            "/devops/" (fn [context] (apply str (t/devops context)))
            "/serverless/" (fn [context] (apply str (t/serverless context)))
            "/nodejs/" (fn [context] (apply str (t/nodejs context)))
            "/clojure/" (fn [context] (apply str (t/clojure context)))
            "/who-we-are/" (fn [context] (apply str (t/about-us context)))}}))

;; Here we specify which files should be bundled together and minified
;; Since we only have one page it makes sense to put all files in a bundle
;; As the app begins to grow we should separate them to keep file sizes small
(defn get-assets []
  ;; Due to multiple asset classes (bundles and images) we must concat all together
  (concat
   (assets/load-bundles
    "public"
    ;; Order matters! See site-generator.asset-util for details
    {"app.css" ["/vendor/bootstrap/css/bootstrap.min.css"
                "/vendor/font-awesome/css/font-awesome.min.css"
                "/vendor/simple-line-icons/css/simple-line-icons.min.css"
                "/vendor/owl.carousel/assets/owl.carousel.min.css"
                "/vendor/owl.carousel/assets/owl.theme.default.min.css"
                "/vendor/magnific-popup/magnific-popup.min.css"
                "/css/theme.css"
                "/css/theme-elements.css"
                "/css/theme-blog.css"
                "/css/theme-shop.css"
                "/css/theme-animate.css"
                "/vendor/rs-plugin/css/settings.css"
                "/vendor/rs-plugin/css/layers.css"
                "/vendor/rs-plugin/css/navigation.css"
                "/vendor/circle-flip-slideshow/css/component.css"
                "/css/skins/epx_skin.css"
                "/css/custom.css"]
     ;; Order matters! See site-generator.asset-util for details
     "app.js" [ ;; Not generated by site-generator.asset-util
               ;; DO NOT DELETE
               "/vendor/modernizr/modernizr.min.js"

               ;; Generated by site-generator.asset-util
               "/vendor/jquery/jquery.min.js"
               "/vendor/jquery.appear/jquery.appear.min.js"
               "/vendor/jquery.easing/jquery.easing.min.js"
               "/vendor/jquery-cookie/jquery-cookie.min.js"
               "/vendor/bootstrap/js/bootstrap.min.js"
               "/vendor/common/common.min.js"
               "/vendor/jquery.validation/jquery.validation.min.js"
               "/vendor/jquery.stellar/jquery.stellar.min.js"
               "/vendor/jquery.easy-pie-chart/jquery.easy-pie-chart.min.js"
               "/vendor/jquery.gmap/jquery.gmap.min.js"
               "/vendor/jquery.lazyload/jquery.lazyload.min.js"
               "/vendor/isotope/jquery.isotope.min.js"
               "/vendor/owl.carousel/owl.carousel.min.js"
               "/vendor/magnific-popup/jquery.magnific-popup.min.js"
               "/vendor/vide/vide.min.js"
               "/js/theme.js"
               "/vendor/rs-plugin/js/jquery.themepunch.tools.min.js"
               "/vendor/rs-plugin/js/jquery.themepunch.revolution.min.js"
               "/vendor/circle-flip-slideshow/js/jquery.flipshow.min.js"
               "/js/views/view.home.js"
               "/js/views/view.contact.js"
               "/js/custom.js"
               "/js/theme.init.js"]})
   ;; Loads in images
   (assets/load-assets "public" ["/img/logos/epx_logo.svg"
                                 "/videos/11845277.mp4"
                                 "/img/landing.jpg"
                                 "/img/epx-favicon.png"])))

(def app
  ;; Optimus will serve our assets dynamically in development while still optimizing
  (-> (s/serve-pages (get-pages))
      (optimus/wrap
       get-assets
       op/all
       strategies/serve-live-assets)
      (ct/wrap-content-type)))

(defn export []
  (let [;; Optimus defaults produce a lot of files to be on the safe side
        ;; Since we only have one page we can remove the original files and the old files
        ;; to produce a smaller export (NOT WORKING CURRENTLY)
        assets (remove #(or (:bundled %) (:outdated %)) (op/all (get-assets) {}))
        export-dir (:export-dir config)]
    (s/empty-directory! export-dir)
    (export/save-assets assets export-dir)
    ;; Since Optimus will add cache busters to the file names we need to pass the names
    ;; to the templates so they can generate <link> headers correctly
    (s/export-pages
     ;; Enlive returns a list of strings so we must concatenate them
     (get-pages)
     export-dir
     {:optimus-assets assets})))
