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

    /**
     * 文件上传
     */
    uploadFile() {
      let _this = this;
      /* _this.$refs调用Dom节点元素值 */
      let file = _this.$refs.file.files[0];

      console.log(file);
      // md5 是信息摘要算法，相同文件只会生成一个key
      // 生成文件标识，标识多次上传分片的是不是同一个文件
      /* 生成16进值的MD5 */
      let key = hex_md5(file);
      /* 用于缩短md5密钥的长度 */
      /* 转化为10进制的md5 */
      let key10 = parseInt(key, 16);
      /* 转化为62进制的md5 大小写字母52个+10个阿拉伯数字 */
      let key62 = Tool._10to62(key10);

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
      let shardSize = 10 * 1024 * 1024;
      /* 分片初始索引, 1 表示第一个分片 */
      let shardIndex = 1;
      /* 文件大小 */
      let size = file.size;
      /* 分片页数 */
      let shardTotal = Math.ceil(size / shardSize); //总片数

      let param = {
        'shardIndex': shardIndex,
        'shardSize': shardSize,
        'shardTotal': shardTotal,
        'use': _this.use,
        'name': file.name,
        'suffix': suffix,
        'size': file.size,
        'key': key62
      };

      _this.check(param);
    },


    /**
     * 检查文件状态，是否已上传过？传到第几个分片？
     */
    check (param) {
      let _this = this;
      _this.$ajax.get(process.env.VUE_APP_SERVER + '/file/admin/check/' + param.key).then((response)=>{
        let resp = response.data;
        if (resp.success) {
          let obj = resp.content;
          if (!obj) {
            param.shardIndex = 1;
            console.log("没有找到文件记录，从分片1开始上传");
            _this.upload(param);
          } else if (obj.shardIndex === obj.shardTotal) {
            Toast.success("文件极速秒传成功！");
            _this.afterUpload(resp);
            $("#" + _this.inputId + "-input").val("");
          }else {
            param.shardIndex = obj.shardIndex + 1;

            console.log("找到文件记录，从分片" + param.shardIndex + "开始上传");
            _this.upload(param);
          }
        } else {
          Toast.warning("文件上传失败");
          /* 将表单的inputId清空 */
          $("#" + _this.inputId + "-input").val("");
        }
      })
    },


    /**
     * 将文件数据转成Base64传输
     * @param param 文件各个参数
     */
    upload (param) {
      let _this = this;
      let shardIndex = param.shardIndex;
      let shardTotal = param.shardTotal;
      let shardSize = param.shardSize;
      let fileShard = _this.getFileShard(shardIndex, shardSize);
      /* 将文件转为base64进行传输 */
      let fileReader = new FileReader();

      Progress.show(parseInt(100 * (shardIndex - 1) / shardTotal));
      /* 文件上传监听事件 */
      fileReader.onload = function(e) {
        // target 事件属性可返回事件的目标节点（触发该事件的节点），如生成事件的元素、文档或窗口。
        /* 获得该分片文件的Base64文件 */
        let base64 = e.target.result;
        param.shard = base64;

        _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/upload', param).then((response) => {
          Loading.hide();
          let resp = response.data;
          console.log("上传文件成功：", resp);

          Progress.show(parseInt(100 * shardIndex / shardTotal));
          /* 判断文件分片是否上传完毕，否则继续上传 */
          if (shardIndex < shardTotal) {
            // 上传下一个分片
            param.shardIndex = param.shardIndex + 1;
            _this.upload(param);
          } else {
            Progress.hide();
            _this.afterUpload(resp);
            $("#" + _this.inputId + "-input").val("");
          }
        });
      };
      fileReader.readAsDataURL(fileShard);
    },


    getFileShard (shardIndex, shardSize) {
      let _this = this;
      let file = _this.$refs.file.files[0];
      let start = (shardIndex - 1) * shardSize;	//当前分片起始位置
      let end = Math.min(file.size, start + shardSize); //当前分片结束位置
      let fileShard = file.slice(start, end); //从文件中截取当前的分片数据
      return fileShard;
    },

    selectFile () {
      let _this = this;
      $("#" + _this.inputId + "-input").trigger("click");
    }
  }
}
</script>
