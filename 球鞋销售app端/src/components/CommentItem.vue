<template>
  <!-- emit 发射，将组件上接受到的click事件发射给发组件中的click事件 -->
  <div class="comment_item" @click="$emit('click')">
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
        <img v-else src="../assets/pl.jpg" style="margin-top:25px">
      </van-col>
      <van-col :offset="1" :span="19">
        <div> <strong>评论时间：</strong> {{data.commentTime | dateFmt('YYYY-MM-DD HH:mm:ss')}}</div>
        <div> <strong>评论内容：</strong> {{data.content}}</div>
        <div> <strong>商品id：</strong> {{data.orderId}}</div>
      </van-col>

    </van-row>
           <!-- <van-rate value="{{ value }}" bind:change="onChange" /> -->
  </div>
</template>
<script>
export default {
  props:['data','title'],
  data(){
    return {
      value:0 ,
    }
  },
  methods:{
        backHandler(){
      this.$router.go(-1)
    },
    onChange(event) {
    this.setData({
      value: event.detail,
      });
    }
  }
}
</script>
<style scoped>
.comment_item {
  margin:.9em 1em;
  padding: .9em;
  border-radius: 15px;
  height: 90px;
  border: 1px dotted #ccc;
}

.comment_item img {
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