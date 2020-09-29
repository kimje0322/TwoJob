<template>
  <div class="writeinvest">
    <navbar />
    <!-- 투자 메뉴바 -->
    <div class="investNav">
      <div class="items">
        <div>
          <router-link to="/investhome">
            <h5>투자홈</h5>
          </router-link>
        </div>
        <div style="margin: 0 15%">
          <router-link to="/investproject">
            <h5>프로젝트</h5>
          </router-link>
        </div>
        <div>
          <router-link to="/writeinvest">
            <h5 style="margin: 0; color: rgb(22, 150, 245)">투자오픈</h5>
          </router-link>
        </div>
      </div>
    </div>
    <!-- 투자 글쓰기 메뉴바 -->
    <div>
      <!-- 투자 글쓰기 메뉴 -->
      <div class="writeMenuBar">
        <v-tabs v-model="tab" class="elevation-2" dark hide-slider>
          <v-tab v-for="(item, i) in tabs" :key="i" :href="`#tab-${i}`" class="writeMenu">{{ item }}</v-tab>
          <!-- 투자오픈버튼 -->
          <div class="openbtn" @click="openInvestBtn">
            <v-btn color="rgb(22, 150, 245)" style="font-weight: 600">투자 프로젝트 오픈</v-btn>
          </div>
          <!-- 프로젝트 정보 창 -->
          <v-tab-item :value="'tab-0'">
            <v-card flat tile>
              <v-card-text>
                <div class="pjtinfo">
                  <p>프로젝트에대한 정보를 정확하게 입력해주세요.</p>
                  <h5>프로젝트명</h5>
                  <input v-model="title" type="text" placeholder="프로젝트명을 입력해주세요." />
                  <h5>프로젝트 한줄 소개</h5>
                  <input v-model="content" type="text" placeholder="프로젝트에대해 100자이내로 설명해주세요." />
                  <h5>프로젝트 마감 날짜</h5>
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
                  <h5>달성 목표 금액</h5>
                  <p style="margin: 0 0 5px 10px">마감일 자정까지 목표 금액을 100%달성하셔야만 물품을 판매하실 수 있습니다.</p>
                  <input
                    v-model="targetPrice"
                    @click="removeTargetPrice"
                    type="text"
                    style="width: 35%; text-align: right; font-size: 18px"
                  />
                  <h5 style="display: inline-block; margin-left: 5px;">원</h5>
                  <h5>상품 판매 예정 금액</h5>
                  <p style="margin: 0 0 5px 10px">상품 판매할 예정 금액을 입력해주세요(상품이 여러개이면 대표상품으로 입력해주세요).</p>
                  <input
                    v-model="sellPrice"
                    @click="removeSellPrice"
                    type="text"
                    style="width: 35%; text-align: right; font-size: 18px"
                  />
                  <h5 style="display: inline-block; margin-left: 5px;">원</h5>
                  <h5>대표 사진</h5>
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
                  <h5>카테고리</h5>
                  <div class="categoryDiv" style>
                    <v-btn
                      class="categorybtn"
                      :class="key"
                      v-for="(value, key) in categoryList"
                      :key="key"
                      @click="checkcategory(key)"
                    >{{value}}</v-btn>
                  </div>
                  <h5>검색용 태그</h5>
                  <div>
                    <v-combobox
                      v-model="model"
                      hide-details
                      hide-selected
                      label="태그를 입력해주세요."
                      multiple
                      small-chips
                      solo
                      dense
                      deletable-chips
                      @keyup.enter="change"
                      class="searchBarBtn"
                      style="overflow-y:hidden;"
                    ></v-combobox>
                  </div>
                </div>
              </v-card-text>
            </v-card>
          </v-tab-item>
          <!-- 금손 정보 창 -->
          <v-tab-item :value="'tab-1'">
            <v-card flat tile>
              <v-card-text>
                <div class="writerinfo">
                  <p>금손님에대한 정보를 입력해주세요.</p>
                  <h5>개인 / 사업자 구분</h5>
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
                    <h5>금손님 소개</h5>
                    <textarea
                      v-model="introduce"
                      name="introduce"
                      id="introduce"
                      cols="98"
                      rows="10"
                      placeholder="금손님을 소개하는 글을 써주세요."
                    ></textarea>
                    <h5>금손님 소개 사이트</h5>
                    <input v-model="siteUrl" type="text" placeholder="ex)홈페이지, SNS" />
                  </div>
                  <!-- 개인사업자/기업 -->
                  <div v-if="business">
                    <h5>회사명</h5>
                    <input v-model="companyName" type="text" placeholder="회사명을 입력해주세요." />
                    <h5>금손님 소개</h5>
                    <textarea
                      v-model="introduce"
                      name="introduce"
                      id="introduce"
                      cols="98"
                      rows="10"
                      placeholder="금손님을 소개하는 글을 써주세요."
                    ></textarea>
                    <h5>금손님 소개 사이트</h5>
                    <input v-model="siteUrl" type="text" placeholder="ex)홈페이지, SNS" />
                  </div>
                </div>
              </v-card-text>
            </v-card>
          </v-tab-item>
          <!-- 투자 설명서 창 -->
          <v-tab-item :value="'tab-2'">
            <v-card flat tile>
              <v-card-text>
                <div class="investContent">
                  <p>프로젝트 내용에 대해 자세히 설명해주세요.</p>
                  <div style="margin-bottom: 1rem">
                    <h5
                      style="display: inline-block; height: 36px; line-height: 36px; Smargin: 0;"
                    >투자설명</h5>
                    <v-btn @click="onSave" style="float: right; background-color: white; color: rgb(22, 150,245); font-weight: 600">저장하기</v-btn>
                  </div>
                  <!-- <textarea name="introduce" id="introduce" cols="180" rows="20" placeholder="투자에 대한 설명을 입력해주세요(사진, 글 입력 가능)"></textarea> -->
                  <editor ref="toastuiEditor" v-model="editortext" initialEditType="wysiwyg" height="800px" :options="editorOptions"  />
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
import Navbar from "../../components/Navbar.vue";
// editor
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Editor } from "@toast-ui/vue-editor";
import axios from "axios";
import store from '../../store/index.js'

