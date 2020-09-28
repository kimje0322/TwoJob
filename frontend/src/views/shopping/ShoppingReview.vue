<template>
    <v-card flat tile>

      <!-- 리뷰 작성부분 --> 
      <v-card-text style="height:450;" class="pa-1">
        <v-list>
          <input ref="imageInput" type="file" hidden />
          <!-- 만족도 확인 -->
          <v-toolbar dense elevation="1">
            <h5 class="mx-auto">리뷰 등록</h5>
          </v-toolbar>
          
          <div style="text-align: center; margin-top: 30px;">
            <h4>상품은 만족하셨나요?</h4>
            <v-icon :class="key"
            v-for="key in stars"
            :key="key"
            @click="starPoint(key)" size=33 
            style="color: #E0E0E0">
            mdi-star
            </v-icon>
          </div>
          <!-- 리뷰 내용 -->
          <v-list-item>0
            <v-textarea
              v-model = "content"
              min-height = "300px"
              prepend-inner-icon="mdi-lead-pencil"
              rows="2" class="mx-2" 
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
      checkedStars: false,
      content: '',
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
  }
}
</script>

<style scoped>
 /* .v-input__slot {
   min-height: 100px !important;
 } */
</style>