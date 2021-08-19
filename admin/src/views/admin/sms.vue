<template>
  <div>
    <!--新增短信验证码和刷新按钮-->
    <p>
      <!--点击触发事件查询list()-->
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh "></i>
        刷新
      </button>
    </p>

    <!--短信验证码数据分页显示列表-->
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>id</th>
        <th>手机号</th>
        <th>验证码</th>
        <th>用途</th>
        <th>生成时间</th>
        <th>状态</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="sms in smss">
        <td>{{sms.id}}</td>
        <td>{{sms.mobile}}</td>
        <td>{{sms.code}}</td>
        <td>{{SMS_USE | optionKV(sms.use)}}</td>
        <td>{{sms.at}}</td>
        <td>{{SMS_STATUS | optionKV(sms.status)}}</td>
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
    name: "business-sms",
    data: function() {
      return {
        sms: {},
        smss: [],
        SMS_USE: SMS_USE,
        SMS_STATUS: SMS_STATUS,
      }
    },

    mounted: function() {
      let _this = this;
      _this.$refs.pagination.size = 5;
      _this.list(1);
    },

    methods: {

      /**
       * 显示指定页码的短信验证码列表
       * @param page 前端传入查询的页码数
       */
      list(page) {
        let _this = this;
        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/sms/list",{
          page: page,
          size: _this.$refs.pagination.size,
        }).then((response)=>{
          Loading.hide();
          let resp = response.data
					if (resp.success) {
						_this.smss = resp.content.list;
						_this.$refs.pagination.render(page, resp.content.total);
					}
        })
      },

    }
  }
</script>