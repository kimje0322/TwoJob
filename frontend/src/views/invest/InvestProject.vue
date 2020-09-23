<template>
  <div>
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
            <h5 style="color: rgb(22, 150, 245)">프로젝트</h5>
          </router-link>
        </div>
        <div>
          <router-link to="/writeinvest">
            <h5 style="margin: 0;">투자오픈</h5>
          </router-link>
        </div>
      </div>
    </div>
    <div style="background: white">
      <h4 style="padding: 2%; margin: 0">투자 프로젝트</h4>
    </div>
    <!-- 카테고리 -->
    <div>
      <v-app id="inspire" style="text-align:center">
        <v-container class="cateContainer">
          <v-row no-gutters>
            <!-- 정렬 맞추기 위해 왼쪽 빈칸 사용 -->
            <v-col cols="12" sm="1"></v-col>
            <v-col cols="12" sm="1">
              <v-card class="pa-2" outlined tile>
                <v-icon size="30">mdi-book-multiple-outline</v-icon>
                <!-- <br /> -->
                <p class="categoryTag">전체</p>
              </v-card>
            </v-col>
            <v-col cols="12" sm="1">
              <v-card class="pa-2" outlined tile>
                <v-icon size="30">mdi-laptop-windows</v-icon>
                <p class="categoryTag">테크, 가전</p>
              </v-card>
            </v-col>
            <v-col cols="12" sm="1">
              <v-card class="pa-2" outlined tile>
                <v-icon size="30">mdi-shoe-heel</v-icon>
                <p class="categoryTag">패션, 잡화</p>
              </v-card>
            </v-col>
            <v-col cols="12" sm="1">
              <v-card class="pa-2" outlined tile>
                <v-icon size="30">mdi-lipstick</v-icon>
                <p class="categoryTag">뷰티</p>
              </v-card>
            </v-col>
            <v-col cols="12" sm="1">
              <v-card class="pa-2" outlined tile>
                <v-icon size="30">mdi-food</v-icon>
                <p class="categoryTag">푸드</p>
              </v-card>
            </v-col>
            <v-col cols="12" sm="1">
              <v-card class="pa-2" outlined tile>
                <v-icon size="30">mdi-hair-dryer</v-icon>
                <p class="categoryTag">홈리빙</p>
              </v-card>
            </v-col>
            <v-col cols="12" sm="1">
              <v-card class="pa-2" outlined tile>
                <v-icon size="30">mdi-handball</v-icon>
                <p class="categoryTag">스포츠</p>
              </v-card>
            </v-col>
            <v-col cols="12" sm="1">
              <v-card class="pa-2" outlined tile>
                <v-icon size="30">mdi-dog</v-icon>
                <p class="categoryTag">반려동물</p>
              </v-card>
            </v-col>
            <v-col cols="12" sm="1">
              <v-card class="pa-2" outlined tile>
                <v-icon size="30">mdi-book-open-variant</v-icon>
                <p class="categoryTag">책</p>
              </v-card>
            </v-col>
            <v-col cols="12" sm="1">
              <v-card class="pa-2" outlined tile>
                <v-icon size="30">mdi-violin</v-icon>
                <p class="categoryTag">악기</p>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
      </v-app>
    </div>
  </div>
</template>

<script>
import Navbar from "../../components/Navbar.vue";

export default {
  components: {
    Navbar,
  },
  data() {
    return {
      category: [
        {icon: 'book-multiple-outline', name: '전체', key: 'all'},
        {icon: 'book-multiple-outline', name: '테크, 가전', key: 'tech'},
        {icon: 'book-multiple-outline', name: '테크, 가전', key: 'tech'},
        {icon: 'book-multiple-outline', name: '테크, 가전', key: 'tech'},
        {icon: 'book-multiple-outline', name: '테크, 가전', key: 'tech'},
        {icon: 'book-multiple-outline', name: '테크, 가전', key: 'tech'},
        {icon: 'book-multiple-outline', name: '테크, 가전', key: 'tech'},
        {icon: 'book-multiple-outline', name: '테크, 가전', key: 'tech'},
        {icon: 'book-multiple-outline', name: '테크, 가전', key: 'tech'},
      ]
    }
  }
};
</script>

<style scoped>
.investNav {
  height: 50px;
  text-align: center;
  line-height: 50px;
  border-bottom: 1px solid gray;
  border-top: 1px solid lightgray;
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
.cateContainer .v-card {
  /* margin: 0 10%; */
}
</style>