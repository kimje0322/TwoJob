import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/Home.vue";
import InvestHome from "@/views/invest/InvestHome.vue";
import WriteInvest from "@/views/invest/WriteInvest.vue";
import Search from "@/views/search/Search.vue"
import SearchDetail from "@/views/search/SearchDetail.vue"
import ShoppingHome from "@/views/shopping/ShoppingHome.vue";
import ShoppingDetail from "@/views/shopping/ShoppingDetail.vue";
import Mypage from "@/views/mypage/Mypage.vue";
import WriteShopping from "@/views/shopping/WriteShopping.vue";
import MyInvestPjt from "@/views/mypage/MyInvestPjt.vue"

Vue.use(VueRouter);

/**
 * 아래의 router를 변경하여 구현할 수 있습니다.
 */
const routes = [{
        path: "/",
        name: "home",
        component: Home,
    },
    {
        path: "/investhome",
        name: "InvestHome",
        component: InvestHome,
    },
    {
        path: "/writeinvest",
        name: "WriteInves",
        component: WriteInvest,

    },
    {
        path: "/search",
        name: "Search",
        component: Search,
    },
    {
        path: "/search/detail",
        name: "SearchDetail",
        component: SearchDetail,
    },
    {
        path: "/shoppinghome",
        name: "ShoppingHome",
        component: ShoppingHome,
    },
    {
        path: "/shoppingdetail",
        name: "ShoppingDetail",
        component: ShoppingDetail,
    },
    {
        path: "/mypage",
        name: "Mypage",
        component: Mypage,
    },
    {
        path: "/writeshopping",
        name: "WriteShopping",
        component: WriteShopping,
    },
    {
        path: "/myinvestpjt",
        name: "MyInvestPjt",
        component: MyInvestPjt,
    }
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes,
});


export default router;