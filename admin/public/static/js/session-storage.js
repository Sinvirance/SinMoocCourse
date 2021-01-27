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