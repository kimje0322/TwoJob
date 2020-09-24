<template>
  <div class="home">
    <!-- 상단 Navbar -->
    <navbar />
    <!-- 투자 글쓰기 메뉴바 -->
    <div style="max-width: 1000px; max-height: 1000px; margin: 0 auto;">
      <!-- 투자 글쓰기 메뉴 -->
      <div class="writeMenuBar" style="background-color: white !important;">
        <v-tabs v-model="tab" class="elevation-2" dark hide-slider>
          <v-tab
            style="color: black;"
            v-for="(item, i) in tabs"
            :key="i"
            :href="`#tab-${i}`"
            class="writeMenu"
          >{{ item }}</v-tab>
          <!-- 프로젝트 정보 창 -->
          <v-tab-item :value="'tab-0'">
            <v-card flat tile>
              <v-card-text>
                <!-- style="float: left; padding: 50px 20px 0; width: 200px; box-sizing: border-box;" -->
                <div>
                  <!-- <div style="width: 55px; display: inline-block; margin: 0 auto 25px;"> -->
                  <div style="margin-top: 40px; float: left;">
                    <img :src="userimg" style="height: 100px; border-radius: 50%" />
                    <div style="text-align: center; margin-top: 20px;">
                      <h5>{{username}} 님</h5>
                      <p>총 {{userbalance}}.0 원</p>
                      <router-link to="/">
                        <v-btn
                          @click="onLogout"
                          style="vertical-align: middle; background-color: mintcream;"
                        >로그아웃</v-btn>
                      </router-link>
                      <br>
                      <v-btn @click="onWallet" style="margin-top: 20px">지갑생성</v-btn>   
                    </div>
                  </div>
                  <div class="project_info">
                    <h3 class="mypage_title">마이페이지</h3>
                    <div class="info_box">
                      <div class="info_frame">
                        <ul class="info_ul">
                          <li class="info_li">
                            <a href="/writeinvest" class="pjt_a">
                              <span class="pjt_span">
                                투자 프로젝트
                                <h3 style="padding-top: 7px;">
                                  XX
                                  <h5 style="display: inline-block;">회</h5>
                                </h3>
                              </span>
                            </a>
                          </li>
                          <li class="info_li" style="border-left: 2px solid #e9ecef;">
                            <a href="#" class="pjt_a">
                              <span class="pjt_span">
                                판매 프로젝트
                                <h3 style="padding-top: 7px;">
                                  XX
                                  <h5 style="display: inline-block;">회</h5>
                                </h3>
                              </span>
                            </a>
                          </li>
                        </ul>
                        <ul class="open_pjt">
                          <li class="opjt_li">
                            <a href="/writeinvest" class="opjt_a">투자 프로젝트 생성</a>
                          </li>
                          <li class="opjt_li">
                            <a href="#" class="opjt_a">판매 프로젝트 생성</a>
                          </li>
                        </ul>
                      </div>
                      <div style="margin-top: 15px;">
                        <div
                          style="padding: 18px 12px; float: left; width: 50%; border-top: 2px solid #f2f4f6; border-bottom: 2px solid #f2f4f6;"
                        >
                          <span style="margin-right: 5px;">
                            <i class="far fa-heart fa-lg"></i>
                          </span>
                          <strong style="font-size: 17px;">찜 한 목록</strong>
                        </div>
                        <div
                          style="padding: 18px 12px; float: left; width: 50%; border-top: 2px solid #f2f4f6; border-bottom: 2px solid #f2f4f6;"
                        >
                          <span style="margin-right: 5px;">
                            <i class="far fa-comments fa-lg"></i>
                          </span>
                          <strong style="font-size: 17px;">1 : 1 문의</strong>
                        </div>
                      </div>
                      <div style="margin: 100px 20px 0;">
                        <h4 style="display: inline-block; font-size: 17px; font-weight: 600; ">거래내역</h4>
                        <span style="float: right">더보기</span>
                        <div style="margin-top: 18px;">
                          <div>
                            <img
                              :src="userimg"
                              style="height: 40px; border-radius: 50%; display: inline-block;"
                            />
                            <span style="margin-left: 100px;">프로젝트 명</span>
                            <span style="float: right">+ 120,0000 ETH</span>
                          </div>
                          <div style="margin-top: 15px;">
                            <img
                              :src="userimg"
                              style="height: 40px; border-radius: 50%; display: inline-block;"
                            />
                            <span style="margin-left: 100px;">프로젝트 명</span>
                            <span style="float: right">+ 120,0000 ETH</span>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- <div style="display: inline-block; margin-left: 200px;"></div> -->
              </v-card-text>
            </v-card>
          </v-tab-item>
          <!-- 금손 정보 창 -->
          <v-tab-item :value="'tab-1'">
            <v-card flat tile>
              <v-card-text>
                <div>
                  <!-- <div style="width: 55px; display: inline-block; margin: 0 auto 25px;"> -->
                  <div style="margin-top: 40px; float: left;">
                    <img :src="userimg" style="height: 100px; border-radius: 50%" />
                    <div style="text-align: center; margin-top: 20px;">
                      <h5>{{username}} 님</h5>
                      <p>총 {{userbalance}}.0 원</p>
                      <router-link to="/">
                        <v-btn
                          @click="onLogout"
                          style="vertical-align: middle; background-color: mintcream;"
                        >로그아웃</v-btn>
                      </router-link>
                    </div>
                  </div>
                  <div class="project_info">
                    <h3 class="mypage_title">마이페이지</h3>
                    <div class="info_box">
                      <div class="info_frame">
                        <ul class="info_ul">
                          <li class="info_li">
                            <a href="/writeinvest" class="pjt_a">
                              <span class="pjt_span">
                                투자하기
                                <h3 style="padding-top: 7px;">
                                  XX
                                  <h5 style="display: inline-block;">회</h5>
                                </h3>
                              </span>
                            </a>
                          </li>
                          <li class="info_li" style="border-left: 2px solid #e9ecef;">
                            <a href="#" class="pjt_a">
                              <span class="pjt_span">
                                판매하기
                                <h3 style="padding-top: 7px;">
                                  XX
                                  <h5 style="display: inline-block;">회</h5>
                                </h3>
                              </span>
                            </a>
                          </li>
                        </ul>
                        <ul class="open_pjt">
                          <li class="opjt_li">
                            <a href="/writeinvest" class="opjt_a">투자 프로젝트 생성</a>
                          </li>
                          <li class="opjt_li">
                            <a href="#" class="opjt_a">판매 프로젝트 생성</a>
                          </li>
                        </ul>
                      </div>
                      <div style="margin-top: 15px;">
                        <div
                          style="padding: 18px 12px; float: left; width: 50%; border-top: 2px solid #f2f4f6; border-bottom: 2px solid #f2f4f6;"
                        >
                          <span style="margin-right: 5px;">
                            <i class="far fa-heart fa-lg"></i>
                          </span>
                          <strong style="font-size: 17px;">찜 한 목록</strong>
                        </div>
                        <div
                          style="padding: 18px 12px; float: left; width: 50%; border-top: 2px solid #f2f4f6; border-bottom: 2px solid #f2f4f6;"
                        >
                          <span style="margin-right: 5px;">
                            <i class="far fa-comments fa-lg"></i>
                          </span>
                          <strong style="font-size: 17px;">1 : 1 문의</strong>
                        </div>
                      </div>
                      <div style="margin: 100px 20px 0;">
                        <h4 style="display: inline-block; font-size: 17px; font-weight: 600; ">거래내역</h4>
                        <span style="float: right">더보기</span>
                        <div style="margin-top: 18px;">
                          <div>
                            <img
                              :src="userimg"
                              style="height: 40px; border-radius: 50%; display: inline-block;"
                            />
                            <span style="margin-left: 100px;">프로젝트 명</span>
                            <span style="float: right">+ 120,0000 ETH</span>
                          </div>
                          <div style="margin-top: 15px;">
                            <img
                              :src="userimg"
                              style="height: 40px; border-radius: 50%; display: inline-block;"
                            />
                            <span style="margin-left: 100px;">프로젝트 명</span>
                            <span style="float: right">+ 120,0000 ETH</span>
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
  </div>
