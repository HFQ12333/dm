<template>
  <!-- 病员详情  -->
  <div class="patient">
    <Briupdrawer
      :drawer-visible="patientDetailDrawer"
      title="病员详情"
      width="45%"
      @on-change-visible="handlerVisibleChange"
    >
      <el-divider />
      <div slot="content">
        <div class="drawer_content drawer_content_detail">
          <el-form
            ref="form"
            :model="detailForm"
            label-width="130px"
            size="small"
          >
            <!-- 上部详情 -->
            <!-- {{ patientDetail }} -->
            <!-- 病员详情组件 -->
            <Bingyuanxiangqing :detailForm="patientDetail" />
            <!-- 中部详情表格 -->
            <div class="center_table">
              <div class="drawer_title_text">陪护人员</div>
              <!-- 查询病员详情中的陪护人员 -->
              <el-table
                :data="patientDetail.attendant_info"
                style="width: 100%"
                :default-sort="{ prop: 'date', order: 'descending' }"
                size="small"
                :header-cell-style="{
                  background: '#f5f7fa',
                  color: '#606266',
                }"
              >
                <el-table-column
                  prop="name"
                  label="陪护姓名"
                  align="center"
                  class="show_img"
                >
                  <template slot-scope="scope">
                    <div class="show_img">
                      <div class="table_img">
                        <img :src="scope.row.face_data" alt="" />
                      </div>
                      <div class="table_img_name">
                        {{ scope.row.name }}
                      </div>
                      <div class="big_img">
                        <img
                          :src="scope.row.face_data"
                          alt=""
                          style="width: 200px; height: 200px"
                        />
                      </div>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column label="疫情检测信息" align="center">
                  <el-table-column
                    prop="risk_level"
                    label="地区"
                    width="60"
                    align="center"
                  >
                    <template slot-scope="scope">
                      <svg-icon
                        v-if="scope.row.risk_level == '1'"
                        icon-class="br-select-di"
                      />
                      <svg-icon
                        v-if="scope.row.risk_level == '2'"
                        icon-class="br-select-zhong"
                      />
                      <svg-icon
                        v-if="scope.row.risk_level == '3'"
                        icon-class="br-select-gao"
                      />
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="trip"
                    label="行程"
                    width="60"
                    align="center"
                  >
                    <template slot-scope="scope">
                      <svg-icon
                        v-if="scope.row.trip == '1'"
                        icon-class="br-select-right"
                      />
                      <svg-icon
                        v-if="scope.row.trip == '2'"
                        icon-class="br-select-error"
                      />
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="nuclein"
                    label="核酸"
                    width="60"
                    align="center"
                  >
                    <template slot-scope="scope">
                      <svg-icon
                        v-if="scope.row.nuclein == '1'"
                        icon-class="br-select-right"
                      />
                      <svg-icon
                        v-if="scope.row.nuclein == '2'"
                        icon-class="br-select-error"
                      />
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="survey"
                    label="流调"
                    width="60"
                    align="center"
                  >
                    <template slot-scope="scope">
                      <svg-icon
                        v-if="scope.row.survey == '1'"
                        icon-class="br-select-right"
                      />
                      <svg-icon
                        v-if="scope.row.survey == '2'"
                        icon-class="br-select-error"
                      />
                    </template>
                  </el-table-column>
                </el-table-column>
                <!-- 状态 -->
                <el-table-column
                  prop="state_text"
                  label="状态"
                  align="center"
                  width="130"
                >
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.state_text == '有效'" size="mini"
                      >有效</el-tag
                    >
                    <el-tag
                      v-else-if="scope.row.state_text == '无效'"
                      type="info"
                      size="mini"
                      >无效</el-tag
                    >
                  </template>
                </el-table-column>
                <el-table-column
                  prop="date"
                  label="操作"
                  width="120"
                  align="center"
                  fixed="right"
                >
                  <template slot-scope="scope">
                    <el-button
                      type="text"
                      size="small"
                      @click="toEscortdetailsHandler(scope.row)"
                      >详情</el-button
                    >
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div>
              <!-- {{ patientDetail.attendant_date_record }} -->

              <div class="drawer_title_text">陪护记录</div>
              <el-calendar>
                <template slot="dateCell" slot-scope="{ date, data }">
                  <!-- date	单元格代表的日期 -->
                  {{ data.day.split("-").slice(-1).join("-") }}
                  <!-- {{ data.day.split("-").slice(0).join("-") }} -->
                  <div style="position: absolute; bottom: 5px; heigth: 10px">
                    <div
                      v-for="(i, index) in patientDetail.attendant_date_record"
                      :key="index"
                    >
                      <div
                        v-if="
                          i.attendant_time ==
                          data.day.split('-').slice(0).join('-')
                        "
                      >
                        <el-tag type="success" size="mini">
                          {{
                            i.attendant_name.length > 4
                              ? i.attendant_name.substring(0, 3) + "..."
                              : i.attendant_name
                          }}
                        </el-tag>
                      </div>
                    </div>
                  </div>
                </template>
              </el-calendar>
            </div>
            <!-- 底部详情表格 -->
            <div class="drawer_title_text">陪护通行记录</div>

            <!-- {{ attendantThrough.results }} -->
            <el-table
              :data="attendantThrough.results"
              style="width: 100%"
              :default-sort="{ prop: 'date', order: 'descending' }"
              size="small"
            >
              <el-table-column
                prop="access_time"
                label="扫描时间"
                align="center"
              />
              <el-table-column
                prop="attendant_info.name"
                label="陪护姓名"
                align="center"
              />
              <el-table-column
                prop="temperature"
                label="陪护通行体温°C"
                align="center"
              >
                <template slot-scope="scope">
                  <div
                    v-if="scope.row.temperature >= 37"
                    style="color: #f5222d"
                  >
                    {{ scope.row.temperature }}
                  </div>
                  <div
                    v-else-if="scope.row.temperature < 37"
                    style="color: #52c41a"
                  >
                    {{ scope.row.temperature }}
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="image" label="扫描照片" align="center">
                <template slot-scope="scope">
                  <div class="show_img">
                    <img :src="scope.row.image" alt="" />
                    <div class="big_img_2">
                      <img
                        :src="scope.row.image"
                        alt=""
                        style="width: 200px; height: 200px"
                      />
                    </div>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </el-form>
          <!-- <div class="loading_more">
            <el-button plain>加载更多...</el-button>
          </div> -->
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <!-- <el-button @click="changePatientDetailDrawer(false)" >取消</el-button> -->
        <el-button type="primary" @click="changePatientDetailDrawer(false)"
          >确定</el-button
        >
      </span>
    </Briupdrawer>
  </div>
