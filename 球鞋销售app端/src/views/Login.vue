<template>
  <div class="login">
    <div class="header">
      <div class="logo"></div>
      <div class="title">
        爱鞋吧
      </div>
    </div>
    <div class="form">
      <van-cell-group>
        <van-field
          v-model="form.username"
          required
          clearable
          label="用户名"
          icon="question-o"
          placeholder="请输入用户名"
        />
        <van-field
          v-model="form.password"
          type="password"
          label="密码"
          placeholder="请输入密码"
          required
        />
      </van-cell-group>
      <div style="text-align:center;padding:1em" >
       <van-button 
        plain round 
        @click="loginHandler" 
        style="width:100%">登录</van-button>
      </div>
    </div>
  </div>
</template>
<script>
import {mapActions} from 'vuex'
export default {
  data(){
    return {
      form:{
        type:'customer'
      },
       imagesbox:[{id:0,idView:"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603869660317&di=69b9fcb892fb05e9485dcbaf0085fefe&imgtype=0&src=http%3A%2F%2Fimg3.tbcdn.cn%2Ftfscom%2Fi2%2F821973913%2FTB2gwUsbrFlpuFjy0FgXXbRBVXa_%2521%2521821973913.gif"},   
           {id:1,idView:"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603869598808&di=656237b36d8408dd8e30e05f593998c5&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20130530%2FImg377553343.jpg"},
          {id:2,idView:"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603869713658&di=18e3e63c9a4f86d97318626f7ff13d61&imgtype=0&src=http%3A%2F%2Fi1.letvimg.com%2Flc03_qmt%2F201708%2F23%2F07%2F52%2Fvidxsujfcl6j%2F169.jpg"}],
           active: 0,
           
    }
  },
  methods:{
    ...mapActions("user",["login"]),
    loginHandler(){
      this.login(this.form)
      .then(()=>{
        //跳转到首页
        this.$router.push({path:'/manager/home'})
      })
    }
  }
}
</script>
<style scoped>
.header {
  height: 140px;
  padding: 50px;
  color: #ffffff;
  background-color: #8aa5ee;
  text-align: center
}
.header .logo {
  width: 50px;
  height: 50px;
  line-height: 50px;
  border-radius: 50%;
  border: 1px solid #efefef;
  margin: 0 auto;
  color:#efefef;
  font-weight: bold;
  margin-bottom: 1em;
  font-size: 1.5em;
  background-image: url('./manager/image/timg.jpg');
  background-size: 100% 100%;
  
}
</style>