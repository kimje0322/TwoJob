<template>
  <div class="investdetail">
    <navbar />
    <!-- 투자 대표 사진 -->
    <div
      class="investImg"
      style="height: 300px; background-color: lightgrey; display: flex; justify-content: center; align-items: center"
    ></div>
    <!-- 투자 정보 -->
    <div class="investInfoBox">
      <div class="investInfo">
        <div style="overflow: hidden">
          <!-- 좌측: 상품 사진 -->
          <div class="investThumnail">
            <v-carousel>
              <!-- v-for="(item,i) in items"
                :key="i" -->
              <v-carousel-item
                :src="investPjt.picture"
                reverse-transition="fade-transition"
                transition="fade-transition"
              ></v-carousel-item>
            </v-carousel>
          </div>
          <!-- 우측: 상품 디테일 -->
          <div style="float: left; width:36%;">
            <!-- 기업명
            <p>다이슨</p>-->
            <!-- 상품 제목 -->
            <h3>{{ investPjt.pjtName }}</h3>
            <hr />
            <p style="font-size: 1.2em">
              <span style="color: rgb(22, 150, 245);">없음</span>명 투자 중
            </p>
            <!-- 카테고리 -->
            <div style="margin-bottom: 4%">
              <strong>
                <p class="listTitle" style="margin-bottom: 1%">투자 카테고리</p>
              </strong>
              <v-chip
                v-for="(value, key) in investPjt.categorys"
                :key="key"
                class="categoryBadge mr-1"
                small
              >{{value}}</v-chip>
            </div>
            <!-- 달성 목표 금액 -->
            <div style="margin-bottom: 4%">
              <strong>
                <p class="listTitle" style="margin-bottom: 1%">모인금액</p>
              </strong>
              <h3 class="totalPrice">없음</h3>
              <strong>
                <span>원 달성</span>
              </strong>
              <div style="display: inline-block; margin-left: 5%">
                <h3
                  style="display: inline-block; color:rgb(22, 150, 245); font-weight: 600; margin-right: 5px"
                >없음%</h3>
                <h5 style="display: inline-block;">달성</h5>
              </div>
              <p style="font-size: 13px">
                목표 금액인
                <strong>{{investPjt.goalPrice}}</strong>원이 모여야만 결제됩니다.
              </p>
            </div>
            <!-- 마감일 -->
            <div style="margin-bottom: 4%;">
              <strong>
                <p class="listTitle" style="margin: 0 2% 1% 0; display: inline-block">{{investPjt.lastday}}일 남음</p>
              </strong>
              <span>{{investPjt.deadLine}} 24:00 마감</span>
            </div>
            <!-- 태그 -->
            <div style="margin-bottom: 3%">
              <strong>
                <p class="listTitle" style="margin-bottom: 1%">태그</p>
              </strong>
              <v-chip v-for="(tag, i) in investPjt.tags" :key="i" class="mr-1" small>#{{tag}}</v-chip>
            </div>
            <!-- 투자하기 버튼 -->
            <v-btn class="investBtn white--text" style="width: 100%; height: 42px">
              <v-icon size="25" class="mr-1">mdi-cash-usd</v-icon>투자 하기
            </v-btn>
            <!-- 좋아요 문의하기 버튼 -->
            <div style="display: flex; margin-top: 15px;">
              <button style="flex: 1;" @click="likebtn">
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
        <div class="mt-5" style="overflow: hidden;">
          <strong>
            <p class="mb-3">금손님 정보</p>
          </strong>
          <div class="maker" style="float:left">
            <img class="makerImg" src="../../assets/금손.jpg" alt />
          </div>
          <div style="float:left; margin: 3px 0 0 15px;">
            <span style="font-size: 0.9rem; margin-bottom:5px;">
              (주){{investPjt.compName}}
            </span>
            <span><strong>글쓴이 이름</strong></span>
            <p style="font-size: 0.9rem;" v-html="investPjt.introduce">{{investPjt.introduce}}</p>
          </div>
        </div>
        <hr />
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
            <v-icon class="tabBtn" v-if="tabItem=='projects'">프로젝트 이력</v-icon>
            <v-icon class="tabBtn" v-if="tabItem=='pjtInfo'">투자 설명서</v-icon>
            <v-icon class="tabBtn" v-if="tabItem=='comments'">댓글</v-icon>
          </v-tab>
        </v-tabs>
        <br />

        <v-tabs-items v-model="currentItem">
          <v-tab-item v-for="tabItem in tabItems" :key="tabItem" :value="'tab-' + tabItem">
            <!-- 프로젝트 이력 -->
            <div v-if="tabItem=='projects'" class="mt-2">
              <div style="overflow: hidden; margin-bottom: 50px">
                <div style="float:left; width: 33%">
                  <div style="text-align: center">
                    <h5 style="margin-bottom: 1.25rem">프로젝트 성공률</h5>
                    <!-- <img style="width: 50%;" src="../../assets/80.png" alt=""> -->
                    <v-progress-circular
                      :rotate="360"
                      :size="100"
                      :width="15"
                      :value="successRate"
                      color="rgb(22, 150, 245)"
                    >{{ successRate }}%</v-progress-circular>
                  </div>
                </div>
                <div style="float:left; width: 33%">
                  <div style="text-align: center">
                    <h5>성공 프로젝트</h5>
                    <p class="projectsNum mt-4" style="font-size: 60px">8</p>
                  </div>
                </div>
                <div style="float:left; width: 33%">
                  <div style="text-align: center">
                    <h5>만든 프로젝트</h5>
                    <p class="projectsNum mt-4" style="font-size: 60px">10</p>
                  </div>
                </div>
              </div>
              <hr>
              <!-- 프로젝트 이력 -->
              <div style="display: flex; text-align: center; margin: 20px 0">
                <h4 style="flex: 1">투자 프로젝트</h4>
                <h4 style="flex: 1">쇼핑 프로젝트</h4>
              </div>
              <div v-for="(item, i) in projectList" :key="i" style="padding: 2%">
                <div style="display: flex;">
                  <!-- 투자 프로젝트 -->
                  <div style="padding: 0 1% 0 5%">
                    <v-card style="width: 100%; height: 180px; display: inline-block;">
                      <v-img
                        style="width:36%; float:left;"
                        height="180"
                        src="https://cdn.vuetifyjs.com/images/cards/cooking.png"
                      ></v-img>
                      <div style="width: 64%; float:right;">
                        <v-card-title style="font-weight: 600;">
                          {{item.investPjt.title}}
                          <div style="margin-left: 5%;">
                            <v-chip
                              class="investPjtBadge"
                              style="background-color: gray; color: white; "
                            >{{item.investPjt.isfinished}}</v-chip>
                          </div>
                        </v-card-title>
                        <v-card-text>
                          <div style="margin-bottom: 15px;">{{item.investPjt.content}}</div>
                          <div style="color: black;">
                            <h5
                              style="display: inline-block; height: 41.6px; line-height: 41.6px"
                            >{{item.investPjt.totalPrice}}원</h5>
                            <div style="display: inline-block; float: right;">
                              <h3
                                style="display: inline-block; color:rgb(22, 150, 245)"
                              >{{item.investPjt.percent}}%</h3>
                              <h5 style="display: inline-block; color:rgb(123, 197, 254)">달성</h5>
                            </div>
                          </div>
                        </v-card-text>
                      </div>
                    </v-card>
                  </div>
                  <!-- 쇼핑 프로젝트 -->
                  <div style="padding: 0 5% 0 1%">
                    <v-card style="width: 100%; height: 180px; display: inline-block;">
                      <v-img
                        style="width:36%; float:left;"
                        height="180"
                        src="https://cdn.vuetifyjs.com/images/cards/cooking.png"
                      ></v-img>
                      <div style="width: 64%; float:right;">
                        <v-card-title style="font-weight: 600;">
                          {{item.shoppingPjt.title}}
                          <div style="margin-left: 5%;">
                            <v-chip
                              class="shoppingPjtBadge"
                              style="background-color: rgb(22, 150, 245); color: white"
                            >{{item.shoppingPjt.isfinished}}</v-chip>
                          </div>
                        </v-card-title>
                        <v-card-text>
                          <div style="margin-bottom: 15px;">{{item.shoppingPjt.content}}</div>
                          <div style="color: black;">
                            <h5
                              style="display: inline-block; height: 41.6px; line-height: 41.6px"
                            >개당 {{item.shoppingPjt.price}}원</h5>
                            <div style="display: inline-block; float: right;">
                              <h3
                                style="display: inline-block; color:rgb(22, 150, 245)"
                              >{{item.shoppingPjt.sellNum}}개</h3>
                              <h5 style="display: inline-block; color:rgb(123, 197, 254)">판매</h5>
                            </div>
                          </div>
                        </v-card-text>
                      </div>
                    </v-card>
                  </div>
                </div>
                <!-- 투자금 사용 내역 -->
                <div style="margin: 30px 0; padding: 0 5%">
                  <!-- <h5>투자금 사용 내역</h5> -->
                  <!-- <vueper-slides
                    class="no-shadow"
                    :visible-slides="5"
                    slide-multiple
                    :gap="3"
                    :slide-ratio="1 / 4"
                    :dragging-distance="200"
                    :breakpoints="{ 800: { visibleSlides: 2, slideMultiple: 2 } }"
                    :bullets="false"
                    style="padding: 0 5%;"
                  >
                    <vueper-slide v-for="(item, i) in items" :key="i" :image="item.src" />
                  </vueper-slides> -->
                </div>
              </div>
            </div>
            <!-- 투자 설명서 -->
            <div v-if="tabItem=='pjtInfo'" class="mt-2" style="text-align: center;">
              <div class="editorContent" v-html="investPjt.editorhtml">
                {{investPjt.editorhtml}}
                <!-- <img style="width: 60%" :src="item.src" alt /> -->
              </div>
            </div>
            <!-- 댓글 -->
            <div v-if="tabItem=='comments'" class="my-4">
              <div style="padding: 0 10%; margin-bottom: 50px">
                <input v-model="comment" class="commentInput" type="text" placeholder="댓글을 입력해주세요." />
                <v-btn @click="oncomment" class="commentBtn">댓글</v-btn>
              </div>
              <hr />
              <p style="margin-left:10px">총 25건의 댓글이 있습니다.</p>
              <div style="padding: 0 5%; display: flex; margin-bottom: 10px">
                <div style="padding-right: 2%">
                  <v-avatar style="width: 60px; height: 60px">
                    <img src="https://cdn.vuetifyjs.com/images/john.jpg" alt="John" />
                  </v-avatar>
                </div>
                <div>
                  <strong>
                    <p style="display:inline; margin: 2px 0px 0px 3px;">솜사탕강쥐</p>
                  </strong>
                  <span class="ml-2" style="color: grey">2020.09.15</span>
                  <p
                  >저희 집 강아지가 좋아하는 드라이기를 드디어 찾았네요. 강아지랑 같이 쓰려고 샀어요. 잘 쓸게요. 많이 파세요..저희 집 강아지가 좋아하는 드라이기를 드디어 찾았네요. 강아지랑 같이 쓰려고 샀어요. 잘 쓸게요. 많이 파세요..</p>
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
import Navbar from "../../components/Navbar.vue";
import "../../../public/css/InvestDetail.scss";
import { VueperSlides, VueperSlide } from "vueperslides";
import "vueperslides/dist/vueperslides.css";
import axios from "axios";

