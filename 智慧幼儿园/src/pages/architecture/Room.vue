<!--
  功能：功能描述
  作者：hufq
  邮箱：hufq@briup.com
  时间：2020年11月27日 15:30:00
  版本：v1.0
  修改记录：
  修改内容：
  修改人员：
  修改时间：
-->
<template>
  <!-- 教室管理 -->
  <div class="room_list">
    <!--按钮区域-->
    <div class="btns">
        <el-row>
          <el-col :span="16">
            <el-dropdown>
              <span class="el-dropdown-link">
                搜索<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-button @click="handleCommand">班级名称</el-button>
                <el-button @click="handleCommand2">学校名称</el-button>
              </el-dropdown-menu>
            </el-dropdown>
          </el-col>
          <el-col :span="8" style="text-align:right">
            <el-button type="primary" size="small" @click="toAdd">新增</el-button>
          </el-col>
        </el-row>
    </div>
    <!--按钮区域结束-->
    <!--表格-->
    <el-table :data="rooms.list" size="small" v-loading="loading">
      <el-table-column type="index" :index="1" label="序号"/>
      <el-table-column prop="name" label="名称" width="160"/>
      <el-table-column prop="introduce" label="介绍" width="600"/>
      <el-table-column prop="schoolId" label="学校id"/>
      <el-table-column label="操作" align="center" width="100" fixed="right">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click.prevent="deleteHandler(scope.row.id)">删除</el-button>
          <el-button type="text" size="mini" @click.prevent="toEdit(scope.row)">修改</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--表格结束-->
    <!-- 分页 -->
    <div class="page">
      <el-pagination background layout="prev, pager, next, sizes" small
        :total="rooms.total"
        :page-size="rooms.pageSize"
        :current-page="rooms.page"
        :page-sizes="[5,10,15,20]"
        @size-change="handleSizeChange"
        @current-change="currentChangeHandler">
      </el-pagination>
    </div>
    <!-- 分页 -->
    <!-- 抽屉 -->
    <Briupdrawer :drawer-visible="visible" :title="title" width="40%" @on-change-visible="handlerVisibleChange" >
      <div slot="content">
        <el-form :model="form" ref="roomform" :rules="rules" label-width="80px">
          <el-form-item label="名称" prop="name">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="介绍" label-width="80px" prop="introduce">
            <el-input v-model="form.introduce" autocomplete="off"  placeholder="请输入介绍" />
          </el-form-item>
          <el-form-item label="学校" label-width="80px" prop="school.name">
            <el-select v-model="form.schoolId" placeholder="请选择学校" clearable>
              <el-option v-for="s in school" :key="s.id" :label="s.name" :value="s.id"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="visible = false">取消</el-button>
        <el-button size="small" type="primary" @click="submitHandler">确定</el-button>
      </span>
    </Briupdrawer>
    <!-- 抽屉结束 -->
    <!-- 搜索抽屉 -->
    <Briupdrawer :drawer-visible="visible1" :title="title1" width="25%" @on-change-visible="handlerVisiblesousuo" >
      <div slot="content">
        <el-form :inline="true" size="small">
          <el-form-item label="名称">
            <el-input clearable v-model="param.name"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="loadrooms">搜索</el-button>
          </el-form-item>
          </el-form>
      </div>
    </Briupdrawer>
    <!-- 搜索抽屉结束 -->
    <!-- 搜索抽屉 -->
    <Briupdrawer :drawer-visible="visible2" :title="title2" width="27%" @on-change-visible="handlerVisiblesousuo2" >
      <div slot="content">
        <el-form :inline="true" size="small">
          <el-form-item label="学校名称">
            <el-input clearable v-model="param.schoolId"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="loadrooms">搜索</el-button>
          </el-form-item>
        </el-form>
      </div>
    </Briupdrawer>
    <!-- 搜索抽屉结束 -->
  </div>
