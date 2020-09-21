<template>
  <div class="shoppingDetail">
    <navbar/>
    <!-- 상품 게시글 제목 -->
    <div class="shoppingTitle" style="height: 230px; background-color: lightgrey;">
      <h4 style="text-align: center; padding-top:95px;">Ostay Hair dryer Dock for Dyson</h4>
    </div>
    <div class="entirePage">
      <div class="shoppingDetail">
        <!-- 좌측: 상품 사진 -->
        <div class="shoppingimg">
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
        <!-- 우측: 상품 디테일 -->
        <div style="float: left; width:36%; margin-top:10px;">
          <!-- 기업명 -->
          <p>다이손</p>
          <!-- 상품 제목 -->
          <h3>{{ detailItems.title }}</h3>
          <hr />
          <p style="font-size: 1.2em">
            <strong>
              <span style="color: rgb(22, 150, 245);">{{ detailItems.perchase }}</span>
            </strong>개 구매 중
          </p>
          <!-- 카테고리 -->
          <div style="margin-bottom: 4%">
            <strong>
              <p class="listTitle" style="margin-bottom: 1%">상품 카테고리</p>
            </strong>
            <v-chip class="categoryBadge mr-1" small>홈리빙</v-chip>
            <v-chip class="categoryBadge mr-1" small>드라이기</v-chip>
          </div>
          <!-- 가격 -->
          <div style="margin-bottom: 4%">
            <strong>
              <p class="listTitle" style="margin-bottom: 1%">판매가</p>
            </strong>
            <h5>75,000원</h5>
          </div>
          <!-- 태그 -->
          <strong>
            <p class="listTitle" style="margin-bottom: 1%">태그</p>
          </strong>
          <v-chip class="mr-1" small>#다이슨</v-chip>
          <v-chip class="mr-1" small>#헤어</v-chip>
          <v-chip class="mr-1" small>#스탠드</v-chip>
          <br />
          <br />
          <v-btn class="perchaseBtn white--text">
            <v-icon size="18" class="mr-1">mdi-cart-outline</v-icon>상품 구매
          </v-btn>

          <v-container class="cardContainer">
            <v-row class="cards">
              <!-- 정렬 맞추기 위해 왼쪽 빈칸 사용 -->
              <v-col class="otherCard" cols="5">
                <v-card
                  style="text-align: center; padding: 5px 2px 5px 2px"
                  class="pa-2"
                  outlined
                  tile
                >
                  <v-icon size="20" class="mr-2">mdi-heart</v-icon>22
                  <br />
                </v-card>
              </v-col>
              <v-col class="otherCard" cols="5">
                <v-card
                  style="text-align: center; padding: 5px 2px 5px 2px"
                  class="pa-2"
                  outlined
                  tile
                >
                  <v-icon size="20" class="mr-1">mdi-message-bulleted</v-icon>문의
                  <br />
                </v-card>
              </v-col>
            </v-row>
          </v-container>
        </div>
      </div>
      <!-- 금손님 정보 -->
      <div class="mt-5">
        <strong>
          <p class="mb-3">금손님 정보</p>
        </strong>
        <div class="maker" style="float:left">
          <img class="makerImg" src="../../assets/금손.jpg" alt />
        </div>
        <div style="float:left; margin: 3px 0 0 15px;">
          <p style="font-size: 0.9rem; margin-bottom:5px;">
            (주)강한하늘 대표
            <strong>강하늘</strong>
          </p>
          <p style="font-size: 0.9rem;">여행을 다니며 영감을 얻는 사업가 강하늘입니다.</p>
        </div>
      </div>
      <hr style="margin-top: 80px" />
      <!-- 탭 -->
      <v-tabs
        style="background-color: #f8f9fa;"
        fixed-tabs
        v-model="currentItem"
        slider-color="rgb(22, 150, 245);"
      >
        <v-tab
          style="background-color: #f8f9fa;"
          v-for="tabItem in tabItems"
          :key="tabItem"
          :href="'#tab-' + tabItem"
        >
          <v-icon class="tabBtn" v-if="tabItem=='pjtInfo'">상품 설명서</v-icon>
          <v-icon class="tabBtn" v-if="tabItem=='reviews'">리뷰</v-icon>
        </v-tab>
      </v-tabs>
      <br />

      <v-tabs-items v-model="currentItem">
        <v-tab-item v-for="tabItem in tabItems" :key="tabItem" :value="'tab-' + tabItem">
          <!-- 프로젝트 이력 -->
          <!-- <div v-if="tabItem=='projects'" class="mt-2">
            <div style="float:left; width: 33%">
              <div style="text-align: center">
                <p>프로젝트 성공률</p>
                <img style="width: 50%;" src="../../assets/80.png" alt="">
              </div>
            </div>
            <div style="float:left; width: 33%">
              <div style="text-align: center">
                <p>성공 프로젝트</p>
                <p class="projectsNum mt-4">8</p>
              </div>
            </div>
            <div style="float:left; width: 33%">
              <div style="text-align: center">
                <p>만든 프로젝트</p>
                <p class="projectsNum mt-4">10</p>
              </div>
            </div>
          </div>-->
          <!-- 상품 설명서 -->
          <div
            v-if="tabItem=='pjtInfo'"
            class="mt-2"
            style="text-align: center; background-color: #f8f9fa ;"
          >
            <img
              style="width: 60%"
              src="https://cdn.wadiz.kr/ft/images/green001/2020/0812/20200812123041902_22.png/wadiz/format/jpg/quality/80/optimize"
              alt
            />
            <img
              style="width: 60%"
              src="https://cdn.wadiz.kr/ft/images/green001/2020/0812/20200812123820943_38.png/wadiz/format/jpg/quality/80/optimize"
              alt
            />
            <img
              style="width: 60%"
              src="https://cdn.wadiz.kr/ft/images/green001/2020/0812/20200812124034563_3.png/wadiz/format/jpg/quality/80/optimize"
              alt
            />
            <img
              style="width: 60%"
              src="https://cdn.wadiz.kr/ft/images/green001/2020/0812/20200812130135430_57.png/wadiz/format/jpg/quality/80/optimize"
              alt
            />
          </div>
          <!-- 리뷰 -->
          <div v-if="tabItem=='reviews'" class="my-4" style="background-color: #f8f9fa ;">
            <div>
              <p style="margin-left:10px">총 25건의 후기가 있습니다.</p>
              <div style="text-align: center;">
                <img style="width: 65%" src="../../assets/평점.png" alt />
              </div>
              <h5>전체 후기</h5>
              <!-- 1 -->
              <div class="mt-3">
                <v-icon style="display:inline" size="38">mdi-emoticon-happy-outline</v-icon>
                <strong>
                  <p style="display:inline; margin: 2px 0px 0px 3px;">솜사탕강쥐</p>
                </strong>
                <span class="ml-2" style="color: grey">2020.09.15</span>
                <p class="mx-5">{{ reviews }}</p>
              </div>
              <!-- 2 -->
              <div class="mt-3">
                <v-icon style="display:inline" size="38">mdi-emoticon-happy-outline</v-icon>
                <strong>
                  <p style="display:inline; margin: 2px 0px 0px 3px;">동백꽃</p>
                </strong>
                <span class="ml-2" style="color: grey">2020.09.15</span>
                <p class="mx-5">{{ reviews }}</p>
              </div>
              <div class="mt-3">
                <v-icon style="display:inline" size="38">mdi-emoticon-happy-outline</v-icon>
                <strong>
                  <p style="display:inline; margin: 2px 0px 0px 3px;">싸피인</p>
                </strong>
                <span class="ml-2" style="color: grey">2020.09.15</span>
                <p class="mx-5">{{ reviews }}</p>
              </div>
            </div>
          </div>
        </v-tab-item>
      </v-tabs-items>
    </div>
  </div>
