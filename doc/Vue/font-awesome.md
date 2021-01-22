## Font Awesome
Font Awesome 是一套为Bootstrap而创造的图标字体库及CSS框架

### 引入项目
* CDN 引入
```html
<link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
```
* 下载文件至本地引入
```html
<link rel="stylesheet" href="path/to/font-awesome/css/font-awesome.min.css">
```

### 基本使用
* 基本图标：使用 fa 前缀 + 图标名称
```html
<i class="fa fa-edit"></i>编辑图标
```

* 大图标：使用 fa-lg (33%递增)、fa-2x、 fa-3x、fa-4x，或者 fa-5x  来放大图标
```html
<i class="fa fa-edit fa-lg"></i>
```

* 列表：使用 fa-ul 和 fa-li 便可以简单的将无序列表的默认符号替换掉
```html
<ul class="fa-ul">
  <li><i class="fa-li fa fa-check-square"></i>List icons</li>
  <li><i class="fa-li fa fa-check-square"></i>can be used</li>
  <li><i class="fa-li fa fa-spinner fa-spin"></i>as bullets</li>
  <li><i class="fa-li fa fa-square"></i>in lists</li>
</ul>
```

> 更多使用参考 fontAwesome 中文网：http://www.fontawesome.com.cn