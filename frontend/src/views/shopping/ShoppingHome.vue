<template>
  <div class="shoppingHome">
    <navbar/>
    <!-- 쇼핑 메뉴바 -->
    <div class="shoppingNav">
      <div class="items">
        <div>
          <router-link to="/shoppinghome">
            <h5 style="color: rgb(22, 150, 245)">쇼핑홈</h5>
          </router-link>
        </div>
        <div>
          <router-link to="/shoppingproject">
            <h5>프로젝트</h5>
          </router-link>
        </div>
        <div>
          <router-link to="/shoppinghome">
            <h5>오픈예정</h5>
          </router-link>
        </div>
        <div>
          <router-link to="/writeshopping">
            <h5>쇼핑 오픈</h5>
          </router-link>
        </div>
      </div>
    </div>
    <!-- 쇼핑홈 광고사진 -->
    <div class="homeImg">
      <v-carousel>
        <v-carousel-item
          v-for="(item,i) in items"
          :key="i"
          :src="item.src"
          reverse-transition="fade-transition"
          transition="fade-transition"
        ></v-carousel-item>
      </v-carousel>
    </div>
    <!-- 카테고리 -->
    <v-app id="inspire" style="text-align:center">
      <v-container class="cateContainer">
        <v-row no-gutters>
          <!-- 정렬 맞추기 위해 왼쪽 빈칸 사용 -->
          <v-col cols="12" sm="1"></v-col>
          <!-- 카테고리 for문 -->
          <v-col v-for="(category, i) in categoryList" :key="i" cols="12" sm="1">
            <v-card
              class="pa-2"
              outlined
              tile
            >
              <v-icon size="30">mdi-{{category.icon}}</v-icon><br>
              <p class="categoryTag">{{category.name}}</p>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-app>

    <!-- 품절임박 -->
    <div style="margin-left: 5%;">
      <h4 style="font-weight: 600">품절임박<v-icon style="font-size: 36px; color: black;">mdi-chevron-right</v-icon></h4>
    </div>
    <div style="display: flex; padding: 1% 0">
      <div v-for="(item, i) in deadlineItems" :key="i" style="display: inline-block; flex:1">
        <v-card :loading="loading" class="my-12" max-width="320" style="margin: auto">
          <router-link to="/ShoppingDetail">
          <v-img height="250" src="https://cdn.vuetifyjs.com/images/cards/cooking.png"></v-img>
          </router-link>
          <v-card-title style="font-weight: 600; margin: auto">{{item.title}}
            <div style="margin-left: auto;"><v-chip class="deadlineBadge">{{item.deadline}}개 남음</v-chip></div>
          </v-card-title>
          <v-card-text>
            <div style="margin-bottom: 15px;">{{item.content}}</div>
            <!-- <div style="color: black;">
              <h5 style="display: inline-block; height: 41.6px; line-height: 41.6px">{{item.price}} 원</h5>
            </div> -->
          </v-card-text>
        </v-card>
      </div>
    </div>
    <!-- 인기순 -->
    <div style="margin-left: 5%; margin-top: 3%">
      <h4 style="font-weight: 600">인기순<v-icon style="font-size: 36px; color: black;">mdi-chevron-right</v-icon></h4>
    </div>
    <div style="display: flex; padding: 1% 0">
      <div v-for="(item, i) in likeItems" :key="i" style="display: inline-block; flex:1">
        <v-card :loading="loading" class="my-12" max-width="320" style="margin: auto">
          <v-img height="250" src="https://cdn.vuetifyjs.com/images/cards/cooking.png"></v-img>
          <v-card-title style="font-weight: 600; margin: auto">{{item.title}}
            <div style="margin-left: auto;"><v-chip class="likeBadge">{{item.likenum}}명 좋아요</v-chip></div>
          </v-card-title>
          <v-card-text>
            <div style="margin-bottom: 15px;">{{item.content}}</div>
            <!-- <div style="color: black; display: inline-block; float: right;">
              <v-icon size="20" class="mr-1">mdi-heart</v-icon>128
            </div> -->
          </v-card-text>
        </v-card>
      </div>
    </div>
    <!-- 오픈예정 -->
    <div style="margin-left: 5%; margin-top: 3%">
      <h4 style="font-weight: 600">오픈예정<v-icon style="font-size: 36px; color: black;">mdi-chevron-right</v-icon></h4>
    </div>
    <div style="padding: 1% 0">
      <v-card v-for="(item, i) in openItems" :key="i" style="width: 38%; height: 180px; display: inline-block; margin: 0 6% 4% 6%;">
        <v-img style="width:33%; float:left;" height="180" src="https://cdn.vuetifyjs.com/images/cards/cooking.png"></v-img>
        <div style="width: 67%; float:right;">
          <v-card-title style="font-weight: 600;">{{item.title}}
            <div style="margin-left: auto;">
              <v-icon style="color:rgb(22, 150, 245); margin-right: 10px">mdi-clock-outline</v-icon><span class="openBadge">{{item.opendate}}일 뒤 오픈</span>
            </div>
          </v-card-title>
          <v-card-text>
            <div style="margin-bottom: 15px;">{{item.content}}</div>
            <div style="color: black;">
              <strong><p style="display: inline-block; height: 41.6px; line-height: 41.6px">판매 예정금액 {{item.price}} 원</p></strong>
            </div>
          </v-card-text>
        </div>
      </v-card>
    </div>
  </div>
