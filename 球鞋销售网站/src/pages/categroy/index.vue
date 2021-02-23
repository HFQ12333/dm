<template>
	<div>
		<el-tabs v-model="activeName" @tab-click="handleClick">
			<!-- 球鞋品牌 -->
				<el-tab-pane label="球鞋品牌" name="first">
							<el-button type="primary" size="small" @click="toAddHandler">添加商品种类</el-button>
		<el-input v-model="list.name" clearable placeholder="请输入商品种类名称" style="width:200px;display:inline-block"></el-input>
	    <el-button type="primary" size="small" @click="searchHandle">查询</el-button>
  	<el-table :data="categorys" border style="width: 100%">
    <el-table-column prop="id" label="ID" width="180" align="center"></el-table-column>
    <el-table-column prop="name" label="品牌分类" align="center"></el-table-column>
    <el-table-column prop="num" label="数量" align="center"></el-table-column>
    <!-- <el-table-column  prop="parentId" label="该种类主打品牌"></el-table-column> -->
    <el-table-column  prop="icon" label="图片" align="center">
		<template slot-scope="scope">
		<el-avatar :size="50" :src="scope.row.icon" alt="图片找不到" style="width:60px;height:60px">
		</el-avatar></template>
		</el-table-column>
	<el-table-column
      fixed="right"
      label="操作"
      width="200"
	  align="center">
      <template slot-scope="scope">
		<el-button @click="editHandler(scope.row)" type="text" size="small">编辑</el-button>
        <el-button @click="delHandler(scope.row.id)" type="text" size="small">删除商品</el-button>
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
    <el-form-item label="商品类型">
      <el-input v-model="form.name" clearable placeholder="请输入商品类型"></el-input>
    </el-form-item>
	<el-form-item label="商品数量">
      <el-input v-model="form.num" clearable placeholder="请输入商品数量"></el-input>
    </el-form-item>
	<!-- 上传图片 -->
	<div class="user-header">
      <input type="file" name="image" accept="image/*" @change='onchangeImgFun'
       class="header-upload-btn user-header-com">
      <img  alt="" :src='imgStr' class="user-header-img user-header-com">
      <p class="tip">图片限制50kb <span class="error">{{errorStr}}</span></p>
    </div>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="submit()">确 定</el-button>
  </div>
</el-dialog>
  <!-- 模态框结束 -->
				</el-tab-pane>
				<!-- 球鞋位置 -->
				<el-tab-pane label="球鞋位置" name="second" @click="detailsHandle_two(scope.row.id)">
					<el-button type="primary" size="small" @click="toAddHandler">添加商品种类</el-button>
		<el-input v-model="list.name" clearable placeholder="请输入商品种类名称" style="width:200px;display:inline-block"></el-input>
	    <el-button type="primary" size="small" @click="searchHandle">查询</el-button>
  	<el-table :data="categorys" border style="width: 100%">
    <el-table-column prop="id" label="ID" width="180" align="center"></el-table-column>
    <el-table-column prop="name" label="品牌分类" align="center"></el-table-column>
    <el-table-column prop="num" label="数量" align="center"></el-table-column>
    <!-- <el-table-column  prop="parentId" label="该种类主打品牌"></el-table-column> -->
    <el-table-column  prop="icon" label="图片" align="center">
		<template slot-scope="scope">
		<el-avatar :size="50" :src="scope.row.icon" alt="图片找不到" style="width:60px;height:60px">
		</el-avatar></template>
		</el-table-column>
	<el-table-column
      fixed="right"
      label="操作"
      width="200"
	  align="center">
      <template slot-scope="scope">
		<el-button @click="editHandler(scope.row)" type="text" size="small">编辑</el-button>
        <el-button @click="delHandler(scope.row.id)" type="text" size="small">删除商品</el-button>
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
    <el-form-item label="商品种类名称">
      <el-input v-model="form.name" clearable placeholder="请输入商品种类名称"></el-input>
    </el-form-item>
	<el-form-item label="商品种类icon地址">
      <el-input v-model="form.icon" clearable placeholder="请输入icon地址"></el-input>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="submit()">确 定</el-button>
  </div>
