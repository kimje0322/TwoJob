<template>
  <div class="shoppingHome">
    <navbar/>
    <!-- 쇼핑 메뉴바 -->
    <div class="shoppingNav">
      <div class="items">
        <div>
          <router-link to="/shoppinghome">
            <h5 style="color: rgb(22, 150, 245)">쇼핑홈</h5>
          </router-link>
        </div>
        <div>
          <router-link to="/shoppingproject">
            <h5>프로젝트</h5>
          </router-link>
        </div>
        <div>
          <router-link to="/shoppinghome">
            <h5>오픈예정</h5>
          </router-link>
        </div>
        <div>
          <router-link to="/writeshopping">
            <h5>쇼핑 오픈</h5>
          </router-link>
        </div>
      </div>
    </div>
    <hr>
    <!-- 카테고리 -->
    <v-app id="inspire" style="text-align:center">
      <v-container class="cateContainer">
        <v-row no-gutters>
          <!-- 정렬 맞추기 위해 왼쪽 빈칸 사용 -->
          <v-col cols="12" sm="1"></v-col>
          <!-- 카테고리 for문 -->
          <v-col v-for="(category, i) in categoryList" :key="i" cols="12" sm="1">
            <v-card
              class="pa-2"
              outlined
              tile
            >
              <v-icon size="30">mdi-{{category.icon}}</v-icon><br>
              <p class="categoryTag">{{category.name}}</p>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-app>

    <!-- 필터 -->
    <div class="filterBox">
      <!-- 상태 -->
      <div style="display: inline-block; margin-right: 2%">
        <v-select
          :items="state"
          label="상태"
          outlined
          hide-details
          v-model="nowstate"
          @click="openState"
          :class="{checkstate: checkstate}"
          class="origin"
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
          <span style="color: rgb(22, 150, 245);">4</span>
          <span>개의 프로젝트가 있습니다.</span>
        </div>
        <div style="width: 100px; display: inline-block; float: right;">
          <v-select
            :items="filter"
            hide-details
            label="최신순"
            single-line
            @click="openFilter"
            v-model="nowfilter"
            append-icon="mdi-arrow-down-drop-circle-outline"
            class="filter"
          ></v-select>
        </div>
      </div>
      <div style="padding: 1% 0">
        <div v-for="(item, i) in shoppingProjects" :key="i" style="display: inline-block; width: 30%; margin-bottom: 30px;">
          <v-card class="my-12" max-width="320" style="margin: auto">
            <router-link to="/shoppingdetail">
            <v-img height="250" src="https://cdn.vuetifyjs.com/images/cards/cooking.png"></v-img>
            </router-link>
            <v-card-title style="font-weight: 600; margin: auto">
              {{item.title}}
              <div style="margin-left: auto;">
                <v-chip class="projectBadge">{{item.deadline}}일 남음</v-chip>
              </div>
            </v-card-title>
            <v-card-text>
              <div style="margin-bottom: 15px;">{{item.content}}</div>
              <div style="color: black;">
                <h5
                  style="display: inline-block; height: 41.6px; line-height: 41.6px"
                >{{item.price}} 원</h5>
                <div style="display: inline-block; float: right;">
                  <h3 style="display: inline-block; color:rgb(22, 150, 245)">{{item.percent}}%</h3>
                  <h5 style="display: inline-block; color:rgb(123, 197, 254)">달성</h5>
                </div>
              </div>
            </v-card-text>
          </v-card>
        </div>
      </div>
    </div>

    <div style="display: flex; padding: 1% 0">
      <div v-for="(item, i) in deadlineItems" :key="i" style="display: inline-block; flex:1">
        <v-card :loading="loading" class="my-12" max-width="320" style="margin: auto">
          <router-link to="/ShoppingDetail">
          <v-img height="250" src="https://cdn.vuetifyjs.com/images/cards/cooking.png"></v-img>
          </router-link>
          <v-card-title style="font-weight: 600; margin: auto">{{item.title}}
            <div style="margin-left: auto;"><v-chip class="deadlineBadge">{{item.deadline}}개 남음</v-chip></div>
          </v-card-title>
          <v-card-text>
            <div style="margin-bottom: 15px;">{{item.content}}</div>
          </v-card-text>
        </v-card>
      </div>
    </div>
    <!-- 프로젝트 -->
    <div class="projectList" style="padding: 10px 3%;">
      <div style="height: 45px;">
        <div style="display: inline-block;">
         
        </div>
        <div style="width: 100px; display: inline-block; float: right;">
          <v-select
            :items="filter"
            hide-details
            label="최신순"
            single-line
            @click="openFilter"
            v-model="nowfilter"
            append-icon="mdi-arrow-down-drop-circle-outline"
            class="filter"
          ></v-select>
        </div>
      </div>
    <div style="display: flex; padding: 1% 0">
      <div v-for="(item, i) in likeItems" :key="i" style="display: inline-block; flex:1">
        <v-card :loading="loading" class="my-12" max-width="320" style="margin: auto">
          <v-img height="250" src="https://cdn.vuetifyjs.com/images/cards/cooking.png"></v-img>
          <v-card-title style="font-weight: 600; margin: auto">{{item.title}}
            <div style="margin-left: auto;"><v-chip class="likeBadge">{{item.likenum}}명 좋아요</v-chip></div>
          </v-card-title>
          <v-card-text>
            <div style="margin-bottom: 15px;">{{item.content}}</div>
            <!-- <div style="color: black; display: inline-block; float: right;">
              <v-icon size="20" class="mr-1">mdi-heart</v-icon>128
            </div> -->
          </v-card-text>
        </v-card>
      </div>
    </div>
    
    <div style="padding: 1% 0">
      <v-card v-for="(item, i) in openItems" :key="i" style="width: 38%; height: 180px; display: inline-block; margin: 0 6% 4% 6%;">
        <v-img style="width:33%; float:left;" height="180" src="https://cdn.vuetifyjs.com/images/cards/cooking.png"></v-img>
        <div style="width: 67%; float:right;">
          <v-card-title style="font-weight: 600;">{{item.title}}
            <div style="margin-left: auto;">
              <v-icon style="color:rgb(22, 150, 245); margin-right: 10px">mdi-clock-outline</v-icon><span class="openBadge">{{item.opendate}}일 뒤 오픈</span>
            </div>
          </v-card-title>
          <v-card-text>
            <div style="margin-bottom: 15px;">{{item.content}}</div>
            <div style="color: black;">
              <strong><p style="display: inline-block; height: 41.6px; line-height: 41.6px">판매 예정금액 {{item.price}} 원</p></strong>
            </div>
          </v-card-text>
        </div>
      </v-card>
    </div>
    </div>
  </div>
