<template>
  <div class="investdetail">
    <navbar/>
    <!-- 투자 대표 사진 -->
    <div class="investImg" style="height: 300px; background-color: lightgrey; display: flex; justify-content: center; align-items: center">
    </div>
    <!-- 투자 정보 -->
    <div class="investInfoBox">
      <div class="investInfo">
        <!-- 좌측: 상품 사진 -->
        <div class="investThumnail">
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
          <!-- 기업명
          <p>다이슨</p> -->
          <!-- 상품 제목 -->
          <h3>{{ detailItems.title }}</h3>
          <hr />
          <p style="font-size: 1.2em">
              <span style="color: rgb(22, 150, 245);">{{ detailItems.invest }}</span>명 투자 중
          </p>
          <!-- 카테고리 -->
          <div style="margin-bottom: 4%">
            <strong>
              <p class="listTitle" style="margin-bottom: 1%">투자 카테고리</p>
            </strong>
            <v-chip v-for="(value, key) in detailItems.category" :key="key" class="categoryBadge mr-1" small>{{value}}</v-chip>
          </div>
          <!-- 달성 목표 금액 -->
          <div style="margin-bottom: 4%">
            <strong>
              <p class="listTitle" style="margin-bottom: 1%">모인금액</p>
            </strong>
            <h3 class="totalPrice">{{detailItems.totalPrice}}</h3>
            <strong><span>원 달성</span></strong>
            <div style="display: inline-block;">
              <h3 style="display: inline-block; color:rgb(22, 150, 245)">{{detailItems.percent}}%</h3>
              <h5 style="display: inline-block; color:rgb(123, 197, 254)">달성</h5>
            </div>
            <p style="font-size: 15px">목표 금액인 <strong>{{detailItems.goalPrice}}</strong>원이 모여야만 결제됩니다.</p>
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
          <v-btn class="investBtn white--text">
            <v-icon size="18" class="mr-1">mdi-cart-outline</v-icon>투자 하기
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
    </div>
  </div>
</template>

<script>
import Navbar from "../../components/Navbar.vue";
import "../../../public/css/InvestDetail.scss";

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
        invest: "1,370,502",
        category: {
          tech: "테크, 가전",
          home: "홈리빙",
        },
        totalPrice: "1,000,000",
        goalPrice: "1,500,000",
        percent: "75",
      },
    }
  }
}
</script>

<style scoped>
.investImg {
  background-image: url("https://cdn.wadiz.kr/wwwwadiz/green001/2020/0811/20200811193143172_73945.jpg/wadiz/format/jpg/quality/80/optimize");
  background-size: cover;
  background-position: center center;
  background-repeat: no-repeat;
  opacity: 0.3;
}
.investInfoBox {
  margin: 0 150px;
  position: absolute;
  background: white;
  top: 250px;
  padding: 20px;
  border-radius: 4px;
}
.investInfo {
  height: 500px;
}
.investThumnail {
  width: 45%;
  margin-right: 7%;
  float: left;
}
.categoryBadge {
  background-color: rgb(123, 197, 254) !important;
  color: white !important;
}
.totalPrice {
  display:inline-block; 
  color: rgb(22, 150, 245);
  margin-right: 5px;
  font-weight: 600;
}
.investBtn {
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
</style>