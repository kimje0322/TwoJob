<template>
  <!-- 상단 네브바 -->
    <div class="Navbar">
      <div class="items">
        <div class="serviceName">
          <router-link to="/">
            <h3>TwoJob</h3>
          </router-link>
        </div>
        <div>
          <router-link to="/investhome">
            <h5>투자목록</h5>
          </router-link>
        </div>
        <div>
          <router-link to="/shoppinghome">
            <h5>쇼핑목록</h5>
          </router-link>
        </div>
        <div v-if="!login">
          <button @click="onClick">
            <h5 style="margin: 0">로그인</h5>
          </button>
        </div>
        <v-row v-else style="display: inline-block; width: 150px; ">
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
          <v-btn @click="onchargebox">
            <i class="fas fa-user fa-lg"></i>
          </v-btn>
          <p @click="onLogout()" style="display: inline-block; cursor: pointer">로그아웃</p>
          <div class="chargebox" style="inline-block" v-if="openbox">
            <v-card style="padding: 20px; margin: 0">
              <v-card-title class="headline">{{userInfo.name}}님의 자산 현황 : {{asset}}원</v-card-title>
              <v-text-field class="moneyinput" v-model="money" label="충전금액" required></v-text-field>
              <v-card-actions class="moneybtns">
                <v-spacer></v-spacer>
                <v-btn class="chargebtn" text @click="onKakao">충전하기</v-btn>
                <v-btn class="closebtn" text @click="openbox = false">닫기</v-btn>
              </v-card-actions>
            </v-card>
          </div>
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
</template>

<script>
import axios from "axios";
import store from '../store/index.js'
import "../../public/css/Navbar.scss"

const SERVER_URL = "http://j3b102.p.ssafy.io:8080";
const app_key = "2d3bdff993293b2a8c5a82f963175c8a";
const redirect_uri = "http://j3b102.p.ssafy.io:8080";

export default {
  data() {
    return {
      login: false,
      openbox: false,
      money: "",
      asset: "0",
      next: false,
      nexturl: '',
      userInfo: {
        email: '',
        name: '',
        img: '',
        login: false,
      },
    }
  },
  mounted() {
    if (store.state.isSigned) {
      console.log(store.state.isSigned)
      this.userInfo = store.state.userInfo
      this.login = store.state.isSigned;
    }else {
      this.login = false;
    }
  },
  methods: {
    onchargebox() {
      this.openbox = !this.openbox
    },
    onKakao() {
      this.money = this.money * 1;
      const fd = new FormData();
      fd.append("count", this.money);
      console.log(typeof(this.money));
      axios
        .post(`${SERVER_URL}/kakaopay/kakaoPay`, fd)
        .then((response) => {
          // this.asset = response.data
          console.log(response);
          // router.push(response.data)
          this.next = true
          console.log(this.next)
          this.nexturl= response.data
          window.location.href = this.nexturl
        })
        .catch(error=> {
          console.log(error)
        });
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
              this.userInfo.login = true
              this.login = true
              // console.log(this.login)
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
    onLogout() {
      this.login = false
      store.commit('deluserInfo');
    }
  },
}
</script>

<style scoped>
.Navbar {
  height: 50px;
  /* text-align: center; */
  text-align: right;
  line-height: 50px;
}
.serviceName {
  float: left;
  margin-left: 20px !important;
  height: 50px;
  /* font-weight: 600; */
}
.serviceName h3 {
  line-height: 50px;
  font-weight: 600;
  font-size: 30px;
  letter-spacing: 1.2px;
}
.items div {
  display: inline-block;
  margin: 0 10% 0 0;
}
.items div a {
  color: black;
  text-decoration: none;
}
.chargebox {
  position: absolute;
  z-index: 98;
  width: 25%;
  top: 7%;
  right: 0%;
}
.v-card__title {
  margin: 0 !important;
  padding: 20px 0 !important;
  text-align: center;
  width: 100%;
}
.moneyinput {
  margin: 0 !important;
  width: 100%;
  padding: 0 20px;
}
.moneybtns {
  width: 100%;
  margin: 0 !important;
  text-align: center;
}
.chargebtn{
  background: rgb(22, 150, 245);
  color: white !important;
  margin-right: 30px;
  font-weight: 600;
  font-size: 1.25rem;
}
.closebtn {
  background: red;
  color: white !important;
  font-weight: 600;
  font-size: 1.25rem;
}
</style>