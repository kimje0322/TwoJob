<template>
  <!-- 상단 네브바 -->
  <div class="Navbar">
    <v-app>
      <div class="items">
        <div class="serviceName">
          <router-link to="/">
            <h3>TwoJob</h3>
          </router-link>
        </div>
        <div style="text-align: right">
          <div class="navbarItem">
            <router-link to="/investhome">
              <h5 class="navbarmenu">투자하기</h5>
            </router-link>
          </div>
          <div class="navbarItem">
            <router-link to="/shoppinghome">
              <h5 class="navbarmenu">쇼핑하기</h5>
            </router-link>
          </div>
          <div class="navbarItem" v-if="!login">
            <button @click="onLogin">
              <h5 style="margin: 0">로그인</h5>
              <a href="http://developers.kakao.com/logout"></a>
            </button>
          </div>
          <v-row
            class="navbarItem"
            v-if="login"
            justify="center"
            style="display: inline-block"
          >
            <v-menu bottom min-width="200px" rounded offset-y>
              <template v-slot:activator="{ on }">
                <v-btn v-on="on" rounded style="padding: 0">
                  <v-avatar style="width: 35px; height: 35px; margin: 0">
                    <img :src="userInfo.img" alt="John" />
                  </v-avatar>
                  <div style="margin-right: 55px">
                    <h5
                      style="
                        display: inline-block;
                        margin: 0;
                        font-size: 17px;
                        font-weight: 550;
                      "
                    >
                      {{ userInfo.name }}
                    </h5>
                    <span>님</span>
                  </div>
                </v-btn>
              </template>
              <v-card>
                <v-list-item-content class="justify-center">
                  <div class="mx-auto text-center">
                    <v-avatar
                      style="width: 50px; height: 50px; margin-bottom: 15px"
                    >
                      <img :src="userInfo.img" alt="John" />
                    </v-avatar>
                    <h5 style="margin-bottom: 10px">{{ userInfo.name }}</h5>
                    <p
                      v-if="iswallet"
                      class="caption mt-1"
                      style="font-size: 15px !important"
                    >
                      {{ asset }}원
                    </p>
                    <v-divider class="my-2"></v-divider>
                    <router-link to="/">
                      <v-btn depressed rounded text @click="onLogout">
                        로그아웃
                      </v-btn>
                    </router-link>
                    <v-divider class="my-2"></v-divider>
                    <v-btn
                      v-if="iswallet"
                      depressed
                      rounded
                      text
                      @click.stop="chargeDialog = true"
                    >
                      충전하기
                    </v-btn>

                    <v-btn v-else depressed rounded text @click="onWallet">
                      지갑생성
                    </v-btn>
                    <!-- 충전하기 모달 -->
                    <v-dialog
                      v-model="chargeDialog"
                      scrollable
                      max-width="40%"
                      style="height: 400px"
                    >
                      <v-card>
                        <v-card-title
                          class="headline lighten-2"
                          style="padding-bottom: 0 !important"
                        >
                          <h4 style="margin-left: 30px">충전하기</h4>
                        </v-card-title>
                        <v-divider></v-divider>
                        <v-card-text style="padding: 50px 50px 30px 50px">
                          <v-text-field
                            class="moneyinput"
                            v-model="money"
                            label="충전금액"
                            required
                          ></v-text-field>
                        </v-card-text>
                        <!-- <v-divider></v-divider> -->
                        <v-card-actions style="background-color: white">
                          <v-spacer></v-spacer>
                          <v-btn text @click="chargeDialog = false">닫기</v-btn>
                          <v-btn text color="blue" @click="onKakao"
                            >충전하기</v-btn
                          >
                        </v-card-actions>
                      </v-card>
                    </v-dialog>
                    <v-divider class="my-2"></v-divider>
                    <router-link to="/mypage" style="text-decoration: none">
                      <v-btn depressed rounded text> 마이페이지 </v-btn>
                    </router-link>
                  </div>
                </v-list-item-content>
              </v-card>
            </v-menu>
          </v-row>
          <!-- <div class="navbarItem">
            <router-link to="/search">
              <v-btn icon style="flex-right: 0">
                <i style="color: black" class="fas fa-search"></i>
              </v-btn>
            </router-link>
          </div> -->
        </div>
      </div>
    </v-app>
  </div>
</template>


<script scoped>
import axios from "axios";
import store from "../store/index.js";
import "../../public/css/Navbar.scss";
import Web3 from "web3";
import Swal from "sweetalert2";

const SERVER_URL = "https://www.twojob.ga/api";
// const SERVER_URL = "http://j3b102.p.ssafy.io:8080";
const app_key = "2d3bdff993293b2a8c5a82f963175c8a";
const redirect_uri = "https://www.twojob.ga/api";

