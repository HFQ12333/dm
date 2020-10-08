<template>
	<div>
		<el-button type="primary" size="small" @click="toAddHandler">添加产品</el-button>
		<el-input v-model="list.status" clearable placeholder="请输入产品名称" style="width:200px;display:inline-block"></el-input>
	<el-button type="primary" size="small" @click="searchHandle">查询</el-button>
	
  	<el-table :data="products" border style="width: 100%">
    <el-table-column prop="id" label="ID" width="180"></el-table-column>
    <el-table-column prop="name" label="产品名称"></el-table-column>
    <el-table-column prop="description" label="描述"></el-table-column>
    <el-table-column prop="price" label="价格"></el-table-column>
	<el-table-column
      fixed="right"
      label="操作"
      width="200">
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
  @current-change = "changePage"></el-pagination>
  <!-- 分页结束 -->
  <!-- 模态框开始 -->
  <el-dialog :title="title" :visible.sync="dialogFormVisible">
  <el-form :model="form">
    <el-form-item label="用户名">
      <el-input v-model="form.username" clearable placeholder="请输入用户名"></el-input>
    </el-form-item>
	<el-form-item label="手机号">
      <el-input v-model="form.telephone" clearable placeholder="请输入手机号"></el-input>
    </el-form-item>
    <el-form-item label="状态">
      <el-select v-model="form.status" clearable placeholder="请选择顾客状态">
        <el-option label="禁用" value="禁用"></el-option>
        <el-option label="启用" value="启用"></el-option>
      </el-select>
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
				tltle:""
			}
		},
		created(){
			// this.findAll();
			// 分页查询顾客信息
			this.queryproduct(this.list);
			console.log(list)
		},
		computed:{
				...mapState("product",["products","total"]),
			},
		methods:{
			...mapActions("product",["findAll","queryproduct","saveproduct","delproductById"]),
			changePage(page){
				this.list.page = page - 1;
				this.queryproduct(this.list);
			},
			toAddHandler(){
				//控制模态框打开
				this.dialogFormVisible = true;
				this.title = "添加顾客"
				//清空表单
				this.form = {}
			},
			submit(form){
				//调用store中的方法提交请求
				this.saveproduct(this.form)
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
					this.delproductById(id);
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
				this.title = "修改顾客信息"
				this.form = row
			},
			searchHandle(){
				this.queryproduct(this.list)
			},
			detailsHandle(id){
				this.$router.push({name:"details",params:{id:id}})
			}
		}
	}
</script>
<style scoped>

</style>
