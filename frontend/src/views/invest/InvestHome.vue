<template>
  <div class="investhome">
    <HomeNav />
    <!-- 투자 메뉴바 -->
    <div class="investNav">
      <div class="items" style="text-align: center; height: 37.6px !important;margin: 24px 0px 0px 0px;">
        <div>
          <router-link to="/investhome">
            <h5 class="pageTab" style="color: rgb(22, 150, 245)">투자홈</h5>
          </router-link>
        </div>
        <div style="margin: 0 11%">
          <router-link to="/investproject">
            <h5 class="pageTab">프로젝트</h5>
          </router-link>
        </div>
        <div>
          <h5 @click="openinvestbtn" class="pageTab" style="margin: 0; cursor:pointer">투자오픈</h5>
        </div><br>
        <hr class="divider mx-auto mt-2" style="display:inline-block; width: 16%; border: solid 2px rgb(22, 150, 245); background-color: rgb(22, 150, 245)">
        <hr class="divider mx-auto mt-2" style="display:inline-block; width: 16%; border: solid 2px lightgrey; background-color: lightgrey">
        <hr class="divider mx-auto mt-2" style="display:inline-block; width: 16%; border: solid 2px lightgrey; background-color: lightgrey">
      </div>
    </div>
    <!-- 투자홈 광고사진 -->
    <div class="homeImg mt-5">
      <v-carousel>
        <v-carousel-item
          v-for="(item, i) in items"
          :key="i"
          :src="item.src"
          reverse-transition="fade-transition"
          transition="fade-transition"
        ></v-carousel-item>
      </v-carousel>
    </div>
    <!-- 마감임박 -->
    <div style="margin: 0 11%">
       <h4 style="margin-left: 5px; font-weight: 600; margin-bottom:3px">
        이런 프로젝트 어때요?
      </h4>
      <span style="font-size: 0.9rem; margin-left:5px; margin-bottom:4px">마감이 임박한 있는 프로젝트입니다.</span>
      <hr class="divider mt-2" style="width: 24%; border: solid 2px lightgrey; background-color: lightgrey">
    </div>
    <div style="display: flex; padding: 1% 8%">
      <div
        v-for="(item, i) in deadlineItems"
        :key="i"
        style="display: inline-block; flex: 1"
      >
        <v-card class="my-12 shadowimg" max-width="320" style="margin: auto">
          <router-link
            style="text-decoration: none"
            :to="{ name: 'InvestDetail', params: { address: item.address } }"
          >
            <v-img height="250" :src="item.picture"></v-img>
          </router-link>
          <v-card-title style="font-weight: 600; margin: auto"
            ><router-link
              style="color: black"
              :to="{ name: 'InvestDetail', params: { address: item.address } }"
              ><span>{{ item.pjtname }}</span></router-link
            >
            <div style="margin-left: auto">
              <v-chip class="deadlineBadge">{{ item.lastday }}일 남음</v-chip>
            </div>
          </v-card-title>
          <v-card-text style="height: 130px">
            <div style="margin-bottom: 15px; height: 60px">
              {{ item.onelineintro }}
            </div>
            <div style="color: black; height: 50px">
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
    <!-- 인기순 -->
    <div style="margin: 5% 11% 0 11%">
       <h4 style="font-weight: 600; margin-bottom:3px">
        인기 프로젝트를 추천해드려요
      </h4>
      <hr class="divider mt-2" style="width: 28%; border: solid 2px lightgrey; background-color: lightgrey">
    </div>
    <div style="display: flex; padding: 1% 8%; padding-bottom: 5%">
      <div
        v-for="(item, i) in likeItems"
        :key="i"
        style="display: inline-block; flex: 1"
      >
        <v-card class="my-12 shadowimg" max-width="320" style="margin: auto">
          <router-link
            style="text-decoration: none"
            :to="{ name: 'InvestDetail', params: { address: item.address } }"
          >
            <v-img height="250" :src="item.picture"></v-img>
          </router-link>
          <v-card-title style="font-weight: 600; margin: auto"
            ><router-link
              style="color: black"
              :to="{ name: 'InvestDetail', params: { address: item.address } }"
              ><span>{{ item.pjtname }}</span></router-link
            >
            <div style="margin-left: auto">
              <!-- <v-chip class="likeBadge">{{item.likenum}}명 좋아요</v-chip> -->
              <span>
                <v-icon left style="color: red" class="mr-0" size="19">
                  mdi-heart
                </v-icon>
                {{ item.likenum }}개
              </span>
            </div>
          </v-card-title>
          <v-card-text style="height: 130px">
            <div style="margin-bottom: 15px; height: 60px">
              {{ item.onelineintro }}
            </div>
            <div style="color: black; height: 50px">
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
  </div>
