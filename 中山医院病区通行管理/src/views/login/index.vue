<template>
  <div class="login-container">
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      autocomplete="on"
      label-position="left"
    >
      <div class="logo-div">
        <img class="logo" src="../../../public/zshospital.png" alt="" />
      </div>
      <div class="title-container">
        <h4 class="title">中山医院病区通行管理系统</h4>
      </div>

      <el-form-item prop="account">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="account"
          v-model="loginForm.account"
          placeholder="用户名"
          name="account"
          type="text"
          tabindex="1"
          autocomplete="on"
        />
      </el-form-item>

      <el-tooltip
        v-model="capsTooltip"
        content="大写锁定已开启"
        placement="right"
        manual
      >
        <el-form-item prop="password">
          <span class="svg-container">
            <svg-icon icon-class="password" />
          </span>
          <el-input
            :key="passwordType"
            ref="password"
            v-model="loginForm.password"
            :type="passwordType"
            placeholder="密码"
            name="password"
            tabindex="2"
            autocomplete="on"
            @keyup.native="checkCapslock"
            @blur="capsTooltip = false"
            @keyup.enter.native="handleLogin"
          />
          <span class="show-pwd" @click="showPwd">
            <svg-icon
              :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"
            />
          </span>
        </el-form-item>
      </el-tooltip>

      <el-button
        :loading="loading"
        type="primary"
        style="width: 100%; margin-bottom: 30px"
        @click.native.prevent="handleLogin"
        >登录</el-button
      >
    </el-form>
  </div>
</template>

<script>
import { validUsername } from "@/utils/validate";
import SocialSign from "./components/SocialSignin";
import store from '../../store'

export default {
  name: "Login",
  components: { SocialSign },
  data () {
    return {
      loginForm: {
        account: "",
        password: "",
      },
      loginRules: {
        account: [
          {
            required: true,
            trigger: "blur",
            message: '请输入用户名',
            // 关闭用户名验证
            // validator: validateUsername
          },
        ],
        password: [
          {
            required: true,
            trigger: "blur",
            message: '请输入密码',
            // 关闭密码长度验证
            // validator: validatePassword,
          },
        ],
      },
      passwordType: "password",
      capsTooltip: false,
      loading: false,
      redirect: undefined,
      otherQuery: {},
    };
  },
  watch: {
    $route: {
      handler: function (route) {
        const query = route.query;
        if (query) {
          // 关闭登录时路由重定向的问题
          // this.redirect = query.redirect;
          // this.otherQuery = this.getOtherQuery(query);
        }
      },
      immediate: true,
    },
  },
  created () {
    // window.addEventListener('storage', this.afterQRScan)
  },
  mounted () {
    // 关闭自动聚焦 防止浏览器记住密码时导致的表单校验失败
    // if (this.loginForm.account === "") {
    //   this.$refs.account.focus();
    // } else if (this.loginForm.password === "") {
    //   this.$refs.password.focus();
    // }
  },
  destroyed () {
    // window.removeEventListener('storage', this.afterQRScan)
  },
  methods: {
    checkCapslock (e) {
      const { key } = e;
      this.capsTooltip = key && key.length === 1 && key >= "A" && key <= "Z";
    },
    showPwd () {
      if (this.passwordType === "password") {
        this.passwordType = "";
      } else {
        this.passwordType = "password";
      }
      this.$nextTick(() => {
        this.$refs.password.focus();
      });
    },
    handleLogin () {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          this.$store
            .dispatch("user/login", this.loginForm)
            .then(() => {
              console.log("登录成功");
              new Promise(
                function (resolve) {
                  var res = store.dispatch('user/getInfo')
                  resolve(res)
                }
              ).then((res) => {
                console.log(res);
                const { is_manager } = res;
                if (is_manager) {
                  this.$router.push("/systemManage/accountManage");
                  this.$router.go(0)
                } else {
                  this.$router.push({
                    path: this.redirect || "/",
                    query: this.otherQuery,
                  });
                  this.$router.go(0)
                }

                console.log(res)
              },  // 成功
              )


              this.loading = false;
            })
            .catch(() => {
              this.loading = false;
              console.log("catch");
            });
        } else {
          console.log("错误的提交!");
          return false;
        }
      });
    },
    getOtherQuery (query) {
      return Object.keys(query).reduce((acc, cur) => {
        if (cur !== "redirect") {
          acc[cur] = query[cur];
        }
        return acc;
      }, {});
    },
  },
};
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #fff;
$light_gray: #000;
$cursor: #000;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  padding-top: 100px;
  background-image: url("../../../public/backgroungImg.jpg");
  background-repeat: no-repeat;
  background-size: 100% 100%;
  .logo-div {
    text-align: center;
  }
  .logo {
    width: 200px;
  }
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg: #fff;
$dark_gray: #889aa4;
$light_gray: rgb(24, 121, 255);

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }

  .thirdparty-button {
    position: absolute;
    right: 0;
    bottom: 6px;
  }

  @media only screen and (max-width: 470px) {
    .thirdparty-button {
      display: none;
    }
  }
}
</style>
