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
axios.get('/user').then((response) => {
		console.log(response);
	}).catch( (error) => {
		console.log(error);
	});
```