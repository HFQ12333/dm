<template>
  <!-- 陪护登记抽屉 -->
  <div class="patient">
    <!-- 登记抽屉 drawer -->
    <Briupdrawer
      :drawer-visible="peihudengjiDrawer"
      title="陪护人员登记"
      width="85%"
      @on-change-visible="handlerVisibleChange"
      @closed="handleClose"
    >
      >
      <div slot="content">
        <div class="drawer_content">
          <el-form
            ref="form"
            :model="form"
            label-width="130px"
            size="small"
            :rules="rules"
          >
            <el-row>
              <el-col :span="12">
                <div class="drawer_title_text">绑定陪护对象</div>
                <el-form-item label="病区:">
                  {{ this.ward_address }}</el-form-item
                >
                <el-form-item label="病员:" prop="patient">
                  <!-- {{ patientId }} -->
                  <span v-if="patientId"
                    >{{ patientId.bed_num }}-{{ patientId.name }}
                    <el-input
                      v-model="form.patient"
                      style="display: none"
                      type="text"
                    />
                  </span>
                  <el-select
                    v-else
                    v-model="form.patient"
                    filterable
                    collapse-tags
                    placeholder="请点击或者搜索选择已有病员"
                    style="width: 100%"
                  >
                    <div v-for="item in patientData.results" :key="item.id">
                      <el-option
                        v-if="item.bed_num"
                        :label="item.bed_num + '-' + item.name"
                        :value="item.id"
                      />
                      <el-option
                        v-else
                        :label="'-' + '-' + item.name"
                        :value="item.id"
                      />
                    </div>
                  </el-select>
                </el-form-item>
                <div class="drawer_title">陪护人员信息</div>

                <el-form-item label="采集陪护信息:">
                  <!-- 是否重新采集身份证 -->
                  <div v-if="form.id">
                    <el-button
                      v-if="collect == false"
                      plain
                      icon="el-icon-plus"
                      class="dashBtn"
                      @click="collectIDHandler"
                      >读取身份证信息</el-button
                    >
                    <el-button
                      v-if="collect == true"
                      :loading="true"
                      plain
                      style="width: 100%"
                      >正在采集</el-button
                    >
                  </div>

                  <div v-else>
                    <el-button
                      v-if="collect == false"
                      id="readIDcard"
                      plain
                      icon="el-icon-plus"
                      class="dashBtn"
                      @click="collectIDHandler"
                      >读取身份证信息</el-button
                    >
                    <el-button
                      v-if="collect == true"
                      :loading="true"
                      plain
                      style="width: 100%"
                      >正在采集</el-button
                    >
                  </div>
                </el-form-item>
                <el-form-item label="陪护姓名:" prop="name">
                  <el-input
                    ref="get_name_focus"
                    v-model="form.name"
                    placeholder="请输入或采集陪护人员姓名"
                  />
                </el-form-item>

                <el-form-item prop="IDCard" label="身份证号:">
                  <el-input
                    v-model="form.IDCard"
                    placeholder="请输入或采集陪护人员身份证号"
                  />
                </el-form-item>

                <el-form-item prop="phone" label="电话号码:">
                  <el-input
                    v-model="form.phone"
                    placeholder="请输入陪护人员电话号码"
                  />
                </el-form-item>
                <el-form-item label="地区风险等级:">
                  <br-radio-diqu
                    v-model="form.risk_level"
                    :radio="form.risk_level"
                    @change-radio="changeRadio1"
                  />
                </el-form-item>
                <el-form-item label="14天行程信息:">
                  <br-radio-xingcheng
                    v-model="form.trip"
                    :radio="form.trip"
                    @change-radio="changeRadio2"
                  />
                </el-form-item>
                <el-form-item label="核酸检测报告状态:">
                  <br-radio-hesuan
                    v-model="form.nuclein"
                    :radio="form.nuclein"
                    @change-radio="changeRadio3"
                  />
                </el-form-item>
                <el-form-item label="流调信息:">
                  <br-radio-liudiao
                    v-model="form.survey"
                    :radio="form.survey"
                    @change-radio="changeRadio4"
                  />
                </el-form-item>
                <el-form-item prop="temperature" label="陪护体温:">
                  <el-input
                    v-model="form.temperature"
                    placeholder="请输入陪护人员体温"
                    ><i slot="suffix">°C </i></el-input
                  >
                </el-form-item>
                <el-form-item prop="start_time" label="陪护开始日期:">
                  <el-date-picker
                    v-model="form.start_time"
                    :picker-options="pickerOptions0"
                    type="date"
                    placeholder="请选择陪护开始日期"
                    style="width: 100%"
                  />
                </el-form-item>
                <el-form-item label="陪护结束日期:">
                  <el-date-picker
                    v-model="form.end_time"
                    :picker-options="pickerOptions0"
                    type="date"
                    placeholder="请选择陪护结束日期"
                    style="width: 100%"
                  />
                </el-form-item>
                <el-form-item prop="state" label="状态:">
                  <el-select
                    v-model="form.state"
                    placeholder="请选择状态"
                    style="width: 100%"
                  >
                    <el-option key="1" label="有效" value="1" />
                    <el-option key="2" label="无效" value="2" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <!-- 人脸采集 -->

                <!-- 修改人脸 -->
                <div class="drawer_title_text">陪护人脸信息</div>
                <el-form-item
                  v-if="form.id"
                  v-model="form.face_data"
                  label="陪护人脸信息:"
                >
                  <img
                    v-if="collectFace == 1"
                    :src="form.face_data"
                    class="escort_face"
                    alt=""
                  />
                  <el-button
                    v-if="collectFace == 1"
                    id="camera"
                    plain
                    icon="el-icon-plus"
                    class="dashBtn"
                    @click="collectFaceHandler"
                    >重新采集添加</el-button
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
                  <el-row style="margin-top: 1rem">
                    <el-col :span="11">
                      <video
                        v-if="collectFace != 1"
                        id="video"
                        ref="video"
                        width="100%"
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
                        style="margin-top: 20px"
                      />
                    </el-col>
                  </el-row>
                </el-form-item>
                <!-- 新增人脸 -->

                <el-form-item v-else label="采集人脸信息:">
                  <el-button
                    v-if="collectFace == 1"
                    id="camera"
                    plain
                    icon="el-icon-plus"
                    class="dashBtn"
                    @click="collectFaceHandler"
                    >采集人脸信息</el-button
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
                  <el-row style="margin-top: 1rem">
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
                        style="margin-top: 20px"
                      />
                    </el-col>
                  </el-row>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button
          @click="
            changePeihudengjiDrawer(false);
            close();
            handleClose();
            collectFace = 1;
            // 清空patientId
            setPatientId();
          "
          >取消</el-button
        >
        <el-button type="primary" @click="onSubmit()">确定</el-button>
      </span>
    </Briupdrawer>
  </div>
