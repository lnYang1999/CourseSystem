import Vue from "vue"
import Router from "vue-router"
import Index from "./views/index.vue"
import List from "./views/list.vue"
import Detail from "./views/detail.vue"
import Line from "./views/line.vue"
import About from "./views/about.vue"

Vue.use(Router);

export default new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [{
    path: "*",
    redirect: "/index",
  }, {
    path: "/index",
    component: Index
  }, {
    path: "/list",
    component: List
  }, {
    path: "/detail",
    component: Detail
  }, {
    path: "/line",
    component: Line
  }, {
    path: "/about",
    component: About
  }]
})
