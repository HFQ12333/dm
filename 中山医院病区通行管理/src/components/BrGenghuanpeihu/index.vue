<template>
  <!-- 陪护人员详情抽屉组件 -->
  <div class="patient">
    <!-- 换人 -->
    <Briupdrawer
      :drawer-visible="genghuanpeihuDrawer"
      title="陪护人员更换"
      width="45%"
      @on-change-visible="handlerVisibleChange"
    >
      <div slot="content">
        <div class="drawer_content drawer_content_detail">
          <!-- {{ form }} -->
          <!-- 病员信息 -->
          <div class="drawer_title">病员信息</div>
          <!-- {{ patientDetail }} -->
          <el-form ref="form" label-width="130px" size="small">
            <Bingyuanxiangqing :detailForm="patientDetail" />
          </el-form>
          <el-form ref="form" label-width="130px" size="small">
            <!-- <Bingyuanxiangqing /> -->
          </el-form>
          <div class="drawer_title" style="margin-top: 1rem">更换陪护人员</div>
          <!-- {{ patientDetail.attendant_info }} -->
          <div class="change_per">
            <el-form ref="form" :model="form" label-width="130px" size="small">
              <el-form-item label="可选陪护人员:">
                <el-row>
                  <el-col
                    :span="5"
                    v-for="i in patientDetail.attendant_info"
                    :key="i.id"
                    ><el-button
                      type="success"
                      plain
                      @click="attendantId = i.id"
                    >
                      {{
                        i.name.length > 4
                          ? i.name.substring(0, 3) + "..."
                          : i.name
                      }}
                    </el-button></el-col
                  >
                  <el-col :span="14" v-if="patientDetail.attendant_info">
                    <el-button
                      plain
                      class="dashBtn"
                      v-if="patientDetail.attendant_info.length < 3"
                      icon="el-icon-plus"
                      @click="dengJi()"
                      >登记</el-button
                    >
                  </el-col>
                </el-row>
              </el-form-item>
              <el-form-item label="陪护开始日期:">
                <el-date-picker
                  v-model="form.start_time"
                  type="date"
                  placeholder="请选择陪护开始日期"
                  style="width: 100%"
                />
              </el-form-item>
              <el-form-item label="陪护结束日期:">
                <el-date-picker
                  v-model="form.end_time"
                  type="date"
                  placeholder="请选择陪护结束日期"
                  style="width: 100%"
                />
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="changeGenghuanpeihuDrawer(false)">取消</el-button>
        <el-button type="primary" @click="onSubmit()">确定</el-button>
      </span>
    </Briupdrawer>
  </div>
</template>

<script>
import Briupdrawer from "@/components/Briupdrawer";
import Bingyuanxiangqing from "@/components/Bingyuanxiangqing";
import { mapState, mapActions } from "vuex";
import { getWardsId, getWardsName } from "@/utils/auth";
import moment from "moment"
export default {
  // props: ["attendantDetail"],
  components: {
    Briupdrawer,
    Bingyuanxiangqing,
  },
  data () {
    return {
      flag: 2,
      detailForm: {},
      ward_name: getWardsName(),
      attendantId: "",
      form: {
        start_time: new Date().toLocaleDateString(),
      }
    };
  },
  created () {

  },
  computed: {
    ...mapState("dashboard", ["changeBedDrawer",
      "genghuanpeihuDrawer",
    ]),
    ...mapState("patientManage", [
      "emptyBed",
      "patientDetail",
      "attendantDetail",
      "attendantThrough",
    ]),
    ...mapState("accompany", ['accompany', 'accompany_id']),
    ...mapState("traffacRecord", ['traffacRecord', 'traffacRecord_id']),

  },
  methods: {
    ...mapActions("dashboard", ["changeBedDrawerHandle", "queryWorkBenchData",
      "changeDetailDrawerHandle",
      "changeSetAttendantDetailDrawre",
      "changeGenghuanpeihuDrawer",
      "changePeihudengjiDrawer",

    ]),
    ...mapActions("patientManage", ["queryPatientData",]),
    ...mapActions("traffacRecord", ['updateTraffcRecord', "queryPersonlmgtThought"]),
    ...mapActions("accompany", ['updateAccompany']),
    // 处理visible变化的钩子函数，解决抽屉无法收缩的bug
    handlerVisibleChange (val) {
      this.changeGenghuanpeihuDrawer(false)
      this.form = {}
      // this.changeBedDrawer = val;
    },
    // 登记新陪护
    dengJi () {
      // 打开登记陪护抽屉
      this.changePeihudengjiDrawer(true)
      // 关闭当前抽屉
      this.changeGenghuanpeihuDrawer(false)
    },
    // 新增或修改陪护
    onSubmit () {
      // 修改陪护
      console.log("修改陪护");
      this.form.id = this.attendantId;
      this.form.ward_id = getWardsId();
      this.form.state = 1;
      console.log(this.form);
      let form = Object.assign({}, this.form)
      console.log(form);

      // form.state = Number(form.state)
      form.start_time = moment(form.start_time).format('YYYY-MM-DD hh:mm:ss')
      form.end_time = moment(this.form.end_time).format('YYYY-MM-DD hh:mm:ss')
      console.log(form);
      // 发送修改陪护请求
      this.updateAccompany(form).then(() => {
        // 关闭模态框
        this.changeGenghuanpeihuDrawer(false)
        // 查询工作台信息
        this.queryWorkBenchData({ id: getWardsId(), param: { bed_status: 1 } })
      })

    }
  }
};
</script>

