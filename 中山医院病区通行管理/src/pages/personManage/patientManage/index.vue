<template>
  <div class="patient">
    <!-- 搜索区域 -->
    <div class="header">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item>
          <el-button type="primary" size="mini">{{ getWardsName }}</el-button>
        </el-form-item>
        <el-form-item>
          <el-select
            v-model="params.hospitalization_status"
            clearable
            size="small"
            placeholder="请选择住院状态"
          >
            <el-option label="全部" value="allHos" />
            <el-option label="住院中" value="0" />
            <el-option label="已出院" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select
            v-model="params.bed_status"
            placeholder="请选择床位状态"
            clearable
            size="small"
          >
            <el-option label="所有" value="" />
            <el-option label="未分配床位" value="0" />
            <el-option label="已分配床位" value="1" />
          </el-select>
        </el-form-item>
        <!-- <el-form-item>
          <el-select
            v-model="params.bed_status"
            placeholder="请选择陪护状态"
            clearable
            size="small"
          >
            <el-option label="所有陪护状态" value="allescort" />
            <el-option label="有效" value="escort" />
            <el-option label="失效" value="noEscort" />
          </el-select>
        </el-form-item> -->
        <el-form-item>
          <el-input
            v-model="params.search"
            placeholder="请输入信息搜索"
            prefix-icon="el-icon-search"
            size="small"
          />
        </el-form-item>
        <div style="float: right">
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="toAddHandler"
              >登记</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-download"
              size="mini"
              @click="exportList()"
              >导出</el-button
            >
          </el-form-item>
        </div>
      </el-form>
    </div>
    <!-- 表格 -->
    <el-table
      :data="patientData.results"
      style="width: 100%"
      :default-sort="{ prop: 'bed_num', order: 'ascending' }"
      @sort-change="sortHandler"
      size="small"
      :header-cell-style="{
        background: '#f5f7fa',
        color: '#606266',
      }"
    >
      <!-- 床位信息 -->
      <el-table-column
        prop="bed_num"
        label="床位信息"
        width="130"
        sortable
        fixed
        align="right"
      >
        <template
          slot-scope="scope"
          v-if="scope.row.hospitalization_status_text == '住院中'"
        >
          <div v-if="scope.row.bed_num">
            <span>{{ scope.row.bed_num }}</span>
            <span class="changeEscort" @click="changeBedHandler(scope.row)"
              >换床</span
            >
          </div>
          <div v-else>
            <span>--</span>
            <span class="changeEscort" @click="changeBedHandler(scope.row)"
              >分配</span
            >
          </div>
        </template>
      </el-table-column>
      <el-table-column fixed prop="name" label="病员姓名" align="center" />
      <el-table-column
        fixed
        prop="date"
        label="病员信息"
        align="center"
        width="120"
      >
        <template slot-scope="scope">
          <span class="gender">{{
            scope.row.gender == "1" ? "男" : "女"
          }}</span>
          <span>{{ scope.row.age }}岁</span>
        </template>
      </el-table-column>
      <!-- 疫情检测信息 -->
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
        <el-table-column prop="trip" label="行程" width="60" align="center">
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
        <el-table-column prop="nuclein" label="核酸" width="60" align="center">
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
        <el-table-column prop="survey" label="流调" width="60" align="center">
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
        <el-table-column
          prop="temperature"
          label="体温"
          width="60"
          align="center"
        >
          <template slot-scope="scope">
            <div
              v-if="scope.row.temperature_state == false"
              style="color: #f5222d"
            >
              {{ scope.row.temperature }}
            </div>
            <div v-else style="color: #52c41a">
              {{ scope.row.temperature }}
            </div>
          </template>
        </el-table-column>
      </el-table-column>
      <!-- 状态 -->
      <el-table-column
        prop="hospitalization_status_text"
        label="状态"
        align="center"
        width="130"
      >
        <template slot-scope="scope">
          <el-tag
            v-if="scope.row.hospitalization_status_text == '住院中'"
            size="mini"
            >住院中</el-tag
          >
          <el-tag
            v-else-if="scope.row.hospitalization_status_text == '已出院'"
            size="mini"
            type="info"
            >已出院</el-tag
          >
        </template>
      </el-table-column>
      <!-- 陪护人员 -->
      <el-table-column
        type="index"
        prop="attendant_info"
        label="陪护人员"
        align="right"
        width="170"
      >
        <template slot-scope="scope">
          <div v-if="scope.row.hospitalization_status_text == '已出院'">--</div>
          <div v-else>
            <div v-if="scope.row.attendant_info == null">
              <span style="width: 68px; display: inline-block"> -- </span>
              <span class="changeEscort" @click="toEscortHandler(scope.row)"
                >登记</span
              >
            </div>
            <div v-else>
              <el-tag type="success" plain size="mini">
                {{
                  scope.row.attendant_info[0].name.length > 4
                    ? scope.row.attendant_info[0].name.substring(0, 3) + "..."
                    : scope.row.attendant_info[0].name
                }}
              </el-tag>
              <span class="changeEscort" @click="changePersonHandler(scope.row)"
                >换人</span
              >
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="enter_time"
        label="入院日期"
        sortable
        align="center"
        width="180"
      >
        <template slot-scope="scope">
          <div v-if="scope.row.enter_time">
            {{ scope.row.enter_time | dateFMT }}
          </div>
          <div v-else>--</div>
        </template>
      </el-table-column>
      <el-table-column
        prop="leave_time"
        label="出院日期"
        sortable
        width="180"
        align="center"
        ><template slot-scope="scope">
          <div v-if="scope.row.leave_time">
            {{ scope.row.leave_time | dateFMT }}
          </div>
          <div v-else>--</div>
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
          <el-button type="text" size="small" @click="editHandler(scope.row)"
            >编辑</el-button
          >
          <el-button type="text" size="small" @click="detailsHandler(scope.row)"
            >详情</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="changePage">
      <el-pagination
        :current-page="params.page"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="10"
        layout="total, sizes, prev, pager, next, jumper"
        :total="patientData.count"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 进度条病员登记 -->
    <Briupdrawer
      :drawer-visible="printPatientDrawer"
      title="打印陪护证"
      width="45%"
      @on-change-visible="handlerVisibleChange"
    >
      <!-- {{form}} -->
      <div slot="content">
        <div class="drawer_content drawer_content_detail">
          <el-form>
            <div class="escort_content">
              <el-steps :active="active">
                <el-step title="病员登记" />
                <el-step title="陪护人员登记" />
              </el-steps>
              <div v-if="active == 1" class="escortSuccess">
                <div><svg-icon icon-class="br-select-right" /></div>
                <div>病员登记成功</div>
                <div>即将进行陪护人员登记</div>
              </div>
              <div v-else class="block" />
            </div>
          </el-form>
        </div>
      </div>
    </Briupdrawer>

    <!-- 陪护人员详情 -->
    <Briupdrawer
      :drawer-visible="detailEscortDrawer"
      title="陪护人员详情"
      width="45%"
      @on-change-visible="handlerVisibleChange"
    >
      <div slot="content">
        <div class="drawer_content drawer_content_detail drawer_escort_detail">
          <!-- 详情表单 -->
          <!-- {{ attendantDetail }} -->
          <el-form
            ref="form"
            :model="attendantDetail"
            label-width="130px"
            size="small"
          >
            <el-row>
              <el-col :span="12">
                <el-form-item label="陪护姓名:">
                  {{ attendantDetail.name }}</el-form-item
                >
                <el-form-item label="身份证号:">
                  {{ attendantDetail.IDCard }}</el-form-item
                >
                <el-form-item label="电话号码:">
                  {{ attendantDetail.phone }}</el-form-item
                >
                <!-- <el-form-item label="病员信息:">
                  {{ attendantDetail.patient_info.name }}</el-form-item
                > -->
                <el-form-item label="地区风险等级:">
                  <div v-if="detailForm.area == 1">
                    <svg-icon icon-class="br-select-di" />
                    <span class="svg_icon_text">低风险</span>
                  </div>
                  <div v-if="detailForm.area == 2">
                    <svg-icon icon-class="br-select-zhong" />
                    <span class="svg_icon_text">中风险</span>
                  </div>
                  <div v-if="detailForm.area == 3">
                    <svg-icon icon-class="br-select-gao" />
                    <span class="svg_icon_text">高风险</span>
                  </div>
                </el-form-item>
                <el-form-item label="14天行程信息:">
                  <div v-if="detailForm.acid == 1">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">绿色</span>
                  </div>
                  <div v-if="detailForm.acid == 2">
                    <svg-icon icon-class="br-select-error" />
                    <span class="svg_icon_text">红色</span>
                  </div>
                </el-form-item>
                <el-form-item label="核酸检测报告状态:">
                  <div v-if="detailForm.acid == 1">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">已出</span>
                  </div>
                  <div v-if="detailForm.acid == 2">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">已过期</span>
                  </div>
                  <div v-if="detailForm.acid == 3">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">未出</span>
                  </div>
                </el-form-item>
                <el-form-item label="流调信息:">
                  <div v-if="detailForm.flow == 1">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">未出</span>
                  </div>
                  <div v-if="detailForm.flow == 2">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">未出</span>
                  </div>
                </el-form-item>
                <el-form-item label="陪护体温:">
                  <span style="color: #52c41a">{{
                    attendantDetail.temperature
                  }}</span>
                  °C</el-form-item
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
              {{ attendantDetail.start_time }}
              <span v-if="attendantDetail.end_time">
                至{{ attendantDetail.end_time }}
              </span>
            </el-form-item>
            <el-form-item label="陪护状态:">
              <el-tag
                v-if="attendantDetail.state_text == '无效'"
                type="info"
                size="mini"
                >{{ attendantDetail.state_text }}</el-tag
              >
              <el-tag v-else type="success" size="mini">{{
                attendantDetail.state_text
              }}</el-tag>
            </el-form-item>
            <!-- 详情表格 -->
            <div class="drawer_title_text">陪护通行记录</div>
            <!-- {{ attendantThrough.results }} -->

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
                sortable
                align="center"
              />
              <el-table-column
                prop="temperature"
                label="陪护通行体温°C"
                width="260"
                align="center"
              >
                <template slot-scope="scope">
                  <div
                    v-if="scope.row.temperature_state == false"
                    style="color: #f5222d"
                  >
                    {{ scope.row.temperature }}
                  </div>
                  <div v-else style="color: #52c41a">
                    {{ scope.row.temperature }}
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="image" label="扫描照片" align="center">
                <template slot-scope="scope">
                  <div class="show_img">
                    <img :src="scope.row.face_data" alt="" />
                    <div class="big_img_2">
                      <img
                        :src="scope.row.face_data"
                        alt=""
                        style="width: 200px; height: 200px"
                      />
                    </div>
                  </div>
                </template>
              </el-table-column>
            </el-table>
            <div class="loading_more">
              <el-button plain>加载更多...</el-button>
            </div>
          </el-form>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="detailEscortDrawer = false">取消</el-button>
        <el-button type="primary" @click="onSubmit()">确定</el-button>
      </span>
    </Briupdrawer>

    <!-- 分配/换床 -->
    <BrHuanchuang />
    <!-- 病员登记抽屉 -->
    <BrBingyuandengji />
    <!-- 病员详情抽屉 -->
    <BrBingyuanxiangqingdrawer />
    <!-- 更换陪护人员 -->
    <BrGenghuanpeihu />
    <!-- 陪护人员抽屉 -->
    <BrPeihudengji />
    <!-- 陪护登记 -->
    <BrPeihudengji />
    <!-- 陪护人员详情 -->
    <BrPeihurenyuan />
  </div>
