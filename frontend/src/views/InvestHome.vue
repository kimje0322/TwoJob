<template>
  <div>
    <div style="height: 60px; text-align: center; border-bottom: 1px solid gray;">Nav bar</div>
    <div class="investNav">
      <div class="item">
        <h5>투자홈</h5>
        <h5>오픈예정</h5>
        <h5>프로젝트</h5>
        <h5>투자오픈</h5>
      </div>
    </div>
    <div>
      <v-carousel>
        <v-carousel-item
          v-for="(item,i) in items"
          :key="i"
          :src="item.src"
          reverse-transition="fade-transition"
          transition="fade-transition"
        ></v-carousel-item>
      </v-carousel>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: [
          {
            src: 'https://cdn.vuetifyjs.com/images/carousel/squirrel.jpg',
          },
          {
            src: 'https://cdn.vuetifyjs.com/images/carousel/sky.jpg',
          },
          {
            src: 'https://cdn.vuetifyjs.com/images/carousel/bird.jpg',
          },
          {
            src: 'https://cdn.vuetifyjs.com/images/carousel/planet.jpg',
          },
        ],
    }
  }

}
</script>

<style scoped>
.investNav {
  height: 50px;
  text-align: center;
  line-height: 50px;
}
.investNav .item h5 {
  display: inline-block;
  margin: 0 10% 0 0;
}
</style>