<template>
  <div class="shoppingDetail">
    <navbar />
    <!-- 상품 게시글 제목 -->
    <div class="shoppingImg" 
    style="height: 300px; background-color: lightgrey; display: flex; justify-content: center; align-items: center"
    ></div>
    <div class="shoppingInfoBox">
      <div class="shoppingInfo">
        <!-- 좌측: 상품 사진 -->
        <div class="shoppingThumnail">
          <v-carousel>
            <v-carousel-item
              v-for="(item,i) in picitems"
              :key="i"
              :src="item.src"
              reverse-transition="fade-transition"
              transition="fade-transition"
            ></v-carousel-item>
          </v-carousel>
        </div>
        <!-- 우측: 상품 디테일 -->
        <div style="float: left; width:36%;">
          <!-- 기업명 -->
          <p>{{items.compname}}</p>
          <!-- 상품 제목 -->
          <h3>{{ items.introduce }}</h3>
          <!-- 지울거임 -->
          <p>{{items.saleBoardDto.pjtname}}</p>
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
            <v-chip 
              v-for="(value, key) in detailItems.category"
              :key="key"
              class="categoryBadge mr-1" 
              small
            >{{value}}</v-chip>
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
          <!-- 좋아요 & 문의 버튼 -->
          <div style="display: flex; margin-top: 15px;">
            <button style="flex: 1;">
              <div class="btns" style="margin-right: 5px">
                <v-icon size="20" class="mr-2">mdi-heart</v-icon>22
              </div>
            </button>
            <button style="flex: 1;">
              <div class="btns">
                <v-icon size="20" class="mr-1">mdi-message-bulleted</v-icon>문의
              </div>
            </button>
          </div>
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
import axios from 'axios';

const SERVER_URL = "http://j3b102.p.ssafy.io:8080";

export default {
  components: {
    Navbar,
  },
  data() {
    return {
      currentItem: "tab-Web",
      tabItems: ["pjtInfo", "reviews"],
      items: [],
      picitems: [
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
        category: {
          tech: "테크, 가전",
          home: "홈리빙",
        },
        perchase: "1,370,502",
        percent: "95",
      },
      reviews:
        "저희 집 강아지가 좋아하는 드라이기를 드디어 찾았네요. 강아지랑 같이 쓰려고 샀어요. 잘 쓸게요. 많이 파세요..저희 집 강아지가 좋아하는 드라이기를 드디어 찾았네요. 강아지랑 같이 쓰려고 샀어요. 잘 쓸게요. 많이 파세요..",
    };
  },
  created() {
    // 쇼핑 pjt 디테일 정보 
    // address 변수값으로 넣기
    axios
      .get(`${SERVER_URL}/sale/getDetail?address=239a52e9-380b-4e98-a641-b33c749162ef`)
      .then((res) => {
        this.items = res.data.object;
        console.log('이게 items')
        console.log(this.items)
      })
      .catch((error) => {
        console.log('dto찾는 items...')
        console.log(error)
      });

    // 쇼핑 리뷰 조회
    // page값, address 변수로 넣기 (0 => 페이지 길이만큼 반복문)
    axios
      .post(`${SERVER_URL}/sale/getReviews/0`, {
        address: this.shoppingAddress,
        page: 0,
      })
      .then((response) => {
        console.log('리뷰 조회 성공!')
        console.log(response)
      })
      .catch((error) => {
        console.log(error)
      })
  },
  mounted() {
    this.shoppingAddress = this.$route.params.address
    const frm = new FormData();
    frm.append("address",this.shoppingAddress)
    axios.post(`${SERVER_URL}/sale/getReviews/0`, frm
    )
      .then(response => {
        console.log(response)
        console.dir(response)    
      })
  },
}
</script>

<style scoped>
.shoppingImg {
  background-image: url("https://cdn.wadiz.kr/wwwwadiz/green001/2020/0811/20200811193143172_73945.jpg/wadiz/format/jpg/quality/80/optimize");
  background-size: cover;
  background-position: center center;
  background-repeat: no-repeat;
  opacity: 0.3;
}
.tabBtn {
  font-style: normal;
  font-size: 15px !important;
}
.shoppingInfoBox {
  /* margin: 0 150px; */
  position: absolute;
  width: 100%;
  top: 250px;
  padding: 0 10%;
  border-radius: 4px;
}
.shoppingInfo {
  overflow: hidden;
  width: 100%;
  background: white;
  padding: 2%;
}
.shoppingThumnail {
  width: 45%;
  margin-right: 7%;
  float: left;
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
  width: 100%;
  height: 42px !important;
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
.btns {
  height: 38px;
  text-align: center;
  line-height: 38px;
  border: 1px solid lightgray;
}
</style>