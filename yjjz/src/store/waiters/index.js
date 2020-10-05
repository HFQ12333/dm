import { get, post } from '../../utils/axios'
import Vue from 'vue'
import ElementUI from 'element-ui'
Vue.use(ElementUI)

const waiters = {
  // 在使用了仓库名称时，需要开启命名空间
  namespaced: true,
  state: function() {
    return {
      list: null,
      total: 0,
      listQuery: {
        page: 1,
        limit: 5
      },
      dialogVisible: false, // 控制模态框是否显示
      waiter_info: {}, // 当前编辑的员工数据
      imageUrl: ''
    }
  },
  actions: {
    fetchData(context) {
      // get("/waiter/findAll").then((data)=>{
      //   context.state.list = data.data;
      //   console.log(data.data);
      // })
      post('/waiter/query', {
        page: context.state.listQuery.page - 1,
        pageSize: context.state.listQuery.limit
      }).then((data) => {
        context.state.list = data.data.list
        context.state.total = data.data.total
      })
    },
    handleAdd(context) {
      context.state.waiter_info = {}
      context.state.dialogVisible = true
    },
    handleClose(context) {
      context.state.dialogVisible = false
    },
    handleSave(context) {
      post('/waiter/saveOrUpdate', context.state.waiter_info).then((data) => {
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
      get('/waiter/deleteById?id=' + id).then((data) => {
        context.dispatch('fetchData') // 操作完成
        ElementUI.Notification({
          title: '删除',
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
      })
    },
    // 上传图片
    handleAvatarSuccess(context, res) {
      this.imageUrl = 'http://121.199.29.84:8888/group1'
      context.state.imageUrl += imgurl + res.data.id
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    }
  }
}
export default waiters
