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
          <v-carousel>
              <v-carousel-item
                :src="picture"
                reverse-transition="fade-transition"
                transition="fade-transition"
              ></v-carousel-item>
            </v-carousel>
        </div>
        <!-- 우측: 상품 디테일 -->
        <div style="float: left; width:36%;">
          <!-- 기업명 -->
          <p class="my-2">{{items.compname}}</p>
          <!-- 상품 제목 -->
          <h3 class="mt-2">{{dto.pjtname}}</h3>
          <hr />
          <p style="font-size: 1.2em; margin-bottom: 4%">
            <strong>
              <span style="color: rgb(22, 150, 245);">1500</span>
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
            <h5>{{dto.saleprice}}원</h5>
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
            <button @click="likeBtn" style="flex: 1;">
              <div class="btns" style="margin-right: 5px">
                <v-icon size="20" class="mr-2 like">mdi-heart</v-icon>{{likeCount}}
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
      <div class="infoBottom pt-4">
      <hr style="width:95%">
      <div class="ml-5">
        <strong>
          <p class="mb-3" style="font-size: 1.4rem;">금손님 정보</p>
        </strong>
        <div class="maker" style="float:left">
          <img class="makerImg" src="../../assets/금손.jpg" alt />
        </div>
        <div style="float:left; margin: 3px 0 0 15px;">
          <p style="font-size: 1.1rem; margin-bottom:5px;">
            <strong class="mr-2">{{items.compname}}</strong>
            <v-chip @click="visit(items.url)" label small class="visit px-1">바로가기</v-chip>
          </p>
          <p style="font-size: 1rem;">{{ items.introduce }}</p>
        </div>
      </div>
        <hr style="margin-top: 100px; width:95%;" class="mx-auto"/>
      <!-- 탭 -->
      <div class="mt-20">
      <v-tabs
        style="background-color:#f8f9fa;"
        fixed-tabs
        v-model="currentItem"
        slider-color="rgb(22, 150, 245);"
      >
        <v-tab
          style="background-color: #f8f9fa"
          v-for="tabItem in tabItems"
          :key="tabItem"
          :href="'#tab-' + tabItem"
        >
          <v-icon class="tabBtn" v-if="tabItem=='pjtInfo'">상품 설명서</v-icon>
          <v-icon class="tabBtn" v-if="tabItem=='reviews'">리뷰</v-icon>
        </v-tab>
      </v-tabs>
      </div>
      <br />
      <v-tabs-items v-model="currentItem">
        <v-tab-item v-for="tabItem in tabItems" :key="tabItem" :value="'tab-' + tabItem">
          <!-- 상품 설명서 -->
          <div
            v-if="tabItem=='pjtInfo'"
            class="mt-2"
            style="text-align: center; background-color: white ;"
          >
          <div v-html="items.editorhtml" class="editor">
            {{items.editorhtml}}
          </div>       
          </div>
          <!-- 리뷰 -->
          <div v-if="tabItem=='reviews'" class="my-4">
            <div>
                <div class="infoFrame mx-auto py-2 my-3" style="text-align: center;">  
                  <div class="row">
                    <div class="col-md-4">
                      <i class="fas fa-comment-dots fa-6x my-3" style="color: grey"></i><br>
                      <p style="font-size:1.2rem; color:#424242; font-weight: bold;">전체 리뷰수</p>
                      <h1 style="font-size:3rem; display:inline-block; font-weight: bold">3</h1><span style="font-weight: bold"> 개</span>
                    </div>
                    <div class="col-md-4 px-3">
                      <i class="fas fa-star fa-6x my-3" style="color:#26C6DA"></i><br>
                      <p style="font-size:1.2rem; color:#424242; font-weight: bold;">상품 만족도</p>
                      <h1 style="font-size:3rem; display:inline-block; color:#26C6DA;font-weight: bold">{{satisfy}}</h1><span style="font-weight: bold"> 점</span>
                    </div>
                    <div class="col-md-4 px-3">
                      <i class="fas fa-file-signature fa-6x my-3 ml-2" style="color: #29B6F6"></i><br>
                      <p style="font-size:1.2rem; color:#424242; font-weight: bold;">설명서 정확도</p>
                      <h1 style="font-size:3rem; display:inline-block; font-weight: bold; color: #29B6F6">{{accuracy}}</h1><span style="font-weight: bold"> 점</span>
                    </div>
                    </div>
                  </div>
              <!-- 개별 리뷰 -->
              <div 
              class="mt-5 mx-5"
              style="75%"
              v-for="(review, i) in reviews"
              :key="i"
              >
                <img class="reviewPicture" :src="review.picture" alt="reviewPicture">
                <div style="display: flex; width: 78%">
                <div class="userImg" >
                  <img class="userImage" :src="review.profile" alt="profile">
                </div>
                
                <p class="my-1 mx-2" style="font-size: 1.1rem; font-weight: bold;">{{review.userid}}</p>
                <p class="my-1 ml-1" style="color: grey">{{review.createdate}}</p><br>
                </div>

                <div style="width: 78%">
                <div style="display:flex;" class="ml-4">
                  <p class="ml-2" style="font-weight: bold">상품 만족도 
                    <v-chip
                      small
                      label
                      class="ma-2 px-2 satisfaction"
                    >
                      {{review.satisfied}}
                    </v-chip>
                  </p>
                  <p class="ml-3" style="font-weight: bold">설명서 정확도
                    <v-chip
                      small
                      label
                      class="ma-2 px-2 accuracy"
                    >
                      {{review.similar}}
                    </v-chip>
                  </p>
                </div>
                <div class="ml-3">
                  <p class="mt-1 ml-3">{{review.reviewexplain}}</p>
                </div>
                </div>
                <hr>
              </div>
            </div>
          </div>
        </v-tab-item>
      </v-tabs-items>
    </div>
  </div>
  </div>
