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
          <input ref="imageInput" type="file" @change="onChangeImages" hidden />
          <div v-if="!uploadimg" @click="onClickImageUpload" class="reviewImg mx-auto mt-5">
            <v-icon>mdi-camera-plus-outline</v-icon>
            <span style="display:inline-block; margin-top:10px">사진 첨부하기</span>  
          </div>
          <!-- 이미지 첨부된 경우 -->
          <!-- 이미지 삭제 버튼 -->
          <div v-show="uploadimg">
            <v-btn x-small dark fab absolute top right color="black" style="top:181px;right:52px;opacity:.6;"
              @click="onDeleteImg"
            >
              <v-icon dark>mdi-close</v-icon>
            </v-btn>
            <img 
              class="mx-auto mt-3"
              ref="img"
              style="width: 87%; border-radius: 10px;"
              :src="imgPath+img"
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

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text @click="dialog=true">취소</v-btn>
          <v-btn text color="blue">등록</v-btn>
        </v-card-actions>
  </v-card>
</template>

<script>
import axios from "axios";
import "../../../public/css/ShoppingHome.scss";


export default {
  data() {
    return {
      dialog: false,
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
      img: 'no_image.jpg',
      imgPath: 'http://j3b102.p.ssafy.io/:8080/img/post?imgname=', 
    }
  },

  methods: {
    // point 자리에 key값 s1,s2,s3,s4,s5
    starPoint(point) {
      if (this.checkedStars) {
        var lastPoint = point.slice(-1);
        var blank = lastPoint*1 + 1;
        for (var i = 0; i <= lastPoint; i++) {
          $(`.${`s${i}`}`).css("color", "#00B0FF");
        }
        for (var j = blank; j <= 5; j++) {
          $(`.${`s${j}`}`).css("color", "#E0E0E0");
        }
      } else {
        this.checkedStars = true;
        var lastPoint = point.slice(-1)
        for (var i = 0; i <= lastPoint; i++) {
          $(`.${`s${i}`}`).css("color", "#00B0FF");
        }
      }
    },
    circlePoint(point) {
      if (this.checkedCircles) {
        var lastPoint = point.slice(-1);
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
        for (var i = 0; i <= lastPoint; i++) {
          $(`.${`c${i}`}`).css("color", "#00B0FF");
        }
      }
    },
    onDeleteImg() {
      console.log('ondeleteimg')
      this.showImg = false;
      this.postimgurl = null;
      this.postimg = 'no_image.jpg';
      this.uploadimg = false;
    },
    onClickImageUpload() {
      this.$refs.imageInput.click();
    },
    onChangeImages(e) {
      console.log('onchangeImages')
      this.showImg = true;
      this.uploadimg = true;
      this.file = e.target.files[0];
      this.imgPath = URL.createObjectURL(this.file);
      this.img = '';
    },
  }
}
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