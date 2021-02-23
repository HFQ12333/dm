<template>
	<div>
  	<el-table :data="orders" border style="width: 100%">
    <el-table-column prop="id" label="ID" width="80" align="center"></el-table-column>
    <el-table-column prop="orderTime" label="下单时间" align="center">{{time|moment}}</el-table-column>
    <el-table-column prop="total" label="价格￥" align="center"></el-table-column>
    <el-table-column prop="status" label="订单状态" align="center"></el-table-column>
    <!-- <el-table-column prop="remark" label="派件员" align="center"></el-table-column> -->
    <el-table-column prop="customerId" label="用户id" align="center"></el-table-column>
    <el-table-column prop="addressId" label="地址id" align="center"></el-table-column>

	<el-table-column
      fixed="right"
      label="操作"
      width="200"
	  align="center">
      <template slot-scope="scope">
        <el-button @click="doHandler(scope.row.id)" type="danger" size="mini">确认订单</el-button>
        <el-button @click="reHandler(scope.row.id)" type="success" size="mini">取消订单</el-button>
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
  
	</div>
</template>
<script>
import { mapState, mapActions } from "vuex";
	export default{
		data(){
			return {
				list:{
					page:0,
					pageSize:10,
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
			this.queryorder(this.list);
			console.log(list)
		},
		computed:{
				...mapState("order",["orders","total"]),
			},
		methods:{
			...mapActions("order",["findAll","queryorder","saveorder","delorderById","cancelSendOrder","confirmOrder"]),
			changePage(page){
				this.list.page = page - 1;
				this.queryorder(this.list);
			},
			toAddHandler(){
				//控制模态框打开
				this.dialogFormVisible = true;
				this.title = "添加"
				//清空表单
				this.form = {}
			},
			submit(form){
				//调用store中的方法提交请求
				this.saveorder(this.form)
				//关闭模态框
				this.dialogFormVisible = false;
			},
			reHandler(id){
				this.$confirm("此操作将取消订单, 是否继续?", "提示", {
					confirmButtonText: "确定",
					cancelButtonText: "取消",
					type: "warning",
				})
					.then(() => {
					this.cancelSendOrder(id);
					this.$message({
						type: "success",
						message: "取消成功!",
					});
					})
					.catch(() => {
					this.$message({
						type: "info",
						message: "已取消",
					});
					});

			},
			doHandler(id){
				this.$confirm("此操作将确认订单, 是否继续?", "提示", {
					confirmButtonText: "确定",
					cancelButtonText: "取消",
					type: "warning",
				})
					.then(() => {
					this.confirmOrder(id);
					this.$message({
						type: "success",
						message: "确认成功!",
					});
					})
					.catch(() => {
					this.$message({
						type: "info",
						message: "已取消确认",
					});
					});

			},
			searchHandle(){
				this.queryorder(this.list)
			},
			detailsHandle(id){
				this.$router.push({name:"details",params:{id:id}})
			}
		}
	}
</script>
<style scoped>

</style>
