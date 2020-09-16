<template>
  <div>
  <div style="height: 60px; text-align: center; border-bottom: 1px solid gray;">네브 바</div>
    <!-- 상품 게시글 제목 -->
    <div class="shoppingTitle" style="height: 230px; background-color: lightgrey;">
      <h4 style="text-align: center; padding-top:95px;">명품 퀄리티를 그대로 재현한 울100% 커플티</h4>
    </div>
  <div class="entirePage">
    <div class="shoppingDetail">
      <!-- 좌측: 상품 사진 -->
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
      <!-- 우측: 상품 디테일 -->
      <div style="float: left; width:36%; margin-top:10px;">
        <!-- 기업명 -->
        <p>아디단스</p>
        <!-- 상품 제목 -->
        <h3>{{ detailItems.title }}</h3>
        <hr >
        <p style="font-size: 1.2em"><strong><span style="color: rgb(22, 150, 245);">{{ detailItems.perchase }}</span></strong>개 구매 중</p>
        <!-- 카테고리 -->
        <div style="margin-bottom: 4%">
        <strong><p class="listTitle" style="margin-bottom: 1%">상품 카테고리</p></strong>
         <v-chip 
          class="categoryBadge mr-1" small
        >
          패션
        </v-chip>
        <v-chip
          class="categoryBadge mr-1" small
        >
          의류
        </v-chip>
        </div>
        <!-- 가격 -->
        <div style="margin-bottom: 4%">
        <strong><p class="listTitle" style="margin-bottom: 1%">판매가</p></strong>
        <h5>75,000원</h5>
        </div>
        <!-- 태그 -->
         <strong><p class="listTitle" style="margin-bottom: 1%">태그</p></strong>
         <v-chip
          class="mr-1" small
        >
          #반팔티
        </v-chip>
        <v-chip
          class="mr-1" small
        >
          #여름옷
        </v-chip>
        <v-chip
          class=" mr-1" small
        >
          #커플티
        </v-chip><br><br>
        <v-btn class="perchaseBtn white--text">
          <v-icon size="18" class="mr-1">mdi-cart-outline</v-icon>
          상품 구매</v-btn>
        
        <v-container class="cardContainer">
        <v-row class="cards">
          <!-- 정렬 맞추기 위해 왼쪽 빈칸 사용 -->
          <v-col
            class="otherCard"
            cols="5"    
          >
            <v-card
              style="text-align: center; padding: 5px 2px 5px 2px"
              class="pa-2"
              outlined
              tile
            >
            <v-icon size="20" class="mr-2">mdi-heart</v-icon>22<br>
            </v-card>
          </v-col>
          <v-col
            class="otherCard"
            cols="5"
          >
            <v-card
              style="text-align: center; padding: 5px 2px 5px 2px"
              class="pa-2"
              outlined
              tile
            >
            <v-icon size="20" class="mr-1">mdi-message-bulleted</v-icon>문의<br>
            </v-card>
          </v-col>
        </v-row>
        </v-container>
      </div>
    </div>
    <!-- 금손님 정보 -->
    <div class="mt-5">
      <strong><p class="mb-3">금손님 정보</p></strong>
      <div class="maker" style="float:left">
        <img class="makerImg" src="../../assets/금손.jpg" alt="">
      </div>
      <div style="float:left; margin: 3px 0 0 15px;">
        <p style="font-size: 0.9rem; margin-bottom:5px;">(주)강한하늘 대표 <strong>강하늘</strong></p>
        <p style="font-size: 0.9rem;">여행을 다니며 영감을 얻는 패션디자이너 강하늘입니다.</p>
      </div>
    </div>
    <hr style="margin-top: 80px">
    <!-- 탭 -->
    <v-tabs>
      <v-tab><strong>프로젝트 이력</strong></v-tab>
      <v-tab>상품 설명서</v-tab>
      <v-tab>리뷰</v-tab>
      <v-tab>투자금 사용내역</v-tab>
    </v-tabs><br>
    <div >
      <p>프로젝트 성공률</p>
      <img style="width: 30%;" src="../../assets/80.png" alt="">
    </div>
    <p>성공 프로젝트</p>
    <p>만든 프로젝트</p>

  </div>
  </div>
</template>

<script>
// import "../../../public/css/ShoppingHome.css";

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
      ],
      detailItems: 
        {
          title: "스위트 울 100% 커플티",
          category: "Small plates, salads & sandwiches - an intimate setting with 12 indoor seats plus patio seating.",
          perchase: "1,370,502",
          percent: "95",
        }
    }
  }
}
</script>

<style>
  .shoppingTitle{
    /* background-image: url("../../assets/커플티2.png"); */
    width: 100% !important;
  }
  .entirePage {
    margin: 30px 150px 0px 200px;
  }
  .shoppingDetail {
    height: 500px;
  }
  .shoppingimg {
    width: 50%;
    /* height: 20% ; */
    margin-right: 7%;
    float: left;
  }
  .categoryBadge {
    background-color: rgb(123, 197, 254) !important;
    color: white !important;
  }
  .perchaseBtn {
    width: 83%;
    justify-content: center;
    /* text-align: center; */
    color: white !important;
    background-color: rgb(22, 150, 245) !important;
  }
  .listTitle {
    margin-bottom: 0;
  }
  .otherCard {
    padding: 0 3px 0 0;
  }
  .maker {
    border-radius: 70%;
    width: 55px;
    height: 55px;
    overflow: hidden;
  }
  .makerImg {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  p {
    margin-bottom: 0;
  }
</style>