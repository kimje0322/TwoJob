<template>
  <div>
    <div id="main-overview" class="container">
      <div class="row">
        <div class="col-md-12">
          <!-- <h1>DCOMMERCE PLATFORM</h1>
          <h4 class="mt-5">
            블록체인 기반의 에스크로를 사용하는 전자상거래 플랫폼을 이용해보세요!
          </h4>-->
          <div class="search_bar">
            <input class="search_text" type="text" placeholder="검색어를 입력하세요." />
            <button style="flex-right: 0">
              <i class="fas fa-search"></i>
            </button>
          </div>
          <div class="search_word">
            <div style="border-style: solid;">
              <p>인기 검색어</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import Vue from 'vue'
// import "font-awesome/css/font-awesome.min.css";

export default {};
</script>

<style scoped>
.search_bar {
  /* padding-top: 150px; */
  margin: 0 auto;
  border-bottom: 1px solid #333;
  width: 700px;
}
.search_text {
  width: 680px;
  background-color: transparent;
  border-color: transparent;
  /* border: 0; */
}
.search_word {
  margin: 25px auto;
  width: 200px;
  display: inline-block;
}
</style>
