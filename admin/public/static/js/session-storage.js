/* 定义常量用于 Session key */
SESSION_KEY_COURSE = "SESSION_KEY_COURSE"; // 课程管理页面点击章管理时，保存课程信息
SESSION_KEY_CHAPTER = "SESSION_KEY_CHAPTER"; // 章管理页面点击节管理时，保存章信息

/* 在浏览器中存储 key/value 对的数据,但是所有的浏览器中都会把localStorage的值类型限定为string类型 */
/* 我们在使用中经常需要操作JSON对象类型,需要进行转换 */
SessionStorage = {
  get: function (key) {
    let v = sessionStorage.getItem(key);
    if (v && typeof(v) !== "undefined" && v !== "undefined") {
      return JSON.parse(v);
    }
  },
  set: function (key, data) {
    sessionStorage.setItem(key, JSON.stringify(data));
  },
  remove: function (key) {
    sessionStorage.removeItem(key);
  },
  clearAll: function () {
    sessionStorage.clear();
  }
};