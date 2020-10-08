<template>
<!-- 상단 -->
  <div class="HomeNav">
    <v-app>
      <div >
        <div class="items">
          <div class="serviceName">
            <router-link to="/">
              <h3 class="pt-2">TwoJob</h3>
            </router-link>
          </div><hr>
        </div>
      </div>
  <!-- expand-on-hover  -->
  <!-- drawer -->
  <v-card>
    <v-navigation-drawer
      v-model="drawer" app right
      :mini-variant.sync="mini"
      permanent
      expand-on-hover
    >
      <div v-if="!login">
        <v-list-item class="px-2">
          <v-list-item-avatar>
            <v-img src="https://file3.instiz.net/data/cached_img/upload/2020/02/26/12/f7975c2dacddf8bf521e7e6d7e4c02ee.jpg"></v-img>
          </v-list-item-avatar>
          <v-list-item-title @click="onLogin" class="loginBtn ml-3">로그인</v-list-item-title>
          <v-btn
            icon
            @click.stop="mini = !mini"
          >
            <v-icon>mdi-chevron-left</v-icon>
          </v-btn>
          <!-- 처음 로그인하면 튜토리얼 보여주기 -->
          <div v-if="isnew">
            
          </div>
        </v-list-item>
      </div>
      <div v-if="login">
        <v-list-item class="px-2">
          <v-list-item-avatar>
            <v-img :src="userInfo.img"></v-img>
          </v-list-item-avatar>
          <!-- <div style="display:flex"> -->
          <v-list-item-title class="ml-1">{{ userInfo.name }}님</v-list-item-title>
            
            <!-- 지갑 없는 경우 -->
            <v-btn
              v-if="login && !walletExist"
              @click="onWallet"
              class="WalletBtn ma-2 px-1 py-1 mr-2 mt-2"
              outlined
            >
            <v-icon>mdi-wallet-outline</v-icon>
             지갑생성 
            </v-btn>
            
      <!-- 충전하기 new version -->
      <v-dialog max-width="500" min-height="370" v-model="chargeDialog">
            <v-card flat tile>
              <!-- 충전 모달 --> 
              <v-card-text style="height:220px;" class="pa-1">
                <v-list>
                  <v-toolbar dense elevation="1">
                    <h5 class="mx-auto">충전하기</h5>
                  </v-toolbar>
                  <div style="text-align: center; margin-top: 30px;">
                    <p style="font-size:18px" class="my-2">충전할 금액을 입력해주세요.</p>
                    <!-- <span class="mb-3" style="font-size:13px; color: gray">충전완료까지 시간이 다소 걸릴 수 있습니다.</span> -->
                  </div>
                  <!-- 금액 -->
                  <div>
                    <div style="position:relative">
                    <v-text-field
                      class="ml-50"
                      style="width:50%; position:absolue; left: 25%"
                      v-model="money"
                      hide-details
                      outlined=""
                      type="number"
                    />
                    <div style="position:absolute; bottom: 29%; right: 33%">
                      <span> 토큰</span>
                    </div>
                  </div>
                  </div>
                </v-list>
              </v-card-text>
                </v-card>
                <v-card-actions style="background-color: white; padding: 3px 0 3px 0; justify-content:center"> 
                  <v-btn text @click="onKakao" color="white" style="background-color:rgb(22, 150, 245)">충전</v-btn>
                  <v-btn text @click="chargeDialog=false" style="background-color:#ECEFF1">취소</v-btn>
                </v-card-actions>
                <div style="background-color: white; color:white">　.</div>
              </v-dialog>   
        </v-list-item>
      </div>

      <!-- 지갑있는 경우 잔액, 충전 -->
      <v-divider class="divider"></v-divider>
      <div v-if="walletExist">
        <!-- <i class="fas fa-wallet"></i>  -->
      <v-list-item>
        <v-list-item-icon>
          <v-icon>mdi-wallet-outline</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-list-item-title>{{asset}} 토큰</v-list-item-title>
        </v-list-item-content>
        <v-btn
          @click="chargeDialog = true"
          v-if="walletExist"
          class="chargeBtn ma-2 px-1 py-1 mr-2 mt-2"
          outlined
        >
        <!-- <i class="fas fa-coins"></i>   -->
        <v-icon class="mr-1">mdi-plus-circle-outline</v-icon>
          충전
        </v-btn>
      </v-list-item>
      <v-divider class="divider"></v-divider>
      </div>   
      <!-- 페이지 -->
      <v-list dense>
        <v-list-item
          v-for="item in items"
          :key="item.title"
          link
          @click="move(item.title)"
        >
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
  </v-card>
  </v-app>