</template>

<script>
// import Vue from 'vue'
import axios from "axios";
// import router from "@/routes";

// import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
// import 'bootstrap/dist/css/bootstrap.css'
// import 'bootstrap-vue/dist/bootstrap-vue.css'
// import "../../../public/css/Home.scss";
import store from "../../store/index.js";
import Navbar from "../../components/Navbar.vue";
// import "@/../public/css/WriteInvest.scss";
import "@/../public/css/Mypage.scss"
import Web3 from "web3";
import Swal from "sweetalert2";

const SERVER_URL = "http://j3b102.p.ssafy.io:8080";

export default {
  methods: {
    onLogout() {
      store.state.isSigned = false;
      console.log("로그아웃됨");
      console.log("store.state.isSigned " + store.state.isSigned);
      // this.$router.push("/");
    },
    onWallet() {
      // var Web3 = require('web3');
      var web3 = new Web3('http://localhost:8545');

      var Accounts = require('web3-eth-accounts');
      var accounts = new Accounts('http://localhost:8545');
      var result = web3.eth.accounts.create();
      console.log(accounts)
      console.log(result)

      store.commit("setAddress", result.address)
      
      const fd = new FormData();
      fd.append("accessToken", store.state.accessToken);
      fd.append("address", store.state.address);
      fd.append("privatekey", result.privateKey);
      axios
        .post(`${SERVER_URL}/wallet/regist`, fd)
        .then((res) => {
          console.log("wow!!success!!")
          console.log(res)
          console.log(fd)
          if(res.data == 401){
            store.state.isSigned = false;
          }
          else if (res.data == 'success'){
            Swal.fire({
              icon: "success",
              title: "지갑 생성 성공",
              text: `비밀키 : ${result.privateKey}가 발급되었습니다.`,
              showCancelButton: true,
              cancelButtonColor: "#d33",
              // confirmButtonColor: "#3085d6",
              // confirmButtonText: "오픈하기",
              cancelButtonText: "취소하기",
            })
          }
        })

    
      // alert("주소 : " + result.address + " 비밀키 : " + result.privateKey)
    }
  },
  components: {
    Navbar,
  },
  mounted() {
    this.userimg = store.state.userInfo.img;
    this.username = store.state.userInfo.name;
    this.userbalance = store.state.balance;
  },
  data() {
    return {
      userimg: "",
      username: "",
      userbalance: "",
      tab: null,
      text: ["1", "2", "3"],
      tabs: ["큰손", "금손"],
      title: "",
      content: "",
      // 날짜
      date1: "",
      dateFormatted1: "",
      date2: "",
      dateFormatted2: "",
      menu1: false,
      menu2: false,
      targetPrice: 0,
      receivePrice: 0,
      // 사진
      rules: [
        (value) =>
          !value ||
          value.size < 2000000 ||
          "Tunbnail size should be less than 2 MB!",
      ],
      select: "",
      openMenutab: false,
      individual: false,
      business: false,
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
  padding: 16px 5%;
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
  height: 600px;
}
.v-card--flat {
  background-color: rgba(173, 220, 254, 0.4);
}
.project_info {
  margin-left: 50px;
  float: left;
}
.mypage_title {
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 15px;
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
  width: 600px;
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
  font-size: 18px;
  font-weight: 600;
  font-style: normal;
  text-align: center;
}
.open_pjt {
  width: 600px;
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
  font-size: 15px;
  font-weight: 700;
  letter-spacing: 0;
  font-style: normal;
  color: #495057;
}
.v-slide-group__content {
  background-color: white !important;
}
.v-tabs-bar__content {
  background-color: white !important;
}
</style>