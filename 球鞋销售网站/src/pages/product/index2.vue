<template>
	<div>
		<el-button type="primary" size="small" @click="toAddHandler">添加商品</el-button>
		<el-input v-model="list.name" clearable placeholder="请输入球员位置" style="width:200px;display:inline-block"></el-input>
		<el-button type="primary" size="small" @click="searchHandle">查询</el-button>
  	<el-table :data="products" border style="width: 100%">
		<el-table-column prop="id" label="ID" width="100" align="center"></el-table-column>
		<el-table-column prop="name" label="球员位置" align="center" width="150"></el-table-column>
		<el-table-column prop="description" label="描述" align="center"></el-table-column>
		<el-table-column  width="100px" prop="status" label="销量" align="center"></el-table-column>
		<el-table-column  width="80px" prop="price" label="价格￥" align="center"></el-table-column>
		<el-table-column  prop="photo" label="图片" width="140px" align="center">
			<template slot-scope="scope">
				<img :src="scope.row.photo" alt="图片找不到" style="width:130px;height:70px">
			</template>
		</el-table-column>
		<el-table-column
		fixed="right"
		label="操作"
		width="150"
		align="center">
		<template slot-scope="scope">
			<el-button @click="editHandler(scope.row)" type="text" size="small">编辑</el-button>
			<el-button @click="delHandler(scope.row.id)" type="text" size="small">下架</el-button>
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
    <el-form-item label="球员位置">
      <el-input v-model="form.name" clearable placeholder="请输入球员位置"></el-input>
    </el-form-item>
	<el-form-item label="商品描述">
      <el-input v-model="form.description" clearable placeholder="请输入商品描述"></el-input>
    </el-form-item>
	<el-form-item label="商品销量">
      <el-input  v-model="form.status" clearable placeholder="请输入商品销量"></el-input>
    </el-form-item>
	<el-form-item label="商品价格￥">
      <el-input  v-model="form.price" clearable placeholder="请输入商品价格￥"></el-input>
    </el-form-item>
   <el-form-item label="商品照片地址">
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
					page:2,
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
			// 分页查询用户信息
			this.queryproduct(this.list);
			console.log('11111'+this.products)
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
				this.title = "添加商品"
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
				this.title = "修改商品信息"
				this.form = row
			},
			searchHandle(){
				this.queryproduct(this.list)
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
