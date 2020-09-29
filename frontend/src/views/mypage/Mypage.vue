<template>
  <div class="mypage">
    <!-- 상단 Navbar -->
    <navbar style="border: 1px solid lightgray" />
    <!-- 투자 글쓰기 메뉴바 -->
    <div class="mypagebox">
      <!-- user 정보 -->
      <div style="margin-top: 45px;  margin-right: 5%">
        <!-- <img :src="userimg" style="height: 100px; border-radius: 50%" /> -->
        <v-avatar style="width: 150px; height: 150px">
          <img :src="userimg" alt="John" />
        </v-avatar>
        <div style="text-align: center; margin-top: 20px;">
          <h5>{{username}} 님</h5>
          <p>총 {{userbalance}}.0 원</p>
          <div style="margin-top: 50px;">
            <router-link to="/" style="text-decoration: none;">
              <v-btn class="logoutBtn" @click="onLogout">로그아웃</v-btn>
            </router-link>
            <br />
            <v-btn @click="onWallet" class="walletBtn">지갑생성</v-btn>
          </div>
        </div>
      </div>
      <!-- 마이페이지 내용 -->
      <v-tabs v-model="tab" class="elevation-2" dark hide-slider>
        <v-tab
          style="color: black;"
          v-for="(item, i) in tabs"
          :key="i"
          :href="`#tab-${i}`"
          class="writeMenu"
        >{{ item }}</v-tab>
        <!-- 투자 마이페이지 -->
        <v-tab-item :value="'tab-0'">
          <v-card flat tile>
            <v-card-text>
              <div>
                <div class="project_info">
                  <h3 class="mypage_title">투자 페이지</h3>
                  <div class="info_box">
                    <div style="padding: 0 3%; margin-bottom: 30px">
                      <div class="info_frame">
                        <ul class="info_ul">
                          <li class="info_li">
                            <a href="/myinvestpjt" class="pjt_a">
                              <span class="pjt_span">생성한 프로젝트</span>
                              <h2 style="margin-top: 20px;">
                                XX
                                <h5 style="display: inline-block;">회</h5>
                              </h2>
                            </a>
                          </li>
                          <li
                            class="info_li"
                            style="border-left: 2px solid #e9ecef"
                          >
                            <a href="#" class="pjt_a">
                              <span class="pjt_span">참여한 프로젝트</span>
                              <h2 style="margin-top: 20px;">
                                XX
                                <h5 style="display: inline-block;">회</h5>
                              </h2>
                            </a>
                          </li>
                        </ul>
                        <ul class="open_pjt">
                          <li class="opjt_li">
                            <a href="/writeinvest" class="opjt_a"
                              >투자 프로젝트 생성</a
                            >
                          </li>
                          <li class="opjt_li">
                            <a href="#" class="opjt_a">쇼핑 프로젝트 생성</a>
                          </li>
                        </ul>
                      </div>
                    </div>
                    <div style="margin: 30px 0; overflow: hidden">
                      <h3 class="mypage_title">나의 활동</h3>
                      <div style="padding: 0 3%">
                        <div
                          style="padding: 18px 12px; float: left; width: 50%; border-top: 1px solid #f2f4f6; border-bottom: 1px solid #f2f4f6;"
                        >
                          <span style="margin-right: 5px; color: red; font-size: 18px;">
                            <i class="fas fa-heart"></i>
                          </span>
                          <router-link to="/#" style="text-decoration: none; color: black"><span style="font-size: 18px;">찜한 목록</span></router-link>
                        </div>
                        <div
                          style="padding: 18px 12px; float: left; width: 50%; border-top: 1px solid #f2f4f6; border-bottom: 1px solid #f2f4f6;"
                        >
                          <span
                            style="margin-right: 5px; color: rgb(22, 150, 245); font-size: 18px;"
                          >
                            <i class="fas fa-comments"></i>
                          </span>
                          <router-link to="/chat" style="text-decoration: none; color: black"><span style="font-size: 18px;">채팅 목록</span></router-link>
                        </div>
                      </div>
                    </div>
                    <div style="margin: 30px 0">
                      <div style="overflow: hidden">
                        <h3 class="mypage_title" style="float: left">거래내역</h3>
                        <router-link to="/#" style="text-decoration: none; color: black"><span style="float: right">더보기</span></router-link>
                      </div>
                      <div style="margin-top: 18px; padding: 0 3%">
                        <div v-for="(item, i) in accounts" :key="i" style="margin-bottom: 15px;">
                          <v-avatar style="width: 50px; height: 50px">
                            <img :src="userimg" alt="John" />
                          </v-avatar>
                          <span style="margin-left: 5%; font-size: 18px; height: 50px; line-height: 50px">{{item.pjtName}}</span>
                          <div style="display: inline-block; float: right">
                            <v-icon v-if ="item.isAdded" style="float: left; font-size: 16px; height: 50px; line-height: 50px; padding-right: 10px;">mdi-plus</v-icon>
                            <v-icon v-else style="float: left; font-size: 18px; height: 50px; line-height: 50px; padding-right: 10px;">mdi-minus</v-icon>
                            <span style="float: right; font-size: 16px; height: 50px; line-height: 50px;">{{item.transitPrice}} ETH</span>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </v-card-text>
          </v-card>
        </v-tab-item>
        <!-- 쇼핑 마이페이지 -->
        <v-tab-item :value="'tab-1'">
          <v-card flat tile>
            <v-card-text>
              <div>
                <div class="project_info">
                  <h3 class="mypage_title">쇼핑 페이지</h3>
                  <div class="info_box">
                    <div style="padding: 0 3%; margin-bottom: 30px">
                      <div class="info_frame">
                        <ul class="info_ul">
                          <li class="info_li">
                            <a href="/writeinvest" class="pjt_a">
                              <span class="pjt_span">생성한 프로젝트</span>
                              <h2 style="margin-top: 20px;">
                                XX
                                <h5 style="display: inline-block;">회</h5>
                              </h2>
                            </a>
                          </li>
                          <li
                            class="info_li"
                            style="border-left: 2px solid #e9ecef"
                          >
                            <a href="#" class="pjt_a">
                              <span class="pjt_span">참여한 프로젝트</span>
                              <h2 style="margin-top: 20px;">
                                XX
                                <h5 style="display: inline-block;">회</h5>
                              </h2>
                            </a>
                          </li>
                        </ul>
                        <ul class="open_pjt">
                          <li class="opjt_li">
                            <a href="/writeinvest" class="opjt_a"
                              >투자 프로젝트 생성</a
                            >
                          </li>
                          <li class="opjt_li">
                            <a href="#" class="opjt_a">쇼핑 프로젝트 생성</a>
                          </li>
                        </ul>
                      </div>
                    </div>
                    <div style="margin: 30px 0; overflow: hidden">
                      <h3 class="mypage_title">나의 활동</h3>
                      <div style="padding: 0 3%">
                        <div
                          style="padding: 18px 12px; float: left; width: 50%; border-top: 1px solid #f2f4f6; border-bottom: 1px solid #f2f4f6;"
                        >
                          <span style="margin-right: 5px; color: red; font-size: 18px;">
                            <i class="fas fa-heart"></i>
                          </span>
                          <router-link to="/#" style="text-decoration: none; color: black"><span style="font-size: 18px;">찜한 목록</span></router-link>
                        </div>
                        <div
                          style="padding: 18px 12px; float: left; width: 50%; border-top: 1px solid #f2f4f6; border-bottom: 1px solid #f2f4f6;"
                        >
                          <span
                            style="margin-right: 5px; color: rgb(22, 150, 245); font-size: 18px;"
                          >
                            <i class="fas fa-comments"></i>
                          </span>
                          <router-link to="/chat" style="text-decoration: none; color: black"><span style="font-size: 18px;">채팅 목록</span></router-link>
                        </div>
                      </div>
                    </div>
                    <div style="margin: 30px 0">
                      <div style="overflow: hidden">
                        <h3 class="mypage_title" style="float: left">거래내역</h3>
                        <router-link to="/#" style="text-decoration: none; color: black"><span style="float: right">더보기</span></router-link>
                      </div>
                      <div style="margin-top: 18px; padding: 0 3%">
                        <div v-for="(item, i) in accounts" :key="i" style="margin-bottom: 15px;">
                          <v-avatar style="width: 50px; height: 50px">
                            <img :src="userimg" alt="John" />
                          </v-avatar>
                          <span style="margin-left: 5%; font-size: 18px; height: 50px; line-height: 50px">{{item.pjtName}}</span>
                          <div style="display: inline-block; float: right">
                            <v-icon v-if ="item.isAdded" style="float: left; font-size: 16px; height: 50px; line-height: 50px; padding-right: 10px;">mdi-plus</v-icon>
                            <v-icon v-else style="float: left; font-size: 18px; height: 50px; line-height: 50px; padding-right: 10px;">mdi-minus</v-icon>
                            <span style="float: right; font-size: 16px; height: 50px; line-height: 50px;">{{item.transitPrice}} ETH</span>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </v-card-text>
          </v-card>
        </v-tab-item>
      </v-tabs>
    </div>
  </div>
