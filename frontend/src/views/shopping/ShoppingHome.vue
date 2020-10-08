<template>
  <div class="shoppingHome">
    <HomeNav />
    <!-- 쇼핑 메뉴바 -->
    <div class="shoppingNav">
      <div class="items" style="margin: 24px 0px 13px 0px;">
        <div style="margin: 0 12% 0 0;">
          <router-link to="/shoppinghome">
            <h5 class="pageTab mb-0" style="color: rgb(22, 150, 245)">쇼핑홈</h5>
          </router-link>
        </div>
        <div>
          <router-link to="/shoppingproject">
            <h5 class="pageTab mb-0">프로젝트</h5>
          </router-link>
        </div><br>
      <hr class="divider mx-auto mt-2" style="display:inline-block; width: 18%; border: solid 2px rgb(22, 150, 245); background-color: rgb(22, 150, 245)">
      <hr class="divider mx-auto mt-2" style="display:inline-block; width: 18%; border: solid 2px lightgrey; background-color: lightgrey">
      </div>
    </div>
    <!-- 쇼핑홈 광고사진 -->
    <div class="homeImg">
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
    <div>
      <!-- 임시 리뷰작성 버튼 activator -->

      <!-- 리뷰 모달 -->
      <!-- <v-dialog max-width="640" min-height="500" v-model="reviewDialog">
        <ShoppingReview @closeReviewDialog="closeReviewDialog"></ShoppingReview>
        <v-card-actions style="background-color: white;">
          <v-spacer></v-spacer>
          <v-btn text @click="reviewDialog=false">취소</v-btn>
          <v-btn text color="blue">등록</v-btn>
        </v-card-actions>
      </v-dialog>     -->
    </div>

    <div style="margin: 0px 185px 0 185px">
    <!-- 오픈예정 -->
    <div style="margin-left: 3%; margin-top: 3%">
      <h4 style="font-weight: 600; margin-bottom:3px">
        이런 프로젝트 어때요?
      </h4>
      <span style="font-size: 0.9rem; margin-bottom:4px">오픈이 예정되어 있는 프로젝트입니다.</span>
      <hr class="divider mt-2" style="width: 24%; border: solid 2px lightgrey; background-color: lightgrey">
    </div>

    <div style="display: flex; padding: 1% 0">
      <div v-for="(item, i) in openItems" :key="i" style="display: inline-block; flex:1">
        <v-card class="my-12 shadowimg" max-width="320" style="margin: auto">
          <router-link :to="{ name: 'ShoppingDetail', params: { address: item.address } }">
          <v-img height="250" :src="item.picture"></v-img>
          <v-card-title style="font-weight: 600; margin: auto">{{item.pjtname}}
            <v-chip label small style="padding:7px; background-color: rgb(22, 150, 245); margin-left: auto;">
                <span class="openBadge" style="color:white">오픈 D-{{ item.afterday }}</span>
            </v-chip>
          </v-card-title>
          <v-card-text>
            <div style="margin-bottom: 15px;">{{item.openintro}}</div>
            <div style="color: black;">
              <h5 style="text-align: end; font-weight: bold; margin-right:10px; margin-bottom:0px;">판매가 {{item.saleprice}} 원</h5>
            </div>
          </v-card-text>
          </router-link>
        </v-card>
      </div>
    </div>

    <!-- 인기순 -->
    <div style="margin-left: 5%; margin-top: 5%">
      <h4 style="font-weight: 600; margin-left:8px;">
        인기 프로젝트를 추천해드려요
      </h4>
      <hr class="divider ml-2 mt-2" style="width: 30%; border: solid 2px lightgrey; background-color: lightgrey">
    </div>
    <div style="padding: 1% 0">
      <v-card
        class="shadowimg"
        v-for="(item, i) in likeItems"
        :key="i"
        style="
          width: 38%;
          height: 180px;
          display: inline-block;
          margin: 0 6% 4% 6%;
        "
      >
      <router-link :to="{ name: 'ShoppingDetail', params: { address: item.address } }">
        <v-img
          style="width: 33%; float: left"
          height="180"
          :src="item.picture"
        ></v-img>
        <div style="margin-top: 3px; width: 67%; float: right">
          <v-card-title style="font-weight: 600"
            >{{ item.pjtname }}
          <v-chip
            small
            class="ma-2 ml-auto px-2"
            label
            outlined=""
            style="background-color:#FF4081; color:black"
          >
          <span>
            <v-icon left
              style="color:#FF1744"
              class="mr-0" size="19">
              mdi-heart
            </v-icon>
            {{item.likecount}}개
          </span>
          </v-chip>
          </v-card-title>
          <v-card-text>
            <div style="margin-bottom: 13px; height:48px">{{ item.likeintro }}</div>
            <div style="color: black">
              <h5 style="text-align: end; font-weight: bold; margin-right:8px; margin-bottom:0px;">판매가 {{item.saleprice}} 원</h5>
            </div>
          </v-card-text>
        </div>
        </router-link>
      </v-card>
    </div>
    </div>
  </div>
