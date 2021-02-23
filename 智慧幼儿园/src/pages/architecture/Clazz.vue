<template>
  <!-- 班级管理 -->
  <div class="clazz_list">
    <!--按钮-->
    <div class="btns">
      <el-row>
        <el-col :span="16">
         <el-form :inline="true" size="small">
           <el-form-item label="名称">
              <el-input clearable v-model="param.name"></el-input>
           </el-form-item>
           <el-form-item>
              <el-button @click="loadClazzs">搜索</el-button>
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
    <el-table :data="clazzs.list" size="small" v-loading="loading">
      <el-table-column type="index" :index="1" label="序号" fixed="left" />
      <el-table-column prop="name" label="名称" width="70" fixed="left" />
      <el-table-column prop="introduce" label="介绍" width="300"/>
      <el-table-column prop="school.name" label="学校"  />
      <el-table-column prop="school.introduce" label="学校简介" width="200" />
      <el-table-column prop="grade.name" label="年级"  />
      <el-table-column prop="room.name" label="教室"  />
      <el-table-column prop="charge.realname" label="负责人"  />

      <el-table-column label="操作" align="center" width="180" fixed="right"  >
        <template slot-scope="scope">
          <el-button type="text" size="small" @click.prevent="deleteHandler(scope.row.id)">删除</el-button>
          <el-button type="text" size="small" @click.prevent="toEdit(scope.row)" >修改</el-button>
          <el-button type="text" size="small" @click.prevent="toAddchargeId(scope.row)" >指定负责人</el-button>

        </template>
      </el-table-column>
    </el-table>
    <!--表格结束-->
    <!-- 分页 -->
    <div class="page"></div>
    <el-pagination background layout="prev, pager, next, sizes" small
      :total="clazzs.total"
      :page-size="clazzs.pageSize"
      :current-page="clazzs.page"
      :page-sizes="[5,10,15,20]"
      @size-change="handleSizeChange"
      @current-change="currentChangeHandler">
    </el-pagination>
    <!-- 分页 -->
    <!-- 抽屉 -->
    <Briupdrawer :drawer-visible="visible" :title="title" width="40%" @on-change-visible="handlerVisibleChange" >
      <div slot="content">
        <el-form :model="form" ref="clazzform" :rules="rules" label-width="80px">
          <el-form-item label="名称" prop="name">
            <el-input v-model="form.name"  placeholder="请输入班级名称"></el-input>
          </el-form-item>
          <el-form-item label="介绍" label-width="80px" prop="introduce">
          <el-input v-model="form.introduce" autocomplete="off"  placeholder="请输入介绍" />
          </el-form-item>          
          <el-form-item label="学校" label-width="80px" prop="name" >
          <el-select @change="selectChanged" v-model="form.schoolId"  clearable>
            <el-option v-for="s in school" :key="s.id" :label="s.name" :value="s.id"></el-option>
          </el-select>
          </el-form-item>
          <el-form-item label="年级" label-width="80px" prop="name" >
          <el-select v-model="form.gradeId"  clearable>
            <el-option v-for="g in grade" :key="g.id" :label="g.name" :value="g.id" ></el-option>
          </el-select>
          </el-form-item>
          <el-form-item label="教室" label-width="80px" prop="name">
          <el-select v-model="form.roomId" placeholder="请选择教室" clearable>
            <el-option v-for="r in room" :key="r.id" :label="r.name" :value="r.id"></el-option>
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
    <!--设置负责人抽屉-->
    <!-- setChargevisible -->
    <Briupdrawer :drawer-visible="setChargevisible" :title="title1" width="40%" @on-change-visile="handlersetcVisibleChange" >
    <div slot="content">
      <el-form :model="setfrom" ref="chargefrom"  label-width="80px">
        <el-form-item label="负责人" label-width="80px" prop="charge.realname">
          <el-select v-model="setfrom.chargeId" placeholder="请选择负责人" clearable>
            <el-option v-for="c in charge" :key="c.id" :label="c.realname" :value="c.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </div>
    <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="setChargevisible = false">取消</el-button>
        <el-button size="small" type="primary" @click="submitsetCHandler">确定</el-button>
    </span>
    </Briupdrawer>
    <!--抽屉结束-->
    
  </div>
