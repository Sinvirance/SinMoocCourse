<template>
  <div>
    <!--新增讲师和刷新按钮-->
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

    <!--讲师数据分页显示列表-->
    <div class="row">
      <div v-for="teacher in teachers" class="col-md-3 center">
        <div>
          <span class="profile-picture">
            <img v-show="!teacher.image" class="editable img-responsive editable-click editable-empty" src="/ace/assets/images/avatars/profile-pic.jpg" v-bind:title="teacher.intro"/>
            <img v-show="teacher.image" class="media-object" v-bind:src="teacher.image" v-bind:title="teacher.intro"/>
          </span>

          <div class="space-4"></div>

          <div class="width-85 label label-info label-xlg arrowed-in arrowed-in-right">
            <div class="inline position-relative">
              <a href="javascript:;" class="user-title-label dropdown-toggle" data-toggle="dropdown">
                <i class="ace-icon fa fa-circle light-green"></i>
                &nbsp;
                <span class="white">{{teacher.position}}</span>
              </a>
            </div>
          </div>
        </div>

        <div class="space-6"></div>

        <a href="javascript:;" class="text-info bigger-110" v-bind:title="teacher.motto">
          <i class="ace-icon fa fa-user"></i>
          {{teacher.name}}【{{teacher.nickname}}】
        </a>

        <div class="space-6"></div>

        <div class="profile-social-links align-center">
          <button v-on:click="edit(teacher)" class="btn btn-xs btn-info">
            <i class="ace-icon fa fa-pencil bigger-120"></i>
          </button>
          &nbsp;
          <button v-on:click="del(teacher.id)" class="btn btn-xs btn-danger">
            <i class="ace-icon fa fa-trash-o bigger-120"></i>
          </button>
        </div>

        <div class="hr hr16 dotted"></div>

      </div>
    </div>

    <!-- 分页组件显示 -->
    <pagination ref="pagination" v-bind:list="list"/>

    <!--新增或编辑讲师功能表单-->
    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">讲师编辑</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-md-2 control-label">姓名</label>
                <div class="col-sm-9">
                  <input v-model="teacher.name" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">昵称</label>
                <div class="col-sm-9">
                  <input v-model="teacher.nickname" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">头像</label>
                <div class="col-sm-9">
                  <input type="file" v-on:change="uploadImage()" id="file-upload-input">
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">职位</label>
                <div class="col-sm-9">
                  <input v-model="teacher.position" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">座右铭</label>
                <div class="col-sm-9">
                  <input v-model="teacher.motto" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">简介</label>
                <div class="col-sm-9">
                  <textarea v-model="teacher.intro" class="form-control" rows="5"></textarea>
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
    name: "business-teacher",
    data: function() {
      return {
        teacher: {},
        teachers: [],
      }
    },

    mounted: function() {
      let _this = this;
      _this.$refs.pagination.size = 5;
      _this.list(1);
    },

    methods: {

      /**
       * 添加讲师功能,点击弹出表单
       */
      add() {
        let _this = this;
        _this.teacher = {};
        $("#form-modal").modal("show");
      },

      /**
       * 显示指定页码的讲师列表
       * @param page 前端传入查询的页码数
       */
      list(page) {
        let _this = this;
        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/teacher/list",{
          page: page,
          size: _this.$refs.pagination.size,
        }).then((response)=>{
          Loading.hide();
          let resp = response.data;
          _this.teachers = resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);
        })
      },

      /**
       * 表单添加讲师数据后保存
       */
      save() {
        let _this = this

        /* 保存时校验 */
        if (1 != 1
          || !Validator.require(_this.teacher.name, "姓名")
          || !Validator.length(_this.teacher.name, "姓名", 1, 50)
          || !Validator.length(_this.teacher.nickname, "昵称", 1, 50)
          || !Validator.length(_this.teacher.image, "头像", 1, 100)
          || !Validator.length(_this.teacher.position, "职位", 1, 50)
          || !Validator.length(_this.teacher.motto, "座右铭", 1, 50)
          || !Validator.length(_this.teacher.intro, "简介", 1, 500)
        ) {
          return;
        }

        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/teacher/save", _this.teacher).then((response)=>{
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
       * 表单修改已有讲师数据
       * @param teacher
       */
      edit(teacher) {
        let _this = this;
        _this.teacher = $.extend({},teacher);
        $("#form-modal").modal("show");
      },

      /**
       * 根据讲师id删除对应数据
       * @param id
       */
      del(id) {
        let _this = this;
        Confirm.show("删除讲师数据后不可恢复，确认删除?", function () {
          Loading.show();
          // 使用 restful 风格传递要删除的id
          _this.$ajax.delete(process.env.VUE_APP_SERVER + "/business/admin/teacher/delete/" + id).then((response) =>{
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
       * 表单方式上传头像图片
       */
      uploadImage() {
        let _this = this;
        /* FormData 接口提供了一种表示表单数据的键值对 key/value 的构造方式 */
        let formData = new window.FormData;
        /* FormData 接口的append() 方法 会添加一个新值到 FormData 对象内的一个已存在的键中，如果键不存在则会添加该键。当该键有值时，进行追加而不是覆盖 */
        /* key：files 必须与后端名称一致*/
        formData.append("file", document.querySelector("#file-upload-input").files[0]);
        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/file/admin/upload", formData).then((response) =>{
          Loading.show();
          let resp = response.data;
        });
      },

    }
  }
</script>