## SinMooc 约定
本项目是基于SpringBoot,在SpringBoot中，约定优于配置<br/>
本文记录了SinMooc中的约定

### 后端约定
* 查询类接口以list或者query开头，保存以save开头，删除用delete开头

### 前端约定
* 使用 route.js 下的name属性，将其中"/" -> "-" + "-sidebar" 就变化成了侧边栏菜单的激活id