<template>
  <!-- 分配/换床 -->
  <div class="patient">
    <Briupdrawer
      :drawer-visible="changeBedDrawer"
      @on-change-visible="changeBedDrawerHandle"
      title="病员分配床位"
      width="45%"
    >
      <div slot="content">
        <div class="drawer_content drawer_content_detail">
          <!-- 病员信息 -->
          <div class="drawer_title">病员信息</div>
          <el-form ref="form" label-width="130px" size="small">
            <Bingyuanxiangqing :detailForm="patientDetail" />
          </el-form>
          <!-- 可换床位 -->
          <div class="drawer_title" style="margin-top: 1rem">可分配床位</div>
          <div class="change_bed">
            <el-button
              type="primary"
              v-for="item in emptyBed.empty_bed_list"
              :key="item"
              plain
              @click="myChangeBedHandler(item)"
              >{{ item }}</el-button
            >
          </div>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button
          @click="
            changeBedDrawerHandle(false);
            form = {};
          "
          >取消</el-button
        >
      </span>
    </Briupdrawer>
  </div>
</template>

<script>
import Briupdrawer from "@/components/Briupdrawer";
import Bingyuanxiangqing from "@/components/Bingyuanxiangqing";
import { mapState, mapActions } from "vuex";
import { getWardsId } from "@/utils/auth";
export default {
  inject: ['reload'],
  components: {
    Briupdrawer,
    Bingyuanxiangqing,
  },
  data () {
    return {
      flag: 2,
    };
  },
  created () {

  },
  computed: {
    ...mapState("dashboard", ["changeBedDrawer"]),
    ...mapState("patientManage", [
      "emptyBed",
      "patientDetail",

    ]),
  },
  methods: {
    ...mapActions("dashboard", ["changeBedDrawerHandle", "queryWorkBenchData"]),
    ...mapActions("patientManage", ["changeBed", "queryPatientData"]),
    // 处理visible变化的钩子函数，解决抽屉无法收缩的bug
    handlerVisibleChange (val) {
      this.changePerDrawer = val;
      this.changeBedDrawer = val;
      this.drawer = val;
      this.detailDrawer = val;
      this.detailEscortDrawer = val;
      this.escortDrawer = val;
      this.printPatientDrawer = val;
    },
    // 点击床位按钮换床
    myChangeBedHandler (bed_num) {
      // 关闭换床模态框
      this.changeBedDrawerHandle(false);
      // 发送换床请求
      var data = {
        id: this.patientDetail.id,
        // name: this.patientDetail.name,
        bed_num: bed_num,
        ward: getWardsId(),
      };
      this.changeBed(data).then(() => {
        this.queryWorkBenchData({ id: getWardsId(), param: { bed_status: 1 } })
        this.queryPatientData({
          page: 1,
          page_size: 10,
          ward_id: getWardsId(),
          hospitalization_status: "0",
          bed_status: "",
          search: "",
        })
        this.form = {}
        this.reload();
        // this.$router.go(0)
      })
    },
  },
};
</script>

<style scoped>
/* 换床 */
.change_bed,
.change_per {
  padding: 1rem 2rem;
}
.change_bed .el-button {
  width: 80px;
  height: 50px;
  font-size: 22px;
  font-weight: bolder;
  margin: 0 0.8rem 1.5rem 0;
}
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