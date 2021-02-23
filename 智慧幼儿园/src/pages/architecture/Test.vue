<!--
  功能：功能描述
  作者：licy
  邮箱：licy@briup.com
  时间：2020年11月23日 15:37:09
  版本：v1.0
  修改记录：
  修改内容：
  修改人员：
  修改时间：
-->
<template>
  <div class='school_list'>
    <!-- 按钮区域 -->
    <div class="btns">
      <el-row>
        <el-col :span="16">
          <el-form inline size="small">
            <el-form-item label="名称">
              <el-input v-model="param.name" clearable></el-input>
            </el-form-item>
            <el-form-item>
              <el-button @click="loadSchools">搜索</el-button>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="8" style="text-align:right">
          <el-button type="primary" size="small" @click="toSaveHandler">新增</el-button>
        </el-col>
      </el-row>
    </div>
    <!-- 按钮区域结束 -->
    <!-- 表格 -->
    <el-table size="small" :data="schoolData.list" v-loading="loading">
      <el-table-column label="序号" type="index" :index="1"></el-table-column>
      <el-table-column label="学校logo" align="center" width="100">
        <template slot-scope="scope">
          <div style="width:60px;height:60px;border-radius:50%;background-color:#f4f4f4">
             <img :src="scope.row.photo" alt="" style="width:50px;cursor:pointer" @click="toPhotoHandler(scope.row)">
          </div>
        </template>
      </el-table-column>
      <el-table-column label="名称" prop="name">
        <template slot-scope="scope">
          <div class="prop">
            <div class="prop_name">名称</div>
            <div class="prop_val">{{scope.row.name}}</div>
          </div>
          <div class="prop">
            <div class="prop_name">简介</div>
            <div class="prop_val">{{scope.row.introduce}}</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="100" align="center">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="toDeleteHandler(scope.row)">删除</el-button>
          <el-button type="text" size="mini" @click="toEditHandler(scope.row)">修改</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 表格结束 -->
    <!-- 分页 -->
   <div class="page">
      <el-pagination background 
        layout="prev, pager, next,sizes" 
        small
        :total="schoolData.total" 
        :page-size="schoolData.pageSize" 
        :current-page="schoolData.page"
        :page-sizes="[5, 10, 15, 20]"
        @size-change="handleSizeChange"
        @current-change="currentChangeHandler">
      </el-pagination>
   </div>
    <!-- 分页 -->
    <!-- 抽屉 -->
    <Briupdrawer :drawer-visible="visible" :title="title" width="40%" @on-change-visible="handlerVisibleChange" >
      <div slot="content">
        <el-form :model="form" ref="schoolForm" :rules="rules" label-width="80px">
          <el-form-item label="名称" prop="name">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="介绍" prop="introduce">
            <el-input type="textarea" v-model="form.introduce"></el-input>
          </el-form-item>
          <el-form-item label="学校图片">
            <el-upload
              class="upload-demo"
              action="http://localhost:8004/file/upload"
              :on-success="uploadSuccessHandler"
              :limit="1"
              list-type="picture-card"
              :file-list="fileList">
              <i class="el-icon-plus"></i>
              <div slot="tip" class="el-upload__tip">文件大小不允许超过3M</div>
            </el-upload>
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
import {get,post} from '@/utils/request'
import _ from 'lodash'
export default {
  // 组件名称
  name: 'demo',
  // 组件参数 接收来自父组件的数据
  props: {},
  // 组件状态值
  data () {
   return {
    visible:false,
    visible_school:false,
    loading:false,
    title:"",
    param:{
      page:1,
      pageSize:5
    },
    schoolData:{
      list:[]
    },
    fileList:[],
    form:{},
    rules:{
      name: [ { required: true, message: '请输入名称', trigger: 'blur' } ],
      introduce: [ { required: true, message: '请输入简介', trigger: 'blur' } ]
    }
   }
  },
  // 计算属性
  computed: {},
  // 侦听器
  watch: {},
  // 组件方法
  methods: {
    // 去查看图片详情
    toPhotoHandler(row){
      this.form = row;
      this.visible_school = true;
    },
    // 上传成功
    uploadSuccessHandler(response){
      if(response.status == 200){
        this.$set(this.form,'photo',response.data.id);
      } else {
        this.$message({type:'error',message:'附件服务器异常！'})
        this.visible = false;
      }
    },
    submitHandler(){
      console.log(this.$refs['schoolForm']);
      this.$refs['schoolForm'].validate((valid) => {
        if (valid) {
          let url = '/school/saveOrUpdate'
          post(url,this.form).then(response => {
            this.$message({type:"success",message:response.message})
            this.loadSchools();
            this.visible = false;
          })
        } else {
          return false;
        }
      });
    },
    // 去保存
    toSaveHandler(){
      this.title = "新增学校信息";
      this.form = {};
      this.fileList = [];
      this.visible = true;
    },
    // 去修改
    toEditHandler(row){
      this.title = "修改学校信息"
      this.form = _.clone(row);
      this.visible = true;
    },
    // 去删除
    toDeleteHandler(row){
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let url = '/school/deleteById'
        get(url,{id:row.id}).then(response =>{
          this.$message({ type: 'success', message: '删除成功!' });
          this.loadSchools();
        })
      })
    },
    // 查询
    loadSchools(){
      this.loading = true;
      let url = "/school/pageQuery"
      get(url,this.param).then(response => {
        this.schoolData = response.data;
        this.loading = false;
      })
    },
    // 当前页发生改变
    currentChangeHandler(page){
      this.param.page = page;
      this.loadSchools();
    },
    // 处理分页大小变化的函数
    handleSizeChange(pageSize){
      // 当前页为第一页
      this.page = 1;
      // 页面大小为用户选择的页面
      this.param.pageSize = pageSize;
      this.loadSchools();
    },
    // 重置表单校验
    clearValidate() {
      this.$refs['schoolForm'].clearValidate();
    },
    handlerVisibleChange(val) {
      this.visible = val
    }
  },
  // 以下是生命周期钩子
  /**
  * 组件实例创建完成，属性已绑定，但DOM还未生成，$ el属性还不存在
  */
  created () {
    // 加载学校信息
    this.loadSchools();
  },
  /**
  * el 被新创建的 vm.$ el 替换，并挂载到实例上去之后调用该钩子。
  * 如果 root 实例挂载了一个文档内元素，当 mounted 被调用时 vm.$ el 也在文档内。
  */
  mounted () {
  },
  /**
  * 实例销毁之前调用。在这一步，实例仍然完全可用。
  */
  beforeDestroy () {
  },
  /**
  * Vue 实例销毁后调用。调用后，Vue 实例指示的所有东西都会解绑定，
  * 所有的事件监听器会被移除，所有的子实例也会被销毁。
  */
  destroyed () {
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<!--使用了scoped属性之后，父组件的style样式将不会渗透到子组件中，-->
<!--然而子组件的根节点元素会同时被设置了scoped的父css样式和设置了scoped的子css样式影响，-->
<!--这么设计的目的是父组件可以对子组件根元素进行布局。-->
<style scoped>

</style>
