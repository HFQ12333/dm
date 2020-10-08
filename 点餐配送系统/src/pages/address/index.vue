<template>
  <div>
    <el-button type="primary" size="small" @click="toAddHandler">添加地址</el-button>
    <el-input v-model="list.status" clearable placeholder="请输入地址" style="width:200px;display:inline-block" />
    <el-input v-model="list.telephone" clearable placeholder="请输入省份" style="width:200px;display:inline-block" />
    <el-button type="primary" size="small" @click="searchHandle">查询</el-button>

    <el-table :data="customers" border style="width: 100%">
      <el-table-column prop="id" label="ID" width="120" />
      <el-table-column prop="province" label="省份" />
      <el-table-column prop="city" label="城市" />
      <el-table-column prop="area" label="地区" />
      <el-table-column prop="address" label="详细地址" />
      <el-table-column
        fixed="right"
        label="操作"
        width="200"
      >
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="detailsHandle(scope.row.id)">详情</el-button>
          <el-button type="text" size="small" @click="editHandler(scope.row)">编辑</el-button>
          <el-button type="text" size="small" @click="delHandler(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 表格结束 -->
    <!-- 分页 -->
    <el-pagination
      background
      layout="prev, pager, next"
      :total="total"
      :page-size="this.list.pageSize"
      @current-change="changePage"
    />
    <!-- 分页结束 -->
    <!-- 模态框开始 -->
    <el-dialog :title="title" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="用户名">
          <el-input v-model="form.username" clearable placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="省份">
          <el-input v-model="form.telephone" clearable placeholder="请输入省份" />
        </el-form-item>
        <el-form-item label="地址">
          <el-select v-model="form.status" clearable placeholder="请选择地址">
            <el-option label="江苏省" value="江苏省" />
            <el-option label="浙江省" value="浙江省" />
            <el-option label="安徽省" value="安徽省" />
            <el-option label="广东省" value="广东省" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit()">确 定</el-button>
      </div>
    </el-dialog>
  <!-- 模态框结束 -->
  </div>
</template>
<script>
import { mapState, mapActions } from 'vuex'
export default {
  data() {
    return {
      list: {
        page: 0,
        pageSize: 5
      },
      // 模态框隐藏
      dialogFormVisible: false,
      form: {
      },
      tltle: ''
    }
  },
  created() {
    // this.findAll();
    // 分页查询地址信息
    this.queryCustomer(this.list)
  },
  computed: {
    ...mapState('address', ['customers', 'total'])
  },
  methods: {
    ...mapActions('address', ['findAll', 'queryCustomer', 'saveCustomer', 'delCustomerById']),
    changePage(page) {
      this.list.page = page - 1
      this.queryCustomer(this.list)
    },
    toAddHandler() {
      // 控制模态框打开
      this.dialogFormVisible = true
      this.title = '添加地址'
      // 清空表单
      this.form = {}
    },
    submit(form) {
      // 调用store中的方法提交请求
      this.saveCustomer(this.form)
      // 关闭模态框
      this.dialogFormVisible = false
    },
    delHandler(id) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.delCustomerById(id)
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    editHandler(row) {
      // 打开模态框
      this.dialogFormVisible = true
      this.title = '修改地址信息'
      this.form = row
    },
    searchHandle() {
      this.queryCustomer(this.list)
    },
    detailsHandle(id) {
      this.$router.push({ name: 'details', params: { id: id }})
    }
  }
}
</script>
<style scoped>

</style>
