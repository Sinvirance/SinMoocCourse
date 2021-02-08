<template>
  <div>
    <!--新增文件和刷新按钮-->
    <p>
      <button v-on:click="add()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>
      &nbsp;
      <!--点击触发事件查询list()-->
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh "></i>
        刷新
      </button>
    </p>

    <!--文件数据分页显示列表-->
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>id</th>
        <th>相对路径</th>
        <th>文件名</th>
        <th>后缀</th>
        <th>大小</th>
        <th>用途</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="file in files">
        <td>{{file.id}}</td>
        <td>{{file.path}}</td>
        <td>{{file.name}}</td>
        <td>{{file.suffix}}</td>
        <td>{{file.size}}</td>
        <td>{{FILE_USE | optionKV(file.use)}}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button v-on:click="edit(file)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>
            <button v-on:click="del(file.id)" class="btn btn-xs btn-danger">
              <i class="ace-icon fa fa-trash-o bigger-120"></i>
            </button>
          </div>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- 分页组件显示 -->
    <pagination ref="pagination" v-bind:list="list"/>

    <!--新增或编辑文件功能表单-->
    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">文件编辑</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-md-2 control-label">相对路径</label>
                <div class="col-sm-9">
                  <input v-model="file.path" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">文件名</label>
                <div class="col-sm-9">
                  <input v-model="file.name" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">后缀</label>
                <div class="col-sm-9">
                  <input v-model="file.suffix" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">大小</label>
                <div class="col-sm-9">
                  <input v-model="file.size" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">用途</label>
                <div class="col-sm-9">
                  <select v-model="file.use" class="form-control">
                    <option v-for="o in FILE_USE" v-bind:value="o.key">{{o.value}}</option>
                  </select>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Pagination from "../../components/pagination";

  export default {
    components: {Pagination},
    name: "file-file",
    data: function() {
      return {
        file: {},
        files: [],
        FILE_USE: FILE_USE,
      }
    },

    mounted: function() {
      let _this = this;
      _this.$refs.pagination.size = 5;
      _this.list(1);
    },

    methods: {

      /**
       * 添加文件功能,点击弹出表单
       */
      add() {
        let _this = this;
        _this.file = {};
        $("#form-modal").modal("show");
      },

      /**
       * 显示指定页码的文件列表
       * @param page 前端传入查询的页码数
       */
      list(page) {
        let _this = this;
        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/file/admin/file/list",{
          page: page,
          size: _this.$refs.pagination.size,
        }).then((response)=>{
          Loading.hide();
          let resp = response.data
          _this.files = resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);
        })
      },

      /**
       * 表单添加文件数据后保存
       */
      save() {
        let _this = this

        /* 保存时校验 */
        if (1 != 1
          || !Validator.require(_this.file.path, "相对路径")
          || !Validator.length(_this.file.path, "相对路径", 1, 100)
          || !Validator.length(_this.file.name, "文件名", 1, 100)
          || !Validator.length(_this.file.suffix, "后缀", 1, 10)
        ) {
          return;
        }

        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/file/admin/file/save", _this.file).then((response)=>{
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
       * 表单修改已有文件数据
       * @param file
       */
      edit(file) {
        let _this = this;
        _this.file = $.extend({},file);
        $("#form-modal").modal("show");
      },

      /**
       * 根据文件id删除对应数据
       * @param id
       */
      del(id) {
        let _this = this;
        Confirm.show("删除文件数据后不可恢复，确认删除?", function () {
          Loading.show();
          // 使用 restful 风格传递要删除的id
          _this.$ajax.delete(process.env.VUE_APP_SERVER + "/file/admin/file/delete/" + id).then((response) =>{
            Loading.hide();
            let resp = response.data;
            if (resp.success) {
              _this.list(1);
              Toast.success("删除成功！");
            }
          })
        })
      }
    }
  }
</script>