</el-dialog>
  <!-- 模态框结束 -->
				</el-tab-pane>
				<!-- 喜爱球星 -->
				<el-tab-pane label="喜爱球星" name="third" @click="detailsHandle_three(scope.row.id)">
				</el-tab-pane>
				<!-- 球鞋高低帮 -->
				<el-tab-pane label="球鞋高低帮" name="fourth" @click="detailsHandle_four(scope.row.id)"></el-tab-pane>															
		</el-tabs>

	</div>
</template>
<script>
import { mapState, mapActions } from "vuex";
	export default{
		data(){
			return {
				 circleUrl: "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1277861296,2710786810&fm=26&gp=0.jpg",
				list:{
					page:0,
					pageSize:5,
				},
				//模态框隐藏
				dialogFormVisible:false,
				form:{
				},
				title:'',
				activeName: 'first',
				imgStr: require('../../assets/g.jpg'),
     			errorStr: ''
			}
		},
		created(){
			// this.findAll();
			// 分页查询用户信息
			this.querycategory(this.list);
			console.log(list)
		},
		computed:{
				...mapState("category",["categorys","total"]),
			},
		methods:{
			...mapActions("category",["findAll","querycategory","savecategory","delcategoryById"]),
			changePage(page){
				this.list.page = page - 1;
				this.querycategory(this.list);
			},
			toAddHandler(){
				//控制模态框打开
				this.dialogFormVisible = true;
				this.title = "添加商品种类"
				//清空表单
				this.form = {}
			},
			submit(form){
				//调用store中的方法提交请求
				this.savecategory(this.form)
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
					this.delcategoryById(id);
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
				this.title = "修改商品种类信息"
				this.form = row
			},
			searchHandle(){
				this.querycategory(this.list)
			},
			detailsHandle(id){
				this.$router.push({name:"details",params:{id:id}})
     		 },
				handleClick(tab, event) {
					console.log(tab, event);
					},
					onchangeImgFun (e) {
						var file = e.target.files[0]
						console.log(file)
						// 获取图片的大小，做大小限制有用
						let imgSize = file.size
						console.log(imgSize)
						var _this = this // this指向问题，所以在外面先定义
						// 比如上传头像限制5M大小，这里获取的大小单位是b
						if (imgSize <= 50 * 1024) {
							// 合格
							_this.errorStr = ''
							console.log('大小合适')
							// 开始渲染选择的图片
							// 本地路径方法 1
							// this.imgStr = window.URL.createObjectURL(e.target.files[0])
							// console.log(window.URL.createObjectURL(e.target.files[0])) // 获取当前文件的信息
					
							// base64方法 2
							var reader = new FileReader()
							reader.readAsDataURL(file) // 读出 base64
							reader.onloadend = function () {
							// 图片的 base64 格式, 可以直接当成 img 的 src 属性值
							var dataURL = reader.result
							console.log(dataURL)
							_this.imgStr = dataURL
							// 下面逻辑处理
							}
						} else {
							console.log('大小不合适')
							_this.errorStr = '图片大小超出范围'
						}
   					 }
  }
}
</script>
<style scoped>
	.user-header{
  position: relative;
  display: inline-block;
}
.user-header-com{
  width: 144px;
  height: 144px;
  display: inline-block;
}
.header-upload-btn{
  position: absolute;
  left: 0;
  top: 0;
  opacity: 0;
  /* 通过定位把input放在img标签上面，通过不透明度隐藏 */
}
.tip{
  font-size: 14px;
  color: #666;
}
/* error是用于错误提示 */
.error{
  font-size: 12px;
  color: tomato;
  margin-left: 10px;
}
</style>
