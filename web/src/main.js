import Vue from 'vue'
import App from './app.vue'
import router from "./router";
import axios from "axios";
import filter from "./filter/filter"

Vue.config.productionTip = false;
/* 每一个vue组件都是Vue的实例，所以组件内this可以拿到Vue.prototype上添加的属性和方法 */
Vue.prototype.$ajax = axios;

/**
 * 全局过滤器
 */
Object.keys(filter).forEach(key => {
  /* Vue.filter()：把Array的某些元素过滤掉，然后返回剩下的元素,这里是将key去掉，留value  */
  Vue.filter(key, filter[key])
});

new Vue({
  router,
  render: h => h(App),
}).$mount('#app');

/* process.env: 会返回包含用户环境的对象 */
console.log("前台环境：" + process.env.NODE_ENV);