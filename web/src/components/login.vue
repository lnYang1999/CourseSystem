<template>
  <div id="login-modal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-login" role="document">
      <div class="modal-content">
        <div class="modal-body">
          <div class="login-div" v-show="MODAL_STATUS === STATUS_LOGIN">
            <h3>登&nbsp;&nbsp;录</h3>
            <div class="form-group">
              <input v-model="clubber.mobile" class="form-control" placeholder="手机号">
            </div>
            <div class="form-group">
              <input class="form-control" type="password" placeholder="密码" v-model="clubber.password">
            </div>
            <div class="form-group">
              <div class="input-group">
                <input id="image-code-input" class="form-control" type="text" placeholder="验证码"
                       v-model="clubber.imageCode">
                <div class="input-group-addon" id="image-code-addon">
                  <img id="image-code" alt="验证码" v-on:click="loadImageCode()"/>
                </div>
              </div>
            </div>
            <div class="form-group">
              <button v-on:click="login()" class="btn btn-primary btn-block submit-button">
                登&nbsp;&nbsp;录
              </button>
            </div>
            <div class="form-group">
              <div class="checkbox">
                <label>
                  <input type="checkbox" class="remember" v-model="remember"> 记住密码
                </label>
                <div class="pull-right">
                  <a href="javascript:;" v-on:click="toForgetDiv()">忘记密码</a>&nbsp;
                  <a href="javascript:;" v-on:click="toRegisterDiv()">我要注册</a>
                </div>
              </div>
            </div>
            <div class="form-group to-register-div">
            </div>
          </div>
          <div class="register-div" v-show="MODAL_STATUS === STATUS_REGISTER">
            <h3>注&nbsp;&nbsp;册</h3>
            <div class="form-group">
              <input id="register-mobile" v-model="clubberRegister.mobile"
                     class="form-control" placeholder="手机号">
            </div>
            <div class="form-group">
              <div class="input-group">
                <input id="register-mobile-code" class="form-control"
                       placeholder="手机验证码" v-model="clubberRegister.code">
                <div class="input-group-append">
                  <button class="btn btn-outline-secondary" id="register-send-code-btn"
                          v-on:click="sendSmsForRegister()">发送验证码
                  </button>
                </div>
              </div>
            </div>
            <div class="form-group">
              <input id="register-name" v-model="clubberRegister.name"
                     class="form-control" placeholder="昵称">
            </div>
            <div class="form-group">
              <input id="register-password" v-model="clubberRegister.passwordOriginal"
                     class="form-control" placeholder="密码" type="password">
            </div>
            <div class="form-group">
              <input id="register-confirm-password" v-model="clubberRegister.confirm"
                     class="form-control" placeholder="确认密码"
                     name="clubberRegisterConfirm" type="password">
            </div>
            <div class="form-group">
              <button class="btn btn-primary btn-block submit-button" v-on:click="register()">
                注&nbsp;&nbsp;册
              </button>
            </div>
            <div class="form-group to-login-div">
              <a href="javascript:;" v-on:click="toLoginDiv()">我要登录</a>
            </div>
          </div>
          <div class="forget-div" v-show="MODAL_STATUS === STATUS_FORGET">
            <h3>忘记密码</h3>
            <div class="form-group">
              <input id="forget-mobile" v-model="clubberForget.mobile"
                     class="form-control" placeholder="手机号">
            </div>
            <div class="form-group">
              <div class="input-group">
                <input id="forget-mobile-code" class="form-control"
                       placeholder="手机验证码" v-model="clubberForget.code">
                <div class="input-group-append">
                  <button class="btn btn-outline-secondary" id="forget-send-code-btn">
                    发送验证码
                  </button>
                </div>
              </div>
            </div>
            <div class="form-group">
              <input id="forget-password" v-model="clubberForget.passwordOriginal"
                     class="form-control" placeholder="密码" type="password">
            </div>
            <div class="form-group">
              <input id="forget-confirm-password" v-model="clubberForget.confirm"
                     class="form-control" placeholder="确认密码" type="password">
            </div>
            <div class="form-group">
              <button class="btn btn-primary btn-block submit-button">
                重&nbsp;&nbsp;置
              </button>
            </div>
            <div class="form-group to-login-div">
              <a href="javascript:;" v-on:click="toLoginDiv()">我要登录</a>
            </div>
          </div>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->
</template>

