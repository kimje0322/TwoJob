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
          <div class="openbtn">
            <v-btn color="rgb(22, 150, 245)" style="font-weight: 600">투자 프로젝트 오픈</v-btn>
          </div>
          <!-- 프로젝트 정보 창 -->
          <v-tab-item :value="'tab-0'">
            <v-card flat tile>
              <v-card-text>
                <div class="pjtinfo">
                  <p>프로젝트에대한 정보를 정확하게 입력해주세요.</p>
                  <h5>프로젝트명</h5>
                  <input type="text" placeholder="프로젝트명을 입력해주세요." />
                  <h5>프로젝트 한줄 소개</h5>
                  <input type="text" placeholder="프로젝트에대해 100자이내로 설명해주세요." />
                  <h5>프로젝트 오픈 기간</h5>
                  <div>
                    <!-- 시작날짜 -->
                    <div>
                      <p>시작 날짜</p>
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
                              v-model="dateFormatted"
                              persistent-hint
                              v-bind="attrs"
                              @blur="date = parseDate(dateFormatted)"
                              v-on="on"
                              color="rgb(22, 150, 245)"
                              hide-details
                              outlined
                              placeholder="프로젝트 시작 날짜"
                            ></v-text-field>
                          </template>
                          <v-date-picker v-model="date" no-title @input="menu1 = false" color="rgb(22, 150, 245)"></v-date-picker>
                        </v-menu>
                      </div>
                    </div>
                  </div>
                </div>
              </v-card-text>
            </v-card>
          </v-tab-item>
          <!-- 금손 정보 창 -->
          <v-tab-item :value="'tab-1'">
            <v-card flat tile>
              <v-card-text>금손 정보</v-card-text>
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

export default {
  data() {
    return {
      tab: null,
      text: ["1", "2", "3"],
      tabs: ["프로젝트 정보", "금손 정보", "투자 설명서"],
      // 날짜
      date: "",
      dateFormatted: "",
      menu1: false,
    };
  },
  computed: {
    // computedDateFormatted () {
    //   return this.formatDate(this.date)
    // },
  },
  watch: {
    date(val) {
      this.dateFormatted = this.formatDate(this.date);
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
.pjtinfo h5 {
  font-size: 1.15rem;
  font-weight: 600;
}
.pjtinfo input {
  background-color: white;
  width: 90%;
  height: 40px;
  border: 1px solid lightgray;
  border-radius: 5px;
  margin-left: 10px;
  margin-bottom: 30px;
  padding: 10px;
}
.v-input__slot fieldset {
  display: none;
}
.v-menu {
  display: unset;
}
</style>