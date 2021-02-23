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
    order:"",
    orders:[],
    total:null,
    
  },
  mutations: {
    SET_ORDERS(state,orders){
      state.orders = orders
      console.log(orders)
    },
    SET_TOTAL(state,total){
      state.total=total
    },
    SET_ORDER(state,order){
      state.order = order;
    },
  },
  actions: {
    findAll(context){
      axios.get("/order/findAll").then(res=>{
          
        context.commit("SET_ORDERS",res.data)
        console.log(res.data)
      })
    },
  
    queryorder({commit},data){
      axios.post("/order/queryPage",data).then(res=>{
        commit("SET_ORDERS",res.data.list)
        commit("SET_TOTAL",res.data.total)
      })
    },
    saveorder({dispatch},data){
      axios.post("/order/saveOrUpdate",data).then(res=>{
        dispatch('queryorder',{page:0,pageSize:5})
      })
    },
    confirmOrder({dispatch},id){
      axios.get("/order/confirmOrder?orderId="+id).then(res=>{
        dispatch("queryorder",{page:0,pageSize:5})
      })
    },
    cancelSendOrder({dispatch},id){
        axios.get("/order/cancelSendOrder?orderId="+id).then(res=>{
          dispatch("queryorder",{page:0,pageSize:5})
        })
      },
    findorderById({commit},id){
      axios.get("/order/findById?id="+id).then(res=>{
        commit("SET_ORDER",res.data)
      })
    }
  }
}