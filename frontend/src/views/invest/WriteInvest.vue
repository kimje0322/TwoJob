<template>
  <div>
    <div style="height: 60px; text-align: center; border-bottom: 1px solid gray;">네브 바</div>
    <!-- 투자 메뉴바 -->
    <div class="investNav">
      <div class="items">
        <div>
          <router-link to="/investhome">
            <h5>투자홈</h5>
          </router-link>
        </div>
        <div>
          <router-link to="/">
            <h5>프로젝트</h5>
          </router-link>
        </div>
        <div>
          <router-link to="/">
            <h5>오픈예정</h5>
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
                  <h5>프로젝트 오픈 기간</h5>
                  <div>
                    <!-- 시작날짜 -->
                    <div class="startDayBox">
                      <p style="margin: 0">시작 날짜</p>
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
                              @blur="date1 = parseDate(dateFormatted1)"
                              v-on="on"
                              color="rgb(22, 150, 245)"
                              hide-details
                              outlined
                              placeholder="프로젝트 시작 날짜"
                            ></v-text-field>
                          </template>
                          <v-date-picker
                            v-model="date1"
                            no-title
                            @input="menu1 = false"
                            color="rgb(22, 150, 245)"
                          ></v-date-picker>
                        </v-menu>
                      </div>
                    </div>
                    <!-- 물결 -->
                    <div class="tilddIcon">
                      <v-icon>mdi-tilde</v-icon>
                    </div>
                    <!-- 마감날짜 -->
                    <div class="startDayBox">
                      <p style="margin: 0">마감 날짜</p>
                      <div>
                        <v-menu
                          ref="menu2"
                          v-model="menu2"
                          :close-on-content-click="false"
                          transition="scale-transition"
                          offset-y
                          max-width="290px"
                          min-width="290px"
                        >
                          <template v-slot:activator="{ on, attrs }">
                            <v-text-field
                              v-model="dateFormatted2"
                              persistent-hint
                              v-bind="attrs"
                              @blur="date2 = parseDate(dateFormatted2)"
                              v-on="on"
                              color="rgb(22, 150, 245)"
                              hide-details
                              outlined
                              placeholder="프로젝트 시작 날짜"
                            ></v-text-field>
                          </template>
                          <v-date-picker
                            v-model="date2"
                            no-title
                            @input="menu2 = false"
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
                    v-model="receivePrice"
                    @click="removeReceivePrice"
                    type="text"
                    style="width: 35%; text-align: right; font-size: 18px"
                  />
                  <h5 style="display: inline-block; margin-left: 5px;">원</h5>
                  <h5>대표 사진</h5>
                  <v-file-input
                    :rules="rules"
                    accept="image/png, image/jpeg, image/bmp"
                    placeholder="Pick an thumbnail"
                    prepend-icon="mdi-camera"
                    outlined
                    hide-details
                  ></v-file-input>
                  <h5>소개 영상 URL</h5>
                  <input type="text" placeholder="프로젝트 소개 영상 URL을 입력해주세요." />
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
                      name="introduce"
                      id="introduce"
                      cols="98"
                      rows="10"
                      placeholder="금손님을 소개하는 글을 써주세요."
                    ></textarea>
                    <h5>금손님 소개 사이트</h5>
                    <input type="text" placeholder="ex)홈페이지, SNS" />
                  </div>
                  <!-- 개인사업자/기업 -->
                  <div v-if="business">
                    <h5>회사명</h5>
                    <input type="text" placeholder="회사명을 입력해주세요." />
                    <h5>금손님 소개</h5>
                    <textarea
                      name="introduce"
                      id="introduce"
                      cols="98"
                      rows="10"
                      placeholder="금손님을 소개하는 글을 써주세요."
                    ></textarea>
                    <h5>금손님 소개 사이트</h5>
                    <input type="text" placeholder="ex)홈페이지, SNS" />
                  </div>
                </div>
              </v-card-text>
            </v-card>
          </v-tab-item>
          <!-- 투자 설명서 창 -->
          <v-tab-item :value="'tab-2'">
            <v-card flat tile>
              <v-card-text>투자 설명서</v-card-text>
            </v-card>
          </v-tab-item>
        </v-tabs>
      </div>
    </div>
  </div>
</template>

<script>
import "@/../public/css/WriteInvest.css";
import $ from "jquery";
import Swal from "sweetalert2";

export default {
  data() {
    return {
      tab: null,
      text: ["1", "2", "3"],
      tabs: ["프로젝트 정보", "금손 정보", "투자 설명서"],
      title: "",
      content: "",
      // 날짜
      date1: "",
      dateFormatted1: "",
      date2: "",
      dateFormatted2: "",
      menu1: false,
      menu2: false,
      targetPrice: 0,
      receivePrice: 0,
      // 사진
      rules: [
        (value) =>
          !value ||
          value.size < 2000000 ||
          "Tunbnail size should be less than 2 MB!",
      ],
      // 카테고리
      categoryList: {
        tech: "테크, 가전",
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
      items: [],
      model: [],
      // 금손 정보
      items: ['개인', '개인 사업자/기업'],
      select: '',
      openMenutab: false,
      individual: false,
      business: false,
    };
  },
  computed: {
    // computedDateFormatted () {
    //   return this.formatDate(this.date)
    // },
  },
  watch: {
    date1(val) {
      this.dateFormatted1 = this.formatDate(this.date1);
    },
    date2(val) {
      this.dateFormatted2 = this.formatDate(this.date2);
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
    removeReceivePrice() {
      this.receivePrice = "";
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
  },
};
</script>

<style scoped>
.investNav {
  height: 50px;
  text-align: center;
  line-height: 50px;
  border-bottom: 1px solid gray;
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
</style>