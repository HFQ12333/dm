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
    waiter:"",
    waiters:[],
    total:null,
    
  },
  mutations: {
    SET_WAITERS(state,waiters){
      state.waiters = waiters
      console.log(waiters)
    },
    SET_TOTAL(state,total){
      state.total=total
    },
    SET_WAITER(state,waiter){
      state.waiter = waiter;
    },
  },
  actions: {
    findAll(context){
      axios.get("/waiter/findAll").then(res=>{
          
        context.commit("SET_WAITERS",res.data)
        console.log(res.data)
      })
    },
    querywaiter({commit},data){
      axios.post("/waiter/query",data).then(res=>{
        commit("SET_WAITERS",res.data.list)
        commit("SET_TOTAL",res.data.total)
      })
    },
    savewaiter({dispatch},data){
      axios.post("/waiter/saveOrUpdate",data).then(res=>{
        dispatch('querywaiter',{page:0,pageSize:5})
      })
    },
    delwaiterById({dispatch},id){
      axios.get("/waiter/deleteById?id="+id).then(res=>{
        dispatch("querywaiter",{page:0,pageSize:5})
      })
    },
    findwaiterById({commit},id){
      axios.get("/waiter/findWaiterById?id="+id).then(res=>{
        // console.log(res)
        commit("SET_WAITERS",res.data)
      })
    }
  }
}