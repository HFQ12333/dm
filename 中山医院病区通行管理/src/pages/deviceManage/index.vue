<template>
  <div class="device">
    <!-- 搜索区域 -->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item>
        <el-select
          v-model="params.ward"
          size="small"
          type="info"
          class="serach_select"
          placeholder="全部病区"
          @change="serach"
        >
          <el-option
            label="全部病区"
            value=""
          />
          <el-option
            v-for="item in ward"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-input
          v-model="params.search"
          placeholder="请输入内容"
          prefix-icon="el-icon-search"
          size="small"
          @input="serach"
        />
      </el-form-item>
      <div style="float: right">
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="toAddHandler"
          >添加</el-button>
        </el-form-item>
      </div>
    </el-form>
    <!-- 表格 -->
    <el-table
      @sort-change="sortHandler"
      :data="device.results"
      style="width: 100%"
      :default-sort="{ prop: 'no', order: 'descending' }"
      size="small"
      :header-cell-style="{
        background: '#f5f7fa',
        color: '#606266',
      }"
    >
      <el-table-column
        prop="no"
        label="设备号"
        :sortable="'custom'"
        width="180"
        align="center"
      />
      <el-table-column
        prop="ward_info.name"
        label="病区"
        align="center"
      />
      <el-table-column
        prop="name"
        label="设备名称"
        align="center"
        class="show_img"
      />
      <el-table-column
        prop="address"
        label="设备地址"
        :sortable="'custom'"
        align="center"
      />
      <el-table-column label="操作" align="center" width="150">
        <template slot-scope="scope">
          <el-button
            type="text"
            size="small"
            @click="editHandler(scope.row)"
          >编辑</el-button>
          <el-button
            type="text"
            size="small"
            @click="deleteHandler(scope.row.id)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div class="changePage">
      <el-pagination
        :current-page="params.p"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="10"
        layout="total, sizes, prev, pager, next, jumper"
        :total="device.count"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 录入抽屉 -->
    <Briupdrawer
      :drawer-visible="drawer"
      title="设备管理"
      width="45%"
      @on-change-visible="handlerVisibleChange"
    >
      <div slot="content">
        <div class="drawer_content">
          <div class="drawer_title">{{ title }}</div>
          <el-form ref="form" :model="form" label-width="130px" size="small">
            <el-form-item label="设备号:">
              <el-input v-model="form.no" placeholder="请输入设备号" />
            </el-form-item>
            <el-form-item label="病区:">
              <el-select v-model="form.ward" >
                <el-option v-for="item in ward" :key="item.id" :label="item.name" :value="item.id" />
              </el-select>
            </el-form-item>
            <el-form-item label="设备名称:">
              <el-input v-model="form.name" placeholder="请输入设备名称" />
            </el-form-item>
            <el-form-item label="设备地址:">
              <el-input v-model="form.address" placeholder="请输入设备地址" />
            </el-form-item>
          </el-form>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="drawer = false">取消</el-button>
        <el-button type="primary" @click="onSubmit">确定</el-button>
      </span>
    </Briupdrawer>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import Briupdrawer from '@/components/Briupdrawer'
import log from '@/store/modules/log'
export default {
  components: {
    Briupdrawer
  },
  data() {
    return {
      // 查询所有的设备传递参数
      params: {
        p: 1,
        page_size: 10
      },
      // 查询所有的病区传递参数
      wardparams: {
        p: 1,
        page_size: 10000
      },
      value: '2',
      title: '',
      form: {
        id: '',
        ward:''
      },
      drawer: false,
      formInline: {},
      flag: 0
    }
  },
  computed: {
    ...mapState('device', ['device', 'ward'])
  },
  created() {
    // 查询所有的设备
    this.queryDeivces(this.params)
    // 查询所有的病区下拉框
    this.queryWards(this.wardparams)
  },
  methods: {
    ...mapActions('device', ['queryDeivces', 'queryWards', 'saveDevice', 'updateDevice', 'deleteDevice']),
    // 排序
    sortHandler (column) {
      if (column.column.label == '设备号') {
        if (column.order == 'ascending') { //上箭头
          var obj = {
            ordering: 'no'
          }
        } else if (column.order == "descending") { // 下箭头
          var obj = {
            ordering: '-no'
          }
        }
        this.params = Object.assign(this.params, obj)
        this.queryDeivces(this.params)
      } else if (column.column.label == '设备地址') {
        if (column.order == 'ascending') { //上箭头
          var obj = {
            ordering: 'address'
          }
        } else if (column.order == "descending") { // 下箭头
          var obj = {
            ordering: '-address'
          }
        }
        this.params = Object.assign(this.params, obj)
        this.queryDeivces(this.params)
      }
    },
    serach() {
      this.queryDeivces(this.params)
    },
    // 编辑
    editHandler(row) {
      this.flag = 0
      this.drawer = true
      this.title = '编辑设备'
      this.form=row
      var obj = {ward: 0}
      obj.ward=row.ward_info.id
      this.form = Object.assign({},this.form,obj)
      this.form.ward_info.id = this.form.ward
    },
    // 删除
    deleteHandler(id) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.form.id = id
        this.deleteDevice(this.form).then((res) => {
          this.queryDeivces(this.params)
          this.$message({
            message: res.msg,
            type: 'success'
          })
        })
      })
        .catch(() => {
          this.$message({
            message: '已取消删除',
            type: 'info'
          })
        })
    },
    // 编辑保存
    onSubmit() {
      if (this.flag == 0) {
        let form = Object.assign({},this.form)
        this.updateDevice(form).then((res) => {
          this.queryDeivces(this.params)
          this.drawer = false
        })
      } 
      // 新增保存
      else if (this.flag == 1) {
        const add_form = Object.assign({}, this.form)
        add_form.state = 1
        add_form.ward=this.form.ward
        this.saveDevice(add_form)
        .then(()=>{
          this.queryWards(this.wardparams)
          this.drawer = false
          this.queryDeivces(this.params)
        })
      }
    },
    // 添加
    toAddHandler() {
      this.flag = 1
      this.form = {}
      this.title = '添加管理'
      this.drawer = true
    },
    // 关闭模态框
    handlerVisibleChange(val) {
      this.drawer = val
    },
    // 分页
    handleSizeChange(val) {
      this.params.page_size = val
      this.queryDeivces(this.params)
    },
    // 改变当前页
    handleCurrentChange(val) {
      this.params.p = val
      this.queryDeivces(this.params)
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
  left: 50%;
  margin: 0 0 0 -202px;
}
.el-table {
  font-weight: 600;
  font-size: 14px;
  margin-bottom: 3rem;
}
</style>
