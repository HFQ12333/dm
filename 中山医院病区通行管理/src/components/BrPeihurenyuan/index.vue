<template>
  <!-- 陪护人员详情抽屉组件 -->
  <div class="escort_passage">
    <Briupdrawer
      :drawer-visible="changeDetailDrawer"
      title="陪护人员详情"
      width="45%"
      @on-change-visible="handlerVisibleChange"
    >
      <div slot="content">
        <div class="drawer_content drawer_content_detail">
          <!-- {{ attendantDetail }} -->
          <!-- 详情表单 -->
          <el-form
            ref="form"
            :model="attendantDetail"
            label-width="130px"
            size="mini"
          >
            <el-row>
              <el-col :span="12">
                <el-form-item label="陪护姓名:">
                  {{ attendantDetail.name }}
                </el-form-item>
                <el-form-item label="身份证号:">
                  {{ attendantDetail.IDCard }}</el-form-item
                >
                <el-form-item label="电话号码:">
                  {{ attendantDetail.phone }}</el-form-item
                >
                <el-form-item
                  label="病员信息:"
                  v-if="attendantDetail.patient_info"
                >
                  <!-- {{ this.ward_address }} -->
                  {{ attendantDetail.patient_info.bed_num }}号
                  {{ attendantDetail.patient_info.name }}
                </el-form-item>
                <el-form-item label="地区风险等级:">
                  <div v-if="attendantDetail.risk_level == '1'">
                    <svg-icon icon-class="br-select-di" />
                    <span class="svg_icon_text">低风险</span>
                  </div>
                  <div v-if="attendantDetail.risk_level == '2'">
                    <svg-icon icon-class="br-select-zhong" />
                    <span class="svg_icon_text">中风险</span>
                  </div>
                  <div v-if="attendantDetail.risk_level == '3'">
                    <svg-icon icon-class="br-select-gao" />
                    <span class="svg_icon_text">高风险</span>
                  </div>
                </el-form-item>
                <el-form-item label="14天行程信息:">
                  <div v-if="attendantDetail.trip == '1'">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">绿色</span>
                  </div>
                  <div v-if="attendantDetail.trip == '2'">
                    <svg-icon icon-class="br-select-error" />
                    <span class="svg_icon_text">红色</span>
                  </div>
                </el-form-item>
                <el-form-item label="核酸检测报告状态:">
                  <div v-if="attendantDetail.nuclein == '1'">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">已出</span>
                  </div>
                  <div v-if="attendantDetail.nuclein == '3'">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">已过期</span>
                  </div>
                  <div v-if="attendantDetail.nuclein == '2'">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">未出</span>
                  </div>
                </el-form-item>
                <el-form-item label="流调信息:">
                  <div v-if="attendantDetail.survey == '1'">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">未出</span>
                  </div>
                  <div v-if="attendantDetail.survey == '2'">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">未出</span>
                  </div>
                </el-form-item>
                <el-form-item label="陪护体温:">
                  <span
                    v-if="attendantDetail.temperature >= 37.7"
                    style="color: #f5222d"
                    >{{ attendantDetail.temperature }}
                  </span>
                  <span
                    v-if="attendantDetail.temperature < 37.7"
                    style="color: #52c41a"
                    >{{ attendantDetail.temperature }} </span
                  >°C</el-form-item
                >
              </el-col>
              <el-col :span="12">
                <img
                  class="escort_face"
                  :src="attendantDetail.face_data"
                  alt=""
                />
              </el-col>
            </el-row>
            <el-form-item label="陪护日期:">
              {{ attendantDetail.start_time | dateFMT }}
              <span v-if="attendantDetail.end_time">
                至
                {{ attendantDetail.end_time | dateFMT }}
              </span></el-form-item
            >
            <el-form-item label="陪护状态:">
              <el-tag
                v-if="attendantDetail.state_text == '1'"
                type="success"
                size="mini"
                >有效</el-tag
              >
              <el-tag v-else size="mini" type="info" plain
                >无效</el-tag
              ></el-form-item
            >
            <!-- 详情表格 -->
            <!-- {{ attendantDetail }} -->
            <!-- {{ attendantThrough }} -->
            <div class="drawer_title">通行陪护记录</div>
            <el-table
              :data="attendantThrough.results"
              style="width: 100%"
              :default-sort="{ prop: 'date', order: 'descending' }"
              size="small"
              :header-cell-style="{
                background: '#f5f7fa',
                color: '#606266',
              }"
            >
              <el-table-column
                prop="access_time"
                label="扫描时间"
                align="center"
              />
              <el-table-column
                prop="temperature"
                label="陪护通行体温°C"
                width="180"
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
              <el-table-column
                prop="image"
                label="扫描照片"
                align="center"
                width="120"
              >
                <template slot-scope="scope">
                  <el-popover placement="right" trigger="hover">
                    <img
                      :src="scope.row.image"
                      style="max-height: 150px; width: 150px"
                    />
                    <img slot="reference" :src="scope.row.image" />
                  </el-popover>
                </template>
              </el-table-column>
            </el-table>
            <div class="loading_more">
              <!-- <el-button plain @click="toLoadingMore">加载更多...</el-button> -->
              <el-pagination
                layout="prev, pager, next"
                :total="attendantThrough.count"
                style="text-align: center"
                :page-size="params_details.page_size"
                @current-change="changePageDetails"
              />
            </div>
          </el-form>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="changeDetailDrawerHandle(false)">取消</el-button>
        <el-button type="primary" @click="accompanySubmit()">确定</el-button>
      </span>
    </Briupdrawer>
    <Briupdrawer
      :drawer-visible="setAttendantDetailDrawre"
      title="陪护通行记录体温异常"
      width="45%"
      @on-change-visible="handlerVisibleChange"
    >
      <div slot="content">
        <div class="drawer_content">
          <div class="drawer_title">陪护通行记录体温编辑</div>
          <!-- {{ attendantData.attendant_info.id }} -->
          <el-form
            ref="form"
            :model="attendantData"
            label-width="130px"
            size="small"
          >
            <!-- <el-form-item label="病区:" v-if="form.ward_info">
              {{ form.ward_info.name }}</el-form-item
            > -->
            <el-form-item label="陪护人员:">
              <el-row>
                <el-col :span="20" v-if="attendantData.attendant_info">
                  {{ attendantData.attendant_info.name }}
                </el-col>
                <el-col :span="4"
                  ><el-link
                    :underline="false"
                    type="primary"
                    @click="toDeatilsHandler(attendantData)"
                    >详情</el-link
                  ></el-col
                >
              </el-row>
            </el-form-item>
            <el-form-item label="陪护通行体温:">
              <el-input
                ref="get_tempture_focus"
                v-model="attendantData.temperature"
                placeholder="请输入陪护人员通行人工测量体温"
              >
                <i slot="suffix">°C </i>
              </el-input>
            </el-form-item>
            <el-form-item label="陪护通行扫描照片:">
              <img :src="attendantData.image" alt="" />
            </el-form-item>
            <div class="fall">
              <div>
                工作人员: <span>{{ getUserName }}</span>
              </div>
              <div>
                系统时间: <span>{{ new Date().toLocaleString() }}</span>
              </div>
            </div>
          </el-form>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="changeSetAttendantDetailDrawre(false)"
          >取消</el-button
        >
        <el-button
          size="small"
          type="primary"
          @click="onEditSubmit(attendantData)"
          >确定</el-button
        >
      </span>
    </Briupdrawer>
    <!-- 陪护人员详情 -->
    <Briupdrawer
      :drawer-visible="detailDrawer"
      title="陪护人员详情"
      width="45%"
      @on-change-visible="handlerVisibleChange"
    >
      <div slot="content">
        <div class="drawer_content drawer_content_detail">
          <!-- 详情表单 -->
          <el-form
            ref="form"
            :model="detailForm"
            label-width="130px"
            size="mini"
          >
            <el-row>
              <el-col :span="12">
                <el-form-item label="陪护姓名:" v-if="accompany.results">
                  <!-- {{ accompany_id.name }} -->
                </el-form-item>
                <el-form-item label="身份证号:">
                  {{ accompany_id.IDCard }}</el-form-item
                >
                <el-form-item label="电话号码:">
                  {{ accompany_id.phone }}</el-form-item
                >
                <el-form-item
                  label="病员信息:"
                  v-if="accompany_id.patient_info"
                >
                  {{ this.ward_name }}
                  {{ accompany_id.patient_info.bed_num }}号
                  {{ accompany_id.patient_info.name }}</el-form-item
                >
                <el-form-item label="地区风险等级:">
                  <div v-if="accompany_id.risk_level == '0'">
                    <svg-icon icon-class="br-select-di" />
                    <span class="svg_icon_text">低风险</span>
                  </div>
                  <div v-if="accompany_id.risk_level == '1'">
                    <svg-icon icon-class="br-select-zhong" />
                    <span class="svg_icon_text">中风险</span>
                  </div>
                  <div v-if="accompany_id.risk_level == '2'">
                    <svg-icon icon-class="br-select-gao" />
                    <span class="svg_icon_text">高风险</span>
                  </div>
                </el-form-item>
                <el-form-item label="14天行程信息:">
                  <div v-if="accompany_id.trip == '1'">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">绿色</span>
                  </div>
                  <div v-if="accompany_id.trip == '2'">
                    <svg-icon icon-class="br-select-error" />
                    <span class="svg_icon_text">红色</span>
                  </div>
                </el-form-item>
                <el-form-item label="核酸检测报告状态:">
                  <div v-if="accompany_id.nuclein == '1'">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">已出</span>
                  </div>
                  <div v-if="accompany_id.nuclein == '3'">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">已过期</span>
                  </div>
                  <div v-if="accompany_id.nuclein == '1'">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">未出</span>
                  </div>
                </el-form-item>
                <el-form-item label="流调信息:">
                  <div v-if="accompany_id.survey == '1'">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">未出</span>
                  </div>
                  <div v-if="accompany_id.survey == '2'">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">未出</span>
                  </div>
                </el-form-item>
                <el-form-item label="陪护体温:">
                  <span>{{ accompany_id.temperature }}</span></el-form-item
                >
              </el-col>
              <el-col :span="12">
                <img class="escort_face" :src="accompany_id.face_data" alt="" />
              </el-col>
            </el-row>
            <el-form-item label="陪护日期:">
              {{ accompany_id.start_time }}
              <span v-if="accompany_id.end_time">
                至
                {{ accompany_id.end_time }}
              </span></el-form-item
            >
            <el-form-item label="陪护状态:">
              <el-button
                v-if="accompany_id.state_text == '有效'"
                type="success"
                plain
                >{{ accompany_id.state_text }}</el-button
              >
              <el-button v-else type="info" plain>{{
                accompany_id.state_text
              }}</el-button></el-form-item
            >
            <!-- 详情表格 -->
            <div class="drawer_title">通行陪护记录</div>
            <el-table
              :data="traffacRecord_id.results"
              style="width: 100%"
              :default-sort="{ prop: 'date', order: 'descending' }"
              size="small"
              :header-cell-style="{
                background: '#f5f7fa',
                color: '#606266',
              }"
            >
              <el-table-column
                prop="access_time"
                label="扫描时间"
                align="center"
              />
              <el-table-column
                prop="temperature"
                label="陪护通行体温°C"
                width="180"
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
              <el-table-column
                prop="image"
                label="扫描照片"
                align="center"
                width="120"
              >
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
            <!-- <div class="loading_more">
              <el-pagination
                layout="prev, pager, next"
                :total="traffacRecord_id.count"
                style="text-align: center"
                @current-change="changePageDetails"
              >
              </el-pagination>
            </div> -->
          </el-form>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="detailDrawer = false">取消</el-button>
        <!-- <el-button type="primary" @click="accompanySubmit('123')"
          >确定</el-button
        > -->
      </span>
    </Briupdrawer>
  </div>
