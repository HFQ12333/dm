<template>
  <div class="user_details">
    <!-- 头像 -->
    <div class="userface">
      <div class="photo">
        <div class="box">
           <img :src="user.userFace" alt="">
        </div>
      </div>

      <div style="padding-top:50px;text-align:right">
        <el-button size="small" type="text" @click="toAlterUserface">修改头像</el-button>
      </div>
    </div>
    <div class="info">
      <el-form label-width="100" size="small">
        <el-form-item label="用户姓名"> {{user.realname}} </el-form-item>
        <el-form-item label="用户手机"> {{user.telephone}} </el-form-item>
        <el-form-item label="用户性别">
           {{user.gender=='male'?'男':'女'}} 
           <i v-if="user.gender==='male'" class="el-icon-male"></i>
           <i v-else class="el-icon-female"></i>
        </el-form-item>
        <el-form-item label="用户角色"> {{user.roles.map(item => item.name)}} </el-form-item>
        <el-form-item label="注册时间"> {{user.registerTime |fmtDate }} </el-form-item>
        <el-form-item label="登录用户"> {{user.username}} </el-form-item>
        <el-form-item label="用户状态"> <el-tag size="small">{{ user.status }}</el-tag> </el-form-item>
      </el-form>
    </div>
    <div style="text-align:right;padding-right:200px;border-top:1px solid #f4f4f4" >
      <el-button size="small" type="text" @click="toAlterInfo">修改个人信息</el-button>
    </div>
    <!-- 个人信息模态框 -->
    <el-dialog title="提示" :visible.sync="visible" width="50%" @close="clearValidate" class="customer_modal">
      <el-form size="small" :model="form" ref="userForm" :rules="rules" label-width="80px">
        <el-form-item label="用户名"> 
          <el-input disabled :placeholder="form.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名" prop="realname"> 
          <el-input v-model="form.realname"></el-input>
        </el-form-item>
        <el-form-item label="用户手机" prop="telephone"> 
          <el-input v-model="form.telephone"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="male">男</el-radio>
            <el-radio label="female">女</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false" size="small">取 消</el-button>
        <el-button type="primary" size="small" @click="submitHandler">确 定 修 改</el-button>
      </span>
    </el-dialog>
    <!-- /个人信息模态框 -->
    <!-- 个人信息模态框 -->
    <el-dialog title="更改头像" :visible.sync="visible_userface" width="50%"  class="customer_modal">
      <div style="display:flex">
        <div class="photo" style="width:120px; margin:0 auto;flex:1">
          <img :src="userface" alt="" style="width:100%">
        </div>
        <div style="flex:1;padding:.5em 1em">
          <el-upload
            class="upload-demo"
            action="http://localhost:8004/file/upload"
            :on-success="uploadSuccessHandler"
            :limit="1"
            :file-list="fileList">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">文件大小不允许超过3M</div>
          </el-upload>
        </div>
        
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="visible_userface = false" size="small">取 消</el-button>
        <el-button type="primary" size="small" @click="alterUserfaceHandler">确 定 修 改</el-button>
      </span>
    </el-dialog>
    <!-- /个人信息模态框 -->
  </div>
</template>
<script>
import PanThumb from '@/components/PanThumb'
import { mapGetters,mapActions } from 'vuex'
import moment from 'moment'
import _ from 'lodash'
import {post} from '@/utils/request'
export default {
  components: { PanThumb },
  data() {
    return {
      form:{},
      userface:'',
      visible:false,
      visible_userface:false,
      rules:{
        realname: [ { required: true, message: '请输入姓名', trigger: 'blur' } ],
        password: [ { required: true, message: '请输入密码', trigger: 'blur' } ],
        telephone: [ { required: true, message: '请输入手机号', trigger: 'blur' } ],
      },
      fileList:[]
    }
  },
  computed:{
    ...mapGetters(["user"])
  },
  methods: {
    moment,
    ...mapActions('user',['getInfo']),
    // 更改头像
    toAlterUserface(){
      this.userface = this.user.userFace;
      this.visible_userface = true;
    },
    // 更改个人信息
    toAlterInfo(){
      this.form = _.clone(this.user);
      this.visible = true;
    },
    alterUserfaceHandler(){
      let url = "/baseUser/alterUserface"
      let data = {
        id:this.user.id,
        userface:this.userface
      }
      post(url,data).then(response =>{
        this.visible_userface = false;
        this.$message({type:"success",message:response.message})
        this.getInfo();
        // this.$router.back();
      })
    },
    submitHandler(){
      let url = "/baseUser/saveOrUpdate"
      post(url,this.form).then(response =>{
        this.visible = false;
        this.$message({type:"success",message:response.message})
        this.getInfo();
        // this.$router.back();
      })
    },
     // 重置表单校验
    clearValidate() {
      this.$refs['userForm'].clearValidate();
    },
    // 上传成功
    uploadSuccessHandler(response){
      if(response.status == 200){
        this.userface = 'http://121.199.29.84:8888/group1/'+response.data.id;
      } else {
        this.$message({type:'error',message:'附件服务器异常！'})
        this.visible = false;
      }
    },
  }
}
</script>
<style lang="scss" scoped>

.userface {
  display: flex;
  padding:1em 200px;
  & > div {
    flex: 1;
  }
  .photo {
    .box {
      width: 80px;
      height:80px;
      border-radius: 50%;
      overflow: hidden;
    }
    img {
      width:100%;
    }
  }
  border-bottom: 2px solid #f4f4f4;
  margin-bottom: 1em;
}

</style>
