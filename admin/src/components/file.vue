<template>
  <div>
    <button type="button" v-on:click="selectFile()" className="btn btn-white btn-default btn-round">
      <i class="ace-icon fa fa-upload"></i>
      {{text}}
    </button>
    <input class="hidden" type="file" ref="file" v-on:change="uploadFile()" v-bind:id="inputId+'-input'">
  </div>
</template>

<script>
export default {
  name: 'file',
  props: {
    text: {
      default: "上传文件"
    },
    inputId: {
      default: "file-upload"
    },
    suffixs: {
      default: []
    },
    use: {
      default: ""
    },
    afterUpload: {
      type: Function,
      default: null
    },
  },
  data: function () {
    return {}
  },
  methods: {
    uploadFile() {
      let _this = this;
      /* FormData 接口提供了一种表示表单数据的键值对 key/value 的构造方式 */
      let formData = new window.FormData;
      /* _this.$refs调用Dom节点元素值 */
      let file = _this.$refs.file.files[0];

      /* 判断文件格式 */
      let suffixs = _this.suffixs;
      let fileName = file.name;
      let suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length).toLowerCase();
      let validateSuffix = false;
      for (let i = 0; i < suffixs.length; i++) {
        if (suffixs[i].toLowerCase() === suffix) {
          validateSuffix = true;
          break;
        }
      }
      if (!validateSuffix) {
        Toast.warning("文件格式不正确！只支持上传：" + suffixs.join(" "));
        $("#" + _this.inputId + "-input").val("");
        return;
      }

      /* FormData 接口的append() 方法 会添加一个新值到 FormData 对象内的一个已存在的键中，如果键不存在则会添加该键。当该键有值时，进行追加而不是覆盖 */
      /* key：files 必须与后端名称一致*/
      formData.append("file", file);
      /* 将枚举类型传递给后端 */
      formData.append("use", _this.use)
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/file/admin/upload", formData).then((response) =>{
        Loading.hide();
        let resp = response.data;
        console.log("文件上传成功：", resp);
        _this.afterUpload(resp);
        $("#" + _this.inputId + "-input").val("");
      });
    },

    selectFile() {
      let _this = this;
      $("#" + _this.inputId + "-input").trigger("click");
    }
  }
}
</script>
