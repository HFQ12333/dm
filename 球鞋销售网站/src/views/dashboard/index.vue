<template>
 <div>
  <el-badge value="new" class="item">
  <el-button size="small" :plain="true" @click="open">评论</el-button>
</el-badge>
<el-badge value="hot" class="item">
  <el-button size="small" @click="repy" to="/">回复</el-button>
</el-badge>
 <el-carousel :interval="1500" type="card" height="400px">
        <el-carousel-item v-for="item in imagesbox" :key="item.id">
           <img :src="item.idView" class="image" size="100% 100%">
        </el-carousel-item>
      </el-carousel>
<br>
<el-tag>了解数据的流程如下</el-tag>

<el-steps :active="active" finish-status="success">
  <el-step title="用户下单完毕"></el-step>
  <el-step title="后台接收数据"></el-step>
  <el-step title="后台进行操作"></el-step>
</el-steps>
<el-button style="margin-top: 12px;" @click="next">下一步</el-button>
</div>

</template>
import comment from '@'
<style>
.item {
  margin-top: 10px;
  margin-right: 40px;
}
.image{
  width: 610px;
  size:100% 100%;
}
</style>
<script>
  export default {
    data(){
      return {
        imagesbox:[{id:0,idView:"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603869660317&di=69b9fcb892fb05e9485dcbaf0085fefe&imgtype=0&src=http%3A%2F%2Fimg3.tbcdn.cn%2Ftfscom%2Fi2%2F821973913%2FTB2gwUsbrFlpuFjy0FgXXbRBVXa_%2521%2521821973913.gif"},   
           {id:1,idView:"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603869598808&di=656237b36d8408dd8e30e05f593998c5&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20130530%2FImg377553343.jpg"},
          {id:2,idView:"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603869713658&di=18e3e63c9a4f86d97318626f7ff13d61&imgtype=0&src=http%3A%2F%2Fi1.letvimg.com%2Flc03_qmt%2F201708%2F23%2F07%2F52%2Fvidxsujfcl6j%2F169.jpg"},
          {id:3,idView:"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=341404928,3515712917&fm=26&gp=0.jpg"},
          {id:4,idView:"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2691521390,1145631538&fm=26&gp=0.jpg"},
          {id:5,idView:"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3894645046,2157605017&fm=26&gp=0.jpg"}],
           active: 0
      }
    },
    methods: {
      open() {
        this.$message('评论消息在评论列表中');
      },
      repy() {
        this.$alert('暂无回复消息', '回复', {
          confirmButtonText: '确定',
          callback: action => {
            this.$message({
              type: 'info',
              message: `action: ${ action }`
            });
          }
        });
      },
       next() {
        if (this.active++ > 2) this.active = 0;
      }
    }
  }
</script>