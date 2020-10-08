<template>
  <div class="home">
    <!-- 상단 Navbar -->
    <HomeNav />
    <div class="mt-2 words" style="text-align:center;">
      <h1 class="my-element my-5" style="font-size:3vw;">Get funding with <span class="twojob">TwoJob</span></h1>
    </div>
    <img style="width:100%; margin-bottom: 8%" class="mx-0 py-0" src="../assets/images/배경12.png" alt="">
    <!-- 투자홈 사진 -->
    <div class="homeDiv homDiv1 mx-auto mt-5" style="display:flex"> 
      <img class="homeImg1" src="../assets/images/home8.jpg" alt="">
      <div class="mt-4 ml-5 pt-3 pl-4">
        <h1 class="engTitle mt-5" style="font-size:5vw">Reliable</h1>
        <h3 class="korTitle">신뢰가능한</h3>
        <p class="mt-5 ml-2" style="font-size:1vw"><span class="twojob">TwoJob</span>서비스는 블록체인에 프로젝트 이력을 저장하고<br> 상품설명서 정확도를 체크하여 신뢰가능한<br> 투자/쇼핑이 가능합니다.</p>
      </div>
    </div>

    <div class="homeDiv mx-auto" style="display:flex"> 
      <div class="mt-4 ml-5 mr-5 pt-3 pl-4 pr-4">
        <h1 class="engTitle mt-5" style="font-size:4.7vw">Transparent</h1>
        <h3 class="korTitle mr-2" style="text-align: end">투명한</h3>
        <p class="mt-5 mr-2" style="font-size:1vw; text-align: end">모든 사용자에게 투자한 금액의 사용내역서를<br> <span class="twojob">Twojob</span>에서는 투명하게 공개하고 있습니다.<br> 이제 걱정없이 투자해보세요.</p>
      </div>
      <img class="homeImg2 ml-2" src="../assets/images/home5.jpg" alt="">
    </div>

    <div class="homeDiv mx-auto" style="display:flex"> 
      <img class="homeImg3 mr-1" src="../assets/images/하늘filter.jpg" alt="">
      <div class="mt-4 ml-4 pt-3 pl-3">
        <h1 class="engTitle mt-5" style="font-size:4.6vw">profitable</h1>
        <h3 class="korTitle ml-2">수익성있는</h3>
        <p class="mt-5 ml-2" style="font-size:1vw">자신이 투자한 프로젝트에서 나오는 수익을<br> <span class="twojob">TwoJob</span>은 자동화 시스템으로 토큰을 보상해줍니다.<br> 다양한 투자프로젝트에 참여하시고 토큰을 얻어 수익을 얻어보세요.</p>
      </div>
    </div>
    <!-- <div class="homeImg">
      <v-carousel>
        <v-carousel-item          
          v-for="(item, i) in items" :key="i"
          :src="require(`../assets/images/${item.src}`)"
          reverse-transition="fade-transition"
          transition="fade-transition"
        ></v-carousel-item>
      </v-carousel>
      <img style="width: 100%" src="../assets/images/home.gif" alt="home-main">
    </div> -->
    <!-- <button @click="onWallet" style="margin-left: 200px;">
      눌러봐
    </button> -->
    <!-- 홈페이지 설명 -->
    <!-- <div class="home_info">
      <div class="home_div">
        <div style="display: inline-block; flex: 1">
          <i class="fab fa-medapps fa-7x"></i>
        </div>
        <div style="flex: 7; margin-left: 10px">
          <h3 class="info_left">
            투잡러들의 투잡현황!
            <br />기대 이상의 수익률!
          </h3>
          <h3 class="info_text">기대 이상의 수익률!</h3>
        </div>
        <div class="info_right" style="flex: 4">
          <div style="text-align: center">
            <h5>투자 프로젝트</h5>
            <h1>100</h1>
          </div>
          <div style="text-align: center">
            <h5>투자 규모</h5>
            <h1>1000</h1>
          </div>
        </div>
        <div class="info_right" style="flex: 4">
          <div style="text-align: center">
            <h5>투자 프로젝트</h5>
            <h1>100</h1>
          </div>
          <div style="text-align: center">
            <h5>투자 규모</h5>
            <h1>1000</h1>
          </div>
        </div>
      </div>
      <div class="home_div">
        <div style="display: inline-block; flex: 1">
          <i class="fab fa-medapps fa-7x"></i>
          <i class="fas fa-lock fa-5x"></i>
        </div>
        <div style="flex: 7; margin-left: 10px">
          <h3 class="info_left">
            소중한 자산 보안은
            <br />블록체인 기술로!
          </h3>
          <h3 class="info_text">기대 이상의 수익률!</h3>
        </div>

        <div class="info_right" style="flex: 4">
          <div style="text-align: center">
            <h5>투자 프로젝트</h5>
            <h1>100</h1>
          </div>
          <div style="text-align: center">
            <h5>투자 규모</h5>
            <h1>1000</h1>
          </div>
        </div>
        <div class="info_right" style="flex: 4">
          <div style="text-align: center">
            <h5>투자 프로젝트</h5>
            <h1>100</h1>
          </div>
          <div style="text-align: center">
            <h5>투자 규모</h5>
            <h1>1000</h1>
          </div>
        </div>
      </div> -->
      <!-- <div class="home_div">
        <div style="display: inline-block; flex: 1">
          <i class="fas fa-desktop fa-4x"></i>
        </div>
        <div style="flex: 7; margin-left: 10px">
          <h3 class="info_left">
            언제 어디서든
            <br />쉽고 간편하게!
          </h3>
          <h3 class="info_text">기대 이상의 수익률!</h3>
        </div>

        <div class="info_right" style="flex: 5">
          <img
            style="width: 400px"
            src="https://i.ytimg.com/vi/GWra1dWRA8g/maxresdefault.jpg"
            alt
          />
        </div>
      </div> -->
      <!-- <div class="home_div">
        <div style="display: inline-block; flex: 1">
          <i class="far fa-thumbs-up fa-5x"></i>
        </div>
        <div style="flex: 7; margin-left: 10px">
          <h3 class="info_left">
            다양한 카테고리를
            <br />즐길 수 있는 유일한 곳!
          </h3>
          <h3 class="info_text">기대 이상의 수익률!</h3>
        </div>
        <div class="info_right" style="flex: 4.5">
          <img
            style="width: 300px"
            src="https://gscaltexmediahub.com/wp-content/uploads/2015/04/teamwork.jpg"
            alt
          />
        </div>
      </div> -->
    <!-- </div> -->
    <div class="home_info"></div>
  </div>