</template>

<script>
import Briupdrawer from "@/components/Briupdrawer";
import Bingyuanxiangqing from "@/components/Bingyuanxiangqing";
import { mapState, mapActions } from "vuex";
import { getWardsId, getWardsName, getUserName } from "@/utils/auth";
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
      detailDrawer: false,
      detailForm: {},
      ward_name: getWardsName(),
      getUserName: getUserName(),
      params_details: {
        page: 1,
        page_size: 10,
        ward_id: getWardsId()
      }
    };
  },
  created () {

  },
  computed: {
    ...mapState("dashboard", ["changeBedDrawer", "changeDetailDrawer", "setAttendantDetailDrawre",
      "attendantData",
    ]),
    ...mapState("patientManage", [
      "emptyBed",
      "patientDetail",
      "attendantDetail",
      "attendantThrough",
    ]),
    ...mapState("accompany", ['accompany', 'accompany_id']),
    ...mapState("traffacRecord", ['traffacRecord', 'traffacRecord_id',]),

  },
  filters: {
    // 处理表格日期
    dateFMT (str) {
      return moment(str).format('YYYY-MM-DD')
    }
  },
  methods: {
    ...mapActions("dashboard", ["changeBedDrawerHandle", "queryWorkBenchData",
      "changeDetailDrawerHandle",
      "changeSetAttendantDetailDrawre"
    ]),
    ...mapActions("patientManage", ["changeBed", "queryPatientData",
      "queryAttendantDetail",
      "queryAttendantThrough",
    ]),
    ...mapActions("traffacRecord", ['updateTraffcRecord', "queryPersonlmgtThought", "queryPersonlmgtThoughtId"]),
    ...mapActions("accompany", ['detailsAccompanyById']),
    // 处理visible变化的钩子函数，解决抽屉无法收缩的bug
    handlerVisibleChange (val) {
      this.changeDetailDrawerHandle(false)
      this.changeSetAttendantDetailDrawre(false)
      // this.changeBedDrawer = val;
      this.drawer = val;
      this.detailDrawer = val;
    },
    // 详情分页
    changePageDetails (page) {
      this.params_details.attendant__id = this.attendantDetail.id
      this.params_details.page = page;
      this.queryAttendantThrough(this.params_details)
    },
    // 陪护体温异常中查看陪护详情 
    toDeatilsHandler (data) {
      // 打开陪护详情抽屉
      this.changeDetailDrawerHandle(true)
      // 查询陪护详情
      var params = {
        id: data.attendant_info.id,
        param: { ward_id: getWardsId() },
      };
      this.queryAttendantDetail(params);
      this.params_details.attendant__id = this.attendantData.attendant_info.id
      this.params_details.page = 1;
      this.queryAttendantThrough(this.params_details)
    },
    // 编辑陪护通行记录体温
    onEditSubmit (data) {
      console.log('陪护通行记录体温');
      console.log(data);
      var thisData = {
        id: data.id,
        ward_id: getWardsId(),
        data: {
          "temperature": data.temperature,
          "attendant": data.attendant_info.info,
          "ward": getWardsId()
        }
      }
      this.updateTraffcRecord(thisData).then((res) => {
        console.log(getWardsId());
        // 关闭修正抽屉
        this.changeSetAttendantDetailDrawre(false);
        this.queryPersonlmgtThought({ ward_id: getWardsId(), temperature_state: 0 });
        this.$message({
          message: '保存成功',
          type: 'success'
        })
        // this.attendantDetail = {}
      })
    }
  },
};
</script>