export default {
  data() {
    return {
      // kakopay: false,
      index: "",
      pg_token: "",
      login: false,
      openbox: false,
      money: "",
      asset: "0",
      next: false,
      nexturl: "",
      userInfo: {
        id: "",
        name: "",
        img: "",
        login: false,
      },
      // 충전 모달
      chargeDialog: false,
      iswallet: false,
    };
  },
  watch: {
    money(val) {
      return (this.money = this.money.replace(/[^0-9]/g, ""));
    },
  },
  mounted() {
    axios
      .get(
        `${SERVER_URL}/wallet/toid?oauthid=${store.state.userInfo.id}`
      )
      .then((res) => {
        console.log(res.data.balance);
        // this.mywallet = res.data.address;
        console.log("여기여기``");
        // console.log(this.mywallet);
        this.iswallet = true;
        store.commit("setBalance", res.data.balance); 
        // store.state.balance = res.data.balance;
        // console.log(store.state.balance + 123123);
      })
      .catch((error) => {
        console.log(error);
      });

    // console.log("네브바 콘솔" + store.state.balance);
    this.asset = store.state.balance;
    console.log(this.asset);
    if (store.state.isSigned) {
      this.userInfo = store.state.userInfo;
      this.login = store.state.isSigned;
      console.log(this.userInfo);
    } else {
      this.login = false;
    }
  },
  methods: {
    onWallet() {
      var web3 = new Web3("http://j3b102.p.ssafy.io:8545");

      var Accounts = require("web3-eth-accounts");
      var accounts = new Accounts("http://j3b102.p.ssafy.io:8545");
      var result = web3.eth.accounts.create();
      console.log(accounts);
      console.log(result);

      store.commit("setAddress", result.address);

      const fd = new FormData();
      fd.append("accessToken", store.state.accessToken);
      fd.append("address", store.state.address);
      fd.append("privatekey", result.privateKey);
      axios.post(`${SERVER_URL}/wallet/regist`, fd).then((res) => {
        console.log("wow!!success!!");
        console.log(res);
        console.log(fd);
        if (res.data == 401) {
          store.state.isSigned = false;
        } else if (res.data == "success") {
          Swal.fire({
            icon: "success",
            title: "지갑 생성 성공",
            text: `비밀키 : ${result.privateKey}가 발급되었습니다.`,
            confirmButtonText: "확인",
          });
        }
      });
    },

    onchargebox() {
      this.openbox = trueenbox = !this.openbox;
    },
    onKakao() {
      // this.kakopay = true;
      this.money = this.money * 1;
      store.commit("setCharge", this.money);
      console.log("vuex에 저장된 충전할 금액은");
      console.log(store.state.charge);
      const fd = new FormData();
      fd.append("count", this.money);
      fd.append("userid", this.userInfo.id);
      axios
        .post(`${SERVER_URL}/kakaopay/kakaoPay`, fd)
        .then((response) => {
          this.next = true;
          this.nexturl = response.data;
          window.location.href = this.nexturl;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    onLogin() {
      window.Kakao.Auth.loginForm({
        success: this.GetMe,
      });
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
        this.userInfo.id = res.data.oauthId;
        this.userInfo.name = res.data.name;
        if (res.data.profileImg == null) {
          this.userInfo.img =
            "https://file3.instiz.net/data/cached_img/upload/2020/02/26/12/f7975c2dacddf8bf521e7e6d7e4c02ee.jpg";
        } else {
          this.userInfo.img = res.data.profileImg;
        }
        store.commit("setUserInfo", this.userInfo);
        // this.userInfo.email = res.data.email;
        console.log("이건 userinfo내용");
        console.log(this.userInfo);
        // this.$router.push("/");
      });
    },
    onLogout() {
      // this.$store.reset()
      console.log("로그아웃됨");
      this.login = false;
      store.commit("deluserInfo");
      console.log("store.state.isSigned " + store.state.isSigned);
      // this.$router.push("/");
    },
    onChargeDialog() {
      console.log("충전모달");
      console.log(this.chargeDialog);
      this.chargeDialog = true;
      console.log(this.chargeDialog);
      // this.chargeDialog = true;
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
  font-family: "Lobster", cursive;
  line-height: 50px;
  font-weight: 600;
  font-size: 30px;
  letter-spacing: 1.2px;
}
.navbarItem {
  display: inline-block;
  margin-right: 5% !important;
  /* height: 50px;
  line-height: 50px; */
}
.navbarItem a h5 {
  height: 50px;
  line-height: 50px;
  margin: 0;
}
.navbarmenu:hover {
  color: rgb(22, 150, 245);
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
  font-size: 1rem !important;
}
.closebtn {
  background: red;
  color: white !important;
  font-weight: 600;
  font-size: 1rem !important;
}
.userimgbox {
  border-radius: 70%;
  width: 55px;
  height: 55px;
  overflow: hidden;
}
.userimg {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>