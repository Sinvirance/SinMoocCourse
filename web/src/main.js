import Vue from 'vue'
import App from './app.vue'
import router from "./router";

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app');

/* process.env: 会返回包含用户环境的对象 */
console.log("前台环境：" + process.env.NODE_ENV);