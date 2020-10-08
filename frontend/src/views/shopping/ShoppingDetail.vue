<template>
  <div class="shoppingDetail">
    <HomeNav />
    
    <div>
    <!-- 상품 게시글 제목 -->
    <div
      class="shoppingImg"
      v-bind:style="{ 'background-image': 'url(' + picture + ')' }"
      style="
        height: 300px;
        display: flex;
        justify-content: center;
        align-items: center;
      "
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
        <div style="float: left; width: 36%">
          <!-- 기업명 -->
          <p class="my-2">{{ items.compname }}</p>
          <!-- 상품 제목 -->
          <h3 class="mt-2">{{ dto.pjtname }}</h3>
          <hr />
          <p style="font-size: 1.2em; margin-bottom: 4%">
            <strong>
              <span style="color: rgb(22, 150, 245)">{{this.perchased}}</span> </strong
            >개 구매 중
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
              >{{ value }}</v-chip
            >
          </div>
          <!-- 가격 -->
          <div style="margin-bottom: 4%">
            <strong>
              <p class="listTitle" style="margin-bottom: 1%">판매가</p>
            </strong>
            <h5>{{ dto.saleprice }}원</h5>
          </div>
          <!-- 태그 -->
          <strong>
            <p class="listTitle" style="margin-bottom: 1%">태그</p>
          </strong>
          <v-chip v-for="(val, key) in items.tags" :key="key" class="mr-1" small
            >#{{ val }}</v-chip
          >
          <div v-if="!items.isopen" style="height: 110px; display: flex; justify-content: center; align-items: center;">
            <h5 style="margin-top: 10px"><strong>D-{{items.opendate}}</strong>일 후 오픈예정입니다.</h5>
          </div>
          <div v-else style="height:110px">
          </div>
          
          <v-btn 
            @click="selectCount" 
            class="perchaseBtn white--text"
            :disabled="islogin == false || items.isopen == false"
          >
            <v-icon size="18" class="mr-1">mdi-cart-outline</v-icon>상품 구매
          </v-btn>

            <v-dialog max-width="500" min-height="370" v-model="perchaseDialog">

            <v-card flat tile>
              <!-- 모달 --> 
              <v-card-text style="height:270;" class="pa-1">
                <v-list>
                  <v-toolbar dense elevation="1">
                    <h5 class="mx-auto">상품 구매</h5>
                  </v-toolbar>
                  <div style="text-align: center; margin-top: 30px;">
                    <h4>{{ dto.pjtname }}</h4> 
                    <img :src="dto.picture" width="65%" style="border-radius: 15px;" alt="picture">
                    <p class="my-2">구매 수량을 선택해주세요!</p>
                  </div>
                  <!-- 수량 -->
                  <div>
                    <div style="position:relative">
                    <v-text-field
                      class="ml-50"
                      style="width:50%; position:absolue; left: 25%"
                      v-model="numberCount"
                      hide-details
                      outlined=""
                      type="number"
                    />
                    <div style="position:absolute; bottom: 29%; right: 33%">
                      <span>개</span>
                    </div>
                  </div>
                  </div>
                </v-list>
              </v-card-text>
                </v-card>
                <v-card-actions style="background-color: white; justify-content:center"> 
                  <v-btn text @click="onPerchase" color="white" style="background-color:rgb(22, 150, 245)">구매</v-btn>
                  <v-btn text @click="perchaseDialog=false" style="background-color:#ECEFF1">취소</v-btn>
                </v-card-actions>
              </v-dialog>   

          <!-- 좋아요 & 문의 버튼 -->
          <div style="display: flex; margin-top: 15px">
            <button 
              :disabled="islogin == false"
              @click="likeBtn" 
              style="flex: 1">
              <div class="btns" style="margin-right: 5px">
                <v-icon size="20" class="mr-2 like">mdi-heart</v-icon
                >{{ likeCount }}
              </div>
            </button>
            <button 
              :disabled="islogin == false || this.maker.oauthId == userid"
              style="flex: 1">
              <div @click="onChat()" class="btns">
                <v-app class="vApp"></v-app>
                <div>
                  <v-dialog max-width="800" min-height="500" v-model="chatroom">
                    <ChatRoom @closeChatRoom="closeChatRoom"></ChatRoom>
                  </v-dialog>
                </div>
                <v-icon size="20" class="mr-1" 
                  >mdi-message-bulleted</v-icon
                >문의
              </div>
            </button>
          </div>
        </div>
      </div>
      <!-- 금손님 정보 -->
      <div class="infoBottom pt-4">
        <hr style="width: 95%" />
        <div class="ml-5">
          <strong>
            <p class="mb-3" style="font-size: 1.4rem">금손님 정보</p>
          </strong>
          <div class="maker" style="float: left">
            <img class="makerImg" :src="makerImg" alt />
          </div>
          <div style="float: left; margin: 3px 0 0 15px">
            <p v-if="items.compName" style="font-size: 1rem; margin-bottom: 5px">
              (주){{ items.compname }}
            </p>
              <!-- <router-link style="color: black;"
              :to="{ name: 'Mypage', params: { userid: maker.oauthId } }"
              > -->
              <button :disabled="!islogin" @click="onpageuserpage">
                <strong class="mr-2">{{ makerName }}</strong>
              </button>
              <!-- </router-link> -->
              <div v-if="isurl" style="display: inline-block;">
              <v-chip @click="visit(items.url)" label small class="visit px-1"
                >사이트 방문</v-chip>
              </div>
            <p v-html="items.introduce" style="font-size: 1rem">{{ items.introduce }}</p>
          </div>
        </div>
        <hr style="margin-top: 100px; width: 95%" class="mx-auto" />
        <!-- 탭 -->
        <div class="mt-20">
          <v-tabs
            style="background-color: #f8f9fa"
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
              <v-icon class="tabBtn" v-if="tabItem == 'pjtInfo'"
                >상품 설명서</v-icon
              >
              <v-icon class="tabBtn" v-if="tabItem == 'reviews'">리뷰</v-icon>
            </v-tab>
          </v-tabs>
        </div>
        <br />
        <v-tabs-items v-model="currentItem">
          <v-tab-item
            v-for="tabItem in tabItems"
            :key="tabItem"
            :value="'tab-' + tabItem"
          >
            <!-- 상품 설명서 -->
            <div
              v-if="tabItem == 'pjtInfo'"
              class="mt-2"
              style="text-align: center; background-color: white"
            >
              <div v-html="items.editorhtml" class="editor">
                {{ items.editorhtml }}
              </div>
            </div>
            <!-- 리뷰 -->
            <div v-if="tabItem == 'reviews'" class="my-4">
              <div>
                <div
                  class="infoFrame mx-auto py-2 my-3"
                  style="text-align: center"
                >
                  <div class="row">
                    <div class="col-md-4">
                      <i
                        class="fas fa-comment-dots fa-4x my-3"
                        style="color: grey"
                      ></i
                      ><br />
                      <p
                        style="
                          font-size: 1.2rem;
                          color: #424242;
                          font-weight: bold;
                        "
                      >
                        전체 리뷰수
                      </p>
                      <h1
                        style="
                          font-size: 3rem;
                          display: inline-block;
                          font-weight: bold;
                        "
                      >
                        {{ reviewTotal }}
                      </h1>
                      <span style="font-weight: bold"> 개</span>
                    </div>
                    <div class="col-md-4 px-3">
                      <i
                        class="fas fa-star fa-4x my-3"
                        style="color: #26c6da"
                      ></i
                      ><br />
                      <p
                        style="
                          font-size: 1.2rem;
                          color: #424242;
                          font-weight: bold;
                        "
                      >
                        상품 만족도
                      </p>
                      <h1
                        style="
                          font-size: 3rem;
                          display: inline-block;
                          color: #26c6da;
                          font-weight: bold;
                        "
                      >
                        {{ satisfy }}
                      </h1>
                      <span style="font-weight: bold"> 점</span>
                    </div>
                    <div class="col-md-4 px-3">
                      <i
                        class="fas fa-file-signature fa-4x my-3 ml-2"
                        style="color: #29b6f6"
                      ></i
                      ><br />
                      <p
                        style="
                          font-size: 1.2rem;
                          color: #424242;
                          font-weight: bold;
                        "
                      >
                        설명서 정확도
                      </p>
                      <h1
                        style="
                          font-size: 3rem;
                          display: inline-block;
                          font-weight: bold;
                          color: #29b6f6;
                        "
                      >
                        {{ accuracy }}
                      </h1>
                      <span style="font-weight: bold"> 점</span>
                    </div>
                  </div>
                </div>
                <!-- 개별 리뷰 -->
                <!-- 1. 리뷰 없는 경우 -->
                <div v-if="!totalreview" class="my-5 pb-5" style="text-align:center">
                  <h5>등록된 리뷰가 없습니다.</h5>
                </div>
                <!-- 2. 리뷰 있는 경우 -->
                <div 
                  class="mt-5 mx-5"
                  style="75%"
                  v-for="(review, i) in reviews"
                  :key="i"
                >
                  <img
                    class="reviewPicture"
                    :src="review.picture"
                    alt="reviewPicture"
                  />
                  <div style="display: flex; width: 78%">
                    <div class="userImg">
                      <img
                        class="userImage"
                        :src="review.profile"
                        alt="profile"
                      />
                    </div>
                    <p
                      class="my-1 mx-2"
                      style="font-size: 1.1rem; font-weight: bold"
                    >
                      {{ review.userid }}
                    </p>
                    <p class="my-1 ml-1" style="color: grey">
                      {{ review.createdate }}
                    </p>
                    <br />
                  </div>
                  <div style="width: 78%">
                    <div style="display: flex" class="ml-4">
                      <p class="ml-2" style="font-weight: bold">
                        상품 만족도
                        <v-chip small label class="ma-2 px-2 satisfaction">
                          {{ review.satisfied }}
                        </v-chip>
                      </p>
                      <p class="ml-3" style="font-weight: bold">
                        설명서 정확도
                        <v-chip small label class="ma-2 px-2 accuracy">
                          {{ review.similar }}
                        </v-chip>
                      </p>
                    </div>
                    <div class="ml-3">
                      <p class="mt-1 ml-3">{{ review.reviewexplain }}</p>
                    </div>
                  </div>
                  <hr />
                </div>
              </div>
            </div>
          </v-tab-item>
        </v-tabs-items>
      </div>
    </div>
    </div>
  </div>
