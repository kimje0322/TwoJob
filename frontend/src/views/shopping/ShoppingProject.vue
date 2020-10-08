<template>
  <div class="investproject">
    <HomeNav />
    <!-- 쇼핑 메뉴바 -->
    <div class="shoppingNav">
      <div class="items" style="text-align: center; height: 37.6px; margin: 24px 0px 13px 0px;">
        <div style="margin: 0 12% 0 0;">
          <router-link to="/shoppinghome">
            <h5 class="pageTab mb-0">쇼핑홈</h5>
          </router-link>
        </div>
        <div>
          <router-link to="/shoppingproject">
            <h5 class="pageTab mb-0" style="color: rgb(22, 150, 245)">프로젝트</h5>
          </router-link>
        </div><br>
          <hr class="divider mx-auto mt-2" style="display:inline-block; width: 18%; border: solid 2px lightgrey; background-color: lightgrey">
          <hr class="divider mx-auto mt-2" style="display:inline-block; width: 18%; border: solid 2px rgb(22, 150, 245); background-color: rgb(22, 150, 245)">
      </div>
    </div>
    <!-- 카테고리 -->
    <div style="margin: 3rem 0px;">
      <v-container class="cateContainer" style="text-align:center;">
        <v-row no-gutters>
          <!-- 정렬 맞추기 위해 왼쪽 빈칸 사용 -->
          <v-col cols="12" sm="1"></v-col>
          <!-- 카테고리 for문 -->
          <v-col v-for="(category, i) in categoryList" :key="i" cols="12" sm="1">
            <v-card
               @click="onCategory(category.name, category.key)"
              :class="category.key"
              class="pa-2 categoryCard"
              outlined
              tile
            >
              <v-icon class="categoryicon" :class="`${category.key}icon`" size="30">mdi-{{ category.icon }}</v-icon>
              <p class="categoryTag">{{category.name}}</p>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </div>
    <!-- 필터 -->
    <div class="filterBox">
      <div style="display: inline-block; margin-right: 2%">
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
          <v-icon color="rgba(0, 0, 0, 0.38)" style="margin-right: 10px">mdi-reload</v-icon>필터 초기화
        </v-btn>
      </div>
    </div>
  <!-- 프로젝트 -->
  <div style="margin: 15px 80px; padding-left: 105px">
      <div v-if="ispjt" style="padding: 1% 0">
        <div
          v-for="(item, i) in shoppingList"
          :key="i"
          style="display: inline-block; width: 30%; margin-bottom: 30px"
        >
          <v-card class="my-12 shadowimg" max-width="320" style="margin: auto">
            <router-link
              :to="{ name: 'ShoppingDetail', params: { address: item.address } }"
            >
              <v-img
                height="250"
                :src="item.picture"
              ></v-img>
            </router-link>
            
            
            <v-card-title style="font-weight: 600; margin: auto;">
              {{ item.pjtname }}
              <div style="margin-left: auto">
                  <v-chip
                  v-if="item.isopen"
                  small
                  class="ma-2 ml-auto px-2"
                  label
                  outlined=""
                  style="background-color:#FF4081; color:black; float:right;"
                >
                  <span>
                    <v-icon left
                      style="color:#FF1744"
                      class="mr-0 heartChip" size="19">
                      mdi-heart
                    </v-icon>
                    {{item.likeCount}}개
                  </span>
                </v-chip>
                <v-chip v-else label small style="padding:7px; background-color: rgb(22, 150, 245); margin-left: auto;">
                  <span class="openBadge" style="color:white">오픈 D-{{ item.opendate }}</span>
                </v-chip>
              </div>
            </v-card-title>
            <v-card-text style="height: 105px">
              <div style="margin-bottom: 10px; height: 40px">
                {{ item.onelineintro }}
              </div>
              <div style="color: black">
                <h5
                  style="
                    text-align:end;
                    font-weight: bold;
                    height: 41.6px;
                    line-height: 41.6px;
                  "
                >
                  판매가 {{item.saleprice}}원
                </h5>
                <div style="display: inline-block; float: right">
                  <h3 style="display: inline-block; color: rgb(22, 150, 245)">
                    <!-- {{ item.rate }}% -->
                  </h3>
                </div>
              </div>
            </v-card-text>
          </v-card>
        </div>
      </div>
      <!-- shoppingpjt 없는 경우 -->
      <div v-if="!ispjt" style="height: 100vh; display: flex; justify-content: center;;">
        <h5 style="margin-top:20%">쇼핑 프로젝트 검색 결과가 없습니다.</h5>
      </div>
    </div>
    <infinite-loading @infinite="infiniteHandler" spinner="waveDots">
      <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
    </infinite-loading>
  </div>
