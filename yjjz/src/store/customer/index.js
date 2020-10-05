import { get, post } from '../../utils/axios'
import Vue from 'vue'
import ElementUI from 'element-ui'
Vue.use(ElementUI)

const customer = {
  // 在使用了仓库名称时，需要开启命名空间
  namespaced: true,
  state: function() {
    return {
      list: null,
      total: 0,
      listQuery: {
        page: 1,
        limit: 8
      },
      dialogVisible: false,
      customer_info: {},
      id_info: {}
    }
  },
  actions: {
    fetchData(context) {
      // get("/waiter/findAll").then((data)=>{
      //   context.state.list = data.data;
      //   console.log(data.data);
      // })
      post('/customer/query', {
        page: context.state.listQuery.page - 1,
        pageSize: context.state.listQuery.limit
      }).then((data) => {
        context.state.list = data.data.list
        context.state.total = data.data.total
      })
    },
    handleAdd(context) {
      context.state.customer_info = {}
      context.state.id_info = {}
      context.state.dialogVisible = true
    },
    handleDelAll(context) {
      alert('确定全部删除吗，删除后不可恢复数据！')
    },
    handleClose(context) {
      context.state.dialogVisible = false
    },
    handleSave(context) {
      post('customer/saveOrUpdate', context.state.customer_info).then((data) => {
        context.dispatch('fetchData') // 操作完成
        ElementUI.Notification({
          title: '保存',
          message: '保存成功',
          type: 'success',
          duration: 2000
        })
      })
      context.state.dialogVisible = false
    },
    handleEdit(context, data) {
      context.state.waiter_info = data
      context.state.dialogVisible = true
    },
    handleDelete(context, id) {
      get('/customer/deleteById?id=' + id).then((data) => {
        context.dispatch('fetchData') // 操作完成
        ElementUI.Notification({
          title: '删除',
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
      })
    }
  }
}
export default customer
