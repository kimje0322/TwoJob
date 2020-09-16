<template>
  <div>
  <div style="height: 60px; text-align: center; border-bottom: 1px solid gray;">네브 바</div>
    <!-- 상품 게시글 제목 -->
    <div style="height: 230px; background-color: lightgrey">
      <h4 style="text-align: center; padding-top:90px;">명품 퀄리티를 그대로 재현한 울100% 가디건</h4>
    </div>
    <div class="shoppingDetail">
      <!-- 상품 사진 -->
      <div class="shoppingimg">
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
    </div>
  </div>
</template>

<script>
// import "../../../public/css/InvestHome.css";

export default {
  data() {
    return {
      items: [
        {
          src: "https://contents.lotteon.com/itemimage/LO/10/43/94/48/22/_1/04/39/44/82/4/LO1043944822_1043944824_1.jpg/dims/resizef/824X824",
        },
        {
          src: "https://greenelderbrother.com/wp-content/uploads/2019/11/%EC%96%91%ED%84%B81.jpg",
        },
        {
          src: "https://allthumb.co.kr/web/product/big/20191218/3996ec6812280dbe2a056116cb6014aa.jpg",
        },

      ]
    }
  }
}
</script>

<style>
  .shoppingDetail {
    margin: 100px 150px 15px 150px;
    text-align: center;
  }
  .shoppingimg {
    width: 35%;
  }
</style>