</template>

<script>
import "../../../public/css/ShoppingDetail.scss";
// import Navbar from "../../components/Navbar.vue";
import HomeNav from "../../components/HomeNav.vue";
import axios from "axios";
import store from "../../store/index.js";
import ChatRoom from "@/views/mypage/ChatRoom.vue";
import Swal from "sweetalert2";

const SERVER_URL = "https://www.twojob.ga/api";

export default {
  components: {
    // Navbar,
    HomeNav,
    ChatRoom,
  },
  data() {
    return {
      // 로그인 
      islogin: store.state.isSigned,
      userid: store.state.userInfo.id,
      currentItem: "tab-Web",
      tabItems: ["pjtInfo", "reviews"],
      items: [],
      isurl: "",
      dto: {},
      reviewDate: "",
      shoppingPjt: "",
      picture: "",
      // 리뷰
      totalreview: 0,
      reviews: {},
      accuracy: 0,
      satisfy: 0,
      reviewTotal: 0,
      // 좋아요
      isliked: false,
      likeCount: 0,
      likeState: 0,
      // 금손님
      makerImg: '',
      makerName:'',
      maker: '',
      chatroom: false,
      // 블록체인, 결제
      investaddress:'',
      perchased: 0,
      perchaeCount: 0,
      perchaseDialog: false,
      numberCount: 1,
    };
  },
  created() {},
  mounted() {
    // 판매개수
    axios
      .get(`${SERVER_URL}/funding/gettotalsell?campaignId=${this.investaddress}`)
      .then((res) => {
        this.perchased = res.data
        // console.log('판매개수')
        // console.log(this.perchased)
      })
    // 디테일 정보
    this.shoppingAddress = this.$route.params.address
    // 쇼핑 디테일 정보 
    const frm = new FormData();
    frm.append("address",this.shoppingAddress)
    frm.append("userid", store.state.userInfo.id)
    axios
      .post(`${SERVER_URL}/sale/getDetail`, frm)
      .then((res) => {
        this.items = res.data.object.object;
        this.investaddress = this.items.saleBoardDto.investaddress
        this.isurl = this.items.url
        // 오픈예정
        const year = this.items.saleBoardDto.startdate.substring(0, 4);
        const month = this.items.saleBoardDto.startdate.substring(5, 7);
        const day = this.items.saleBoardDto.startdate.substring(8, 10);
        var Dday = new Date(year, month-1, day);
        var now = new Date();
        var gap = now.getTime() - Dday.getTime();
        var result = Math.floor(gap / (1000 * 60 * 60 * 24)) * -1;
        this.$set(this.items, "opendate", result);
        if(result > 0){
          this.$set(this.items, "isopen", false)
        }else{
          this.$set(this.items, "isopen", true)
        }
        // 좋아요
        this.likeCount = res.data.object.likecount;
        this.isliked = res.data.object.like;
        if (this.isliked) {
          $(".like").css("color", "red");
        } else {
          $(".like").css("color", "rgba(0, 0, 0, 0.54)");
        }
        this.dto = this.items.saleBoardDto
        this.picture = this.items.saleBoardDto.picture;
        const userid= this.items.saleBoardDto.userid
        // 금손 정보
        axios
          .post(`${SERVER_URL}/util/userinfo?userid=${userid}`)
          .then((res) => {
            this.maker = res.data.object
            this.makerName = res.data.object.name
            if (res.data.object.profileImg == null) {
            this.makerImg =
            "https://file3.instiz.net/data/cached_img/upload/2020/02/26/12/f7975c2dacddf8bf521e7e6d7e4c02ee.jpg";
            } else {
              this.makerImg = res.data.object.profileImg;              
            }  
          });
        // 글쓴이 소개글 엔터 변환
        this.items.introduce = this.items.introduce
          .split("\n")
          .join("<br />");
        // 투자 설명서 엔터 변환
        this.items.editorhtml = this.items.editorhtml
          .split("\n")
          .join("<br />");
      })
      .catch((error) => {
        // console.log(error);
      });

    // 쇼핑 리뷰
    const fm = new FormData();
    fm.append("address", this.shoppingAddress);
    axios.post(`${SERVER_URL}/sale/getReviews/0`, fm).then((response) => {
      this.reviews = response.data.object.reviews;
      this.totalreview = response.data.object.totalreviewcount
      
      if (this.totalreview) { 
      // 날짜 slice
      for (let i = 0; i < this.reviews.length; i++) {
        this.reviews[i].createdate = this.reviews[i].createdate.slice(0, 10);
        const userId = this.reviews[i].userid;

        axios
          .post(`${SERVER_URL}/util/userinfo?userid=${userId}`)
          .then((res) => {
            this.reviews[i].userid = res.data.object.name;
            // console.log(res.data.object)
            if (res.data.object.profileImg == null) {
            this.reviews[i].profile = "https://file3.instiz.net/data/cached_img/upload/2020/02/26/12/f7975c2dacddf8bf521e7e6d7e4c02ee.jpg";
            } else {
              this.reviews[i].profile = res.data.object.profileImg;
            }
          })
          .catch((err) => {
            // console.log(err)
          })
        }
      }
      // console.dir(response)
    });
    // 상품 만족도, 정확도 평균(리뷰)
    axios
      .get(`${SERVER_URL}/sale/accuracy?address=${this.shoppingAddress}`)
      .then((res) => {
        this.satisfy = res.data.object.satisfy;
        this.accuracy = res.data.object.accuracy;
        this.reviewTotal = res.data.object.reviewtotalcount;
      })
      .catch((error) => {
        // console.log(error);
      });
  },
  methods: {
    onpageuserpage(){
      if(islogin){
        this.$router.push(`/mypage/${this.maker.oauthId}`)
      }
    },
    // 상품 구매 전 모달
    selectCount() {
      this.perchaseDialog = true;
    },
    // 상품 구매
    onPerchase() {
      const fd = new FormData();
      fd.append("campaignId ", this.investaddress);
      fd.append("accessToken ", store.state.accessToken);
      fd.append("count", this.numberCount);
      fd.append("money", this.dto.saleprice);
      axios
        .post(`${SERVER_URL}/funding/sellitem`, fd)
        .then((res) => {
          // console.log('구매 된겁니까??')
          // console.log(res)
        })
        let timerInterval;
        Swal.fire({
          title: '구매완료까지',
          timer: 5000,
          timerProgressBar: true,
          onBeforeOpen: () => {
            Swal.showLoading();
            timerInterval = setInterval(() => {
              const content = Swal.getContent();
              if (content) {
                const b = content.querySelector("b");
                if (b) {
                  b.textContent = Swal.getTimerLeft();
                }
              }
            }, 100);
           },
          onClose: () => {
            clearInterval(timerInterval);
          },
        })
        .then((res) => {
          Swal.fire({
            icon: "success",
            title: "",
            text: "성공적으로 구매되었습니다.",
            showConfirmButton: false,
          })
          this.perchaseDialog = false;
        })
    },
        

    closeChatRoom() {
      this.chatroom = false;
      store.commit("setAsk", null);
    },
    onChat() {
      // window.open("");
      this.chatroom = true;
      // console.log("oauthId " + this.maker.oauthId);
      store.commit("setAsk", this.maker.name, this.maker.oauthId);
      store.state.askuserid = this.maker.oauthId;
      // console.log("모달 열어보자" + this.chatroom);
      // this.$router.push("/chat")
    },
    visit(url) {
      window.open(url);
    },
    likeBtn() {
      axios
        .post(`${SERVER_URL}/util/createlike`, {
          address: this.shoppingAddress,
          userid: store.state.userInfo.id,
        })
        .then((res) => {
          this.likeCount = res.data.object.likecount;
          this.likeState = res.data.object.likestate;
          if (this.likeState == 1) {
            $(".like").css("color", "red");
          } else {
            $(".like").css("color", "rgba(0, 0, 0, 0.54)");
          }
        });
    },
  },
};
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
.img-cover {
  position: absolute;
  height: 100%;
  width: 100%;
  background-color: rgba(0, 0, 0, 0.6);
  z-index: 1;
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
  top: 35%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  z-index: 2;
  text-align: center;
}
.accuracy {
  background-color: #00b0ff !important;
  color: white;
}
.satisfaction {
  background-color: #4dd0e1 !important;
  color: white;
}
.reviewPicture {
  width: 130px;
  height: 90px;
  border-radius: 7px;
  float: right;
}
.infoFrame {
  width: 88%;
  background-color: #f8f8f8;
  border: 1px solid #cdd3d8;
  border-radius: 12px;
  box-shadow: 0 2px 4px 0 rgba(33, 37, 41, 0.11);
}
.infoBottom {
  background-color: white !important;
}
.visit {
  background-color: #4fc3f7 !important;
  color: white;
}
.a:hover {
  text-decoration: none!important;
}
</style>