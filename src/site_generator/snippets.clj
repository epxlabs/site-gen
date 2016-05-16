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
             :who-we-are "We are winners."})

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
