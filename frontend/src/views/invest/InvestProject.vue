<template>
  <div class="investproject">
    <HomeNav />
    <!-- 투자 메뉴바 -->
    <div class="investNav">
      <div class="items" style="margin: 24px 0px 0px 0px;">
        <div >
          <router-link to="/investhome">
            <h5 class="pageTab">투자홈</h5>
          </router-link>
        </div>
        <div style="margin: 0 11%" class="pageTab">
          <router-link to="/investproject">
            <h5 class="pageTab" style="color: rgb(22, 150, 245)">프로젝트</h5>
          </router-link>
        </div>
        <div>
          <h5 @click="openinvestbtn" class="pageTab" style="margin: 0; cursor:pointer">투자오픈</h5>
        </div><br>
        <hr class="divider mx-auto mt-2" style="display:inline-block; width: 16%; border: solid 2px lightgrey; background-color: lightgrey">
        <hr class="divider mx-auto mt-2" style="display:inline-block; width: 16%; border: solid 2px rgb(22, 150, 245); background-color: rgb(22, 150, 245)">
        <hr class="divider mx-auto mt-2" style="display:inline-block; width: 16%; border: solid 2px lightgrey; background-color: lightgrey">
      </div>
    </div>
    <!-- <div>
      <h4 style="padding: 2%; margin: 0">투자 프로젝트</h4>
    </div> -->
    <!-- 카테고리 -->
    <div style="margin: 3rem 0">
      <!-- <v-app id="inspire" style="text-align:center"> -->
      <v-container class="cateContainer" style="text-align: center">
        <v-row no-gutters>
          <!-- 정렬 맞추기 위해 왼쪽 빈칸 사용 -->
          <v-col cols="12" sm="1"></v-col>
          <!-- 카테고리 for문 -->
          <v-col
            v-for="(category, i) in categoryList"
            :key="i"
            cols="12"
            sm="1"
          >
            <v-card
              @click="onCategory(category.name, category.key)"
              class="pa-2 categoryCard"
              :class="category.key"
              outlined
              tile
            >
              <v-icon class="categoryicon" :class="`${category.key}icon`"  size="30"
                >mdi-{{ category.icon }}</v-icon
              >
              <p class="categoryTag">{{ category.name }}</p>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
      <!-- </v-app> -->
    </div>
    <!-- 필터 -->
    <div class="filterBox">
      <div style="display: inline-block">
        <v-select
          :items="filter"
          label="　정렬"
          outlined
          hide-details
          class="origin"
          v-model="clickfilter"
          @change="onfilter()"
        ></v-select>
      </div>
      <div style="width: 150px; display: inline-block; float: right">
        <v-btn class="reloadbtn" @click="filterInit">
          <v-icon color="rgba(0, 0, 0, 0.38)" style="margin-right: 10px"
            >mdi-reload</v-icon
          >필터 초기화
        </v-btn>
      </div>
    </div>
    <!-- 프로젝트 -->
    <div class="projectList" style="padding: 10px 13%">
      <div v-if="ispjt" style="padding: 1% 0">
        <div
          v-for="(item, i) in investProjects"
          :key="i"
          style="display: inline-block; width: 33%; margin-top: 5%"
        >
          <!-- :class="`${item.address}img`" @mouseover="cardhover(item.address)" transform:scale(1); -->
          <v-card class="my-12 shadowimg" max-width="320" style="margin: auto">
            <router-link
              style="text-decoration: none"
              :to="{ name: 'InvestDetail', params: { address: item.address } }"
            >
              <v-img height="220" :src="item.picture"></v-img>
            </router-link>
            <v-card-title style="font-weight: 600; margin: auto">
              <router-link
                style="color: black"
                :to="{
                  name: 'InvestDetail',
                  params: { address: item.address },
                }"
              >
                <span>{{ item.pjtName }}</span></router-link>
              <div style="margin-left: auto">
                <!-- 종료 -->
                <v-chip
                  v-if="item.isfinish"
                  class="investPjtBadge"
                  style="background-color: gray; color: white"
                  >종료</v-chip
                >
                <v-chip v-else  class="projectBadge"
                  >{{ item.lastday }}일 남음</v-chip
                >
              </div>
            </v-card-title>
            <v-card-text style="height: 120px">
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
                  {{ item.investprice }}원
                </h5>
                <div style="display: inline-block; float: right">
                  <h3 style="display: inline-block; color: rgb(22, 150, 245)">
                    {{ item.rate }}%
                  </h3>
                  <h5 style="display: inline-block; color: rgb(123, 197, 254)">
                    달성
                  </h5>
                </div>
              </div>
            </v-card-text>
          </v-card>
        </div>
      </div>
      <!-- investPjt 없을 때 -->
      <div
        v-else
        style="
          height: 350px;
          display: flex;
          justify-content: center;
          align-items: center;
        "
      >
        <h4>투자 프로젝트 검색 결과가 없습니다.</h4>
      </div>
    </div>
    <infinite-loading @infinite="infiniteHandler" spinner="waveDots">
      <div
        slot="no-more"
        style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px"
      >
        목록의 끝입니다 :)
      </div>
    </infinite-loading>
  </div>
