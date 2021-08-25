<template>
  <div class="main-container">
    <div class="main-content">
      <div class="row">
        <div class="col-sm-10 col-sm-offset-1">
          <div class="login-container">
            <div class="center">
              <h1>
                <i class="ace-icon fa fa-leaf green"></i>
                <span class="">控台登录</span>
              </h1>
            </div>

            <div class="space-6"></div>

            <div class="position-relative">
              <div id="login-box" class="login-box visible widget-box no-border">
                <div class="widget-body">
                  <div class="widget-main">
                    <h4 class="header blue lighter bigger">
                      <i class="ace-icon fa fa-coffee green"></i>
                      请输入用户名密码
                    </h4>

                    <div class="space-6"></div>

                    <form>
                      <fieldset>
                        <label class="block clearfix">
                          <span class="block input-icon input-icon-right">
                            <!-- v-model 用于双向绑定表单数据，只能用于表单 -->
                            <input v-model="user.loginName" type="text" class="form-control" placeholder="用户名"/>
                            <i class="ace-icon fa fa-user"></i>
                          </span>
                        </label>

                        <label class="block clearfix">
                          <span class="block input-icon input-icon-right">
                            <input v-model="user.password" type="password" class="form-control" placeholder="密码"/>
                            <i class="ace-icon fa fa-lock"></i>
                          </span>
                        </label>

                        <!-- 引入图像验证码 -->
                        <label class="block clearfix">
                          <span class="block input-icon input-icon-right">
                            <div class="input-group">
                              <input v-model="user.imageCode" type="text" class="form-control" placeholder="验证码">
                              <span class="input-group-addon" id="basic-addon2">
                                <img v-on:click="loadImageCode()" id="image-code" alt="验证码"/>
                              </span>
                            </div>
                          </span>
                        </label>

                        <div class="space"></div>

                        <div class="clearfix">
                          <label class="inline">
                            <input v-model="remember" type="checkbox" class="ace"/>
                            <span class="lbl">记住我</span>
                          </label>

                          <!--添加点击事件，实现页面跳转-->
                          <button type="button"
                                  class="width-35 pull-right btn btn-sm btn-primary"
                                  v-on:click="login()">
                            <i class="ace-icon fa fa-key"></i>
                            <span class="bigger-110">登录</span>
                          </button>
                        </div>

                        <div class="space-4"></div>
                      </fieldset>
                    </form>

                  </div><!-- /.widget-main -->

                </div><!-- /.widget-body -->
              </div><!-- /.login-box -->
            </div><!-- /.position-relative -->

          </div>
        </div><!-- /.col -->
      </div><!-- /.row -->
    </div><!-- /.main-content -->
  </div><!-- /.main-container -->
</template>

<script>
  export default {
    name: "login",
    data: function () {
      return {
        user: {},
        remember: true, //记住我功能默认勾选
        imageCodeToken: "",
      }
    },

    // 在模板渲染成html后调用，通常是初始化页面完成后，再对html的dom节点进行一些需要的操作。
    mounted: function () {
      let _this = this;
      // 删除样式
      $("body").removeClass("no-skin");
      // 增加样式
      $("body").attr("class", "login-layout light-login");

      // 从缓存中获取记住的用户名密码，如果获取不到，说明上一次没有勾选“记住我”
      let rememberUser = LocalStorage.get(LOCAL_KEY_REMEMBER_USER);
      if (rememberUser) {
        _this.user = rememberUser;
      }

      /* 页面初始加载时加载验证码图片 */
      _this.loadImageCode();
    },

    methods: {

      login(){
        let _this = this;

        let md5 = hex_md5(_this.user.password);
        let rememberUser = LocalStorage.get(LOCAL_KEY_REMEMBER_USER) || {};
        if (md5 !== rememberUser.md5) {
          _this.user.password = hex_md5(_this.user.password + KEY);
        }

        _this.user.imageCodeToken = _this.imageCodeToken;

        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/login', _this.user).then(
            (response) => {
              Loading.hide();
              let resp = response.data;
              if (resp.success) {
                console.log("登录成功:", resp.content);
                let loginUser = resp.content;
                SessionStorage.setLoginUser(resp.content);

                // 判断“记住我”
                if (_this.remember) {
                  // 原：这里需要保存密码明文，否则登录时又会再加一层密
                  // 新：这里保存密码密文，并保存密文md5，用于检测密码是否被重新输入过
                  let md5 = hex_md5(_this.user.password);
                  // 如果勾选记住我，则将用户名密码保存到本地缓存，这里需要保存密码明文，否则登录时又会再加一层密
                  LocalStorage.set(LOCAL_KEY_REMEMBER_USER, {
                    loginName: loginUser.loginName,
                    password: _this.user.password,
                    md5: md5,
                  });
                } else {
                  // 没有勾选“记住我”时，要把本地缓存清空，否则按照mounted的逻辑，下次打开时会自动显示用户名密码
                  LocalStorage.set(LOCAL_KEY_REMEMBER_USER, null);
                }
                // 想要导航到不同的URL，使用router.push()方法，这个方法会向history栈添加一个新纪录，所以，当用户点击浏览器后退按钮时，会回到之前的URL
                _this.$router.push("/welcome");
								// window.open("/welcome", "_self");
              } else {
                Toast.warning(resp.message);
                _this.loadImageCode();
              }
            })
      },

      /**
       * 加载图形验证码
       */
      loadImageCode(){
        let _this = this;
        /* 生成随机toke来通过后端生成验证吗 */
        _this.imageCodeToken = Tool.uuid(8);
        $('#image-code').attr('src', process.env.VUE_APP_SERVER + '/system/admin/kaptcha/image-code/' + _this.imageCodeToken);
      },
    }
  }
</script>

<style scoped>
  .input-group-addon {
    padding: 0;
  }
</style>