<style scoped>
.patient >>> .el-calendar-day {
  position: relative !important;
}
.patient_drawer >>> .el-input {
  width: 100%;
}
.patient >>> .el-loading-spinner .circular {
  height: 20px;
  width: 20px;
}
/* 进度条病员登记 */
.escortSuccess div:nth-child(3) {
  font-weight: 500;
  font-size: 14px;
}
.escortSuccess div:nth-child(2) {
  font-size: 22px;
  margin: 1.2rem 0 1rem 0;
  font-weight: bolder;
}
.el-steps--horizontal {
  padding: 0 25%;
  height: 60px;
  margin-top: 2rem;
}
.escortSuccess {
  height: 200px;
  position: relative;
  margin-top: 4rem;
}
.escortSuccess div {
  text-align: center;
}
.escortSuccess div:first-child {
  height: 70px;
}

.escortSuccess div:first-child .svg-icon {
  width: 60px;
  height: 60px;
  position: absolute;
  left: 50%;
  margin-left: -35px;
}
/* 去表格边框 */
.patient >>> .el-table {
  border: none;
}
.patient >>> .el-table::after {
  width: 0;
}
.patient >>> .el-table td,
.patient >>> .el-table--border th,
.patient >>> .el-table--border td {
  border-right: none !important;
}
.patient >>> .el-table__header-wrapper th {
  border-right: none !important;
  border-left: none !important;
}

.svg_icon_text {
  float: left;
  margin-left: 0.2rem;
}
.drawer_escort_detail >>> .svg-icon {
  float: left;
  line-height: 32px !important;
  height: 18px !important;
  margin-top: 0.5rem;
}
/* 详情 */
/* 放大图片 */
.loading_more >>> .el-button {
  margin-left: 43%;
  margin-bottom: 1rem;
}
.table_img {
  margin: 0 1rem 0 0.8rem;
  float: left;
  line-height: 23px;
  padding: 0;
}
.center_table >>> .el-table--small th,
.center_table >>> .el-table--small td {
  padding: 5px 0 0 0;
}

.big_img img {
  display: none;
  position: absolute;
  top: 0;
  z-index: 9999;
  right: -120%;
}
.show_img:hover .big_img img {
  display: block;
}
.patient >>> .is-scrolling-none tr:nth-last-child(-n + 4) .big_img img {
  top: -400%;
  z-index: 9999;
  right: -120%;
}
.patient >>> .is-scrolling-none tr:nth-last-child(-n + 4) .big_img_2 img {
  top: -800%;
  z-index: 9999;
  left: -180%;
}

.big_img_2 {
  height: 25px;
  width: 100px;
  position: absolute;
}
.big_img_2 img {
  display: none;
  position: absolute;
  top: 0;
  z-index: 9999;
  left: -180%;
}
.show_img:hover .big_img_2 img {
  display: block;
}

.drawer_content_detail >>> .svg-icon {
  float: left;
  height: 18px;
}
.svg_icon_text {
  float: left;
  margin-left: 0.2rem;
}
.drawer_content_detail .el-row {
  font-size: 14px;
}
.drawer_content_detail .el-row:first-child .el-col div,
.bed_details .el-col div {
  margin-bottom: 0.5rem;
}
.div_center >>> .svg-icon {
  margin-top: 0.5em;
}
.div_center div {
  height: 32px;
  line-height: 32px;
}
.drawer_content_detail {
  padding: 0 !important;
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
.patient >>> .el-drawer {
  overflow: auto;
}
.patient >>> .el-drawer__wrapper {
  overflow: auto;
}
.drawer_content {
  padding: 0 30px;
}
.svg-icon {
  width: 1.2rem;
  height: 1.2rem;
}
.patient >>> .el-drawer__header > :first-child,
.drawer_title {
  color: #272727;
  font-weight: 700;
}
.drawer_title {
  margin: 0 0 1rem 1rem;
}
.patient >>> .el-divider--horizontal {
  margin: 12px 0;
}
.patient >>> .el-drawer__header {
  margin-bottom: 0;
  padding: 10px 0 0 30px;
}
.changeEscort {
  margin-left: 1rem;
  color: #1890ff;
  cursor: pointer;
}
.editCell:hover {
  cursor: pointer;
}
.el-table {
  font-weight: 600;
  font-size: 14px;
  margin-bottom: 3rem;
}

.gender {
  margin-right: 0.5rem;
  display: inline-block;
}
.header .el-button {
  font-size: 14px;
  font-weight: 700;
}
</style>