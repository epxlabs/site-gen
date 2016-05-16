(ns site-generator.core
  (:require [optimus.assets :as assets]
            [optimus.export :as export]
            [optimus.optimizations :as op]
            [optimus.prime :as optimus]
            [optimus.strategies :as strategies]
            [site-generator.templates :as t]
            [stasis.core :as s]))

;; Config to be moved to DB
(def config {:export-dir "resources/exported_site"})

(defn get-pages []
  ;; merge-page-sources is a convenience to identify conflicts
  (s/merge-page-sources
   {:pages {"/" (fn [context] (t/home-page context))}}))

;; Here we specify which files should be bundled together and minified
;; Since we only have one page it makes sense to put all files in a bundle
;; As the app begins to grow we should separate them to keep file sizes small
(defn get-assets []
  (assets/load-bundles "public"
                       ;; All CSS files under public/css and vendor/ are bundled into app.css
                       {"app.css" [#"/css.+\.css$"
                                   #"/vendor/.+\.css$"]
                        ;; All JS files under public/js and vendor/ are bundled into app.js
                        "app.js" [#"/js.+\.js$"
                                  #"/vendor/.+\.js$"]}))

(def app
  ;; Optimus will serve our assets dynamically in development while still optimizing
  (optimus/wrap (s/serve-pages (get-pages))
                get-assets
                op/all
                strategies/serve-live-assets))

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
    (s/export-pages (get-pages) export-dir {:optimus-assets assets})))
