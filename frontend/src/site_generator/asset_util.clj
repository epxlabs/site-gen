(ns site-generator.asset-util
  (:require [net.cgrand.enlive-html :as html]))

;; The purpose of this utility is to parse an HTML file and extract CSS and JS links
;; This is useful since order is important. When regexes are used with Optimus
;;  files are pulled in alphabetical order which is not always desirable

;; Run this util by updating the path-to-html path
;;  and executing (get-js-paths) or (get-css-paths)
;; The results are pretty printed to the console. Remember to get the output from there
;; These lists of paths are usually used for Optimus in conjunction with load-bundles

(def path-to-html "/home/prachetas/Documents/epx_labs/code/website/site-template/final_assets/index.html")

;; Might be a cleaner way to get the Enlive HTML parse but it evades me currently
(defn get-html [html-file]
  (html/html-resource (java.io.StringReader. (slurp html-file))))

;; We can pass in any selector (vector of keywords) and this will return a list of the
;;  selected tags as Enlive maps (hence the e-html)
(defn get-all-tags [html-file selector]
  (let [e-html (get-html html-file)]
    (html/select e-html selector)))

;; Pretty print to console after placing into array
;; This makes it easy to just copy and paste into your Optimus code
(defn clean-output [output]
  (clojure.pprint/pprint
   (into []
         ;; Optimus requires asset paths to begin with a slash
         (map #(str "/" %) output))))

;; Reaches into the Enlive HTML maps and gets a specific attribute (usually a src or href)
;; We only want local paths not remote scripts or stylesheets so we filter any http
;;  values out
(defn get-local-paths [attr e-htmls]
  (clean-output
   (filter #(not (re-find #"^http" %))
           (map
            #(get-in % [:attrs attr])
            e-htmls))))

;; Pretty prints all local CSS paths to console
(defn get-css-paths []
  (get-local-paths
   :href
   (filter
    #(= (get-in % [:attrs :rel]) "stylesheet")
    (get-all-tags path-to-html [:head :link]))))

;; Pretty prints all local JS paths to console
(defn get-js-paths []
  (get-local-paths
   :src
   (get-all-tags path-to-html [:body :script])))

;; Sample function executions
#_(get-css-paths)
#_(get-js-paths)
