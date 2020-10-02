<template>
  <div class="shoppingDetail">
    <navbar />
    <!-- 상품 게시글 제목 -->
    <div class="shoppingImg" 
    v-bind:style="{ 'background-image': 'url(' + picture + ')' }"
    style="height: 300px; display: flex; justify-content: center; align-items: center"
    >
      <h4 class="intro">{{ items.onelineIntro }}</h4>
      <div class="img-cover"></div>
    </div>
    <div class="shoppingInfoBox">
      <div class="shoppingInfo">
        <!-- 좌측: 상품 사진 -->
        <div class="shoppingThumnail">
          <img :src="picture" alt="">
        </div>
        <!-- 우측: 상품 디테일 -->
        <div style="float: left; width:36%;">
          <!-- 기업명 -->
          <p>{{items.compname}}</p>
          <!-- 상품 제목 -->
          <h3 class="mt-2">{{items.saleBoardDto.pjtname}}</h3>
          <hr />
          <p style="font-size: 1.2em; margin-bottom: 4%">
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
              v-for="(value, key) in items.categorys"
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
            <h5>{{items.saleBoardDto.saleprice}}원</h5>
          </div>
          <!-- 태그 -->
          <strong>
            <p class="listTitle" style="margin-bottom: 1%">태그</p>
          </strong>
          <v-chip 
            v-for="(val, key) in items.tags"
            :key="key"
            class="mr-1" small
          >#{{val}}</v-chip>
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
            <strong>{{items.compname}}</strong>
          </p>
          <p style="font-size: 0.9rem;">{{ items.introduce }}</p>
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
          </div>
          <!-- 리뷰 -->
          <div v-if="tabItem=='reviews'" class="my-4" style="background-color: #f8f9fa ;">
            <div>
              <p style="margin-left:10px">총 25건의 후기가 있습니다.</p>
              <div style="text-align: center;">
                <img style="width: 65%" src="../../assets/평점.png" alt />
              </div>
              <div style="text-align: center;">
                <h2>상품 설명서 정확도 {{accuracy}}</h2>
                <h2>상품 만족도 {{satisfy}}</h2>
              </div>
              
              <!-- 리뷰 -->
              <div 
              class="mt-3"
              v-for="(review, i) in reviews"
              :key="i"
              >
                <v-icon style="display:inline" size="38">mdi-emoticon-happy-outline</v-icon>
                <strong>
                  <p style="display:inline; margin: 2px 0px 0px 3px;">솜사탕강쥐</p>
                </strong>
                <span class="ml-2" style="color: grey">{{ review.createdate}}</span>
                <p class="mx-5">{{ review.reviewexplain }}</p>
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
      reviewDate: '',
      shoppingPjt: '',
      picture: '',
      detailItems: {
        title: "Ostay 다이슨 헤어드라이어",
        category: {
          tech: "테크, 가전",
          home: "홈리빙",
        },
        perchase: "1,370,502",
      },
      reviews: [],
      accuracy: 0,
      satisfy: 0,
    };
  },
  created() {
    this.shoppingAddress = this.$route.params.address
    // 쇼핑 디테일 정보 
    axios
      .get(`${SERVER_URL}/sale/getDetail?address=${this.shoppingAddress}`)
      .then((res) => {
        this.items = res.data.object;
        console.log('이게 items')
        console.log(this.items)
        this.picture = this.items.saleBoardDto.picture;
        // 글쓴이 소개글 엔터 변환
        // this.investPjt.introduce = this.investPjt.introduce.split('\n').join('<br />');
        // 투자 설명서 엔터 변환
        // this.investPjt.editorhtml = this.investPjt.editorhtml.split('\n').join('<br />');
        // const cutUrl = this.shoppingPjt.picture
      })
      .catch((error) => {
        console.log(error)
      });
  },
  mounted() {
    // 쇼핑 리뷰
    // page 수정 해야함! (반복문)
    const frm = new FormData();
    frm.append("address",this.shoppingAddress)
    axios.post(`${SERVER_URL}/sale/getReviews/0`, frm
    )
      .then(response => {
        console.log('리뷰data')
        this.reviews = response.data.object.list;
        // 날짜 slice
        for (var i = 0; i <= this.reviews.length; i++) {
          this.reviews[i].createdate = this.reviews[i].createdate.slice(0, 10);
          // console.log(this.reviews[i].createdate)
        }
        // console.dir(response)    
      })
    // 상품 만족도, 정확도 (리뷰)
    axios.get(`${SERVER_URL}/sale/accuracy?address=${this.shoppingAddress}`)
      .then(res => {
        this.satisfy = res.data.object.satisfy
        this.accuracy = res.data.object.accuracy
      })
      .catch(error => {
        console.log(error)
        console.log('아니얌')
      })
  },
}
</script>

<style scoped>
.shoppingImg {
  position: relative;
  background-size: cover;
  background-position: center center;
  background-repeat: no-repeat;
  height: 100vh;
  /* opacity: 0.3; */
}
.img-cover{
   position: absolute;
   height: 100%;
   width: 100%;
   background-color: rgba(0, 0, 0, 0.6);                                                                 
   z-index:1;
}
.tabBtn {
  font-style: normal;
  font-size: 15px !important;
}
.shoppingInfoBox {
  /* margin: 0 150px; */
  z-index: 3;
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
/* .shoppingimg {
  width: 45%; */
  /* height: 20% ; */
  /* margin-right: 7%;
  float: left;
} */
.categoryBadge {
  background-color: rgb(123, 197, 254) !important;
  color: white !important;
}
.perchaseBtn {
  width: 100%;
  height: 42px !important;
  justify-content: center;
  text-align: center;
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
.intro {
  position: absolute;
  top:35%;
  left:50%;
  transform: translate(-50%, -50%);                                                                   
  color: white;
  z-index: 2;
  text-align: center;
}
</style>