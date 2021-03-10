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
  isNotEmpty: function (obj) {
    return !this.isEmpty(obj);
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
  },


  /**
   * 时间格式化，date为空时取当前时间
   * @param format
   * @param date
   * @returns {*}
   */
  dateFormat: function (format, date) {
    let result;
    if (!date) {
      date = new Date();
    }
    const option = {
      "y+": date.getFullYear().toString(),        // 年
      "M+": (date.getMonth() + 1).toString(),     // 月
      "d+": date.getDate().toString(),            // 日
      "h+": date.getHours().toString(),           // 时
      "m+": date.getMinutes().toString(),         // 分
      "s+": date.getSeconds().toString()          // 秒
    };
    for (let i in option) {
      result = new RegExp("(" + i + ")").exec(format);
      if (result) {
        format = format.replace(result[1], (result[1].length == 1) ? (option[i]) : (option[i].padStart(result[1].length, "0")))
      }
    }
    return format;
  },


  /**
   * 移除对象数组中的对象
   * @param array
   * @param obj
   * @returns {number}
   */
  removeObj: function (array, obj) {
    let index = -1;
    for (let i = 0; i < array.length; i++) {
      if (array[i] === obj) {
        array.splice(i, 1);
        index = i;
        break;
      }
    }
    return index;
  },


  /**
   * 10进制转62进制
   * @param number
   * @returns {string}
   * @private
   */
  _10to62: function (number) {
    let chars = '0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ';
    let radix = chars.length;
    let arr = [];
    do {
      let mod = number % radix;
      number = (number - mod) / radix;
      arr.unshift(chars[mod]);
    } while (number);
    return arr.join('');
  },

  /**
   * 随机生成[len]长度的[radix]进制数
   * @param len
   * @param radix 默认62
   * @returns {string}
   */
  uuid: function (len, radix) {
    let chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    let uuid = [];
    radix = radix || chars.length;

    for (let i = 0; i < len; i++) {
      uuid[i] = chars[0 | Math.random() * radix];
    }

    return uuid.join('');
  }
}