import Vue from 'vue'
import App from './app.vue'
import router from './router'
import axios from "axios";

// 阻止启动生产消息
Vue.config.productionTip = false;
// 设置全局变量
Vue.prototype.$ajax = axios;

/**
 * axios request拦截器
 */
axios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    console.log("请求:", config);
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

new Vue({
    router,
    render: h => h(App),
}).$mount('#app');

console.log("环境", process.env.NODE_ENV);