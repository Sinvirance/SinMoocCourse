import Vue from 'vue'
import App from './app.vue'
import router from "./router";
import axios from "axios";

Vue.config.productionTip = false;
/* 每一个vue组件都是Vue的实例，所以组件内this可以拿到Vue.prototype上添加的属性和方法 */
Vue.prototype.$ajax = axios;

new Vue({
  router,
  render: h => h(App),
}).$mount('#app');

/* process.env: 会返回包含用户环境的对象 */
console.log("前台环境：" + process.env.NODE_ENV);