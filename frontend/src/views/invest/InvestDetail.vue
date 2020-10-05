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
            <!-- 상품 제목 -->
            <h3>{{ investPjt.pjtName }}</h3>
            <hr />
            <p style="font-size: 1.2em">
              <span style="color: rgb(22, 150, 245);">{{investPjt.investnum}}</span>명 투자 중
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
              <h3 class="totalPrice">{{investPjt.investprice}}</h3>
              <strong>
                <span>원 달성</span>
              </strong>
              <div style="display: inline-block; margin-left: 5%">
                <h3
                  style="display: inline-block; color:rgb(22, 150, 245); font-weight: 600; margin-right: 5px"
                >{{investPjt.rate}}%</h3>
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
            <v-btn :disabled="islogin==false" class="investBtn white--text" style="width: 100%; height: 42px" @click="oninvestbtn">
              <v-icon size="25" class="mr-1">mdi-cash-usd</v-icon>투자 하기
            </v-btn>
            <!-- 투자하기 모달 -->
            <v-dialog
              v-model="investbtn"
              scrollable
              max-width="40%"
              style="height: 400px"
            >
              <v-card>
                <v-card-title class="headline lighten-2" style="padding-bottom: 0 !important">
                  <h4 style="margin-left: 30px">투자하기</h4>
                </v-card-title>
                <v-divider></v-divider>
                <v-card-text style="padding: 50px 50px 30px 50px">
                  <v-text-field class="moneyinput" v-model="investmoney" label="투자금액" required></v-text-field>
                </v-card-text>
                <!-- <v-divider></v-divider> -->
                <v-card-actions style="background-color: white">
                  <v-spacer></v-spacer>
                  <v-btn text @click="investbtn = false">닫기</v-btn>
                  <v-btn text color="blue" @click="oninvest">투자하기</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <!-- 좋아요 문의하기 버튼 -->
            <div style="display: flex; margin-top: 15px;">
              <button :disabled="islogin==false" style="flex: 1;" @click="likebtn">
                <div class="btns" style="margin-right: 5px">
                  <v-icon size="20" class="mr-2 like">mdi-heart</v-icon>{{likeCount}}
                </div>
              </button>
              <button :disabled="islogin==false||this.writer.oauthId==userid" style="flex: 1" @click="onChat()">
                <div class="btns">
                  <v-app class="chatbtn"></v-app>
                  <div>
                    <v-dialog
                      max-width="800"
                      min-height="500"
                      v-model="chatroom"
                    >
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
        <div class="mt-5" style="overflow: hidden;">
          <strong>
            <p class="mb-3">금손님 정보</p>
          </strong>
          <div class="maker" style="float:left">
            <img class="makerImg" :src="writer.profileImg" :alt="writer.name" />
          </div>
          <div style="float:left; margin: 3px 0 0 15px;">
            <span v-if="investPjt.compName" style="font-size: 0.9rem; margin-bottom:5px;">
              (주){{investPjt.compName}}
            </span>
            <span><strong>{{writer.name}}</strong></span>
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
              <!-- 프로젝트 이력 -->
              <div style="display: flex; text-align: center; margin: 20px 0">
                <h4 style="flex: 1">투자 프로젝트</h4>
                <h4 style="flex: 1">쇼핑 프로젝트</h4>
              </div>
              <div v-for="(item, i) in projectList" :key="i" style="padding: 2%">
                <div>
                  <!-- 투자 프로젝트 -->
                  <div style="padding: 0 1% 0 5%; width: 50%; display: inline-block">
                    <v-card style="width: 100%; height: 180px; display: inline-block;">
                      <v-img
                        style="width:36%; float:left;"
                        height="180"
                        :src="item.investmentDto.picture"
                      ></v-img>
                      <div style="width: 64%; float:right;">
                        <v-card-title style="font-weight: 600;">
                          {{item.investmentDto.pjtname}}
                          <div style="margin-left: 5%;">
                            <!-- 진행중 -->
                            <v-chip
                              v-if="!item.investmentDto.isfinish"
                              class="investPjtBadge"
                              style="background-color:  rgb(123, 197, 254); color: white; "
                            >{{item.investmentDto.chip}}</v-chip>
                            <!-- 종료 -->
                            <v-chip
                              v-else
                              class="investPjtBadge"
                              style="background-color: gray; color: white; "
                            >{{item.investmentDto.chip}}</v-chip>
                          </div>
                        </v-card-title>
                        <v-card-text style="height: 115px;">
                          <div style="margin-bottom: 15px; height: 50px;">{{item.investmentDto.onelineintro}}</div>
                          <div style="color: black; height: 50px;">
                            <h5
                              style="display: inline-block; height: 41.6px; line-height: 41.6px"
                            >{{item.investmentDto.investprice}}원</h5>
                            <div style="display: inline-block; float: right;">
                              <h3
                                style="display: inline-block; color:rgb(22, 150, 245)"
                              >{{item.investmentDto.rate}}%</h3>
                              <h5 style="display: inline-block; color:rgb(123, 197, 254)">달성</h5>
                            </div>
                          </div>
                        </v-card-text>
                      </div>
                    </v-card>
                  </div>
                  <!-- 쇼핑 프로젝트 -->
                  <div v-if="item.saleBoardDto" style="padding: 0 5% 0 1%; width: 50%; display: inline-block">
                    <v-card style="width: 100%; height: 180px; display: inline-block;">
                      <v-img
                        style="width:36%; float:left;"
                        height="180"
                        :src="item.saleBoardDto.picture"
                      ></v-img>
                      <div style="width: 64%; float:right;">
                        <v-card-title style="font-weight: 600;">
                          {{item.saleBoardDto.pjtname}}
                          <div style="margin-left: 5%;">
                            <!-- 진행중 -->
                            <v-chip
                              v-if="!item.saleBoardDto.isfinish"
                              class="investPjtBadge"
                              style="background-color:  rgb(123, 197, 254); color: white; "
                            >{{item.saleBoardDto.chip}}</v-chip>
                            <!-- 종료 -->
                            <v-chip
                              v-else
                              class="investPjtBadge"
                              style="background-color: gray; color: white; "
                            >{{item.saleBoardDto.chip}}</v-chip>
                          </div>
                        </v-card-title>
                        <v-card-text style="height: 115px;">
                          <div style="margin-bottom: 15px; height: 50px;"></div>
                          <div style="color: black; height: 50px;">
                            <h5
                              style="display: inline-block; height: 41.6px; line-height: 41.6px"
                            >개당 {{item.saleBoardDto.saleprice}}원</h5>
                            <div style="display: inline-block; float: right;">
                              <h3
                                style="display: inline-block; color:rgb(22, 150, 245)"
                              >{{item.saleBoardDto.sellNum}}개</h3>
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
              <div v-if="islogin" style="padding: 0 10%; margin-bottom: 50px">
                <input v-model="comment" @keyup.enter="oncomment" class="commentInput" type="text" placeholder="댓글을 입력해주세요." />
                <v-btn @click="oncomment" class="commentBtn">댓글</v-btn>
              </div>
              <hr v-if="islogin" />
              <p style="margin-left:10px">총 25건의 댓글이 있습니다.</p>
              <div v-for="(comment, i) in commentList" :key="i" style="padding: 0 5%; display: flex; margin-bottom: 10px">
                <div style="padding-right: 2%">
                  <v-avatar style="width: 60px; height: 60px">
                    <img :src="comment.profileImg" alt="John" />
                  </v-avatar>
                </div>
                <div>
                  <strong>
                    <p style="display:inline; margin: 2px 0px 0px 3px;">{{comment.name}}</p>
                  </strong>
                  <span class="ml-2" style="color: grey">{{comment.createat}}</span>
                  <v-btn icon v-if="comment.userid==userid" @click="delcomment(comment)"><v-icon>mdi-trash-can-outline</v-icon></v-btn>
                  <p>{{comment.content}}</p>
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
import store from "../../store/index.js";
import Swal from "sweetalert2";
import ChatRoom from "@/views/mypage/ChatRoom.vue";

