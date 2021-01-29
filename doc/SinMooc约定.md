## SinMooc 约定
本项目是基于SpringBoot,在SpringBoot中，约定优于配置<br/>
本文记录了SinMooc中的约定

### 后端约定
* 查询类接口以list或者query开头，保存以save开头，删除用delete开头
* 当length > 0 是，表示该字段需要校验， length = 0，表示该字段不需要校验

### 前端约定
* 使用 route.js 下的name属性，将其中"/" -> "-" + "-sidebar" 就变化成了侧边栏菜单的激活id

### 数据表约定
* category 分类表一级分类父id为00000000，二级子分类父id对应一级分类的id
* 枚举字段在comment会标注枚举字样，和对应的枚举类，用于前端枚举字段的生成
* 在comment使用 "|" 来间隔 字段中文名|解释 