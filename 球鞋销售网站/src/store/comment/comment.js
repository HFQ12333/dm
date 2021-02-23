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
    comment:"",
    comments:[],
    total:null,
    
  },
  mutations: {
    SET_COMMENTS(state,comments){
      state.comments = comments
      console.log(comments)
    },
    SET_TOTAL(state,total){
      state.total=total
    },
    SET_COMMENT(state,comment){
      state.comment = comment;
    },
  },
  actions: {
    findAll(context){
      axios.get("/comment/findAll").then(res=>{
          
        context.commit("SET_COMMENTS",res.data)
        console.log(res.data)
      })
    },
  
    querycomment({commit},data){
      axios.post("/comment/query",data).then(res=>{
        commit("SET_COMMENTS",res.data.list)
        commit("SET_TOTAL",res.data.total)
      })
    },
    savecomment({dispatch},data){
      axios.post("/comment/saveOrUpdate",data).then(res=>{
        dispatch('querycomment',{page:0,pageSize:5})
      })
    },
    commentExamine({dispatch},id){
      axios.get("/comment/commentExamine?commentid="+id).then(res=>{
        dispatch("querycomment",{page:0,pageSize:5})
      })
    },
    commentRefuseExamine({dispatch},id){
        axios.get("/comment/commentRefuseExamine?commentid="+id).then(res=>{
          dispatch("querycomment",{page:0,pageSize:5})
        })
      },
    findcommentById({commit},id){
      axios.get("/comment/findById?id="+id).then(res=>{
        commit("SET_COMMENT",res.data)
      })
    }
  }
}