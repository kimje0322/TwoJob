<template>
  <div class="mypage">
    <!-- 상단 Navbar -->
    <HomeNav />
    <!-- 투자 글쓰기 메뉴바 -->
    <div class="mypagebox" style="background-color: white">
      <!-- user 정보 -->
      <div style="margin-top: 48px; margin-right: 5%">
        <!-- <img :src="userimg" style="height: 100px; border-radius: 50%" /> -->
        <v-avatar style="width: 150px; height: 150px">
          <img :src="pageuserimg" alt="John" />
        </v-avatar>
        <div style="text-align: center; margin-top: 20px">
          <h5>{{ pageusername }} 님</h5>
          <div v-if="pageusername == username">
            <p v-if="iswallet">총 {{ pageuserbalance }} 원</p>
          </div>
          <div style="margin-top: 50px">
            <router-link to="/" style="text-decoration: none">
              <v-btn class="logoutBtn" @click="onLogout">로그아웃</v-btn>
            </router-link>
            <br />
            <v-btn v-if="!iswallet" @click="onWallet" class="walletBtn"
              >지갑생성</v-btn
            >
          </div>
        </div>
      </div>
      <!-- 마이페이지 내용 -->

      <div
        style="
          width: 100%;
          background: rgba(22, 150, 245, 0.1);
          padding: 20px 5%;
          border-radius: 12px;
        "
      >
        <div class="project_info">
          <!-- <h2 class="mypage_title">마이 페이지</h2> -->
          <div class="info_box">
            <div style="padding: 30px">
              <div class="info_frame">
                <ul class="info_ul">
                  <router-link
                    style="color: black; text-decoration: none"
                    :to="{
                      name: 'MyInvestCreate',
                      params: { userid: pageuserid },
                    }"
                  >
                    <li class="info_li">
                      <span class="pjt_span">투자 프로젝트</span>
                      <h2 style="margin-top: 20px">
                        {{ investcreatenum }}
                        <h5 style="display: inline-block">회</h5>
                      </h2>
                    </li>
                  </router-link>
                  <router-link
                    style="color: black; text-decoration: none"
                    :to="{
                      name: 'MyShoppingCreate',
                      params: { userid: pageuserid },
                    }"
                  >
                    <li class="info_li" style="border-left: 2px solid #e9ecef">
                      <span class="pjt_span">쇼핑 프로젝트</span>
                      <h2 style="margin-top: 20px">
                        {{ shoppingcreatenum }}
                        <h5 style="display: inline-block">회</h5>
                      </h2>
                    </li>
                  </router-link>
                </ul>
                <ul class="open_pjt">
                  <a href="/writeinvest" class="opjt_a">
                    <li class="opjt_li" style="width: 100%">
                      투자 프로젝트 생성
                    </li>
                  </a>
                </ul>
              </div>
            </div>

            <div
              v-if="pageusername == username"
              style="margin: 30px 0; overflow: hidden"
            >
              <h3 class="mypage_title">나의 활동</h3>
              <div style="padding: 0 3%">
                <div
                  style="
                    padding: 18px 12px;
                    float: left;
                    width: 50%;
                    border-top: 1px solid #f2f4f6;
                    border-bottom: 1px solid #f2f4f6;
                  "
                >
                  <router-link
                    to="/likelist"
                    style="text-decoration: none; color: black"
                  >
                    <span
                      style="margin-right: 5px; color: red; font-size: 18px"
                    >
                      <i class="fas fa-heart"></i>
                    </span>
                    <span style="font-size: 18px">찜한 목록</span></router-link
                  >
                </div>
                <div
                  style="
                    padding: 18px 12px;
                    float: left;
                    width: 50%;
                    border-top: 1px solid #f2f4f6;
                    border-bottom: 1px solid #f2f4f6;
                  "
                >
                  <v-app class="vapp"></v-app>
                  <div>
                    <v-dialog
                      max-width="800"
                      min-height="500"
                      v-model="chatroom"
                    >
                      <ChatRoom @closeChatRoom="closeChatRoom"></ChatRoom>
                    </v-dialog>
                  </div>
                  <span
                    @click="onChat()"
                    style="
                      margin-right: 5px;
                      color: rgb(22, 150, 245);
                      font-size: 18px;
                      cursor: pointer;
                    "
                  >
                    <i class="fas fa-comments"></i>
                  </span>
                  <!-- <router-link
                            to="/chat"
                            style="text-decoration: none; color: black"
                            > -->
                  <span
                    style="font-size: 18px; cursor: pointer"
                    @click="onChat()"
                    >채팅 목록</span
                  >
                  <!-- </router-link -->
                </div>
              </div>
            </div>
            <div style="margin: 30px 0">
              <div style="overflow: hidden">
                <h3 class="mypage_title" style="float: left">거래내역</h3>
                <router-link to="/#" style="text-decoration: none; color: black"
                  ><span style="float: right">더보기</span></router-link
                >
              </div>
              <div style="margin-top: 18px; padding: 0 3%">
                <div
                  v-for="(item, i) in transactions"
                  :key="i"
                  style="margin-bottom: 15px"
                >
                  <div style="display: flex; margin-left: 3%">
                    <v-avatar
                      style="width: 50px; height: 50px;"
                    >
                      <img :src="item.image" alt="John" />
                    </v-avatar>
                  </div>
                  <div style="display: inline-block; width: 320px">
                    <span
                      style="
                        margin-left: 5%;
                        font-size: 18px;
                        height: 50px;
                        line-height: 50px;
                      "
                      v-if="item.pjtname.length > 20"
                      >{{ item.pjtname.substring(0, 20) }} ...</span
                    >
                    <span
                      style="
                        margin-left: 5%;
                        font-size: 18px;
                        height: 50px;
                        line-height: 50px;
                      "
                      v-else
                      >{{ item.pjtname }}</span
                    >
                    <p style="margin-left: 5%">
                      HASH {{ item.blockhash.substring(0, 25) }}
                      {{ item.blockhash.substring(25, 50) }}
                      {{ item.blockhash.substring(50, item.blockhash.length) }}
                    </p>
                  </div>
                  <div style="display: inline-block; float: right">
                    <p style="margin-left: 44px">{{ item.type }}</p>
                    <v-icon
                      v-if="item.type == 'FUND'"
                      style="
                        float: left;
                        font-size: 16px;
                        height: 50px;
                        line-height: 50px;
                        padding-right: 10px;
                      "
                      >mdi-minus</v-icon
                    >
                    <v-icon
                      v-else
                      style="
                        float: left;
                        font-size: 18px;
                        height: 50px;
                        line-height: 50px;
                        padding-right: 10px;
                      "
                      >mdi-plus</v-icon
                    >
                    <span
                      style="
                        float: right;
                        font-size: 16px;
                        height: 50px;
                        line-height: 50px;
                      "
                      >{{ item.value }} 토큰</span
                    >
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

