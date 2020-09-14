import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/Home.vue";
import InvestHome from "@/views/InvestHome.vue";

Vue.use(VueRouter);

/**
 * 아래의 router를 변경하여 구현할 수 있습니다.
 */
const routes = [
  {
    path: "/",
    name: "home",
    component: Home,
  },
  {
    path: "/investhome",
    name: "InvestHome",
    component: InvestHome,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});


export default router;