const SERVER_URL = "http://localhost:8080";
export default {
  components: {
    Navbar,
    Editor,
  },
  data() {
    return {
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
      pp: "응?",
      thumbnail: "file",
      picture: "",
      rules: [
        (thumbnail) =>
          !thumbnail ||
          thumbnail.size < 2000000 ||
          "Tunbnail size should be less than 2 MB!",
      ],
      // 카테고리
      categoryList: {
        tech: "테크, 가전",
        fashion: "패션, 잡화",
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
            console.log(blob)
            // const imageURL = URL.createObjectURL(blob)
            // callback(imageURL);
            var formData = new FormData();
            formData.append("img", blob);

            axios.post(`${SERVER_URL}/investment/changePath`, formData, { 
                headers: { 'Content-Type': 'multipart/form-data' } 
            }).then(response => {
                console.log(response.data);
              callback(response.data)
            });
          },
        },
      },
    };
  },
  computed: {

  },
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
    },
  },
  mounted() {
    if (store.state.isSigned) {
      this.userInfo = store.state.userInfo;
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
      this.receivePrice = "";
    },
    onthumbnail(event) {
      console.log(event)
      var formData = new FormData();
      formData.append("img", event);

      axios.post(`${SERVER_URL}/investment/changePath`, formData, { 
          headers: { 'Content-Type': 'multipart/form-data' }
      }).then(response => {
          console.log(response.data);
          this.picture = response.data
      }).catch(error => {
          console.log(error)
        })
    },
    checkcategory(category) {
      if (this.checkCategory.indexOf(category) >= 0) {
        const idx = this.checkCategory.indexOf(category);
        this.checkCategory.splice(idx, 1);
        $(`.${category}`).css("background-color", "white");
        $(`.${category}`).css("color", "black");
      } else {
        this.checkCategory.push(category);
        $(`.${category}`).css("background-color", "rgb(22, 150, 245)");
        $(`.${category}`).css("color", "white");
      }
    },
    change() {
      const tags = [];
      this.model.forEach((tag) => {
        tags.push(tag.text);
      });
      this.$refs.feeditem.searchTag(tags);
    },
    openMenu() {
      this.openMenutab = !this.openMenutab;
      if (this.openMenutab) {
        $(".v-menu").css("display", "unset");
      } else {
        $(".v-menu").css("display", "none");
      }
    },
    // uploadImage() {
    //   var formData = new FormData();
    //   for (var x=0; x<this.editorImages.length; x++){
    //     formData.append("files", this.editorImages[x])
    //   }
    //   // formData.append("image", blob); // 설명서 사진
    //   // formData.append("image-name", blob.name); // 사진 이름

    //   axios.post(`URL`, formData, { 
    //       headers: { 'Content-Type': 'multipart/form-data' } 
    //   }).then(response => {
    //     // // console.log(response);
    //     // this.image = response.data;
    //   });
    // },
    onSave() {
      this.editortext = this.$refs.toastuiEditor.invoke("getHtml");
      console.log(this.editortext)
    },
    openInvestBtn() {
      console.log("클릭");
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
          axios.post(`${SERVER_URL}/investment/create`, {
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
            editorhtml: this.editortext
          })
            .then(response => {
              Swal.fire({
                // position: 'top-end',
                icon: 'success',
                title: '',
                text: '프로젝트가 성공적으로 오픈되었습니다.',
                showConfirmButton: false,
                // timer: 1500
              })
            })
            .catch(error => {
              console.log(error)
            })
        }
      })
    },
  },
};
</script>

<style scoped>
.investNav {
  height: 50px;
  text-align: center;
  line-height: 50px;
  border-bottom: 1px solid gray;
  border-top: 1px solid lightgray;
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
.openbtn {
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
  border: 2px solid rgb(22, 150, 245);
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
}
#introduce:hover {
  border: 2px solid rgb(22, 150, 245);
}
</style>