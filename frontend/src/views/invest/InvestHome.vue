<template>
  <div class="investhome">
    <navbar/>
    <!-- 투자 메뉴바 -->
    <div class="investNav">
      <div class="items">
        <div>
          <router-link to="/investhome">
            <h5 style="color: rgb(22, 150, 245)">투자홈</h5>
          </router-link>
        </div>
        <div style="margin: 0 15%">
          <router-link to="/investproject">
            <h5>프로젝트</h5>
          </router-link>
        </div>
        <div>
          <router-link to="/writeinvest">
            <h5 style="margin: 0">투자오픈</h5>
          </router-link>
        </div>
      </div>
    </div>
    <!-- 투자홈 광고사진 -->
    <div class="homeImg">
      <v-carousel>
        <v-carousel-item
          v-for="(item,i) in items"
          :key="i"
          :src="item.src"
          reverse-transition="fade-transition"
          transition="fade-transition"
        ></v-carousel-item>
      </v-carousel>
    </div>
    <!-- 마감임박 -->
    <div style="margin: 0 8%;">
      <h4 style="font-weight: 600">마감임박</h4>
      <hr>
    </div>
    <div style="display: flex; padding: 1% 8%">
      <div v-for="(item, i) in deadlineItems" :key="i" style="display: inline-block; flex:1">
        <v-card class="my-12" max-width="320" style="margin: auto">
          <router-link
              :to="{ name: 'InvestDetail', params: { address: item.address } }"
            >
            <v-img height="250" :src="item.picture"></v-img>
          </router-link>
          <v-card-title style="font-weight: 600; margin: auto">{{item.pjtname}}
            <div style="margin-left: auto;"><v-chip class="deadlineBadge">{{item.lastday}}일 남음</v-chip></div>
          </v-card-title>
          <v-card-text>
            <div style="margin-bottom: 15px;">{{item.onelineintro}}</div>
            <div style="color: black;">
              <h5 style="display: inline-block; height: 41.6px; line-height: 41.6px">{{item.investprice}}원</h5>
              <div style="display: inline-block; float: right;">
                <h3 style="display: inline-block; color:rgb(22, 150, 245)">{{item.rate}}%</h3>
                <h5 style="display: inline-block; color:rgb(123, 197, 254)">달성</h5>
              </div>
            </div>
          </v-card-text>
        </v-card>
      </div>
    </div>
    <!-- 인기순 -->
    <div style="margin:5% 8% 0 8%;">
      <h4 style="font-weight: 600">인기순</h4>
      <hr>
    </div>
    <div style="display: flex; padding: 1% 8%; padding-bottom: 5%">
      <div v-for="(item, i) in likeItems" :key="i" style="display: inline-block; flex:1">
        <v-card class="my-12" max-width="320" style="margin: auto">
          <router-link
              :to="{ name: 'InvestDetail', params: { address: item.address } }"
            >
            <v-img height="250" :src="item.picture"></v-img>
          </router-link>
          <v-card-title style="font-weight: 600; margin: auto">{{item.pjtname}}
            <div style="margin-left: auto;"><v-chip class="likeBadge">{{item.likenum}}명 좋아요</v-chip></div>
          </v-card-title>
          <v-card-text>
            <div style="margin-bottom: 15px;">{{item.onelineintro}}</div>
            <div style="color: black;">
              <h5 style="display: inline-block; height: 41.6px; line-height: 41.6px">{{item.investprice}}원</h5>
              <div style="display: inline-block; float: right;">
                <h3 style="display: inline-block; color:rgb(22, 150, 245)">{{item.rate}}%</h3>
                <h5 style="display: inline-block; color:rgb(123, 197, 254)">달성</h5>
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
import Navbar from "../../components/Navbar.vue";
import axios from "axios";

const SERVER_URL = "https://www.twojob.ga/api";

export default {
  components: {
    Navbar,
  },
  data() {
    return {
      items: [
        {
          src: "https://www.guerlain.com/sites/default/files/styles/desktop_homepage_slide/public/2720x1116_Avant_Premiere_desktop_homepage_V2_UK_0.jpg?itok=g-beqsFp",
        },
        {
          src: "https://www.gaguguide.com/img/news/4ca2bb540b179a0de888231e0603a21c.jpg",
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
    axios.get(`${SERVER_URL}/investment/curation`)
      .then(response => {
        this.deadlineItems = response.data.object.closedeadlines
        this.likeItems = response.data.object.popular
        this.deadlineItems.forEach(item => {
          // 마감일
          const day = item.deadline.substring(8, 10);
          let today = new Date();
          today.setDate(day - today.getDate());
          this.$set(item, "lastday", today.getDate());
          // 제목
          if (item.pjtname.length > 8) {
            item.pjtname = item.pjtname.substring(0, 10) + "...";
          }
          // 투자금액 axios 보내기
          axios.get(`${SERVER_URL}/funding/nowfund?campaignId=${item.address}`)
            .then(response => {
              this.$set(item, "investprice", response.data);
            })
          // 달성률 axios 보내기
          const fd = new FormData();
          fd.append("campaignId", item.address)
          axios.post(`${SERVER_URL}/funding/fundingrate`, fd)
            .then(response => {
              this.$set(item, "rate", response.data);
            })
        })
        this.likeItems.forEach(item=>{
          // 제목
          if (item.pjtname.length > 8) {
            item.pjtname = item.pjtname.substring(0, 10) + "...";
          }
          // 좋아요 수
          const idx = this.likeItems.indexOf(item)
          this.$set(item, "likenum", response.data.object.popularlikecount[idx])
          // 투자금액 axios 보내기
          axios.get(`${SERVER_URL}/funding/nowfund?campaignId=${item.address}`)
            .then(response => {
              this.$set(item, "investprice", response.data);
            })
          // 달성률 axios 보내기
          const fd = new FormData();
          fd.append("campaignId", item.address)
          axios.post(`${SERVER_URL}/funding/fundingrate`, fd)
            .then(response => {
              this.$set(item, "rate", response.data);
            })
        })
      })
  },
  methods: {

  },
};
</script>

<style scoped>
.investNav {
  height: 50px;
  text-align: center;
  line-height: 50px;
  border-top: 1px solid lightgray;
  border-bottom: 1px solid lightgray;
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
</style>