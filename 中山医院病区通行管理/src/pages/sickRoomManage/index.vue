<template>
  <div class="sickRoom">
    <!-- 搜索区域 -->
    <el-form :inline="true" :model="params" class="demo-form-inline">
      <el-form-item>
        <el-input
          v-model="params.search"
          placeholder=" 请输入信息搜索 "
          prefix-icon="el-icon-search"
          size="small"
        />
      </el-form-item>
    </el-form>

    <el-row>
      <!-- 添加按钮 -->
      <el-col :span="6" class="list_padding">
        <el-button
          plain
          icon="el-icon-plus"
          class="dashBtn"
          @click="toAddHandler"
          >添加</el-button
        >
      </el-col>
      <!-- 病区信息列表 -->
      <el-col
        v-for="item in sickroom.results"
        :key="item.id"
        :span="6"
        class="list_padding"
      >
        <el-card shadow="hover">
          <div class="my_div div_two">
            <div class="sickRoom_list_title">
              {{ item.name_abbr }} - {{ item.name }}
            </div>
            <!-- 床位信息详情 -->
            <div class="sickRoom_list_details">
              <div>床位数：{{ item.bed_num }}个</div>
              <div>加床数：{{ item.added_num }}个</div>
              <div>加床前缀：{{ item.added_prefix }}</div>
              <div>陪护人员上限：{{ item.attend_limit }}人次</div>
              <div>病区描述：{{ item.desc }}</div>
            </div>
            <div class="divider_list" />
            <div class="sickRoom_device_title">设备名称：</div>
            <!-- 设备名称 -->
            <el-row class="sickRoom_tag">
              <el-col
                v-for="i_device in item.device_info"
                :key="i_device.id"
                :span="12"
              >
                <el-tag type="info" size="mini">{{ i_device.name }}</el-tag>
              </el-col>
            </el-row>
            <div class="divider_list" />
            <div class="sickRoom_device_title">工作账号：</div>
            <el-row class="sickRoom_tag_2">
              <el-col>
                <el-tag v-if="item.user_info[0]" type="info" size="mini">{{
                  item.user_info[0].username
                }}</el-tag>
              </el-col>
            </el-row>
            <div class="sickRoom_btn">
              <el-row>
                <el-col>
                  <el-button
                    type="primary"
                    plain
                    size="small"
                    @click="deleteHandle(item.id)"
                    >删除</el-button
                  >
                </el-col>
                <!-- <el-col :span="12">
                  <el-button
                    type="primary"
                    plain
                    size="small"
                    @click="editHandler(item)"
                    >编辑</el-button
                  >
                </el-col> -->
              </el-row>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 抽屉 -->
    <Briupdrawer
      :drawer-visible="drawer"
      :title="title"
      width="45%"
      @on-change-visible="handlerVisibleChange"
      @closed="handleClose"
    >
      <div slot="content">
        <div class="drawer_content">
          <el-form
            ref="form"
            :model="form"
            label-width="130px"
            size="small"
            :rules="rules"
            class="demo-form-inline"
          >
            <el-form-item label="病区全称:" prop="name">
              <el-input v-model="form.name" placeholder="请输入病区全称" />
            </el-form-item>
            <el-form-item label="病区简称:" prop="name_abbr">
              <el-input v-model="form.name_abbr" placeholder="请输入病区简称" />
            </el-form-item>
            <el-form-item
              label="床位数:"
              prop="bed_num"
              :rules="[
                { required: true, message: '床位数不能为空' },
                { type: 'number', message: '床位数必须为数字值' },
              ]"
            >
              <el-input
                v-model.number="form.bed_num"
                placeholder="请输入床位数"
              />
            </el-form-item>
            <el-form-item label="加床数:">
              <el-input v-model="form.added_num" placeholder="请输入加床数" />
            </el-form-item>
            <el-form-item label="加床前缀:" prop="added_prefix">
              <el-input v-model="form.added_prefix" placeholder="加床前缀" />
            </el-form-item>
            <el-form-item label="陪护人员上限:">
              <el-input
                ref="get_tempture_focus"
                v-model="form.attend_limit"
                placeholder="请输入人次"
              >
                <i slot="suffix">人次 </i>
              </el-input>
            </el-form-item>
            <el-form-item label="病区描述:">
              <el-input v-model="form.desc" placeholder="病区描述" />
            </el-form-item>
            <el-form-item label="设备名称:">
              <el-select
                v-model="form.device"
                filterable
                multiple
                placeholder="请点击或者搜索已有设备"
                popper-class="escort_select"
              >
                <el-option
                  v-for="item in device.results"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="工作账号:">
              <el-select
                v-model="form.user"
                filterable
                placeholder="请点击或者搜索已有工作账号"
                popper-class="escort_select"
              >
                <el-option
                  v-for="item in account.results"
                  :key="item.id"
                  :label="item.username"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="drawer = false">取消</el-button>
        <el-button size="small" type="primary" @click="onSubmit"
          >确定</el-button
        >
      </span>
    </Briupdrawer>
  </div>
</template>