</template>

<script>
import Briupdrawer from '@/components/Briupdrawer'
import BrRadioXingcheng from '@/components/BrRadioXingcheng'
import BrRadioDiqu from '@/components/BrRadioDiqu'
import BrRadioHesuan from '@/components/BrRadioHesuan'
import BrRadioLiudiao from '@/components/BrRadioLiudiao'
import Bingyuanxiangqing from '@/components/Bingyuanxiangqing'
import { mapState, mapActions } from 'vuex'
import { getWardsId, getWardsName } from '@/utils/auth'
import moment from 'moment'

export default {
  components: {
    Briupdrawer,
    Bingyuanxiangqing,
    BrRadioXingcheng,
    BrRadioDiqu,
    BrRadioHesuan,
    BrRadioLiudiao
  },
  data () {
    return {
      pickerOptions0: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7// 如果没有后面的-8.64e7就是不可以选择今天的
        }
      },
      params: {
        page: 1,
        page_size: 10,
        ward_id: getWardsId(),
        state: '',
        hospitalization_status: '0',
        ordering: 'patient__bed_num'
      },
      flag: 2,
      rules: {
        name: [
          { required: true, message: '请采集或输入姓名', trigger: 'blur' }
        ],
        patient: [
          { required: true, message: '请选择已有病员', trigger: 'blur' }
        ],
        IDCard: [
          { required: true, message: '请采集或出入身份证号', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { min: 11, max: 11, message: '请输入11位手机号码', trigger: 'blur' }
        ],
        temperature: [
          { required: true, message: '请输入体温', trigger: 'blur' }
        ],
        start_time: [
          { required: true, message: '请选择陪护开始时间', trigger: 'blur' }
        ],
        state: [
          { required: true, message: '请选择状态', trigger: 'blur' }
        ]
      },
      ward_address: getWardsName(),
      collect: false,
      collectFace: 1,
      form: {
        start_time: moment(new Date()).format('YYYY-MM-DD hh:mm:ss'),
        nuclein: '1',
        risk_level: '1',
        survey: '1',
        trip: '1',
        ward_id: getWardsId(),
        state: '1',
        patient: [""], // 欺骗表单校验
      },
      collect: false,
      collectFace: 1
    }
  },
  created () {

  },
  computed: {
    ...mapState('dashboard', ['changeBedDrawer', 'peihudengjiDrawer',
      'patientId']),
    ...mapState('patientManage', [
      'emptyBed',
      'patientDetail',
      'patientData'
    ])
  },
  watch: {
    "patientId": {
      handler () {
        this.form.patient = [""]// 欺骗表单校验
      }
    }
  },
  methods: {
    ...mapActions('dashboard', ['changeBedDrawerHandle', 'queryWorkBenchData', 'changePeihudengjiDrawer', 'setPatientId']),
    ...mapActions('accompany', ['queryAccompany', 'saveAccompany', 'updateAccompany']),
    ...mapActions('patientManage', ['changeBed', 'queryPatientData']),
    // 关闭表单校验
    handleClose () {
      this.$refs.form.resetFields()
    },
    // 处理visible变化的钩子函数，解决抽屉无法收缩的bug
    handlerVisibleChange () {
      this.changePeihudengjiDrawer(false)
      this.close()
      // 关闭抽屉清空表单
      this.form = {}
      // 设置表单默认值
      this.setThisFormDefault()
      this.handleClose()
      // 清空patientId
      this.setPatientId();
    },
    setThisFormDefault () {
      this.collectFace = 1
      this.changeRadio1(1)
      this.changeRadio2(1)
      this.changeRadio3(1)
      this.changeRadio4(1)
      this.form.start_time = moment(new Date()).format('YYYY-MM-DD hh:mm:ss');
      this.form.ward_id = getWardsId();
      this.form.state = '1';
    },
    // 采集身份证信息
    collectIDHandler () {
      this.collect = true
      setTimeout(() => {
        this.collect = false
      }, 500)
      var httpServerPort = 8989
      $('#divInfo').html('')
      $('#img').attr('src', '')
      $.ajax({
        dataType: 'JSONP',
        type: 'get',
        url: 'http://localhost:' + httpServerPort + '/api/ReadMsg?waitTime=3&readOnce=1', // 接口服务器地址
        success: (data) => {
          this.IDCard_form = data
        },
        error (e) {
          // 失败执行
          alert(e.status + ',' + e.statusText)
        }
      })
      setTimeout(() => {
        console.log(this.IDCard_form, '身份证信息')
        this.form.name = this.IDCard_form.name
        this.form.IDCard = this.IDCard_form.cardno
        console.log((this.form))
        this.$forceUpdate()
      }, 1000)
    },
    changeRadio1 (data) {
      // 获取子组件的label
      this.form.risk_level = data
      console.log(this.form.risk_level)
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
          this.getUserMedia({ video: { width: video.videoWidth, height: video.videoHeight, deviceId: device_videos[device_videos_index++ % device_videos.length] } }, success, error)
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
    // 访问用户媒体设备的兼容方法
    getUserMedia (constraints, success, error) {
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

    // 拍照
    takePhoto () {
      var context = this.myContext()
      // context.drawImage(video, 0, 0, video.videoWidth / 3, video.videoHeight / 3);
      context.drawImage(video, 0, 0, 213, 160)
      // 图片
      const base64 = this.convertCanvasToImage(canvas)
      var data = window.atob(base64.src.split(',')[1])
      const ia = new Uint8Array(data.length)
      for (let i = 0; i < data.length; i++) {
        ia[i] = data.charCodeAt(i)
      }
      const blob = new Blob([ia], { type: 'image/jpeg' })
      this.form.face_data = blob
    },
    // 新增或修改陪护
    onSubmit () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          // 新增保存
          if (this.patientId) {
            console.log('patientId存在')
            this.form.patient = this.patientId.id
          }
          const form = Object.assign({}, this.form)
          // form.patient = form.patient
          form.start_time = moment(form.start_time).format('YYYY-MM-DD hh:mm:ss')
          if (form.end_time) {
            form.end_time = moment(form.end_time).format('YYYY-MM-DD hh:mm:ss')
          }
          this.saveAccompany(form).then((res) => {
            this.changePeihudengjiDrawer(false)

            this.close()
            this.queryWorkBenchData({ id: getWardsId(), param: { bed_status: 1 } })
            // 清空patientId
            this.setPatientId();
            this.queryAccompany(this.params)
          })
          // .catch((error) => {
          //   console.log(error);
          //   if (error.status == 400) {
          //     this.$message.error(error.data.msg)
          //   }
          // })
        } else {
          console.log('表单校验失败')
          return false
        }
      })
    }

  }
}
</script>

<style scoped>
.patient >>> .el-radio__input.is-checked + .el-radio__label {
  color: #606266 !important;
}
.patient >>> .el-calendar-day {
  position: relative !important;
}
.drawer_content >>> .el-input {
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
  margin: 1rem 0 0 -330px;
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
