<template>
  <div class="app-container">
    <el-button @click="handleAdd()">新增</el-button>
    <!-- 用户数据列表 -->
    <el-table
      :data="list"
      stripe
      style="width: 100%"
    >
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
          <el-button
            size="mini"
            @click="handleAvatarSuccess(scope.row.id)"
          >图片</el-button>
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
      <p>用户名：<el-input v-model="waiter_info.username" placeholder="请输入内容" /></p>
      <p>密码：<el-input v-model="waiter_info.password" placeholder="请输入内容" /></p>
      <p>手机号：<el-input v-model="waiter_info.telephone" placeholder="请输入内容" /></p>
      <!-- 图片上传 -->
      <el-upload
        class="avatar-uploader"
        action="http://121.199.29.84:8001/file/upload"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload"
      >
        <img v-if="imageUrl" :src="imageUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon" />
      </el-upload>
      <!-- /图片上传 -->
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
    ...mapState('waiters', ['list', 'total', 'listQuery', 'dialogVisible', 'waiter_info'])
  },
  created() {
    this.fetchData()
  },
  methods: {
    ...mapActions('waiters', ['fetchData', 'handleAdd', 'handleClose',
      'handleSave', 'handleEdit', 'handleDelete', 'handleAvatarSuccess',
      'beforeAvatarUpload'])
  }
}
</script>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