<style scoped>
.fall div:nth-child(2),
.fall div:nth-child(1) {
  margin-bottom: 1rem;
}
.fall {
  position: absolute;
  bottom: 4rem;
  right: 2rem;
}
.escort_face {
  height: 200px;
  width: 200px;
}

/* 放大头像 */
.drawer_content_detail .big_img_2 {
  height: 42px;
  width: 100px;
  position: absolute;
}
.drawer_content_detail .big_img_2 img {
  display: none;
  position: absolute;
  top: -100%;
  z-index: 9999;
  left: -180%;
}
.drawer_content_detail .show_img:hover .big_img_2 img {
  display: block;
}

/* 详情放大头像 */
.table .big_img_2 {
  height: 42px;
  width: 100px;
  position: absolute;
}
.table .big_img_2 img {
  display: none;
  position: absolute;
  top: -100%;
  z-index: 9999;
  left: -100%;
}
.table .show_img:hover .big_img_2 img {
  display: block;
}

.big_img {
  height: 42px;
}
.big_img img {
  display: none;
  position: absolute;
  top: 0;
  z-index: 9999;
  right: -60%;
}
.show_img:hover .big_img img {
  display: block;
}
.el-table img {
  width: 40px;
  height: 40px;
}
.escort_passage >>> .is-scrolling-none tr:nth-last-child(-n + 3) .big_img img {
  top: -300%;
  z-index: 9999;
  right: -60%;
}
.escort_passage >>> .is-scrolling-none tr:nth-child(1) .big_img img {
  top: 0%;
  z-index: 9999;
  right: -60%;
}
.escort_passage >>> .is-scrolling-none tr:nth-child(2) .big_img img {
  top: -100%;
  z-index: 9999;
  right: -60%;
}
.escort_passage >>> .is-scrolling-none tr:nth-child(3) .big_img img {
  top: -200%;
  z-index: 9999;
  right: -60%;
}
.escort_passage
  >>> .is-scrolling-none
  tr:nth-last-child(-n + 3)
  .big_img_2
  img {
  top: -490%;
  z-index: 9999;
  right: -100%;
}
.escort_passage >>> .is-scrolling-none tr:nth-child(1) .big_img_2 img {
  top: -140%;
  z-index: 9999;
  right: -100%;
}
.escort_passage >>> .is-scrolling-none tr:nth-child(2) .big_img_2 img {
  top: -260%;
  z-index: 9999;
  right: -100%;
}
.escort_passage >>> .is-scrolling-none tr:nth-child(3) .big_img_2 img {
  top: -370%;
  z-index: 9999;
  right: -100%;
}

