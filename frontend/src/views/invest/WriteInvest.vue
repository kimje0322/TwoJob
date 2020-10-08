<template>
  <div class="writeinvest">
    <HomeNav />
    <!-- 투자 메뉴바 -->
    <div class="investNav">
      <div class="items" style="height: 37.6px !important;margin: 24px 0px 0px 0px; !important">
        <div>
          <router-link to="/investhome">
            <h5 class="pageTab" style="font-size:1.25rem !important">투자홈</h5>
          </router-link>
        </div>
        <div style="margin: 0 11%">
          <router-link to="/investproject">
            <h5 class="pageTab" style="font-size:1.25rem !important">프로젝트</h5>
          </router-link>
        </div>
        <div>
          <router-link to="/writeinvest">
            <h5 class="pageTab" style="margin: 0; color: rgb(22, 150, 245);font-size:1.25rem !important">투자오픈</h5>
          </router-link>
        </div>
        <br>
        <hr class="divider mx-auto mt-2" style="display:inline-block; width: 16%; border: solid 2px lightgrey; background-color: lightgrey">
        <hr class="divider mx-auto mt-2" style="display:inline-block; width: 16%; border: solid 2px lightgrey; background-color: lightgrey">
        <hr class="divider mx-auto mt-2" style="display:inline-block; width: 16%; border: solid 2px rgb(22, 150, 245); background-color:  rgb(22, 150, 245)">
      </div>
    </div>
  
    <!-- 투자 글쓰기 메뉴바 -->
    <div style=" padding-right: 15px;">
      <!-- 투자 글쓰기 메뉴 -->
      <div class="writeMenuBar mt-4">
        <v-tabs v-model="tab" class="elevation-2" dark hide-slider>
          <v-tab
            v-for="(item, i) in tabs"
            :key="i"
            :href="`#tab-${i}`"
            class="writeMenu"
            >{{ item }}</v-tab
          >
          <!-- 투자오픈버튼 -->
          <div class="openbtnBox" @click="openInvestBtn">
            <v-btn class="openBtn mr-5" color="#808080" style
              >투자 프로젝트 오픈</v-btn
            >
          </div>
          <!-- 프로젝트 정보 창 -->
          <v-tab-item :value="'tab-0'">
            <v-card flat tile>
              <v-card-text style="padding-bottom: 100px;">
                <div class="pjtinfo">
                  <p>프로젝트에대한 정보를 정확하게 입력해주세요.</p>
                  <h5><span style="color: red">*</span> 프로젝트명</h5>
                  <input
                    v-model="title"
                    type="text"
                    placeholder="프로젝트명을 입력해주세요."
                  />
                  <h5><span style="color: red">*</span> 프로젝트 한줄 소개</h5>
                  <input
                    v-model="content"
                    type="text"
                    maxlength="50"
                    placeholder="프로젝트에대해 50자이내로 설명해주세요."
                  />
                  <h5><span style="color: red">*</span> 프로젝트 마감 날짜</h5>
                  <div>
                    <!-- 마감날짜 -->
                    <div class="startDayBox">
                      <div>
                        <v-menu
                          ref="menu"
                          v-model="menu"
                          :close-on-content-click="false"
                          transition="scale-transition"
                          offset-y
                          max-width="290px"
                          min-width="290px"
                        >
                          <template v-slot:activator="{ on, attrs }">
                            <v-text-field
                              v-model="dateFormatted"
                              persistent-hint
                              v-bind="attrs"
                              @blur="date = parseDate(dateFormatted)"
                              v-on="on"
                              color="rgb(22, 150, 245)"
                              hide-details
                              outlined
                              placeholder="프로젝트 마감 날짜"
                            ></v-text-field>
                          </template>
                          <v-date-picker
                            v-model="date"
                            no-title
                            @input="menu = false"
                            color="rgb(22, 150, 245)"
                          ></v-date-picker>
                        </v-menu>
                      </div>
                    </div>
                  </div>
                  <h5><span style="color: red">*</span> 달성 목표 금액</h5>
                  <p style="margin: 0 0 5px 10px">
                    마감일 자정까지 목표 금액을 100%달성하셔야만 물품을 판매하실
                    수 있습니다.
                  </p>
                  <input
                    v-model="targetPrice"
                    @click="removeTargetPrice"
                    type="text"
                    style="width: 35%; text-align: right; font-size: 18px"
                  />
                  <h5 style="display: inline-block; margin-left: 5px">토큰</h5>
                  <h5><span style="color: red">*</span> 상품 판매 예정 금액</h5>
                  <p style="margin: 0 0 5px 10px">
                    상품 판매할 예정 금액을 입력해주세요(상품이 여러개이면
                    대표상품으로 입력해주세요).
                  </p>
                  <input
                    v-model="sellPrice"
                    @click="removeSellPrice"
                    type="text"
                    style="width: 35%; text-align: right; font-size: 18px"
                  />
                  <h5 style="display: inline-block; margin-left: 5px">토큰</h5>
                  <h5><span style="color: red">*</span> 대표 사진</h5>
                  <!-- v-model="thumbnail" -->
                  <v-file-input
                    :value="this.thumbnail"
                    :rules="rules"
                    accept="image/png, image/jpeg, image/bmp"
                    placeholder="Pick an thumbnail"
                    prepend-icon="mdi-camera"
                    outlined
                    hide-details
                    @change="onthumbnail"
                  ></v-file-input>
                  <h5><span style="color: red">*</span> 카테고리</h5>
                  <div class="categoryDiv" style>
                    <v-btn
                      class="categorybtn"
                      :class="key"
                      v-for="(value, key) in categoryList"
                      :key="key"
                      @click="checkcategory(value, key)"
                      >{{ value }}</v-btn
                    >
                  </div>
                  <h5><span style="color: red">*</span> 검색용 태그</h5>
                  <div>
                    <v-combobox
                      v-model="model"
                      hide-details
                      hide-selected
                      label="태그를 입력 후 엔터를 눌러주세요."
                      multiple
                      small-chips
                      solo
                      dense
                      deletable-chips
                      @keyup.enter="change"
                      class="searchBarBtn"
                      style="overflow-y: hidden"
                    ></v-combobox>
                  </div>
                </div>
              </v-card-text>
            </v-card>
          </v-tab-item>
          <!-- 금손 정보 창 -->
          <v-tab-item :value="'tab-1'">
            <v-card flat tile>
              <v-card-text style="padding-bottom: 500px;">
                <div class="writerinfo">
                  <p>금손님에대한 정보를 입력해주세요.</p>
                  <h5><span style="color: red">*</span> 개인 / 사업자 구분</h5>
                  <v-select
                    v-model="select"
                    :items="items"
                    label="사업자 구분 선택"
                    solo
                    hide-details
                    @click="openMenu"
                  ></v-select>
                  <!-- 개인 -->
                  <div v-if="individual">
                    <h5><span style="color: red">*</span> 금손님 소개</h5>
                    <textarea
                      v-model="introduce"
                      name="introduce"
                      id="introduce"
                      cols="98"
                      rows="10"
                      placeholder="금손님을 소개하는 글을 써주세요."
                    ></textarea>
                    <h5>금손님 소개 사이트</h5>
                    <input
                      v-model="siteUrl"
                      type="text"
                      placeholder="ex)홈페이지, SNS"
                    />
                  </div>
                  <!-- 개인사업자/기업 -->
                  <div v-if="business">
                    <h5><span style="color: red">*</span> 회사명</h5>
                    <input
                      v-model="companyName"
                      type="text"
                      placeholder="회사명을 입력해주세요."
                    />
                    <h5><span style="color: red">*</span> 금손님 소개</h5>
                    <textarea
                      v-model="introduce"
                      name="introduce"
                      id="introduce"
                      cols="98"
                      rows="10"
                      placeholder="금손님을 소개하는 글을 써주세요."
                    ></textarea>
                    <h5>금손님 소개 사이트</h5>
                    <input
                      v-model="siteUrl"
                      type="text"
                      placeholder="ex)홈페이지, SNS"
                    />
                  </div>
                </div>
              </v-card-text>
            </v-card>
          </v-tab-item>
          <!-- 투자 설명서 창 -->
          <v-tab-item :value="'tab-2'">
            <v-card flat tile>
              <v-card-text style="padding-bottom: 100px;">
                <div class="investContent">
                  <p>프로젝트 내용에 대해 자세히 설명해주세요.</p>
                  <div style="margin-bottom: 1rem">
                    <h5
                      style="
                        display: inline-block;
                        height: 36px;
                        line-height: 36px;
                        smargin: 0;
                      "
                    >
                      <span style="color: red">*</span> 투자설명
                    </h5>
                    <v-btn
                      class="saveBtn"
                      @click="onSave"
                      style="
                        float: right;
                        background-color: white;
                        color: rgb(22, 150, 245);
                        font-weight: 600;
                      "
                      >저장하기</v-btn
                    >
                    <!-- 스낵바 -->
                    <v-snackbar
                      v-model="snackbar"
                      :timeout="timeout"
                      center
                    >
                      투자 설명서가 저장되었습니다.
                      <!-- 스낵바 닫기 -->
                      <!-- <template v-slot:action="{ attrs }">
                        <v-btn
                          color="blue"
                          text
                          
                          v-bind="attrs"
                          @click="snackbar = false"
                        >
                          x
                        </v-btn>
                      </template> -->
                    </v-snackbar>

                    </div>
                  <!-- <textarea name="introduce" id="introduce" cols="180" rows="20" placeholder="투자에 대한 설명을 입력해주세요(사진, 글 입력 가능)"></textarea> -->
                  <editor
                    ref="toastuiEditor"
                    v-model="editortext"
                    initialEditType="markdown"
                    height="800px"
                    :options="editorOptions"
                  />
                </div>
              </v-card-text>
            </v-card>
          </v-tab-item>
        </v-tabs>
      </div>
    </div>  
  </div>
