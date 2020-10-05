<template>
<!-- 상단 -->
  <div class="Navbar">
    <v-app>
      <div >
        <div class="items">
          <div class="serviceName">
            <router-link to="/">
              <h3>TwoJob</h3>
            </router-link>
          </div><hr>
        </div>
      </div>
  
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
            <v-img src="https://randomuser.me/api/portraits/men/85.jpg"></v-img>
          </v-list-item-avatar>
          <v-list-item-title @click="onLogin" class="loginBtn ml-3">로그인</v-list-item-title>
          <v-btn
            icon
            @click.stop="mini = !mini"
          >
            <v-icon>mdi-chevron-left</v-icon>
          </v-btn>
        </v-list-item>
      </div>
      <div v-if="login">
        <v-list-item class="px-2">
          <v-list-item-avatar>
            <v-img :src="userInfo.img"></v-img>
          </v-list-item-avatar>
          <!-- <div style="display:flex"> -->
          <v-list-item-title class="ml-3">{{ userInfo.name }}님</v-list-item-title>
            <v-btn
              class="chargeBtn ma-2 px-1 py-1 mr-2 mt-2"
              outlined
            >
            <!-- <i class="fas fa-coins"></i>   -->
            <v-icon class="mr-1" @click="chargeDialog = true">mdi-plus-circle-outline</v-icon>
             충전
            </v-btn>

        <!-- 충전하기 모달 -->
          <v-dialog
            v-model="chargeDialog"
            scrollable
            max-width="40%"
            style="height: 400px"
          >
            <v-card>
              <v-card-title class="headline lighten-2" style="padding-bottom: 0 !important">
                <h4 style="margin-left: 30px">충전하기</h4>
              </v-card-title>
              <v-divider></v-divider>
              <v-card-text style="padding: 50px 50px 30px 50px">
                <v-text-field class="moneyinput" v-model="money" label="충전금액" required></v-text-field>
              </v-card-text>
              <!-- <v-divider></v-divider> -->
              <v-card-actions style="background-color: white">
                <v-spacer></v-spacer>
                <v-btn text @click="chargeDialog = false">닫기</v-btn>
                <v-btn text color="blue" @click="onKakao">충전하기</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-list-item>
      </div>

      <v-divider class="divider"></v-divider>

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

const SERVER_URL = "https://www.twojob.ga/api";
const app_key = "2d3bdff993293b2a8c5a82f963175c8a";
const redirect_uri = "https://www.twojob.ga/api";

export default {
    data () {
      return {
        drawer: true,
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
    if (location.href.includes("pg_token")) {
      //     window.opener.closed = true;
      this.index = location.href.indexOf("pg_token");
      this.pg_token = location.href.slice(this.index + 9);
      axios
        .get(
          `${SERVER_URL}/kakaopay/kakaoPayReadySuccess?access_token=${store.state.accessToken}&pg_token=${this.pg_token}&userid=${store.state.userInfo.id}`
        )
        .then((res) => {
          console.log(res);
        });
      //}
    }
    this.asset = store.state.balance;
    if (store.state.isSigned) {
      this.userInfo = store.state.userInfo;
      this.login = store.state.isSigned;
      console.log(this.userInfo);
    } else {
      this.login = false;
    }
  },
  updated() {
    if (this.login && this.items.length == 2) {
        this.items.unshift({ title: '마이페이지', icon: 'mdi-account'},)
        this.items.push({ title: '로그아웃', icon: 'mdi-logout-variant' },)
    }
  },
  methods: {
    move(title) {
      if (title == '투자 프로젝트') {
        this.$router.push('/investhome');
      } else if (title == '쇼핑 프로젝트') {
        this.$router.push('/shoppinghome');
      } else if (title == '마이페이지') {
        this.$router.push('/mypage');
      } else if (title == '로그아웃'){
        this.onLogout();
      }
    },
    onLogin() {
      window.Kakao.Auth.loginForm({
      success: this.GetMe,
      });
      console.log('아이템s길이'+this.items.length)
      // if ({ title: '쇼핑 프로젝트', icon: 'mdi-basket' } in this.items) {
      //   console.log('이미있어요')
      // } else {
      //   console.log('없어요')
      // }
    },
    GetMe(authObj) {
      //토큰값 받아오는 부분
      console.log('authObj입니다');
      console.log(authObj);
      console.log(authObj.access_token);
      store.commit("setAccessToken", authObj.access_token);
      const fd = new FormData();
      fd.append("accessToken", authObj.access_token);

      axios.post(`${SERVER_URL}/login/kakaologin`, fd).then((res) => {
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
        console.log(this.userInfo);
        // this.$router.push("/");
     });
    },
    onLogout() {
      store.commit("deluserInfo");
      this.login = false;
      console.log("로그아웃됨");
      this.items = [
          { title: '투자 프로젝트', icon: 'mdi-lightbulb-on-outline' },
          { title: '쇼핑 프로젝트', icon: 'mdi-basket' },
        ]
      // console.log("store.state.isSigned " + store.state.isSigned);
    },
    onChargeDialog() {
      console.log("충전모달");
      console.log(this.chargeDialog);
      this.chargeDialog = true;
      console.log(this.chargeDialog);
      // this.chargeDialog = true;
     },
    //  충전
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
          console.log(response);
          // router.push(response.data)
          this.next = true;
          console.log("이건 넥스트");
          console.log(this.next);
          this.nexturl = response.data;
          window.location.href = this.nexturl;
        })
        .catch((error) => {
          console.log(error);
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
.loginBtn:hover {
  cursor: pointer;
}

</style>