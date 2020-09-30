<template>
  <div class="chatroom">
    <v-card flat tile style="background-color: black">
      <v-card-text style="height: 550" class="pa-1">
        <v-list>
          <v-toolbar dense elevation="1">
            <h5 class="mx-auto">1 : 1 채팅</h5>
          </v-toolbar>

          <v-list-item>
            <div style="display: flex">
              <div style="min-width: 300px; flex: 0 0 25%">
                <div
                  style="
                    align-items: center;
                    box-sizing: border-box;
                    display: flex;
                    height: 60px;
                    padding: 10px 16px;
                  "
                >
                  <h2>채팅</h2>
                </div>
                <div style="height: 600px">
                  <div style="padding: 5px 10px">
                    <div
                      v-for="(lst, i) in userlst"
                      :key="i"
                      style="padding: 7px 0"
                    >
                      <img
                        :src="lst.toimg"
                        style="
													width: 40px;
                          height: 40px;
                          border-radius: 50%;
                          display: inline-block;
                        "
                      />
                      <span style="margin-left: 20px;" @click="openChat(lst.user1, lst.toimg)" v-if="lst.user1 != username"> {{ lst.user1 }} </span>
											<span style="margin-left: 20px;" @click="openChat(lst.user1, lst.toimg)" v-else> {{ lst.user2 }} </span>
                    </div>
										<v-app></v-app>
										<v-dialog max-width="640" min-height="500" v-model="openchat">
											<Chat @closeChat="closeChat"></Chat>
										</v-dialog>

                    <!-- <span class="userimgbox" style="width: 15px; height: 15px">
              <img class="userimg" :src="userimg" style="height: 10%" />
            </span> -->
                  </div>
                </div>
              </div>
            </div>
          </v-list-item>
        </v-list>
      </v-card-text>
    </v-card>
  </div>
</template>


<script>
import axios from "axios";
import store from "@/store/index.js";
import "@/../public/css/ChatRoom.scss";
import router from "@/router";

import Chat from "../../components/Chat.vue"


// import Navbar from "../../components/Navbar.vue";

const SERVER_URL = "http://j3b102.p.ssafy.io:8080";

var sock = new SockJS("http://j3b102.p.ssafy:8080/notice");
var ws = Stomp.over(sock);
var reconnect = 0;

export default {
  data() {
    return {
			// dialog: false,
			openchat: false,
			username: store.state.userInfo.name,
      userlst: [],

      userimg: "",
    };
  },
  components: {
    // Navbar,
  },

  mounted() {
    // this.userimg = store.state.userInfo.img;
    this.userimg = store.state.userInfo.img;
    axios
      .get(`${SERVER_URL}/chat/chatlist?accessToken=${store.state.accessToken}`)
      .then((res) => {
        console.log(res);
        this.userlst = res.data;
      });

    // 소켓 연결
    function connect() {
      // pub/sub event
      ws.connect(
        {},
        function (frame) {
          ws.subscribe("/sub/notice", function (message) {
            console.log("!!!!!!!!!!!!event>>", message);
          });
          ws.send("/pub/notice", {}, "msg: Haha~~~");
        },
        function (error) {
          console.log("에러다에러!!!!!");
          if (reconnect++ <= 5) {
            setTimeout(function () {
              console.log("connection reconnect");
              sock = new SockJS("http://j3b102.p.ssafy.io:8080/notice");
              ws = Stomp.over(sock);
              connect();
            }, 10 * 1000);
          }
        }
      );
    }
    connect();
  },
  methods: {
		openChat(name, img) {
			let chatinfo = {
				chatname: name,
				chatprofile: img,
			};
			this.openchat = true;
			// router.push( { name: "Chat", query: chatinfo })
		},
		closeChat() {
			this.openchat = false;
		}
    // init() {
    // 	axios
    // 		.get( `${SERVER_URL}`)
    // 		.then((res) => {
    // 		})
    // },
  },
};
</script>

<style scoped>
.userimgbox {
  border-radius: 70%;
  width: 55px;
  height: 55px;
  overflow: hidden;
}
.userimg {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>