<script>
import Briupdrawer from '@/components/Briupdrawer'
import { mapActions, mapState } from 'vuex'
// import BrDivBingquguanli from "@/components/BrDivBingquguanli";
export default {
  components: {
    Briupdrawer
  },
  data () {
    return {
      flag: 0,
      title: '',
      params: {
        page: 1,
        page_size: 1000
      },
      form: {
        // device: [],
        // user: [],


      },
      drawer: false,
      rules: {
        name: [
          { required: true, message: '病区全称不能为空', trigger: 'blur' }
        ],
        name_abbr: [
          { required: true, message: '病区简称不能为空', trigger: 'blur' }
        ],
        bed_num: [
          { required: true, message: '床位数不能为空', trigger: 'change' }
        ],
        added_prefix: [
          { required: true, message: '加床前缀不能为空', trigger: 'change' }
        ]
      },
      flag: true,
      input1: ''
    }
  },
  computed: {
    ...mapState('sickRoom', ['sickroom']),
    ...mapState('device', ['device']),
    ...mapState('account', ['account'])
  },
  watch: {
    params: {
      handler () {
        this.querySickRoom(this.params)
      },
      deep: true
    }
  },
  created () {
    // 查询病区
    this.querySickRoom(this.params)
    // 查询所有设备
    this.queryDeivces(this.params)
    // 查询账号
    this.queryAccount(this.params)
  },
  methods: {
    ...mapActions('sickRoom', ['querySickRoom', 'saveSickRoom', 'deleteSickRoom', 'updateSickRoom']),
    ...mapActions('device', ['queryDeivces']),
    ...mapActions('account', ['queryAccount']),
    // 清空表单校验
    handleClose () {
      this.$refs.form.resetFields()
    },
    // 保存
    onSubmit () {
      if (this.flag == 0) {
        // 修改保存
        const arr = []
        arr.push(this.form.user)
        this.form.user = arr
        this.form = Object.assign({}, this.form, arr)
        console.log(this.form);
        this.updateSickRoom(this.form).then((res) => {

          this.querySickRoom(this.params)
          this.drawer = false
        })
      } else if (this.flag == 1) {
        // 新增保存
        this.saveSickRoom(this.form).then((res) => {
          // if (res.code == 202) {
          //   this.$message({
          //     message: res.msg,
          //     type: 'warning'
          //   })
          // } else if (res.code == 200) {
          //   this.$message({
          //     message: res.msg,
          //     type: 'success'
          //   })
          // }
          this.querySickRoom(this.params)
          this.drawer = false
        })
      }
    },

    // 修改
    editHandler (row) {
      this.drawer = true
      this.title = '修改病区'
      this.flag = 0
      this.form = row
      var obj = { deivice: [] }
      this.form.device = []
      for (var item of row.device_info) {
        this.form.device.push(item.id)
      }
      this.form.user = row.user_info[0].id
      obj.device = this.form.device
      this.form = Object.assign({}, this.form, obj)
    },

    // 添加
    toAddHandler () {
      this.drawer = true
      this.title = '添加病区'
      this.flag = 1
      this.form = {
        bed_num: 50,
        added_num: 3,
        added_prefix: 10,
        attend_limit: 3
      }

    },

    // 删除
    deleteHandle (id) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then((res) => {
          this.deleteSickRoom({ id }).then((res) => {
            console.log(res);
            if (res.code == 200) {
              this.$message({
                type: 'success',
                message: res.msg
              })
              this.querySickRoom(this.params)
            } else {
              this.$message.error(res.msg);
            }
          })

        })
        .catch((err) => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },

    // 关闭模态框
    handlerVisibleChange (val) {
      this.drawer = val
      this.handleClose()
    }
  }
}
</script>

<style scoped>
/* 抽屉 */
.drawer_content >>> .el-input,
.drawer_content >>> .el-select {
  width: 100%;
}
.sickRoom >>> .el-drawer__header > :first-child,
.drawer_title {
  color: #272727;
  font-weight: 700;
}
.drawer_title {
  margin-bottom: 1rem;
}
.sickRoom >>> .el-divider--horizontal {
  margin: 12px 0;
}
.sickRoom >>> .el-drawer__header {
  margin-bottom: 0;
  padding: 10px 0 0 30px;
}
/* 信息列表 */
.sickRoom_tag .el-col-7 {
  float: left;
  margin-bottom: 0.2rem;
}
.sickRoom_btn {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
}
.sickRoom_btn .el-button {
  width: 100%;
  border-radius: 0;
}
.sickRoom_tag_2 {
  /* margin: 1rem 0 1.5rem 0; */
}
.sickRoom_device_title {
  font-weight: 700;
  color: #5a5a5a;
  margin: 0.8rem 0;
}
.divider_list {
  height: 1px;
  border-bottom: 1px dashed #ccc;
  margin: 1rem 0;
}
.sickRoom_list_details div {
  font-size: 14px;
  color: #4a4a4a;
  margin-bottom: 0.3rem;
}
.sickRoom_list_title {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 1rem;
}
/* 添加 */
.drawer_content .dashBtn:hover,
.dashBtn:focus {
  border-color: #1890ff;
}
.dashBtn {
  border: 1px dashed #dcdfe6;
  width: 100%;
  height: 390px;
}
/* 卡片 */
.list_padding {
  padding: 1rem;
}
.el-card.is-hover-shadow:hover,
.el-card.is-hover-shadow:focus {
  box-shadow: 0px 0 3px 1px #108ee9;
}
.el-card {
  position: relative;
  height: 390px;
  /* border-bottom-left-radius: 0;
  border-bottom-right-radius: 0; */
}
.demo-form-inline {
  margin-left: 1rem;
  height: 40px;
}
</style>
