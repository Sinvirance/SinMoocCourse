<template>
  <div>
    <!--新增课程和刷新按钮-->
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

    <div class="row">
      <div v-for="course in courses" class="col-md-2">
        <div class="thumbnail search-thumbnail">
          <img v-show="!course.image" class="" src="/static/image/课程封面/demo-course.jpg" />
          <img v-show="course.image" class="media-object" v-bind:src="course.image" />
          <div class="caption">
            <div class="clearfix">
              <span class="pull-right label label-primary info-label">
                {{COURSE_LEVEL | optionKV(course.level)}}
              </span>
              <span class="pull-right label label-primary info-label">
                {{COURSE_CHARGE | optionKV(course.charge)}}
              </span>
              <span class="pull-right label label-primary info-label">
                {{COURSE_STATUS | optionKV(course.status)}}
              </span>
            </div>

            <h3 class="search-title">
              <a href="#" class="blue">{{course.name}}</a>
            </h3>

            <!-- 讲师模块 -->
            <div v-for="teacher in teachers.filter(t=>{return t.id===course.teacherId})" class="profile-activity clearfix">
              <div>
                <img v-show="!teacher.image" class="pull-left" src="/ace/assets/images/avatars/avatar5.png">
                <img v-show="teacher.image" class="pull-left" v-bind:src="teacher.image">
                <a class="user" href="#"> {{teacher.name}} </a>
                <br>
                {{teacher.position}}
              </div>
            </div>
            <p>
              <span class="blue bolder bigger-150">{{course.price}}&nbsp;<i class="fa fa-rmb"></i></span>&nbsp;
            </p>
            <p class="preview">{{course.summary}}</p>
            <p>
              <span class="badge badge-info">{{course.id}}</span>
              <span class="badge badge-info">排序：{{course.sort}}</span>
              <span class="badge badge-info">{{course.time | formatSecond}}</span>
            </p>
            <p>
              <button v-on:click="toChapter(course)" class="btn btn-white btn-xs btn-info btn-round">
                大章
              </button>&nbsp;
              <button v-on:click="toContent(course)" class="btn btn-white btn-xs btn-info btn-round">
                内容
              </button>&nbsp;
              <button v-on:click="openSortModal(course)" class="btn btn-white btn-xs btn-info btn-round">
                排序
              </button>&nbsp;
              <button v-on:click="edit(course)" class="btn btn-white btn-xs btn-info btn-round">
                编辑
              </button>&nbsp;
              <button v-on:click="del(course.id)" class="btn btn-white btn-xs btn-warning btn-round">
                删除
              </button>
            </p>
          </div>
        </div>
      </div>
    </div>

    <!-- 分页组件显示 -->
    <pagination ref="pagination" v-bind:list="list"/>

    <!--新增或编辑课程功能表单-->
    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">课程编辑</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-md-2 control-label">分类</label>
                <div class="col-sm-9">
                  <ul id="tree" class="ztree"></ul>
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">名称</label>
                <div class="col-sm-9">
                  <input v-model="course.name" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">讲师</label>
                <div class="col-sm-9">
                  <select v-model="course.teacherId" class="form-control">
                    <option v-for="o in teachers" v-bind:value="o.id">{{o.name}}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">封面</label>
                <div class="col-sm-9">
                  <big-file v-bind:input-id="'image-upload'"
                        v-bind:text="'上传封面'"
                        v-bind:suffixs="['jpg', 'jpeg', 'png']"
                        v-bind:use="FILE_USE.COURSE.key"
                        v-bind:after-upload="afterUpload"></big-file>
                  <div v-show="course.image" class="row">
                    <div class="col-md-6">
                      <img v-bind:src="course.image" class="img-responsive">
                    </div>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">概述</label>
                <div class="col-sm-9">
                  <textarea v-model="course.summary" class="form-control"/>
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">时长</label>
                <div class="col-sm-9">
                  <input v-model="course.time" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">价格(元)</label>
                <div class="col-sm-9">
                  <input v-model="course.price" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">级别</label>
                <div class="col-sm-9">
                  <select v-model="course.level" class="form-control">
                    <option v-for="o in COURSE_LEVEL" v-bind:value="o.key">{{o.value}}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">收费</label>
                <div class="col-sm-9">
                  <select v-model="course.charge" class="form-control">
                    <option v-for="o in COURSE_CHARGE" v-bind:value="o.key">{{o.value}}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">状态</label>
                <div class="col-sm-9">
                  <select v-model="course.status" class="form-control">
                    <option v-for="o in COURSE_STATUS" v-bind:value="o.key">{{o.value}}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">报名数</label>
                <div class="col-sm-9">
                  <input v-model="course.enroll" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">顺序</label>
                <div class="col-sm-9">
                  <input v-model="course.sort" class="form-control" disabled>
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

    <div id="course-sort-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">排序</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="control-label col-lg-3">
                  当前排序
                </label>
                <div class="col-lg-9">
                  <input class="form-control" v-model="sort.oldSort" name="oldSort" disabled>
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-lg-3">
                  新排序
                </label>
                <div class="col-lg-9">
                  <input class="form-control" v-model="sort.newSort" name="newSort">
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
              <i class="ace-icon fa fa-times"></i>
              取消
            </button>
            <button type="button" class="btn btn-white btn-info btn-round" v-on:click="updateSort()">
              <i class="ace-icon fa fa-plus blue"></i>
              更新排序
            </button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
  </div>
