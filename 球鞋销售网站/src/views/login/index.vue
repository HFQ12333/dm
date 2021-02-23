<template>
  <div class="login-container" >
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="right">
    <div class="outer">
      <div class="title-container">
        <h3 class="title">线上球鞋销售网站</h3>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="Username"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password" >
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="Password"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <!-- 验证码 -->
      <el-form-item prop="login_code">
        <span class="svg-container">
          <svg-icon icon-class="icon-mima" />
        </span>
        <el-input 
          :key="identifyCodes"
          :type="identifyCodes"
          placeholder="请输入验证码" 
          style="width:150px" 
          ref="identifyCode"
          v-model="loginForm.identifyCode"
          name="identifyCode"
          tabindex="3"
          />
        <div class="code" @click="refreshCode">
        <s-identify :identifyCode="identifyCode"></s-identify>
        </div>
      </el-form-item>
      <el-button :loading="loading" type="default" style="width:90%;margin-bottom:30px;background-color:lightblue;margin-left:25px" @click.native.prevent="handleLogin">Login</el-button>
      <div class="tips">
        <span> username: admin</span>
        <span> password:123321</span>
        <span> 验证码:请输入</span>
      </div>
      </div>
    </el-form>
  </div>
</template>

<script>
import { validUsername } from '@/utils/validate'
import SIdentify from '@/components/identify'
export default {
  name: 'Login',
  components:{
    's-identify':SIdentify
  },
  data() {
    const validateUsername = (rule, value, callback) => {
     
        callback()
     
    }
    const validatePassword = (rule, value, callback) => {
      
        callback()
      
    }
    const validateidentifyCode = (rule, value, callback) => {
        if(value != this.identifyCode){
          callback(new Error('请输入正确的验证码'))
        } else{
        callback()
        }
    }
    return {
      loginForm: {
        username: '',
        password: '',
        identifyCode:''
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }],
        identifyCode: [{ required: true, trigger: 'blur', validator: validateidentifyCode }]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined,
      identifyCodes: "1234567890",
      identifyCode: ""
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  mounted() {
    this.identifyCode = "";
    this.makeCode(this.identifyCodes, 4);
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login',this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || '/' })
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
      // js原生验证码
      // var str = "abcdefghijklmnopqrstuvwxyz";
      // str = str.concat("0123456789",str.toUpperCase())
      // var arr = str.split('')
      // var ranCode = ''
      // for(var i = 0; i < 4; i++){
      //    var ranIdx = parseInt(Math.random() * arr.length);
      //    ranCode+=arr[ranIdx]
      // }
    },
    randomNum(min, max) {
      return Math.floor(Math.random() * (max - min) + min);
    },
    refreshCode() {
      this.identifyCode = "";
      this.makeCode(this.identifyCodes, 4);
    },
    makeCode(o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[
          this.randomNum(0, this.identifyCodes.length)
        ];
      }
      console.log(this.identifyCode);
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-胡富强/pull/927 */

$bg:#978FE0;
$light_gray:rgb(255, 255, 255);
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.outer{
  background-color: rgba(122, 121, 121, 0.5);
}
.login-container {
  width: 100%;
  background-image:url('https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603896593009&di=422746123596f0a21b5c33c062daf17e&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20160414%2F824a271dbf4247cbb2329950230cbda8_th.jpg') ;
  background-size: 100% 100%;
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: rgb(151, 143, 224);
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
    border: 1px solid rgba(126, 218, 73, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #1f1d1d;
  }
}
</style>

<style lang="scss" scoped>
$bg:#3777ca;
$dark_gray:#889aa4;
$light_gray:rgb(204, 42, 42);

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 500px;
    max-width: 100%;
    padding: 160px 35px 0px;
    // margin: 0 auto;
    margin-left: 300px;
    margin-top: 120px;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: rgb(208, 238, 199);
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: #030303;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;
    background-color: #92acce;

    .title {
      font-size: 26px;
      color: rgb(43, 29, 172);
      margin: -80px  auto 40px auto;
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
  .code {
  width: 110px;
  height: 45px;
  border: 1px solid rgba(250, 249, 249,0.1);
  display: inline-block;
  margin-right:30px;
  float: right;
}
  .el-icon-refresh{
    margin-left: 3px;
  } 
}

</style>