const SERVER_URL = "https://www.twojob.ga/api";

export default {
  components: {
    Navbar,
    VueperSlides,
    VueperSlide,
    ChatRoom,
  },
  data() {
    return {
      // 로그인 여부
      islogin: store.state.isSigned,
      userid: store.state.userInfo.id,
      // 주소
      nowAddress: "",
      // 프로젝트 정보
      investPjt: {},
      // 금손 정보
      writer: {},
      // 탭
      currentItem: "tab-Web",
      tabItems: ["projects", "pjtInfo", "comments"],
      // 프로젝트 이력
      successRate: "80",
      projectList: [],
      // 무한 스크롤
      page: 0,
      totalPage: 0,
      // 좋아요
      isliked: false,
      likeCount: 0,
      // 댓글
      commentList: [],
      comment: "",
      // 투자하기 버튼
      investbtn: false,
      investmoney: "",
      chatroom: false,
    };
  },
  mounted() {
    this.nowAddress = this.$route.params.address
    // 투자 프로젝트 정보 가져오기
    const frm = new FormData();
    frm.append("address",this.nowAddress)
    frm.append("userid", store.state.userInfo.id)
    axios.post(`${SERVER_URL}/investment/getDetail`, frm)
      .then(response => {
        // console.log(response)
        // 좋아요
        this.likeCount = response.data.object.likecount
        this.isliked = response.data.object.like
        if(this.isliked){
          $('.like').css('color', 'red')
        }else{
          $('.like').css('color', 'rgba(0, 0, 0, 0.54)')
        }
        this.investPjt = response.data.object.object
        // 댓글 사용자 정보 가져오기
        this.commentList = this.investPjt.comments
        this.commentList.forEach(comment=>{
          comment.createat = `${comment.createat.substring(0, 4)}.${comment.createat.substring(5, 7)}.${comment.createat.substring(8, 10)}`
          const fc = new FormData();
          fc.append("userid", comment.userid)
          axios.post(`${SERVER_URL}/util/userinfo`, fc)
            .then(response=> {
              this.$set(comment, "name", response.data.object.name)
              this.$set(comment, "profileImg", response.data.object.profileImg)
            })
        })
        // 투자한 사람 수 axios 보내기
        axios.get(`${SERVER_URL}/funding/fundernum?campaignId=${this.investPjt.address}`)
          .then(response => {
            this.$set(this.investPjt, "investnum", response.data)
          })
        // 투자금액 axios 보내기
        axios.get(`${SERVER_URL}/funding/nowfund?campaignId=${this.investPjt.address}`)
          .then(response => {
            this.$set(this.investPjt, "investprice", response.data);
          })
        // 달성률 axios 보내기
        const fr = new FormData();
        fr.append("campaignId", this.investPjt.address)
        axios.post(`${SERVER_URL}/funding/fundingrate`, fr)
          .then(response => {
            this.$set(this.investPjt, "rate", response.data);
          })
        // 마감일 기준 남은날짜 계산
        const day = this.investPjt.deadLine.substring(8, 10);
        let today = new Date();
        today.setDate(day - today.getDate());
        this.$set(this.investPjt, "lastday", today.getDate());
        // 배경 이미지
        $('.investImg').css('background-image', `url(${this.investPjt.picture})`)
        // 글쓴이 소개글 엔터 변환
        this.investPjt.introduce = this.investPjt.introduce.split('\n').join('<br />');
        // 투자 설명서 엔터 변환
        this.investPjt.editorhtml = this.investPjt.editorhtml.split('\n').join('<br />');
        // 금손 정보 가져오기
        const fu = new FormData();
        fu.append("userid", this.investPjt.userid)
        axios.post(`${SERVER_URL}/util/userinfo`, fu)
          .then(response =>{
            // console.log(response)
            this.writer = response.data.object
          })
        // 금손 프로젝트 이력 가져오기
        const fd = new FormData();
        fd.append("userid", this.investPjt.userid)
        axios.post(`${SERVER_URL}/investment/getAllPJT/${this.page}`, fd)
          .then(response => {
            console.log(response)
            this.projectList = response.data.object
            this.totalPage = response.data.object[0].totalpages
            this.projectList.forEach(item => {
              // 투자 프로젝트 chip 
              if (item.investmentDto.isfinish) {
                this.$set(item.investmentDto, "chip", '종료');
              }
              else{
                this.$set(item.investmentDto, "chip", '진행중');
              }
              // pjtname
              if(item.investmentDto.pjtname.length > 8) {
                item.investmentDto.pjtname = item.investmentDto.pjtname.substring(0, 8) + '...'
              }
              // 투자 금액 axios 가져오기
              axios.get(`${SERVER_URL}/funding/nowfund?campaignId=${item.investmentDto.address}`)
                .then(response => {
                  this.$set(item.investmentDto, "investprice", response.data);
                })
              // 달성률 axios 보내기
              const fr = new FormData();
              fr.append("campaignId", item.investmentDto.address)
              axios.post(`${SERVER_URL}/funding/fundingrate`, fr)
                .then(response => {
                  this.$set(item.investmentDto, "rate", response.data);
                })
              // 쇼핑 프로젝트 chip 
              if(item.saleBoardDto) {
                if (item.saleBoardDto.isfinish) {
                  this.$set(item.saleBoardDto, "chip", '종료');
                }
                else{
                  this.$set(item.saleBoardDto, "chip", '진행중');
                }
                // pjtname
                if(item.saleBoardDto.pjtname.length > 8) {
                  item.saleBoardDto.pjtname = item.saleBoardDto.pjtname.substring(0, 8) + '...'
                }
              }
            })
          })
      })    
  },
  methods: {
    closeChatRoom() {
      this.chatroom = false;
      store.state.askusername = null;
      store.state.askuserid = null;
    },
    onChat() {
      // window.open("");
      this.chatroom = true;
      console.log("oauthId " + this.writer.oauthId)
      store.commit("setAsk", this.writer.name, this.writer.oauthId);
      store.state.askuserid = this.writer.oauthId
      // console.log("모달 열어보자" + this.chatroom);
      // this.$router.push("/chat")
    },
    likebtn() {
      axios.post(`${SERVER_URL}/util/createlike`, {
        address: this.$route.params.address,
        userid: store.state.userInfo.id
      })
        .then(response => {
          this.likeCount = response.data.object.likecount
          if(response.data.object.likestate) {
            $('.like').css('color', 'red')
          }
          else{
            $('.like').css('color', 'rgba(0, 0, 0, 0.54)')
          }          
        })
    },
    oncomment() {
      axios.post(`${SERVER_URL}/investment/createcomment`, {
        address: this.$route.params.address,
        comment: this.comment,
        userid: store.state.userInfo.id
      })
        .then(response => {
          console.log(response)
          const today = new Date();
          let year = today.getFullYear(); // 년도
          let month = today.getMonth() + 1;  // 월
          let date = today.getDate();  // 날짜
          const day = `${year}.${month}.${date}`
          this.commentList.push({
            content: this.comment,
            createat: day,
            name: store.state.userInfo.name,
            profileImg: store.state.userInfo.img,
            userid: store.state.userInfo.id,
            num: response.data.object.num
          })
          this.comment = ""
        })
    },
    delcomment(comment) {
      const idx = this.commentList.indexOf(comment);
      this.commentList.splice(idx, 1);
      axios.delete(`${SERVER_URL}/investment/deletecomment?num=${comment.num}`)
        .then(response =>{
          console.log(response)
        })
    },
    oninvestbtn() {
      this.investbtn = !this.investbtn
    },
    oninvest(){
      // console.log('투자하기 버튼')
      const fd = new FormData();
      fd.append("Value", this.investmoney)
      fd.append("accessToken", store.state.accessToken)
      fd.append("campaignId", this.$route.params.address)
      this.investmoney = ""
      this.investbtn = false
      axios.post(`${SERVER_URL}/funding/tofunding`, fd)
        let timerInterval;
        Swal.fire({
          title: `${this.investPjt.pjtName}프로젝트에 투자하기`,
          html: "<b></b> milliseconds 기다려주세요.",
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
        .then(response =>{
          // console.log(response)
        })
      
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