<template>
  <div>
    <!--新增资源和刷新按钮-->
    <p>
      <!--点击触发事件查询list()-->
      <button v-on:click="list()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh "></i>
        刷新
      </button>
    </p>

    <!-- 用于json格式资源管理提交文本框 -->
    <div class="row">
      <div class="col-md-6">
        <textarea id="resource-textarea" class="form-control" v-model="resourceStr" name="resource" rows="10"></textarea>

        <br>
        <button id="save-btn" type="button" class="btn btn-primary" v-on:click="save()">
          保存
        </button>
      </div>
      <div class="col-md-6">
        <ul id="tree" class="ztree"></ul>
      </div>
    </div>
    <hr>

    <!--资源数据分页显示列表-->
    <!-- 分页组件显示 -->
    <!--资源不需要编辑资源功能表单-->
  </div>
</template>

<script>

  export default {
    name: "system-resource",
    data: function() {
      return {
        resource: {},
        resources: [],
        resourceStr: "",
        tree: {},
      }
    },

    mounted: function() {
      let _this = this;
      _this.list();
    },

    methods: {

      /**
       * 添加资源功能,点击弹出表单
       */

      /**
       * 显示资源树
       */
      list() {
        let _this = this;
        Loading.show();
        _this.$ajax.get(process.env.VUE_APP_SERVER + "/system/admin/resource/load-tree").then((response)=>{
          Loading.hide();
          let resp = response.data
          _this.resources = resp.content;
          /* 初始化资源树 */
          _this.initTree();
        })
      },

      /**
       * 表单添加资源数据后保存
       */
      save() {
        let _this = this

        /* 保存时校验 */
        if (Tool.isEmpty(_this.resourceStr)) {
          Toast.warning("资源不能为空！");
          return;
        }
        let json = JSON.parse(_this.resourceStr);

        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/resource/save", json).then((response)=>{
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
            $("#form-modal").modal("hide");
            _this.list(1);
            Toast.success("保存成功！");
          } else {
            Toast.warning(resp.message)
          }
        })
      },


      /**
       * 根据资源id删除对应数据
       * @param id
       */
      del(id) {
        let _this = this;
        Confirm.show("删除资源数据后不可恢复，确认删除?", function () {
          Loading.show();
          // 使用 restful 风格传递要删除的id
          _this.$ajax.delete(process.env.VUE_APP_SERVER + "/system/admin/resource/delete/" + id).then((response) =>{
            Loading.hide();
            let resp = response.data;
            if (resp.success) {
              _this.list(1);
              Toast.success("删除成功！");
            }
          })
        })
      },


      /**
       * 初始资源树
       */
      initTree() {
        let _this = this;
        let setting = {
          data: {
            simpleData: {
              idKey: "id",
              pIdKey: "parent",
              rootPId: "",
              // enable: true
            }
          }
        };

        _this.zTree = $.fn.zTree.init($("#tree"), setting, _this.resources);
        _this.zTree.expandAll(true);
      },
    }
  }
</script>