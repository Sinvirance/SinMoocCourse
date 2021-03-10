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

console.log("环境", process.env.NODE_ENV);