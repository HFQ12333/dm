<template>
	<div>
		<el-button type="primary" size="small" @click="toAddHandler">添加用户</el-button>
		<el-input v-model="list.status" clearable placeholder="请输入状态" style="width:200px;display:inline-block"></el-input>
		<el-input v-model="list.telephone" clearable placeholder="请输入手机号" style="width:200px;display:inline-block"></el-input>
	<el-button type="primary" size="small" @click="searchHandle">查询</el-button>
	<!-- 天气 -->
   <iframe style="height: 30px;margin-top:10px;float:right"  frameborder="0" scrolling="no" hspace="0"  src="http://i.tianqi.com/index.php?c=code&a=getcode&id=34&h=25&w=280"></iframe>
   <!-- /天气 -->
  	<el-table :data="customers" border style="width: 95%">
    <el-table-column prop="id" label="ID" width="100" align="center"></el-table-column>
    <el-table-column prop="realname" label="昵称" width="150px" align="center"></el-table-column>
    <el-table-column prop="password" label="密码" align="center"></el-table-column>
    <!-- <el-table-column prop="registerTime" label="注册时间" align="center" width="200px">{{time|moment}}</el-table-column> -->
    <el-table-column prop="telephone" label="手机号" align="center"></el-table-column>
    <el-table-column prop="status" label="实名认证" align="center"></el-table-column>
		<el-table-column  prop="photo" label="头像" width="140px" align="center"> 
			<template slot-scope="scope">
				<img :src="scope.row.photo" alt="图片找不到" style="width:130px;height:70px">
			</template>
		</el-table-column>
	<el-table-column
      fixed="right" 
      label="操作"
      width="200"
	  align="center">
      <template slot-scope="scope">
		<el-button @click="detailsHandle(scope.row.id)" type="text" size="small">详情</el-button>
		<el-button @click="editHandler(scope.row)" type="text" size="small">编辑</el-button>
        <el-button @click="delHandler(scope.row.id)" type="text" size="small">删除</el-button>
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
  @current-change = "changePage"
  align="center"></el-pagination>
  <!-- 分页结束 -->
  <!-- 模态框开始 -->
  <el-dialog :title="title" :visible.sync="dialogFormVisible">
  <el-form :model="form" :rules="rules">
    <el-form-item label="昵称" prop="realname">
      <el-input v-model="form.realname" clearable placeholder="请输入昵称"></el-input>
    </el-form-item>
	<el-form-item label="密码" prop="password">
      <el-input v-model="form.password" clearable placeholder="请输入密码"></el-input>
    </el-form-item>
	<!-- <el-form-item label="注册时间">
      <el-input v-model="form.registerTime" clearable placeholder="请输入注册时间"></el-input>
    </el-form-item> -->
	<el-form-item label="手机号" prop="telephone">
      <el-input v-model="form.telephone" clearable placeholder="请输入手机号"></el-input>
    </el-form-item>
    <el-form-item label="状态"  prop="status">
      <el-select v-model="form.status" clearable placeholder="请选择用户状态">
        <el-option label="认证成功" value="认证成功"></el-option>
        <el-option label="未认证" value="未认证"></el-option>
      </el-select>
    </el-form-item>
   <el-form-item label="商品照片地址" prop="photo">
      <el-input v-model="form.photo" clearable placeholder="请输入商品照片地址"></el-input>
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
import { mapState, mapActions } from "vuex";
	export default{
		data(){
			return {
				list:{
					page:0,
					pageSize:5,
				},
				//模态框隐藏
				dialogFormVisible:false,
				form:{
				},
				title:"",
				rules: {
					realname: [
					{ required: true, message: '请输入昵称', trigger: 'change' }
					],
					password: [
					{ required: true, message: '请输入密码', trigger: 'change' }
					]
				}
			}
		},
		created(){
			// this.findAll();
			// 分页查询用户信息
			this.queryCustomer(this.list);
		},
		computed:{
				...mapState("customer",["customers","total"]),
			},
		methods:{
			...mapActions("customer",["findAll","queryCustomer","saveCustomer","delCustomerById"]),
			changePage(page){
				this.list.page = page - 1;
				this.queryCustomer(this.list);
			},
			toAddHandler(){
				//控制模态框打开
				this.dialogFormVisible = true;
				this.title = "添加用户"
				//清空表单
				this.form = {}
			},
			submit(form){
				//调用store中的方法提交请求
				this.saveCustomer(this.form)
				//关闭模态框
				this.dialogFormVisible = false;
			},
			delHandler(id){
				this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
					confirmButtonText: "确定",
					cancelButtonText: "取消",
					type: "warning",
				}) 
					.then(() => {
					this.delCustomerById(id);
					this.$message({
						type: "success",
						message: "删除成功!",
					});
					})
					.catch(() => {
					this.$message({
						type: "info",
						message: "已取消删除",
					});
					});

			},
			editHandler(row){
				//打开模态框
				this.dialogFormVisible = true;
				this.title = "修改用户信息"
				this.form = row
			},
			searchHandle(){
				this.queryCustomer(this.list)
			},
			detailsHandle(id){
				this.$router.push({name:"details",params:{id:id}})
			},
			upTx() {
                var _this = this;
                var r = new FileReader();
                var file = document.getElementById("txUrl").files[0];
                var beat64Url;
                if (!/image\/\w+/.test(file.type)) {
                    // this.$message.error("上传的文件必须为图片！！");
                    alert('上传的文件必须为图片');
                    return false;
                }
                r.readAsDataURL(file);
                r.onload = function(e) {
                    beat64Url = this.result;
                                   };
                var formData = new FormData();
                formData.append("upload_img", $("#txUrl")[0].files[0]);
                axios({
                    url: "http://134.175.154.93:8888",
                    method: "POST",
                    headers: {
                        token: '55a31e89d7dd8b80282cba7d844d1c28'
                    },
                    data: formData
                })
                    .then(res => {
                        console.log(res.data);
                        if (res.data.code == 0) {
                            vm.imgsrc =res.data.data.src;
                         console.log(vm.imgsrc)
                        } else {
                            _this.$message({
                                message: res.data.msg,
                                type: "alert"
                            });
                        }
                    })
                    .catch(function(error) {
                    })
		}
	}
	}
</script>
<style scoped>

</style>
