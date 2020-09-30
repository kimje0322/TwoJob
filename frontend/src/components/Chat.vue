<template>
  <v-card flat tile>
    <v-card-text style="height: 500" class="pa-1">
      <v-list>
        <v-toolbar dense elevation="1">
          <span
            ><img
              :src="chatprofile"
              style="height: 40px; border-radius: 50%; display: inline-block"
          /></span>
					<span>{{ chatname }}</span>
        </v-toolbar>
      </v-list>
    </v-card-text>
    <div style="border-left: 1px solid rgba(0, 0, 0, 0.1); flex: 3">
      <div>
        <span>
          <img
            :src="userimg"
            style="height: 40px; border-radius: 50%; display: inline-block"
          />
        </span>
        <span style="padding-left: 10px"> user namedfdfdfdfdf 나옴 </span>
        <!-- 프로필 부분 -->
      </div>
      <!-- <div style="display: flex; flex: 1 1 0%"> -->
      <div>
        dfdsds
        <div style="position: relative; display: flex; flex-direction: column">
          <div style="padding-left: 12px; height: 550px">
            여기에 대화내용이나와야 됨
          </div>
          <div style="border-top: none; position: absolute">
            <div style="padding: 9px 0; font-size: 14px">
              <div>메세지를 입력하세요</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </v-card>
</template>

<script>
import axios from "axios";
import store from "@/store/index.js";
// import { component } from 'vue/types/umd';

import Vue from 'vue';

const SERVER_URL = "http://j3b102.p.ssafy.io:8080";

Vue.component('chat-item', {

})

export default {
	name: "Chat",
  data() {
    return {
			// name: this.$route.params.name,
			chatname: "",
			chatprofile: "",
			
		};
  },
  components: {},

  mounted() {
    axios
      .get(`${SERVER_URL}/chat/chatlist?accessToken=${store.state.accessToken}`)
      .then((res) => {
        console.log(res);
        this.userlst = res.data;
			});
		this.chatname = this.$route.query.chatname
		this.chatprofile = this.$$route.query.chatprofile
  },
};
</script>

<style>
</style>