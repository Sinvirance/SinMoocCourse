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
  name: 'big-file',
  props: {
    text: {
      default: "上传大文件"
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

      /* 对于大文件进行分片 */
      /* 每个分片大小 */
      let shardSize = 20 * 1024 * 1024;
      /* 分片初始索引 */
      let shardIndex = 0;
      /* 当前分片内存中起始位置 */
      let start = shardIndex * shardSize;
      /* 当前分片内存结束位置 */
      let end = Math.min(file.size, start + shardSize);
      /* file.slice: 截取文件 */
      let fileShard = file.slice(start, end);
      /* 文件大小 */
      let size = file.size;
      /* 分片页数 */
      let shardTotal = Math.ceil(size / shardSize); //总片数


      /* FormData 接口的append() 方法 会添加一个新值到 FormData 对象内的一个已存在的键中，如果键不存在则会添加该键。当该键有值时，进行追加而不是覆盖 */
      /* key：files 必须与后端名称一致*/
      formData.append("shard", fileShard);
      /* 将枚举类型传递给后端 */
      formData.append("use", _this.use)
      formData.append('shard', fileShard);
      formData.append('shardIndex', shardIndex);
      formData.append('shardSize', shardSize);
      formData.append('shardTotal', shardTotal);
      formData.append('name', file.name);
      formData.append('suffix', suffix);
      formData.append('size', size);
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