</template>
<script>
import { get, post, del } from '@/utils/request'
import _ from 'lodash'
import { valid } from 'mockjs'
export default {
  data() {
    return {
      title1:"设置负责人",
      form: {},
      setfrom:{},
      loading:false,
      visible: false,
      setChargevisible:false,
      title: '添加班级信息',
      clazz: {},
      res:[],
      clazzs: [],
      grade:[],
      school:[],
      charge:[],
      room:[],
      param:{
        page:1,
        pageSize:5
      },
      props: { multiple: true, value: 'id', label: 'name', emitPath: false },
      rules: {
        name: [ { required: true, message: '请输入班级名称', trigger: 'change' } ],
      },
    }
  },
  created() {
    this.loadClazzs()
    this.loadGrade()
    this.loadSchool()
    this.loadCharge()
    this.loadRoom()
  },
  methods: {
    //查询学校对应的教室
    loadRoom(){
      get('/room/pageQuery',{
        page:1,
        pageSize:100000,
        schoolId:this.form.schoolId
      })
        .then(response => {
          this.room = response.data.list
        })
    },
    //查询所有的负责人
    loadCharge(){
    get('/baseUser/pageQuery',{
        page:1,
        pageSize:100000,
        rolename:'教师'
      })
        .then(response => {
       this.charge=response.data.list
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
    //学校选择变化后处理函数,查询对应的教室和年级
    selectChanged(){
        this.loadGrade();
        this.loadRoom();
    },
    //查询学校对应的年级
    loadGrade(){
       get('/grade/pageQuery',{
        page:1,
        pageSize:100000,
        schoolId:this.form.schoolId
      })
      .then(response => {
        this.grade = response.data.list
      })
    },
    //增加修改抽屉确认按钮处理函数
    submitHandler() {
      this.$refs['clazzform'].validate((valid) => {
        if (valid) {
          const url = '/clazz/saveOrUpdate'
          post(url, this.form)
            .then(response => {
              this.visible = false
              this.$message({ message: response.message, type: 'success' })
              this.loadClazzs()
            })
        } else {
          return false
        }
      })
    },
    //设置负责人抽屉处理函数
    submitsetCHandler(){
      this.$refs['chargefrom'].validate((valid)=>{
        if(valid){
          const url='/clazz/setCharge'
          post(url,this.setfrom)
          .then(response=>{
            this.setChargevisible=false
            this.$message({message:response.message,type:'success'})
            this.loadClazzs()
          })
        }
        else{
          return false
        }
      })
    },
    toAdd() {
      this.title="新增班级信息";
      this.form = {}
      this.visible = true
    },
    //分类查询所有的班级
    loadClazzs() {
      this.loading = true;
      get('/clazz/pageQuery',this.param)
        .then(response => {
          this.clazzs = response.data
          this.loading = false;
        })
    },
    deleteHandler(id) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const url = '/clazz/deleteById'
        get(url, { id }).then(response => {
          this.$message({ type: 'success', message: response.message })
          this.loadClazzs()
        })
      })
    },
    //指定负责人处理函数
    toAddchargeId(row){
      this.setfrom=_.clone(row)
      this.setChargevisible=true
    },
    //去修改处理函数
    toEdit(row) {
      this.title="修改班级信息"
      this.form =_.clone(row)
      if(this.form.roomId!==""&&this.form.gradeId!==""){
        this.form.roomId=""
        this.form.gradeId=""
      }
      //显示抽屉
      this.visible = true
    },
    // 当前页发生改变
    currentChangeHandler(page){
      this.param.page = page;
      this.loadClazzs();
    },
    // 重置表单校验
    clearValidate() {
      this.$refs['clazzform'].resetFields();
    },
    // 重置表单校验
    clearValidate() {
      this.$refs['chargefrom'].resetFields();
    },
    //处理visible变化的钩子函数,解决抽屉无法收缩的bug
    handlerVisibleChange(val){
      this.visible=val
    },
    //设置负责人处理visible变化的钩子函数,解决抽屉无法收缩的bug
    handlersetcVisibleChange(val){
      this.setChargevisible=val
    },
    //处理分页大小变化的函数
    handleSizeChange(pageSize){
    //当前页为第一页
    this.page=1;
    //页面大小用户选择的页面
    this.param.pageSize=pageSize;
    this.loadClazzs();
    },
  }
}
</script>
