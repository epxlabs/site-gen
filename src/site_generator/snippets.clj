(ns site-generator.snippets
  (:require [net.cgrand.enlive-html :as html]))

;; Config to be moved to DB
(def config {:nav-links {"#home" "Home"
                         "#identity" "Who We Are"
                         "#services" "Our Services"
                         "#blog" "Blog"
                         "#contact" "Contact Us"}
             :header-logo "img/logos/epx_logo_simplify.svg"
             :social-icons {:github {:title "GitHub"
                                     :url "https://github.com/epxlabs/"}
                            :linkedin {:title "LinkedIn"
                                       :url "https://www.linkedin.com/company/epx-labs/"}
                            :twitter {:title "Twitter"
                                      :url "https://twitter.com/"}}
             :who-we-are "We are winners."
             :what-we-do [{:title "DevOps"
                           :summary "We Do DevOps"
                           :icon (html/add-class "icon-settings")}
                          {:title "Serverless & Event Driven"
                           :summary "We Do Serverless"
                           :icon (html/add-class "icon-social-dropbox")}
                          {:title "Functional Development"
                           :summary "We Do Clojure"
                           :icon (html/substitute
                                  (html/html-snippet
                                   "<span class=\"fa fa-alpha-l\"></span>"))}]
             :months {"01" "Jan"
                      "02" "Feb"
                      "03" "Mar"
                      "04" "Apr"
                      "05" "May"
                      "06" "Jun"
                      "07" "Jul"
                      "08" "Aug"
                      "09" "Sep"
                      "10" "Oct"
                      "11" "Nov"
                      "12" "Dec"}
             :blogs [{:title "Blog 1"
                      :date "01242016"
                      :summary "Blog 1 Rocks!"
                      :link "https://blog.epxlabs.com/1"}
                     {:title "Blog 2"
                      :date "03182016"
                      :summary "Blog 2 Rocks!"
                      :link "https://blog.epxlabs.com/2"}
                     {:title "Blog 3"
                      :date "07232016"
                      :summary "Blog 3 Rocks!"
                      :link "https://blog.epxlabs.com/3"}]})

;; TODO: Add src to header logo img tag once logo decided
(html/defsnippet header "partials/header.html"
  [html/root]
  []
  ;; Social icons in header
  [:ul.social-icons
   [:li html/first-of-type]] (html/clone-for
                              ;; destructure our social-icons value
                              [[media {:keys [title url]}] (:social-icons config)]
                              [:li] (html/add-class (str "social-icons-" (name media)))
                              [:li :a] (html/set-attr :href url :title title)
                              [:li :a :i] (html/add-class (str "fa-" (name media))))
  [:ul#mainNav
   [:li html/first-of-type]] (html/clone-for [[href content] (:nav-links config)]
                                             [:li :a] (html/set-attr :href href)
                                             [:li :a] (html/content content))
  [:ul#mainNav
   [:li html/first-of-type]] (html/add-class "dropdown" "active"))

(html/defsnippet slider "partials/slider.html"
  [html/root]
  [])

(html/defsnippet call-to-action "partials/call-to-action.html"
  [html/root]
  [])

(html/defsnippet who-we-are "partials/who-we-are.html"
  [html/root]
  []
  [:div.center :p] (html/content (:who-we-are config)))

;; We should see if it is possible to write a macro to create the selector
;;  and transformations so we can use maps of the selector to content
;; This would be helpful if we build this out since we could create a GUI around it
(html/defsnippet what-we-do "partials/what-we-do.html"
  [html/root]
  []
  [:div.col-md-4] (html/clone-for [{:keys [title summary icon]} (:what-we-do config)]
                                  [:h4.mb-sm] (html/content title)
                                  [:p.mb-lg] (html/content summary)
                                  [:i.icons] icon))

;; n is the nth you would like to get of the partitioned date string
(defn parse-date [date n chunk-size]
  (apply str (nth (partition chunk-size date) n)))

(html/defsnippet our-blog "partials/our-blog.html"
  [html/root]
  []
  [:div.col-md-4] (html/clone-for [{:keys [date link title summary]} (:blogs config)]
                                  [:span.day] (html/content (parse-date date 1 2))
                                  [:span.month] (html/content ((:months config)
                                                               (parse-date date 0 2)))
                                  [:h4 :a] (html/set-attr :href link )
                                  [:h4 :a] (html/content title)
                                  [:p] (html/content summary)
                                  [:article.post :a] (html/set-attr :href link )))

(html/defsnippet contact-us-form "partials/contact-us-form.html"
  [html/root]
  [])