import store from "../../store/index.js";
import HomeNav from "../../components/HomeNav.vue";
import "@/../public/css/Mypage.scss";
import Web3 from "web3";
import Swal from "sweetalert2";
import ChatRoom from "@/views/mypage/ChatRoom.vue";

const SERVER_URL = "https://www.twojob.ga/api";
// const SERVER_URL = "http://j3b102.p.ssafy.io:8080";

export default {
  methods: {
    closeChatRoom() {
      this.chatroom = false;
    },
    onChat() {
      this.chatroom = true;
      // console.log("모달 열어보자" + this.chatroom);
      // this.$router.push("/chat")
    },
    onLogout() {
      // this.$store.reset()
      store.state.isSigned = false;
      // console.log("로그아웃됨");
      // console.log("store.state.isSigned " + store.state.isSigned);
      // this.$router.push("/");
    },
    onWallet() {
      // var Web3 = require('web3');
      var web3 = new Web3("https://twojob.ga/eth/");

      var Accounts = require("web3-eth-accounts");
      var accounts = new Accounts("https://twojob.ga/eth/");
      var result = web3.eth.accounts.create();
      // console.log(accounts);
      // console.log(result);

      store.commit("setAddress", result.address);

      const fd = new FormData();
      fd.append("accessToken", store.state.accessToken);
      fd.append("address", store.state.address);
      fd.append("privatekey", result.privateKey);
      axios.post(`${SERVER_URL}/wallet/regist`, fd).then((res) => {
        // console.log("wow!!success!!");
        // console.log(res);
        // console.log(fd);
        if (res.data == 401) {
          store.state.isSigned = false;
        } else if (res.data == "success") {
          Swal.fire({
            icon: "success",
            title: "지갑 생성 성공",
            text: `비밀키 : ${result.privateKey}가 발급되었습니다.`,
            // showCancelButton: true,
            // cancelButtonColor: "#d33",
            // confirmButtonColor: "#3085d6",
            confirmButtonText: "확인",
            // cancelButtonText: "취소하기",
          });
        }
      });

      // alert("주소 : " + result.address + " 비밀키 : " + result.privateKey)
    },
  },
  components: {
    HomeNav,
    ChatRoom,
  },
  mounted() {
    var idx = window.location.href.indexOf("mypage");
    // console.log(idx);
    // console.log("url주소 길이" + window.location.href.length);
    const pageid = window.location.href.substring(
      idx + 7,
      window.location.href.length
    );
    // console.log(pageid);
    // console.log(pageid + "asdfadsddd");
    // console.log(typeof pageid);
    const fd = new FormData();
    fd.append("userid", pageid);
    // console.log("pageid" + pageid);
    this.pageuserid = pageid;
    axios.post(`${SERVER_URL}/util/userinfo`, fd).then((res) => {
      // console.log("성공인가??");
      // console.log(res);
      this.pageuserimg = res.data.object.profileImg;
      this.pageusername = res.data.object.name;
      this.pageuseraccestoken = res.data.object.accessToken;
    });

    axios
      .get(`${SERVER_URL}/wallet/toid?oauthid=${this.pageuserid}`)
      .then((res) => {
        // this.pageuserbalance = res.data.balance;
        // console.log(res.data.balance);
        // this.mywallet = res.data.address;
        // console.log("여기여기``");
        // console.log(this.mywallet);
        if (res.data != "novalid") {
          this.iswallet = true;
        }
        // store.commit("setBalance", res.data.balance);
        // store.state.balance = res.data.balance;
        // console.log(store.state.balance + 123123);
      })
      .catch((error) => {
        // console.log(error);
      });

    axios
      .get(`${SERVER_URL}/Token/balance?accessToken=${store.state.accessToken}`)
      .then((res) => {
        // console.log("총 잔액보여줘제발");
        // console.log(res);
        this.pageuserbalance = res.data;
      });

    this.userimg = store.state.userInfo.img;
    this.username = store.state.userInfo.name;
    this.userbalance = store.state.accessToken;
    // console.log("이건 유저 발란스 값" + this.userbalance);

    // 거래내역
    axios
      .get(
        `${SERVER_URL}/mypage/tojalist?direction=ASC&oauthId=${this.pageuserid}&page=0&size=1`
      )
      .then((res) => {
        console.log("거래내역");
        console.log(res.data.totalElements);
        this.transnum = res.data.totalElements;
        if (this.transnum > 0) {
          axios
            .get(
              `${SERVER_URL}/mypage/tojalist?direction=ASC&oauthId=${this.pageuserid}&page=0&size=${this.transnum}`
            )
            .then((res) => {
              this.transactions = res.data.content;
              console.log(this.transactions);
            });
        }
      });

    axios
      .get(`${SERVER_URL}/mypage/myproject?oauthId=${store.state.userInfo.id}`)
      .then((res) => {
        // console.log(res);
        this.investcreatenum = res.data.createNum;
        // this.investinnum = res.data.partInNum;
        this.shoppingcreatenum = res.data.saleNum;
        // this.shoppinginnum = res.data.partInSaleNum;
      });
  },
  data() {
    return {
      transnum: "",
      transactions: [],
      pageuserid: "",
      pageuserimg: "",
      pageusername: "",
      pageuseraccestoken: "",
      pageuserbalance: "",

      investcreatenum: 0,
      investinnum: 0,
      shoppingcreatenum: 0,
      shoppinginnum: 0,
      iswallet: true,
      chatroom: false,
      userimg: "",
      username: "",
      userbalance: "",
      tab: null,
      text: ["1", "2", "3"],
      tabs: ["투자", "쇼핑"],
      // 거래내역
      accounts: [
        { pjtName: "특별한 자전거", transitPrice: "120,0000", isAdded: true },
        { pjtName: "특별한 자전거", transitPrice: "120,0000", isAdded: false },
        { pjtName: "특별한 자전거", transitPrice: "120,0000", isAdded: true },
        { pjtName: "특별한 자전거", transitPrice: "120,0000", isAdded: true },
      ],
    };
  },
  computed: {},
};
</script>