</template>

<script>
  import Pagination from "../../components/pagination";
  import BigFile from "../../components/big-file";

  export default {
    components: {Pagination, BigFile},
    name: "business-course",
    data: function() {
      return {
        course: {},
        courses: [],
        COURSE_LEVEL: COURSE_LEVEL,
        COURSE_CHARGE: COURSE_CHARGE,
        COURSE_STATUS: COURSE_STATUS,
        FILE_USE: FILE_USE,
        categorys: [],
        teachers: [],
        tree: {},
        saveContentLabel: "",
        sort: {
          id: "",
          oldSort: 0,
          newSort: 0
        },
      }
    },

    mounted: function() {
      let _this = this;
      _this.$refs.pagination.size = 5;
      _this.allCategory();
      _this.allTeacher();
      _this.list(1);
    },

    methods: {

      /**
       * 添加课程功能,点击弹出表单
       */
      add() {
        let _this = this;
        _this.course =  {
          sort: _this.$refs.pagination.total + 1
        };

        /* 点击新增时, 所有节点都不选中 */
        _this.tree.checkAllNodes(false);
        $("#form-modal").modal("show");
      },

      /**
       * 显示指定页码的课程列表
       * @param page 前端传入查询的页码数
       */
      list(page) {
        let _this = this;
        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/course/list",{
          page: page,
          size: _this.$refs.pagination.size,
        }).then((response)=>{
          Loading.hide();
          let resp = response.data
					if (resp.success) {
						_this.courses = resp.content.list;
						_this.$refs.pagination.render(page, resp.content.total);
					}
        })
      },

      /**
       * 表单添加课程数据后保存
       */
      save() {
        let _this = this;
        _this.tree.expandAll(false);
        /* 保存时校验 */
        if (1 != 1
          || !Validator.require(_this.course.name, "名称")
          || !Validator.length(_this.course.name, "名称", 1, 50)
          || !Validator.length(_this.course.summary, "概述", 1, 2000)
          || !Validator.length(_this.course.image, "封面", 1, 100)
        ) {
          return;
        }

        /* 获取选中的分类数组 */
        let categorys = _this.tree.getCheckedNodes();
        if (Tool.isEmpty(categorys)) {
          Toast.warning("请选择分类！");
          return;
        }
        console.log(categorys);
        _this.course.categorys = categorys;

        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/course/save", _this.course).then((response)=>{
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
       * 表单修改已有课程数据
       * @param course
       */
      edit(course) {
        let _this = this;
        _this.course = $.extend({},course);
        _this.listCategory(course.id);
        $("#form-modal").modal("show");
      },

      /**
       * 根据课程id删除对应数据
       * @param id
       */
      del(id) {
        let _this = this;
        Confirm.show("删除课程数据后不可恢复，确认删除?", function () {
          Loading.show();
          // 使用 restful 风格传递要删除的id
          _this.$ajax.delete(process.env.VUE_APP_SERVER + "/business/admin/course/delete/" + id).then((response) =>{
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
       * 点击大章节跳转
       * @param course
       */
      toChapter(course) {
        let _this = this;
        SessionStorage.set(SESSION_KEY_COURSE, course);
        _this.$router.push("/business/chapter");
      },

      /**
       * 添加课程时分类展示
       */
      allCategory() {
        let _this = this;
        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/category/all').then((response)=>{
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
						_this.categorys = resp.content;
						_this.initTree();
					}
        })
      },

      /**
       * 增加zTree插件
       */
      initTree() {
        let _this = this;
        let setting = {
          check: {
            enable: true
          },
          data: {
            simpleData: {
              /* 将分类对象字段和zTree 展示字段关联 */
              idKey: "id",
              pIdKey: "parent",
              rootPId: "00000000",
              enable: true,
            }
          }
        };

        let zNodes = _this.categorys;

        _this.tree = $.fn.zTree.init($("#tree"), setting, zNodes);
        // 展开所有的节点
        // _this.tree.expandAll(true);
      },

      /**
       * 查找课程下所有分类
       * @param courseId
       */
      listCategory(courseId) {
        let _this = this;
        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/list-category/' + courseId).then((res)=>{
          Loading.hide();
          console.log("查找课程下所有分类结果：", res);
          let response = res.data;
          let categorys = response.content;

          // 勾选查询到的分类
          _this.tree.checkAllNodes(false);
          for (let i = 0; i < categorys.length; i++) {
            let node = _this.tree.getNodeByParam("id", categorys[i].categoryId);
            _this.tree.checkNode(node, true);
          }
        })
      },

      /**
       * 手动点击保存按钮
       */
      saveContent () {
        let _this = this;
        let content = $("#content").summernote("code");
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/save-content', {
          id: _this.course.id,
          content: content
        }).then((response)=>{
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
            Toast.success("内容保存成功");
            let now = Tool.dateFormat("mm:ss");
            _this.saveContentLabel = now;
          } else {
            Toast.warning(resp.message);
          }
        });
      },

      /**
       * 自动保存
       */
      saveTime() {
        let _this = this;
        let content = $("#content").summernote("code");
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/save-content', {
          id: _this.course.id,
          content: content
        }).then((response)=>{
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
            // Toast.success("内容保存成功");
            let now = Tool.dateFormat("yyyy-MM-dd hh:mm:ss");
            //let now = Tool.dateFormat("mm:ss");
            _this.saveContentLabel = now;
          } else {
            Toast.warning(resp.message);
          }
        });
      },

      /**
       * 打开排序编辑模态框
       * @param course
       */
      openSortModal(course) {
        let _this = this;
        _this.sort = {
          id: course.id,
          oldSort: course.sort,
          newSort: course.sort
        };
        $("#course-sort-modal").modal("show");
      },


      updateSort() {
        let _this = this;
        if (_this.sort.newSort === _this.sort.oldSort) {
          Toast.warning("排序没有变化");
          return;
        }
        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/course/sort", _this.sort).then((res) => {
          let response = res.data;

          if (response.success) {
            Toast.success("更新排序成功");
            $("#course-sort-modal").modal("hide");
            _this.list(1);
          } else {
            Toast.error("更新排序失败");
          }
        });
      },

      /**
       * 教师列表
       */
      allTeacher() {
        let _this = this;
        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/teacher/all').then((response)=>{
          Loading.hide();
          let resp = response.data;
          _this.teachers = resp.content;
        })
      },

      /**
       * 表单方式上传封面图片
       */
      afterUpload(resp) {
        let _this = this;
        let image = resp.content.path;
        _this.course.image = image;
				// 解决不能实时预览的问题
				_this.$forceUpdate();
      },


      /**
       * 点击【内容】
       */
      toContent(course) {
        let _this = this;
        SessionStorage.set(SESSION_KEY_COURSE, course);
        _this.$router.push("/business/content");
      },

    }
  }
</script>

<!-- scoped:只应用当前组件，防止相互污染 -->
<style scoped>
.caption h3 {
  font-size: 20px;
}
/* 设置summary只显示两行剩下的省略号代替*/
.preview {
  text-overflow:ellipsis;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

@media (max-width: 1199px) {
  .caption h3 {
    font-size: 16px;
  }
}
</style>