// 在Vuex中 需要处理网路请求 我们就需要导入axios 
// @ 以根目录的方式定义相对路径 想到与src/
import axios from '@/http/axios'

// 配置Vuex结构
export default {
  // vuex中的store分成模块来管理 
  // 需要在index.js中引入每一个Vuex模块进行配置 
  // 为了解决不同模块命名冲突的问题  
  // 将不同模块的namespaced 全部开启 之后 
  // 在不同的页面中再使用getters，actions，mutations，state的时候 
  // 需要根据不同的模块名来获取
  namespaced: true,

  state: {
    product:"",
    products:[],
    total:null,
    
  },
  mutations: {
    SET_PRODUCTS(state,products){
      state.products = products
      console.log(products)
    },
    SET_TOTAL(state,total){
      state.total=total
    },
    SET_PRODUCT(state,product){
      state.product = product;
    },
  },
  actions: {
    findAll(context){
      axios.get("/product/findAll").then(res=>{
        context.commit("SET_PRODUCTS",res.data)
      })
    },
    queryproduct({commit},data){
      axios.post("/product/query",data).then(res=>{
        commit("SET_PRODUCTS",res.data.list)
        commit("SET_TOTAL",res.data.total)
      })
    },
    saveproduct({dispatch},data){
      axios.post("/product/saveOrUpdate",data).then(res=>{
        dispatch('queryproduct',{page:0,pageSize:5})
      })
    },
    delproductById({dispatch},id){
      axios.get("/product/deleteById?id="+id).then(res=>{
        dispatch("queryproduct",{page:0,pageSize:5})
      })
    },
    findproductById({commit},id){
      axios.get("/product/findById?id="+id).then(res=>{
        commit("SET_PRODUCT",res.data)
      })
    }
  }
}