</template>

<script>
// import Navbar from "../../components/Navbar.vue";
import HomeNav from "../../components/HomeNav.vue";
import "../../../public/css/InvestProject.scss";
import axios from "axios";
import InfiniteLoading from "vue-infinite-loading";
import store from "../../store/index.js";
import Swal from "sweetalert2";

const SERVER_URL = "https://www.twojob.ga/api";

export default {
  components: {
    HomeNav,
    InfiniteLoading
  },
  data() {
    return {
      islogin: store.state.isSigned,
      categoryList: [
        { icon: "book-multiple-outline", name: "전체", key: "all" },
        { icon: "laptop-windows", name: "테크", key: "tech" },
        { icon: "shoe-heel", name: "패션", key: "fashion" },
        { icon: "lipstick", name: "뷰티", key: "beauty" },
        { icon: "food", name: "푸드", key: "food" },
        { icon: "hair-dryer", name: "홈리빙", key: "home" },
        { icon: "handball", name: "스포츠", key: "sports" },
        { icon: "dog", name: "반려동물", key: "animal" },
        { icon: "book-open-variant", name: "책", key: "book" },
        { icon: "violin", name: "악기", key: "instrument" },
      ],
      nowcategory: [],
      // 필터
      nowfilter: 0,
      clickfilter: "",
      filter: ["최신순", "인기순"],
      // 프로젝트
      investProjects: [],
      ispjt: true,
      // page
      page: 0,
      totalpage: 0,
    };
  },
  watch: {
    nowcategory() {
      if (this.nowcategory == false) {
        $(".all").addClass("active");
        $(".allicon").addClass("activeIcon");
        this.initAxios();
        // console.log("비어있음");
      }
    },
  },
  mounted() {
    // 카테고리
    $(".all").addClass("active");
    $(".allicon").addClass("activeIcon");
    this.initAxios();
  },
  methods: {
    openinvestbtn() {
      if(this.islogin){
        this.$router.push('/writeinvest')
      }
      else{
        Swal.fire({
            icon: "warning",
            title: "",
            text: "로그인 후 이용해 주세요.",
            showConfirmButton: false,
          })
      }
    },
    initAxios() {
      this.page = 0
      // console.log("초기 axios");
      const fd = new FormData();
      fd.append("orderOption", 0);
      fd.append("categoryfilter", "");
      axios
        .post(`${SERVER_URL}/investment/getAllInvestBoard/${this.page}`, fd)
        .then((response) => {
          // console.log(response);
          if (response.data.data == "success") {
            this.investProjects = response.data.object;
            // console.log(this.investProjects.length);
            if (this.investProjects.length > 0) {
              this.ispjt = true;
              this.investProjects = response.data.object;
              this.totalpage = this.investProjects[0].totalpage;
              this.investProjects.forEach((investPjt) => {
                // 마감일 기준 남은날짜 계산
                const year = investPjt.deadLine.substring(0, 4);
                const month = investPjt.deadLine.substring(5, 7);
                const day = investPjt.deadLine.substring(8, 10);
                var Dday = new Date(year, month - 1, day);
                var now = new Date();
                var gap = now.getTime() - Dday.getTime();
                var result = Math.floor(gap / (1000 * 60 * 60 * 24)) * -1;
                this.$set(investPjt, "lastday", result);
                // 제목
                if (investPjt.pjtName.length > 10) {
                  investPjt.pjtName =
                    investPjt.pjtName.substring(0, 10) + "...";
                }
                // 투자금액 axios 보내기
                axios
                  .get(
                    `${SERVER_URL}/funding/nowfund?campaignId=${investPjt.address}`
                  )
                  .then((response) => {
                    this.$set(investPjt, "investprice", response.data);
                  });
                // 달성률 axios 보내기
                const fd = new FormData();
                fd.append("campaignId", investPjt.address);
                axios
                  .post(`${SERVER_URL}/funding/fundingrate`, fd)
                  .then((response) => {
                    this.$set(investPjt, "rate", response.data);
                  });
              });
            } else {
              this.ispjt = false;
            }
          }
        })
        .catch((error) => {
          // console.log(error);
        });
    },
    filterAxios() {
      this.page = 0;
      const fd = new FormData();
      // const data = this.nowfilter
      // console.log("필터 axios");
      fd.append("orderOption", this.nowfilter);
      // console.log(Boolean(this.nowcategory));
      // console.log(this.nowcategory.length)
      if (this.nowcategory.length > 0) {
        this.nowcategory.forEach((category) => {
          fd.append("categoryfilter", category);
          // console.log(fd);
        });
      } else {
        fd.append("categoryfilter", "");
      }
      axios
        .post(`${SERVER_URL}/investment/getAllInvestBoard/${this.page}`, fd)
        .then((response) => {
          // console.log(response);
          if (response.data.data == "success") {
            this.investProjects = response.data.object;
            if (this.investProjects.length > 0) {
              this.ispjt = true;
              this.totalpage = this.investProjects[0].totalpage;
              this.investProjects.forEach((investPjt) => {
                // 마감일 기준 남은날짜 계산
                const year = investPjt.deadLine.substring(0, 4);
                const month = investPjt.deadLine.substring(5, 7);
                const day = investPjt.deadLine.substring(8, 10);
                var Dday = new Date(year, month - 1, day);
                var now = new Date();
                var gap = now.getTime() - Dday.getTime();
                var result = Math.floor(gap / (1000 * 60 * 60 * 24)) * -1;
                this.$set(investPjt, "lastday", result);
                // 제목
                if (investPjt.pjtName.length > 10) {
                  investPjt.pjtName =
                    investPjt.pjtName.substring(0, 10) + "...";
                }
                // 투자금액 axios 보내기
                axios
                  .get(
                    `${SERVER_URL}/funding/nowfund?campaignId=${investPjt.address}`
                  )
                  .then((response) => {
                    this.$set(investPjt, "investprice", response.data);
                  });
                // 달성률 axios 보내기
                const fd = new FormData();
                fd.append("campaignId", investPjt.address);
                axios
                  .post(`${SERVER_URL}/funding/fundingrate`, fd)
                  .then((response) => {
                    this.$set(investPjt, "rate", response.data);
                  });
              });
            } else {
              this.ispjt = false;
            }
          }
        })
        .catch((error) => {
          // console.log(error);
        });
    },
    filterInit() {
      // 카테고리 초기화
      this.categoryList.forEach((item) => {
        this.nowcategory.forEach((nn) => {
          if (item.name == nn) {
            $(`.${item.key}`).removeClass("active");
            $(`.${item.key}icon`).removeClass("activeIcon");
          }
        });
      });
      this.nowcategory = [];
      $(".all").addClass("active");
      $(".allicon").addClass("activeIcon")(
        // 필터 초기화
        (this.clickfilter = "")
      ),
        (this.nowfilter = 0);
      this.openfilter = false;
      this.initAxios();
    },
    onCategory(name, key) {
      // 카테고리
      if (name == "전체") {
        this.categoryList.forEach((item) => {
          this.nowcategory.forEach((nn) => {
            if (item.name == nn) {
              $(`.${item.key}`).removeClass("active");
              $(`.${item.key}icon`).removeClass("activeIcon");
            }
          });
        });
        this.nowcategory = [];
        $(".all").addClass("active");
        $(".allicon").addClass("activeIcon");
        this.initAxios();
      } else {
        // 제거
        if (this.nowcategory.indexOf(name) >= 0) {
          const idx = this.nowcategory.indexOf(name);
          this.nowcategory.splice(idx, 1);
          $(`.${key}`).removeClass("active");
          $(`.${key}icon`).removeClass("activeIcon");
        } else {
          this.nowcategory.push(name);
          $(`.${key}`).addClass("active");
          $(`.${key}icon`).addClass("activeIcon");
          $(".all").removeClass("active");
          $(".allicon").removeClass("activeIcon");
        }
        this.filterAxios();
      }
    },
    onfilter() {
      // 필터
      if (this.clickfilter == "") {
        this.nowfilter = 0;
      } else if (this.clickfilter == "최신순") {
        this.nowfilter = 1;
      } else if (this.clickfilter == "인기순") {
        this.nowfilter = 2;
      }
      this.filterAxios();
    },
    // 카드 mouseover 했을 떄
    // cardhover(address) {
    //   $(`.${address}img`).hover(function() {
    //     $(`.${address}img`).css("transform", "scale(1.1)")
    //   }, function() {
    //     $(`.${address}img`).css("transform", "scale(1)")
    //   })
    // }
    // 무한 스크롤
    infiniteHandler($state) {
      this.page += 1;
      const fd = new FormData();
      // const data = this.nowfilter
      // console.log("필터 axios");
      fd.append("orderOption", this.nowfilter);
      // console.log(Boolean(this.nowcategory));
      if (this.nowcategory.length > 0) {
        this.nowcategory.forEach((category) => {
          fd.append("categoryfilter", category);
          // console.log(fd);
        });
      } else {
        fd.append("categoryfilter", "");
        // console.log("여기");
      }
      // console.log("폼데이터");
      // console.log(fd);
      axios
        .post(`${SERVER_URL}/investment/getAllInvestBoard/${this.page}`, fd)
        .then((response) => {
          setTimeout(() => {
            // console.log(response);
            if (response.data.data == "success") {
              this.investProjects = this.investProjects.concat(
                response.data.object
              );
              if (this.investProjects.length > 0) {
                this.ispjt = true;
                this.totalpage = this.investProjects[0].totalpage;
                this.investProjects.forEach((investPjt) => {
                  // 마감일 기준 남은날짜 계산
                  const year = investPjt.deadLine.substring(0, 4);
                  const month = investPjt.deadLine.substring(5, 7);
                  const day = investPjt.deadLine.substring(8, 10);
                  var Dday = new Date(year, month - 1, day);
                  var now = new Date();
                  var gap = now.getTime() - Dday.getTime();
                  var result = Math.floor(gap / (1000 * 60 * 60 * 24)) * -1;
                  this.$set(investPjt, "lastday", result);
                  // 제목
                  if (investPjt.pjtName.length > 10) {
                    investPjt.pjtName =
                      investPjt.pjtName.substring(0, 10) + "...";
                  }
                  // 투자금액 axios 보내기
                  axios
                    .get(
                      `${SERVER_URL}/funding/nowfund?campaignId=${investPjt.address}`
                    )
                    .then((response) => {
                      this.$set(investPjt, "investprice", response.data);
                    });
                  // 달성률 axios 보내기
                  const fd = new FormData();
                  fd.append("campaignId", investPjt.address);
                  axios
                    .post(`${SERVER_URL}/funding/fundingrate`, fd)
                    .then((response) => {
                      this.$set(investPjt, "rate", response.data);
                    });
                });
                $state.loaded()
                if(this.page >= this.totalpage) {
                  $state.complete()
                }
              }else{
                this.ispjt = false;
              }
            }else {
                $state.complete()
              }
          }, 1000)
        })
        .catch((error) => {
          // console.log(error);
        });
    },
  },
};
</script>