</template>

<script>
import "../../../public/css/InvestHome.scss";
import HomeNav from "../../components/HomeNav.vue";
import axios from "axios";
import store from "../../store/index.js";
import Swal from "sweetalert2";

const SERVER_URL = "https://www.twojob.ga/api";

export default {
  components: {
    HomeNav,
  },
  data() {
    return {
      islogin: store.state.isSigned,
      items: [
        {
          src:
            "https://www.guerlain.com/sites/default/files/styles/desktop_homepage_slide/public/2720x1116_Avant_Premiere_desktop_homepage_V2_UK_0.jpg?itok=g-beqsFp",
        },
        {
          src:
            "https://www.gaguguide.com/img/news/4ca2bb540b179a0de888231e0603a21c.jpg",
        },
        {
          src: "https://www.yakup.com/data/editor/news/201504/JtVmeFiV86.jpg",
        },
      ],
      deadlineItems: [],
      likeItems: [],
    };
  },
  mounted() {
    axios.get(`${SERVER_URL}/investment/curation`).then((response) => {
      this.deadlineItems = response.data.object.closedeadlines;
      this.likeItems = response.data.object.popular;
      this.deadlineItems.forEach((item) => {
        // 마감일 기준 남은날짜 계산
        const year = item.deadline.substring(0, 4);
        const month = item.deadline.substring(5, 7);
        const day = item.deadline.substring(8, 10);
        var Dday = new Date(year, month - 1, day);
        var now = new Date();
        var gap = now.getTime() - Dday.getTime();
        var result = Math.floor(gap / (1000 * 60 * 60 * 24)) * -1;
        this.$set(item, "lastday", result);
        // 제목
        if (item.pjtname.length > 10) {
          item.pjtname = item.pjtname.substring(0, 10) + "...";
        }
        // 투자금액 axios 보내기
        axios
          .get(`${SERVER_URL}/funding/nowfund?campaignId=${item.address}`)
          .then((response) => {
            this.$set(item, "investprice", response.data);
          });
        // 달성률 axios 보내기
        const fd = new FormData();
        fd.append("campaignId", item.address);
        axios.post(`${SERVER_URL}/funding/fundingrate`, fd).then((response) => {
          this.$set(item, "rate", response.data);
        });
      });
      this.likeItems.forEach((item) => {
        // 제목
        if (item.pjtname.length > 10) {
          item.pjtname = item.pjtname.substring(0, 10) + "...";
        }
        // 좋아요 수
        const idx = this.likeItems.indexOf(item);
        this.$set(item, "likenum", response.data.object.popularlikecount[idx]);
        // 투자금액 axios 보내기
        axios
          .get(`${SERVER_URL}/funding/nowfund?campaignId=${item.address}`)
          .then((response) => {
            this.$set(item, "investprice", response.data);
          });
        // 달성률 axios 보내기
        const fd = new FormData();
        fd.append("campaignId", item.address);
        axios.post(`${SERVER_URL}/funding/fundingrate`, fd).then((response) => {
          this.$set(item, "rate", response.data);
        });
      });
    });
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
    }
  },
};
</script>

<style scoped>
.investNav {
  /* height: 50px; */
  text-align: center;
  /* line-height: 50px; */
  /* border-top: 1px solid lightgray; */
  /* border-bottom: 1px solid lightgray; */
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
  margin: 0;
}
.homeImg {
  margin-bottom: 5%;
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
.pageTab {
  margin-bottom: 0px !important;
}
.shadowimg:hover {
  box-shadow: 10px 10px 20px grey;
}
</style>