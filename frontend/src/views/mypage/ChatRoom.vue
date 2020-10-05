<template>
  <div class="chatroom">
    <v-card flat tile>
      <v-card-text style="height: 500; width: 800" class="pa-1">
        <v-list>
          <!-- <v-toolbar dense elevation="1">
            <h5 class="mx-auto">1 : 1 채팅</h5>
          </v-toolbar> -->

          <v-list-item style="padding: 0">
            <div style="width: 270px">
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
                  <div style="padding: 5px 0; float: left; width: 220px">
                    <div
                      style="
                        height: 55px;
                        padding: 12px;
                        box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.1);
                        border-bottom: none;
                        font-weight: ;
                      "
                    >
                      <h5
                        style="
                          font-size: 15px;
                          padding: auto;
                          text-align: center;
                        "
                      >
                        1 : 1 채팅
                      </h5>
                    </div>
                    <div
                      class="profile"
                      v-for="(lst, i) in userlst"
                      :key="i"
                      style="padding: 10px 0"
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
                        <!-- <router-link
                          :to="{ name: 'Mypage', params: { userid: lst.}}"
                        > -->
                        {{ lst.user1 }}
                        <!-- </router-link> -->
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
                <div v-if="click == false">
                  <h5>채팅 상대를 선택해주세요</h5>
                </div>
                <!-- v-if="chatusername != null" -->
                <div v-else style="diplay: inline-block; float: left">
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
                <!-- <body> -->
                <!-- 대화창 -->
                <div
                  style="
                    height: 480px;
                    overflow-x: hidden;
                    overflow-y: auto;
                    position: relative;
                  "
                >
                  <!-- <div v-if="chatmessage"></div> -->
                  <!-- onscroll="chat_on_scroll()"
                    id="app_chat_list" -->

                  <div
                    v-for="(lst, i) in chatmessage"
                    :key="i"
                    style="margin: 20px 10px"
                  >
                    <!-- 상대방 메시지 -->
                    <div
                      v-if="lst.sender != username"
                      style="
                        width: 250px;
                        height: 30px;
                        background-color: rgb(0, 153, 255);
                        border-radius: 18px;
                        padding: 5px;
                        color: white;
                        margin-bottom: 40px;
                      "
                    >
                      {{ lst.message }}
                      <div
                        style="
                          color: rgba(0, 0, 0, 0.4);
                          margin-bottom: 5px;
                          text-align: left;
                        "
                      >
                        {{ lst.time }}
                      </div>
                    </div>

                    <!-- 내 메시지 -->
                    <div
                      v-else
                      style="
                        float: right;
                        width: 250px;
                        height: 30px;
                        background-color: rgba(0, 0, 0, 0.15);
                        border-radius: 18px;
                        padding: 5px;
                        color: white;
                        margin-bottom: 40px;
                        margin-left: 200px;
                      "
                    >
                      {{ lst.message }}
                      <div
                        style="
                          color: rgba(0, 0, 0, 0.4);
                          margin-bottom: 5px;
                          text-align: right;
                        "
                      >
                        {{ lst.time }}
                      </div>
                    </div>
                  </div>
                  <!-- <div v-for="(message, j) in messages" :key="j">
                    {{ message }}
                    dd
                  </div> -->
                </div>
                <!-- 메세지 입력창 -->
                <div v-if="click == true" style="height: 40px">
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
                    <!-- <body> -->
                    <input
                      id="inputxt"
                      type="text"
                      placeholder="메시지를 입력하세요."
                      style="margin-top: 8px; margin-left: 5px; float: left"
                      v-model="message"
                      v-on:keypress.enter="sendMessage"
                    />
                    <!-- </body> -->
                  </div>
                  <!-- <div style="display: inline-block; float: right; flex: 8">
                    <v-btn @click="sendMessage"> 전송 </v-btn>
                  </div> -->
                </div>
                <!-- </body> -->
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
import Vue from "vue";

// import Chat from "../../components/Chat.vue";

// import Navbar from "../../components/Navbar.vue";

// const SERVER_URL = "https://www.twojob.ga/api";
const SERVER_URL = "http://j3b102.p.ssafy.io:8080"

var sock = new SockJS("https://www.twojob.ga/api/notice");
// var sock = new SockJS("http://j3b102.p.ssafy.io:8080/api/notice");
var ws = Stomp.over(sock);
var reconnect = 0;
// var pre_diffHeight = 0;
// var bottom_flag = true;
// var chat_on_scroll = function () {
//   var objDiv = document.getElementById("app_chat_list");

//   if (objDiv.scrollTop + objDiv.clientHeight == objDiv.scrollHeight) {
//     bottom_flag = true;
//   }
//   if (pre_diffHeight > objDiv.scrollTop + objDiv.clientHeight) {
//     bottom_flag = false;
//   }
//   //
//   pre_diffHeight = objDiv.scrollTop + objDiv.clientHeight;
// };

// var app_chat_list = new Vue({
//   el: "#app_chat_list",
//   data: function () {
//     return returnData;
//   },
//   updated: function () {
//     // app_chat_list 의 변화가 발생할때마다 수행되는 영역

//     var objDiv = document.getElementById("app_chat_list");
//     if (bottom_flag) {
//       // 채팅창 스크롤 바닥 유지
//       objDiv.scrollTop = objDiv.scrollHeight;
//     }
//   },
// });

// 소켓 연결
function connect() {
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
          sock = new SockJS("https://www.twojob.ga/api/notice");
          // sock = new SockJS("http://j3b102.p.ssafy.io:8080/api/notice");
          ws = Stomp.over(sock);
          connect();
        }, 10 * 1000);
      }
    }
  );
}
connect();