</template>
<script>
import { get, post, del } from '@/utils/request'
import _ from 'lodash'
import qs from 'querystring'
import { valid } from 'mockjs'
export default {
  data() {
    return {
      form: {},
      form1: {},
      form2: {},
      setfrom:{},
      loading:false,
      visible: false,
      visible1: false,
      visible2: false,
      title: '添加教室信息',
      title1: '根据班级名称进行搜索',
      title2: '根据学校名称进行搜索',
      room: {},
      res:[],
      rooms: [],
      school:[],
      param:{
        page:1,
        pageSize:10
      },
      props: { multiple: true, value: 'id', label: 'name', emitPath: false },
      rules: {
        name: [ { required: true, message: '请输入教室名称', trigger: 'change' } ],
        introduce: [ { required: true, message: '请输入介绍', trigger: 'change' } ],
        schoolId: [ { required: true, message: '请选择所属学校', trigger: 'change' } ],
      },
    }
  },
  created() {
    this.loadrooms()
    this.loadSchool()
  },
  methods: {
    // 下拉搜索框
    handleCommand() {
      this.title1="根据班级名称进行搜索";
      this.form1 = {}
      this.visible1 = true
      },
    handleCommand2() {
      this.title2="根据学校名称进行搜索";
      this.form2 = {}
      this.visible2 = true
      },
    // 新增按钮
    toAdd() {
      this.title="新增教室信息";
      this.form = {}
      this.visible = true 
    },
    //查询所有的教室
    loadRoom(){
      get('/room/pageQuery',{
        page:1,
        pageSize:100000
      })
        .then(response => {
          this.room = response.data.list
        })
    },
    //查询所有的学校
    loadSchool(){
       get('/school/pageQuery',{
        page:1,
        pageSize:100000
      })
        .then(response => {
          this.school = response.data.list
        })
    },
    //增加修改抽屉确认按钮处理函数
    submitHandler() {
      this.$refs['roomform'].validate((valid) => {
        if (valid) {
          const url = '/room/saveOrUpdate'
          post(url, this.form)
            .then(response => {
              this.visible = false
              this.$message({ message: response.message, type: 'success' })
              this.loadrooms()
            })
        } else {
          return false
        }
      })
    },
    loadrooms() {
      this.loading = true;
      get('/room/pageQuery',this.param)
        .then(response => {
          this.rooms = response.data
          console.log(response.data)
          this.loading = false;
        })
    },
    deleteHandler(id) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const url = '/room/deleteById'
        del(url, { id }).then(response => {
          this.$message({ type: 'success', message: response.message })
          this.loadrooms()
        })
      })
    },
    //去修改处理函数
    toEdit(row) {
      this.title="修改教室信息"
      this.form =_.clone(row)
      //显示抽屉
      this.visible = true
    },
    // 当前页发生改变
    currentChangeHandler(page){
      this.param.page = page;
      this.loadrooms();
    },
    // 重置表单校验
    clearValidate() {
      this.$refs['roomform'].resetFields();
    },
    // 重置表单校验
    clearValidate() {
      this.$refs['chargefrom'].resetFields();
    },
    //处理visible变化的钩子函数,解决抽屉无法收缩的bug
    handlerVisibleChange(val){
      this.visible=val
    },
    //处理搜索变化的钩子函数,解决抽屉无法收缩的bug
    handlerVisiblesousuo(val){
      this.visible1=val
    },
    //处理搜索变化的钩子函数,解决抽屉无法收缩的bug
    handlerVisiblesousuo2(val){
      this.visible2=val
    },
    //处理分页大小变化的函数
    handleSizeChange(pageSize){
    //当前页为第一页
    this.page=1;
    //页面大小用户选择的页面
    this.param.pageSize=pageSize;
    this.loadrooms();
    },
  }
}
</script>
<style>
  .el-dropdown-link {
    cursor: pointer;
    color: #1071f0;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
</style>