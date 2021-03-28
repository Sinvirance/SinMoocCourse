<template>
  <main role="main">
    <section class="jumbotron text-center">
      <div class="container">
        <h1>在线视频课程平台</h1>
        <p class="lead text-muted m-3">
          知识付费时代刚刚起步，在这个领域有很多的发展机会。整个课程以实战为基础，手把手从零开始，一步一步搭建一个完整的企业级开发架构。不讲废话，只讲干货。
        </p>
        <p>
          <a href="#" class="btn btn-primary my-2 p-3 font-weight-bold">点击进入所有课程</a>
        </p>
      </div>
    </section>

    <div class="album py-5 bg-light">
      <div class="container">
        <div class="title1">最新上线</div>
        <div class="row">
          <div v-for="o in news" class="col-md-4">
            <the-course v-bind:course="o"></the-course>
          </div>
        </div>

        <hr style="FILTER: alpha(opacity=100,finishopacity=0,style=2)" width="80%" color=#987cb9 SIZE=10>
        <div class="title2">近期热门</div>
        <div class="row">
          <div v-for="h in hots" class="col-md-4">
            <the-course v-bind:course="h"></the-course>
          </div>
        </div>


      </div>
    </div>
  </main>
</template>

<script>
import TheCourse from "../components/the-course.vue";
export default {
  name: "index",
  components:{TheCourse},
  data: function () {
    return {
      news: [],
      hots: [],
    }
  },

  mounted() {
    let _this = this;
    _this.listNew();
    _this.listRecommend();
  },

  methods: {

    /**
     * 查询新上好课
     */
    listNew() {
      let _this = this;
      _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/web/course/list-new').then((response)=>{
        console.log("查询新上好课结果：", response);
        let resp = response.data;
        if (resp.success) {
          _this.news = resp.content;
        }
      }).catch((response)=>{
        console.log("error：", response);
      })
    },
    listRecommend() {
      let _this = this;
      _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/web/course/list-hot').then((response)=>{
        console.log("查询近期热门结果：", response);
        let resp = response.data;
        if (resp.success) {
          _this.hots = resp.content;
        }
      }).catch((response)=>{
        console.log("error：", response);
      })
    },
  }

}
</script>

<!-- scoped:只应用当前组件，防止相互污染 -->
<style scoped>
.title1{
  margin-bottom: 2rem;
  color: #fafafa;
  letter-spacing: 0;
  text-shadow: 0px 1px 0px #999, 0px 2px 0px #888, 0px 3px 0px #777, 0px 4px 0px #666, 0px 5px 0px #555, 0px 6px 0px #444, 0px 7px 0px #333, 0px 8px 7px #001135;
  font-size: 2rem;
}
.title2{
  margin-bottom: 2rem;
  color: transparent;
  -webkit-text-stroke: 1px black;
  letter-spacing: 0.04em;
  font-size: 2rem;
}

</style>