function connection() {
  ws.connection(
    {},
    function (frame) {
      ws.subscribe("/sub/chat/room" + this.chatroomid, function (message) {
        var recv = JSON.parse(message.body);
        vm.recvMessage(recv);
      });
      ws.send(
        "/pub/chat/message",
        {},
        JSON.stringify({
          type: "ENTER",
          roomid: this.chatroomid,
          sender: store.state.userInfo.name,
        })
      );
    },
    function (error) {
      if (reconnect++ <= 5) {
        setTimeout(function () {
          console.log("connection reconnect");
          sock = new SockJS("https://www.twojob.ga/api/ws-stomp");
          // sock = new SockJS("http://j3b102.p.ssafy.io:8080/api/ws-stomp");
          ws = Stomp.over(sock);
          connection();
        }, 10 * 1000);
      }
    }
  );
}

export default {
  data() {
    return {
      // dialog: false,
      openchat: false,
      username: store.state.userInfo.name,
      userlst: [],

      userimg: "",
      click: false,

      chatusername: "",
      chatuserimg: "",
      chatroomid: "",
      chatmessage: [],
      totalmessage: "",

      message: "",
      messages: [],
      sender: "",

      createroom: false,
    };
  },
  components: {
    // Navbar,
  },

  mounted() {
    // sender
    this.sender = localStorage.getItem("wschat.sender");
    // console.log("이거는 sender야" + this.sender);
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

        if (store.state.askusername != null) {
          console.log("askusername" + store.state.askusername);
          this.createroom = true;
          // console.log(this.userlst[1].user1);
          for (var i = 0; i < this.userlst.length; i++) {
            if (this.userlst[i].user1 == store.state.askusername) {
              this.createroom = false;
              console.log(1);
            }
            if (this.userlst[i].user2 == store.state.askusername) {
              this.createroom = false;
              console.log(2);
            }
          }
          if (this.createroom) {
            console.log("채팅방만들기");
            this.createRoom();
          }
        }
      });
    // console.log("askusername" + store.state.askusername)
  },
  methods: {
    openChat(roomid, name, img) {
      this.click = true;

      this.chatusername = name;
      this.chatuserimg = img;
      this.chatroomid = roomid;

      this.chatmessage = [];
      this.totalmessage = "";

      axios
        .get(
          `${SERVER_URL}/chat/getMessage?direction=ASC&page=0&roomId=${this.chatroomid}&size=1`
        )
        .then((res) => {
          console.log(res);
          this.totalmessage = res.data.totalElements;
          console.log(this.totalmessage);
          if (this.totalmessage == "0") {
          } else {
            axios
              .get(
                `${SERVER_URL}/chat/getMessage?direction=ASC&page=0&roomId=${this.chatroomid}&size=${this.totalmessage}`
              )
              .then((res) => {
                this.chatmessage = res.data.content;
                console.log(this.chatmessage);
              });
            // this.chatmessage =
          }
        });
    },

    closeChat() {
      this.openchat = false;
    },

    // 채팅 전송
    sendMessage: function (recv) {
      if (this.message == "") {
      } else {
        ws.send(
          "/pub/chat/message",
          {},
          JSON.stringify({
            type: "TALK",
            roomId: this.chatroomid,
            sender: store.state.userInfo.name,
            message: this.message,
          })
        );
        const today = new Date();
        let year = today.getFullYear(); // 년도
        let month = today.getMonth() + 1; // 월
        let date = today.getDate();
        let hours = today.getHours(); // 시
        let minutes = today.getMinutes(); // 분
        let seconds = today.getSeconds();
        const d = `${year}-${month}-${date} ${hours}:${minutes}:${seconds}`;
        const messageBox = {
          message: this.message,
          time: d,
          sender: this.username,
        };
        this.chatmessage.push(messageBox);
        this.message = "";
        // this.openChat(this.chatroomid, this.chatusername, this.chatroomid)
        // document.body.scrollTop = document.body.scrollHeight;
        // console.log("스크롤좀 내려줘");
      }
    },

    // 채팅 수신
    recvMessage: function (recv) {
      this.messages.unshift({
        type: recv.type,
        sender: recv.type == "ENTER" ? "[알림]" : recv.sender,
        message: recv.message,
        time: recv.time,
      });
      console.log("메시지 수신");
      console.log(this.messages);
    },

    // 채팅방 만들기
    createRoom: function () {
      console.log("채팅방만들기11");
      const fd = new FormData();
      fd.append("fromAccessToken", store.state.accessToken);
      fd.append("toOauthID", store.state.askuserid);
      console.log("여기여깅겨이겨이")
      console.log(store.state.accessToken)
      console.log(store.state.askuserid)
      axios
        .post(`${SERVER_URL}/chat/startchat`, fd)
        .then((response) => {
          alert("방 개설에 성공하였습니다.");
          // this.mounted();
          // this.room_name = "";
          // this.findAllRoom();
          this.chatlist()
        })
        .catch((response) => {
          alert("채팅방 개설에 실패하였습니다.");
        });
    },
    chatlist(){
      axios
        .get(`${SERVER_URL}/chat/chatlist?accessToken=${store.state.accessToken}`)
        .then(response =>{
          console.log("채팅 리스트")
          console.log(response)
          this.userlst = response.data;
        })
        .catch(error=>{
          console.log(error)
        })
    },
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
.profile:hover {
  background-color: #efefef;
}
</style>
