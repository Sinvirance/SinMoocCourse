<template>
  <div>
    <h4 class="lighter">
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/course" class="pink"> {{course.name}} </router-link>
    </h4>
    <hr>
    <!--新增和刷新按钮-->
    <p>
      <router-link to="/business/course" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-arrow-left"></i>
        返回课程
      </router-link>
      &nbsp;
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

    <!--大章数据显示表格-->
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>Id</th>
        <th>名称</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="chapter in chapters">
        <td>{{chapter.id}}</td>
        <td>{{chapter.name}}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button v-on:click="toSection(chapter)" class="btn btn-white btn-xs btn-info btn-round">
              小节
            </button>&nbsp;
            <button v-on:click="edit(chapter)" class="btn btn-white btn-xs btn-info btn-round">
              编辑
            </button>&nbsp;
            <button v-on:click="del(chapter.id)" class="btn btn-white btn-xs btn-warning btn-round">
              删除
            </button>
          </div>
        </td>
      </tr>
      </tbody>
    </table>

    <!--显示分页组件,这里将会被渲染成dom-->
    <!--pagination组件暴露的组件名，ref: 给元素或子组件注册引用信息-->
    <!--v-bind: 用于绑绑定数据 -->
    <pagination ref="pagination" v-bind:list="list"/>

    <!--新增大章功能表单-->
    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">新增大章</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-md-2 control-label">名称</label>
                <div class="col-sm-9">
                  <input v-model="chapter.name" class="form-control" placeholder="名称">
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-2 control-label">课程</label>
                <div class="col-sm-9">
                  <p class="form-control-static">{{course.name}}</p>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
  </div>
</template>

<script>
  // 引入分页组件
  import Pagination from "../../components/pagination";
  export default {
    components: {Pagination},
    name: "chapter",
    // 为什么data属性需要写成函数并return
    // 不使用return包裹的数据会在项目的全局可见,会造成变量污染,使用return包裹后数据中变量只在当前组件中生效，不会影响其他组件
    data: function() {
      return {
        // object: {}
        chapter: {},
        // array: []
        chapters: [],
        course:{},
      }
    },
    // 在模板渲染成html后调用，通常是初始化页面完成后，再对html的dom节点进行一些需要的操作。
    mounted: function() {
      let _this = this;
      // 在点击大章管理后，显示的内容：第一页，5条页面数据
      _this.$refs.pagination.size = 5;
      // 从会话对象获取course对象
      let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
      if (Tool.isEmpty(course)) {
        _this.$router.push("/welcome");
      }
      _this.course = course;
      _this.list(1);
      // $parent 调用父组件admin的方法
      this.$parent.activeSidebar("business-course-sidebar");
    },

    methods: {

      /**
       * 添加大章功能,点击弹出表单
       */
      add() {
        let _this = this;
        // 不在新增中显示以往编辑的数据
        _this.chapter = {};
        $("#form-modal").modal("show");
      },

      /**
       * 显示指定页码的大章列表
       * @param page 前端传入查询的页码数
       */
      list(page) {
        let _this = this;
        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/chapter/list",{
          page: page,
          // 获取组件 pagination 里定义的size
          // $refs 获取通过 ref 注册的引用来获取数据
          size: _this.$refs.pagination.size,
          courseId: _this.course.id,
        }).then((response)=>{
          Loading.hide();
          // resp 就是后端的统一返回对象 ResponseDto
          let resp = response.data
          // 真实数据存储在响应对象的 data.list属性
					if (resp.success)  {
						_this.chapters = resp.content.list;
						// render：pagination组件定义的方法, 用于使用数据重新渲染页面
						_this.$refs.pagination.render(page, resp.content.total);
					}
        })
      },

      /**
       * 表单添加大章数据后保存
       */
      save() {
        let _this = this;
        // 保存时校验
        if (!Validator.require(_this.chapter.name, "名称")
        || !Validator.length(_this.chapter.courseId, "课程ID", 1, 8)) {
          return;
        }
        // 添加大章课程id不应该由用户来决定，而是根据会话来里的course来决定
        _this.chapter.courseId = _this.course.id;

        Loading.show();
        // 将上面点击事件传过来的 chapter 对象属性值，使用post请求传递后端
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/chapter/save", _this.chapter).then((response)=>{
          Loading.hide();
          // 当保存成功时，关闭表单并刷新
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
       * 表单修改已有大章数据
       * @param chapter
       */
      edit(chapter) {
        let _this = this;
        // 解决 Vue 数据双向绑定导致的修改数据直接在页面显示问题，如果未保存依然会显示
        // 使用一个空变量接收chapter对象
        _this.chapter = $.extend({},chapter);
        $("#form-modal").modal("show");
      },

      /**
       * 根据大章id删除对应数据
       * @param id
       */
      del(id) {
        let _this = this;
        // 使用弹出框对删除大章功能进行用户确认
        Confirm.show("删除大章数据后不可恢复，确认删除?", function () {
          Loading.show();
          // 使用 restful 风格传递要删除的id
          _this.$ajax.delete(process.env.VUE_APP_SERVER + "/business/admin/chapter/delete/" + id).then((response) =>{
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
       * 点击小节按钮跳到对应小节
       * @param chapter
       */
      toSection(chapter) {
        let _this = this;
        SessionStorage.set(SESSION_KEY_CHAPTER,chapter);
        _this.$router.push("/business/section")
      }
    }
  }
</script>