<template>
  <div class="home">
    <div class="home_container">
      <div class="articles">
        <div class="article" v-for="article in articleData.list" :key="article.id" @click="toDetails(article)">
          <div class="article_info">
            <div class="img">
              <img :src="article.cover" alt="">
            </div>
            <div class="introduce">
              <div class="title">{{article.title}}</div>
              <div class="info">{{article.publishTime | fmtDate}}</div>
              <div class="info"> <strong>阅读次数：</strong> {{article.readTimes}} 次</div>
              <div class="info"> <strong>点赞次数：</strong> {{article.thumpUp}} 次</div>
              <div class="content"> <strong>作者：</strong> {{article.baseUser?article.baseUser.realname:"匿名"}}</div>
              <div>{{article.status}}</div>
            </div>
          </div>
        </div>
      </div>
      <div class="notices">
        <div class="title">边栏</div>
      </div>
      
    </div>
    <!-- 项目详情 -->
    <el-dialog title="研学项目" :visible.sync="visible_project" width="80%" class="customer_modal">
      <div v-html="project.details"> </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="visible_project = false">关闭</el-button>
      </span>
    </el-dialog>
    <!-- /项目详情 -->
  </div>
</template>
<script>
import {get,post} from '@/utils/request'
export default {
  data(){
    return {
      param:{
        page:1,
        pageSize:15,
        status:'审核通过'
      },
      articleData:{list:[]},
      projectData:{list:[]},
      loading:false,
      visible_project:false,
      project:{}
    }
  },
  created(){
    this.loadArticles();
    // this.loadProjects();
  },
  methods:{
    toProjectDetails(row){
      this.project = row;
      this.visible_project = true;
    },
    // 跳转到详情页面
    toDetails(article){
      this.$router.push({
        path:'/studies/ArticleDetails',
        query:{id:article.id}
      })
    },
    loadArticles(){
      this.param.status = "审核通过";
      get('/article/pageQuery',this.param)
      .then(response => {
        this.articleData = response.data
      })
    },
     // 查询
    loadProjects(){
      let url = "/project/pageQuery"
      get(url,{page:1,pageSize:10,status:"审核通过"}).then(response => {
        this.projectData = response.data;
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.home {
  background-color: #f3f3f9;
  margin: -.5em;
  padding: .5em;

  .home_container {
    // min-height: 1000px;
    border-radius: 5px;
    display: flex;
    flex-direction: row;
    font-size: 14px;
    color: #666;
    & > div.articles {
      flex:1;
      display: flex;
      // justify-content: space-between;
      flex-wrap: wrap;
      .article:hover {
        box-shadow: 0 0 10px #cccccc;
      }
      .article {
        width: 32.5%;
        height: 340px;
        margin-right: .7%;
        background-color: #ffffff;
        margin-bottom: 1em;
        padding: 1em;
        border-radius: 5px;
        display: flex;
        // 信息
        .article_info {
          cursor:pointer;
          .img {
            height: 180px;
            background-color: #f4f4f4;
            overflow: hidden;
            img {
              width:100%
            }
          }
          .introduce {
            flex:1;
            padding:0 1em;
            .title {
              font-weight:bold;
              line-height: 2em;
            }
            .info {
              line-height: 1.5em;
            }
            .content {
              padding: .4em 0;
            }
          }
        }
      }
      .other {
        line-height: 2em;
      }
    }
    & > div.notices {
      margin-left: .2em;
      padding: .5em 1em;
      flex-basis: 320px;
      background-color: #ffffff;
      .title {
        font-size: 16px;
        font-weight: bold;
        line-height: 2em;
      }

      .projects {
        font-size: 12px;
        .project {
          line-height: 2em;
          cursor: pointer;
          border-bottom: 1px solid #f4f4f4;
        }
      }
    }
  }
}
</style>