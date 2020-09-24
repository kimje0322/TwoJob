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

          <!-- <a id="kakao-login-btn"></a> -->
          <!-- <a href="http://developers.kakao.com/logout"></a> -->
          <a href="http://developers.kakao.com/logout"></a>
        </button>
      </div>
      <v-row v-else style="display: inline-block; width: 150px; ">
        <!-- <v-btn @click="onchargebox"> -->
          <v-btn @click.stop="openbox = true">
          <!-- <i class="fas fa-user fa-lg"></i> -->
          <span style="width: 35px; height: 35px;">
            <img :src="userInfo.img" style="height: 100%; border-radius: 50%" />
          </span>
          <h5
            style="display: inline-block; margin: 0; padding-left: 10px; font-size: 17px; font-weight: 550;"
          >{{ userInfo.name }}</h5>
          <span>님</span>
        </v-btn>
        <div class="chargebox" style="inline-block" v-if="openbox">
          <v-card style="padding: 0; margin: 0">
            <v-card-title class="headline">{{userInfo.name}}님의 자산 현황  <br>  {{asset}}원</v-card-title>
            <v-text-field class="moneyinput" v-model="money" label="충전금액" required></v-text-field>
            <v-card-actions class="moneybtns">
              <v-spacer></v-spacer>
              <v-btn class="chargebtn" text @click="onKakao">
                충전하기
                <!-- <div style="inline-block" v-if="kakaopay">
                  <v-card>

                  </v-card>
                </div>-->
              </v-btn>
              <v-btn class="closebtn" text @click="openbox = false">닫기</v-btn>
            </v-card-actions>
            <div style="text-align: center; width: 100%; padding: 0 2px">
              <router-link to="/mypage">
                <v-btn @click="openbox = false" style="width: 100%; background: rgb(22, 150, 245) !important; color: white;">마이페이지</v-btn>
              </router-link>
            </div>
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


<script scoped>
import axios from "axios";
import store from "../store/index.js";
import "../../public/css/Navbar.scss";

const SERVER_URL = "http://j3b102.p.ssafy.io:8080";
const app_key = "2d3bdff993293b2a8c5a82f963175c8a";
const redirect_uri = "http://j3b102.p.ssafy.io:8080";

export default {
  data() {
    return {
      // kakopay: false,
      login: false,
      openbox: false,
      money: "",
      asset: "0",
      next: false,
      nexturl: "",
      userInfo: {
        email: "",
        name: "",
        img: "",
        login: false,
      },
    };
  },
  mounted() {
    console.log(location.href)
    this.asset = store.state.balance + ".0";
    // this.asset = "100"
    console.log("여기여기여기여기");
    console.log(this.userInfo);
    if (store.state.isSigned) {
      console.log(store.state.isSigned);
      console.log(store.state.userInfo);
      this.userInfo = store.state.userInfo;
      this.login = store.state.isSigned;
      console.log(this.userInfo);
    } else {
      this.login = false;
    }
    // let externalScript = document.createElement('script')
    // externalScript.setAttribute('src', 'https://developers.kakao.com/sdk/js/kakao.min.js')
    // document.head.appendChild(externalScript)
  },
  methods: {
    onchargebox() {
      this.openbox = trueenbox = !this.openbox;
    },
    onKakao() {
      // this.kakopay = true;

      this.money = this.money * 1;
      const fd = new FormData();
      fd.append("count", this.money);
      console.log(typeof this.money);
      axios
        .post(`${SERVER_URL}/kakaopay/kakaoPay`, fd)
        .then((response) => {
          console.log(response);
          // router.push(response.data)
          this.next = true;
          console.log(this.next);
          this.nexturl = response.data;
          window.location.href = this.nexturl;
          // window.open(
          //   this.nexturl,
          //   "토큰 충전",
          //   "width=500,height=500,left=600"
          // );
          console.log("여기를봐라")
          console.log(response)
          console.log(window.location.href)
        })
        .catch((error) => {
          console.log(error);
        });
    },
    onClick() {
      window.Kakao.Auth.loginForm({
        success: this.GetMe,
        
      });
      // onclick() {
      //   Kakao.init('a98b416e875c32a2c8aa2bc5da03103f');
      //   Kakao.Auth.createLoginButton({
      //     container: "#kakao-login-btn",
      //     success: function (authObj) {
      //       alert(JSON.stringify(authObj));
      //     },
      //     fail:  function (err) {
      //       alert(JSON.stringify(err));
      //     },
      //   });
    },
    GetMe(authObj) {
      console.log(authObj);
      //토큰값 받아오는 부분
      console.log(authObj.access_token);
      store.commit("setAccessToken", authObj.access_token);
      const fd = new FormData();
      fd.append("accessToken", authObj.access_token);

      axios.post(`${SERVER_URL}/login/kakaologin`, fd).then((res) => {
        console.log("여기여기");
        console.log(res);
        this.login = true;
        // store.state.isSigned = true;
        this.userInfo.login = true;
        this.userInfo.name = res.data.name;
        this.userInfo.img = res.data.profileImg;
        store.commit("setUserInfo", this.userInfo);
        // this.userInfo.email = res.data.email;
        console.log("이게뭐냐면");
        console.log(this.userInfo);
        // this.$router.push("/");
      });
    },
  },
};
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
  font-family: 'Lobster', cursive;
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
.chargebtn {
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