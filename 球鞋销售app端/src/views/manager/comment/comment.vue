<template>
  <section class="cart">
    <div class="box">
            <briup-comment-item 
            v-for="p in products"
            :key="p.id" 
            :data="p">
          </briup-comment-item>
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
    this.loadProducts();
  },
   mounted() {},
  methods:{
    loadProducts(){
      let url = "/comment/findAll"
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
    }
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
  padding-top: 40px;
}
</style>
