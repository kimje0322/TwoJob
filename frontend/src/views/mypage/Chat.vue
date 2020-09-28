<template>
  <div>
    <!-- 상단 Navbar -->
    <navbar />
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
          <div>
            <span class="userimgbox" style="width: 35px; height: 35px">
              <img class="userimg" :src="userimg" style="height: 100%" />
            </span>
            <span> userInfo.name </span>
          </div>
        </div>
      </div>
      <div style="border-left: 1px solid rgba(0, 0, 0, 0.1); flex: 3">
        <div
          style="
            align-items: center;
            border-bottom: none;
            box-shadow: 0 1px 2px 0;
            display: flex;
            height: 60px;
            padding-left: 16px;
          "
        >
          <div>프로필 부분</div>
					<div style="display: flex; flex: 1 1 0%; flex-direction: row-reverse;">
						<div style="position: relative; display: flex; flex-direction: column;">
							<div style="padding-left: 12px; height: 550px; ">
								여기에 대화내용이나와야 됨
							</div>
							<div style="border-top: none; position: absolute;">
								<div style="padding: 9px 0; font-size: 14px;">
									<div>
										메세지를 입력하세요
									</div>

								</div>

							</div>

						</div>

					</div>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import axios from "axios";
import store from "../../store/index.js";
import Navbar from "../../components/Navbar.vue";

const SERVER_URL = "http://j3b102.p.ssafy.io:8080";

export default {
  data() {
    return {
			userlst: [],
			
			userimg: "",
    };
  },
  methods: {
    init() {
			this.userimg = store.state.userInfo.img
    // 	axios
    // 		.get( `${SERVER_URL}`)
    // 		.then((res) => {
    // 		})
    }
  },
};
</script>

<style>
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