<style scoped>
.items div {
  display: inline-block;
  margin: 0 10% 0 0;
}
.items div a {
  color: black;
  text-decoration: none;
}
.items div a:hover {
  color: rgb(22, 150, 245);
}
.items h5 {
  font-weight: 600;
}
.writeMenu {
  margin-left: 3%;
  letter-spacing: unset !important;
  font-size: 1.25rem;
  border-top-right-radius: 10px;
  border-top-left-radius: 10px;
  text-decoration: none;
}
.writeMenu:hover {
  background-color: white;
  border: 1px solid lightgray;
  border-bottom: none;
}
.v-tab--active {
  background-color: white;
  border: 1px solid lightgray;
  border-bottom: none;
}
.v-tab:before {
  background-color: unset;
}
.v-tabs-items {
  border-radius: 12px;
}
.openbtn {
  line-height: 45px;
  position: absolute;
  right: 1%;
}
.v-card__text {
  padding: 0;
  color: black !important;
}
h5 {
  font-size: 1.15rem;
  font-weight: 600;
  margin-bottom: 1rem;
}
input {
  background-color: white;
  width: 90%;
  height: 40px;
  border: 1px solid lightgray;
  border-radius: 5px;
  margin-left: 10px;
  margin-bottom: 30px;
  padding: 10px;
}
input:hover {
  border: 2px solid rgb(22, 150, 245);
}
.v-input__slot fieldset {
  display: none;
}
.pjtinfo .v-menu {
  display: unset;
}
.startDayBox {
  display: inline-block;
  width: 35%;
}
.tilddIcon {
  display: inline-block;
  margin: 0 9%;
}
.categoryDiv {
  margin-bottom: 28px;
}
.categoryDiv .v-btn {
  width: 105px;
  margin-right: 10px;
  margin-bottom: 10px;
}
.categorybtn:hover {
  border: 2px solid rgb(22, 150, 245);
}
.searchBarBtn {
  border: 1px solid lightgray;
}
.v-card__text {
  /* height: 600px; */
  overflow: hidden;
}
.v-card--flat {
  background-color: transparent;
}
.mypage_title {
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 20px;
}
.info_box {
  box-sizing: border-box;
}
.info_frame {
  background-color: #fff;
  border: 1px solid #cdd3d8;
  border-radius: 12px;
  box-shadow: 0 2px 4px 0 rgba(33, 37, 41, 0.11);
}
.info_ul {
  list-style: none;
  height: 120px;
  margin-top: 15px;
}
.info_li {
  margin-top: 20px;
  display: inline-block;
  width: 50%;
  text-align: center;
}
.pjt_a {
  display: block;
  padding: 20px 0;
  text-decoration: none;
  color: #495057;
}
.pjt_span {
  line-height: 25px;
  font-size: 23px;
  font-weight: 600;
  font-style: normal;
  text-align: center;
}
.open_pjt {
  height: 80px;
  display: block;
  margin: 0;
  list-style: none;
  background-color: #f2f4f6;
  border-radius: 0 0 12px 12px;
}
.opjt_li {
  display: inline-block;
  width: 50%;
  text-align: center;
  padding: 29px 6px;
}
.opjt_a {
  font-size: 18px;
  font-weight: 700;
  letter-spacing: 0;
  font-style: normal;
  color: #495057;
  text-decoration: none;
}
.mypagebox {
  /* background-color: white; */
  display: flex;
  padding: 5% 13%;
  width: 100%;
}
.logoutBtn {
  vertical-align: middle;
  border: 1px solid lightgray;
  border-radius: 15px;
  width: 100%;
  box-shadow: unset;
  background-color: unset;
}
.walletBtn {
  margin-top: 20px;
  border: 1px solid lightgray;
  border-radius: 15px;
  width: 100%;
  box-shadow: unset;
  background-color: rgb(22, 150, 245) !important;
  color: white;
}
.vapp {
  display: none;
}
</style>
