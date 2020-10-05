<template>
<div>
  <div class="Navbar">
    <v-app>
      <div class="items">
        <div class="serviceName">
          <router-link to="/">
            <h3>TwoJob</h3>
          </router-link>
        </div>
      </div>
    </v-app>
  </div>
     <!-- drawer -->
  <v-card>
    <v-navigation-drawer
      v-model="drawer" app right
      :mini-variant.sync="mini"
      permanent
      expand-on-hover
    >
      <v-list-item class="px-2">
        <v-list-item-avatar>
          <v-img src="https://randomuser.me/api/portraits/men/85.jpg"></v-img>
        </v-list-item-avatar>

        <v-list-item-title>　blocker</v-list-item-title>

        <v-btn
          icon
          @click.stop="mini = !mini"
        >
          <v-icon>mdi-chevron-left</v-icon>
        </v-btn>
      </v-list-item>

      <v-divider></v-divider>

      <v-list dense>
        <v-list-item
          v-for="item in items"
          :key="item.title"
          link
          @click="move(item.title)"
        >
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
  </v-card>
</div>
</template>

<script>
export default {
    data () {
      return {
        drawer: true,
        items: [
          { title: '　투자 프로젝트', icon: 'mdi-lightbulb-on-outline' },
          { title: '　쇼핑 프로젝트', icon: 'mdi-basket' },
          { title: '　로그인', icon: 'mdi-account' },
        ],
        mini: true,
        // 로그인
        userInfo: {
          id: "",
          name: "",
          img: "",
          login: false,
        },
        pg_token: "",
        login: false,
      }
    },
    methods: {
      move(title) {
        if (title == '　투자 프로젝트') {
          this.$router.push('/investhome');
        } else if (title == '　쇼핑 프로젝트') {
          this.$router.push('/shoppinghome');
        } else {
          // this.$router.push("http://developers.kakao.com/logout")
          window.Kakao.Auth.loginForm({
          success: this.GetMe,
          })
        }
      },
          GetMe(authObj) {
      console.log(authObj);
      //토큰값 받아오는 부분
      console.log(authObj.access_token);
      store.commit("setAccessToken", authObj.access_token);
      const fd = new FormData();
      fd.append("accessToken", authObj.access_token);

      axios.post(`${SERVER_URL}/login/kakaologin`, fd).then((res) => {
        console.log("여기여기");
        console.log(res);
        this.login = true;
        // store.state.isSigned = true;
        this.userInfo.login = true;
        this.userInfo.id = res.data.oauthId;
        this.userInfo.name = res.data.name;
        if (res.data.profileImg == null) {
          this.userInfo.img =
            "https://file3.instiz.net/data/cached_img/upload/2020/02/26/12/f7975c2dacddf8bf521e7e6d7e4c02ee.jpg";
        } else {
          this.userInfo.img = res.data.profileImg;
        }
        store.commit("setUserInfo", this.userInfo);
        // this.userInfo.email = res.data.email;
        console.log("이게뭐냐면");
        console.log(this.userInfo);
        // this.$router.push("/");
      });
    },
    },
  }
</script>

<style>
.Navbar {
  height: 50px;
  /* text-align: center; */
  text-align: right;
  line-height: 50px;
}
.serviceName {
  /* float: left; */
  margin-left: 20px !important;
  height: 50px;
}
.serviceName h3 {
  font-family: "Lobster", cursive;
  line-height: 50px;
  font-weight: 600;
  font-size: 30px;
  letter-spacing: 1.2px;
}
.items div a {
  color: black;
  text-decoration: none;
}
</style>