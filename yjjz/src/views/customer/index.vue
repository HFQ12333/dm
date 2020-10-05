<template>
  <div class="app-container">
    <el-button @click="handleAdd()">新增</el-button>
    <el-button @click="handleDelAll()">批量删除</el-button>
    <!-- 用户数据列表 -->
    <el-table
      :data="list"
      stripe
      style="width: 100%"
    >
      <el-table-column
        prop="id"
        label="编号"
        width="180"
      />
      <el-table-column
        prop="username"
        label="姓名"
        width="180"
      />
      <el-table-column
        prop="telephone"
        label="手机号"
      />
      <el-table-column
        prop="idCard"
        label="身份证号"
      />
      <el-table-column
        prop="status"
        label="状态"
      />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit( scope.row)"
          >编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row.id)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- /用户数据列表 -->
    <!-- 分页 -->
    <template>
      <pagination
        :total="total"
        :page.sync="listQuery.page"
        :limit.sync="listQuery.limit"
        @pagination="fetchData"
      />
    </template>
    <!-- /分页 -->
    <!-- 模态框 -->
    <el-dialog
      title="保存员工信息"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <p>用户名：<el-input v-model="customer_info.username" placeholder="请输入内容" /></p>
      <p>密码：<el-input v-model="customer_info.password" placeholder="请输入内容" /></p>
      <p>手机号：<el-input v-model="customer_info.telephone" placeholder="请输入内容" /></p>
      <p>身份证号：<el-input v-model="customer_info.idCard" placeholder="请输入身份证号" /></p>
      <p>状态：<el-input v-model="customer_info.status" placeholder="请输入状态" /></p>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleSave()">确 定</el-button>
      </span>
    </el-dialog>
    <!-- /模态框 -->
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import { mapState, mapActions } from 'vuex'
export default {
  components: { Pagination },
  data() {
    return {
      // list: null,//表格的数据
      listLoading: false
    }
  },
  // 在仓库中的数据，获取时，必须卸载computed中
  computed: {
    ...mapState('customer', ['list', 'total', 'listQuery', 'dialogVisible', 'customer_info', 'id_info'])
  },
  created() {
    this.fetchData()
  },
  methods: {
    ...mapActions('customer', ['fetchData', 'handleAdd', 'handleClose', 'handleSave', 'handleEdit', 'handleDelete', 'handleDelAll'])
  }
}
</script>
