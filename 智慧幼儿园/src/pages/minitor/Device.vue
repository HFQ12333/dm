<template>
  <div>
    <!-- 输入框和搜索按钮 -->
    <div class="btns">
      <el-row>
        <el-col :span="16">
          <!-- 输入框 -->
          <el-form :inline="true" size="small">
            <el-form-item label="名称">
              <el-input v-model="input"></el-input>
            </el-form-item>
            <!-- 搜索按钮 -->
            <el-form-item>
              <el-button @click="search">搜索</el-button>
            </el-form-item>
          </el-form>
        </el-col>
        <!-- 新增按钮 -->
        <el-col :span="8" style="text-align:right">
          <el-button type="primary" size="small" @click="toAdd">新增</el-button>
        </el-col>
      </el-row>
    </div>
    <!-- 表格开始 -->
    <el-table :data="devicelist" size="small">
      <el-table-column type="index" :index="1" label="序号"/>
      <el-table-column prop="name" label="名称" width="380" />
      <el-table-column prop="schoolId" label="schoolId" width="180" />
      <el-table-column prop="roomId" label="roomId" width="480" />
      <el-table-column prop="address" label="操作" >
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="deleteById(scope.row.id)">删除</el-button>
            <el-button type="text" size="small" @click="edit(scope.row)">修改</el-button>
         </template>
      </el-table-column>
    </el-table>
    <!-- 表格结束 -->
    <!-- 分页按钮 -->
    <el-pagination background layout="prev, pager, next,sizes" :total="total" 
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="devicelist.page"
            :page-size="devicelist.pageSize"
            :page-sizes="[5,10,15,20]"></el-pagination>
    <!-- 分页结束-->
    <!-- 抽屉 -->
    <el-drawer :title="title" :visible.sync="drawer" width="40%"  >
       <div class="content">
         <el-form  :model="form" label-width="80px" :rules="rules" ref="myForm">
           <el-form-item label="班级" prop="name">
            <el-select v-model="form.name"  placeholder="请选择班级">
              <el-option :label="item.name" :value="item.name"  v-for="item in roomList" :key="item.id"></el-option>
            </el-select>
          </el-form-item>
          <!-- 学校id -->
          <el-form-item label="schoolId" prop="schoolId">
            <el-select v-model="form.schoolId" placeholder="请选择学校Id" >
              <el-option :label="item.schoolId" :value="item.id" v-for="item in schoolList" :key="item.id"></el-option>
            </el-select>
          </el-form-item>
          <!-- 房间id -->
          <el-form-item label="roomId" prop="roomId">
            <el-select v-model="form.roomId" placeholder="请选择房间id">
              <el-option :label="item.roomId" :value="item.id" v-for="item in room" :key="item.id"></el-option>
            </el-select>
          </el-form-item>
         </el-form>
       </div>
           <div class="demo-drawer__footer"  >
              <el-button @click="cancelForm" size="small">取 消</el-button>
              <el-button type="primary" size="small" @click="submitForm('myForm')" >确定</el-button>
            </div>
    </el-drawer>
    <!-- 抽屉结束 -->
  </div>
</template>
<script>
import {get,post} from '@/utils/request'
import qs from 'querystring'
import { type } from 'os';
import _ from 'lodash'
export default {
  data() {
    return {
      form:{
        name: "",
        schoolId:"",
        roomId:"",
      },
      roomList:[],
      title:"新增监控设备",
      room:[],
      input:"",
      drawer: false,
      param:{
        page: 1,
        pageSize:10,
      },
      total:0,
      schoolList:[],
      devicelist:[],
      schoolIdList:[],
         rules: {
          name: [
            { required: true, message: '请选择班级', trigger: 'change' }
          ],
          schoolId: [
            { required: true, message: '请选择schoolId', trigger: 'change' }
          ],
          roomId: [
            { required: true, message: '请选择roomId', trigger: 'change' }
          ]}
    };
  },
  methods: {
    //给确定按钮绑定事件
    submitForm(myForm){
         this.$refs[myForm].validate((valid) =>{
          if (valid) {
            //表单验证成功
            //获取用户输入 发送保存请求
            post("/device/saveOrUpdate",this.form).then(response=>{
              //如果保存成功
              if(response.status==200){
                //发送请求
                //刷新页面
                this.findAll();
                //弹出消息 保存成功
                this.$message({ 
                   message: '保存成功',
                   type: 'success'
                   });
              }else{
                //保存失败
                  this.$message.error('保存失败');
              }
              this.drawer=false;
            })
            //2.发送请求
            this.findAll()
          } else {
            // console.log('error submit!!');
            return false;
          }
        });
    },
    //给修改按钮绑定事件
    edit(row){
      this.title="修改监控设备"
      this.drawer=true;
      this.form={
        id:row.id,
        name:row.name,
        schoolId:row.schoolId,
        roomId:row.roomId
      }
    },
    //通过名字搜索
    search(){
      if(this.input){
        this.findAll(this.input)
      }else{
        this.findAll()
      }
    },
    //查询所有房间id
    findRoomId(){
      get("/clazz/pageQuery",this.param).then(
        response=>{
          // console.log(response)
          this.room=response.data.list
        }
      )
    },
    //查询所有学校
    findSchool(){
      get("/room/pageQuery",{
        page:1,
        pageSize:10000
      }).then(response=>{
        console.log(response)
        this.schoolList=response.data.list;
      })
    },
    //查询所有学校请求
    findRoom(){
      get("/clazz/pageQuery",{
        page:1,
        pageSize:10000
      }).then(response=>{
        // console.log(response)
        this.roomList=response.data.list
      })
    },
    //给删除按钮绑定事件
    deleteById(id){
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //确认删除
          get("/device/deleteById",{
            id
          }).then(response=>{
            console.log(response)
            if(response.status==200){
                //删除成功
                this.findAll();
                 this.$message({
                  message: '删除成功',
                  type: 'success'
                });
            }else{
               this.$message.error('删除失败');
            }
          })
          //2.发送请求
          this.findAll()
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
    },
    //给新增按钮绑定事件
    toAdd() {
      this.drawer = true;
      this.title="新增监控设备";
      this.form={}
    },
    //全局封装分页查询所有相关监控设备
    findAll(name) {
      if (name) {
        //搜索查询
        var data = {
          page: this.param.page,
          pageSize: this.param.pageSize,
          name
        };
      }else{
        //非搜索查询
        var data = {
          page: this.param.page,
          pageSize: this.param.pageSize,
        };
      }
      get("/device/pageQuery",data).then(response=>{
          // console.log(response)
          this.devicelist=response.data.list
          this.total=response.data.total
          
      });
    },
     handleSizeChange(pageSize) {
        // console.log(`每页 ${val} 条`);
        this.page=1;
        this.param.pageSize=pageSize;
        this.findAll()

      },
      handleCurrentChange(page) {
        // console.log(`当前页: ${val}`);
        this.param.page=page;
        this.findAll();
        
      },
      cancelForm() {
      this.drawer = false;
      clearTimeout(this.timer);
    },
    
  },
  created(){
    this.findAll()
    this.findRoom()
    this.findSchool();
    this.findRoomId();
    
  },
};
</script>
<style lang="scss" scoped>
      .content{
        margin-top: 30px;
      }
      .content{
        margin-left: 30px;
      }
      .demo-drawer__footer{
        text-align: center;
        margin-top:400px;
      }
</style>

