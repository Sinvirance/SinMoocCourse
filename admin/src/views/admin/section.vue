<template>
  <div>
    <h4 class="lighter">
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/course" class="pink"> {{course.name}} </router-link>：
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/chapter" class="pink"> {{chapter.name}} </router-link>
    </h4>
    <hr>
    <!--新增小节和刷新按钮-->
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

    <!--小节数据分页显示列表-->
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>小节Id</th>
        <th>标题</th>
        <th>视频</th>
        <th>时长</th>
        <th>收费</th>
        <th>顺序</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="section in sections">
        <td>{{section.id}}</td>
        <td>{{section.title}}</td>
        <td>{{section.video}}</td>
        <td>{{section.time | formatSecond}}</td>
        <td>{{SECTION_CHARGE | optionKV(section.charge)}}</td>
        <td>{{section.sort}}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button v-on:click="edit(section)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>
            <button v-on:click="del(section.id)" class="btn btn-xs btn-danger">
              <i class="ace-icon fa fa-trash-o bigger-120"></i>
            </button>
          </div>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- 分页组件显示 -->
    <pagination ref="pagination" v-bind:list="list"/>

    <!--新增或编辑小节功能表单-->
    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog " role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" >小节编辑</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-md-2 control-label">标题</label>
                <div class="col-sm-9">
                  <input v-model="section.title" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">课程</label>
                <div class="col-sm-9">
                  <p class="form-control-static">{{course.name}}</p>
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">大章</label>
                <div class="col-sm-9">
                  <p class="form-control-static">{{chapter.name}}</p>
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">视频</label>
                <div class="col-sm-9">
                  <file v-bind:input-id="'video-upload'"
                        v-bind:text="'上传视频'"
                        v-bind:suffixs="['mp4']"
                        v-bind:use="FILE_USE.COURSE.key"
                        v-bind:after-upload="afterUpload"></file>
                  <div v-show="section.video" class="row">
                    <div class="col-md-9">
                      <video v-bind:src="section.video" id="video" controls="controls"></video>
                    </div>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">时长</label>
                <div class="col-sm-9">
                  <input v-model="section.time" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">收费</label>
                <div class="col-sm-9">
                  <!-- 选择收费与否下拉框 -->
                  <select v-model="section.charge" class="form-control">
                    <option v-for="o in SECTION_CHARGE" v-bind:value="o.key">{{o.value}}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">顺序</label>
                <div class="col-sm-9">
                  <input v-model="section.sort" class="form-control">
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
  import File from "../../components/file";

  export default {
    components: {Pagination,File},
    name: "business-section",
    data: function() {
      return {
        section: {},
        sections: [],
        // 前端枚举字段
        SECTION_CHARGE: SECTION_CHARGE,
        FILE_USE: FILE_USE,
        course: {},
        chapter: {},}
    },

    mounted: function() {
      let _this = this;
      _this.$refs.pagination.size = 5;
      let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
      let chapter = SessionStorage.get(SESSION_KEY_CHAPTER) || {};
      if (Tool.isEmpty(chapter) || Tool.isEmpty(course)) {
        _this.$router.push("/welcome")
      }
      _this.course = course;
      _this.chapter = chapter;
      _this.list(1)

      this.$parent.activeSidebar("business-course-sidebar");
    },

    methods: {

      /**
       * 添加小节功能,点击弹出表单
       */
      add() {
        let _this = this;
        _this.section = {};
        $("#form-modal").modal("show");
      },

      /**
       * 显示指定页码的小节列表
       * @param page 前端传入查询的页码数
       */
      list(page) {
        let _this = this;
        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/section/list",{
          page: page,
          size: _this.$refs.pagination.size,
          courseId: _this.course.id,
          chapterId: _this.chapter.id,
        }).then((response)=>{
          Loading.hide();
          let resp = response.data
          _this.sections = resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);
        })
      },

      /**
       * 表单添加小节数据后保存
       */
      save() {
        let _this = this

        /* 保存时校验 */
        if (1 != 1
          || !Validator.require(_this.section.title, "标题")
          || !Validator.length(_this.section.title, "标题", 1, 50)
          || !Validator.length(_this.section.video, "视频", 1, 200)
        ) {
          return;
        }

        _this.section.courseId = _this.course.id;
        _this.section.chapterId = _this.chapter.id;

        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/section/save", _this.section).then((response)=>{
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
       * 表单修改已有小节数据
       * @param section
       */
      edit(section) {
        let _this = this;
        _this.section = $.extend({},section);
        $("#form-modal").modal("show");
      },

      /**
       * 点击删除按钮根据小节id删除对应数据
       * @param id
       */
      del(id) {
        let _this = this;
        Confirm.show("删除小节数据后不可恢复，确认删除?", function () {
          Loading.show();
          // 使用 restful 风格传递要删除的id
          _this.$ajax.delete(process.env.VUE_APP_SERVER + "/business/admin/section/delete/" + id).then((response) =>{
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
       * 上传小节视频
       * @param resp
       */
      afterUpload(resp) {
        let _this = this;
        let video = resp.content.path;
        _this.section.video = video;
        _this.getTime();
      },


      /**
       * 获取时长
       */
      getTime() {
        let _this = this;
        let ele = document.getElementById("video");
        _this.section.time = parseInt(ele.duration, 10);
      },
    }
  }
</script>

<style scoped>
  video {
    width: 100%;
    height: auto;
    margin-top: 10px;
  }
</style>