<template>
	<div>
  	<el-table :data="comments" border style="width: 100%">
    <el-table-column prop="id" label="ID" width="100" align="center"></el-table-column>
    <el-table-column prop="commentTime" label="评论时间" width="151px">{{list.commentTime | dateFmt('YYYY-MM-DD HH:mm:ss')}}</el-table-column>
    <el-table-column prop="content" label="评论内容" width="1000px"></el-table-column>
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
<el-dialog :title="title" :visible.sync="dialogFormVisible">
  <el-form :model="form" :rules="rules">
	   <el-form-item label="评论内容">
      <el-input v-model="form.id" clearable placeholder="请输入评论内容"></el-input>
    </el-form-item>
   <el-form-item label="评论内容">
      <el-input v-model="form.content" clearable placeholder="请输入评论内容"></el-input>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="submit()">确 定</el-button>
  </div>
</el-dialog>
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
				tltle:""
			}
		},
		created(){
			// this.findAll();
			// 分页查询用户信息
			this.querycomment(this.list);
			// console.log(list)
		},
		computed:{
				...mapState("comment",["comments","total"]),
			},
		methods:{
			...mapActions("comment",["findAll","querycomment","savecomment","delcommentById","commentExamine","commentRefuseExamine"]),
			changePage(page){
				this.list.page = page - 1;
				this.querycomment(this.list);
			},
			toAddHandler(){
				//控制模态框打开
				this.dialogFormVisible = true;
				this.title = "添加商品"
				//清空表单
				this.form = {}
			},
			submit(form){
				//调用store中的方法提交请求
				this.savecomment(this.form)
				//关闭模态框
				this.dialogFormVisible = false;
			},
			reHandler(id){
				this.$confirm("此操作将不通过该评论, 是否继续?", "提示", {
					confirmButtonText: "确定",
					cancelButtonText: "取消",
					type: "warning",
				})
					.then(() => {
					this.commentRefuseExamine(id);
					this.$message({
						type: "success",
						message: "拒绝成功!",
					});
					})
					.catch(() => {
					this.$message({
						type: "info",
						message: "已取消拒绝",
					});
					});

			},
			doHandler(id){
				this.$confirm("此操作将通过该评论, 是否继续?", "提示", {
					confirmButtonText: "确定",
					cancelButtonText: "取消",
					type: "warning",
				})
					.then(() => {
					this.commentExamine(id);
					this.$message({
						type: "success",
						message: "通过成功!",
					});
					})
					.catch(() => {
					this.$message({
						type: "info",
						message: "已取消通过",
					});
					});

			},
			searchHandle(){
				this.querycomment(this.list)
			},
			detailsHandle(id){
				this.$router.push({name:"details",params:{id:id}})
			},
			editHandler(row){
				//打开模态框
				this.dialogFormVisible = true;
				this.title = "修改商品信息"
				this.form = row
			},
		},
	}
</script>
<style scoped>

</style>
