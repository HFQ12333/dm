<template>
  <section class="cart">
    <div class="box">
            <briup-message-item 
            v-for="p in products"
            :key="p.id" 
            :data="p">
          </briup-message-item>
    </div>
  </section>
</template>

<script>
import {get,post} from '../../../http/axios'
export default {
  name: 'Cart',
  data () {
    return {
      navRightShow: true,
      checked: false,
      // categories:[],
      products:[],
      list:{
					page:0,
					pageSize:5,
        },
         isAll: 0,
      allPrice: 0,
    }
  },
    created(){
    // 查询栏目信息
    // this.loadCategories();
    // 查询产品
    this.loadProducts();
  },
   mounted() {},
  methods:{
    // toBuyHandler(p){
    //   // 跳转到订单确认页面，并且携带数据p
    //   this.$router.push({
    //     path:"/manager/order_confirm",
    //     query:p
    //   })
    // },
    // 加载栏目信息
    // loadCategories(){
    //   let url = "/category/findAll";
    //   get(url).then((response)=>{
    //     // 将查询结果，数组中的前6个元素获取到
    //     this.categories = response.data.slice(0,4);
    //   })
    // },
    // 加载产品信息
    loadProducts(){
      let url = "/order/findAll"
      get(url).then((response)=>{
        this.products = response.data; 
      })
    },
    changePage(page){
				this.list.page = page - 1;
				this.queryCustomer(this.list);
      },
       delGoods(index, i) {
      var list = this.orderData[index].goodsList;
      list.splice(i, 1);
      if (list.length == 0) {
        this.orderData.splice(index, 1);
      }
      this.watchAll();
      this.countPrice();
    },

    // 选择商品
  }
}
</script>

<style scoped>
  .box {
  position: relative;
  width: 100%;
  height: 100%;
  padding: 15px;
  box-sizing: border-box;
  background: #fff;
  /* margin-bottom: 104px; */
  padding-top: 40px;
}
</style>
