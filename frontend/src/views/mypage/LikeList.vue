<template>
  <div class="myinvestpjt">
    <!-- 상단 Navbar -->
    <navbar style="border-bottom: 1px solid lightgray" />
    <div style="padding: 1% 10%">
      <h3 style="font-weight: 800">찜한 프로젝트</h3>
    </div>
    <!-- 프로젝트 메뉴바 -->
    <div style="padding: 0 10%">
      <!-- 프로젝트 메뉴 -->
      <div class="writeMenuBar">
        <v-tabs v-model="tab" class="elevation-2" dark hide-slider>
          <v-tab
            style="color: black"
            v-for="(item, i) in tabs"
            :key="i"
            :href="`#tab-${i}`"
            class="writeMenu"
            >{{ item }}</v-tab
          >
          <!-- 투자 좋아요 프로젝트 창 -->
          <v-tab-item :value="'tab-0'">
            <v-card flat tile>
              <v-card-text>
                <!-- style="float: left; padding: 50px 20px 0; width: 200px; box-sizing: border-box;" -->
                <div style="padding: 1% 0">
                  <div
                    v-for="(item, i) in investlikelst"
                    :key="i"
                    style="
                      display: inline-block;
                      width: 33%;
                      margin: 20px 5px;
                    "
                  >
                    <v-card
                      class="my-12"
                      max-width="90%"
                      max-height="600px"
                      style="margin: auto"
                    >
                      <router-link
                        :to="{
                          name: 'InvestDetail',
                          params: { address: item.address },
                        }"
                      >
                        <v-img height="250" :src="item.picture"></v-img>
                      </router-link>
                      <v-card-title
                        style="
                          font-weight: 600;
                          margin: auto;
                          color: rgba(0, 0, 0, 0.87);
                          font-family: BPreplayExtended;
                        "
                      >
                        <p v-if="item.pjtname.length > 6">
                          {{ item.pjtname.substring(0, 8) }} ...
                        </p>
                        <p v-else style="width: 100%">
                          {{ item.pjtname }}
                        </p>
                        <div style="margin-left: auto">
                          <v-chip class="projectBadge">
                            {{ item.lastday }}일 남음</v-chip
                          >
                          <!-- <v-chip v-model="a" class="likeBadge" style="font-size: 12px"
                            >100명 좋아요 {{ b }}</v-chip
                          > -->
                        </div>
                      </v-card-title>
                      <!-- max-height: 120px -->
                      <v-card-text style="height: 70px">
                        <div
                          style="
                            margin-bottom: 10px;
                            font-size: 0.875rem;
                            font-weight: 400;
                            line-height: 1.375rem;
                            letter-spacing: 0.0071428571em;
                            color: rgb(0, 0, 0, 0.6);
                          "
                        >
                          {{ item.onelineintro }}
                        </div>
                        <!-- <div style="color: black">
                        </div>                         -->
                      </v-card-text>
                    </v-card>
                  </div>
                </div>
              </v-card-text>
            </v-card>
          </v-tab-item>
          <!-- 쇼핑 좋아요 프로젝트 창 -->
          <v-tab-item :value="'tab-1'">
            <v-card flat tile>
              <v-card-text>
                <!-- style="float: left; padding: 50px 20px 0; width: 200px; box-sizing: border-box;" -->
                <div style="padding: 1% 0">
                  <div
                    v-for="(item, i) in shopplinglikelst"
                    :key="i"
                    style="
                      display: inline-block;
                      width: 33%;
                      margin-bottom: 20px;
                    "
                  >
                    <v-card
                      class="my-12"
                      max-width="90%"
                      max-height="600px"
                      style="margin: auto"
                    >
                      <router-link
                        :to="{
                          name: 'ShoppingDetail',
                          params: { address: item.address },
                        }"
                      >
                        <v-img height="250" :src="item.picture"></v-img>
                      </router-link>
                      <v-card-title
                        style="
                          font-weight: 600;
                          margin: auto;
                          color: rgba(0, 0, 0, 0.87);
                          font-family: BPreplayExtended;
                        "
                      >
                        <p v-if="item.pjtname.length > 6">
                          {{ item.pjtname.substring(0, 8) }} ...
                        </p>
                        <p v-else>
                          {{ item.pjtname }}
                        </p>
                        <!-- <div style="margin-left: auto">
                          <v-chip class="likeBadge" style="font-size: 12px"
                            >100명 좋아요</v-chip
                          >
                        </div> -->
                      </v-card-title>
                      <!-- max-height: 120px -->
                      <v-card-text style="height: 70px">
                        <div
                          style="
                            margin-bottom: 10px;
                            font-size: 0.875rem;
                            font-weight: 400;
                            line-height: 1.375rem;
                            letter-spacing: 0.0071428571em;
                            color: rgb(0, 0, 0, 0.6);
                          "
                        >
                          {{ item.onelineintro }}
                        </div>
                        <!-- <div style="color: black">
                        </div>                         -->
                      </v-card-text>
                    </v-card>
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
import axios from "axios";
import store from "../../store/index.js";
import Navbar from "../../components/Navbar.vue";
import Web3 from "web3";
import Swal from "sweetalert2";
import "../../../public/css/MyInvestPjt.scss";