const SERVER_URL = "http://j3b102.p.ssafy.io:8080";

export default {
  components: {
    Navbar,
    VueperSlides,
    VueperSlide,
  },
  data() {
    return {
      // 주소
      nowAddress: "",
      // 프로젝트 정보
      investPjt: {},
      // 프로젝트 이력
      page: 0,
      // 좋아요
      isliked: false,
      likeCount: 0,
      currentItem: "tab-Web",
      tabItems: ["projects", "pjtInfo", "comments"],
      successRate: "80",
      // 프로젝트 이력
      projectList: [
        {
          investPjt: {
            picture: "",
            title: "특별한 드라이기",
            content:
              "한줄 소개 글 저희 집 강아지가 좋아하는 드라이기를 드디어 찾았네요.",
            totalPrice: "2,000,000",
            percent: "100",
            isfinished: "성공",
          },
          shoppingPjt: {
            picture: "",
            title: "드라이기",
            content:
              "한줄 소개 글 저희 집 강아지가 좋아하는 드라이기를 드디어 찾았네요.",
            price: "80,000",
            sellNum: "1,500",
            isfinished: "진행중",
          },
        },
      ],
      model: null,
      // 댓글
      commentList: [],
      comment: "",
    };
  },
  mounted() {
    this.nowAddress = this.$route.params.address
    // 투자 프로젝트 정보 가져오기
    const frm = new FormData();
    frm.append("address",this.nowAddress)
    axios.post(`${SERVER_URL}/investment/getDetail`, frm)
      .then(response => {
        this.investPjt = response.data.object
        // 마감일 기준 남은날짜 계산
        const day = this.investPjt.deadLine.substring(8, 10);
        let today = new Date();
        today.setDate(day - today.getDate());
        this.$set(this.investPjt, "lastday", today.getDate());
        // 사진 url 처리
        const cutUrl = this.investPjt.picture.substr(18, this.investPjt.picture.length - 17);
        const imgUrl = "http://j3b102.p.ssafy.io/" + cutUrl;
        this.investPjt.picture = imgUrl
        // 배경 이미지
        $('.investImg').css('background-image', `url(${this.investPjt.picture})`)
        // 글쓴이 소개글 엔터 변환
        this.investPjt.introduce = this.investPjt.introduce.split('\n').join('<br />');
        // 투자 설명서 엔터 변환
        this.investPjt.editorhtml = this.investPjt.editorhtml.split('\n').join('<br />');
      })
    // 금손 프로젝트 이력 가져오기
    const fd = new FormData();
    fd.append("userid",this.investPjt.userid)
    axios.post(`${SERVER_URL}/investment/getAllPJT/${this.page}`, fd)
      .then(response => {
        console.log(response)
        console.dir(response)
      })
  },
  methods: {
    likebtn() {
      this.isliked = !this.isliked
      if(this.isliked) {
        $('.like').css('color', 'red')
        this.likeCount += 1
      }
      else{
        $('.like').css('color', 'rgba(0, 0, 0, 0.54)')
        this.likeCount -= 1
      }
    },
    oncomment() {
      this.commentList.push(this.comment)
      this.comment = ""
    }
  }
};
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
  /* margin: 0 150px; */
  position: absolute;
  width: 100%;
  top: 250px;
  padding: 0 10%;
  border-radius: 4px;
}
.investInfo {
  overflow: hidden;
  width: 100%;
  background: white;
  padding: 2%;
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
  display: inline-block;
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
.tabBtn {
  font-style: normal;
  font-size: 15px !important;
}
.v-slide-group__content a {
  text-decoration: none;
}
.commentInput {
  width: 80%;
  height: 40px;
  border: 1px solid lightgray;
  padding: 10px;
  margin-right: 3%;
}
.commentBtn {
  width: 15%;
  background-color: rgb(22, 150, 245) !important;
  color: white;
  height: 40px !important;
  margin-bottom: 3px;
}
.btns {
  height: 38px;
  text-align: center;
  line-height: 38px;
  border: 1px solid lightgray;
}
</style>