<template>
  <div>
    <!--新增文件和刷新按钮-->
    <p>
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
      </tr>
      </tbody>
    </table>

    <!-- 分页组件显示 -->
    <pagination ref="pagination" v-bind:list="list"/>
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
      }
    }
  }
</script>