</template>

<script>
import "../../../public/css/InvestHome.scss";
import "../../../public/css/ShoppingHome.scss";
import Navbar from "../../components/Navbar.vue";

export default {
  components: {
    Navbar
  },
  data() {
    return {
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
      items: [
        {
          src: "https://image.freepik.com/free-photo/_93675-87338.jpg",
        },
        {
          src:
            "https://lh3.googleusercontent.com/proxy/eEAKz6kNc0gXbYyQR5AM2PFZQYoKepaO4JbqZAjJWtbGtTPvBVfh_2sTRVlj6Woh-UIHNphyKm9bIdkz5va1p2KKywJyl7Ed4872B5o9yQ",
        },
        {
          src:
            "https://cdn.wadiz.kr/wwwwadiz/green002/2019/0430/20190430185646591_32703.jpg/wadiz/format/jpg/quality/80/optimize",
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/planet.jpg",
        },
      ],
      // 상태
      nowstate: "",
      checkstate: false,
      openstate: false,
      state: [
        "전체 프로젝트",
        "진행중인 프로젝트",
        "종료된 프로젝트",
      ],
      // 필터
      nowfilter: "",
      openfilter: false,
      filter: ["최신순", "인기순", "마감임박순"],
      // 프로젝트
      shoppingProjects: [
        {
          title: "특별한 자전거",
          deadline: "1",
          content: "Small plates, salads & sandwiches - an intimate setting with 12 indoor seats plus patio seating.",
          price: "1,500,500",
          percent: "98",
        },
        {
          title: "특별한 숟가락",
          deadline: "3",
          content: "Small plates, salads & sandwiches - an intimate setting with 12 indoor seats plus patio seating.",
          price: "1,300,500",
          percent: "95",
        },
        {
          title: "달라진 안경",
          deadline: "7",
          content: "Small plates, salads & sandwiches - an intimate setting with 12 indoor seats plus patio seating.",
          price: "1,000,500",
          percent: "88",
        },
        {
          title: "달라진 안경",
          deadline: "7",
          content: "Small plates, salads & sandwiches - an intimate setting with 12 indoor seats plus patio seating.",
          price: "1,000,500",
          percent: "88",
        },
      ],
    };
  },
  watch: {

  },
  methods: {
    nowstate(val) {
      if(this.nowstate) {
        this.checkstate = true
      }
      else {
        this.checkstate = false
      }
    },
    filterInit() {
      this.nowstate = ''
      this.checkstate = false
      this.opensate = false
      this.nowrate = ""
      this.checkrate = false
      this.longrate = false
      this.openrate = false
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
</style>