</template>

<script>
// import Vue from 'vue'
import axios from "axios";
// import router from "@/routes";

import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
// import 'bootstrap/dist/css/bootstrap.css'
// import 'bootstrap-vue/dist/bootstrap-vue.css'
import "../../public/css/Home.scss";
import store from "../store/index.js";
import HomeNav from "../components/HomeNav.vue";
import Web3 from "web3";
import { registerWallet } from "@/api/wallet.js";

const SERVER_URL = "https://www.twojob.ga/api";
const app_key = "2d3bdff993293b2a8c5a82f963175c8a";
const redirect_uri = "https://www.twojob.ga/api";

export default {
  mounted() {
    axios
      .get(`${SERVER_URL}/Token/balance?accessToken=${store.state.accessToken}`)
      .then((res) => {
        // console.log("이건 밸런스값임");
        // console.log(res);
        if (res.data != "401") {
          // console.log("401 에러안나");
          store.commit("setBalance", res.data);
        }
        // console.log(store.state.balance);
      });
  },
  methods: {
  },
  components: {
    HomeNav,
  },
  data() {
    return {
      items: [
        {
          src: "home4.jpg",
          // src: "https://image.freepik.com/free-photo/_93675-87338.jpg",
        },
        {
          src: "home1.jpg",
            // "https://lh3.googleusercontent.com/proxy/eEAKz6kNc0gXbYyQR5AM2PFZQYoKepaO4JbqZAjJWtbGtTPvBVfh_2sTRVlj6Woh-UIHNphyKm9bIdkz5va1p2KKywJyl7Ed4872B5o9yQ",
        },
        {
          src: "home2.jpg",
            // "https://cdn.wadiz.kr/wwwwadiz/green002/2019/0430/20190430185646591_32703.jpg/wadiz/format/jpg/quality/80/optimize",
        },
        {
          src: "home3.jpg", 
          // "https://cdn.vuetifyjs.com/images/carousel/planet.jpg",
        },
      ],
    };
  },
};
</script>


<style scoped>
.investNav {
  height: 50px;
  /* text-align: center; */
  text-align: right;
  line-height: 50px;
}
.items div {
  display: inline-block;
  margin: 0 10% 0 0;
}
.items div a {
  color: black;
  text-decoration: none;
}
.homeImg {
  filter: brightness(85%);
  margin-bottom: 3%;
}
.home_info {
  padding: 50px 0;
  margin: 0 150px;
}
.info_left {
  display: inline-block;
  margin: 40px 30px;
  width: 300px;
  text-align: center;
}
.info_right {
  display: inline-block;
}
.home_div {
  margin-bottom: 150px;
  display: flex;
  justify-content: center;
  align-items: center;
}
/* .ImgHomediv {
  display: inline-block;
  padding: 120px 120px 120px 120px;
  margin-right: 40%;
  background-color:#E1F5FE;
} */
.homeDiv {
  margin: 3% 3% 12% 3%;
  width: 80%;
  /* background-color: #212121; */
  background-color: rgb(250, 247, 243) ;
  /* border: 1px solid #cdd3d8; */
  border-radius: 12px;
  box-shadow: 0 10px 20px 0 rgba(31, 34, 36, 0.4);;

}
.homeImg1 {
  border-radius: 12px 0 0 12px;
  width: 55%; 
}
.homeImg2 {
  border-radius: 0 12px 12px 0;
  width: 53%; 
}
.homeImg3 {
  border-radius: 12px 0 0 12px;
  width: 54%; 
}
.engTitle {
  /* font-family: 'Black Han Sans', sans-serif; */
  font-family: 'Libre Baskerville', serif;
}
.korTitle {
  font-family: 'Noto Serif KR', serif;
}
.twojob {
  font-family: 'Lobster', cursive;
}
.my-element {
  display: inline-block;
  margin: 0 0.5rem;
  font-family: 'Libre Baskerville', serif;
  animation: slideInLeft; /* referring directly to the animation's @keyframe declaration */
  animation-duration: 1.7s; /* don't forget to set a duration! */
}
.words {
  background-color:white !important;
}
.homDiv1 {
  animation: slideInLeft;
  animation-duration: 1.7s;
}
</style>