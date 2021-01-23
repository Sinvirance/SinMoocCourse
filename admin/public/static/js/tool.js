Tool = {
  /**
   * 空校验: null 或"" 都返回true
   * @param obj：校验的对象
   * @returns {boolean}
   */
  isEmpty: function (obj) {
    if ((typeof obj == "string")) {
      // !obj 如果操作数能够转换为true则返回false；否则返回true
      // 能被转换为false的值有null, 0, NaN, 空字符串("")和undefined。
      return !obj || obj.replace(/\s+/g, "") == "";
    } else {
      return (!obj || JSON.stringify(obj) === '{}' || obj.length === 0);
    }
  },


  /**
   * 非空校验
   * @returns {boolean}
   */
  isNotEmpty: function () {
    return !this.isEmpty();
  },

  /**
   * 长度校验
   * @param str 传递进来的字符串
   * @param min 最小长度
   * @param max 最大长度
   * @returns {boolean}
   */
  isLength: function (str, min, max) {
    return $.trim(str).length >= min && $.trim(str).length <= max;
  }


}