</template>

<script>
import "../../../public/css/InvestHome.scss";
import "../../../public/css/ShoppingHome.scss";
import Navbar from "../../components/Navbar.vue";

export default {
  components: {
    Navbar
  },
  data() {
    return {
      categoryList: [
        { icon: "book-multiple-outline", name: "전체", key: "all" },
        { icon: "laptop-windows", name: "테크, 가전", key: "tech" },
        { icon: "shoe-heel", name: "패션, 잡화", key: "fashion" },
        { icon: "lipstick", name: "뷰티", key: "beauty" },
        { icon: "food", name: "푸드", key: "food" },
        { icon: "hair-dryer", name: "홈리빙", key: "home" },
        { icon: "handball", name: "스포츠", key: "sports" },
        { icon: "dog", name: "반려동물", key: "animal" },
        { icon: "book-open-variant", name: "책", key: "book" },
        { icon: "violin", name: "악기", key: "instrument" },
      ],
      items: [
        {
          src: "https://image.freepik.com/free-photo/_93675-87338.jpg",
        },
        {
          src:
            "https://lh3.googleusercontent.com/proxy/eEAKz6kNc0gXbYyQR5AM2PFZQYoKepaO4JbqZAjJWtbGtTPvBVfh_2sTRVlj6Woh-UIHNphyKm9bIdkz5va1p2KKywJyl7Ed4872B5o9yQ",
        },
        {
          src:
            "https://cdn.wadiz.kr/wwwwadiz/green002/2019/0430/20190430185646591_32703.jpg/wadiz/format/jpg/quality/80/optimize",
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/planet.jpg",
        },
      ],
      deadlineItems: [
        {
          title: "Ostay 다이슨 드라이어",
          deadline: "2",
          content: "Small plates, salads & sandwiches - an intimate setting with 12 indoor seats plus patio seating.",
          price: "1,500,500",
          percent: "98",
        },
        {
          title: "특별한 숟가락",
          deadline: "3",
          content: "Small plates, salads & sandwiches - an intimate setting with 12 indoor seats plus patio seating.",
          price: "1,300,500",
          percent: "95",
        },
        {
          title: "달라진 안경",
          deadline: "7",
          content: "Small plates, salads & sandwiches - an intimate setting with 12 indoor seats plus patio seating.",
          price: "1,000,500",
          percent: "88",
        },
      ],
      likeItems: [
        {
          title: "특별한 자전거",
          likenum: "10000",
          content: "Small plates, salads & sandwiches - an intimate setting with 12 indoor seats plus patio seating.",
          price: "1,500,500",
          percent: "92",
        },
        {
          title: "특별한 숟가락",
          likenum: "5000",
          content: "Small plates, salads & sandwiches - an intimate setting with 12 indoor seats plus patio seating.",
          price: "1,300,500",
          percent: "85",
        },
        {
          title: "달라진 안경",
          likenum: "3500",
          content: "Small plates, salads & sandwiches - an intimate setting with 12 indoor seats plus patio seating.",
          price: "1,000,500",
          percent: "79",
        },
      ],
      openItems: [
        {
          title: "특별한 자전거",
          opendate: "2",
          content: "Small plates, salads & sandwiches - an intimate setting with 12 indoor seats plus patio seating.",
          price: "39,000",
        },
        {
          title: "특별한 숟가락",
          opendate: "5",
          content: "Small plates, salads & sandwiches - an intimate setting with 12 indoor seats plus patio seating.",
          price: "75,000",
        },
        {
          title: "달라진 안경",
          opendate: "10",
          content: "Small plates, salads & sandwiches - an intimate setting with 12 indoor seats plus patio seating.",
          price: "1,000,000",
        },
        {
          title: "날이 없는 선풍기",
          opendate: "12",
          content: "Small plates, salads & sandwiches - an intimate setting with 12 indoor seats plus patio seating.",
          price: "50,000",
        },
      ]
    };
  },
  methods: {

  },
};
</script>

<style scoped>
.shoppingNav {
  height: 50px;
  text-align: center;
  line-height: 50px;
  border-top: 1px solid lightgray;
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
  color:
   rgb(22, 150, 245);
}
.items h5 {
  font-weight: 600;
}
.homeImg {
  margin-bottom: 3%;
}
.deadlineBadge {
  background-color: rgb(22, 150, 245) !important;
  color: white !important;
  text-align: right;
}
.likeBadge {
  background-color: red !important;
  color: white !important;
  text-align: right;
}
.openBadge {
  color: rgb(22, 150, 245);
}
.categoryTag {
  margin-bottom: 0;
}
.v-application--wrap {
  min-height: 0;
}
</style>