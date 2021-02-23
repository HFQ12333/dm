<template>
	<div>
		<el-button type="primary" size="small" @click="toAddHandler">添加地址</el-button>
		<el-input v-model="list.province" clearable placeholder="请输入省份" style="width:200px;display:inline-block"></el-input>
		<el-button type="primary" size="small" @click="searchHandle">查询</el-button>
  	<el-table :data="addresss" border style="width: 100%">
		<el-table-column prop="id" label="ID" width="100" align="center"></el-table-column>
		<el-table-column prop="province" label="省份" align="center" width="120"></el-table-column>
		<el-table-column prop="city" label="省市" align="center" width="200"></el-table-column>
		<el-table-column  width="120" prop="area" label="地区" align="center" ></el-table-column>
		<el-table-column  width="300" prop="address" label="详细位置" align="center" ></el-table-column>
		<el-table-column prop="telephone" label="联系方式" align="center" ></el-table-column>
		<el-table-column
		fixed="right"
		label="操作"
		width="150"
		align="center">
		<template slot-scope="scope">
			<el-button @click="editHandler(scope.row)" type="text" size="small">修改地址</el-button>
			<el-button @click="delHandler(scope.row.id)" type="text" size="small">删除地址</el-button>
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
  <el-form :model="form">
    <el-form-item label="省份">
      <el-input v-model="form.province" clearable placeholder="请输入省份"></el-input>
    </el-form-item>
	<el-form-item label="市">
      <el-input v-model="form.city" clearable placeholder="请输入市"></el-input>
    </el-form-item>
	<el-form-item label="地区">
      <el-input  v-model="form.area" clearable placeholder="请输入地区"></el-input>
    </el-form-item>
	<el-form-item label="详细位置">
    <el-input  v-model="form.address" clearable placeholder="请输入详细位置"></el-input>
	</el-form-item>
   <el-form-item label="联系方式">
      <el-input v-model="form.telephone" clearable placeholder="请输入联系方式"></el-input>
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
					pageSize:9,
				},
				//模态框隐藏
				dialogFormVisible:false,
				form:{
				},
				title:""
			}
		},
		created(){
			// this.findAll();
			// 分页查询用户信息
			this.queryaddress(this.list);
			console.log('11111'+this.addresss)
		},
		computed:{
				...mapState("address",["addresss","total"]),
			},
		methods:{
			...mapActions("address",["findAll","findAllAddressWithCustomer","queryaddress","saveaddress","deladdressById"]),
			changePage(page){
				this.list.page = page - 1;
				this.queryaddress(this.list);
			},
			toAddHandler(){
				//控制模态框打开
				this.dialogFormVisible = true;
				this.title = "添加地址"
				//清空表单
				this.form = {}
			},
			submit(form){
				//调用store中的方法提交请求
				this.saveaddress(this.form)
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
					this.deladdressById(id);
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
				this.title = "修改地址信息"
				this.form = row
			},
			searchHandle(){
				this.queryaddress(this.list)
			},
			detailsHandle(id){
				this.$router.push({name:"details",params:{id:id}})
			}
		}
	}
</script>
<style scoped>

</style>
