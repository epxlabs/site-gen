# An Introduction to Vue.Js Routing
##### By Alex Martin
Many of EPX Labs's recent projects have been built using Vue.Js, an excellent new framework for building user interfaces. The reactive framework relies on self-contained, and reusable components that allow for economic and efficient application design. I'm going to walk through a tutorial on routing in Vue.Js, an issue which I initially found troublesome, but is actually much simpler than it first appears.

The most pain-free way to handle routing in Vue.Js is to use [vue-router](https://github.com/vuejs/vue-router). To install, simply run `npm install vue-router --save` in the root of your project. Then create a `routes.js` file inside your `src` folder. At the top of this file, import all of the components in your project. For instance:

```javascript
import Users from './components/Login.vue'
import Events from './components/Events.vue'
```

Then, the most important step is to create a routing object that holds all your routes and the components that they correspond to, and export the object. Here is an example:

```javascript
const routes = [{
  path: '/events',
  component: Events
  name: 'Events',
  description: 'View All Events'
}, {
  path: '/users',
  component: Users,
  name: 'Users',
  description: 'View All Users'
}]
export default routes
```

The name and description fields are optional, but recommended for clarity. Once you have your routing object set up, the remainder of the work is in your `main.js` file. In this file, you must first import and require vue-router:

```javascript
import VueRouter from 'vue-router'
Vue.use(VueRouter)
```
Then, y
ou must create the actual router object and set it in your view instance, like so:

```javascript
var router = new VueRouter({
  routes: routes,
  mode: 'history',
  scrollBehavior: function (to, from, savedPosition) {
    return savedPosition || { x: 0, y: 0 }
  }
})

new Vue({
  el: '#root',
  router: router,
  store: store,
  render: h => h(AppView)
})
```

These special options allow your browser to easily navigate to previous pages, or return back to the next page. It's that simple! One wonderful thing about view-router is that it makes dynamic routing simple. All that needs to be added to a path is a dynamic word, indicated by a semicolon. For instance:

```javascript
{
  path: '/events/:id',
  component: Event
}
```

The router now expects this route to be accessed with a dynamic parameter after events. This parameter is easily accessed within the component by calling `this.$route.params.id`. This is just a brief introduction to the magic and ease of vue-router; it also facilitates nested routing, authentication, redirects, and error handling, along with much more. Having dealt with several frameworks where configuring routes is incredibly painstaking and confusing, I hope I've shed some light on what a painless and developer-friendly process Vue.js routing is.
