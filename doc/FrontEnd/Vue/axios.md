## Axios-Vue实现Ajax
Vue 本身不支持发送 ajax 请求,需要通过其他组件来实现
Vue.js 2.0 版本推荐使用 axios 来完成 ajax 请求

### Axios 安装
在Vue项目路径下
```node.js
npm install axios --save
```
### Axios 基本用法
* 引入到 Vue.cil 项目中
```javascript
// 引入 axios 组件
import axios from 'axios';

// 将其定义为全局变量，在任意 Vue 组件中，使用 this.$ajax 使用
Vue.prototype.$ajax = axios;
```
* Get 请求
```javascript
  // 使用get请求访问 /user,成功打印访问结果，失败打印error
  _this.$ajax.get('/user').then((response) => {
		console.log(response);
	}).catch( (error) => {
		console.log(error);
		// 真实数据存储在 data中
		respondse.data;
	});
```

* Post 请求: axios的post请求默认是以流的方式传递参数，所以controller里的参数要加@RequestBody注解
```javascript
  _this.$ajax.post("http://127.0.0.1:9000/business/admin/chapter/list",{
    page: 1,
    size: 1,
  }).then((response)=>{
    console.log("查询大章结果：", response.data);
    // 真实数据存储在响应对象的 data.list属性
    response.data.list;
  })
```

* 拦截器：在请求或响应被 then 或 catch 处理前拦截它们
```javascript
// 添加请求拦截器
axios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    return config;
  }, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  });

// 添加响应拦截器
axios.interceptors.response.use(function (response) {
    // 对响应数据做点什么
    return response;
  }, function (error) {
    // 对响应错误做点什么
    return Promise.reject(error);
  });
```
如果你想在稍后移除拦截器，可以这样：
```javascript
var myInterceptor = axios.interceptors.request.use(function () {/*...*/});
axios.interceptors.request.eject(myInterceptor);
```