</template>

<script>
import { getWardsName, getWardsId } from "@/utils/auth";
import { mapState, mapActions, mapMutations } from "vuex";
import Briupdrawer from "@/components/Briupdrawer";
import Bingyuanxiangqing from "@/components/Bingyuanxiangqing";

export default {

  props: ["detailForm"],
  components: {
    Briupdrawer,
    Bingyuanxiangqing,
  },
  data () {
    return {
      getWardsName: getWardsName(),
    };
  },
  created () {

  },
  computed: {
    ...mapState("dashboard", ["patientDetailDrawer",]),
    ...mapState("patientManage", [
      "patientDetail",
      "attendantThrough",
    ]),
  },
  methods: {
    changeData () {
    },
    ...mapActions("dashboard", [
      "changePatientDetailDrawer",
      "changeDetailDrawerHandle",
    ]),
    ...mapMutations("patientManage", [
      "setpatientDetail",
    ]),
    ...mapActions("patientManage", [
      "queryAttendantDetail",
      "queryAttendantThrough",
    ]),
    handlerVisibleChange () {
      this.changePatientDetailDrawer(false);
      // 将 patientDetail 置空
      this.setpatientDetail("");
    },
    // 跳转到陪护人员详情
    toEscortdetailsHandler (row) {
      // 打开陪护详情抽屉
      this.changeDetailDrawerHandle(true);
      // 查询陪护详情
      var params = {
        id: row.id,
        param: { ward_id: getWardsId() },
      };
      this.queryAttendantDetail(params);
      this.queryAttendantThrough({ attendant__id: row.id, page: 1, page_size: 10, id: row.id, ward_id: getWardsId() });
    },
  },
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
