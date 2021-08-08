import Vue from 'vue'
import App from './app.vue'
import router from "./router";
import axios from "axios";
import filter from "./filter/filter"

Vue.config.productionTip = false;
/* 每一个vue组件都是Vue的实例，所以组件内this可以拿到Vue.prototype上添加的属性和方法 */
Vue.prototype.$ajax = axios;
// 解决每次ajax请求，对应的sessionId不一致的问题
axios.defaults.withCredentials = true;

/**
 * 全局过滤器
 */
Object.keys(filter).forEach(key => {
  /* Vue.filter()：把Array的某些元素过滤掉，然后返回剩下的元素,这里是将key去掉，留value  */
  Vue.filter(key, filter[key])
});

/**
 * axios request拦截器
 */
axios.interceptors.request.use(function (config) {
  /* 在请求时统一打印请求数据 */
  console.log("请求：", config);
  return config;
}, error => {});
  //对请求错误做些什么

/**
 * axios response 拦截器
 */
axios.interceptors.response.use(function (response) {
  console.log("返回结果:", response);
  return response;
}, error => {});


new Vue({
  router,
  render: h => h(App),
}).$mount('#app');

/* process.env: 会返回包含用户环境的对象 */
console.log("前台环境：" + process.env.NODE_ENV);

/* 解决原页面跳转的冗余报错 */
// 保存原来的push函数
const routerPush = router.push
// 重写push函数
router.push = function push(location) {

  // 这个if语句在跳转相同路径的时候，在路径末尾添加新参数（一些随机数字）
  // 用来触发watch
  if(typeof(location)=="string"){
    var Separator = "&";
    if(location.indexOf('?')==-1) { Separator='?'; }
    location = location + Separator + "random=" + Math.random();
  }

  // 这个语句用来解决报错
  // 调用原来的push函数，并捕获异常
  return routerPush.call(this, location).catch(error => error)
}
