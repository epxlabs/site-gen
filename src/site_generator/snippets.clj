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
             :what-we-do {:DevOps {:h4.mb-sm "DevOps"
                                   :p.mb-lg "We Do DevOps"
                                   :i.icons (html/add-class "icon-settings")}
                          :Serverless {:h4.mb-sm "Serverless & Event Driven"
                                       :p.mb-lg "We Do Serverless"
                                       :i.icons (html/add-class "icon-social-dropbox")}
                          :Functional {:h4.mb-sm "Functional Development"
                                       :p.mb-lg "We Do Clojure"
                                       :i.icons (html/substitute
                                                 (html/html-snippet
                                                  "<div class=\"feature-box-icon\">
                                                   <span class=\"fa fa-alpha-l\"></span>
                                                   </div>"))}}})

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
  [:div.col-md-4] (html/clone-for [[_ data] (:what-we-do config)]
                                  [:h4.mb-sm] (html/content (:h4.mb-sm data))
                                  [:p.mb-lg] (html/content (:h4.mb-sm data))
                                  [:i.icons] (:i.icons data)))
