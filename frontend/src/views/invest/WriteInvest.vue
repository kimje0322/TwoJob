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
        <v-tabs
          v-model="tab"
          background-color="deep-purple accent-4"
          class="elevation-2"
          dark
          :prev-icon="prevIcon ? 'mdi-arrow-left-bold-box-outline' : undefined"
          :next-icon="nextIcon ? 'mdi-arrow-right-bold-box-outline' : undefined"
          :icons-and-text="icons"
        >
          <!-- <v-tabs-slider></v-tabs-slider> -->
          <v-tab v-for="(item, i) in tabs" :key="i" :href="`#tab-${i}`">
            {{ item }}
          </v-tab>
          <!-- 투자오픈버튼 -->
          <div>
            <v-btn>투자 오픈</v-btn>
          </div>
          <!-- 프로젝트 정보 창 -->
          <v-tab-item :value="'tab-0'">
            <v-card flat tile>
              <v-card-text>프로젝트 정보</v-card-text>
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
import "@/../public/css/WriteInvest.css"

export default {
  data() {
    return {
      tab: null,
      text: ['1', '2', '3'],
      tabs: ['프로젝트 정보', '금손 정보', '투자 설명서'],
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
.theme--dark.v-tabs > .v-tabs-bar {
  background: unset;
}
</style>