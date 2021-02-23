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
    category:"",
    categorys:[],
    total:null,
  },
  mutations: {
    SET_CATEGORYS(state,categorys){
      state.categorys = categorys
    },
    SET_TOTAL(state,total){
      state.total=total
    },
    SET_CATEGORY(state,category){
      state.category = category;
    },
  },
  actions: {
    findAll(context){
      axios.get("/category/findAll").then(res=>{
        context.commit("SET_CATEGORYS",res.data)
      })
    },
    querycategory({commit},data){
      axios.post("/category/query",data).then(res=>{
        commit("SET_CATEGORYS",res.data.list)
        commit("SET_TOTAL",res.data.total)
      })
    },
    savecategory({dispatch},data){
      axios.post("/category/saveOrUpdate",data).then(res=>{
        dispatch('querycategory',{page:0,pageSize:5})
      })
    },
    delcategoryById({dispatch},id){
      axios.get("/category/deleteById?id="+id).then(res=>{
        dispatch("querycategory",{page:0,pageSize:5})
      })
    },
    findcategoryById({commit},id){
      axios.get("/category/findById?id="+id).then(res=>{
        commit("SET_CATEGORY",res.data)
      })
    },
  }
}