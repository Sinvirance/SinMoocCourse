<template>
  <div>
    <div class="row">
      <div class="col-md-5">
        <!--新增分类和刷新按钮-->
        <p>
          <button v-on:click="add1()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-edit"></i>
            新增一级
          </button>
          &nbsp;
          <!--点击触发事件查询all()-->
          <button v-on:click="all()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-refresh "></i>
            刷新
          </button>
        </p>

        <!--分类数据显示列表-->
        <table id="level1-table" class="table  table-bordered table-hover">
          <thead>
          <tr>
            <th>id</th>
            <th>名称</th>
            <th>顺序</th>
            <th>操作</th>
          </tr>
          </thead>

          <tbody>
          <!-- v-bind:class="{key : value}" 是一个动态的class,当value值未ture, 相当于 class="key" -->
          <tr v-for="category in level1" v-on:click="onClickLevel(category)" v-bind:class="{'active' : category.id === active.id}">
            <td>{{category.id}}</td>
            <td>{{category.name}}</td>
            <td>{{category.sort}}</td>
            <td>
              <div class="hidden-sm hidden-xs btn-group">
                <button v-on:click="edit(category)" class="btn btn-xs btn-info">
                  <i class="ace-icon fa fa-pencil bigger-120"></i>
                </button>
                <button v-on:click="del(category.id)" class="btn btn-xs btn-danger">
                  <i class="ace-icon fa fa-trash-o bigger-120"></i>
                </button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="col-md-5">
        <!--新增分类和刷新按钮-->
        <p>
          <button v-on:click="add2()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-edit"></i>
            新增二级
          </button>
          &nbsp;
        </p>

        <!--分类数据显示列表-->
        <table id="level2-table" class="table  table-bordered table-hover">
          <thead>
          <tr>
            <th>id</th>
            <th>名称</th>
            <th>顺序</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="category in level2">
            <td>{{category.id}}</td>
            <td>{{category.name}}</td>
            <td>{{category.sort}}</td>
            <td>
              <div class="hidden-sm hidden-xs btn-group">
                <button v-on:click="edit(category)" class="btn btn-xs btn-info">
                  <i class="ace-icon fa fa-pencil bigger-120"></i>
                </button>
                <button v-on:click="del(category.id)" class="btn btn-xs btn-danger">
                  <i class="ace-icon fa fa-trash-o bigger-120"></i>
                </button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>


    <!--新增或编辑分类功能表单-->
    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">分类编辑</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">父分类</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{active.name || "无"}}</p>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input v-model="category.name" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">顺序</label>
                <div class="col-sm-10">
                  <input v-model="category.sort" class="form-control">
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

  export default {
    name: "business-category",
    data: function() {
      return {
        category: {},
        categorys: [],
        /* 一级二级分类数组 */
        level1: [],
        level2: [],
        active: {},
      }
    },

    mounted: function() {
      let _this = this;
      _this.all();
    },

    methods: {

      /**
       * 添加分类功能,点击弹出表单
       */
      add1() {
        let _this = this;
        _this.active = {};
        _this.level1 = [];
        _this.category = {
          parent: "00000000"
        };
        $("#form-modal").modal("show");
      },


      add2() {
        let _this = this;
        if (Tool.isEmpty(_this.active)) {
          Toast.warning("请先点击一级分类");
          return;
        }
        _this.category = {
          parent: _this.active.id
        };
        $(".modal").modal("show");
      },


      /**
       * 查询分类列表
       */
      all() {
        let _this = this;
        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/category/all").then((response)=>{
          Loading.hide();
          let resp = response.data
          _this.categorys = resp.content;
          // 将分类记录格式化成树型结构
          _this.level1 = [];
          for (let i = 0; i < _this.categorys.length; i++) {
            let c = _this.categorys[i];
            if (c.parent === '00000000') {
              /*push() 方法可向数组的末尾添加一个或多个元素，并返回新的长度。*/
              _this.level1.push(c);
              for (let j = 0; j < _this.categorys.length; j++) {
                let child = _this.categorys[j];
                if (child.parent === c.id) {
                  if (Tool.isEmpty(c.children)) {
                    c.children = [];
                  }
                  c.children.push(child);
                }
              }
            }
          }

        })
      },

      /**
       * 表单添加分类数据后保存
       */
      save() {
        let _this = this

        /* 保存时校验 */
        if (1 != 1
          || !Validator.require(_this.category.parent, "父id")
          || !Validator.require(_this.category.name, "名称")
          || !Validator.length(_this.category.name, "名称", 1, 50)
        ) {
          return;
        }

        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/category/save", _this.category).then((response)=>{
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
            $("#form-modal").modal("hide");
            _this.all();
            Toast.success("保存成功！");
          } else {
            Toast.warning(resp.message)
          }
        })
      },

      /**
       * 表单修改已有分类数据
       * @param category
       */
      edit(category) {
        let _this = this;
        _this.category = $.extend({},category);
        $("#form-modal").modal("show");
      },

      /**
       * 根据分类id删除对应数据
       * @param id
       */
      del(id) {
        let _this = this;
        Confirm.show("删除分类数据后不可恢复，确认删除?", function () {
          Loading.show();
          // 使用 restful 风格传递要删除的id
          _this.$ajax.delete(process.env.VUE_APP_SERVER + "/business/admin/category/delete/" + id).then((response) =>{
            Loading.hide();
            let resp = response.data;
            if (resp.success) {
              _this.all();
              Toast.success("删除成功！");
            }
          })
        })
      },

      onClickLevel(category) {
        let _this = this;
        _this.active = category;
        _this.level2 = category.children;
      }
    }
  }
</script>


<style scoped>
  .active td {
    background-color: #d6e9c6 !important;
  }
</style>