</template>

<script>
import "../../../public/css/ShoppingDetail.scss";
import Navbar from "../../components/Navbar.vue";

export default {
  components: {
    Navbar
  },
  data() {
    return {
      currentItem: "tab-Web",
      tabItems: ["pjtInfo", "reviews"],
      items: [
        {
          src:
            "https://cdn.wadiz.kr/wwwwadiz/green001/2020/0811/20200811193143172_73945.jpg/wadiz/format/jpg/quality/80/optimize",
        },
        {
          src:
            "https://cdn.wadiz.kr/wwwwadiz/green001/2020/0811/20200811193147129_73945.jpg/wadiz/format/jpg/quality/80/optimize",
        },
        {
          src:
            "https://cdn.wadiz.kr/wwwwadiz/green001/2020/0811/20200811193112234_73945.jpg/wadiz/format/jpg/quality/80/optimize",
        },
      ],
      detailItems: {
        title: "Ostay 다이슨 헤어드라이어",
        category:
          "Small plates, salads & sandwiches - an intimate setting with 12 indoor seats plus patio seating.",
        perchase: "1,370,502",
        percent: "95",
      },
      reviews:
        "저희 집 강아지가 좋아하는 드라이기를 드디어 찾았네요. 강아지랑 같이 쓰려고 샀어요. 잘 쓸게요. 많이 파세요..저희 집 강아지가 좋아하는 드라이기를 드디어 찾았네요. 강아지랑 같이 쓰려고 샀어요. 잘 쓸게요. 많이 파세요..",
    };
  },
};
</script>

<style scoped>
.shoppingTitle {
  /* background-image: url("../../assets/커플티2.png"); */
  width: 100% !important;
}
.tabBtn {
  font-style: normal;
  font-size: 15px !important;
}
.entirePage {
  margin: 30px 150px 0px 200px;
}
.shoppingDetail {
  height: 500px;
}
.shoppingimg {
  width: 45%;
  /* height: 20% ; */
  margin-right: 7%;
  float: left;
}
.categoryBadge {
  background-color: rgb(123, 197, 254) !important;
  color: white !important;
}
.perchaseBtn {
  width: 83%;
  justify-content: center;
  /* text-align: center; */
  color: white !important;
  background-color: rgb(22, 150, 245) !important;
}
.listTitle {
  margin-bottom: 0;
}
.otherCard {
  padding: 0 3px 0 0;
}
.maker {
  border-radius: 70%;
  width: 55px;
  height: 55px;
  overflow: hidden;
}
.makerImg {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
p {
  margin-bottom: 0;
}
.projectsNum {
  font-size: 3.3rem;
  font-weight: bold;
}
</style>