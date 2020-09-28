<template>
  <div class="investproject">
    <navbar />
    <!-- 투자 메뉴바 -->
    <div class="investNav">
      <div class="items">
        <div>
          <router-link to="/investhome">
            <h5>투자홈</h5>
          </router-link>
        </div>
        <div style="margin: 0 15%">
          <router-link to="/investproject">
            <h5 style="color: rgb(22, 150, 245)">프로젝트</h5>
          </router-link>
        </div>
        <div>
          <router-link to="/writeinvest">
            <h5 style="margin: 0;">투자오픈</h5>
          </router-link>
        </div>
      </div>
    </div>
    <div>
      <h4 style="padding: 2%; margin: 0">투자 프로젝트</h4>
    </div>
    <!-- 카테고리 -->
    <div style="margin-bottom: 1rem">
      <!-- <v-app id="inspire" style="text-align:center"> -->
      <v-container class="cateContainer" style="text-align:center">
        <v-row no-gutters>
          <!-- 정렬 맞추기 위해 왼쪽 빈칸 사용 -->
          <v-col cols="12" sm="1"></v-col>
          <!-- 카테고리 for문 -->
          <v-col v-for="(category, i) in categoryList" :key="i" cols="12" sm="1">
            <v-card class="pa-2 categoryCard" outlined tile>
              <v-icon size="30">mdi-{{category.icon}}</v-icon>
              <p class="categoryTag">{{category.name}}</p>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
      <!-- </v-app> -->
    </div>
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
      <div style="display: inline-block">
        <v-select 
        :items="successRate" 
        label="달성률" 
        outlined 
        hide-details
        v-model="nowrate"
        @click="openRate"
        :class="{checkrate: checkrate, longrate: longrate}"
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
          <span style="color: rgb(22, 150, 245);">25,540</span>
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
        <div v-for="(item, i) in investProjects" :key="i" style="display: inline-block; width: 30%; margin-bottom: 30px;">
          <v-card class="my-12" max-width="320" style="margin: auto">
            <v-img height="250" src="https://cdn.vuetifyjs.com/images/cards/cooking.png"></v-img>
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
  </div>
</template>

<script>
import Navbar from "../../components/Navbar.vue";
import "../../../public/css/InvestProject.scss";
import axios from "axios";
const SERVER_URL = "http://j3b102.p.ssafy.io:8080";

export default {
  components: {
    Navbar,
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
      // 상태
      nowstate: "",
      checkstate: false,
      openstate: false,
      state: [
        "전체 프로젝트",
        "진행중인 프로젝트",
        "성공한 프로젝트",
        "종료된 프로젝트",
      ],
      // 달성률
      nowrate: "",
      checkrate: false,
      longrate: false,
      openrate: false,
      successRate: [
        "전체 프로젝트",
        "50% 이하",
        "50% ~ 75%",
        "75% ~ 100%",
      ],
      // 필터
      nowfilter: "",
      openfilter: false,
      filter: ["최신순", "인기순"],
      // 프로젝트
      investProjects: [
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
      // page
      page: 0,
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
    nowrate(val) {
      if(this.nowrate=="전체 프로젝트") {
        this.longrate = true
        this.checkrate = false
      }
      else if(this.nowrate){
        this.checkrate = true
        this.longrate = false
      }
    },
  },
  mounted() {
    axios.get(`${SERVER_URL}/investment/getAllInvestBoard/${this.page}`)
      .then(response => {
        console.log(response)
      })
      .catch(error => {
        console.log(error)
      })
  },
  methods: {
    openState() {
      this.openstate = !this.openstate
      if(this.openstate) {
        this.nowstate = ''
        $('.v-menu').css('display', 'block')
      }
      else{
        $('.v-menu').css('display', 'none')
      }
    },
    openRate() {
      this.openrate = !this.openrate
      if(this.openrate) {
        this.nowrate = ''
        $('.v-menu').css('display', 'block')
      }
      else{
        $('.v-menu').css('display', 'none')
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
    openFilter() {
      this.openfilter = !this.openfilter
      if(this.openfilter) {
        this.nowfilter = ''
        $('.v-menu').css('display', 'block')
      }
      else{
        $('.v-menu').css('display', 'none')
      }
    }
  },
};
</script>

<style scoped>
.investNav {
  height: 50px;
  text-align: center;
  line-height: 50px;
  border-bottom: 1px solid gray;
  border-top: 1px solid lightgray;
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
}
.categoryCard:hover {
  border: 2px solid rgb(22, 150, 245);
  background-color: rgba(173, 220, 254, 0.4);
  cursor: pointer;
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

</style>