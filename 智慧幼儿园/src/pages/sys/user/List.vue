<template>
  <!-- 用户管理 -->
  <div class="user_list">
    <div class="btns">
      <el-form :inline="true" size="small">
        <el-button type="primary" size="small" @click="toAdd">添加</el-button>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <el-form-item>
          <el-input placeholder="请输入用户名" clearable v-model="param.username"></el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="param.rolename" placeholder="请选择角色" clearable>
            <el-option v-for="r in roles" :key="r.id" :label="r.name" :value="r.name"></el-option>
          </el-select>
        </el-form-item>
        <el-button type="text" size="small" @click="loadUsers">搜索</el-button>
      </el-form>
    </div>
    <el-table :data="users.list" size="small" v-loading="loading">
      <el-table-column type="index" :index="1" label="序号"/>
      <el-table-column prop="username" label="用户名" width="120"/>
      <el-table-column prop="realname" label="姓名" width="120"/>
      <el-table-column label="角色" width="100">
        <template slot-scope="scope">
          {{ scope.row.role_name }}
        </template>
      </el-table-column>
      <el-table-column prop="gender" label="性别" width="60">
        <template slot-scope="scope">
          <span v-if="scope.row.gender ==='male'">男</span>
          <span v-else>女</span>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="60" />
      <el-table-column prop="telephone" label="手机号" />

      <el-table-column label="操作" align="center" width="160">
        <template slot-scope="scope">
          <a type="text" size="small" @click.prevent="toSetRole(scope.row)">设置</a>
          <a type="text" size="small" @click.prevent="deleteHandler(scope.row.id)">移除</a>
          <a type="text" size="small" @click.prevent="toDetails(scope.row)">详情</a>
          <a type="text" size="small" @click.prevent="toEdit(scope.row)">修改</a>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination background layout="prev, pager, next" small
      :hide-on-single-page="true"
      :total="users.total"
      :page-size="users.pageSize"
      :current-page="users.page"
      @current-change="currentChangeHandler">
    </el-pagination>
    <!-- 分页 -->
    <!-- 模态框 -->
    <el-dialog :title="title" @close="clearValidate" :visible.sync="visible">
      <el-form ref="user_form" :model="form" :rules="rules">
        <el-form-item label="用户名" label-width="80px" prop="username">
          <el-input v-model="form.username" autocomplete="off" />
        </el-form-item>
        <el-form-item label="姓名" label-width="80px" prop="realname">
          <el-input v-model="form.realname" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码" label-width="80px" prop="password">
          <el-input v-model="form.password" autocomplete="off" />
        </el-form-item>
        <el-form-item label="性别" label-width="80px" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="male">男</el-radio>
            <el-radio label="female">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号" label-width="80px">
          <el-input v-model="form.telephone" autocomplete="off" />
        </el-form-item>
        <el-form-item label="出生日期" label-width="80px">
          <el-date-picker v-model="form.birth" value-format="timestamp" type="date" placeholder="选择日期" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="visible = false">取 消</el-button>
        <el-button type="primary" size="small" @click="saveUserHandler">确 定</el-button>
      </div>
    </el-dialog>
    <!-- /模态框 -->
    <!-- 角色模态框 -->
    <el-dialog title="设置角色" :visible.sync="role_visible">
      <el-form :model="user">
        <el-form-item label="用户名" label-width="80px">
          {{ user.realname }}
        </el-form-item>
        <el-form-item label="角色" label-width="80px">
          <el-cascader v-model="user.roles" :options="roles" :props="props" clearable />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="role_visible = false">取 消</el-button>
        <el-button type="primary" size="small" @click="setRolesHandler">确 定</el-button>
      </div>
    </el-dialog>
    <!-- /模态框 -->
  </div>
</template>
<script>
import { get, post, del } from '@/utils/request'

import qs from 'querystring'
export default {
  data() {
    return {
      form: {},
      loading:false,
      visible: false,
      role_visible: false,
      title: '添加用户',
      user: {},
      users: [],
      roles: [],
      param:{
        page:1,
        pageSize:13
      },
      props: { multiple: true, value: 'id', label: 'name', emitPath: false },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'change' }
        ],
        realname: [
          { required: true, message: '请输入姓名', trigger: 'change' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'change' }
        ],
        gender: [
          { required: true, message: '请选择性别', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.loadUsers()
    this.loadRoles()
  },
  methods: {
    loadRoles() {
      get('/role/findAll')
        .then(response => {
          this.roles = response.data
        })
    },
    saveUserHandler() {
      this.$refs['user_form'].validate((valid) => {
        if (valid) {
          const url = '/baseUser/saveOrUpdate'
          post(url, this.form)
            .then(response => {
              this.visible = false
              this.$message({ message: response.message, type: 'success' })
              this.loadUsers()
            })
        } else {
          return false
        }
      })
    },
    toAdd() {
      this.form = {}
      this.visible = true
    },
    loadUsers() {
      this.loading = true;
      get('/baseUser/pageQuery',this.param)
        .then(response => {
          this.users = response.data
          this.loading = false;
        })
    },
    deleteHandler(id) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const url = '/baseUser/deleteById'
        get(url, { id }).then(response => {
          this.$message({ type: 'success', message: response.message })
          this.loadUsers()
        })
      })
    },
    toEdit(record) {
      this.form = record
      this.visible = true
    },
    toSetRole(record) {
      // 初始化角色
      // record.roles = [];
      this.user = record
      this.role_visible = true
    },
    toDetails(row) {
      this.$router.push({
        // path:'/sys/user/Details',
        name: '_sys_user_Details',
        query: row
      })
    },
    setRolesHandler() {
      post('/baseUser/setRoles', {
        id: this.user.id,
        roles: this.user.roles
      })
        .then(response => {
          this.role_visible = false
          this.$message({ message: response.message, type: 'success' })
          this.loadUsers()
        })
    },
    // 当前页发生改变
    currentChangeHandler(page){
      this.param.page = page;
      this.loadUsers();
    },
    // 重置表单校验
    clearValidate() {
      this.$refs['user_form'].resetFields();
    },
  }
}
</script>