</div>
</template>

<script scoped>
import axios from "axios";
import store from "../store/index.js";
import "../../public/css/HomeNav.scss";
import Web3 from "web3";
import Swal from "sweetalert2";

const SERVER_URL = "https://www.twojob.ga/api";
const app_key = "2d3bdff993293b2a8c5a82f963175c8a";
const redirect_uri = "https://www.twojob.ga/api";

export default {
  data () {
    return {
      drawer: true,
      wallet: false,
      walletExist: false,
      items: [
        { title: '투자 프로젝트', icon: 'mdi-lightbulb-on-outline' },
        { title: '쇼핑 프로젝트', icon: 'mdi-basket' },
      ],
      mini: true,
      // 로그인
      userInfo: {
        id: "",
        name: "",
        img: "",
        login: false,
      },
      index: "",
      pg_token: "",
      login: false,
      openbox: false,
      money: "",
      asset: "0",
      next: false,
      nexturl: "",
      // 튜토리얼
      isnew: false,
      // 충전모달
      chargeDialog: false,
    }
  },
  watch: {
    money(val) {
      return (this.money = this.money.replace(/[^0-9]/g, ""));
    }
  },
  mounted() {
    if (store.state.isSigned) {
      this.userInfo = store.state.userInfo;
      this.login = store.state.isSigned;
    } else {
      this.login = false;
    }
    // 지갑 생성 여부 확인
    axios.get(
        `${SERVER_URL}/wallet/toid?oauthid=${store.state.userInfo.id}`)
        .then((res) => {
          if (res.data == "novalid") {
            store.commit("setWalletExist", false);
            store.commit("setAddress", res.data.address);
            this.walletExist = store.state.userInfo.walletExist;
          } else {
            store.commit("setWalletExist", true);
            store.commit("setAddress", null);
            this.walletExist = store.state.userInfo.walletExist;
          }
        })
    if (location.href.includes("pg_token")) {
      // window.opener.closed = true;
      this.index = location.href.indexOf("pg_token");
      this.pg_token = location.href.slice(this.index + 9);
      axios
        .get(
          `${SERVER_URL}/kakaopay/kakaoPayReadySuccess?access_token=${store.state.accessToken}&pg_token=${this.pg_token}&userid=${store.state.userInfo.id}`
        )
        .then((res) => {
          // console.log(res);
        });
      }
    this.asset = store.state.balance;
    // 총 balance
    axios
     .get(`${SERVER_URL}/Token/balance?accessToken=${store.state.accessToken}`)
     .then((res) => {
       this.asset = res.data
     })
  },
  updated() {
    if (this.login && this.items.length == 2) {
        this.items.unshift({ title: '마이페이지', icon: 'mdi-account'},)
        this.items.push({ title: '로그아웃', icon: 'mdi-logout-variant' },)
    }
  },
  methods: {
    onWallet() {
      var web3 = new Web3("https://twojob.ga/eth/");
      var Accounts = require("web3-eth-accounts");
      var accounts = new Accounts("https://twojob.ga/eth/");
      var result = web3.eth.accounts.create();
      store.commit("setAddress", result.address);

      store.state.userInfo.walletAddress = result.address
      const fd = new FormData();
      fd.append("accessToken", store.state.accessToken);
      fd.append("address", store.state.userInfo.walletAddress);
      fd.append("privatekey", result.privateKey);
      axios.post(`${SERVER_URL}/wallet/regist`, fd)
      .then((res) => {
        // console.log(fd);
        if (res.data == 401) {
          store.state.isSigned = false;
        } else if (res.data == "success") {
          this.wallet = store.state.userInfo.walletAddress;
          store.commit("setWalletExist", true)
          this.walletExist = store.state.userInfo.walletExist;
          Swal.fire({
            icon: "success",
            title: "지갑 생성 성공",
            text: `비밀키 : ${result.privateKey}가 발급되었습니다.`,
            confirmButtonText: "확인",
            // cancelButtonText: "취소하기",
          });
        }
      })
    },
    move(title) {
      if (title == '투자 프로젝트') {
        this.$router.push('/investhome');
      } else if (title == '쇼핑 프로젝트') {
        this.$router.push('/shoppinghome');
      } else if (title == '마이페이지') {
        this.$router.push({ name: 'Mypage', params: { userid: this.userInfo.id}})
      } else if (title == '로그아웃'){
        this.onLogout();
      }
    },
    onLogin() {
      window.Kakao.Auth.loginForm({
      success: this.GetMe,
      });
    },
    GetMe(authObj) {
      //토큰값 받아오는 부분
      // console.log(authObj);
      store.state.accessToken = authObj.access_token
      store.commit("setAccessToken", authObj.access_token);
      const fd = new FormData();
      fd.append("accessToken", authObj.access_token);

      axios.post(`${SERVER_URL}/login/kakaologin`, fd)
      .then((res) => {
        this.login = true;
        this.isnew = res.data.isfirsttime
        // store.state.isSigned = true;
        this.userInfo.login = true;
        this.userInfo.id = res.data.oauthId;
        this.userInfo.name = res.data.name;
        // 지갑 정보 조회
        axios.get(
        `${SERVER_URL}/wallet/toid?oauthid=${this.userInfo.id}`)
        .then((res) => {
          if (res.data == "novalid") {
            store.commit("setWalletExist", false);
            this.walletExist = store.state.userInfo.walletExist;
          } else {
            store.commit("setWalletExist", true);
            this.walletExist = store.state.userInfo.walletExist;
          }
        })
        .catch((err) => {
        })
        window.location.reload();
        if (res.data.profileImg == null) {
          this.userInfo.img =
            "https://file3.instiz.net/data/cached_img/upload/2020/02/26/12/f7975c2dacddf8bf521e7e6d7e4c02ee.jpg";
        } else {
          this.userInfo.img = res.data.profileImg;
        }
        store.commit("setUserInfo", this.userInfo);
     });
    },
    onLogout() {
      window.location.reload();
      store.commit("deluserInfo");
      this.login = false;
      this.asset = 0
      this.items = [
          { title: '투자 프로젝트', icon: 'mdi-lightbulb-on-outline' },
          { title: '쇼핑 프로젝트', icon: 'mdi-basket' },
        ]
      // console.log("store.state.isSigned " + store.state.isSigned);
    },
    onChargeDialog() {
      this.chargeDialog = true;
     },
    //  충전
    onchargebox() {
      this.openbox = trueenbox = !this.openbox;
    },
    onKakao() {
      // this.kakopay = true;
      this.money = this.money * 1;
      store.commit("setCharge", this.money);
      const fd = new FormData();
      fd.append("count", this.money);
      fd.append("userid", this.userInfo.id);
      axios
        .post(`${SERVER_URL}/kakaopay/kakaoPay`, fd)
        .then((response) => {
          // console.log(response);
          // router.push(response.data)
          this.next = true;
          this.nexturl = response.data;
          window.location.href = this.nexturl;
        })
        .catch((error) => {
          // console.log(error);
        });
      },
    },
  }
</script>

<style>
.Navbar {
  height: 50px;
  /* text-align: center; */
  text-align: right;
  line-height: 50px;
}
.serviceName {
  /* float: left; */
  margin-left: 20px !important;
  height: 50px;
}
.serviceName h3 {
  font-family: "Lobster", cursive;
  line-height: 50px;
  font-weight: 600;
  font-size: 30px;
  letter-spacing: 1.2px;
}
.items div a {
  color: black;
  text-decoration: none;
}
.divider {
  margin-top: 10px;
}
.loginBtn:hover{
  cursor: pointer;
  /* background-color: rgba(123, 197, 254, 0.8) */
}
.chargeBtn:hover, .WalletBtn:hover {
  background-color: rgb(250, 247, 243) !important;
   /* rgba(123, 197, 254, 0.1) !important; */
}

</style>