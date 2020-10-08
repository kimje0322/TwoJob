<template>
  <div class="myinvestcreate">
    <!-- 상단 Navbar -->
    <HomeNav />
    <div>
      <div style="padding: 3% 0 2% 10%">
        <h4 style="font-weight: 800">
          <span style="color: rgb(22, 150, 245)">{{ pageusername }}</span
          >님이 생성한 투자 프로젝트
        </h4>
      </div>
      <!-- 프로젝트 메뉴바 -->
      <div style="padding: 0 12%">
        <!-- 프로젝트 메뉴 -->
        <div class="writeMenuBar">
          <div style="padding: 1% 0">
            <div
              v-for="(item, i) in investList"
              :key="i"
              style="display: inline-block; width: 33%; margin-bottom: 20px"
            >
              <v-card
                class="my-12 shadowimg"
                max-width="78%"
                max-height="600px"
                style="margin: auto; margin-bottom: 30px"
              >
                <router-link
                  style="text-decoration: none"
                  :to="{
                    name: 'InvestDetail',
                    params: { address: item.address },
                  }"
                >
                  <v-img height="250" :src="item.picture"></v-img>
                </router-link>
                <v-card-title style="font-weight: 600; margin: auto">
                  <router-link
                    style="color: black"
                    :to="{
                      name: 'InvestDetail',
                      params: { address: item.address },
                    }"
                  >
                    <span>{{ item.pjtName }}</span></router-link
                  >
                  <div style="margin-left: auto">
                    <!-- 종료 -->
                    <v-chip
                      v-if="item.isfinish"
                      class="investPjtBadge"
                      style="background-color: gray; color: white"
                      >종료</v-chip
                    >
                    <v-chip v-else  class="deadlineBadge"
                      >{{ item.lastday }}일 남음</v-chip
                    >
                  </div>
                </v-card-title>
                <!-- max-height: 120px -->
                <v-card-text style="">
                  <div style="margin-bottom: 15px; height: 50px">
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
                      {{ item.investprice }} 원
                    </h5>
                    <div style="display: inline-block; float: right">
                      <h3
                        style="display: inline-block; color: rgb(22, 150, 245)"
                      >
                        {{ item.rate }}%
                      </h3>
                      <h5
                        style="display: inline-block; color: rgb(123, 197, 254)"
                      >
                        달성
                      </h5>
                    </div>
                  </div>
                  <!-- 영수증 등록, 상품 판매 글쓰기 -->
                  <div v-if="userid == pageuserid" style="margin-bottom: 10px; height: 36px">
                    <!-- :disabled="item.isfinish" -->
                    <v-btn
                      :disabled = item.isfail
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
                        <v-card-text>
                          <p style="font-size: 18px">
                            투자금 사용내역 등록하기
                          </p>
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
                                style="display: inline-block; margin-top: 10px"
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
                              <p style="font-size: 25px; text-align: right">
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
                          <v-btn
                            text
                            color="rgb(22, 150, 245)"
                            @click="onreceipt(item)"
                            >등록하기</v-btn
                          >
                        </v-card-actions>
                      </v-card>
                    </v-dialog>
                    <!-- <router-link
                      style="text-decoration: none"
                      :to="{
                        name: 'WriteShopping',
                        params: { address: item.address },
                      }"
                      > -->
                      <!-- #a9a9a9 -->
                      <v-btn
                        @click="towriteshopping(item)"
                        :disabled = item.isfail
                        style="
                          background-color: rgb(22, 150, 245);
                          color: white;
                          width: 40%;
                        "
                        >쇼핑 오픈</v-btn
                      >
                      <!-- </router-link> -->
                  </div>
                </v-card-text>
              </v-card>
            </div>
            <!-- 무한 스크롤 -->
            <infinite-loading @infinite="infiniteHandler" spinner="waveDots">
              <div
                slot="no-more"
                style="
                  color: rgb(102, 102, 102);
                  font-size: 14px;
                  padding: 10px 0px;
                "
              >
                목록의 끝입니다 :)
              </div>
            </infinite-loading>
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
import Web3 from "web3";
import Swal from "sweetalert2";
import "../../../public/css/MyInvestCreate.scss";
import InfiniteLoading from "vue-infinite-loading";

const SERVER_URL = "https://www.twojob.ga/api";

