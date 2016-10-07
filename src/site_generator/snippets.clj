(ns site-generator.snippets
  (:require [net.cgrand.enlive-html :as html]
            [optimus.link :as link])
  (:import java.time.Year))

;; Config to be moved to DB
(def config {:blogs [{:title "Blog 1"
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
                      :link "https://blog.epxlabs.com/3"}]
             :contact-us {:get-in-touch "We are always available to help solve your problems, meet others in the space, and discuss what we're passionate about. Tell us how we can help!"}
             :email "hello@epxlabs.com"
             :logo-image "/img/logos/epx_logo.svg"
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
             :nav-links {"#home" "Home"
                         "/devops" "Devops"
                         "/serverless" "Serverless"
                         "#" "Node.js"
                         "/clojure" "Clojure"
                         "/who-we-are" "Who We Are"
                         "##" "Blog"
                         "#contact" "Contact Us"}
             :phone "646.768.0123"
             :social-icons {:github {:title "GitHub"
                                     :url "https://github.com/epxlabs/"}
                            :linkedin {:title "LinkedIn"
                                       :url "https://www.linkedin.com/company/epx-labs/"}
                            :twitter {:title "Twitter"
                                      :url "https://twitter.com/epxlabs"}}
             :slider {:background-video "/videos/11845277.mp4"
                      :background-image "/img/landing.jpg"
                      :btn-href "#identity"
                      :top-label "ARE YOU READY FOR"
                      :main-label "THE FUTURE?"}
             :what-we-do [{:title "DevOps"
                           :summary ["The EPX Labs team implements DevOps as a philosophy."
                                     "We work with you to establish business priorities."
                                     "Then we choose the best technologies to accomplish your goals."
                                     "Lets end the war between Development and Operations."
                                     "EPX Labs delivers the maximum value to your customers."
                                     "Sign the DevOps Peace Treaty."]
                           :icon (html/add-class "icon-settings")}
                          {:title "Serverless"
                           :summary ["No more configuration, provisioning, and patching."
                                     "No more exorbitant bills for Hosting and Support."
                                     "No more wasted capacity."
                                     "Sleep well knowing your services are managed by experts."
                                     "Now you can focus more on your business."
                                     "EPX Labs specializes in implementing Serverless solutions that save time and money."
                                     "The future is less about servers and more about services."]
                           :icon (html/add-class "icon-social-dropbox")}
                          {:title "Clojure & ClojureScript"
                           :summary ["We love Clojure."
                                     "Our language of choice."
                                     "Rapid development."
                                     "Fewer defects."
                                     "Happier developers."
                                     "We could go on."
                                     "EPX Labs delivers more value to your customers faster."
                                     "We specialize in architecting and implementing Clojure and ClojureScript applications on time and on budget."]
                           :icon (html/substitute
                                  (html/html-snippet
                                   "<span class=\"fa fa-alpha-l\"></span>"))}]
             :who-we-are ["System Architects, Managers of Applications, Programmers, and Engineers."
                          "EPX Labs is in the business of challenging assumptions."
                          "We are change agents for hire."
                          "We automate away your IT pains and deliver recurring value propositions to our client-partners."
                          "Let us show you the power of IT Simplicity."]})

(defn build-social-icons []
  (html/clone-for
   ;; destructure our social-icons value
   [[media {:keys [title url]}] (:social-icons config)]
   [:li] (html/add-class (str "social-icons-" (name media)))
   [:li :a] (html/set-attr :href url :title title)
   [:li :a :i] (html/add-class (str "fa-" (name media)))))

;; Adding a multi-line function to be called by each snippet to correctly place
;;  vectors of strings into <p> wrapper tags
(defn make-multiline [lines]
  (html/clone-for [line lines]
                  [:p] (html/content line)))

(html/defsnippet header "partials/header.html"
  [html/root]
  [request]
  [:a#header-logo :img] (html/set-attr :src (link/file-path request (:logo-image config)))
  ;; Social icons in header
  [:ul.social-icons [:li html/first-of-type]] (build-social-icons)
  [:ul#mainNav
   [:li html/first-of-type]] (html/clone-for [[href content] (:nav-links config)]
                                             [:li :a] (html/set-attr :href href)
                                             [:li :a] (html/content content))
  [:ul#mainNav
   [:li html/first-of-type]] (html/add-class "dropdown" "active"))

(html/defsnippet slider "partials/slider.html"
  [html/root]
  [request]
  [:img.rev-slidebg] (html/set-attr :src (link/file-path request (get-in config [:slider :background-image])))
  [:ul
   [:li html/first-of-type]
   :div.rs-background-video-layer] (html/set-attr
                                    :data-videomp4 (link/file-path request
                                                                   (get-in config [:slider :background-video])))
  [:div.top-label] (html/content (get-in config [:slider :top-label]))
  [:div.main-label] (html/content (get-in config [:slider :main-label]))
  [:a.btn-slider-action] (html/set-attr :href (get-in config [:slider :btn-href])))

(html/defsnippet call-to-action "partials/call-to-action.html"
  [html/root]
  [])

(html/defsnippet who-we-are "partials/who-we-are.html"
  [html/root]
  []
  [:div.center :p] (make-multiline (:who-we-are config)))

;; We should see if it is possible to write a macro to create the selector
;;  and transformations so we can use maps of the selector to content
;; This would be helpful if we build this out since we could create a GUI around it
(html/defsnippet what-we-do "partials/what-we-do.html"
  [html/root]
  []
  [:div.col-md-4] (html/clone-for [{:keys [title summary icon]} (:what-we-do config)]
                                  [:h4.mb-sm] (html/content title)
                                  [:p] (make-multiline summary)
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

(html/defsnippet contact-us "partials/contact-us.html"
  [html/root]
  []
  [:p#get-in-touch] (html/content (get-in config [:contact-us :get-in-touch]))
  [:ul#contact-info
   [:li html/first-of-type]] (html/append " 646.768.0123")
  [:ul#contact-info
   [:li html/last-of-type]] (html/append
                             (html/html-snippet
                              (str
                               "<a href=\"mailto:"
                               (:email config) "\">"
                               " "
                               (:email config)
                               "</a>")))
  [:ul.social-icons [:li html/first-of-type]] (build-social-icons))

(html/defsnippet footer "partials/footer.html"
  [html/root]
  [request]
  [:a.logo :img] (html/set-attr :src (link/file-path request (:logo-image config)))
  [:div#copyright :p] (html/content
                       (str
                        "© Copyright "
                        (.. Year now getValue)
                        " EPX Labs, Inc. All Rights Reserved.")))

(html/defsnippet main "partials/index.mdown"
  [html/root]
  [])

(html/defsnippet devops "partials/devops.html"
  [html/root]
  [])

(html/defsnippet serverless "partials/serverless.html"
  [html/root]
  [])

(html/defsnippet clojure "partials/clojure.html"
  [html/root]
  [])

(html/defsnippet about-us "partials/about-us.html"
  [html/root]
  [])