</template>

<script>
import axios from "axios";

// import Vuetify from 'vuetify'

import store from "../../store/index.js";
import Navbar from "../../components/Navbar.vue";
import "@/../public/css/Mypage.scss";
import Web3 from "web3";
import Swal from "sweetalert2";

import ChatRoom from "@/views/mypage/ChatRoom.vue";

const SERVER_URL = "http://j3b102.p.ssafy.io:8080";

export default {
  methods: {
    closeChatRoom() {
      this.chatroom = false;
    },
    onChat() {
      // window.open("");
      this.chatroom = true;
      console.log("모달 열어보자" + this.chatroom)
      // this.$router.push("/chat")
    },
    onLogout() {
      // this.$store.reset()
      console.log("로그아웃됨");
      store.state.isSigned = false;
      console.log("store.state.isSigned " + store.state.isSigned);
      // this.$router.push("/");
    },
    onWallet() {
      // var Web3 = require('web3');
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
    Navbar,
    ChatRoom,
  },
  mounted() {
    this.userimg = store.state.userInfo.img;
    this.username = store.state.userInfo.name;
    this.userbalance = store.state.balance;
  },
  data() {
    return {
      chatroom: false,
      chat_dialog: false,
      room_dialog: false,
      chat_lst: ["user1", "user2", "user3"],

      userimg: "",
      username: "",
      userbalance: "",
      tab: null,
      text: ["1", "2", "3"],
      tabs: ["투자", "쇼핑"],
      // 거래내역
      accounts: [
        {pjtName: "특별한 자전거", transitPrice: "120,0000", isAdded: true},
        {pjtName: "특별한 자전거", transitPrice: "120,0000", isAdded: false},
        {pjtName: "특별한 자전거", transitPrice: "120,0000", isAdded: true},
        {pjtName: "특별한 자전거", transitPrice: "120,0000", isAdded: true},
      ]
    };
  },
  computed: {},
  watch: {
    date1(val) {
      this.dateFormatted1 = this.formatDate(this.date1);
    },
    date2(val) {
      this.dateFormatted2 = this.formatDate(this.date2);
    },
    model(val, prev) {
      if (val.length === prev.length) return;
      this.model = val.map((v) => {
        if (typeof v === "string") {
          v = { text: `#${v}` };
          this.items.push(v);
          this.nonce++;
        }
        return v;
      });
    },
    select(val) {
      if (val == "개인") {
        this.individual = true;
        this.business = false;
      } else {
        this.business = true;
        this.individual = false;
      }
    },
  },
};
</script>


<style scoped>
.investNav {
  height: 50px;
  text-align: center;
  line-height: 50px;
  border-bottom: 1px solid gray;
  margin-bottom: 15px;
}
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
}
.writeMenu:hover {
  background-color: rgba(173, 220, 254, 0.4);
}
.v-tab--active {
  background-color: rgba(173, 220, 254, 0.4);
}
.v-tab:before {
  background-color: unset;
}
.openbtn {
  line-height: 45px;
  position: absolute;
  right: 1%;
}
.v-card__text {
  padding: 16px 10%;
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
#introduce {
  background-color: white;
  border: 1px solid lightgray;
  border-radius: 5px;
  resize: none;
  padding: 8px;
  margin: 0 0 20px 10px;
}
#introduce:hover {
  border: 2px solid rgb(22, 150, 245);
}
.v-card__text {
  /* height: 600px; */
  overflow: hidden;
}
.v-card--flat {
  background-color: rgba(173, 220, 254, 0.4);
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
  font-size: 20px;
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
  display: flex;
  padding: 3% 10%;
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
</style>