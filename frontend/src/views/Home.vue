<template>
  <div class="home">
    <!-- 상단 Navbar -->
    <navbar/>
    <!-- 투자홈 광고사진 -->
    <div class="homeImg">
      <v-carousel>
        <v-carousel-item
          v-for="(item,i) in items"
          :key="i"
          :src="item.src"
          reverse-transition="fade-transition"
          transition="fade-transition"
        ></v-carousel-item>
      </v-carousel>
    </div>
    <!-- 홈페이지 설명 -->
    <div class="home_info">
      <div class="home_div">
        <div style="display: inline-block; flex: 1;">
          <i class="fab fa-medapps fa-7x"></i>
        </div>
        <div style="flex: 7; margin-left: 10px;">
          <h3 class="info_left">
            투잡러들의 투잡현황!
            <br />기대 이상의 수익률!
          </h3>
          <!-- <h3 class="info_text">기대 이상의 수익률!</h3> -->
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
        <div style="display: inline-block; flex: 1;">
          <!-- <i class="fab fa-medapps fa-7x"></i> -->
          <i class="fas fa-lock fa-5x"></i>
        </div>
        <div style="flex: 7; margin-left: 10px;">
          <h3 class="info_left">
            소중한 자산 보안은
            <br />블록체인 기술로!
          </h3>
          <!-- <h3 class="info_text">기대 이상의 수익률!</h3> -->
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
        <div style="display: inline-block; flex: 1;">
          <i class="fas fa-desktop fa-4x"></i>
        </div>
        <div style="flex: 7; margin-left: 10px;">
          <h3 class="info_left">
            언제 어디서든
            <br />쉽고 간편하게!
          </h3>
          <!-- <h3 class="info_text">기대 이상의 수익률!</h3> -->
        </div>

        <div class="info_right" style="flex: 5">
          <img style="width: 400px;" src="https://i.ytimg.com/vi/GWra1dWRA8g/maxresdefault.jpg" alt />
        </div>
      </div>
      <div class="home_div">
        <div style="display: inline-block; flex: 1;">
          <i class="far fa-thumbs-up fa-5x"></i>
        </div>
        <div style="flex: 7; margin-left: 10px;">
          <h3 class="info_left">
            다양한 카테고리를
            <br />즐길 수 있는 유일한 곳!
          </h3>
          <!-- <h3 class="info_text">기대 이상의 수익률!</h3> -->
        </div>
        <div class="info_right" style="flex: 4.5">
          <img
            style="width: 300px;"
            src="https://gscaltexmediahub.com/wp-content/uploads/2015/04/teamwork.jpg"
            alt
          />
        </div>
      </div>
    </div>
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
import store from '../store/index.js'
import Navbar from '../components/Navbar.vue'

const SERVER_URL = "http://j3b102.p.ssafy.io:8080";
const app_key = "2d3bdff993293b2a8c5a82f963175c8a";
const redirect_uri = "http://j3b102.p.ssafy.io:8080";

export default {
  components: {
    Navbar,
  },
  data() {
    return {
      items: [
        {
          src: "https://image.freepik.com/free-photo/_93675-87338.jpg",
        },
        {
          src:
            "https://lh3.googleusercontent.com/proxy/eEAKz6kNc0gXbYyQR5AM2PFZQYoKepaO4JbqZAjJWtbGtTPvBVfh_2sTRVlj6Woh-UIHNphyKm9bIdkz5va1p2KKywJyl7Ed4872B5o9yQ",
        },
        {
          src:
            "https://cdn.wadiz.kr/wwwwadiz/green002/2019/0430/20190430185646591_32703.jpg/wadiz/format/jpg/quality/80/optimize",
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/planet.jpg",
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
  margin-bottom: 3%;
}
.home_info {
  padding: 200px 0;
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
</style>