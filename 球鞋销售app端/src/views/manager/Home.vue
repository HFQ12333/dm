<template>
  <div class="home">
    <!-- 头部图片 -->
    <header class="header">
      <!-- <img src="../../assets/home.jpg" alt=""> -->
      <van-swipe class="my-swipe" :autoplay="2000" indicator-color="white">
      <van-swipe-item v-for="item in imagesbox" :key="item.id">
          <img :src="item.idView" class="image" size="100% 100%">
      </van-swipe-item>
      </van-swipe>
    </header>
    <!-- 内容区域 -->
    <div>
      <!-- 分类 6个-->
      <van-grid :column-num="2">
        <van-grid-item
          v-for="value in categories"
          :key="value.id"
          :icon="value.icon"
          :text="value.name"
          @click="go"
        />
      </van-grid>
      <!-- 产品 n个-->
      <briup-product-item
        @click="toBuyHandler(p)"
        v-for="p in products"
        :key="p.id" 
        :data="p"
        class="liebiao"
        >
      </briup-product-item>
      <!-- /产品 -->
    </div>
   
  </div>
</template>

<script>
import {get,post} from '../../http/axios';
export default {
  data(){
    return {
      categories:[],
      products:[],
       imagesbox:[{id:0,idView:"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603869660317&di=69b9fcb892fb05e9485dcbaf0085fefe&imgtype=0&src=http%3A%2F%2Fimg3.tbcdn.cn%2Ftfscom%2Fi2%2F821973913%2FTB2gwUsbrFlpuFjy0FgXXbRBVXa_%2521%2521821973913.gif"},   
           {id:1,idView:"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603869598808&di=656237b36d8408dd8e30e05f593998c5&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20130530%2FImg377553343.jpg"},
          {id:2,idView:"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603869713658&di=18e3e63c9a4f86d97318626f7ff13d61&imgtype=0&src=http%3A%2F%2Fi1.letvimg.com%2Flc03_qmt%2F201708%2F23%2F07%2F52%2Fvidxsujfcl6j%2F169.jpg"},
          {id:3,idView:"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3867519936,1533951642&fm=26&gp=0.jpg"}],
           active: 0
    }
  },
  created(){
    // 查询栏目信息
    this.loadCategories();
    // 查询产品
    this.loadProducts();
  },
  methods:{
    toBuyHandler(p){
      // 跳转到订单确认页面，并且携带数据p
      this.$router.push({
        path:"/manager/order_confirm",
        query:p
      })
    },
    // 加载栏目信息
    loadCategories(){
      let url = "/category/findAll";
      get(url).then((response)=>{
        // 将查询结果，数组中的前6个元素获取到
        this.categories = response.data.slice(0,4);
      })
    },
    // 加载产品信息
    loadProducts(){
      let url = "/product/query"
      let params = { page:0, pageSize:1000 }
      post(url,params).then((response)=>{
        this.products = response.data.list; 
      })
    },
    go(){
      this.$router.push('-1')
    },
    onSearchIcon(){
        console.log('搜索了')  
      },
      onSearchIcon2(e){
        if(e.keyCode == 13){
          this.onSearchIcon();
        }
      },
      formSubmit(){
        return false;
      }
  }
}
</script>
<style scoped>
.home {
  padding-bottom: 60px;
}
.header {
  height: 188px;
  overflow: hidden;
}
.header img {
  width: 100%;
}
.image{
  height: 188px;
}
.sousuo{
  text-align: center;
  border: 2px solid rgb(125, 108, 146);
}
</style>