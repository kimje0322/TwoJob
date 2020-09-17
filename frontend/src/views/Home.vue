<template>
  <div>
    <!-- 상단 네브바 -->
    <div class="investNav">
      <div class="items">
        <div>
          <router-link to="/investhome">
            <h5>투자목록</h5>
          </router-link>
        </div>
        <div>
          <router-link to="#">
            <h5>판매목록</h5>
          </router-link>
        </div>
        <div v-if="!login">
          <button @click="onClick">
            <h5 style="margin: 0">로그인</h5>
          </button>
        </div>
        <v-row v-else style="display: inline-block; width: 100px;">
          <!-- <router-link to="/oauth2/authorization/kakao"> -->
          <!-- <h5 style="margin: 0">로그인</h5> -->
          <!-- <v-row justify="center">
            <v-btn @click.stop="dialog = true">
              <i class="fas fa-user fa-lg"></i>
            </v-btn>
            <v-dialog v-model="dialog" max-width="250">
              <v-card>
                <v-text-field label="충전금액" required></v-text-field>
              </v-card>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="green darken-1" text @click="dialog = false">Disagree</v-btn>
                <v-btn color="green darken-1" text @click="dialog = false">Agree</v-btn>
              </v-card-actions>
            </v-dialog>
          </v-row>-->
          <!-- </router-link> -->

          <v-btn @click.stop="dialog = true">
            <i class="fas fa-user fa-lg"></i>
          </v-btn>

          <v-dialog v-model="dialog" max-width="290">
            <v-card>
              <v-card-title class="headline">XX님의 자산 현황 : {{asset}}원</v-card-title>
              <v-text-field v-model="money" label="충전금액" required></v-text-field>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="green darken-1" text @click="onKakao">충전하기</v-btn>
                <v-btn color="green darken-1" text @click="dialog = false">닫기</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-row>
        <div>
          <router-link to="/search">
            <button style="flex-right: 0">
              <i class="fas fa-search"></i>
            </button>
          </router-link>
        </div>
      </div>
    </div>
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
// import "../../public/css/Home.css";
import store from '../store/index.js'

const SERVER_URL = "https://j3b102.p.ssafy.io:8080";
const app_key = "2d3bdff993293b2a8c5a82f963175c8a";
const redirect_uri = "https://j3b102.p.ssafy.io:8080";

export default {
  data() {
    return {
      login: false,
      dialog: false,
      money: "",
      asset: "",
      // useremail: "",
      // username: "",
      // userimg: "",
      userInfo: {
        email: '',
        name: '',
        img: '',
      },
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
  methods: {
    onKakao() {
      axios
        .post(
          `${SEVER_URL}/kakaopay/kakaoPay?count=${this.money * 1}&totalPrice=${
            this.money * 1
          }`
        )
        .then();
    },
    onClick() {
      window.Kakao.Auth.loginForm({
        success: this.GetMe,
      });
    },
    GetMe(authObj) {
      console.log(authObj);
      //토큰값 받아오는 부분
      console.log(authObj.access_token);
      // this.$cookies.set("auth-token", authObj.access_token);
      window.Kakao.API.request({
        url: "/v2/user/me",
        success: (res) => {
          console.log(res)
          this.userInfo.email = res.kakao_account.email
          this.userInfo.name = res.kakao_account.profile.nickname
          this.userInfo.img = res.kakao_account.profile.thumbnail_image_url

          axios
            .post(
              `http://j3b102.p.ssafy.io:8080/account/kakaologin`,
              {
                email: this.userInfo.email,
                nickname: this.userInfo.name,
                image: this.userInfo.img,
              }
            )
            .then((res) => {
              this.login = true
              console.log(this.login)
              console.log(res);
              console.log("저기")
              store.commit('setUserInfo', this.userInfo)
              this.$router.push("/#");
            })
            .catch((error) => {
              console.log(error);
              // this.$router.push("/error");
            });
        },
        fail: (error) => {
          this.$router.push("/error");
        },
      });
    },

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
.v-dialog--active {
  top: 3%;
  position: absolute;
  left: 68%;
}
</style>