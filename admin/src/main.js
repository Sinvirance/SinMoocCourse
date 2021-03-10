import Vue from 'vue'
import App from './app.vue'
import router from './router'
import axios from "axios";
import filter from "./filter/filter";

// 阻止启动生产消息
Vue.config.productionTip = false;
// 解决每次ajax请求，对应的sessionId不一致的问题
axios.defaults.withCredentials = true;
// 设置全局变量
Vue.prototype.$ajax = axios;

/**
 * axios request拦截器
 */
axios.interceptors.request.use(function (config) {
    /* 在请求时统一打印请求数据 */
    console.log("请求:", config);

    /* 当用户token不为空时，为所有请求头加上token */
    let token = SessionStorage.getLoginUser().token;
    if (Tool.isNotEmpty(token)) {
      config.headers.token = token;
      console.log("请求headers增加token:", token);
    }
    return config;
}, //对请求错误做些什么
 error => {});

/**
 * axios response 拦截器
 */
axios.interceptors.response.use(function (response) {
    console.log("返回结果:", response);
    return response;
}, error => {});

/**
 * 全局过滤器
 */
Object.keys(filter).forEach(key => {
    /* Vue.filter()：把Array的某些元素过滤掉，然后返回剩下的元素,这里是将key去掉，留value  */
    Vue.filter(key, filter[key])
});

/**
 * 路由登录拦截
 */
router.beforeEach((to, from, next) => {
  // 要不要对meta.loginRequire属性做监控拦截
  if (to.matched.some(function (item) {
    return item.meta.loginRequire;
  })) {
    let loginUser = SessionStorage.getLoginUser();
    if (Tool.isEmpty(loginUser)) {
      next('/login');
    } else {
      next();
    }
  } else {
    next();
  }
});

new Vue({
    router,
    render: h => h(App),
}).$mount('#app');

console.log("环境", process.env.NODE_ENV);