export default {
  components: {
    HomeNav,
    InfiniteLoading,
  },
  data() {
    return {
      // 사용자
      userimg: "",
      username: "",
      userbalance: "",
      userid: "",
      // 페이지 사용자
      pageusername: "",
      pageuserid: "",
      page: 0,
      totalpage: 0,
      // 투자금 사용내역 모달
      receiptPrice: "",
      inputmessage: false,
      receiptDialog: false,
      imgPath: "",
      imgList: [],
      nowref: "",
      // 투자리스트
      investList: [],
    };
  },
  methods: {
    onChangeImages(event) {
      this.file = event.target.files[0];
      var formData = new FormData();
      formData.append("img", this.file);
      axios
        .post(`${SERVER_URL}/investment/changePath`, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((response) => {
          const cutUrl = response.data.substr(18, response.data.length - 17);
          const imgUrl = "https://twojob.ga/" + cutUrl;
          this.imgPath = imgUrl;
          this.imgList.push({ img: imgUrl, price: Number(this.receiptPrice) });
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
    onreceipt(item) {
      item.modal.isopen = false
      const useFund = {
        acceesToken: store.state.accessToken,
        campaignId: item.address,
        list: this.imgList,
      };
      axios.post(`${SERVER_URL}/funding/usefund`, useFund)
        let timerInterval;
        Swal.fire({
          title: `사용내역을 등록하고 있습니다.`,
          timer: 5000,
          timerProgressBar: true,
          onBeforeOpen: () => {
            Swal.showLoading();
            timerInterval = setInterval(() => {
              const content = Swal.getContent();
              if (content) {
                const b = content.querySelector("b");
                if (b) {
                  b.textContent = Swal.getTimerLeft();
                }
              }
            }, 100);
          },
          onClose: () => {
            clearInterval(timerInterval);
          },
        })
        .then((response) => {
          Swal.fire({
            icon: "success",
            title: "",
            text: "사용내역이 성공적으로 등록되었습니다.",
            showConfirmButton: false,
          })
      });
    },
    infiniteHandler($state) {
      const pageid = this.$route.params.userid;
      this.pageuserid = pageid;

      // pageuser가 생성한 투자 프로젝트 가져오기
      axios
        .get(
          `${SERVER_URL}/investment/investList/${this.page}?userid=${this.pageuserid}`
        )
        .then((response) => {
          setTimeout(() => {
            if (response.data.data == "success") {
              this.investList = this.investList.concat(response.data.object);
              this.totalpage = this.investList[0].totalpage
              this.investList.forEach((item) => {
                // 사용내역 모달
                const idx = this.investList.indexOf(item);
                this.$set(item, "modal", {
                  name: `input${idx}`,
                  isopen: false,
                });
                // 제목
                if (item.pjtName.length > 8) {
                  item.pjtName = item.pjtName.substring(0, 8) + "...";
                }
                // 한줄소개
                if (item.oneLineIntro.length > 40) {
                  item.oneLineIntro =
                    item.oneLineIntro.substring(0, 40) + "...";
                }
                // 투자금액 axios 보내기
                axios
                  .get(
                    `${SERVER_URL}/funding/nowfund?campaignId=${item.address}`
                  )
                  .then((response) => {
                    this.$set(item, "investprice", response.data);
                  });
                // 달성률 axios
                const fr = new FormData();
                fr.append("campaignId", item.address);
                axios
                  .post(`${SERVER_URL}/funding/fundingrate`, fr)
                  .then((response) => {
                    this.$set(item, "rate", response.data);
                    // 성공률 기준 issuccess 추가
                    if(response.data >= 100 && item.isfinish){
                      this.$set(item, "isfail", false)
                    }
                    else{
                      this.$set(item, "isfail", true)
                    }
                  });
                // 마감일 기준 남은날짜 계산
                const year = item.deadLine.substring(0, 4);
                const month = item.deadLine.substring(5, 7);
                const day = item.deadLine.substring(8, 10);
                var Dday = new Date(year, month - 1, day);
                var now = new Date();
                var gap = now.getTime() - Dday.getTime();
                var result = Math.floor(gap / (1000 * 60 * 60 * 24)) * -1;
                this.$set(item, "lastday", result);
              });
              $state.loaded();
              this.page += 1;
              if (this.page >= this.totalpage) {
                $state.complete();
              }
            } else {
              $state.complete();
            }
          }, 1000);
        })
        .catch((error) => {
          // console.log(error);
        });
    },
    towriteshopping(item){
      if(item.isfinish){
        this.$router.push(`/writeshopping/${item.address}`)
      }
    }
  },
  mounted() {
    this.userimg = store.state.userInfo.img;
    this.username = store.state.userInfo.name;
    this.userbalance = store.state.balance;
    this.userid = store.state.userInfo.id;
    
    // pageuser 정보 가져오기
    const pageid = this.$route.params.userid;
    this.pageuserid = pageid;
    const fc = new FormData();
    fc.append("userid", this.pageuserid);
    axios.post(`${SERVER_URL}/util/userinfo`, fc).then((response) => {
      this.pageusername = response.data.object.name;
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
  /* border-bottom: 1px solid gray; */
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
  /* border-top-right-radius: 10px;
  border-top-left-radius: 10px; */
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
.deadlineBadge {
  background-color: rgb(22, 150, 245) !important;
  color: white !important;
  text-align: right;
}
.shadowimg:hover {
  box-shadow: 10px 10px 20px grey;
}
</style>