.table_img_name {
  float: left;
  line-height: 46px;
}
.table_img {
  margin: 0 1rem;
  float: left;
  line-height: 23px;
}
.el-drawer .el-form .el-form-item:nth-child(2) .el-select {
  width: 100%;
}
/* .el-drawer .el-form .el-form-item:last-child {
  position: absolute;
  bottom: 1rem;
  right: 2rem;
} */
.fall div:nth-child(2),
.fall div:nth-child(1) {
  margin-bottom: 1rem;
}
.fall {
  position: absolute;
  bottom: 1rem;
  right: 2rem;
  font-size: 14px;
}
.drawer_content .el-form-item:nth-child(3) img {
  width: 260px;
  height: 260px;
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
.escort_passage >>> .el-drawer {
  overflow: auto;
}
.escort_passage >>> .el-drawer__wrapper {
  overflow: auto;
}
.drawer_content {
  padding: 0 30px;
}
.svg-icon {
  width: 1.2rem;
  height: 1.2rem;
  margin-right: 2px;
}
.escort_passage >>> .el-drawer__header {
  margin-bottom: 0;
  padding: 10px 0 0 30px;
}
.escort_passage >>> .el-drawer__header > :first-child,
.drawer_title {
  color: #272727;
  font-weight: 700;
}
.drawer_title {
  margin-bottom: 1rem;
}
.escort_passage >>> .el-divider--horizontal {
  margin: 12px 0;
}
.escort_passage >>> .el-table--medium th,
.escort_passage >>> .el-table--medium td {
  padding: 5px 0 0 0;
}
.table .el-table {
  font-weight: 600;
  font-size: 14px;
  margin-bottom: 3rem;
}
.drawer_content .el-table {
  font-weight: 600;
  font-size: 14px;
  margin-bottom: 1rem;
}

.changeEscort {
  color: #1890ff;
}

.el-button {
  font-size: 14px;
  font-weight: 700;
}
.edit_btn div {
  display: inline-block;
  margin-right: 10px;
}
</style>