</template>
<script>
import BrHuanchuang from "@/components/BrHuanchuang";
import BrRadioXingcheng from "@/components/BrRadioXingcheng";
import BrRadioDiqu from "@/components/BrRadioDiqu";
import BrRadioHesuan from "@/components/BrRadioHesuan";
import BrRadioLiudiao from "@/components/BrRadioLiudiao";
import Briupdrawer from "@/components/Briupdrawer";
import Bingyuanxiangqing from "@/components/Bingyuanxiangqing";
import BrGenghuanpeihu from "@/components/BrGenghuanpeihu";
import { getWardsName, getWardsId } from "@/utils/auth";
import { mapActions, mapState } from "vuex";
import moment from 'moment'
import BrBingyuanxiangqingdrawer from "@/components/BrBingyuanxiangqingdrawer"
import BrBingyuandengji from "@/components/BrBingyuandengji"
import BrPeihudengji from "@/components/BrPeihudengji";
import BrPeihurenyuan from "@/components/BrPeihurenyuan";

export default {
  components: {
    BrRadioXingcheng,
    BrRadioDiqu,
    BrRadioHesuan,
    BrRadioLiudiao,
    Briupdrawer,
    Bingyuanxiangqing,
    BrGenghuanpeihu,
    BrBingyuanxiangqingdrawer,
    BrBingyuandengji,
    BrPeihudengji,
    BrPeihurenyuan,
    BrHuanchuang,
  },
  data () {
    return {
      patient: {},
      collectFace: 1,
      params: {
        page: 1,
        page_size: 10,
        ward_id: getWardsId(),
        hospitalization_status: "0",
        bed_status: "",
        search: "",
        ordering: "bed_num"
      },
      ward_address: getWardsName(),
      loading: true,
      collect: false,
      printPatientDrawer: false,
      active: 1,
      escortDrawer: false,
      detailEscortDrawer: false,
      bedForm: {},
      changePerDrawer: false,
      changeBedDrawer: false,
      changePerForm: {},
      detailForm: {},
      detailDrawer: false,
      form: {
        enter_time: moment(new Date()).format('YYYY-MM-DD hh:mm:ss'),
        gender: "1",
        nuclein: "1",
        risk_level: "1",
        survey: "1",
        trip: "1",
        ward: getWardsId(),
      },
      formInline: {},
      title: "",
      drawer: false,
      showVisiable: false, // 控制显隐
      editIndex: -1, // 当前编辑行index
      radio1: "1",
      radio2: "1",
      radio3: "1",
      radio4: "1",
      input2: "",
      value: "inHos",
      value1: "alldistribution",
      value2: "allescort",
      getWardsName: getWardsName(),
    };
  },
  computed: {
    ...mapState("patientManage", [
      "patientData",
      "patientDetail",
      "attendantDetail",
      "attendantThrough",
      "emptyBed",
    ]),
  },
  filters: {
    // 处理表格日期
    dateFMT (str) {
      return moment(str).format('YYYY-MM-DD')
    }
  },
  created () {
    this.queryPatientData(this.params);
  },
  methods: {
    ...mapActions("patientManage", [
      "queryPatientData",
      "queryPatientDetail",
      "queryAttendantDetail",
      "queryAttendantThrough",
      "queryEmptyBed",
      "changeBed",
      "exportList",
      "addPatient",
    ]),
    ...mapActions("accompany", ['queryAccompany', 'saveAccompany', 'updateAccompany']),
    ...mapActions("dashboard", [
      "changeGenghuanpeihuDrawer",
      "changeBingyuandengjiDrawer",
      "changePatientDetailDrawer",
      "changePeihudengjiDrawer",
      "setPatientId",
      "changeBedDrawerHandle",
    ]),
    // 表格排序
    sortHandler (column) {
      if (column.column.label == '床位信息') {
        if (column.order == 'ascending') { //上箭头
          var obj = {
            ordering: 'bed_num'
          }
        } else if (column.order == "descending") { // 下箭头
          var obj = {
            ordering: '-bed_num'
          }
        }
        this.params = Object.assign(this.params, obj)
        this.queryPatientData(this.params);
      }
    },
    // 立即采集添加
    collectHandler () {
      this.collect = true;
      setTimeout(() => {
        this.collect = false;
      }, 2000);
    },
    closeDrawer () {
      this.drawer = false;
      this.form = {}
      this.form = {
        enter_time: moment(new Date()).format('YYYY-MM-DD hh:mm:ss'),
        gender: "1",
        nuclein: "1",
        risk_level: "1",
        survey: "1",
        trip: "1",
        ward: getWardsId(),
      }
    },
    onSubmitPatient () {
      this.drawer = false
      this.form.enter_time = moment(this.form.enter_time).format('YYYY-MM-DD hh:mm:ss')
      // 发送登记请求
      this.addPatient(this.form).then(() => {
        this.queryPatientData(this.params)
      });
    },
    // 病员登记
    toPatientHandler () {
      this.printPatientDrawer = true;
      setTimeout(() => {
        this.active = 2;
        this.escortDrawer = true;
      }, 2000);
    },
    // 陪护人员登记
    toEscortHandler (row) {
      // 设置病员信息
      this.setPatientId(row)
      // 打开陪护人员登记抽屉
      this.changePeihudengjiDrawer(true);
    },
    // 换人
    changePersonHandler (row) {
      // 这是旧的换人抽屉
      // this.changePerDrawer = true;
      this.changePerForm = row;
      // 将病员id传到抽屉中 ？
      // this.setPatientId(row)
      var params = {
        id: row.id,
        param: { ward_id: getWardsId() },
      };
      this.queryPatientDetail(params).then(() => {
        this.changeGenghuanpeihuDrawer(true)
      });
    },
    // 换床
    changeBedHandler (row) {
      console.log("病员管理页面点击换床");
      // 更改状态机中的换床抽屉flag
      this.changeBedDrawerHandle(true);
      // 根据病员id查询病员详情 传给抽屉
      this.queryPatientDetail({
        id: row.id,
        param: { ward_id: getWardsId() },
      })
      this.queryEmptyBed({ ward_id: getWardsId() });
    },
    // 登记陪护人员
    onSubmit () {
      console.log("陪护人员登记");
      if (this.flag == 0) {
        // 编辑保存
        let form = Object.assign({}, this.form)
        // form.patient = form.patient
        form.ward_id = getWardsId();
        form.state = Number(form.state)
        form.start_time = moment(form.start_time).format('YYYY-MM-DD hh:mm:ss')
        console.log(typeof (form.start_time));
        this.updateAccompany(form).then((res) => {
          this.escortDrawer = false
          this.$message({
            message: '保存成功',
            type: 'success'
          })
          this.queryAccompany(this.params)
          this.form = {}
          this.form = {
            enter_time: moment(new Date()).format('YYYY-MM-DD hh:mm:ss'),
            gender: "1",
            nuclein: "1",
            risk_level: "1",
            survey: "1",
            trip: "1",
            ward: getWardsId(),
          }
          this.close()
        })
      } else {
        // 新增保存
        let form = Object.assign({}, this.form)
        // form.patient = form.patient
        form.start_time = moment(form.start_time).format('YYYY-MM-DD hh:mm:ss')
        this.saveAccompany(form).then((res) => {
          this.escortDrawer = false
          this.close()
          if (res.code == 200) {
            this.$message({
              message: res.msg,
              type: 'success'
            })
            this.queryAccompany(this.params)
          }
        })
          .catch((error) => {
            console.log(error);
            this.$message({
              message: error.data.msg,
              type: 'warning'
            })
          })
      }
    },
    // 采集身份证信息
    collectIDHandler () {
      this.collect = true
      setTimeout(() => {
        this.collect = false
      }, 500)
      var httpServerPort = 8989;
      $("#divInfo").html("");
      $("#img").attr("src", "");
      $.ajax({
        dataType: "JSONP",
        type: "get",
        url: "http://localhost:" + httpServerPort + "/api/ReadMsg?waitTime=3&readOnce=1",//接口服务器地址
        success: (data) => {
          this.IDCard_form = data
        },
        error (e) {
          //失败执行
          alert(e.status + ',' + e.statusText);
        }
      })
      setTimeout(() => {
        console.log(this.IDCard_form, '身份证信息');

        this.form.name = this.IDCard_form.name
        this.form.IDCard = this.IDCard_form.cardno
        console.log((this.form));
        this.$forceUpdate()
      }, 1000)
    },
    // 病员登记
    toAddHandler () {
      // 打开病员登记抽屉组件
      // 查询空床信息
      this.queryEmptyBed({ ward_id: getWardsId() });
      this.changeBingyuandengjiDrawer(true);
      // 旧方法
      // this.drawer = true;
      // this.title = "病员登记";
      // // 查询空床信息
      // this.queryEmptyBed({ ward_id: getWardsId() })
      // setTimeout(() => {
      //   this.$refs.get_name_focus.focus();
      // }, 200);
    },

    // 编辑
    editHandler (row) {
      // this.drawer = true;
      // this.form = row;
      // this.title = "病员编辑";
      console.log("打开编辑抽屉");
      // this.flag = 1;
      // 传递病员id
      console.log(row.id);
      // 根据病员id查询病员详情 传给抽屉
      Promise.all([this.queryPatientDetail({
        id: row.id,
        param: { ward_id: getWardsId() },
      }),
      this.queryEmptyBed({ ward_id: getWardsId() })]).then(
        () => {
          this.changeBingyuandengjiDrawer(true);
        }
      )

      // setTimeout(() => {
      //   this.$refs.get_name_focus.focus();
      // }, 200);
    },

    // 病员详情
    detailsHandler (row) {
      console.log("打开病员详情抽屉");
      console.log(row);
      // 查询病员详情
      var params = {
        id: row.id,
        param: { ward_id: getWardsId() },
      };
      this.queryPatientDetail(params).then(() => {
        this.changePatientDetailDrawer(true)
      });
      // 查询病员的陪护通行记录
      this.queryAttendantThrough({ patient_id: row.id, ward_id: getWardsId() });
    },
    // 采集人脸
    collectFaceHandler () {
      this.collectFace = 1
      setTimeout(() => {
        this.collectFace = 2
      }, 200)
      setTimeout(() => {
        this.collectFace = 3
      }, 2000)
      this.myVideo()
      if (navigator.mediaDevices.getUserMedia || navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia) {
        //调用用户媒体设备, 访问摄像头
        close();
        var device_videos = this.getData().device_videos
        var device_videos_index = this.getData().device_videos_index
        var success = this.success
        var error = this.error
        setTimeout(() => {
          this.getUserMedia({ video: { width: video.videoWidth, height: video.videoHeight, deviceId: device_videos[device_videos_index++ % device_videos.length] } }, success, error);
        }, 1000)


      } else {
        alert('不支持访问用户媒体');
      }
    },

    myVideo () {
      return this.$refs['video'];
    },
    myContext () {
      return this.$refs['canvas'].getContext('2d');
    },
    // 采集人脸
    //访问用户媒体设备的兼容方法
    getUserMedia (constraints, success, error) {
      if (navigator.mediaDevices.getUserMedia) {
        //最新的标准API
        navigator.mediaDevices.getUserMedia(constraints).then(success).catch(error);
      } else if (navigator.webkitGetUserMedia) {
        //webkit核心浏览器
        navigator.webkitGetUserMedia(constraints, success, error)
      } else if (navigator.mozGetUserMedia) {
        //firfox浏览器
        navigator.mozGetUserMedia(constraints, success, error);
      } else if (navigator.getUserMedia) {
        //旧版API
        navigator.getUserMedia(constraints, success, error);
      }
    },

    success (stream) {
      //兼容webkit核心浏览器
      let CompatibleURL = window.URL || window.webkitURL;
      //将视频流设置为video元素的源
      // console.log(stream);
      this.myVideo()
      // video.src = CompatibleURL.createObjectURL(stream);
      video.srcObject = stream;
      video.play();
    },

    error (error) {
      console.log(`访问用户媒体设备失败${error.name}, ${error.message}`);
    },

    close () { //关闭摄像头数据流
      if (this.$refs['video']) {
        if (this.$refs['video'].srcObject) {
          try {
            var tracks = this.$refs['video'].srcObject.getTracks();
            for (var track of tracks) {
              track.stop();
            }
          } catch (e) {
            alert(e);
          }
        }
      }
    },

    convertCanvasToImage (canvas) {
      //新Image对象,可以理解为DOM;
      var image = new Image();
      //canvas.toDataURL返回的是一串Base64编码的URL,当然,浏览器自己肯定支持
      //指定格式PNG
      image.src = canvas.toDataURL("image/png");
      return image;
    },

    //设备摄像头数据获取
    getData () {
      var device_videos = [];
      var device_videos_index = 0;
      navigator.mediaDevices.enumerateDevices().then(function (devices) {
        devices.forEach(function (dv) {
          var kind = dv.kind;
          if (kind.match(/^video.*/)) {
            device_videos.push(dv.deviceId);
          }
        });
      })
      var device = {
        device_videos,
        device_videos_index
      }
      return device;
    },

    // 拍照
    takePhoto () {
      var context = this.myContext()
      // context.drawImage(video, 0, 0, video.videoWidth / 3, video.videoHeight / 3);
      context.drawImage(video, 0, 0, 213, 160);
      // 图片
      let base64 = this.convertCanvasToImage(canvas)
      var data = window.atob(base64.src.split(',')[1]);
      const ia = new Uint8Array(data.length);
      for (let i = 0; i < data.length; i++) {
        ia[i] = data.charCodeAt(i);
      }
      const blob = new Blob([ia], { type: 'image/jpeg' });
      this.form.face_data = blob
    },

    // 处理visible变化的钩子函数，解决抽屉无法收缩的bug
    handlerVisibleChange (val) {
      this.form = {}
      this.form = {
        enter_time: moment(new Date()).format('YYYY-MM-DD hh:mm:ss'),
        gender: "1",
        nuclein: "1",
        risk_level: "1",
        survey: "1",
        trip: "1",
        ward: getWardsId(),
      }
      this.changePerDrawer = val;
      this.changeBedDrawer = val;
      this.drawer = val;
      this.detailDrawer = val;
      this.detailEscortDrawer = val;
      this.escortDrawer = val;
      this.printPatientDrawer = val;
    },
    // 分页
    handleSizeChange (val) {
      this.params.page_size = val;
    },
    // 改变当前页
    handleCurrentChange (val) {
      this.params.page = val;
    },

    changeRadio1 (data) {
      // 获取子组件的label
      this.form.risk_level = data
      console.log(this.form)
    },
    changeRadio2 (data) {
      // 获取子组件的label
      this.form.trip = data
      console.log(this.radio2)
    },
    changeRadio3 (data) {
      // 获取子组件的label
      this.form.nuclein = data
      console.log(this.radio3)
    },
    changeRadio4 (data) {
      // 获取子组件的label
      this.form.survey = data
      console.log(this.radio4)
    }
  },
  watch: {
    // 监测分页
    params: {
      handler () {
        this.queryPatientData(this.params);
      },
      deep: true,
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
.changePage {
  position: absolute;
  bottom: 1rem;
  left: 50%;
  margin: 1rem 0 0 -202px;
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