<style scoped>
.investNav {
  /* height: 50px; */
  text-align: center;
  /* line-height: 40px; */
  /* border-bottom: 1px solid lightgray; */
  /* border-top: 1px solid lightgray; */
}
.items div {
  display: inline-block;
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
.categoryCard {
  padding: 8px;
  border: none;
  border-radius: 100px !important;
  max-width: 94px;
}
.categoryCard:hover {
  background-color: rgba(22, 150, 245, 0.1) !important;
}
.categoryCard:hover .categoryicon {
  color: rgb(22, 150, 245);
}
.v-card--link:before {
  background: none;
}
.categoryTag {
  margin-top: 10px;
}
.filterBox {
  padding: 0 3%;
  height: 65px;
  border-bottom: 1px solid lightgray;
}
.origin {
  width: 100px;
}
.checkstate {
  width: 200px;
}
.checkstate .v-input__slot {
  width: 200px;
}
.checkstate fieldset {
  width: 200px;
}
.longrate {
  width: 180px;
}
.longrate .v-input__slot {
  width: 180px;
}
.longrate fieldset {
  width: 180px;
}
.checkrate {
  width: 160px;
}
.checkrate .v-input__slot {
  width: 160px;
}
.checkrate fieldset {
  width: 160px;
}
.reloadbtn {
  background-color: unset !important;
  border: 1px solid rgba(0, 0, 0, 0.38);
  box-shadow: unset !important;
  color: rgba(0, 0, 0, 0.6);
  height: 45px !important;
}
.reloadbtn:before {
  background: unset !important;
}
.reloadbtn:hover {
  border: 1px solid black;
}
.filter {
  padding: 0;
  margin: 0;
}
.projectBadge {
  background-color: rgb(22, 150, 245) !important;
  color: white !important;
  text-align: right;
}
.active {
  /* border: 2px solid rgb(22, 150, 245); */
  background-color: rgba(22, 150, 245, 0.1) !important;
}
.activeIcon {
  color: rgb(22, 150, 245);
}
.pageTab {
  margin-bottom: 0px !important;
}
.shadowimg:hover {
  box-shadow: 10px 10px 20px grey;
}
</style>