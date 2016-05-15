(ns site-generator.core
  (:require [stasis.core :as s]))

(defn get-pages []
  ;; merge-page-sources is a convenience to identify conflicts
  (s/merge-page-sources
   {:pages {"/" "Hello there"}}))

(def app
  (s/serve-pages (get-pages)))
