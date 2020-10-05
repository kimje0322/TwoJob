<template>
  <div class="myinvestpjt">
    <!-- 상단 Navbar -->
    <navbar style="border-bottom: 1px solid lightgray" />
    <div style="padding: 1% 10%">
      <h3 style="font-weight: 800">나의 프로젝트</h3>
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
          <!-- 금손 프로젝트 창 -->
          <v-tab-item :value="'tab-0'">
            <v-card flat tile>
              <v-card-text>
                <!-- style="float: left; padding: 50px 20px 0; width: 200px; box-sizing: border-box;" -->
                <div style="padding: 1% 0">
                  <div
                    v-for="(item, i) in investList"
                    :key="i"
                    style="
                      display: inline-block;
                      width: 33%;
                      margin-bottom: 20px;
                    "
                  >
                    <v-card
                      class="my-12"
                      max-width="75%"
                      max-height="600px"
                      style="margin: auto"
                    >
                      <v-img height="250" :src="item.picture"></v-img>
                      <v-card-title style="font-weight: 600; margin: auto">
                        {{ item.pjtName }}
                        <div style="margin-left: auto">
                          <v-chip class="likeBadge" style="font-size: 12px"
                            >100명 좋아요</v-chip
                          >
                        </div>
                      </v-card-title>
                      <!-- max-height: 120px -->
                      <v-card-text style="">
                        <div style="margin-bottom: 15px">
                          {{ item.oneLineIntro }}
                        </div>
                        <div style="color: black">
                          <h5
                            style="
                              display: inline-block;
                              height: 41.6px;
                              line-height: 41.6px;
                            "
                          >
                            {{ item.goalPrice }} 원
                          </h5>
                          <div style="display: inline-block; float: right">
                            <h3
                              style="
                                display: inline-block;
                                color: rgb(22, 150, 245);
                              "
                            >
                              {{ item.percent }}%
                            </h3>
                            <h5
                              style="
                                display: inline-block;
                                color: rgb(123, 197, 254);
                              "
                            >
                              달성
                            </h5>
                          </div>
                        </div>
                        <!-- 영수증 등록, 상품 판매 글쓰기 -->
                        <div style="margin-bottom: 10px">
                          <v-btn
                            @click.stop="receiptDialog = true"
                            style="
                              background-color: rgb(22, 150, 245);
                              color: white;
                              width: 50%;
                              margin-right: 10%;
                            "
                            >사용내역 등록</v-btn
                          >
                          <!-- 충전하기 모달 -->
                          <v-dialog
                            v-model="receiptDialog"
                            scrollable
                            max-width="40%"
                            style="height: 400px"
                          >
                            <v-card>
                              <v-card-title
                                class="headline lighten-2"
                                style="padding-bottom: 0 !important"
                              >
                                <h4 style="margin-left: 30px">
                                  투자금 사용내역 등록하기
                                </h4>
                              </v-card-title>
                              <v-divider></v-divider>
                              <v-card-text style="padding: 50px 50px 30px 50px">
                                <!-- 이미지 업로드 버튼 -->
                                <input
                                  ref="imageInput"
                                  type="file"
                                  @change="onChangeImages"
                                  hidden
                                />
                                <div
                                  v-if="!uploadimg"
                                  @click="$refs.imageInput.$el.click()"
                                  class="reviewImg mx-auto mt-5"
                                >
                                  <v-icon>mdi-camera-plus-outline</v-icon>
                                  <span
                                    style="
                                      display: inline-block;
                                      margin-top: 10px;
                                    "
                                    >사진 첨부하기</span
                                  >
                                </div>
                                <!-- 이미지 첨부된 경우 -->
                                <!-- 이미지 삭제 버튼 -->
                                <div v-show="imgPath">
                                  <v-btn
                                    x-small
                                    dark
                                    fab
                                    absolute
                                    top
                                    right
                                    color="black"
                                    style="
                                      top: 293px;
                                      right: 47px;
                                      opacity: 0.6;
                                    "
                                    @click="onDeleteImg"
                                  >
                                    <v-icon dark>mdi-close</v-icon>
                                  </v-btn>
                                  <img
                                    class="mx-auto mt-3"
                                    ref="img"
                                    style="width: 87%; border-radius: 10px"
                                    :src="imgPath"
                                    alt="reviewImg"
                                  />
                                </div>
                              </v-card-text>
                              <!-- <v-divider></v-divider> -->
                              <v-card-actions style="background-color: white">
                                <v-spacer></v-spacer>
                                <v-btn text @click="receiptDialog = false"
                                  >닫기</v-btn
                                >
                              </v-card-actions>
                            </v-card>
                          </v-dialog>
                          <router-link
                            :to="{
                              name: 'WriteShopping',
                              params: { address: item.address },
                            }"
                            ><v-btn
                              style="
                                background-color: #a9a9a9;
                                color: white;
                                width: 40%;
                              "
                              >쇼핑 오픈</v-btn
                            ></router-link
                          >
                        </div>
                      </v-card-text>
                    </v-card>
                  </div>
                </div>
              </v-card-text>
            </v-card>
          </v-tab-item>
          <!-- 큰손 프로젝트 창 -->
          <v-tab-item :value="'tab-1'">
            <v-card flat tile>
              <v-card-text></v-card-text>
            </v-card>
          </v-tab-item>
          <!-- 판매 프로젝트 창 -->
          <v-tab-item :value="'tab-2'">
            <v-card flat tile>
              <v-card-text></v-card-text>
            </v-card>
          </v-tab-item>
          <!-- 구매 프로젝트 창 -->
          <v-tab-item :value="'tab-3'">
            <v-card flat tile>
              <v-card-text></v-card-text>
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

