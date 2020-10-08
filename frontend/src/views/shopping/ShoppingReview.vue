<template>
    <v-card flat tile>
      <!-- 리뷰 작성부분 --> 
      <v-card-text style="height:450;" class="pa-1">
        <v-list>
          <!-- 만족도 확인 -->
          <v-toolbar dense elevation="1">
            <h5 class="mx-auto">리뷰 등록</h5>
          </v-toolbar>
          
          <div style="text-align: center; margin-top: 30px;">
            <h4>상품은 만족하셨나요?</h4>
            <v-icon :class="key"
            v-for="key in stars"
            :key="key"
            @click="starPoint(key)" size=43 
            style="color: #E0E0E0">
            mdi-star
            </v-icon>
            <h4 class="mt-5">상품 설명서와 비슷했나요?</h4>
            <v-icon :class="key"
            v-for="key in circles"
            :key="key"
            @click="circlePoint(key)" size=43
            style="color: #E0E0E0">
            mdi-circle-slice-8
            </v-icon>
          </div>
          <!-- 이미지 업로드 버튼 -->
          <input :value="this.reviewPic" :rules="rules" ref="imageInput" type="file" @change="onChangeImages" hidden />
          <div v-if="!uploadimg" @click="onClickImageUpload" class="reviewImg mx-auto mt-5">
            <v-icon>mdi-camera-plus-outline</v-icon>
            <span style="display:inline-block; margin-top:10px">사진 첨부하기</span>  
          </div>
          <!-- 이미지 첨부된 경우 -->
          <!-- 이미지 삭제 버튼 -->
          <div v-show="imgPath">
            <v-btn x-small dark fab absolute top right style="background-color: black !important; top:293px; right:47px;opacity:.6;"
              @click="onDeleteImg"
            >
              <v-icon dark>mdi-close</v-icon>
            </v-btn>
            <img 
              class="mx-auto mt-3"
              ref="img"
              style="width: 87%; border-radius: 10px;"
              :src="imgPath"
              alt="reviewImg"
            />
          </div>        
          <!-- 리뷰 내용 -->
          <v-list-item>
            <v-textarea
              v-model = "content"
              solo
              prepend-inner-icon="mdi-lead-pencil"
              rows="2" class="mx-5 mt-6" 
              placeholder="리뷰를 작성해주세요."
              clearable
            ></v-textarea>
          </v-list-item>
        </v-list>
      </v-card-text>
    <!-- <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn text @click="dialog=false">취소</v-btn>
      <v-btn text color="blue">등록</v-btn>
    </v-card-actions> -->

    <!-- 페이지 이동후 삭제할 버튼, axios 요청 이 페이지에서 하기위함 -->
    <v-btn color="warning" @click="createReview">임시등록 버튼</v-btn>
  </v-card>
</template>

<script>
import axios from "axios";
import "../../../public/css/ShoppingHome.scss";
import { componentsPlugin } from 'bootstrap-vue';

const SERVER_URL = "https://www.twojob.ga/api";

export default {
  data() {
    return {
      dialog: false,
      satisfaction: 0,
      similarity: 0,
      starpoint: '',
      circlepoint: '',
      stars: {
        star1: "s1",
        star2: "s2",
        star3: "s3",
        star4: "s4",
        star5: "s5",
      },
      circles: {
        circle1: "c1",
        circle2: "c2",
        circle3: "c3",
        circle4: "c4",
        circle5: "c5",
      },
      checkedStars: false,
      checkedCircles: false,
      uploadimg: false,
      showImg : true,
      content: '',
      img: '',
      imgPath: '', 
      reviewPic: '',
      rules: [
        (reviewPic) =>
          !reviewPic ||
          reviewPic.size < 2000000 ||
          "2MB 이하의 사진을 올려주세요!",
      ],
    }
  },
  updated() {
    if (this.satisfaction) {
      this.starPoint('s' + this.satisfaction)
    }
    if (this.similarity) {
      this.circlePoint('c' + this.similarity)
    }
  },
  methods: {
    // point 자리에 key값 s1,s2,s3,s4,s5
    starPoint(point) {
      if (this.checkedStars) {
        this.satisfaction = point.slice(-1)*1;
        var blank = this.satisfaction + 1;
        for (var i = 0; i <= this.satisfaction; i++) {
          $(`.${`s${i}`}`).css("color", "#00B0FF");
        }
        if (this.satisfaction < 5) {
          for (var j = blank; j <= 5; j++) {
            $(`.${`s${j}`}`).css("color", "#E0E0E0");
          }
        }
      } else {
        this.satisfaction = point.slice(-1)*1
        for (var i = 0; i <= this.satisfaction; i++) {
          $(`.${`s${i}`}`).css("color", "#00B0FF");
        }
        this.checkedStars = true;
      }
    },
    circlePoint(point) {
      if (this.checkedCircles) {        
        var lastPoint = point.slice(-1);
        this.similarity = lastPoint*1
        var blank = lastPoint*1 + 1;
        for (var i = 0; i <= lastPoint; i++) {
          $(`.${`c${i}`}`).css("color", "#00B0FF");
        }
        for (var j = blank; j <= 5; j++) {
          $(`.${`c${j}`}`).css("color", "#E0E0E0");
        }
      } else {
        this.checkedCircles = true;
        var lastPoint = point.slice(-1)
        this.similarity = lastPoint*1
        for (var i = 0; i <= lastPoint; i++) {
          $(`.${`c${i}`}`).css("color", "#00B0FF");
        }
      }
    },
    onDeleteImg() {
      this.showImg = false;
      this.uploadimg = false;
      this.imgPath = '';
    },
    onClickImageUpload() {
      // console.log('onClickImageUpload');
      // console.log(this.satisfaction+'만족도');
      this.$refs.imageInput.click();
    },
    onChangeImages(e) {
      // console.log("onchangeImages");
      this.showImg = true;
      this.uploadimg = true;
      this.file = e.target.files[0];
      // console.log(this.file);
      // this.imgPath = URL.createObjectURL(this.file);
      // this.img = '';

      var formData = new FormData();
      formData.append("img", this.file);
      axios.post(`${SERVER_URL}/investment/changePath`, formData, { 
                headers: { 'Content-Type': 'multipart/form-data' } 
        }).then(response => {
            const cutUrl = response.data.substr(18, response.data.length-17)
            const imgUrl = 'https://twojob.ga/' + cutUrl
            this.imgPath = imgUrl;   
        });
    },
    // 리뷰등록 버튼
    // saleaddress, userid 가져오기
    createReview() {
      axios.post(`${SERVER_URL}/sale/Review`, {
        reviewPicture: this.imgPath,
        reviewexplain: this.content,
        saleaddress: 'c270cc9e-52ef-4e92-afca-ae26de74b971',
        satisfied: this.satisfaction,
        similar: this.similarity,
        userid: "1480844047"
      })
      .then(response => {
        // console.log(response)
      })
      .catch(error => {
        // console.log(error)
      })
    },
  },
};
</script>

<style scoped>
  .reviewImg {
    background-color: #E1F5FE;
    width: 60%;
    padding: 5px 0px 12px 0px;
    border-radius: 15px;
  }
  .reviewImg:hover {
   cursor: pointer;
  }
</style>

