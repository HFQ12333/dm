<template>
  <div>
    <!-- 按钮 -->
    <div class="btns">
      <el-form :inline="true" :model="param" size="small">
        <el-button type="primary" size="small" @click="toPublishArticle">发布资讯</el-button>
        <el-form-item label="所属栏目">
          <el-select v-model="param.categoryId" placeholder="请选择所属栏目" :clearable="true">
            <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="标题">
          <el-input v-model="param.title"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <!-- 表格 -->
    <el-table :data="articleData.list" style="width: 100%" size="mini" v-loading="loading">
      <el-table-column type="index" :index="1" label="序号" />
      <el-table-column prop="title" label="标题" width="320" />
      <el-table-column label="发布日期" width="120" >
        <template slot-scope="scope">
          {{moment(scope.row.publishTime).format("YYYY-MM-DD")}}
        </template>
      </el-table-column>
      <el-table-column prop="category.name" label="所属栏目" />
      <!-- <el-table-column prop="readTimes" label="阅读次数" width="100" />
      <el-table-column prop="thumpUp" label="点赞次数" width="100" />
      <el-table-column prop="thumpDown" label="状态" width="80" /> -->
     
      <el-table-column
        fixed="right"
        label="操作"
        align="center"
        width="100"
      >
        <template slot-scope="scope">
          <a type="text" size="small">查看</a>
          <a type="text" size="small" @click.prevent="toEditArticle(scope.row)">编辑</a>
          <a type="text" size="small" @click.prevent="deleteArticleById(scope.row)">删除</a>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
     <!-- 分页 -->
    <el-pagination background layout="prev, pager, next" small
      :hide-on-single-page="true"
      :total="articleData.total" 
      :page-size="articleData.pageSize" 
      :current-page="articleData.page"
      @current-change="currentChangeHandler">
    </el-pagination>
  </div>
</template>

<script>
import {get} from '@/utils/request'
import moment from 'moment'
export default {
  // 模板中要用到的变量
  data() {
    return {
      tableData: [],
      loading:false,
      categories:[],
      param:{
        page:1,
        pageSize:13
      },
      articleData:{
        list:[]
      }
    }
  },
  // 声明周期钩子函数
  created() {
    // 查询所有资讯信息
    this.loadArticles();
    // 查询栏目信息
    this.loadCategories();
  },
  watch:{
    param:{
      handler:function(old,now){
        this.loadArticles();
      },
      deep:true
    }
  },
  // 方法，模块中要用到的方法，
  methods: {
    moment,
    // 通过id删除
    deleteArticleById(row){
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        get('/article/deleteById',{id:row.id})
        .then(response =>{
          this.$message({type:'success',message:response.message})
          this.loadArticles();
        })
      })
    },
    loadCategories() {
      const url = '/category/findAll'
      get(url).then(response => {
        this.categories = response.data
      })
    },
    loadArticles(){
      this.loading = true;
      get('/article/pageQuery',this.param)
      .then(response => {
        this.articleData = response.data
        this.loading = false;
      })
    },
    toPublishArticle() {
      // 跳转页面
      this.$router.push({
         path: '/info/article/Editor' 
      })
    },
    toEditArticle(row) {
      this.$router.push({
        name: '_info_article_Editor',
        params: row
      })
    },
    // 当前页发生改变
    currentChangeHandler(page){
      this.param.page = page;
    }
  }
}
</script>

<style scoped>

</style>
