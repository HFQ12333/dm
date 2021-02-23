<template>
  <div class="user">
    <div class="header">
      <div class="photo">
        <img src="../../assets/1.jpg" alt=""/>
      </div>
      <div class="name">{{info.name}}</div>
    </div>
    
    <van-cell title="常用地址" is-link to="/manager/address" />
    <van-cell title="所有订单" is-link to="/manager/order" />
    <van-cell title="购物列表" is-link to="/manager/message" />
    <van-cell title="我的评论" is-link to="/manager/comment" />

    <div class="btn" @click="logoutHandler">
      退出登录
    </div>
  </div>
</template>
<script>
import {mapState, mapActions} from 'vuex'
export default {
  methods:{
    ...mapActions('user',['logout']),
    toAddressHandler(){
      // this.$router.push({path:'/manager/address'})
    },
    logoutHandler(){
      this.logout()
      .then(()=>{
        this.$router.push({path:'/login'})
      })
    }
  },
  computed:{
    ...mapState("user",["info"])
  }
}
</script>

<style scoped>
.header {
  padding-top: 46px;
  text-align: center;
  background: #9dc4ee;
  margin-bottom: 2em;
}

.header .photo {
  margin: 0 auto;
  width: 10em;
  height: 10em;
  border-radius: 50%;
  box-sizing: border-box;
  border:1px solid #1a1a1b;
  overflow:hidden;
  padding: 1em;
}
.header .name {
  line-height: 3em;
  font-size: 16px;
  color: #241a3a;

}
.header .photo img {
 width: 100%;
 border-radius: 50%;
}

.btn {
  width: 80%;
  margin: 1em auto;
  line-height: 3em;
  text-align: center;
  border: 1px solid #bcc4f1;
  border-radius: 1.8em;
  background-color: rgb(239, 240, 245);
  margin-top: 90px;
}

</style>