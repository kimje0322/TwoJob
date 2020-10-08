<template>
  <div class="myinvestcreate">
    <!-- 상단 Navbar -->
    <HomeNav />
    <div style="border-top: 1px solid lightgray;">
      <div style="padding: 3% 0 2% 10%">
        <h4 style="font-weight: 800">
          <span style="color: rgb(22, 150, 245)">{{ pageusername }}</span
          >님이 생성한 쇼핑 프로젝트
        </h4>
      </div>
      <!-- 프로젝트 메뉴바 -->
      <div style="padding: 0 12%">
        <!-- 프로젝트 메뉴 -->
        <div class="writeMenuBar">
          <div style="padding: 1% 0">
            <div
              v-for="(item, i) in shoppingList"
              :key="i"
              style="display: inline-block; width: 33%; margin-bottom: 20px"
            >
              <v-card
                class="my-12 shadowimg"
                max-width="78%"
                max-height="600px"
                style="margin: auto; margin-bottom: 30px"
                ><router-link
                  style="text-decoration: none"
                  :to="{
                    name: 'ShoppingDetail',
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
                    <span>{{ item.pjtname }}</span></router-link
                  >
                  <!-- <div style="margin-left: auto;"><v-chip class="deadlineBadge">{{item.lastday}}일 남음</v-chip></div> -->
                  <div style="margin-left: auto">
                    <span style="text-align: right">
                      <v-icon left style="color: red" class="mr-0" size="19">
                        mdi-heart
                      </v-icon>
                      {{ item.likecount }}개
                    </span>
                  </div>
                </v-card-title>
                <!-- max-height: 120px -->
                <v-card-text style="">
                  <div style="margin-bottom: 15px; height: 50px">
                    {{ item.oneLineIntro }}
                  </div>
                  <div style="color: black; text-align: right">
                    <h4
                      style="
                        display: inline-block;
                        height: 41.6px;
                        line-height: 41.6px;
                      "
                    >
                      <span style="color: rgb(22, 150, 245)">{{
                        item.saleprice
                      }}</span>
                      원
                    </h4>
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
import HomeNav from "../../components/HomeNav.vue";
import axios from "axios";
import store from "../../store/index.js";
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
      // 쇼핑리스트
      shoppingList: [],
      // 좋아요
      // likecount: "",
      // 한줄소개
      // oneLineIntro: [],
    };
  },
  methods: {
    infiniteHandler($state) {
      const pageid = this.$route.params.userid;
      this.pageuserid = pageid;

      // pageuser가 참가한 투자 프로젝트 가져오기
      axios
        .get(
          `${SERVER_URL}/sale/saleList/${this.page}?userid=${this.pageuserid}`
        )
        .then((response) => {
          setTimeout(() => {
            if (response.data.data == "success") {
              this.shoppingList = response.data.object.object;
              this.totalpage = response.data.object.totalpage;
              this.shoppingList.forEach((item) => {
                const idx = this.shoppingList.indexOf(item);
                this.$set(
                  item,
                  "likecount",
                  response.data.object.likecount[idx]
                );
                this.$set(
                  item,
                  "oneLineIntro",
                  response.data.object.onelineintros[idx]
                );
                // 제목
                if (item.pjtname.length > 10) {
                  item.pjtname = item.pjtname.substring(0, 10) + "...";
                }
                // 한줄소개
                if (item.oneLineIntro.length > 40) {
                  item.oneLineIntro =
                    item.oneLineIntro.substring(0, 40) + "...";
                }
              });
              this.page += 1;
              $state.loaded();
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
  watch: {},
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
.deadlineBadge {
  background-color: rgb(22, 150, 245) !important;
  color: white !important;
  text-align: right;
}
.shadowimg:hover {
  box-shadow: 10px 10px 20px grey;
}
</style>