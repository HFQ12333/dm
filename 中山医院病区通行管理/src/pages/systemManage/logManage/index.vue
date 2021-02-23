<template>
  <div class="log">
    <!-- 搜索区域 -->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item>
        <el-input
          v-model="params.search"
          placeholder=" 请输入信息搜索 "
          prefix-icon="el-icon-search"
          size="small"
          @input="serach"
        />
      </el-form-item>
    </el-form>

    <!-- 表格 -->
    <el-table
      @sort-change="sortHandler"
      :data="log.results"
      style="width: 100%"
      :default-sort="{ prop: 'created_time', order: 'descending' }"
      size="small"
      :header-cell-style="{ background: '#f5f7fa', color: '#606266' }"
    >
      <el-table-column
        prop="created_time"
        label="时间"
        sortable
        width="180"
        align="center"
      />
      <el-table-column
        prop="user_info.username"
        label="账号"
        align="center"
        class="show_img"
      />
      <el-table-column prop="app_name" label="页面" align="center" />
      <el-table-column prop="content" label="操作" align="center" />
    </el-table>

    <!-- 分页 -->
    <div class="changePage">
      <el-pagination
        :current-page="params.p"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="10"
        layout="total, sizes, prev, pager, next, jumper"
        :total="log.count"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
  data() {
    return {
      // 查询所有的日志
      params: {
        p: 1,
        page_size: 10,
      },
      formInline: {},
    }
  },
  computed: {
    ...mapState('log', ['log']  ),
  },
  created() {
    // 查询所有的日志
    this.queryLogs(this.params)
  },
  methods: {
    ...mapActions('log',['queryLogs']),
      // 排序
    sortHandler (column) {
      if (column.column.label == '时间') {
        if (column.order == 'ascending') { //上箭头
          var obj = {
            ordering: 'created_time'
          }
        } else if (column.order == "descending") { // 下箭头
          var obj = {
            ordering: '-created_time'
          }
        }
        this.params = Object.assign(this.params, obj)
        this.queryLogs(this.params)
      }
    },
    serach() {
      this.queryLogs(this.params)
    },
    // 分页
    handleSizeChange(val) {
      this.params.page_size = val
      this.queryLogs(this.params)
    },
    // 改变当前页
    handleCurrentChange(val) {
      this.params.p = val
      this.queryLogs(this.params)
    }
  }
}
</script>

<style scoped>
.device >>> .search_select .el-input--small .el-input__inner {
  background-color: #529cf8;
  color: #fff;
  font-weight: 600;
}
.device >>> .search_select .el-input .el-select__caret {
  color: #fff;
  font-weight: 600;
}
.el-drawer .el-form .el-form-item:nth-child(1) .el-select {
  width: 100%;
}
.el-drawer .el-form .el-form-item:last-child {
  position: absolute;
  bottom: 1rem;
  right: 2rem;
}
.fall div:nth-child(2),
.fall div:nth-child(1) {
  margin-bottom: 1rem;
}
.fall {
  position: absolute;
  bottom: 4rem;
  right: 2rem;
}
.drawer_content .dashBtn:hover,
.dashBtn:focus {
  border-color: #1890ff;
}
.dashBtn {
  border: 1px dashed #dcdfe6;
  width: 100%;
}
.drawer_title_text {
  margin-left: 20px;
  color: #272727;
  font-weight: bolder;
}
.el-form-item {
  margin-bottom: 14px;
}
.device >>> .el-drawer {
  overflow: auto;
}
.device >>> .el-drawer__wrapper {
  overflow: auto;
}
.drawer_content {
  padding: 0 30px;
}
.svg-icon {
  width: 1.3rem;
  height: 1.3rem;
}
.device >>> .el-drawer__header > :first-child,
.drawer_title {
  color: #272727;
  font-weight: 700;
}
.drawer_title {
  margin-bottom: 1rem;
}
.device >>> .el-divider--horizontal {
  margin: 12px 0;
}
.device >>> .el-drawer__header {
  margin-bottom: 0;
  padding: 10px 0 0 30px;
}
.changePage {
  position: absolute;
  /* bottom: 150px; */
  left: 50%;
  margin: -20px 0 0 -300px;
}
.el-table {
  font-weight: 600;
  font-size: 14px;
  margin-bottom: 3rem;
}
</style>
