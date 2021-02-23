<template>
  <!-- 年级管理 -->
  <div class="grade_list">
    <!--按钮-->
    <div class="btns">
      <el-row>
        <el-col :span="16">
         <el-form :inline="true" size="small">
           <el-form-item label="名称">
              <el-input clearable v-model="param.name"></el-input>
           </el-form-item>
           <el-form-item>
              <el-button @click="loadgrades">搜索</el-button>
           </el-form-item>
         </el-form>
        </el-col>
        <el-col :span="8" style="text-align:right">
          <el-button type="primary" size="small" @click="toAdd">新增</el-button>
        </el-col>
    </el-row>
    </div>
    <!--按钮结束-->
    <!--表格-->
    <el-table :data="grades.list" size="small" v-loading="loading">
      <el-table-column type="index" :index="1" label="序号"/>
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="introduce" label="介绍" />
      <el-table-column prop="school.name" label="学校"  />
      <el-table-column prop="school.introduce" label="简介"  />

      <el-table-column label="操作" align="center" width="160">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click.prevent="deleteHandler(scope.row.id)">删除</el-button>
          <el-button type="text" size="small" @click.prevent="toEdit(scope.row)" >修改</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--表格结束-->
    <!-- 分页 -->
    <el-pagination background layout="prev, pager, next, sizes" small
      :total="grades.total"
      :page-size="grades.pageSize"
      :current-page="grades.page"
      :page-sizes="[5,10,15,20]"
      @size-change="handleSizeChange"
      @current-change="currentChangeHandler">
    </el-pagination>
    <!-- 分页 -->
    <!-- 抽屉 -->
    <Briupdrawer :drawer-visible="visible" :title="title" width="40%" @on-change-visible="handlerVisibleChange" >
      <div slot="content">
        <el-form :model="form" ref="gradeform" :rules="rules" label-width="80px">
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
    
  </div>
</template>
<script>
import { get, post, del } from '@/utils/request'
import _ from 'lodash'
import { valid } from 'mockjs'
export default {
  data() {
    return {
      form: {},
      setfrom:{},
      loading:false,
      visible: false,
      setChargevisible:false,
      title: '添加年级信息',
      grade: {},
      grades: [],
      grade:[],
      school:[],
      param:{
        page:1,
        pageSize:5
      },
      props: { multiple: true, value: 'id', label: 'name', emitPath: false },
      rules: {
        name: [ { required: true, message: '请输入年级名称', trigger: 'change' } ],
        schoolId: [ { required: true, message: '请选择所属学校', trigger: 'change' } ],
      },
    }
  },
  created() {
    this.loadgrades()
    this.loadSchool()
  },
  methods: {
    //查询所有的学校
    loadSchool(){
       get('/school/pageQuery',{
        page:1,
        pageSize:10000
      })
        .then(response => {
          this.school = response.data.list
        })
    },
    //查询所有年级
    loadGrade(){
       get('/grade/pageQuery',{
        page:1,
        pageSize:100000
      })
      .then(response => {
        this.grade = response.data.list
      })
    },
    //增加修改抽屉确认按钮处理函数
    submitHandler() {
      this.$refs['gradeform'].validate((valid) => {
        if (valid) {
          const url = '/grade/saveOrUpdate'
          post(url, this.form)
            .then(response => {
              this.visible = false
              this.$message({ message: response.message, type: 'success' })
              this.loadgrades()
            })
        } else {
          return false
        }
      })
    },
    toAdd() {
      this.title="新增年级信息";
      this.form = {}
      this.visible = true
    },
    //分类查询所有的年级
    loadgrades() {
      this.loading = true;
      get('/grade/pageQuery',this.param)
        .then(response => {
          this.grades = response.data
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
        const url = '/grade/deleteById'
        get(url, { id }).then(response => {
          this.$message({ type: 'success', message: response.message })
          this.loadgrades()
        })
      })
    },
    //去修改处理函数
    toEdit(row) {
      this.title="修改年级信息"
      this.form =_.clone(row)
      //显示抽屉
      this.visible = true
    },
    // 当前页发生改变
    currentChangeHandler(page){
      this.param.page = page;
      this.loadgrades();
    },
    // 重置表单校验
    clearValidate() {
      this.$refs['gradeform'].resetFields();
    },
    //处理visible变化的钩子函数,解决抽屉无法收缩的bug
    handlerVisibleChange(val){
      this.visible=val
    },
    //处理分页大小变化的函数
    handleSizeChange(pageSize){
    //当前页为第一页
    this.page=1;
    //页面大小用户选择的页面
    this.param.pageSize=pageSize;
    this.loadgrades();
    },
  }
}
</script>
