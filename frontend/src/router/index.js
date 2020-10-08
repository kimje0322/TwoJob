import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/Home.vue";
import InvestHome from "@/views/invest/InvestHome.vue";
import InvestProject from "@/views/invest/InvestProject.vue";
import InvestDetail from "@/views/invest/InvestDetail.vue";
import WriteInvest from "@/views/invest/WriteInvest.vue";
import Search from "@/views/search/Search.vue"
import SearchDetail from "@/views/search/SearchDetail.vue"
import ShoppingHome from "@/views/shopping/ShoppingHome.vue";
import ShoppingDetail from "@/views/shopping/ShoppingDetail.vue";
import ShoppingProject from "@/views/shopping/ShoppingProject.vue";
import ShoppingReview from "@/views/shopping/ShoppingReview.vue";
import Mypage from "@/views/mypage/Mypage.vue";
import WriteShopping from "@/views/shopping/WriteShopping.vue";
import MyInvestCreate from "@/views/mypage/MyInvestCreate.vue";
import MyShoppingCreate from "@/views/mypage/MyShoppingCreate.vue";
import MyShoppingParticipate from "@/views/mypage/MyShoppingParticipate.vue";
import LikeList from "@/views/mypage/LikeList.vue"
import Messenger from "@/views/mypage/Messenger.vue"
// import ChatRoom from "@/views/c"

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
        path: "/investproject",
        name: "InvestProject",
        component: InvestProject,
    },
    {
        path: "/investdetail/:address?",
        name: "InvestDetail",
        component: InvestDetail,
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
        path: "/shoppingdetail/:address?",
        name: "ShoppingDetail",
        component: ShoppingDetail,
    },
    {
        path: "/shoppingproject",
        name: "ShoppingProject",
        component: ShoppingProject,
    },
    {
        path: "/mypage/:userid?",
        name: "Mypage",
        component: Mypage,
    },
    {
        path: "/shoppingreview",
        name: "ShoppingReview",
        component: ShoppingReview,
    },
    {
        path: "/writeshopping/:address?",
        name: "WriteShopping",
        component: WriteShopping,
    },
    {
        path: "/myinvestcreate/:userid?",
        name: "MyInvestCreate",
        component: MyInvestCreate,
    },
    {
        path: "/myshoppingcreate/:userid?",
        name: "MyShoppingCreate",
        component: MyShoppingCreate,
    },
    {
        path: "/myShoppingparticipate/:userid?",
        name: "MyShoppingParticipate",
        component: MyShoppingParticipate,
    },
    {
        path: "/likelist",
        name: "LikeList",
        component: LikeList,
    },
    {
        path: "/messenger",
        name: "Messsenger",
        component: Messenger,
    },
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes,
});


export default router;