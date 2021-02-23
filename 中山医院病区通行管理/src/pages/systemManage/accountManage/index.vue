<template>
  <div class="account">
    <!-- 左边 -->
    <div class="left_change">
      <el-tabs tab-position="left" style="height: 100%">
        <!-- 用户管理 -->
        <el-tab-pane label="工作账号">
          <el-row>
            <el-col :span="5">
              <!-- 中间 -->
              <div class="center_change">
                <el-button type="primary" plain>操作功能</el-button>
                <!-- 树形空间 -->
                <el-tree
                  :data="data"
                  show-checkbox
                  node-key="id"
                  :default-expanded-keys="[7]"
                  :default-checked-keys="[1, 2, 3, 4]"
                  :props="defaultProps"
                />
              </div>
            </el-col>
            <el-col :span="19">
              <!-- 右侧表格区 -->
              <div class="right_table">
                <!-- 搜索区域 -->
                <el-form
                  :inline="true"
                  :model="formInline"
                  class="demo-form-inline"
                >
                  <el-form-item>
                    <el-input
                      v-model="paramsA.search"
                      placeholder="请输入内容"
                      prefix-icon="el-icon-search"
                      size="small"
                      @input="search"
                    />
                  </el-form-item>
                  <div style="float: right">
                    <el-form-item>
                      <el-button
                        type="primary"
                        icon="el-icon-plus"
                        size="mini"
                        @click="toAddWorkHandler"
                        >添加</el-button
                      >
                    </el-form-item>
                  </div>
                </el-form>
                <!-- 表格 -->
                <el-table
                  :data="account.results"
                  style="width: 100%"
                  :default-sort="{ prop: 'date', order: 'descending' }"
                  size="small"
                  :header-cell-style="{
                    background: '#f5f7fa',
                    color: '#606266',
                  }"
                >
                  <el-table-column
                    prop="ward_info"
                    label="病区"
                    align="center"
                    :formatter="bingquHandler"
                  />
                  <el-table-column
                    prop="username"
                    label="账号"
                    align="center"
                    class="show_img"
                  />
                  <el-table-column prop="is_active" label="状态" align="center">
                    <template slot-scope="scope">
                      <div v-if="scope.row.is_active === true">
                        <el-button type="success" plain size="mini"
                          >有效
                        </el-button>
                      </div>
                      <div v-else>
                        <el-button type="info" plain size="mini"
                          >无效
                        </el-button>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column label="操作" align="center" width="150">
                    <template slot-scope="scope">
                      <el-button
                        type="text"
                        size="small"
                        @click="workEditHandler(scope.row)"
                        >编辑</el-button
                      >
                      <el-button
                        type="text"
                        size="small"
                        @click="workDeleteHandler(scope.row.id)"
                        >删除</el-button
                      >
                    </template>
                  </el-table-column>
                </el-table>

                <!-- 分页 -->
                <div class="changePage">
                  <el-pagination
                    :current-page="paramsA.p"
                    :page-sizes="[5, 10, 15, 20]"
                    :page-size="10"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="account.count"
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                  />
                </div>

                <!-- 抽屉 -->
                <Briupdrawer
                  :drawer-visible="workDrawer"
                  title="工作账号"
                  width="45%"
                  @on-change-visible="handlerVisibleChange"
                >
                  <div slot="content">
                    <div class="drawer_content">
                      <div class="drawer_title">{{ title }}</div>
                      <el-form
                        ref="workForm"
                        :model="workForm"
                        label-width="130px"
                        size="small"
                      >
                        <el-form-item label="病区:">
                          <el-select
                            placeholder="请选择病区"
                            ref="get_address_focus"
                            v-model="workForm.ward"
                          >
                          <el-option v-for="item in ward" :key="item.id" :label="item.name" :value="item.id" />
                          </el-select>
                        </el-form-item>
                        <el-form-item label="账号:">
                          <el-input
                            v-model="workForm.username"
                            placeholder="请输入账号"
                          />
                        </el-form-item>
                        <el-form-item label="密码:">
                          <el-input
                            v-model="workForm.password"
                            placeholder="请输入密码"
                            show-password
                          />
                        </el-form-item>
                        <el-form-item label="状态:">
                          <el-select
                          placeholder="请选择状态"
                            v-model="workForm.is_active"
                            clearable
                            size="small"
                            type="info"
                          >
                            <el-option 
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value" />
                          </el-select>
                        </el-form-item>
                      </el-form>
                    </div>
                  </div>
                  <span slot="footer" class="dialog-footer">
                    <el-button @click="workDrawer = false">取消</el-button>
                    <el-button type="primary" @click="onWorkSubmit"
                      >确定</el-button
                    >
                  </span>
                </Briupdrawer>
              </div>
            </el-col>
          </el-row>
        </el-tab-pane>

        <!-- 配置管理 -->
        <el-tab-pane label="系统账号">
          <el-row>
            <el-col :span="5">
              <!-- 中间 -->
              <div class="center_change">
                <el-button type="primary" plain>操作功能</el-button>
                <!-- 树形空间 -->
                <el-tree
                  :data="data"
                  show-checkbox
                  node-key="id"
                  :default-expanded-keys="[7]"
                  :default-checked-keys="[5, 6, 7]"
                  :props="defaultProps"
                />
              </div>
            </el-col>
            <el-col :span="19">
              <!-- 右侧表格区 -->
              <div class="right_table">
                <!-- 搜索区域 -->
                <el-form
                  :inline="true"
                  :model="formInline"
                  class="demo-form-inline"
                >
                  <el-form-item>
                    <el-input
                      v-model="paramsB.search"
                      placeholder="请输入内容"
                      prefix-icon="el-icon-search"
                      size="small"
                      @input="searchB"
                    />
                  </el-form-item>
                  <div style="float: right">
                    <el-form-item>
                      <el-button
                        type="primary"
                        icon="el-icon-plus"
                        size="mini"
                        @click="toAddConfigurateHandler"
                        >添加</el-button
                      >
                    </el-form-item>
                  </div>
                </el-form>

                <!-- 表格 -->
                <el-table
                  :data="accountB.results"
                  style="width: 100%"
                  :default-sort="{ prop: 'date', order: 'descending' }"
                  size="small"
                  :header-cell-style="{
                    background: '#f5f7fa',
                    color: '#606266',
                  }"
                >
                  <el-table-column
                    prop="username"
                    label="账号"
                    align="center"
                    class="show_img"
                    width="180"
                  />
                  <el-table-column
                    prop="password"
                    label="状态"
                    align="center"
                    show-password
                  >
                    <template slot-scope="scope">
                      <div v-if="scope.row.is_active == true">
                        <el-button type="success" plain size="mini"
                          >有效
                        </el-button>
                      </div>
                      <div v-else>
                        <el-button type="info" plain size="mini"
                          >无效
                        </el-button>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column label="操作" align="center" width="150">
                    <template slot-scope="scope">
                      <el-button
                        type="text"
                        size="small"
                        @click="configurateEditHandler(scope.row)"
                        >编辑</el-button
                      >
                      <el-button
                        type="text"
                        size="small"
                        @click="configurateDeleteHandler(scope.row.id)"
                        >删除</el-button
                      >
                    </template>
                  </el-table-column>
                </el-table>

                <!-- 分页 -->
                <div class="changePage">
                  <el-pagination
                    :current-page="paramsB.p"
                    :page-sizes="[5, 10, 15, 20]"
                    :page-size="10"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="accountB.count"
                    @size-change="handleSizeChangeB"
                    @current-change="handleCurrentChangeB"
                  />
                </div>

                <!-- 抽屉 -->
                <Briupdrawer
                  :drawer-visible="configurateDrawer"
                  title="系统账号"
                  width="45%"
                  @on-change-visible="handlerVisibleChange"
                >
                  <div slot="content">
                    <div class="drawer_content">
                      <div class="drawer_title">{{ title_con }}</div>
                      <el-form
                        ref="configurateForm"
                        :model="configurateForm"
                        label-width="130px"
                        size="small"
                      >
                        <el-form-item label="账号:">
                          <el-input
                            ref="get_name_focus"
                            v-model="configurateForm.username"
                            placeholder="请输入账号"
                          />
                        </el-form-item>
                        <el-form-item label="密码:">
                          <el-input
                            v-model="configurateForm.password"
                            placeholder="请输入密码"
                            show-password
                          />
                        </el-form-item>
                        <el-form-item label="状态:">
                          <el-select
                            v-model="configurateForm.is_active"
                            clearable
                            size="small"
                            type="info"
                          >
                            <el-option 
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value" />
                          </el-select>
                        </el-form-item>
                      </el-form>
                    </div>
                  </div>
                  <span slot="footer" class="dialog-footer">
                    <el-button @click="configurateDrawer = false"
                      >取消</el-button
                    >
                    <el-button type="primary" @click="onConfigurateSubmit"
                      >确定</el-button
                    >
                  </span>
                </Briupdrawer>
              </div>
            </el-col>
          </el-row>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'
