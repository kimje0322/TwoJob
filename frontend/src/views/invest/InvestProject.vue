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
            <h5 style="margin: 0">투자오픈</h5>
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
              @click="onCategory(category.name,)"
              :class="category.name"
              class="pa-2 categoryCard"
              outlined
              tile
            >
              <v-icon size="30">mdi-{{ category.icon }}</v-icon>
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
    <div class="projectList" style="padding: 10px 3%">
      <!-- <div style="height: 45px">
        <div style="display: inline-block">
          <span style="color: rgb(22, 150, 245)">25,540</span>
          <span>개의 프로젝트가 있습니다.</span>
        </div>
      </div> -->
      <div v-if="ispjt" style="padding: 1% 0">
        <div
          v-for="(item, i) in investProjects"
          :key="i"
          style="display: inline-block; width: 30%; margin-bottom: 30px"
        >
          <v-card class="my-12" max-width="320" style="margin: auto">
            <router-link
              :to="{ name: 'InvestDetail', params: { address: item.address } }"
            >
              <v-img
                height="250"
                :src="item.picture"
              ></v-img>
            </router-link>
            <v-card-title style="font-weight: 600; margin: auto">
              {{ item.pjtName }}
              <div style="margin-left: auto">
                <v-chip class="projectBadge">{{ item.lastday }}일 남음</v-chip>
              </div>
            </v-card-title>
            <v-card-text style="height: 130px">
              <div style="margin-bottom: 15px; height: 60px">
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
                  {{item.investprice}}원
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
      <div v-else>
        <h5 style="text-align:center; margin-top:50px">투자 프로젝트 검색 결과가 없습니다.</h5>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "../../components/Navbar.vue";
import "../../../public/css/InvestProject.scss";
import axios from "axios";
const SERVER_URL = "https://www.twojob.ga/api";

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
      nowcategory: [],
      // 필터
      nowfilter: 0,
      clickfilter: "",
      filter: ["최신순", "인기순"],
      // 프로젝트
      investProjects: [],
      ispjt: false,
      // page
      page: 0,
      totalpage: 0,
    };
  },
  watch: {
    nowcategory() {
      if(this.nowcategory==false){
        $(".all").addClass("active");
        this.initAxios()
        console.log('비어있음')
      }
    }
  },
  mounted() {
    // 카테고리
    $('.전체').addClass('active')
    this.initAxios()
  },
  methods: {
    initAxios() {
      console.log("초기 axios")
      const fd = new FormData();
      fd.append("orderOption", 0);
      fd.append("categoryfilter", "");
      axios
      .post(`${SERVER_URL}/investment/getAllInvestBoard/${this.page}`, fd)
      .then((response) => {
        console.log(response);
        if (response.data.data == "success") {
          this.investProjects = response.data.object;
          console.log(this.investProjects.length)
          if(this.investProjects.length > 0) {
            this.ispjt = true
            this.investProjects = response.data.object;
            this.totalpage = this.investProjects[0].totalpage;
            this.investProjects.forEach((investPjt) => {
              // 마감일
              const day = investPjt.deadLine.substring(8, 10);
              let today = new Date();
              today.setDate(day - today.getDate());
              this.$set(investPjt, "lastday", today.getDate());
              // 제목
              if (investPjt.pjtName.length > 8) {
                investPjt.pjtName = investPjt.pjtName.substring(0, 10) + "...";
              }
              // 투자금액 axios 보내기
              axios.get(`${SERVER_URL}/funding/nowfund?campaignId=${investPjt.address}`)
                .then(response => {
                  this.$set(investPjt, "investprice", response.data);
                })
              // 달성률 axios 보내기
              const fd = new FormData();
              fd.append("campaignId", investPjt.address)
              axios.post(`${SERVER_URL}/funding/fundingrate`, fd)
                .then(response => {
                  this.$set(investPjt, "rate", response.data);
                })
            });
          }else{
            this.ispjt = false
          }          
        }
      })
      .catch((error) => {
        console.log(error);
      });
    },
    filterAxios() {
      const fd = new FormData();
      // const data = this.nowfilter
      console.log("필터 axios")
      fd.append("orderOption", this.nowfilter);
      console.log(Boolean(this.nowcategory))
      if (this.nowcategory.length > 0) {
        this.nowcategory.forEach((category) => {
          fd.append("categoryfilter", category);
          console.log(fd)
        });
      } else {
        fd.append("categoryfilter", "");
        console.log("여기")
      }
      console.log('폼데이터')
      console.log(fd)
      axios
        .post(`${SERVER_URL}/investment/getAllInvestBoard/${this.page}`, fd)
        .then((response) => {
          console.log(response);
          if (response.data.data == "success") {
            this.investProjects = response.data.object;
            if(this.investProjects.length > 0) {
              this.ispjt = true
              this.totalpage = this.investProjects[0].totalpage;
              this.investProjects.forEach((investPjt) => {
                // 마감일
                const day = investPjt.deadLine.substring(8, 10);
                let today = new Date();
                today.setDate(day - today.getDate());
                this.$set(investPjt, "lastday", today.getDate());
                // 제목
                if (investPjt.pjtName.length > 8) {
                  investPjt.pjtName = investPjt.pjtName.substring(0, 10) + "...";
                }
                // 투자금액 axios 보내기
                axios.get(`${SERVER_URL}/funding/nowfund?campaignId=${investPjt.address}`)
                  .then(response => {
                    this.$set(investPjt, "investprice", response.data);
                  })
                // 달성률 axios 보내기
                const fd = new FormData();
                fd.append("campaignId", investPjt.address)
                axios.post(`${SERVER_URL}/funding/fundingrate`, fd)
                  .then(response => {
                    this.$set(investPjt, "rate", response.data);
                  })
              });
            } else{
              this.ispjt = false
            }
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
      $(".전체").addClass("active");
      // 필터 초기화
      (this.clickfilter = ""), (this.nowfilter = 0);
      this.openfilter = false;
      this.initAxios()
    },
    onCategory(name) {
      // 카테고리
      if (name == "전체") {
        this.nowcategory.forEach((name) => {
          $(`.${name}`).removeClass("active");
        });
        this.nowcategory = [];
        $(".전체").addClass("active");
        this.initAxios()
      }
       else {
        // 제거
        if (this.nowcategory.indexOf(name) >= 0) {
          const idx = this.nowcategory.indexOf(name);
          this.nowcategory.splice(idx, 1);
          $(`.${name}`).removeClass("active");
        } else {
          this.nowcategory.push(name);
          $(`.${name}`).addClass("active");
          $(".전체").removeClass("active");
        }
        this.filterAxios()
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
      this.filterAxios()
    },
  },
};
</script>

<style scoped>
.investNav {
  height: 50px;
  text-align: center;
  line-height: 50px;
  border-bottom: 1px solid lightgray;
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
  /* background-color: rgba(173, 220, 254, 0.4); */
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
.active {
  border: 2px solid rgb(22, 150, 245);
}
</style>