</template>

<script>
import "@/../public/css/WriteInvest.scss";
import $ from "jquery";
import Swal from "sweetalert2";
import HomeNav from "../../components/HomeNav.vue";
// import Navbar from "../../components/Navbar.vue";
// editor
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Editor } from "@toast-ui/vue-editor";
import axios from "axios";
import store from "../../store/index.js";

const SERVER_URL = "https://www.twojob.ga/api";
export default {
  components: {
    HomeNav,
    Editor,
  },
  data() {
    return {
      // 스낵바
      snackbar: false,
      timeout: 2000,
      // 개인정보
      userInfo: {},
      userid: store.state.userInfo.id,
      login: false,
      tab: null,
      text: ["1", "2", "3"],
      tabs: ["프로젝트 정보", "금손 정보", "투자 설명서"],
      title: "",
      content: "",
      // 날짜
      date: "",
      dateFormatted: "",
      menu: false,
      targetPrice: 0,
      sellPrice: 0,
      // 사진
      thumbnail: "",
      picture: "",
      rules: [
        (thumbnail) =>
          !thumbnail ||
          thumbnail.size < 2000000 ||
          "Tunbnail size should be less than 2 MB!",
      ],
      // 카테고리
      categoryList: {
        tech: "테크",
        fashion: "패션",
        beauty: "뷰티",
        food: "푸드",
        home: "홈리빙",
        sports: "스포츠",
        animal: "반려동물",
        book: "책",
        instrument: "악기",
      },
      checkCategory: [],
      // 검색태그
      tags: [],
      model: [],
      // 금손 정보
      items: ["개인", "개인 사업자/기업"],
      select: "",
      companyName: "",
      introduce: "",
      siteUrl: "",
      openMenutab: false,
      individual: false,
      business: false,
      // editor
      editortext: "",
      editorImages: [],
      editorOptions: {
        hooks: {
          addImageBlobHook: function (blob, callback) {
            // console.log(blob);
            // const imageURL = URL.createObjectURL(blob)
            // callback(imageURL);
            var formData = new FormData();
            formData.append("img", blob);

            axios
              .post(`${SERVER_URL}/investment/changePath`, formData, {
                headers: { "Content-Type": "multipart/form-data" },
              })
              .then((response) => {
                const cutUrl = response.data.substr(
                  18,
                  response.data.length - 17
                );
                const imgUrl = "https://twojob.ga/" + cutUrl;
                callback(imgUrl);
              })
              .catch((error) => {
                Swal.fire({
                  icon: "warning",
                  title: "",
                  text: "사진의 용량이 너무 큽니다.",
                  confirmButtonText: "닫기",
                  confirmButtonColor: "#d33",
                });
              });
          },
        },
      },
    };
  },
  computed: {},
  watch: {
    date(val) {
      this.dateFormatted = this.formatDate(this.date);
    },
    model(val, prev) {
      if (val.length === prev.length) return;
      this.model = val.map((v) => {
        if (typeof v === "string") {
          this.tags.push(v);
          v = { text: `#${v}` };
          this.nonce++;
        }
        return v;
      });
    },
    select(val) {
      if (val == "개인") {
        this.individual = true;
        this.business = false;
      } else {
        this.business = true;
        this.individual = false;
      }
      this.colorBtn();
    },
    // 유효성 검사
    title() {
      this.colorBtn();
    },
    content() {
      this.colorBtn();
    },
    dateFormatted() {
      this.colorBtn();
    },
    targetPrice() {
      this.colorBtn();
    },
    sellPrice() {
      this.colorBtn();
    },
    picture() {
      this.colorBtn();
    },
    checkCategory() {
      this.colorBtn();
    },
    tags() {
      this.colorBtn();
    },
    introduce() {
      this.colorBtn();
    },
    editortext() {
      this.colorBtn();
    },
    // 숫자만 입력 가능
    targetPrice(val) {
      return (this.targetPrice = this.targetPrice.replace(/[^0-9]/g, ""));
    },
    sellPrice(val) {
      return (this.sellPrice = this.sellPrice.replace(/[^0-9]/g, ""));
    },
  },
  mounted() {
    if (store.state.isSigned) {
      this.userInfo = store.state.userInfo;
      this.userid = store.state.userInfo.id;
      this.login = store.state.isSigned;
    } else {
      this.login = false;
    }
  },
  methods: {
    formatDate(date) {
      if (!date) return null;

      const [year, month, day] = date.split("-");
      return `${year}/${month}/${day}`;
    },
    parseDate(date) {
      if (!date) return null;

      const [year, month, day] = date.split("/");
      return `${year}-${month.padStart(2, "0")}-${day.padStart(2, "0")}`;
    },
    removeTargetPrice() {
      this.targetPrice = "";
    },
    removeSellPrice() {
      this.sellPrice = "";
    },
    onthumbnail(event) {
      var formData = new FormData();
      formData.append("img", event);
      axios
        .post(`${SERVER_URL}/investment/changePath`, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((response) => {
          const cutUrl = response.data.substr(18, response.data.length - 17);
          const imgUrl = "https://twojob.ga/" + cutUrl;
          this.picture = imgUrl;
          $(".v-file-input__text").text(event.name);
        })
        .catch((error) => {
          Swal.fire({
            icon: "warning",
            title: "",
            text: "사진의 용량이 너무 큽니다.",
            confirmButtonText: "닫기",
            confirmButtonColor: "#d33",
          }).then((result) => {
            $(".v-file-input__text").text("다시 입력해주세요.");
          });
        });
    },
    checkcategory(category, key) {
      if (this.checkCategory.indexOf(category) >= 0) {
        const idx = this.checkCategory.indexOf(category);
        this.checkCategory.splice(idx, 1);
        $(`.${key}`).css("background-color", "white");
        $(`.${key}`).css("color", "black");
      } else {
        this.checkCategory.push(category);
        $(`.${key}`).attr('style', 'background-color: rgb(22, 150, 245) !important');
        $(`.${key}`).css("color", "white");
      }
    },
    change() {
      const tags = [];
      this.model.forEach((tag) => {
        tags.push(tag.text);
      });
    },
    openMenu() {
      this.openMenutab = !this.openMenutab;
      if (this.openMenutab) {
        $(".v-menu").css("display", "unset");
      } else {
        $(".v-menu").css("display", "none");
      }
    },
    onSave() {
      this.editortext = this.$refs.toastuiEditor.invoke("getHtml");
      if (this.editortext) {
        $(".saveBtn").css("background-color", "rgb(22, 150, 245)");
        this.snackbar = true;
        $(".saveBtn").css("color", "white");
      }
      // console.log(this.editortext);
    },
    colorBtn() {
      if (
        this.userid &&
        this.title &&
        this.content &&
        this.dateFormatted &&
        this.targetPrice &&
        this.sellPrice &&
        this.picture &&
        this.checkCategory &&
        this.tags &&
        this.select &&
        this.introduce &&
        this.editortext
      ) {
        $(".openBtn").css("background-color", "rgb(22, 150, 245)");
        $(".openBtn").css("color", "white");
      } else {
        $(".openBtn").css("background-color", "#808080");
        $(".openBtn").css("color", "white");
      }
    },
    openInvestBtn() {
      if (
        this.userid &&
        this.title &&
        this.content &&
        this.dateFormatted &&
        this.targetPrice &&
        this.sellPrice &&
        this.picture &&
        this.checkCategory &&
        this.tags &&
        this.select &&
        this.introduce &&
        this.editortext
      ) {
        Swal.fire({
          icon: "warning",
          title: "",
          text: "프로젝트를 오픈하기 전 저장하기 버튼을 꼭 클릭해 주세요.",
          showCancelButton: true,
          cancelButtonColor: "#d33",
          confirmButtonColor: "#3085d6",
          confirmButtonText: "오픈하기",
          cancelButtonText: "취소하기",
          reverseButtons: true,
        }).then((result) => {
          if (result.value) {
            // 백엔드에 저장
            axios
              .post(`${SERVER_URL}/investment/create`, {
                userid: this.userid,
                pjtName: this.title,
                oneLineIntro: this.content,
                deadLine: this.dateFormatted,
                goalPrice: this.targetPrice,
                expectedSalePrice: this.sellPrice,
                picture: this.picture,
                categorys: this.checkCategory,
                tags: this.tags,
                identity: this.select,
                compName: this.companyName,
                introduce: this.introduce,
                url: this.siteUrl,
                editorhtml: this.editortext,
              })
              .then((response) => {
                // console.log(response);
                if (response.data.data == "Success") {
                  // 블록체인
                  const fd = new FormData();
                  fd.append("accessToken", store.state.accessToken);
                  fd.append("canpaignId", response.data.object.address);
                  axios
                    .post(`${SERVER_URL}/funding/createfunding`, fd)
                    let timerInterval;
                    Swal.fire({
                      title: "투자 프로젝트 오픈중",
                      timer: 10000,
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
                    .then((response) => {
                      // console.log(response)
                        this.$router.push("/investhome");
                        Swal.fire({
                          icon: "success",
                          title: "",
                          text: "프로젝트가 성공적으로 오픈되었습니다.",
                          showConfirmButton: false,
                        })
                      // else{
                      //   alert("블록체인 프로젝트 오픈에 실패했습니다.");
                      // }
                    });
                } else {
                  alert("프로젝트 오픈에 실패했습니다.");
                }
              })
              .catch((error) => {
                // console.log(error);
              });
          }
        });
      }
    },
  },
};
</script>

<style scoped>
.investNav {
  /* height: 50px; */
  text-align: center;
  /* line-height: 50px; */
  /* border-bottom: 1px solid lightgray;
  border-top: 1px solid lightgray; */
  margin-bottom: 15px;
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
.writeMenu {
  margin-left: 3%;
  letter-spacing: unset !important;
  font-size: 1.25rem;
  border-top-right-radius: 10px;
  border-top-left-radius: 10px;
}
.writeMenu:hover {
  background-color: rgba(173, 220, 254, 0.4);
}
.v-tab--active {
  background-color: rgba(173, 220, 254, 0.4);
}
.v-tab:before {
  background-color: unset;
}
.openbtnBox {
  line-height: 45px;
  position: absolute;
  right: 1%;
}
.v-card__text {
  padding: 16px 10%;
  color: black !important;
}
h5 {
  font-size: 1.15rem;
  font-weight: 600;
  margin-bottom: 1rem;
}
input {
  background-color: white;
  width: 90%;
  height: 40px;
  border: 1px solid lightgray;
  border-radius: 5px;
  margin-left: 10px;
  margin-bottom: 30px;
  padding: 10px;
}
input:hover {
  border: 2px solid rgb(22, 150, 245);
}
.v-input__slot fieldset {
  display: none;
}
.pjtinfo .v-menu {
  display: unset;
}
.startDayBox {
  display: inline-block;
  width: 35%;
}
.tilddIcon {
  display: inline-block;
  margin: 0 9%;
}
.categoryDiv {
  margin-bottom: 28px;
}
.categoryDiv .v-btn {
  width: 105px;
  margin-right: 10px;
  margin-bottom: 10px;
}
.categorybtn:hover {
  /* border: 2px solid rgb(22, 150, 245); */
  background-color: rgb(22, 150, 245) !important;
  color: white !important;
}
.searchBarBtn {
  border: 1px solid lightgray;
}
#introduce {
  background-color: white;
  border: 1px solid lightgray;
  border-radius: 5px;
  resize: none;
  padding: 8px;
  margin: 0 0 20px 10px;
  width: 90%;
}
#introduce:hover {
  border: 2px solid rgb(22, 150, 245);
}
.pageTab {
  margin-bottom: 0px !important;
}
/* .snackbar {
  position: fixed;
  bottom: 330px;
  left: 20px;
  width: 300px;
  color: rgb(22, 150, 245);
} */
</style>
