<template>
  <div class="home">
    <!-- 상단 Navbar -->
    <navbar />
    <!-- 투자 글쓰기 메뉴바 -->
    <div style="max-width: 1000px; margin: 0 auto;">
      <!-- 투자 글쓰기 메뉴 -->
      <div class="writeMenuBar">
        <v-tabs v-model="tab" class="elevation-2" dark hide-slider>
          <v-tab
            style="color: black;"
            v-for="(item, i) in tabs"
            :key="i"
            :href="`#tab-${i}`"
            class="writeMenu"
          >{{ item }}</v-tab>
          <!-- 프로젝트 정보 창 -->
          <v-tab-item :value="'tab-0'">
            <v-card flat tile>
              <v-card-text>
                <!-- style="float: left; padding: 50px 20px 0; width: 200px; box-sizing: border-box;" -->
                <div>
                  <div style="width: 55px; display: inline-block; margin: 0 auto 25px;">
                    <img :src="userimg" style="height: 100px; border-radius: 50%" />
                  </div>
                  <div style="text-align: center">
                    <h5>{{username}} 님</h5>
                    </div>
                  <div>총 {{userbalance}}.0 원</div>
                </div>

                <div style="display: inline-block; margin-left: 200px;">
                  <h4 style="flex: 7">마이페이지</h4>
                </div>
              </v-card-text>
            </v-card>
          </v-tab-item>
          <!-- 금손 정보 창 -->
          <v-tab-item :value="'tab-1'">
            <v-card flat tile>
              <v-card-text></v-card-text>
            </v-card>
          </v-tab-item>
        </v-tabs>
      </div>
    </div>
  </div>
</template>

<script>
// import Vue from 'vue'
import axios from "axios";
// import router from "@/routes";

// import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
// import 'bootstrap/dist/css/bootstrap.css'
// import 'bootstrap-vue/dist/bootstrap-vue.css'
// import "../../../public/css/Home.scss";
import store from "../../store/index.js";
import Navbar from "../../components/Navbar.vue";
import "@/../public/css/WriteInvest.scss";

export default {
  methods: {},
  components: {
    Navbar,
  },
  mounted() {
    this.userimg = store.state.userInfo.img;
    this.username = store.state.userInfo.name;
    this.userbalance = store.state.balance;
  },
  data() {
    return {
      userimg: "",
      username: "",
      userbalance: "",
      tab: null,
      text: ["1", "2", "3"],
      tabs: ["큰손", "금손"],
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
      select: "",
      openMenutab: false,
      individual: false,
      business: false,
    };
  },
  computed: {},
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
  padding: 16px 5%;
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