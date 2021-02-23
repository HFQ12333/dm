<template>
  <!-- emit 发射，将组件上接受到的click事件发射给发组件中的click事件 -->
  <div class="message_item" @click="$emit('click')">
    <van-nav-bar
        class="full_layout"
        :border="true"
        :title="title"
        left-text="返回"
        left-arrow
        @click-left="backHandler" />
    <van-row>
      <van-col :span="4">
        <img v-if="data.photo" :src="data.photo" alt="">
        <img v-else src="../assets/sf.jpg" style="margin-top:25px">
      </van-col>
      <van-col :offset="1" :span="19">
        <div> <strong>下单时间：</strong> {{data.orderTime | dateFmt('YYYY-MM-DD HH:mm:ss')}}</div>
        <div> <strong>订单状态：</strong> {{data.status}}</div>
        <div> <strong>商品价格：$</strong> {{data.total}}</div>
        <!-- <div> <strong>快递小哥：</strong> {{data.remark}}</div> -->
      </van-col>
    </van-row>
  </div>
</template>
<script>
export default {
  props:['data','title'],
  data(){
    return {
      value:0
    }
  },
  methods:{
        backHandler(){
      this.$router.go(-1)
    }
  }
}
</script>
<style scoped>
.message_item {
  margin:.5em 1em;
  padding: .5em;
  border-radius: 5px;
  height: 60px;
  border: 1px dotted #ccc;
}

.message_item img {
  width: 100%;
  border-radius: 3px;
}
.full_layout {
  z-index: 2;
  position: absolute;
  top: 0;
  bottom: 0;
  margin-left: -15px;
  width: 30%;
  background-color: rgba(249, 250, 246, 0);
}
</style>