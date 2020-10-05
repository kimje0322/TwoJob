<template>
  <div class="investproject">
    <navbar/>
    <!-- 쇼핑 메뉴바 -->
    <div class="shoppingNav">
      <div class="items">
        <div>
          <router-link to="/shoppinghome">
            <h5>쇼핑홈</h5>
          </router-link>
        </div>
        <div>
          <router-link to="/shoppingproject">
            <h5 style="color: rgb(22, 150, 245)">프로젝트</h5>
          </router-link>
        </div>
      </div>
    </div>
    <hr>
    <!-- 카테고리 -->
    <div style="margin-bottom: 1rem;">
      <v-container class="cateContainer" style="text-align:center;">
        <v-row no-gutters>
          <!-- 정렬 맞추기 위해 왼쪽 빈칸 사용 -->
          <v-col cols="12" sm="1"></v-col>
          <!-- 카테고리 for문 -->
          <v-col v-for="(category, i) in categoryList" :key="i" cols="12" sm="1">
            <v-card
              @click="onCategory(category.key)"
              class="pa-2 categoryCard"
              outlined
              tile
            >
              <v-icon size="30">mdi-{{category.icon}}</v-icon><br>
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
          label="정렬"
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
    <div class="projectList" style="padding: 10px 3%;">
      <div style="height: 45px;">
        <div style="display: inline-block;">
          <!-- <span style="color: rgb(22, 150, 245);">16</span>
          <span>개의 프로젝트가 있습니다.</span> -->
        </div>
        <div style="width: 100px; display: inline-block; float: right;">
        </div>
      </div>
      <div style="padding: 1% 0">
        <div v-for="(item, i) in shoppingList" :key="i" 
          style="display: inline-block; width: 30%; margin-bottom: 30px;"
        > 
          <router-link :to="{name: 'ShoppingDetail', params: { address : item.address }}">
            <v-card class="my-12 pjtCard" height="436px" max-width="320" style="margin: auto">
              <v-img height="250" src="https://cdn.vuetifyjs.com/images/cards/cooking.png"></v-img>   
              <p class="mt-2 mb-1 ml-3">{{item.compname}}</p>
              <v-card-title style="font-weight: 600; margin: auto">
                {{item.pjtname}}
              </v-card-title>
              <v-card-text>
                <div style="margin-bottom: 15px;">
                  <p>{{item.onelineintro}}</p>
                </div>
                <div style="color: black;">
                  <h5
                    style="display: inline-block; height: 41.6px; line-height: 41.6px"
                  >{{item.saleprice}} 원</h5>
                  <div style="display: inline-block; float: right;">
                    <h3 style="display: inline-block; color:rgb(22, 150, 245)">{{item.sold}}21개</h3>
                    <h5 style="display: inline-block; color:rgb(123, 197, 254)">구매중</h5>
                  </div>
                </div>
              </v-card-text>
            </v-card>
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "../../../public/css/InvestProject.scss";
import "../../../public/css/ShoppingHome.scss";
import Navbar from "../../components/Navbar.vue";
import axios from "axios";

const SERVER_URL = "https://www.twojob.ga/api";

export default {
  components: {
    Navbar
  },
  data() {
    return {
      // page
      page: 0,
      totalpage: 0,
      // 프로젝트
      shoppingList: [],
      categoryList: [
        { icon: "book-multiple-outline", name: "전체", key: "all" },
        { icon: "laptop-windows", name: "테크, 가전", key: "tech" },
        { icon: "shoe-heel", name: "패션, 잡화", key: "fashion" },
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
      nowfilter: "",
      clickfilter: "",
      filter: ["최신순", "인기순"],
    };
  },
  watch: {
    nowstate(val) {
      if(this.nowstate) {
        this.checkstate = true
      }
      else {
        this.checkstate = false
      }
    },
  },
  mounted() {
    // 카테고리
    $('.all').addClass('active')
    this.initAxios();
  },
  methods: {
    initAxios() {
    const fd = new FormData();
    fd.append("orderOption", 0);
    fd.append("categoryfilter", "");
    axios
      .post(`${SERVER_URL}/sale/getAllSaleList/${this.page}`, fd)
      .then((response) => {
        console.log(response);
        if (response.data.data == "success") {
          this.shoppingList = response.data.object;
          console.log('이건 쇼핑리스트')
          console.log(this.shoppingList)
          this.totalpage = this.investProjects[0].totalpage;
          this.shoppingList.forEach((shoppingPjt) => {
            // 제목
            if (Pjt.pjtname.length > 8) {
              Pjt.pjtname = Pjt.pjtame.substring(0, 10) + "...";
            }
          });
        }
      })
      .catch((error) => {
        console.log(error);
      });
    },
    filterAxios() {
      const fd = new FormData();
      fd.append("orderOption", this.nowfilter);
      if (this.nowcategory) {
        this.nowcategory.forEach((category) => {
          console.log(category);
          fd.append("categoryfilter", category);
        });
      } else {
        fd.append("categoryfilter", "");
      }
      axios
        .post(`${SERVER_URL}/investment/getAllInvestBoard/${this.page}`, fd)
        .then((response) => {
          console.log(response);
          if (response.data.data == "success") {
            this.shoppingList = response.data.object;
            this.totalpage = this.investProjects[0].totalpage;
            this.shoppingList.forEach((shoppingPjt) => {
              // 제목
              if (shoppingPjt.pjtname.length > 8) {
                shoppingPjt.pjtname = shoppingPjt.pjtname.substring(0, 10) + "...";
              }
            });
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    filterInit() {
      // 카테고리 초기화
      this.nowcategory.forEach((key) => {
        $(`.${key}`).removeClass("active");
      });
      this.nowcategory = [];
      $(".all").addClass("active");
      // 필터 초기화
      (this.clickfilter = ""), (this.nowfilter = 0);
      this.initAxios()
    },
    onCategory(key) {
      // 카테고리
      if (key == "all") {
        this.nowcategory.forEach((key) => {
          $(`.${key}`).removeClass("active");
        });
        this.nowcategory = [];
        $(".all").addClass("active");
      } else {
        // 제거
        if (this.nowcategory.indexOf(key) >= 0) {
          const idx = this.nowcategory.indexOf(key);
          this.nowcategory.splice(idx, 1);
          $(`.${key}`).removeClass("active");
        } else {
          this.nowcategory.push(key);
          $(`.${key}`).addClass("active");
          $(".all").removeClass("active");
        }
      }
      this.filterAxios()
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
      this.filterAxios()
    },
  },
};
</script>

<style scoped>
.shoppingNav {
  height: 50px;
  text-align: center;
  line-height: 50px;
  border-top: 1px solid lightgray;
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
  margin-bottom: 0;
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
.v-card__title {
  padding-top: 0px;
}
a {
  text-decoration: none;
}
</style>