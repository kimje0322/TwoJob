<template>
  <div class="writeinvest">
    <HomeNav />
    <!-- <h4>마이페이지</h4> -->
    <!-- 쇼핑 글쓰기 메뉴바 -->
    <div class="mt-4">
      <h5 class="pageTab mb-0 mx-auto" style="text-align:center; color:  rgb(22, 150, 245);font-size:1.25rem !important">쇼핑 프로젝트 오픈</h5>
      <hr class="divider mx-auto mt-2" style="text-align:center; width: 20%; border: solid 2px  rgb(22, 150, 245); background-color:  rgb(22, 150, 245)">
    </div>
    <div>
      <!-- 쇼핑 글쓰기 메뉴 -->
      <div class="writeMenuBar mt- mb-5">
        <v-tabs v-model="tab" class="elevation-2" dark hide-slider>
          <v-tab v-for="(item, i) in tabs" :key="i" :href="`#tab-${i}`" class="writeMenu">{{ item }}</v-tab>
          <!-- 쇼핑 오픈버튼 -->
          <div class="openbtn mr-5" @click="checkForm">
            <!-- v-bind:disabled="addedItems.length < 1" -->
            <v-btn class="openbtn mr-4" color="#808080">쇼핑 프로젝트 오픈</v-btn>
          </div>

          <!-- 상품 정보 창 -->
          <v-tab-item :value="'tab-0'">
            <v-card flat tile>
              <v-card-text>
                <div class="pjtinfo">
                  <p>상품에 대한 정보를 정확하게 입력해주세요.</p>
                  <h5><span style="color: red">*</span>상품명</h5>
                  <input v-model="title" 
                  type="text" placeholder="상품명을 입력해주세요." />
                  <h5 style="display: inline-block; margin-left: 5px;"></h5>
                  <h5><span style="color: red">*</span>대표 사진</h5>
                  <v-file-input
                    v-model="thumbnail"
                    @change="onThumbnail"
                    :rules="rules"
                    accept="image/png, image/jpeg, image/bmp"
                    placeholder="Pick an thumbnail"
                    prepend-icon="mdi-camera"
                    outlined
                    hide-details
                  ></v-file-input>
                  <!-- 판매 오픈 날짜 -->
                  <h5><span style="color: red">*</span>판매 오픈 날짜</h5>
                    <div class="startDayBox">
                      <div>
                        <v-menu
                          ref="menu1"
                          v-model="menu1"
                          :close-on-content-click="false"
                          transition="scale-transition"
                          offset-y
                          max-width="290px"
                          min-width="290px"
                        >
                          <template v-slot:activator="{ on, attrs }">
                            <v-text-field
                              v-model="dateFormatted1"
                              persistent-hint
                              v-bind="attrs"
                              @blur="openDate = parseDate(dateFormatted1)"
                              v-on="on"
                              color="rgb(22, 150, 245)"
                              hide-details
                              outlined
                              placeholder="프로젝트 시작 날짜"
                            ></v-text-field>
                          </template>
                          <v-date-picker
                            v-model="openDate"
                            no-title
                            @input="menu1 = false"
                            color="rgb(22, 150, 245)"
                          ></v-date-picker>
                        </v-menu>
                      </div>
                    </div>
                  <!-- 판매 금액 -->
                  <h5><span style="color: red">*</span>판매 금액</h5>
                  <input
                    v-model="price"
                    @click="removeTargetPrice"
                    type="text"
                    style="width: 35%; text-align: right; font-size: 18px"
                  />
                  <h5 style="display: inline-block; margin-left: 5px;">토큰</h5>
                  <!-- 카테고리 -->
                  <!-- <h5>카테고리</h5>
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
                  </div> -->
                </div>
                <!-- 상품 추가 버튼 -->
                <!-- <v-btn outlined @click="onAddItem" class="addItem mt-2 ml-2">
                  <v-icon class="mr-2">mdi-plus-box-multiple-outline</v-icon>
                  상품 추가
                </v-btn> -->
                <!-- 상품 추가 후 요약 -->
                <div v-if="addedItem" class="addedItem">
                  <div stlye="margin:0;">
                    <v-card
                      class="mr-5 mt-3"
                      v-for="(item, key) in addedItems"
                      :key="key" 
                      style="display: inline-block; max-height: 150px; width: 40%; padding: 12px;background-color: #EEEEEE;" 
                      outlined
                    > 
                      <v-list-item>
                        <v-list-item-content>
                          <v-list-item-title
                          class="headline mt-1 mb-3 ">
                          <strong>{{ item.title }}</strong>
                          </v-list-item-title>
                          <v-list-item-subtitle>
                            <span class="summary">
                              <v-icon size=17>mdi-check</v-icon>
                              오픈 날짜
                            </span>{{ item.opendate }} 
                          </v-list-item-subtitle>
                          <v-list-item-subtitle>
                            <span class="summary">
                                <v-icon size=17>mdi-check</v-icon>
                                판매 금액
                            </span>
                            {{ item.price }} 토큰
                            </v-list-item-subtitle>
                          <v-list-item-subtitle>
                            <span class="summary">
                                <v-icon size=17>mdi-check</v-icon>
                                카테고리
                            </span>
                            <v-btn readonly class="showCategory mr-1" color="rgb(22, 150, 245)" x-small
                            v-for="(value, key) in item.category"
                            :key="key"
                            >{{value}}</v-btn>
                          </v-list-item-subtitle>
                        </v-list-item-content> 
                        </v-list-item>
                        <v-card-actions>
                          <v-btn small text @click="onDeleteItem(item)" style="margin-left:auto;">삭제</v-btn>
                        </v-card-actions>
                    </v-card>
                  </div>
                </div>
              </v-card-text>
            </v-card>
          </v-tab-item>
          <!-- 쇼핑 설명서 창 -->
          <v-tab-item :value="'tab-1'">
            <v-card flat tile>
              <v-card-text>
                <div class="investContent">
                  <p>상품 내용에 대해 자세히 설명해주세요.</p>
                  <div style="margin-bottom: 1rem">
                    <h5
                      style="display: inline-block; height: 36px; line-height: 36px; Smargin: 0;"
                    ><span style="color: red">*</span>쇼핑설명</h5>
                    <v-btn @click="onSave" style="float: right; background-color: white; color: rgb(22, 150,245); font-weight: 600">저장하기</v-btn>
                  </div>
                    <!-- 스낵바 -->
                    <v-snackbar
                      v-model="snackbar"
                      :timeout="timeout"
                      top
                    >
                      상품 설명서가 저장되었습니다.
                      <!-- 스낵바 닫기 버튼 -->
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
                  <editor ref="toastuiEditor" v-model="editortext" initialEditType="markdown" height="800px" :options="editorOptions"  />
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
import "@/../public/css/Writeshopping.scss";
import $ from "jquery";
import Swal from "sweetalert2";
import HomeNav from "../../components/HomeNav.vue";
// editor
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Editor } from "@toast-ui/vue-editor";
import axios from "axios";
import store from '../../store/index.js'


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
      investAddress: '',
      campaignId: '',
      userid: '',
      // 추가된 상품
      showCategory: [],
      addedItem: false,
      addedItems: [],
      isActive: true,
      tab: null,
      text: ["1", "2", "3"],
      tabs: ["상품 정보", "쇼핑 설명서"],
      title: "",
      content: "",
      // 날짜
      openDate: "",
      dateFormatted1: "",
      menu1: false,
      // 가격
      price: 0,
      // 사진
      picture: "",
      thumbnail: "",
      rules: [
        (value) =>
          !value ||
          value.size < 2000000 ||
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
      // 검색 태그
      items: [],
      model: [],
      // editor
      editortext: "",
      editorImages: [],
      editorOptions: {
        hooks: {
          addImageBlobHook: function (blob, callback) {
            // console.log(blob)
            // const imageURL = URL.createObjectURL(blob)
            // callback(imageURL);
            var formData = new FormData();
            formData.append("img", blob);

            axios.post(`${SERVER_URL}/investment/changePath`, formData, { 
                headers: { 'Content-Type': 'multipart/form-data' } 
            }).then(response => {
                const cutUrl = response.data.substr(18, response.data.length-17)
                const imgUrl = 'https://twojob.ga/' + cutUrl
              callback(imgUrl)
            });
          },
        },
      },
      completed: false,
    };
  },
  watch: {
    openDate(val) {
      this.dateFormatted1 = this.formatDate(this.openDate);
    },
    model(val, prev) {
      if (val.length === prev.length) return;
      this.model = val.map((v) => {
        if (typeof v === "string") {
          v = { text: `#${v}` };
          this.items.push(v);
          this.nonce++;
        }
        return v;
      });
    },
    addedItems(val) {
      // console.log('넣음')
      if(this.completed = true) {
        this.isActive = true
      }
      else{
        this.isActive = false
      }
    }
  },
  mounted() {
    this.investAddress = this.$route.params.address
    console.log(this.investAddress)
    console.log('invest어드레스')

    if (store.state.isSigned) {
      this.userInfo = store.state.userInfo;
      this.userid = store.state.userInfo.id;
      this.login = store.state.isSigned;
    } else {
      this.login = false;
    }
  },
  updated() {
    if (this.title && this.thumbnail.name && this.openDate && this.price && this.editortext) {
     $(".openbtn").css("background-color", "rgb(22, 150, 245)");
      $(".openbtn").css("color", "white");
    } else {
      $(".openbtn").css('background-color','#808080')
      $(".openbtn").css("color", "white");
    }
  },
  methods: {
    onThumbnail(e) {
       var formData = new FormData();
        formData.append("img", e);
        // console.log(e)
        axios.post(`${SERVER_URL}/investment/changePath`, formData, { 
            headers: { 'Content-Type': 'multipart/form-data' } 
        }).then(response => {
            const cutUrl = response.data.substr(18, response.data.length-17)
            const imgUrl = 'https://twojob.ga/' + cutUrl
            this.picture = imgUrl;

        });
    },
    onSave() {
      this.editortext = this.$refs.toastuiEditor.invoke("getHtml");
      if (this.editortext) {
        this.snackbar = true;
      }
      // console.log(this.editortext)
    },
    formatDate(date) {
      if (!date) return null;

      const [year, month, day] = date.split("-");
      return `${year}/${month}/${day}`;
    },
    parseDate(date) {
      if (!date) return null;

      const [year, month, day] = date.split("/");
      return 
      // return `${year}-${month.padStart(2, "0")}-${day.padStart(2, "0")}`;
    },
    removeTargetPrice() {
      this.price = "";
    },
    checkcategory(category) {
      // console.log(category)
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
    checkForm() {
      if (this.title && this.thumbnail.name && this.openDate && this.price && this.editortext) {
        this.completed = true;
        this.openShoppingBtn();
      } else if (this.title && this.thumbnail.name && this.openDate && this.price && !this.editortext) {
        Swal.fire({
                icon: "warning",
                title: "",
                text: "　　　　　쇼핑 프로젝트를 오픈하기 전,　　　　　저장하기 버튼을 클릭해 주세요.",
                confirmButtonText: "닫기",
                confirmButtonColor: "#d33",
              })
      } else {
         Swal.fire({
                icon: "warning",
                title: "",
                text: "모든 항목을 입력해주세요.",
                confirmButtonText: "닫기",
                confirmButtonColor: "#d33",
              })
      } 
    },
    openShoppingBtn() {
      Swal.fire({
        icon: "warning",
        title: '',
        text: "정말 프로젝트를 오픈하시겠습니까?",
        showCancelButton: true,
        cancelButtonColor: '#d33',
        confirmButtonColor: '#3085d6',
        confirmButtonText: '오픈하기',
        cancelButtonText: '취소하기',
        reverseButtons: true
      }).then((result) => {
        // console.log(this.userid)
        if (result.value) {
          axios.post(`${SERVER_URL}/sale/create`, {
            userid: this.userid,
            investaddress: this.investAddress,
            pjtname: this.title,
            picture: this.picture,
            startdate: this.dateFormatted1,
            saleprice: this.price,
            url: "abc",
            // categorys: this.checkCategory,
            // tags: this.tags,
            editorhtml: this.editortext,
          })
            .then(response => {
              if(response.data.status == true){
                  // 블록체인
                  const fd = new FormData();
                  fd.append("accessToken", store.state.accessToken);
                  fd.append("campaignId", response.data.data);
                  axios
                    .post(`${SERVER_URL}/funding/sellopen`, fd)
                    .then((res) => {
                      // console.log(res);
                    }).catch((err) => {
                      // console.log(err)
                    })
                    let timerInterval;
                    Swal.fire({
                      title: "쇼핑 프로젝트 오픈중",
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
                      // console.log(response);
                      this.$router.push("/shoppinghome");
                      Swal.fire({
                        icon: "success",
                        title: "",
                        text: "프로젝트가 성공적으로 오픈되었습니다.",
                        showConfirmButton: false,
                      })
                    });
                   } else {
                  alert("프로젝트 오픈에 실패했습니다.");             
                  }
            })
            .catch(error => {
              // console.log(error)
            })
        }
      })
    },
    onAddItem() {
      if (this.title.length > 0 && this.openDate.length > 0  && this.price.length > 0 && this.checkCategory.length > 0 ) {
        this.addedItem = true;
        this.addedItems.push({title: this.title, opendate: this.openDate, price: this.price, category: []})
  
        for (var i = 0; i < this.checkCategory.length; i++) {
            // addedItems.category에 카테고리 push
            this.addedItems[this.addedItems.length-1].category.push(this.categoryList[this.checkCategory[i]]);
            // 체크 버튼 초기화
            $(`.${this.checkCategory[i]}`).css("background-color", "white");
            $(`.${this.checkCategory[i]}`).css("color", "black");
          }
        // checkCategory 비우기
        this.checkCategory.splice(0, this.checkCategory.length);
        this.title = "";
        this.openDate = "";
        this.price = 0;
        // console.log(this.addedItems)
      } else {
        alert('모든 정보를 입력해주세요.')
      }
    },
    onDeleteItem(item) {
      this.addedItems.splice(this.addedItems.indexOf(item),1)
    }
  }
}
</script>

<style scoped>
.investNav {
  /* height: 50px; */
  text-align: center;
  /* line-height: 50px; */
  /* border-bottom: 1px solid gray; */
  margin-bottom: 15px;
}
.items div {
  display: inline-block;
  margin: 0 10% 0 0;
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

.addedItem {
  margin: 12px;
}
.showCategory {
  color: white;
}
.summary {
  font-weight: bold;
  margin-right: 3px;
}
.v-card__actions, .v-list-item__content, .v-list-item theme--light {
  padding: 0;
}
 .v-list-item theme--light {
   padding: 0px !important;
 }

</style>
