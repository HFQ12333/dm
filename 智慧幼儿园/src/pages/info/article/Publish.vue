<template>
  <div>
    <el-button type="text" @click="back">返回</el-button>
    <el-form ref="article_form" :model="form" label-width="80px" :rules="rules">
      <el-form-item label="标题" prop="title">
        <el-input v-model="form.title" />
      </el-form-item>
      <el-form-item label="分类" prop="categoryId">
        <el-select v-model="form.categoryId">
          <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="封皮" prop="photoId">
        <el-upload
          class="upload-demo"
          list-type="picture-card"
          action="http://localhost:8004/file/upload"
          :on-success="uploadSuccessHandler"
          :limit="1"
          :file-list="fileList">
          <!-- <el-button size="small" type="primary">点击上传</el-button> -->
          <i class="el-icon-plus"></i>
          <div slot="tip" class="el-upload__tip">文件大小不允许超过3M</div>
        </el-upload>
      </el-form-item>
      <el-form-item label="正文" prop="content">
        <tinymce v-model="form.content" :height="240" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">发布</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import request from '@/utils/request'
import qs from 'querystring'
import Tinymce from '@/components/Tinymce'
import {mapGetters} from 'vuex'
export default {
  data() {
    return {
      form: {},
      categories:[],
      fileList:[],
      rules: {
        categoryId: [ { required: true, message: '请选择栏目', trigger: 'change' } ],
        title: [ { required: true, message: '请输入文章标题', trigger: 'change' } ],
        content: [ { required: true, message: '请输入文章内容', trigger: 'change' } ]
      }
    }
  },
  created() {
    this.form = this.$route.query
    this.loadCategories();
  },
  computed:{
    ...mapGetters(['user'])
  },
  components: { Tinymce },
  methods: {
    back() {
      this.$router.go(-1)
    },
    //加载分类信息
    loadCategories() {
      const url = '/category/findAll'
      request.get(url).then(response => {
        this.categories = response.data
      })
    },
    onSubmit() {
      this.form.authorId = this.user.id;  //用户
      this.form.categoryId = 3;   //风采  
      this.$refs['article_form'].validate((valid) => {
        if (valid) {
          // 交互
          request.request({
            url: '/article/saveOrUpdate',
            method: 'post',
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded'
            },
            data: qs.stringify(this.form)
          })
            .then(response => {
              // 提示成功
              this.$message({ message: response.message, type: 'success' })
              // 返回列表页
              this.back()
            })
        } else {
          return false
        }
      })
    },
    // 上传成功
    uploadSuccessHandler(response){
      if(response.status == 200){
        this.$set(this.form,'cover','http://121.199.29.84:8888/group1/'+response.data.id);
      } else {
        this.$message({type:'error',message:'附件服务器异常！'})
        this.visible = false;
      }
    },
  }
}
</script>

<style scoped>
.editor-content{
  margin-top: 20px;
}
</style>
