(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-513b739c"],{2017:function(e,t,i){"use strict";var o=i("b12d"),n=i.n(o);n.a},"30da":function(e,t,i){},"9ed6":function(e,t,i){"use strict";i.r(t);var o=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"login-container"},[i("el-form",{ref:"loginForm",staticClass:"login-form",attrs:{model:e.loginForm,rules:e.loginRules,"auto-complete":"on","label-position":"right"}},[i("div",{staticClass:"outer"},[i("div",{staticClass:"title-container"},[i("h3",{staticClass:"title"},[e._v("线上球鞋销售网站")])]),e._v(" "),i("el-form-item",{attrs:{prop:"username"}},[i("span",{staticClass:"svg-container"},[i("svg-icon",{attrs:{"icon-class":"user"}})],1),e._v(" "),i("el-input",{ref:"username",attrs:{placeholder:"Username",name:"username",type:"text",tabindex:"1","auto-complete":"on"},model:{value:e.loginForm.username,callback:function(t){e.$set(e.loginForm,"username",t)},expression:"loginForm.username"}})],1),e._v(" "),i("el-form-item",{attrs:{prop:"password"}},[i("span",{staticClass:"svg-container"},[i("svg-icon",{attrs:{"icon-class":"password"}})],1),e._v(" "),i("el-input",{key:e.passwordType,ref:"password",attrs:{type:e.passwordType,placeholder:"Password",name:"password",tabindex:"2","auto-complete":"on"},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleLogin(t)}},model:{value:e.loginForm.password,callback:function(t){e.$set(e.loginForm,"password",t)},expression:"loginForm.password"}}),e._v(" "),i("span",{staticClass:"show-pwd",on:{click:e.showPwd}},[i("svg-icon",{attrs:{"icon-class":"password"===e.passwordType?"eye":"eye-open"}})],1)],1),e._v(" "),i("el-form-item",{attrs:{prop:"login_code"}},[i("span",{staticClass:"svg-container"},[i("svg-icon",{attrs:{"icon-class":"icon-mima"}})],1),e._v(" "),i("el-input",{key:e.identifyCodes,ref:"identifyCode",staticStyle:{width:"150px"},attrs:{type:e.identifyCodes,placeholder:"请输入验证码",name:"identifyCode",tabindex:"3"},model:{value:e.loginForm.identifyCode,callback:function(t){e.$set(e.loginForm,"identifyCode",t)},expression:"loginForm.identifyCode"}}),e._v(" "),i("div",{staticClass:"code",on:{click:e.refreshCode}},[i("s-identify",{attrs:{identifyCode:e.identifyCode}})],1)],1),e._v(" "),i("el-button",{staticStyle:{width:"90%","margin-bottom":"30px","background-color":"lightblue","margin-left":"25px"},attrs:{loading:e.loading,type:"default"},nativeOn:{click:function(t){return t.preventDefault(),e.handleLogin(t)}}},[e._v("Login")]),e._v(" "),i("div",{staticClass:"tips"},[i("span",[e._v(" username: 胡富强")]),e._v(" "),i("span",[e._v(" password:123321")]),e._v(" "),i("span",[e._v(" 验证码:请输入")])])],1)])],1)},n=[],s=(i("61f7"),i("7e88")),r={name:"Login",components:{"s-identify":s["a"]},data:function(){var e=this,t=function(e,t,i){i()},i=function(e,t,i){i()},o=function(t,i,o){i!=e.identifyCode?o(new Error("请输入正确的验证码")):o()};return{loginForm:{username:"",password:"",identifyCode:""},loginRules:{username:[{required:!0,trigger:"blur",validator:t}],password:[{required:!0,trigger:"blur",validator:i}],identifyCode:[{required:!0,trigger:"blur",validator:o}]},loading:!1,passwordType:"password",redirect:void 0,identifyCodes:"1234567890",identifyCode:""}},watch:{$route:{handler:function(e){this.redirect=e.query&&e.query.redirect},immediate:!0}},mounted:function(){this.identifyCode="",this.makeCode(this.identifyCodes,4)},methods:{showPwd:function(){var e=this;"password"===this.passwordType?this.passwordType="":this.passwordType="password",this.$nextTick((function(){e.$refs.password.focus()}))},handleLogin:function(){var e=this;this.$refs.loginForm.validate((function(t){if(!t)return console.log("error submit!!"),!1;e.loading=!0,e.$store.dispatch("user/login",e.loginForm).then((function(){e.$router.push({path:e.redirect||"/"}),e.loading=!1})).catch((function(){e.loading=!1}))}))},randomNum:function(e,t){return Math.floor(Math.random()*(t-e)+e)},refreshCode:function(){this.identifyCode="",this.makeCode(this.identifyCodes,4)},makeCode:function(e,t){for(var i=0;i<t;i++)this.identifyCode+=this.identifyCodes[this.randomNum(0,this.identifyCodes.length)];console.log(this.identifyCode)}}},a=r,d=(i("2017"),i("a185"),i("2877")),l=Object(d["a"])(a,o,n,!1,null,"73524162",null);t["default"]=l.exports},a185:function(e,t,i){"use strict";var o=i("30da"),n=i.n(o);n.a},b12d:function(e,t,i){}}]);