<script>

  export default {
    name: 'the-login',
    data: function () {
      return {
        // 模态框内容切换：登录、注册、忘记密码
        STATUS_LOGIN: "STATUS_LOGIN",
        STATUS_REGISTER: "STATUS_REGISTER",
        STATUS_FORGET: "STATUS_FORGET",
        MODAL_STATUS: "",

        clubber: {},
        clubberForget: {},
        clubberRegister: {},

        remember: true, // 记住密码
        imageCodeToken: ""
      }
    },
    mounted() {
      let _this = this;
      _this.toLoginDiv();
    },
    methods: {

      /**
       * 打开登录注册窗口
       */
      openLoginModal() {
        let _this = this;
        $("#login-modal").modal("show");
      },

      //---------------登录框、注册框、忘记密码框切换-----------------
      toLoginDiv() {
        let _this = this;

        // 从缓存中获取记住的用户名密码，如果获取不到，说明上一次没有勾选“记住我”
        let rememberClubber = LocalStorage.get(LOCAL_KEY_REMEMBER_CLUBBER);
        if (rememberClubber) {
          _this.clubber = rememberClubber;
        }

        // 显示登录框时就刷新一次验证码图片
        _this.loadImageCode();

        _this.MODAL_STATUS = _this.STATUS_LOGIN
      },
      toRegisterDiv() {
        let _this = this;
        _this.MODAL_STATUS = _this.STATUS_REGISTER
      },
      toForgetDiv() {
        let _this = this;
        _this.MODAL_STATUS = _this.STATUS_FORGET
      },

      register() {
        let _this = this;
        _this.clubberRegister.password = hex_md5(_this.clubberRegister.passwordOriginal + KEY);

        // 调服务端注册接口
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/web/clubber/register', _this.clubberRegister).then((response) => {
          let resp = response.data;
          if (resp.success) {
            Toast.success("注册成功");
          } else {
            Toast.warning(resp.message);
          }
        })
      },

      //---------------登录框-----------------
      login () {
        let _this = this;

        // 将明文存储到缓存中
        // let passwordShow = _this.clubber.password;

        // 如果密码是从缓存带出来的，则不需要重新加密
        let md5 = hex_md5(_this.clubber.password);
        let rememberClubber = LocalStorage.get(LOCAL_KEY_REMEMBER_CLUBBER) || {};
        if (md5 !== rememberClubber.md5) {
          _this.clubber.password = hex_md5(_this.clubber.password + KEY);
        }

        _this.clubber.imageCodeToken = _this.imageCodeToken;

        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/web/clubber/login', _this.clubber).then((response)=>{
          let resp = response.data;
          if (resp.success) {
            console.log("登录成功：", resp.content);
            let loginClubber = resp.content;
            Tool.setLoginClubber(resp.content);

            // 判断“记住我”
            if (_this.remember) {
              // 如果勾选记住我，则将用户名密码保存到本地缓存
              // 这里保存密码密文，并保存密文md5，用于检测密码是否被重新输入过
              let md5 = hex_md5(_this.clubber.password);
              LocalStorage.set(LOCAL_KEY_REMEMBER_CLUBBER, {
                mobile: loginClubber.mobile,
                password: _this.clubber.password,
                md5: md5
              });
            } else {
              // 没有勾选“记住我”时，要把本地缓存清空，否则下次显示登录框时会自动显示用户名密码
              LocalStorage.set(LOCAL_KEY_REMEMBER_CLUBBER, null);
            }

            // 登录成功
            _this.$parent.setLoginClubber(loginClubber);
            $("#login-modal").modal("hide");

          } else {
            Toast.warning(resp.message);
            _this.clubber.password = "";
            _this.loadImageCode();
          }
        });
      },

      /**
       * 加载图形验证码
       */
      loadImageCode: function () {
        let _this = this;
        _this.imageCodeToken = Tool.uuid(8);
        $('#image-code').attr('src', process.env.VUE_APP_SERVER + '/business/web/kaptcha/image-code/' + _this.imageCodeToken);
      },

    }
  }
</script>

<style scoped>
  /* 登录框 */
  .login-div .input-group-addon {
    padding: 0;
    border: 0;
  }

  #login-modal h3 {
    text-align: center;
    margin-bottom: 20px;
  }

  #login-modal .modal-login {
    max-width: 400px;
  }

  #login-modal input:not(.remember) {
    height: 45px;
    font-size: 16px;
  }

  #login-modal .submit-button {
    height: 50px;
    font-size: 20px;
  }

  #login-modal .to-login-div {
    text-align: center;
  }
</style>
