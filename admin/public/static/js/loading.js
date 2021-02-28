Loading = {
  show: function () {
    $.blockUI({
      message: "<img src='/static/image/loading.gif'>",
      css: {
        // 解决上传分片文件进度条被遮盖问题
        zIndex: "10011",
        padding: "10px",
        left: "50%",
        width: "80px",
        marginLeft: "-40px",
      }
    });
  },

  hide: function () {
    $.unblockUI();
  }
};