const SERVER_URL = "https://www.twojob.ga/api";
// const SERVER_URL = "http://j3b102.p.ssafy.io:8080";

// let today = new Date();

export default {
  data() {
    return {
      dtime: "",
      userid: "",
      page: 0,
      // 투자금 사용내역 모달
      receiptDialog: false,
      uploadimg: false,
      imgPath: "",
      // 투자리스트
      investList: [],
      userimg: "",
      username: "",
      userbalance: "",
      tab: null,
      text: ["1", "2", "3"],
      tabs: ["투자", "쇼핑"],
      today: null,

      investlikelst: [],
      shopplinglikelst: [],
    };
  },
  methods: {},
  components: {
    Navbar,
  },
  mounted() {
    //  남은 기간
    // this.today = new Date();
    // this.today.setDate()
    // this.today =

    this.userimg = store.state.userInfo.img;
    this.username = store.state.userInfo.name;
    this.userbalance = store.state.balance;
    this.userid = store.state.userInfo.id;

    const fd = new FormData();
    fd.append("userid", store.state.userInfo.id);
    axios.post(`${SERVER_URL}/mypage/likelist`, fd).then((response) => {
      console.log("여기요!");
      console.log(response);

      this.investlikelst = response.data.object.investmentList;
      this.shopplinglikelst = response.data.object.saleboardList;
      console.log("investlikelst");
      console.log(this.investlikelst);
      console.log(this.shopplinglikelst);
      this.investlikelst.forEach((invlst) => {
        console.log("asdf");
        const year = invlst.deadline.substring(0, 4);
        const month = invlst.deadline.substring(5, 7);
        const day = invlst.deadline.substring(8, 10);

        var Dday = new Date(year, month - 1, day);
        var now = new Date();
        var gap = now.getTime() - Dday.getTime();
        var result = Math.floor(gap / (1000 * 60 * 60 * 24)) * -1;
        this.$set(invlst, "lastday", result);
        console.log("남은날짜 " + invlst.lastday);
      });
    });
  },

  computed: {},
  method: {},
  watch: {},
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
  padding: 0 16px 5px 16px;
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

.tilddIcon {
  display: inline-block;
  margin: 0 9%;
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
.v-card--flat {
  background-color: rgba(173, 220, 254, 0.4);
}

.v-slide-group__content {
  background-color: white !important;
}
.v-tabs-bar__content {
  background-color: white !important;
}
.likeBadge {
  background-color: red !important;
  color: white !important;
  text-align: right;
}
.theme--dark.v-tabs > .v-tabs-bar {
  background-color: unset;
}
.reviewImg {
  background-color: #e1f5fe;
  width: 60%;
  padding: 5px 0px 12px 0px;
  border-radius: 15px;
  margin: auto;
}
.reviewImg:hover {
  cursor: pointer;
}
.projectBadge {
  background-color: rgb(22, 150, 245) !important;
  color: white !important;
  text-align: right;
}
</style>