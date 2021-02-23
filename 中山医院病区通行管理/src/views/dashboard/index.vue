<template>
  <div class="dashboard">
    <div v-if="checkPermission(['admin'])" style="padding: 5px">
      管理员，您好，欢迎登录。
    </div>
    <el-container v-if="checkPermission(['editor'])">
      <el-main>
        <div class="dashboard_div_left">
          <div class="dashboard-container">
            <p v-for="(i, index) in workBenchData.results" :key="index">
              <br-div-gongzuotai :data="i" />
            </p>
          </div>
        </div>
      </el-main>
      <el-aside>
        <!-- 右侧操作栏 -->
        <div class="dashboard_div_right">
          <div class="br br_button br_primary_button">
            {{ getWardsName() }}
          </div>
          <div class="br br_button br_default_button" @click="PatientReg">
            病员登记
          </div>
          <div class="br br_button br_default_button" @click="AccompanyinReg()">
            陪护登记
          </div>
          <div
            class="br br_button br_default_button"
            :class="{ unallocated: unallocated_flag }"
            style="font-size: 18px"
            @click="Unallocated"
          >
            未分配床位病员({{ workBenchData.no_bed_patient }})
          </div>
          <div class="br br_div_button">
            <el-row>
              <el-col :span="16">本日新增病员</el-col>
              <el-col :span="8">{{ workBenchData.today_patiend_count }}</el-col>
            </el-row>
            <el-row>
              <el-col :span="16">住院病员总数</el-col>
              <el-col :span="8">{{ workBenchData.ward_patient_count }}</el-col>
            </el-row>
          </div>
          <div class="br br_div_button">
            <el-row>
              <el-col :span="16">本日新增陪护</el-col>
              <el-col :span="8">{{
                workBenchData.today_attendant_count
              }}</el-col>
            </el-row>
            <el-row>
              <el-col :span="16">有效陪护总数</el-col>
              <el-col :span="8">{{
                workBenchData.ward_attendant_count
              }}</el-col>
            </el-row>
          </div>
          <div>
            <!-- 陪护体温列表 -->
            <div
              class="br br_div_button br_div_list"
              style="
                margin: 0px;
                font-size: 14px;
                font-weight: bold;
                height: 42px;
                border: none;
                border-bottom: 1px solid rgb(158, 158, 158);
                line-height: 42px;
                padding: 0px;
              "
            >
              陪护通行异常
            </div>
            <div class="div_peihutongxing">
              <!-- {{ traffacRecord.results }} -->
              <p
                v-for="i in traffacRecord.results"
                :key="i.id"
                style="margin: 0"
              >
                <br-div-peihutongxing :data="i" />
              </p>
            </div>
          </div>
        </div>
      </el-aside>
    </el-container>
    <!-- 换床 -->
    <BrHuanchuang />
    <!-- 陪护人员详情 -->
    <BrPeihurenyuan />
    <!-- 分配床位 -->
    <Briupdrawer
      :drawer-visible="setBedDrawer"
      @on-change-visible="handlerVisibleChange"
      title="病床分配"
      width="45%"
      @closed="handleClose"
    >
      <div slot="content">
        <!-- {{ patientData.results }} -->
        <div class="drawer_content drawer_content_detail">
          <!-- 病员信息 -->
          <el-form
            :rules="rules"
            :model="selectForm"
            label-position="left"
            label-width="120px"
            ref="selectForm"
          >
            <el-form-item label="病区名称：">
              {{ getWardsName() }}
            </el-form-item>
            <el-form-item label="床位信息：">
              {{ bedNum }}
            </el-form-item>
            <el-form-item prop="select_value" label="病员信息：">
              <el-select
                v-model="selectForm.select_value"
                filterable
                placeholder="请选择病员"
              >
                <el-option
                  v-for="item in patientData.results"
                  :key="item.id"
                  :label="
                    item.bed_num
                      ? item.bed_num + '-' + item.name + '-' + item.IDCard
                      : '--' + item.name + '-' + item.IDCard
                  "
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel()">取消</el-button>
        <el-button type="primary" @click="setBedHandle()">确定</el-button>
      </span>
    </Briupdrawer>
    <!-- 病员登记抽屉 -->
    <BrBingyuandengji />
    <!-- 病员详情抽屉 -->
    <BrBingyuanxiangqingdrawer />
    <!-- 陪护人员抽屉 -->
    <BrPeihudengji />
    <!-- 更换陪护人员 -->
    <BrGenghuanpeihu />
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import BrDivGongzuotai from "@/components/BrDivGongzuotai";
import BrDivPeihutongxing from "@/components/BrDivPeihutongxing";
import BrHuanchuang from "@/components/BrHuanchuang";
import BrPeihurenyuan from "@/components/BrPeihurenyuan";
import Briupdrawer from "@/components/Briupdrawer";
import BrPeihudengji from "@/components/BrPeihudengji";
import BrBingyuandengji from "@/components/BrBingyuandengji"
import { getRole, getWardsId, getWardsName } from "@/utils/auth";
import checkPermission from "@/utils/permission"; // 权限判断函数
import BrGenghuanpeihu from "@/components/BrGenghuanpeihu"
import BrBingyuanxiangqingdrawer from "@/components/BrBingyuanxiangqingdrawer"
export default {
  name: "Dashboard",
  components: { BrDivGongzuotai, BrDivPeihutongxing, BrHuanchuang, BrPeihurenyuan, Briupdrawer, BrPeihudengji, BrBingyuandengji, BrBingyuanxiangqingdrawer, BrGenghuanpeihu },
  data () {
    return {
      data: {},
      selectForm: {
        select_value: ""
      },
      rules: {
        select_value: [{ required: true, message: '病员信息不能为空', trigger: 'change', }],
      },
      unallocated_flag: false,
      escortDrawer: false,
    };
  },
  computed: {
    ...mapState("dashboard", ["workBenchData", "setBedDrawer", "bedNum", "changePerDrawer"]),
    ...mapState("traffacRecord", ['traffacRecord',]),
    ...mapState("patientManage", ['patientData']),
  },
  created () {
    if (getRole() == '["editor"]') {
      // 如果当前用户不是admin 则发送工作台请求
      this.queryWorkBenchData({ id: getWardsId(), param: { bed_status: 1 } });
      this.queryPersonlmgtThought({ ward_id: getWardsId(), temperature_state: 0 });
    }
  },
  mounted () {
    if (getRole() == '["editor"]') {
      // 如果当前用户不是admin 则发送工作台请求
      // 定时查询陪护通行异常人员信息
      this.getList();
      this.timer = window.setInterval(() => {
        setTimeout(() => {
          this.getList()
        }, 0)
      }, 10000)
    }
  },

  methods: {
    ...mapActions("dashboard", ["queryWorkBenchData", "setBedDrawerHandle", "changePeihudengjiDrawer", "changeBingyuandengjiDrawer", "setChangePerDrawer", "setBedNumHandle"]),
    ...mapActions("traffacRecord", ['queryPersonlmgtThought']),
    ...mapActions("patientManage", [
      "changeBed",
      "queryPatientData",
      "queryEmptyBed",
    ]),
    handleClose () {
      // 清空表单校验结果
      this.$refs.selectForm.resetFields()
    },
    cancel () {
      this.handleClose()
      this.setBedDrawerHandle(false);
      this.selectForm = {}
    },
    handlerVisibleChange () {
      this.setBedDrawerHandle(false);
      this.handleClose()
      this.selectForm = {}
    },
    PatientReg () {
      console.log("打开病员登记抽屉");
      // 查询空床信息
      this.queryEmptyBed({ ward_id: getWardsId() });
      this.changeBingyuandengjiDrawer(true);
    },
    AccompanyinReg (bed_num) {
      console.log("打开陪护登记抽屉");
      this.changePeihudengjiDrawer(true);
      // 查询住院中（已分配床位、未分配床位）病员
      this.queryPatientData({ ordering: "bed_num", ward_id: getWardsId(), hospitalization_status: 0 })
      if (bed_num) {
        this.setBedNumHandle(bed_num)
      }
    },
    Unallocated () {
      console.log("查询未分配床位病员");
      if (this.unallocated_flag) {
        // 有床位病员及空床位
        this.unallocated_flag = false;
        this.queryWorkBenchData({ id: getWardsId(), param: { bed_status: 1 } })
      } else {
        // 无床位病员
        this.unallocated_flag = true;
        this.queryWorkBenchData({ id: getWardsId(), param: { bed_status: 0 } })
      }
    },
    // 定时查询
    getList () {
      // 发送接口
      this.queryPersonlmgtThought({ ward_id: getWardsId(), temperature_state: 0 });
    },
    setBedHandle () {
      this.$refs.selectForm.validate((valid) => {
        if (valid) {
          // 病床分配 发送换床请求
          var data = {
            id: this.selectForm.select_value,
            // name: this.bedForm.name,
            bed_num: this.bedNum,
            ward: getWardsId(),
          };
          this.changeBed(data).then(() => {
            this.queryWorkBenchData({ id: getWardsId(), param: { bed_status: 1 } }).then(() => {
              // this.$router.go(0)
            })
          })
          // 关闭模态框
          this.setBedDrawerHandle(false)
          this.selectForm = {}
          this.handleClose()
        } else {
          return false;
        }
      })
    },
    // 页面指令权限
    checkPermission,
    getWardsName,

  },
  destroyed () {
    window.clearInterval(this.timer)
  },

};
</script>
<style lang="scss">
.dashboard {
  // position: absolute;
  height: calc(100vh - 60px);
  // height: 100%;
  background-color: rgb(236, 239, 242);
  border-radius: 4px;
}
.el-container {
  height: 100%;
}
.dashboard-container {
  width: 1830px;
  // height: 400px;
}
.dashboard_div_left {
  margin: 20px;
  height: calc(100% - 40px);
  background-color: #fff;
  overflow-x: scroll;
  border-radius: 4px;
}
.dashboard_div_right {
  // height:calc(95% - 30px);
  margin: 20px;
  text-align: center;
  float: right;
  // 自定义右侧陪护通行异常小div里的滚动条
  // ::-webkit-scrollbar {
  //   width: 4px;
  //   background-color: #999;
  // }
}
.br {
  width: 185px;
  height: 66px;
  border-radius: 2px;
  margin-bottom: 10px;
  text-align: center;
  background-color: #fff;
  color: #000;
}
.br_button {
  line-height: 66px;
  font-size: 22px;
  font-weight: 1000;
  text-align: center;
}
.br_primary_button {
  background-color: #409eff;
  color: #fff;
}
.br_default_button {
  background-color: #fff;
  color: #000;
  &:hover {
    border: 1px solid #409eff;
    color: #409eff;
    cursor: pointer;
  }
}
.unallocated {
  border: 1px solid #409eff;
  color: #409eff;
  cursor: pointer;
}
.br_div_button {
  font-size: 16px;
  padding: 9px 5px 5px 5px;
  &:hover {
    border: 1px solid #409eff;
  }
}
.div_peihutongxing {
  position: absolute; // 绝对定位
  bottom: 20px;
  top: 520px;
  width: 186px;
  text-align: center;
  // min-height: 200px;
  overflow-y: scroll;
  padding-top: 0;
}
</style>
<style lang="scss" scoped>
.el-main,
.el-aside {
  padding: 0px;
  height: calc(100% - 40px);
}
.el-main {
  // 默认显示滚动条
  ::-webkit-scrollbar {
    width: 0px;
    height: 6px;
    background-color: #fff;
  }
  ::-webkit-scrollbar-thumb {
    background-color: rgb(158, 158, 158);
    border-radius: 3px;
  }
}
.el-aside {
  position: relative;
  margin: 0;
  max-width: 220px !important;
  // 默认显示滚动条
  ::-webkit-scrollbar {
    width: 6px;
    height: 0px;
    background-color: rgba(0, 0, 0, 0);
  }
  ::-webkit-scrollbar-thumb {
    background-color: rgb(158, 158, 158);
    border-radius: 3px;
  }
}

.dashboard-container {
  // 隐藏小div里的滚动条
  ::-webkit-scrollbar {
    width: 0px !important;
    height: 0px !important;
  }
}
.el-col-4 {
  max-width: 220px;
}
.el-col {
  height: 20px;
  border-radius: 4px;
}
</style>