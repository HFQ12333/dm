<template>
  <div class="escort_passage">
    <!-- 搜索区域 -->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item>
        <el-button type="primary" size="mini">{{ ward_address }}</el-button>
      </el-form-item>
      <el-form-item>
        <el-select
          v-model="params.temperature_state"
          size="small"
          no-data-text="全部"
        >
          <el-option label="所有体温" value="2" />
          <el-option label="正常体温" value="1" />
          <el-option label="异常体温" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-input
          v-model="params.search"
          placeholder="请输入信息搜索"
          prefix-icon="el-icon-search"
          clearable
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
            >人工录入</el-button
          >
        </el-form-item>
      </div>
    </el-form>

    <!-- 表格 -->
    <div class="table">
      <el-table
        :data="traffacRecord.results"
        style="width: 100%"
        :default-sort="{ prop: 'access_time', order: 'descending' }"
        :header-cell-style="{
          background: '#f5f7fa',
          color: '#606266',
        }"
        @sort-change="sortHandler"
      >
        <el-table-column
          prop="access_time"
          label="扫描时间"
          sortable="custom"
          width="300"
          fixed
          align="center"
        >
          <template slot-scope="scope">
            <div>
              {{ scope.row.access_time }}
            </div>
          </template>
        </el-table-column>
        <!-- 陪护姓名 -->
        <el-table-column
          prop="attendant_info.name"
          label="陪护姓名"
          align="center"
          width="220"
        >
          <template slot-scope="scope">
            <el-popover placement="right" trigger="hover">
              <img
                :src="scope.row.attendant_info.face_data"
                style="width: 150px; height: 150px"
              />
              <img
                slot="reference"
                :src="scope.row.attendant_info.face_data"
                style="max-height: 150px; max-width: 150px"
              />
            </el-popover>
            <div class="table_img_name">
              {{ scope.row.attendant_info.name }}
            </div>
            <!-- <div class="show_img">
              <div class="table_img">
                <img :src="scope.row.attendant_info.face_data" alt="" />
              </div>
              <div
                class="table_img_name"
                :title="scope.row.attendant_info.name"
              >
                {{ scope.row.attendant_info.name }}
              </div>
              <div class="big_img">
                <img
                  :src="scope.row.attendant_info.face_data"
                  alt=""
                  style="width: 200px; height: 200px"
                />
              </div>
            </div> -->
          </template>
        </el-table-column>
        <!-- 陪护对象 -->
        <el-table-column prop="address" label="陪护对象" align="center">
          <template slot-scope="scope">
            <div v-if="scope.row.patient_info">
              <span>{{ scope.row.patient_info.bed_num }} - </span>
              <span>{{ scope.row.patient_info.name }} </span>
            </div>
            <div v-else-if="scope.row.patient_info.bed_num == 'string'">
              <span>- </span>
              <span>{{ scope.row.name }} </span>
            </div>
          </template>
        </el-table-column>
        <!-- 扫描照片 -->
        <el-table-column
          prop="image"
          label="扫描照片"
          align="center"
          width="300"
        >
          <template slot-scope="scope">
            <el-popover placement="left" trigger="hover">
              <img :src="scope.row.image" style="width: 150px; height: 150px" />
              <img
                slot="reference"
                :src="scope.row.image"
                style="max-height: 150px; max-width: 150px"
              />
            </el-popover>
            <!-- <div class="show_img">
              <img :src="scope.row.image" alt="" />
              <div class="big_img_2">
                <img
                  :src="scope.row.image"
                  alt=""
                  style="width: 200px; height: 200px"
                />
              </div>
            </div> -->
          </template>
        </el-table-column>
        <el-table-column
          prop="temperature"
          label="陪护通行体温°C"
          sortable="custom"
          fixed="right"
          align="center"
          width="180"
        >
          <template slot-scope="scope">
            <div
              v-if="scope.row.temperature_state == true"
              style="color: #52c41a"
              class="edit_btn"
            >
              <div style="width: 30px">
                {{ scope.row.temperature }}
              </div>
              <el-button
                class="changeEscort"
                type="text"
                size="small"
                @click="editHandler(scope.row)"
                >编辑</el-button
              >
            </div>
            <div
              v-else-if="scope.row.temperature_state == false"
              style="color: #f5222d"
              class="edit_btn"
            >
              <div style="width: 30px">
                {{ scope.row.temperature }}
              </div>
              <el-button
                class="changeEscort"
                type="text"
                size="small"
                @click="editHandler(scope.row)"
                >编辑</el-button
              >
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <div class="changePage">
      <el-pagination
        :current-page="params.page"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="10"
        layout="total, sizes, prev, pager, next, jumper"
        :total="traffacRecord.count"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 录入抽屉 -->
    <Briupdrawer
      :drawer-visible="addDrawer"
      title="人工录入"
      width="48%"
      @on-change-visible="handlerVisibleChange"
      @closed="handleClose"
    >
      <div slot="content">
        <div class="drawer_content">
          <div class="drawer_title">陪护通行信息</div>
          <el-form
            ref="addForm"
            :model="addForm"
            label-width="130px"
            size="small"
            :rules="rules"
          >
            <el-form-item label="病区:"> {{ ward_address }}</el-form-item>
            <el-form-item label="陪护人员:" prop="attendant">
              <el-select
                ref="get_name_foucs"
                v-model="addForm.attendant"
                filterable
                collapse-tags
                placeholder="请点击或者搜索选择已有陪护人员"
                popper-class="escort_select"
              >
                <el-option
                  v-for="item in accompany.results"
                  :key="item.id"
                  :label="
                    item.patient_info.bed_num
                      ? item.name +
                        ' ' +
                        '(陪护 ' +
                        item.patient_info.bed_num +
                        '号' +
                        ' ' +
                        item.patient_info.name +
                        ')'
                      : item.name +
                        ' ' +
                        '(陪护 ' +
                        '-' +
                        '-' +
                        ' ' +
                        item.patient_info.name +
                        ')'
                  "
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="陪护通行体温:" prop="temperature">
              <el-input
                v-model="addForm.temperature"
                placeholder="请输入陪护人员通行人工测量体温"
              >
                <i slot="suffix">°C </i>
              </el-input>
            </el-form-item>
            <el-form-item label="陪护通行扫描照片:">
              <el-button
                v-if="collectFace == 1"
                id="camera"
                plain
                icon="el-icon-plus"
                class="dashBtn"
                @click="collectFaceHandler"
                >立即采集添加</el-button
              >
              <el-button
                v-else-if="collectFace == 2"
                id="camera"
                :loading="true"
                plain
                style="width: 100%"
                >正在采集</el-button
              >
              <el-button
                v-if="collectFace == 3"
                id="camera"
                plain
                icon="el-icon-plus"
                style="width: 100%"
                @click="takePhoto"
                >拍照</el-button
              >
              <el-row style="margin-top: 2rem">
                <el-col :span="11">
                  <video
                    v-if="collectFace != 1"
                    id="video"
                    ref="video"
                    width="200px"
                    height="200px"
                  />
                </el-col>
                <el-col :offset="2" :span="11">
                  <canvas
                    v-if="collectFace != 1"
                    id="canvas"
                    ref="canvas"
                    width="200px"
                    height="200px"
                  />
                </el-col>
              </el-row>
            </el-form-item>
            <div class="fall">
              <div>
                工作人员: <span>{{ username }}</span>
              </div>
              <div>
                系统时间: <span>{{ now_time }}</span>
              </div>
            </div>
          </el-form>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button
          size="small"
          @click="
            addDrawer = false;
            close();
            handleClose();
          "
          >取消</el-button
        >
        <el-button size="small" type="primary" @click="onSubmit()"
          >确定</el-button
        >
      </span>
    </Briupdrawer>

    <!-- 编辑抽屉 -->
    <Briupdrawer
      :drawer-visible="drawer"
      title="陪护通行记录体温异常"
      width="45%"
      @on-change-visible="handlerVisibleChange"
    >
      <div slot="content">
        <div class="drawer_content">
          <div class="drawer_title">陪护通行记录体温编辑</div>
          <el-form
            ref="form"
            :model="form"
            label-width="130px"
            :rules="rules1"
            size="small"
          >
            <el-form-item v-if="form.ward_info" label="病区:">
              {{ form.ward_info.name }}</el-form-item
            >
            <el-form-item label="陪护人员:">
              <el-row style="line-height: 30px">
                <el-col v-if="form.attendant_info" :span="20">{{
                  form.attendant_info.name
                }}</el-col>
                <el-col :span="4"
                  ><el-link
                    :underline="false"
                    type="primary"
                    @click="toDeatilsHandler"
                    >详情</el-link
                  ></el-col
                >
              </el-row>
            </el-form-item>
            <el-form-item label="陪护通行体温:" prop="temperature">
              <el-input
                ref="get_tempture_focus"
                v-model="form.temperature"
                placeholder="请输入陪护人员通行人工测量体温"
                @keyup.enter.native="onEditSubmit"
              >
                <i slot="suffix">°C </i>
              </el-input>
            </el-form-item>
            <el-form-item label="陪护通行扫描照片:">
              <img :src="form.image" alt="" />
            </el-form-item>
            <div class="fall">
              <div>
                工作人员: <span>{{ username }}</span>
              </div>
              <div>
                系统时间: <span>{{ now_time }}</span>
              </div>
            </div>
          </el-form>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="drawer = false">取消</el-button>
        <el-button size="small" type="primary" @click="onEditSubmit"
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
              <el-col :span="13">
                <el-form-item v-if="accompany.results" label="陪护姓名:">
                  {{ accompany_id.name }}</el-form-item
                >
                <el-form-item label="身份证号:">
                  {{ accompany_id.IDCard }}</el-form-item
                >
                <el-form-item label="电话号码:">
                  {{ accompany_id.phone }}</el-form-item
                >
                <el-form-item
                  v-if="accompany_id.patient_info"
                  label="病员信息:"
                >
                  {{ this.ward_address }}
                  {{ accompany_id.patient_info.bed_num }}号
                  {{ accompany_id.patient_info.name }}</el-form-item
                >
                <el-form-item label="地区风险等级:">
                  <div v-if="accompany_id.risk_level == '1'">
                    <svg-icon icon-class="br-select-di" />
                    <span class="svg_icon_text">低风险</span>
                  </div>
                  <div v-if="accompany_id.risk_level == '2'">
                    <svg-icon icon-class="br-select-zhong" />
                    <span class="svg_icon_text">中风险</span>
                  </div>
                  <div v-if="accompany_id.risk_level == '3'">
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
                  <div v-if="accompany_id.nuclein == '2'">
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
              <el-col :offset="1" :span="10">
                <img class="escort_face" :src="accompany_id.face_data" alt="" />
              </el-col>
            </el-row>
            <el-form-item label="陪护日期:">
              {{ accompany_id.start_time }} 至
              {{ accompany_id.end_time }}</el-form-item
            >
            <el-form-item label="陪护状态:">
              <el-tag
                v-if="accompany_id.state_text == '有效'"
                type="success"
                plain
                >{{ accompany_id.state_text }}</el-tag
              >
              <el-tag v-else type="info" plain>{{
                accompany_id.state_text
              }}</el-tag></el-form-item
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
                    v-if="scope.row.temperature_state == true"
                    style="color: #52c41a"
                  >
                    {{ scope.row.temperature }}
                  </div>
                  <div
                    v-else-if="scope.row.temperature_state == false"
                    style="color: #f5222d"
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
                  <el-popover placement="left" trigger="hover">
                    <img
                      :src="scope.row.image"
                      style="width: 150px; height: 150px"
                    />
                    <img
                      slot="reference"
                      :src="scope.row.image"
                      style="max-height: 150px; max-width: 150px"
                    />
                  </el-popover>
                  <!-- <div class="show_img">
                    <img :src="scope.row.image" alt="" />
                    <div class="big_img_2">
                      <img
                        :src="scope.row.image"
                        alt=""
                        style="width: 200px; height: 200px"
                      />
                    </div>
                  </div> -->
                </template>
              </el-table-column>
            </el-table>
            <div class="loading_more">
              <!-- <el-button plain @click="toLoadingMore">加载更多...</el-button> -->
              <el-pagination
                layout="total,prev, pager, next"
                :total="traffacRecord_id.count"
                style="text-align: center"
                :page-size="params_details.page_size"
                @current-change="changePageDetails"
              />
            </div>
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
import Briupdrawer from '@/components/Briupdrawer'
import { mapActions, mapState } from 'vuex'
import moment from 'moment'
import { getToken, getUserName, getWardsId, getWardsName } from '@/utils/auth'
export default {
  components: {
    Briupdrawer
  },
  filters: {
    dateFMT (str) {
      return moment(str).format('YYYY-MM-DD')
    }
  },

  data () {
    var valiNumberPass1 = (rule, value, callback) => { // 包含小数的数字
      const reg = /^[+]?(0|([1-9]\d*))(\.\d+)?$/g
      if (value === '') {
        callback(new Error('请输入体温'))
      } else if (!reg.test(value)) {
        callback(new Error('请输入大于0的数字'))
      } else {
        callback()
      }
    }
    return {
      username: getUserName(),
      rules: {
        temperature: [
          { required: true, validator: valiNumberPass1, trigger: 'blur' }
        ],
        attendant: [
          { required: true, message: '请选择已有陪护', trigger: 'change' }
        ]
      },
      rules1: {
        temperature: [
          { required: true, validator: valiNumberPass1, trigger: 'blur' }
        ],
        attendant: [
          { required: true, message: '请选择已有陪护', trigger: 'change' }
        ]
      },
      collectFace: 1,
      ward_address: getWardsName(),
      tableData: [],
      detailDrawer: false,
      detailForm: {},
      params: {
        page: 1,
        page_size: 10,
        ward_id: getWardsId(),
        temperature_state: '2'
      },
      params_details: {
        page: 1,
        page_size: 10,
        ward_id: getWardsId()
      },
      accompany_params: {
        page: 1,
        page_size: 1000,
        state: 1,
        ordering: 'patient__bed_num',
        ward_id: getWardsId()
      },
      now_time: new Date().toLocaleString(),
      addForm: {
        // ward: getWardsId()
      },
      addDrawer: false,
      value: '1',
      form: {},
      drawer: false,
      formInline: {},
      input2: '',
      direction: 'rtl'
    }
  },
  computed: {
    ...mapState('traffacRecord', ['traffacRecord', 'traffacRecord_id']),
    ...mapState('accompany', ['accompany', 'accompany_id'])
  },
  created () {
    this.queryPersonlmgtThought(this.params)
    // 查询陪护人员
    this.queryAccompany(this.accompany_params)
  },

  methods: {
    ...mapActions('traffacRecord', ['queryPersonlmgtThought', 'saveTraffcRecord', 'updateTraffcRecord', 'queryPersonlmgtThoughtId']),
    ...mapActions('accompany', ['queryAccompany', 'detailsAccompanyById']),
    // 清空表单校验
    handleClose () {
      this.$refs.addForm.resetFields()
    },
    // 排序
    sortHandler (column) {
      if (column.column.label == '扫描时间') {
        if (column.order == 'ascending') { // 上箭头
          var obj = {
            ordering: 'access_time'
          }
        } else if (column.order == 'descending') { // 下箭头
          var obj = {
            ordering: '-access_time'
          }
        }
        this.params = Object.assign(this.params, obj)
        this.queryPersonlmgtThought(this.params)
      } else if (column.column.label == '陪护通行体温°C') {
        if (column.order == 'ascending') { // 上箭头
          var obj = {
            ordering: 'temperature'
          }
        } else if (column.order == 'descending') { // 下箭头
          var obj = {
            ordering: '-temperature'
          }
        }
        this.params = Object.assign(this.params, obj)
        this.queryPersonlmgtThought(this.params)
      }
    },

    // 陪护人员详情
    toDeatilsHandler () {
      this.detailDrawer = true
      const params_id = {
        id: this.form.attendant_info.id,
        ward_id: getWardsId()
      }
      this.detailsAccompanyById(params_id)

      // 当前陪护人陪护通行记录
      this.params_details.attendant__id = this.form.attendant_info.id
      this.queryPersonlmgtThoughtId(this.params_details)
    },

    // 编辑
    editHandler (row) {
      this.drawer = true
      this.form = row
      // 聚焦
      setTimeout(() => {
        this.$refs.get_tempture_focus.focus()
      }, 200)
    },

    // 修改保存
    onEditSubmit () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.form.ward_id = getWardsId()
          this.form.data = {
            'temperature': this.form.temperature,
            'attendant': this.form.attendant_info.id,
            'ward': getWardsId()
          }
          this.updateTraffcRecord(this.form).then((res) => {
            this.drawer = false
            this.$message({
              message: '保存成功',
              type: 'success'
            })
            this.form = {}
          })
        }
      })
    },

    // 新增保存
    onSubmit () {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          const add_form = Object.assign({}, this.addForm)
          add_form.ward = getWardsId()
          console.log(add_form, '[[[[')
          this.saveTraffcRecord(add_form)
            .then((res) => {
              if (res.code == 200) {
                this.$message({
                  message: res.msg,
                  type: 'success'
                })
                this.addDrawer = false
                this.close()
              }
              if (res.code == 400) {
                this.$message.error(res.msg)
                this.addDrawer = true
              }
              this.queryPersonlmgtThought(this.params)
            })
            .catch((error) => {
              // if (error.status == 400) {
              //   this.$message.error('请输入必输项');
              // }
              this.$message({
                message: error.data.msg,
                type: 'warning'
              })
            })
        }
      })
    },

    // 录入
    toAddHandler () {
      this.addDrawer = true
      this.collectFace = 1
      // 聚焦
      setTimeout(() => {
        this.$refs.get_name_foucs.focus()
      }, 700)
      this.addForm = {}
    },

    // 关闭模态框
    handlerVisibleChange (val) {
      this.drawer = val
      this.addDrawer = val
      this.detailDrawer = val
      this.close()
      this.handleClose()
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
        // 调用用户媒体设备, 访问摄像头
        close()
        var device_videos = this.getData().device_videos
        var device_videos_index = this.getData().device_videos_index
        var success = this.success
        var error = this.error
        setTimeout(() => {
          // width: video.videoWidth, height: video.videoHeight
          this.getUserMedia({ video: { width: 200, height: 200, deviceId: device_videos[device_videos_index++ % device_videos.length] } }, success, error)
        }, 1000)
      } else {
        alert('不支持访问用户媒体')
      }
    },

    myVideo () {
      return this.$refs['video']
    },
    myContext () {
      return this.$refs['canvas'].getContext('2d')
    },
    // 采集人脸
    // takePicture () {
    // 访问用户媒体设备的兼容方法
    getUserMedia (constraints, success, error) {
      console.log(111)
      if (navigator.mediaDevices.getUserMedia) {
        // 最新的标准API
        navigator.mediaDevices.getUserMedia(constraints).then(success).catch(error)
      } else if (navigator.webkitGetUserMedia) {
        // webkit核心浏览器
        navigator.webkitGetUserMedia(constraints, success, error)
      } else if (navigator.mozGetUserMedia) {
        // firfox浏览器
        navigator.mozGetUserMedia(constraints, success, error)
      } else if (navigator.getUserMedia) {
        // 旧版API
        navigator.getUserMedia(constraints, success, error)
      }
    },

    success (stream) {
      // 兼容webkit核心浏览器
      const CompatibleURL = window.URL || window.webkitURL
      // 将视频流设置为video元素的源
      // console.log(stream);
      this.myVideo()
      // video.src = CompatibleURL.createObjectURL(stream);
      video.srcObject = stream
      video.play()
    },

    error (error) {
      console.log(`访问用户媒体设备失败${error.name}, ${error.message}`)
    },

    close () { // 关闭摄像头数据流
      if (this.$refs['video']) {
        if (this.$refs['video'].srcObject) {
          try {
            var tracks = this.$refs['video'].srcObject.getTracks()
            for (var track of tracks) {
              track.stop()
            }
          } catch (e) {
            alert(e)
          }
        }
      }
    },

    convertCanvasToImage (canvas) {
      // 新Image对象,可以理解为DOM;
      var image = new Image()
      // canvas.toDataURL返回的是一串Base64编码的URL,当然,浏览器自己肯定支持
      // 指定格式PNG
      image.src = canvas.toDataURL('image/png')
      return image
    },
    // 设备摄像头数据获取
    getData () {
      var device_videos = []
      var device_videos_index = 0
      navigator.mediaDevices.enumerateDevices().then(function (devices) {
        devices.forEach(function (dv) {
          var kind = dv.kind
          if (kind.match(/^video.*/)) {
            device_videos.push(dv.deviceId)
          }
        })
      })
      var device = {
        device_videos,
        device_videos_index
      }
      return device
    },

    takePhoto () {
      var context = this.myContext()
      // context.drawImage(video, 0, 0, video.videoWidth / 3, video.videoHeight / 3);
      context.drawImage(video, 0, 0, 200, 200)
      // 图片
      const base64 = this.convertCanvasToImage(canvas)
      var data = window.atob(base64.src.split(',')[1])
      const ia = new Uint8Array(data.length)
      for (let i = 0; i < data.length; i++) {
        ia[i] = data.charCodeAt(i)
      }
      const blob = new Blob([ia], { type: 'image/jpeg' })
      this.addForm.image = blob
    },
    // 加载更多
    // toLoadingMore () {
    //   this.queryPersonlmgtThought({ page: 1, page_size: 100 })
    // },
    // 分页
    handleSizeChange (val) {
      console.log(`每页 ${val} 条`)
      this.params.page_size = val
    },
    // 改变当前页
    handleCurrentChange (val) {
      console.log(`当前页: ${val}`)
      this.params.page = val
    },
    // 详情分页
    changePageDetails (page) {
      this.params_details.page = page
      this.queryPersonlmgtThoughtId(this.params_details)
    }
  },
  watch: {
    'params.temperature_state': {
      handler: function () {
        this.params.page = 1
        this.queryPersonlmgtThought(this.params)
      },
      deep: true
    },
    // 监测分页
    params: {
      handler: function () {
        this.queryPersonlmgtThought(this.params)
      },
      deep: true
    }
  }
}
</script>

