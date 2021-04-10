SESSION_KEY_LOGIN_MEMBER = "SESSION_KEY_LOGIN_MEMBER"; // 登录信息
SESSION_KEY_LINE = "SESSION_KEY_LINE"; // 路线管理页面点击课程路线管理时，保存路线信息

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