</template>

<script>
import "../../../public/css/InvestHome.scss";
import "../../../public/css/ShoppingHome.scss";
// import Navbar from "../../components/Navbar.vue";
import HomeNav from "../../components/HomeNav.vue";
import axios from "axios";

const SERVER_URL = "https://www.twojob.ga/api";

export default {
  components: {
    HomeNav,
    // Navbar,
    // ShoppingReview,
  },
  data() {
    return {
      reviewDialog: "",
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
          src: "https://boxmaster.co.kr/upload/magazine/5c99e2ed92bd7.jpg",
        },
        {
          src:
            "https://boxmaster.co.kr/upload/magazine/5c99e01e4c37e.jpg",
        },
        {
          src: "https://cdn.pixabay.com/photo/2017/04/06/11/24/fashion-2208045_1280.jpg",
        },
      ],
      openItems: [],
      likeItems: [],
    };
  },
  mounted() {
    axios
      .get(`${SERVER_URL}/sale/curation`)
      .then((res) => {
        // console.log(res)
        this.openItems = res.data.object.closeopen;
        // likeopenonelineintro
        this.likeItems = res.data.object.popular;
        
        for (var i = 0; i < this.likeItems.length; i++) {
          this.likeItems[i].likeintro = res.data.object.likeonelineintro[i]
          this.likeItems[i].likecount = res.data.object.popularlikecount[i]
        }
        for (var j = 0; j < this.openItems.length; j++) {
          this.openItems[j].openintro = res.data.object.closeopenonelineintro[j]
        }

        this.openItems.forEach(item => {
            // 마감일
            const day = item.startdate.substring(8, 10);
            let today = new Date();
            today.setDate(day - today.getDate());
            this.$set(item, "afterday", today.getDate());
            // 제목
            if (item.pjtname.length > 13) {
              item.pjtname = item.pjtname.substring(0, 13) + "...";
            }
        })
        this.likeItems.forEach(item => {
          // 제목
          if (item.pjtname.length > 12) {
            item.pjtname = item.pjtname.substring(0, 12) + "...";
          }
        })
      }) 
  },
  methods: {
    openReviewDialog() {
      this.reviewDialog = true;
    },
    closeReviewDialog() {
      this.reviewDialog = false;
    },
  },
};
</script>

<style scoped>
.shoppingNav {
  /* height: 50px; */
  text-align: center;
  /* line-height: 50px; */
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
.categoryTag {
  margin-bottom: 0;
}
.v-application--wrap {
  min-height: 0;
}
.v-card__title {
  color: black;
  text-decoration: none;
}
a {
  text-decoration: none !important;
  color: black;
}
.shadowimg:hover {
  box-shadow: 10px 10px 20px grey;
}
</style>