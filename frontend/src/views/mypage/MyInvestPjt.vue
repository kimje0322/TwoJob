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
                            @click.stop="item.modal.isopen = true"
                            style="
                              background-color: rgb(22, 150, 245);
                              color: white;
                              width: 50%;
                              margin-right: 10%;
                            "
                            >사용내역 등록</v-btn
                          >
                          <!-- 사용내역 모달 -->
                          <v-dialog
                            v-model="item.modal.isopen"
                            scrollable
                            max-width="30%"
                            style="height: 400px"
                          >
                            <v-card>
                              <v-card-title
                                class="headline lighten-2"
                                style="padding-bottom: 0 !important"
                              >
                                <h4 style="margin-bottom: 0">
                                  {{ item.pjtName }}
                                </h4>
                              </v-card-title>
                              <v-divider></v-divider>
                              <v-card-text >
                                <p style="font-size: 18px;">투자금 사용내역 등록하기</p>
                                <div style="padding: 50px 30px 30px">
                                  <!-- 이미지 총 금액 -->
                                  <div
                                    style="
                                      height: 50px;
                                      display: flex;
                                      justify-content: center;
                                    "
                                  >
                                    <input
                                      v-model="receiptPrice"
                                      type="text"
                                      placeholder="사용 금액을 입력해주세요."
                                      style="margin-bottom: 0"
                                    />
                                    <p
                                      style="
                                        font-size: 28px;
                                        display: inline-block;
                                        margin-bottom: 0;
                                        margin-left: 10px;
                                        padding-top: 13px;
                                      "
                                    >
                                      원
                                    </p>
                                  </div>
                                  <p
                                    v-if="inputmessage"
                                    style="
                                      text-align: left;
                                      margin-left: 30px;
                                      color: red;
                                    "
                                  >
                                    * 필수 항목입니다.
                                  </p>
                                  <!-- 이미지 업로드 버튼 -->
                                  <input
                                    :class="item.modal.name"
                                    type="file"
                                    @change="onChangeImages"
                                    hidden
                                  />
                                  <div
                                    @click="onClickImageUpload(item.modal.name)"
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
                                  <div
                                    v-show="imgList.length > 0"
                                    v-for="(item, i) in imgList"
                                    :key="i"
                                    style="position: relative"
                                  >
                                  <hr />
                                    <v-btn
                                      x-small
                                      dark
                                      fab
                                      absolute
                                      top
                                      right
                                      color="black"
                                      style="
                                        top: 10px;
                                        right: 0px;
                                        opacity: 0.6;
                                        background-color: #272727 !important;
                                        color: white;
                                        position: absolute;
                                      "
                                      @click="onDeleteImg(item)"
                                    >
                                      <v-icon dark>mdi-close</v-icon>
                                    </v-btn>
                                    <img
                                      class="mx-auto mt-3"
                                      ref="img"
                                      style="
                                        width: 87%;
                                        border-radius: 10px;
                                        margin-bottom: 30px;
                                      "
                                      :src="item.img"
                                      alt="reviewImg"
                                    />
                                    <p
                                      style="font-size: 25px; text-align: right"
                                    >
                                      총 : {{ item.price }}원
                                    </p>
                                    <hr />
                                  </div>
                                </div>
                              </v-card-text>
                              <v-divider></v-divider>
                              <v-card-actions style="background-color: white">
                                <v-spacer></v-spacer>
                                <v-btn text @click="item.modal.isopen = false"
                                  >닫기</v-btn
                                >
                                <v-btn text color="rgb(22, 150, 245)">등록하기</v-btn>
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
      receiptPrice: "",
      inputmessage: false,
      receiptDialog: false,
      imgPath: "",
      imgList: [],
      nowref: "",
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
      console.log(event);
      this.file = event.target.files[0];
      var formData = new FormData();
      formData.append("img", this.file);
      axios
        .post(`${SERVER_URL}/investment/changePath`, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((response) => {
          const cutUrl = response.data.substr(18, response.data.length - 17);
          const imgUrl = "http://j3b102.p.ssafy.io/" + cutUrl;
          this.imgPath = imgUrl;
          this.imgList.push({ img: imgUrl, price: this.receiptPrice });
          this.receiptPrice = "";
        });
    },
    onClickImageUpload(item) {
      if (this.receiptPrice) {
        this.inputmessage = false;
        $(`.${item}`).click();
      } else {
        this.inputmessage = true;
      }
    },
    onDeleteImg(item) {
      const idx = this.imgList.indexOf(item);
      this.imgList.splice(idx, 1);
    },
    // ondialog(item, i) {
    //   console.log(item)
    //   console.log(i)
    //   // console.log(item.)
    //   // item.i = !item.i
    // }
  },
  components: {
    Navbar,
  },
  mounted() {
    this.userimg = store.state.userInfo.img;
    this.username = store.state.userInfo.name;
    this.userbalance = store.state.balance;
    this.userid = store.state.userInfo.id;


    var idx = window.location.href.indexOf("pjt");
    console.log(idx);
    const pageid = window.location.href.substring(
      idx + 4,
      window.location.href.length
    );
    console.log(pageid);

    // axios
    // console.log(pageid + "asdfadsddd");
    // console.log(typeof pageid);
    


    axios
      .get(
        `${SERVER_URL}/investment/investList/${this.page}?userid=${pageid}`
      )
      .then((response) => {
        console.log("페이지");
        console.log(response);
        this.investList = response.data.object;
        this.investList.forEach((item) => {
          const idx = this.investList.indexOf(item);
          this.$set(item, "modal", { name: `input${idx}`, isopen: false });
        });
      });
  },
  computed: {},
  watch: {
    receiptPrice(val) {
      if (this.receiptPrice) {
        this.inputmessage = false;
        // 숫자만 입력가능
        return (this.receiptPrice = this.receiptPrice.replace(/[^0-9]/g, ""));
      } else {
        this.inputmessage = true;
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
  background-color: #e1f5fe;
  width: 60%;
  padding: 5px 0px 12px 0px;
  border-radius: 15px;
  margin: auto;
}
.reviewImg:hover {
  cursor: pointer;
}
</style>