<style scoped>
/* 陪护人员详情图片 */
.escort_face {
  height: 200px;
  width: 200px;
}
.loading_more >>> .el-button {
  margin-left: 43%;
  margin-bottom: 1rem;
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
.escort_passage >>> .el-table__body-wrapper {
  overflow: visible !important;
}
.escort_passage >>> .el-table .cell {
  overflow: visible !important;
}
.escort_passage >>> .el-table {
  overflow: visible !important;
}
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
  right: -30%;
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
  right: -30%;
}
.escort_passage >>> .is-scrolling-none tr:nth-child(1) .big_img img {
  top: 0%;
  z-index: 9999;
  right: -30%;
}
.escort_passage >>> .is-scrolling-none tr:nth-child(2) .big_img img {
  top: -100%;
  z-index: 9999;
  right: -30%;
}
.escort_passage >>> .is-scrolling-none tr:nth-child(3) .big_img img {
  top: -200%;
  z-index: 9999;
  right: -30%;
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
  width: 100px;
  height: 46px;
  cursor: pointer;
  line-height: 46px;
  text-overflow: ellipsis;
  overflow: hidden;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
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
.drawer_content .el-form-item:nth-child(4) img {
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
.escort_passage >>> .el-drawer__header {
  margin-bottom: 0;
  padding: 10px 0 0 30px;
}
/* .changePage {
  width: 700px;
  position: absolute;
  bottom: 1rem;
  left: 50%;
  margin: 1rem 0 0 -330px;
  text-align: center;
} */
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
