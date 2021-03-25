<template>
  <footer class="texfot-muted">
    <div class="container">
      <p>SinMooc 在线视频课程系统，程序员的梦工厂，致力于全栈技术推广</p>
      <div class="footer-link">
        <a href="#" title="回到顶部">回到顶部</a>
        <a href="/about/us" target="_blank" title="关于我们">关于我们</a>
        <a href="/about/contact" target="_blank" title="联系我们">联系我们</a>
        <a href="/about/recruit" target="_blank" title="讲师招募">讲师招募</a>
        <a href="/help" target="_blank" title="帮助中心">帮助中心</a>
        <a href="/user/feedback" target="_blank" title="意见反馈">意见反馈</a>
        <a href="/about/friendly" target="_blank" title="友情链接">友情链接</a>
      </div>
      <div class="footer-copyright">
        <p>Copyright&nbsp;<img src="" draggable="false" alt="©">
          &nbsp;2021&nbsp;sinmooc.com&nbsp;&nbsp;
          <a rel="nofollow" href="https://beian.miit.gov.cn/" style="color: rgb(90, 107, 123);font-size:13px;" target="_blank">京ICP备 xxxxxxxxx号-xx</a>
          <a href="http://www.beian.gov.cn/portal/index.do"
             rel="nofollow" target="_blank"
             style="color: rgb(90, 107, 123);font-size:13px;margin:0 5px !important;">
            <i class="beian"></i>京公网安备xxxxxxxxxxxxxxxx号
          </a>
        </p>
      </div>

    </div>
  </footer>
</template>

<script>
  export default {
    name: 'TheFooter',
  }
</script>

<style scoped>
  .footer-link a {
   display: inline-block;
   font-size: 15px;
   line-height: 28px;
   color: #545C63;
   margin-right: 24px;
   font-weight: 400;
 }
  p {
    color: #545C63;
  }
</style>