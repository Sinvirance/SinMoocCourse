<template>
  <div>
    <h1>您好，欢迎进入控台</h1>
  </div>
</template>

<script>
export default {
  name: "welcome",
  // 在模板渲染成html后调用，通常是初始化页面完成后，再对html的dom节点进行一些需要的操作。
  mounted: function() {
    // $parent 调用父组件admin的方法
    // this.$parent.activeSidebar("welcome-sidebar");
  },
  methods: {
  }
}
</script>