<template>
  <div class="myinvestcreate">
    <!-- 상단 Navbar -->
    <HomeNav />
    <div>
      <div style="padding: 3% 0 2% 10%">
        <h4 style="font-weight: 800">
          <span style="color: rgb(22, 150, 245)">{{ username }}</span
          >님 리뷰를 작성해주세요!
        </h4>
        <div style="height: 100vh;">
          아직 참여한 쇼핑 프로젝트가 없습니다.
          쇼핑 프로젝에 참여하고 리뷰를 작성해보세요!
        </div>
        <div style="text-align: end; margin-right: 70px">
          <v-btn @click="openReviewDialog(-1)" outlined> 리뷰 작성 </v-btn>
        </div>
      <!-- 리뷰 모달 -->
      <v-dialog max-width="640" min-height="500" v-model="reviewDialog">
        <ShoppingReview @closeReviewDialog="closeReviewDialog"></ShoppingReview>
        <v-card-actions style="background-color: white;">
          <v-spacer></v-spacer>
          <v-btn text @click="reviewDialog=false">취소</v-btn>
          <v-btn text color="blue">등록</v-btn>
        </v-card-actions>
    </v-dialog>    
    

      
      </div>
      <!-- 프로젝트 메뉴바 -->
      
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
// 쇼핑 리뷰
import ShoppingReview from "@/views/shopping/ShoppingReview.vue";

const SERVER_URL = "https://www.twojob.ga/api";

export default {
  components: {
    HomeNav,
    ShoppingReview,
  },
  data() {
    return {
      // 사용자
      userimg: "",
      username: "",
      userbalance: "",
      userid: "",
      // axio parameter
      page: 0,
      size: 9,
      // 쇼핑리스트
      shoppingList: [],
    };
  },
   methods: {
    openReviewDialog() {
      this.reviewDialog = true;
    },
    closeReviewDialog() {
      this.reviewDialog = false;
    },
  },
  mounted() {
    this.userimg = store.state.userInfo.img;
    this.username = store.state.userInfo.name;
    this.userbalance = store.state.balance;
    this.userid = store.state.userInfo.id;
    
    // 참여한 쇼핑 프로젝트 정보 가져오기
    const pageid = this.$route.params.userid;
    this.pageuserid = pageid;
    const fc = new FormData();
    fc.append("userid", this.pageuserid);
    axios.get(`${SERVER_URL}/mypage/saleplist?direction=DESC&oauthId=${this.userid}&page=${this.page}&size=${this.size}`)
      .then((response) => {
      // console.log('쇼핑 플젝 확인')
      // console.log(response)
    })
  },
  computed: {},
  watch: {},
};
</script>


<style scoped>
.investNav {
  /* height: 50px; */
  text-align: center;
  /* line-height: 50px; */
  /* border-bottom: 1px solid gray; */
  /* margin-bottom: 15px; */
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
  /* border: 1px solid lightgray; */
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

</style>