export default {
  data() {
    return {
      userid: "",
      page: 0,
      // 투자금 사용내역 모달
      receiptDialog: false,
      uploadimg: false,
      imgPath: '',
      // 투자리스트
      investList: [],
      userimg: "",
      username: "",
      userbalance: "",
      tab: null,
      text: ["1", "2", "3"],
      tabs: ["큰손", "금손", "판매", "구매"],
    };
  },
  methods: {
    onLogout() {
      store.state.isSigned = false;
      console.log("로그아웃됨");
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
    onChangeImages(event) {
      this.uploadimg = true;
      console.log(event)
      this.file = event.target.files[0];
      var formData = new FormData();
      formData.append("img", this.file);
      axios.post(`${SERVER_URL}/investment/changePath`, formData, { 
                headers: { 'Content-Type': 'multipart/form-data' } 
        }).then(response => {
            const cutUrl = response.data.substr(18, response.data.length-17)
            const imgUrl = 'http://j3b102.p.ssafy.io/' + cutUrl
            this.imgPath = imgUrl;
        });
    },
    onClickImageUpload() {
      this.$refs.imageInput.$el.click();
    },
    onDeleteImg() {
      this.uploadimg = false;
    }
  },
  components: {
    Navbar,
  },
  mounted() {
    this.userimg = store.state.userInfo.img;
    this.username = store.state.userInfo.name;
    this.userbalance = store.state.balance;
    this.userid = store.state.userInfo.id;
    axios
      .get(
        `${SERVER_URL}/investment/investList/${this.page}?userid=${this.userid}`
      )
      .then((response) => {
        console.log("나의 투자프로젝트 페이지")
        console.log(response);
        this.investList = response.data.object;
      });
  },
  
  computed: {},
  method: {
    init() {
      // 나의 프로젝트 내역 가져오기
      axios
        .get(
          `${SERVER_URL}/investment/investList?userid=${store.state.userInfo.id}`
        )
        .then((res) => {
          console.log(res);
          this.investlst = res.data.object;
        });
    },
  },
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
.likeBadge {
  background-color: red !important;
  color: white !important;
  text-align: right;
}
.theme--dark.v-tabs > .v-tabs-bar {
  background-color: unset;
}
.reviewImg {
  background-color: #E1F5FE;
  width: 60%;
  padding: 5px 0px 12px 0px;
  border-radius: 15px;
  margin: auto;
}
.reviewImg:hover {
  cursor: pointer;
}
</style>