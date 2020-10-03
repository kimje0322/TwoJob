<template>
  <!-- 상단 네브바 -->
  <div class="Navbar">
    <div class="items">
      <div class="serviceName">
        <router-link to="/">
          <h3>TwoJob</h3>
        </router-link>
      </div>
      <div class="navbarItem">
        <router-link to="/investhome">
          <h5>투자하기</h5>
        </router-link>
      </div>
      <div class="navbarItem">
        <router-link to="/shoppinghome">
          <h5>쇼핑하기</h5>
        </router-link>
      </div>
      <div class="navbarItem" v-if="!login">
        <button @click="onClick">
          <h5 style="margin: 0">로그인</h5>
          <a href="http://developers.kakao.com/logout"></a>
        </button>
      </div>
      <v-row
        class="navbarItem"
        v-else
        justify="center"
        style="display: inline-block; width: 150px"
      >
        <v-menu bottom min-width="200px" rounded offset-y>
          <template v-slot:activator="{ on }">
            <v-btn v-on="on">
              <v-avatar style="width: 35px; height: 35px; margin: 0">
                <img :src="userInfo.img" alt="John" />
              </v-avatar>
              <div>
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
                <p class="caption mt-1">{{ asset }}원</p>
                <v-divider class="my-2"></v-divider>
                <v-btn depressed rounded text @click="onLogout">
                  로그아웃
                </v-btn>
                <v-divider class="my-2"></v-divider>
                <div>
                  <v-btn depressed rounded text @click="onChargeDialog">
                    충전하기
                  </v-btn>
                  <!-- 충전하기 모달 -->
                  <v-dialog  max-width="640" min-height="500" v-model="chargeDialog">
                    <v-card-actions style="background-color: white">
                      <v-spacer></v-spacer>
                      <v-btn text @click="chargeDialog = false">취소</v-btn>
                      <v-btn text color="blue">충전하기</v-btn>
                    </v-card-actions>
                  </v-dialog>
                </div>
                <v-divider class="my-2"></v-divider>
                <router-link :to="{ name: 'Mypage', params: { userid : userInfo.id }}">
                  <v-btn depressed rounded text> 마이페이지 </v-btn>
                </router-link>
              </div>
            </v-list-item-content>
          </v-card>
        </v-menu>
      </v-row>
      <!-- 원래 모달 -->
      <!-- <div class="chargebox" style="inline-block" v-if="openbox">
          <v-card style="padding: 0; margin: 0">
            <v-icon style="margin-right: 10px">mdi-close</v-icon>
            <v-card-title class="headline">
              {{ userInfo.name }}님의 자산 현황
            </v-card-title>
            <h5 style="text-align: center">{{ asset }}원</h5>
            <v-text-field class="moneyinput" v-model="money" label="충전금액" required></v-text-field>

            <v-card-actions class="moneybtns">
              <v-spacer></v-spacer>
              <v-btn class="chargebtn" text @click="onKakao">
                충전하기
              </v-btn>
              <v-btn class="closebtn" text @click="openbox = false">닫기</v-btn>
            </v-card-actions>
            <div style="text-align: center; width: 100%; padding: 0 2px">
              <router-link to="/mypage">
                <v-btn
                  @click="openbox = false"
                  style="
                    width: 100%;
                    background: rgb(22, 150, 245) !important;
                    color: white;
                  "
                >마이페이지</v-btn>
              </router-link>
            </div>
          </v-card>
        </div> -->
      <!-- </v-row> -->
      <div class="navbarItem">
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
    };
  },
  mounted() {
    // console.log(location.href);
    // console.log("이거봐라라ㅏㅏㅏ???");
    // console.log(location.href.includes("pg_token"));
    if (location.href.includes("pg_token")) {
      //     window.opener.closed = true;
      this.index = location.href.indexOf("pg_token");
      this.pg_token = location.href.slice(this.index + 9);
      console.log("pg_token 이다ㅏㅏ");
      console.log(this.pg_token);
      //아래와 같은 코드가 필요
      //if(this.index!=-1){
      console.log("충전할 금액은");
      console.log(store.state.charge);
      console.log(store.state.userInfo.id);
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
  methods: {
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
    onClick() {
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
        console.log("이게뭐냐면");
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
      this.chargeDialog = !this.chargeDialog;
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