</template>

<script>
import "../../../public/css/ShoppingDetail.scss";
import Navbar from "../../components/Navbar.vue";
import axios from 'axios';
import store from "../../store/index.js";

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
      dto: {},
      reviewDate: '',
      shoppingPjt: '',
      picture: '',
      reviews: {},
      accuracy: 0,
      satisfy: 0,
      // 좋아요
      isliked: false,
      likeCount: 0,
      likeState: 0,
    };
  },
  created() {
    this.shoppingAddress = this.$route.params.address
    // 쇼핑 디테일 정보 
    axios
      .get(`${SERVER_URL}/sale/getDetail?address=${this.shoppingAddress}`)
      .then((res) => {
        this.items = res.data.object;
        // console.log('쇼핑 아이템즈')
        // console.log(this.items)
        this.dto = res.data.object.saleBoardDto
        this.picture = this.items.saleBoardDto.picture;
        // 글쓴이 소개글 엔터 변환
        this.items.introduce = this.items.introduce.split('\n').join('<br />');
        // 투자 설명서 엔터 변환
        this.items.editorhtml = this.items.editorhtml.split('\n').join('<br />');
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
        this.reviews = response.data.object.list;
        // 날짜 slice
        for (let i = 0; i < this.reviews.length; i++) {
          this.reviews[i].createdate = this.reviews[i].createdate.slice(0, 10);
          const userId = this.reviews[i].userid 
          axios.post(`${SERVER_URL}/util/userinfo?userid=${userId}`)
          .then(res => {
            this.reviews[i].userid = res.data.object.name;
            this.reviews[i].profile = res.data.object.profileImg;
          })
        }
        // console.dir(response)    
      })
    // 상품 만족도, 정확도 평균(리뷰)
    axios.get(`${SERVER_URL}/sale/accuracy?address=${this.shoppingAddress}`)
      .then(res => {
        this.satisfy = res.data.object.satisfy
        this.accuracy = res.data.object.accuracy
      })
      .catch(error => {
        console.log(error)
      })
  },
  methods: {
    visit(url) {
      window.open(url);
    },
    likeBtn() {
      axios.post(`${SERVER_URL}/util/createlike`, {
        address: this.shoppingAddress,
        userid: store.state.userInfo.id,
      }).then(res => {
        this.likeCount = res.data.object.likecount;
        this.likeState = res.data.object.likestate;
        if (this.likeState == 1) {
          $('.like').css('color', 'red')
        } else {
          $('.like').css('color', 'rgba(0, 0, 0, 0.54)')
        }
      })
    },
  }
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
  padding: 0 17%;
  border-radius: 4px;
}
.shoppingInfo {
  overflow: hidden;
  width: 100%;
  background: white;
  padding: 2%;
}
.shoppingThumnail {
  width: 50%;
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
.userImg {
  border-radius: 70%;
  width: 30px;
  height: 30px;
  overflow: hidden;
}
.makerImg {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.userImage {
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
.accuracy {
  background-color: #00B0FF!important;  
  color: white;
}
.satisfaction {
  background-color: #4DD0E1!important;
  color: white;
}
.reviewPicture {
  width: 130px;
  height: 90px;
  border-radius: 7px;
  float:right;
}
.infoFrame {
  width: 88%;
  background-color: #f8f8f8 ;
  border: 1px solid #cdd3d8;
  border-radius: 12px;
  box-shadow: 0 2px 4px 0 rgba(33, 37, 41, 0.11);
}
.infoBottom {
  background-color: white !important;
}
.visit {
  background-color: #4FC3F7 !important;
  color: white;
}
</style>