</template>

<script>
import "../../../public/css/InvestProject.scss";
import HomeNav from "../../components/HomeNav.vue";
// import "../../../public/css/ShoppingHome.scss";
import axios from "axios";
import InfiniteLoading from 'vue-infinite-loading';

const SERVER_URL = "https://www.twojob.ga/api";

export default {
  components: {
    HomeNav,
    InfiniteLoading
  },
  data() {
    return {
      // page
      page: 0,
      totalpage: 0,
      // 카테고리
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
      ispjt: false,
      shoppingList: [],
      likeCount: 0,
    };
  },
  watch: {
    nowcategory() {
      if(this.nowcategory==false){
        $(".all").addClass("active");
         $('.allicon').addClass('activeIcon')
        this.initAxios()
      }
    }
  },

  mounted() {
    // 카테고리
    $('.all').addClass('active')
    $(".allicon").addClass("activeIcon");
    this.initAxios();
  },
  methods: {
    initAxios() {
      this.page = 0
      const fd = new FormData();
      fd.append("orderOption", 0);
      fd.append("categoryfilter", "");
      axios
      .post(`${SERVER_URL}/sale/getAllSaleList/${this.page}`, fd)
      .then((response) => {
        // console.log(response)
        if (response.data.data == "success") {
            this.shoppingList = response.data.object.object;
            // console.log(response.data.object.likecount[0])
            if(this.shoppingList.length > 0) {
              this.ispjt = true
              // console.log(this.shoppingList)
              this.shoppingList = response.data.object.object;
              this.totalpage = this.shoppingList[0].totalpage;
              for (let i = 0; i < this.shoppingList.length; i++) {
                this.shoppingList[i].likeCount =  response.data.object.likecount[i];
            // console.log(this.shoppingList[i].likeCount )
              }
              // console.log(response.data.object.likeCount)
              this.shoppingList.forEach((shoppingPjt) => {
              // 오픈예정
              const year = shoppingPjt.startdate.substring(0, 4);
              const month = shoppingPjt.startdate.substring(5, 7);
              const day = shoppingPjt.startdate.substring(8, 10);
              var Dday = new Date(year, month-1, day);
              var now = new Date();
              var gap = now.getTime() - Dday.getTime();
              var result = Math.floor(gap / (1000 * 60 * 60 * 24)) * -1;
              this.$set(shoppingPjt, "opendate", result);
              if(result > 0){
                this.$set(shoppingPjt, "isopen", false)
              }else{
                this.$set(shoppingPjt, "isopen", true)
              }
              // 좋아요
              const idx = this.shoppingList.indexOf(shoppingPjt)
              shoppingPjt.likeCount = response.data.object.likecount[idx];
              // console.log(shoppingPjt)
              // 제목
              if (shoppingPjt.pjtname.length > 10) {
                shoppingPjt.pjtname = shoppingPjt.pjtname.substring(0, 10) + "...";
              }
            });
            } else {
              this.ispjt = false
            }
          }
        })
      .catch((error) => {
        // console.log(error);
      });
    },
    filterAxios() {
      this.page = 0
      const fd = new FormData();
      fd.append("orderOption", this.nowfilter);
      if (this.nowcategory.length > 0 ) {
        this.nowcategory.forEach((category) => {
          // console.log(Boolean(this.nowcategory))
          fd.append("categoryfilter", category);
        });
      } else {
        fd.append("categoryfilter", "");
      }
      axios
        .post(`${SERVER_URL}/sale/getAllSaleList/${this.page}`, fd)
        .then((response) => {
          // console.log(response)
          if (response.data.data == "success") {
            this.shoppingList = response.data.object.object;
            if(this.shoppingList.length > 0) {
              this.ispjt = true
              this.shoppingList = response.data.object.object;
              this.totalpage = this.shoppingList[0].totalpage;
              for (let i = 0; i < this.shoppingList.length; i++) {
                this.shoppingList[i].likeCount =  response.data.object.likecount[i];
              // console.log(this.shoppingList[i].likeCount )
              }
              this.shoppingList.forEach((shoppingPjt) => {
                // 오픈예정
                const year = shoppingPjt.startdate.substring(0, 4);
                const month = shoppingPjt.startdate.substring(5, 7);
                const day = shoppingPjt.startdate.substring(8, 10);
                var Dday = new Date(year, month-1, day);
                var now = new Date();
                var gap = now.getTime() - Dday.getTime();
                var result = Math.floor(gap / (1000 * 60 * 60 * 24)) * -1;
                this.$set(shoppingPjt, "opendate", result);
                if(result > 0){
                  this.$set(shoppingPjt, "isopen", false)
                }else{
                  this.$set(shoppingPjt, "isopen", true)
                }
              // 좋아요
              const idx = this.shoppingList.indexOf(shoppingPjt)
              shoppingPjt.likeCount = response.data.object.likecount[idx];
              // 제목
              if (shoppingPjt.pjtname.length > 10) {
                shoppingPjt.pjtname = shoppingPjt.pjtname.substring(0, 10) + "...";
              }
            });
            } else {
              this.ispjt = false
          }
          }
        })
        .catch((error) => {
          // console.log(error);
        });
    },
    filterInit() {
      // 카테고리 초기화
      this.categoryList.forEach(item =>{
        this.nowcategory.forEach((nn) => {
          if(item.name == nn){
            $(`.${item.key}`).removeClass("active");
            $(`.${item.key}icon`).removeClass("activeIcon")
          }
        });
      })
      this.nowcategory = [];
      $(".all").addClass("active");
      $(".allicon").addClass("activeIcon")
      // 필터 초기화
      (this.clickfilter = ""), (this.nowfilter = 0);
      this.openfilter = false;
      this.initAxios()
    },
    onCategory(name, key) {
      // 카테고리
      if (name == "전체") {
        this.categoryList.forEach(item =>{
          this.nowcategory.forEach((nn) => {
            if(item.name == nn){
              $(`.${item.key}`).removeClass("active");
              $(`.${item.key}icon`).removeClass("activeIcon")
            }
          });
        })
        this.nowcategory = [];
        $(".all").addClass("active");
        $(".allicon").addClass("activeIcon")
        this.initAxios()
      }
       else {
        // 제거
        if (this.nowcategory.indexOf(name) >= 0) {
          const idx = this.nowcategory.indexOf(name);
          this.nowcategory.splice(idx, 1);
          $(`.${key}`).removeClass("active");
          $(`.${key}icon`).removeClass("activeIcon")
        } else {
          this.nowcategory.push(name);
          $(`.${key}`).addClass("active");
          $(`.${key}icon`).addClass("activeIcon")
          $(".all").removeClass("active");
          $(".allicon").removeClass("activeIcon");
        }
        this.filterAxios()
      }    
    },
    onfilter() {
      // 필터
      if (this.clickfilter == "") {
        this.nowfilter = 0;
        // console.log('nowfilter0')
      } else if (this.clickfilter == "최신순") {
        this.nowfilter = 1;

      } else if (this.clickfilter == "인기순") {
        this.nowfilter = 2;
      }
      this.filterAxios()
    },
    infiniteHandler($state) {
      this.page += 1
      const fd = new FormData();
      fd.append("orderOption", this.nowfilter);
      if (this.nowcategory.length > 0 ) {
        this.nowcategory.forEach((category) => {
          // console.log(Boolean(this.nowcategory))
          fd.append("categoryfilter", category);
        });
      } else {
        fd.append("categoryfilter", "");
      }
      axios
        .post(`${SERVER_URL}/sale/getAllSaleList/${this.page}`, fd)
        .then((response) => {
          setTimeout(() => {
          // console.log(response)
          if (response.data.data == "success") {
            this.shoppingList = this.shoppingList.concat(response.data.object.object);
            if(this.shoppingList.length > 0) {
              this.ispjt = true
              this.totalpage = this.shoppingList[0].totalpage;
              this.shoppingList.forEach((shoppingPjt) => {
                // 오픈예정
                const year = shoppingPjt.startdate.substring(0, 4);
                const month = shoppingPjt.startdate.substring(5, 7);
                const day = shoppingPjt.startdate.substring(8, 10);
                var Dday = new Date(year, month-1, day);
                var now = new Date();
                var gap = now.getTime() - Dday.getTime();
                var result = Math.floor(gap / (1000 * 60 * 60 * 24)) * -1;
                this.$set(shoppingPjt, "opendate", result);
                if(result > 0){
                  this.$set(shoppingPjt, "isopen", false)
                }else{
                  this.$set(shoppingPjt, "isopen", true)
                }
              // 좋아요
              const idx = this.shoppingList.indexOf(shoppingPjt)
              shoppingPjt.likeCount = response.data.object.likecount[idx];
              // 제목
              if (shoppingPjt.pjtname.length > 10) {
                shoppingPjt.pjtname = shoppingPjt.pjtname.substring(0, 10) + "...";
              }
              });
              $state.loaded()
              if(this.page >= this.totalpage) {
                $state.complete()
              }
            } else {
              this.ispjt = false
            }
          }else{
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
  height: 50px;
  text-align: center;
  line-height: 50px;
}
.items div {
  display: inline-block;
}
.items div a {
  color: black;
  text-decoration: none;
}
.items div a:hover {
  color:
   rgb(22, 150, 245);
}
.items h5 {
  font-weight: 600;
}
.homeImg {
  margin-bottom: 3%;
}
.deadlineBadge {
  background-color: rgb(22, 150, 245) !important;
  color: white !important;
  text-align: right;
}
.likeBadge {
  background-color: red !important;
  color: white !important;
  text-align: right;
}
.openBadge {
  color: rgb(22, 150, 245);
}
.categoryTag {
  margin-top: 10px;
  /* margin-bottom: 0; */
}
.v-application--wrap {
  min-height: 0;
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
.filterBox {
  padding: 0 3%;
  height: 65px;
  border-bottom: 1px solid lightgray;
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
.pa-2 {
  padding:8px;
}
a {
  text-decoration: none !important;
  color: black;
}
.active {
  background-color: rgba(22, 150, 245, 0.1) !important;
}
.activeIcon {
  color: rgb(22,150,245);
}
.btn:hover {
  cursor: pointer;
}
.categoryCard {
  padding: 8px;
  border: none;
  border-radius: 100px !important;
  max-width: 94px;
}
.categoryCard:hover {
  background-color: rgba(22, 150, 245, 0.1);
}
.categoryCard:hover .categoryicon {
  color: rgb(22, 150, 245);
}
.v-card--link:before{
  background: none;
}
.shadowimg:hover {
  box-shadow: 10px 10px 20px grey;
}
</style>