<template>
  <div class="shoppingHome">
    <navbar />
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

    <!-- 인기순 -->
    <div style="margin-left: 5%; margin-top: 3%">
      <h4 style="font-weight: 600">
        인기순<v-icon style="font-size: 36px; color: black"
          >mdi-chevron-right</v-icon
        >
      </h4>
    </div>
    <div style="display: flex; padding: 1% 0">
      <div v-for="(item, i) in likeItems" :key="i" style="display: inline-block; flex:1">
        <v-card class="my-12" max-width="320" style="margin: auto">
          <v-img height="250" :src="item.picture"></v-img>
          <v-card-title style="font-weight: 600; margin: auto">{{item.pjtname}}
            <div style="margin-left: auto;"><v-chip class="likeBadge">{{item.likenum}}명 좋아요</v-chip></div>
          </v-card-title>
          <v-card-text>
            <div style="margin-bottom: 15px">{{ item.content }}</div>
            <!-- <div style="color: black; display: inline-block; float: right;">
              <v-icon size="20" class="mr-1">mdi-heart</v-icon>128
            </div> -->
          </v-card-text>
        </v-card>
      </div>
    </div>

    <!-- 오픈예정 -->
    <div style="margin-left: 5%; margin-top: 3%">
      <h4 style="font-weight: 600">
        오픈예정<v-icon style="font-size: 36px; color: black"
          >mdi-chevron-right</v-icon
        >
      </h4>
    </div>
    <div style="padding: 1% 0">
      <v-card
        v-for="(item, i) in openItems"
        :key="i"
        style="
          width: 38%;
          height: 180px;
          display: inline-block;
          margin: 0 6% 4% 6%;
        "
      >
        <v-img
          style="width: 33%; float: left"
          height="180"
          src="https://cdn.vuetifyjs.com/images/cards/cooking.png"
        ></v-img>
        <div style="width: 67%; float: right">
          <v-card-title style="font-weight: 600"
            >{{ item.title }}
            <div style="margin-left: auto">
              <v-icon style="color: rgb(22, 150, 245); margin-right: 10px"
                >mdi-clock-outline</v-icon
              ><span class="openBadge">{{ item.opendate }}일 뒤 오픈</span>
            </div>
          </v-card-title>
          <v-card-text>
            <div style="margin-bottom: 15px">{{ item.content }}</div>
            <div style="color: black">
              <strong
                ><p
                  style="
                    display: inline-block;
                    height: 41.6px;
                    line-height: 41.6px;
                  "
                >
                  판매가 {{ item.price }} 원
                </p></strong>
            </div>
          </v-card-text>
        </div>
      </v-card>
    </div>
  </div>
</template>

<script>
import "../../../public/css/InvestHome.scss";
import "../../../public/css/ShoppingHome.scss";
import Navbar from "../../components/Navbar.vue";
import axios from "axios";

const SERVER_URL = "https://www.twojob.ga/api";

// 쇼핑 리뷰
import ShoppingReview from "@/views/shopping/ShoppingReview.vue";
import Axios from 'axios';

export default {
  components: {
    Navbar,
    ShoppingReview,
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
      openItems: [],
      likeItems: [],
    };
  },
  mounted() {
    axios
      .get(`${SERVER_URL}/sale/curation`)
      .then((res) => {
        console.log(res.data.object);
        this.openItems = res.data.object.closeopen;
        this.likeItems = res.data.object.popular;
        // console.log(this.likeItems);
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
  color: rgb(22, 150, 245);
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
</style>