import Briupdrawer from '@/components/Briupdrawer'
export default {
  components: {
    Briupdrawer
  },
  data () {
    return {
      options:[
        {
          label:'有效',
          value:true
        },
        {
          label:'无效',
          value:false
        }
      ],
      flag:0,
      paramsA:{
        p:1,
        page_size:10,
        is_manager:false
      },
      paramsB:{
        p:1,
        page_size:10,
        is_manager: true
      },
      workForm: {
        id:'',
        ward:[],
        password:'',
        username:" ",
        is_active:true
      },
      workEye: false,
      workDrawer: false,
      configurateDrawer: false,
      configurateForm: {
        is_manager:true,
        username:"",
        password:"",
      },
      title: '',
      title_con: '',
      formInline: {},
      title: '',
      visible: false,
      data: [{
        id: 1, label: '工作台'
      }, {
        id: 2, label: '病员管理'
      }, {
        id: 3, label: '陪护管理'
      }, {
        id: 4, label: '陪护通行记录管理'
      }, {
        id: 5, label: '病区管理'
      }, {
        id: 6, label: '设备管理'
      }, {
        id: 7,
        label: '系统管理',
        children: [{
          id: 8,
          label: '日志管理'
        }, {
          id: 9,
          label: '账户管理'
        }]
      }],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
    }
  },
  computed: {
    ...mapState('account', ['account', 'ward','accountB'])
  },
  created() {
    // 查询所有的账户
    this.queryAccount(this.paramsA)
    this.queryAccountB(this.paramsB)
    // 查询所有的病区下拉框
    this.queryWards(this.wardparams)
  },
  methods: {
    ...mapActions('account', ['queryAccount','queryAccountB', 'queryWards', 'saveAccount', 'updateAccount', 'deleteAccount']),
    bingquHandler(row,col,cell){
      return cell.map(item => item.name).join(' 、')
    },
    search() {
      this.queryAccount(this.paramsA)
    },
    searchB() {
      this.queryAccountB(this.paramsB)
    },
    // 改变眼睛状态
    toCloseEye () {
      this.workEye = false
    },
    toOpenEye () {
      this.workEye = true
    },
    // 删除 - 配置B
    configurateDeleteHandler (id) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.configurateForm.id = id
          this.deleteAccount(this.configurateForm).then(r=>{
            this.queryAccountB(this.paramsB)
          })
          this.$message({
            message: '请求成功。',
            type: 'success'
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    // 修改 - 配置B
    configurateEditHandler (row) {
      this.flag = 0
      this.configurateDrawer = true
      this.title_con = '编辑账号'
      this.configurateForm = row
      // 聚焦
      setTimeout(() => {
        this.$refs.get_name_focus.focus()
      }, 200)
    },
    // 添加 - 配置B
    toAddConfigurateHandler () {
      this.flag = 1
      this.configurateForm.username = " "
      this.configurateForm.password = " "
      this.configurateForm = {}
      this.configurateForm.is_active=this.options[0].value
      this.configurateDrawer = true
      this.title_con = '添加账号'
      // 聚焦
      setTimeout(() => {
        this.$refs.get_name_focus.focus()
      }, 200)
    },
    // 保存 - 配置B - B为系统账号
    // 编辑保存B
    onConfigurateSubmit () {
      if(this.flag == 0){
        let add_formB = Object.assign({},this.configurateForm)
        this.updateAccount(add_formB)
        .then((res) => {
          this.$message({
            message: res.msg,
            type: 'success'
          })
            this.queryAccountB(this.paramsB)
            this.configurateDrawer = false
        })
        .catch((res) => {
          this.$message({
            type: 'info',
            message: res.msg
          })
        })
      } 
      // 新增保存B
      else if(this.flag == 1) {
        const add_form = Object.assign({}, this.configurateForm)
        add_form.is_manager = true
        this.saveAccount(add_form).then((res)=>{
          this.queryAccountB(this.paramsB)
          this.configurateDrawer = false
           this.$message({
            message: res.msg,
            type: 'success'
          })
        })
        .catch((res) => {
          this.$message({
            type: 'info',
            message: res.msg
          })
        })
      }
    },
    // 保存 - 工作
    // 编辑保存
    onWorkSubmit () {
      if(this.flag == 0){
        let add_form = Object.assign({},this.workForm)
        add_form.ward = []
        add_form.ward.push(this.workForm.ward)
        this.updateAccount(add_form)
        .then((res) => {
          this.$message({
            message: res.msg,
            type: 'success'
          })
          this.queryWards(this.wardparams)
          this.queryAccount(this.paramsA)
          this.workDrawer = false
        })
        .catch((res) => {
          this.$message({
            type: 'info',
            message: res.msg
          })
        })
        this.workDrawer = false
      } 
      // 新增保存
      else if(this.flag == 1) {
        // ————病区不为空
        if(this.workForm.ward != []){
          const add_form = Object.assign({}, this.workForm)
          add_form.is_manager = false
          add_form.ward = []
          add_form.ward.push(this.workForm.ward)
          this.saveAccount(add_form)
          .then((res) => {
            this.$message({
              message: res.msg,
              type: 'success'
            })
            this.queryWards(this.wardparams)
            this.workDrawer = false
            this.queryAccount(this.paramsA)
          })
        }
        // ————病区为空
        else if(this.workForm.ward == []){
          const add_form = Object.assign({}, this.workForm)
          add_form.is_manager = false
          add_form.ward = []
          this.saveAccount(add_form)
          .then((res) => {
            this.$message({
              message: res.msg,
              type: 'success'
            })
            this.queryWards(this.wardparams)
            this.workDrawer = false
            this.queryAccount(this.paramsA)
          })
        }
      }
    },
    // 删除 - 工作
    workDeleteHandler (id) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.workForm.id = id
          this.deleteAccount(this.workForm).then(r=>{
              this.queryAccount(this.paramsA)
          })
          this.$message({
            message: '请求成功。',
            type: 'success'
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    // 编辑 - 工作
    workEditHandler (row) {
      this.flag = 0
      this.workForm = row
      this.workDrawer = true
      this.title = '编辑账号'
      var obj = {ward: 0}
      var wardArr = row.ward_info
      var wardObj = {}
      wardArr.map(function(e,item){
        wardObj[e.id] = e.id
      })
      var val = null 
      for(var key in wardObj){
        val = wardObj[key];
        break;
      }
      Object.keys(wardObj)
      var wardid = wardObj[Object.keys(wardObj)[0]]
      obj.ward = wardid
      this.workForm = Object.assign({},this.workForm,obj)
      this.workForm.ward_info.id = this.workForm.ward
      // 聚焦
      setTimeout(() => {
        this.$refs.get_address_focus.focus()
      }, 200)
    },
    // 添加 - 工作
    toAddWorkHandler () {
      this.flag = 1
      this.workForm.ward = ""
      this.workForm.username = ""
      this.workForm.password = ""
      this.workForm.is_active=this.options[0].value
      this.workDrawer = true
      this.title = '添加账号'
    },
     // 分页
    handleSizeChange(val) {
      this.paramsA.page_size = val
      this.queryAccount(this.paramsA)
    },
    // 改变当前页
    handleCurrentChange(val) {
      this.paramsA.p = val
      this.queryAccount(this.paramsA)
    },
     // 分页B       
    handleSizeChangeB(val) {
      this.paramsB.page_size = val
      this.queryAccountB(this.paramsB)
    },
    // 改变当前页B
    handleCurrentChangeB(val) {
      this.paramsB.p = val
      this.queryAccountB(this.paramsB)
    },
    test () {
      this.visible = true
    },

    // 关闭模态框
    handlerVisibleChange (val) {
      this.workDrawer = val
      this.configurateDrawer = val
    }
  }
}
</script>

<style scoped>
.svg-icon {
  margin-left: 0.3rem;
}
/* 右侧 */
.right_table {
  padding-right: 1rem;
}
/* 抽屉 */
.account >>> .el-drawer__header > :first-child,
.drawer_title {
  color: #272727;
  font-weight: 700;
}
.drawer_title {
  margin-bottom: 1rem;
}
.account >>> .el-divider--horizontal {
  margin: 12px 0;
}
.account >>> .el-drawer__header {
  margin-bottom: 0;
  padding: 10px 0 0 30px;
}
/* 分页 */
.changePage {
  position: absolute;
  bottom: 1rem;
  left: 60%;
  margin: 10px 0 0 -330px;
}
.el-table {
  font-weight: 600;
  font-size: 14px;
  margin-bottom: 3rem;
}
/* 中间 */
.center_change {
  margin-left: 1rem;
  height: 100%;
  display: block;
}
/* 左侧切换 */
.account >>> .el-tabs__nav-scroll {
  margin-left: 4rem;
}
.left_change {
  height: 100%;
}
/* 总体布局 */
.account {
  position: absolute;
  top: 1rem;
  bottom: 0;
  right: 0;
  left: 0;
  overflow: scroll;
}
.el-tabs--left,
.el-tabs--right {
  overflow: scroll;
}
.el-table[data-v-759cb044] {
  margin-bottom: 4rem;
}
</style>
