<template>
  <div class="chatroom">
    <v-card flat tile>
      <v-card-text style="height: 500; width: 800" class="pa-1">
        <v-list>
          <v-toolbar dense elevation="1">
            <h5 class="mx-auto">1 : 1 채팅</h5>
          </v-toolbar>

          <v-list-item style="padding: 0">
            <div style="width: 200px">
              <div>
                <!-- <div
                  style="
                    align-items: center;
                    box-sizing: border-box;
                    display: flex;
                    height: 60px;
                    padding: 10px 16px;
                  "
                >
                  <h2>채팅</h2>
                </div> -->
                <div style="height: 600px">
                  <div style="padding: 5px 15px; float: left">
                    <div
                      v-for="(lst, i) in userlst"
                      :key="i"
                      style="padding: 7px 0"
                    >
                      <img
                        v-if="lst.toimg == null"
                        src="https://file3.instiz.net/data/cached_img/upload/2020/02/26/12/f7975c2dacddf8bf521e7e6d7e4c02ee.jpg"
                        style="
                          width: 40px;
                          height: 40px;
                          border-radius: 50%;
                          display: inline-block;
                        "
                      />
                      <img
                        v-else
                        :src="lst.toimg"
                        style="
                          width: 40px;
                          height: 40px;
                          border-radius: 50%;
                          display: inline-block;
                        "
                      />
                      <span
                        style="margin-left: 20px"
                        @click="openChat(lst.roomid, lst.user1, lst.toimg)"
                        v-if="lst.user1 != username"
                      >
                        {{ lst.user1 }}
                      </span>
                      <span
                        style="margin-left: 20px"
                        @click="openChat(lst.roomid, lst.user2, lst.toimg)"
                        v-else
                      >
                        {{ lst.user2 }}
                      </span>
                    </div>
                    <!-- <v-app></v-app>
										<v-dialog max-width="640" min-height="500" v-model="openchat">
											<Chat @closeChat="closeChat"></Chat>
										</v-dialog> -->
                  </div>
                </div>
              </div>
            </div>
            <div
              style="
                display: block;
                height: 590px;
                width: 580px;
                border-left: 1px solid rgba(0, 0, 0, 0.1);
              "
            >
              <div
                style="
                  width: 570px;
                  height: 55px;
                  padding: 12px;
                  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.1);
                  border-bottom: none;
                "
              >
                <div
                  v-if="chatusername != null"
                  style="diplay: inline-block; float: left"
                >
                  <img
                    v-if="chatuserimg == null"
                    src="https://file3.instiz.net/data/cached_img/upload/2020/02/26/12/f7975c2dacddf8bf521e7e6d7e4c02ee.jpg"
                    style="
                      width: 40px;
                      height: 40px;
                      border-radius: 50%;
                      display: inline-block;
                    "
                  />
                  <img
                    v-else
                    :src="chatuserimg"
                    style="
                      width: 40px;
                      height: 40px;
                      border-radius: 50%;
                      display: inline-block;
                    "
                  />
                  <span style="margin-left: 20px">
                    {{ chatusername }}
                  </span>
                </div>
              </div>
              <div style="height: 500px">
                <!-- 대화창 -->
                <div style="height: 480px"></div>
                <!-- 메세지 입력창 -->
                <div style="height: 40px">
                  <div
                    style="
                      display: inline-block;
                      margin-left: 30px;
                      width: 400px;
                      height: 30px;
                      background-color: rgba(0, 0, 0, 0.05);
                      border-radius: 18px;
                      padding: 0 8px 0 12px;
                    "
                  >
                    <input
                      type="text"
                      placeholder="메시지를 입력하세요."
                      style="margin-top: 8px; margin-left: 5px; float: left"
                    />
                  </div>
                  <div style="display: inline-block; float: right; flex: 8">
                    <v-btn> 전송 </v-btn>
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

import Chat from "../../components/Chat.vue";

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

      chatusername: "",
      chatuserimg: "",
      chatroomid: "",
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
        if (this.userlst.toimg == null) {
          this.userlst.toimg =
            "https://file3.instiz.net/data/cached_img/upload/2020/02/26/12/f7975c2dacddf8bf521e7e6d7e4c02ee.jpg";
        }
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
    openChat(roomid, name, img) {
      this.chatusername = name;
      this.chatuserimg = img;
      this.chatroomid = roomid;
      
      axios
      .get(`${SERVER_URL}/chat/getMessage?direction=ASC&page=0&roomid=${this.chatroomid}&size=`)
      
      // axios
      //   .get(`${SERVER_URL}/chat/room/${this.chatroomid}`)
      //   .then((res) => {
      //     connsole.log(res)
      //   })

      // let chatinfo = {
      // 	chatname: name,
      // 	chatprofile: img,
      // };
      // this.openchat = true;
      // router.push( { name: "Chat", query: chatinfo })
    },
    closeChat() {
      this.openchat = false;
    },
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