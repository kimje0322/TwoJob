<template>
  <div class="investproject">
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
    <div>
      <h4 style="padding: 2%; margin: 0">투자 프로젝트</h4>
    </div>
    <!-- 카테고리 -->
    <div>
      <!-- <v-app id="inspire" style="text-align:center"> -->
        <v-container class="cateContainer" style="text-align:center">
          <v-row no-gutters>
            <!-- 정렬 맞추기 위해 왼쪽 빈칸 사용 -->
            <v-col cols="12" sm="1"></v-col>
            <!-- 카테고리 for문 -->
            <v-col v-for="(category, i) in categoryList" :key="i" cols="12" sm="1">
              <v-card class="pa-2 categoryCard" outlined tile>
                <v-icon size="30">mdi-{{category.icon}}</v-icon>
                <p class="categoryTag">{{category.name}}</p>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
      <!-- </v-app> -->
    </div>
    <!-- 필터 -->
    <div style="padding: 0 3%">
      <div style="width: 100px; display: inline-block; margin-right: 2%">
        <v-select
          :items="state"
          label="상태"
          outlined
          hide-details
        ></v-select>
      </div>
      <div style="width: 100px; display: inline-block">
        <v-select
          :items="state"
          label="달성률"
          outlined
          hide-details
        ></v-select>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "../../components/Navbar.vue";
import "../../../public/css/InvestProject.scss";

export default {
  components: {
    Navbar,
  },
  data() {
    return {
      categoryList: [
        {icon: 'book-multiple-outline', name: '전체', key: 'all'},
        {icon: 'laptop-windows', name: '테크, 가전', key: 'tech'},
        {icon: 'shoe-heel', name: '패션, 잡화', key: 'fashion'},
        {icon: 'lipstick', name: '뷰티', key: 'beauty'},
        {icon: 'food', name: '푸드', key: 'food'},
        {icon: 'hair-dryer', name: '홈리빙', key: 'home'},
        {icon: 'handball', name: '스포츠', key: 'sports'},
        {icon: 'dog', name: '반려동물', key: 'animal'},
        {icon: 'book-open-variant', name: '책', key: 'book'},
        {icon: 'violin', name: '악기', key: 'instrument'},
      ],
      nowstate: "상태",
      state: ['전체 프로젝트', '진행중인 프로젝트', '성공한 프로젝트', '종료된 프로젝트'],
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
.categoryCard {
  padding: 8px;
}
.categoryCard:hover {
  border: 2px solid  rgb(22, 150, 245);
  cursor: pointer;
}
</style>