<template>
  <!-- 病员登记抽屉 -->
  <div class="patient">
    <!-- 登记抽屉  -->
    <Briupdrawer
      :drawer-visible="bingyuandengjiDrawer"
      title="病员登记"
      width="45%"
      @on-change-visible="handlerVisibleChange"
      @closed="handleClose"
    >
      <div slot="content">
        <div class="drawer_content">
          <div class="drawer_title">病员入院信息</div>
          <el-form
            ref="form"
            :model="form"
            :rules="rules"
            label-width="130px"
            size="small"
            class="patient_drawer"
          >
            <!-- {{ form }} -->
            <!-- {{ patientDetail }} -->
            <!-- {{ bedNum }} -->
            <!-- {{ emptyBed }} -->
            <el-form-item label="病区:"> {{ getWardsName }}</el-form-item>
            <el-form-item label="采集病员信息:">
              <div v-if="form.id">
                <el-button
                  v-if="collect == false"
                  plain
                  icon="el-icon-plus"
                  class="dashBtn"
                  @click="collectHandler"
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
                  plain
                  icon="el-icon-plus"
                  class="dashBtn"
                  @click="collectHandler"
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
            <el-form-item prop="name" label="病员姓名:">
              <el-input
                ref="get_name_focus"
                v-model="form.name"
                placeholder="请采集或输入病员姓名"
              />
            </el-form-item>
            <el-form-item prop="IDCard" label="身份证号:">
              <el-input
                v-model="form.IDCard"
                placeholder="请采集或输入病员身份证号"
              />
            </el-form-item>
            <el-form-item prop="age" label="年龄:">
              <el-input v-model="form.age" placeholder="请采集或输入病员年龄">
                <i slot="suffix">岁 </i></el-input
              >
            </el-form-item>
            <el-form-item prop="gender" label="性别:">
              <el-radio v-model="form.gender" label="1" value="1">男</el-radio>
              <el-radio v-model="form.gender" label="2" value="2">女</el-radio>
            </el-form-item>
            <el-form-item prop="addmission_num" label="住院号:">
              <el-input
                v-model="form.addmission_num"
                placeholder="请输入病员住院号"
              />
            </el-form-item>
            <el-form-item label="床位信息:">
              {{ bedNum }}
              <el-select
                v-if="!bedNum"
                v-model="form.bed_num"
                placeholder="请选择床号"
                style="width: 100%"
              >
                <el-option
                  v-for="(item, index) in emptyBed.empty_bed_list"
                  :key="index"
                  :label="item"
                  :value="item"
                />
              </el-select>
            </el-form-item>

            <el-form-item prop="enter_time" label="入院日期:">
              <el-date-picker
                v-model="form.enter_time"
                type="date"
                placeholder="选择日期"
                style="width: 100%"
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
            <el-form-item prop="temperature" label="入院体温:">
              <el-input
                v-model="form.temperature"
                placeholder="请输入病员体温"
                style="width: 100%"
              >
                <i slot="suffix">°C </i>
              </el-input>
            </el-form-item>

            <div class="drawer_title_text">陪护人员信息</div>
            <el-form-item label="陪护人员:">
              <el-button
                :disabled="!Boolean(form.temperature)"
                plain
                class="dashBtn"
                icon="el-icon-plus"
                @click="toPatientHandler"
                >登记</el-button
              >
            </el-form-item>
          </el-form>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="closeDrawer()">取消</el-button>
        <el-button type="primary" @click="onSubmitPatient()">确定</el-button>
      </span>
    </Briupdrawer>
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
  </div>
</template>

<script>
import Briupdrawer from "@/components/Briupdrawer";
import BrRadioXingcheng from '@/components/BrRadioXingcheng'
import BrRadioDiqu from '@/components/BrRadioDiqu'
import BrRadioHesuan from '@/components/BrRadioHesuan'
import BrRadioLiudiao from '@/components/BrRadioLiudiao'
import Bingyuanxiangqing from "@/components/Bingyuanxiangqing";
import { mapState, mapActions } from "vuex";
import { getWardsId, getWardsName } from '@/utils/auth'
import moment from 'moment'

export default {
  inject: ['reload'],
  components: {
    Briupdrawer,
    Bingyuanxiangqing,
    BrRadioXingcheng,
    BrRadioDiqu,
    BrRadioHesuan,
    BrRadioLiudiao,
  },
  data () {
    return {
      flag: 2,
      ward_address: getWardsName(),
      // 表单校验中间变量
      formFlag: false,
      form: {
        ward: getWardsId(),
        gender: "1",
        enter_time: moment(new Date()).format('YYYY-MM-DD hh:mm:ss'),
        nuclein: "1",
        risk_level: "1",
        survey: "1",
        trip: "1",
        bed_num: '',
      },
      radio: 1,
      collect: false,
      collectFace: 1,
      getWardsName: getWardsName(),
      active: 1,
      printPatientDrawer: false,
      params: {
        page: 1,
        page_size: 10,
        ward_id: getWardsId(),
        hospitalization_status: "0",
        bed_status: "",
        search: "",
      },
      rules: {
        name: [
          { required: true, message: '请采集姓名', trigger: 'blur' },
        ],
        age: [
          { required: true, message: '请输入病员年龄', trigger: 'blur' }
        ],
        gender: [
          { required: true, message: '请选择病员性别', trigger: 'change' }
        ],
        IDCard: [
          { required: true, message: '请采集身份证号', trigger: 'blur' }
        ],
        temperature: [
          { required: true, message: '请输入体温', trigger: 'blur' }
        ],
        enter_time: [
          { required: true, message: '请选入院日期', trigger: 'blur' }
        ],
        // addmission_num: [
        //   { required: true, message: '请输入住院号', trigger: 'change' }
        // ],

      },
    };
  },
  created () {

  },

  mounted () {
  },
  watch: {
    'patientDetail': {
      handler () {
        this.form = this.patientDetail
        this.form = Object.assign({}, this.form);
        this.form.gender = this.patientDetail.gender ? this.patientDetail.gender.toString() : ""
        this.form.ward = getWardsId();
        console.log(this.form);
      },
      deep: true
    },
  },
  computed: {
    ...mapState("dashboard", ["changeBedDrawer", "bingyuandengjiDrawer", "bedNum"]),
    ...mapState("patientManage", [
      "emptyBed",
      "patientDetail",
      'patientData'
    ]),
  },
  methods: {
    ...mapActions("dashboard", ["changeBedDrawerHandle", "queryWorkBenchData",
      "changeBingyuandengjiDrawer", "queryWorkBenchData", "setBedNumHandle", "setPatientId",
      "changePeihudengjiDrawer",
    ]),
    ...mapActions("accompany", ['saveAccompany',]),
    ...mapActions("patientManage", ["queryPatientData",
      "queryPatientData",
      "addPatient",
      "editPatient",
    ]),
    handleClose () {
      // 清空表单校验结果
      this.$refs.form.resetFields()
    },
    // 立即采集添加身份证信息
    collectHandler () {
      this.collect = true;
      setTimeout(() => {
        this.collect = false;
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
          console.log(data, '/////');
        },
        error (e) {
          //失败执行
          alert(e.status + ',' + e.statusText);
        }
      })
      setTimeout(() => {
        this.form.name = this.IDCard_form.name
        this.form.IDCard = this.IDCard_form.cardno
        this.form.gender = this.IDCard_form.sex == "男" ? "1" : "2"
        moment(new Date()).format('YYYY-MM-DD hh:mm:ss')
        this.form.age = moment(new Date()).format('YYYY') - this.IDCard_form.born.slice(0, 4)
        this.$forceUpdate()
      }, 1000)
    },
    // 关闭抽屉
    closeDrawer () {
      this.changeBingyuandengjiDrawer(false);
      // 关闭模态框清空表单
      this.form = {}
      // 设置表单默认值
      this.setThisFormDefault()
      this.handleClose()
    },
    // 设置表单默认值
    setThisFormDefault () {
      console.log("清空病员表单");
      // 清空登记床位的床位号
      this.setBedNumHandle('');
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
    // 处理visible变化的钩子函数，解决抽屉无法收缩的bug
    handlerVisibleChange (val) {
      this.changeBingyuandengjiDrawer(false);
      // 关闭模态框清空表单
      this.form = {}
      // 设置表单默认值
      this.setThisFormDefault()
      this.handleClose()
      this.escortDrawer = val;
      this.printPatientDrawer = val;
    },
    // 表单校验
    formValidate () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.formFlag = true
        }
        else {
          console.log('表单校验失败');
          return false;
        }
      })
    },
    async onSubmitPatient () {
      this.formValidate()
      if (this.formFlag) {
        var resData
        this.form.enter_time = moment(this.form.enter_time).format('YYYY-MM-DD hh:mm:ss')
        if (this.bedNum) {
          console.log("bedNum存在");
          this.form.bed_num = this.bedNum
        }
        console.log(this.form);
        if (this.form.id) {
          // 如果存在病员id，则发送修改请求
          this.editPatient(this.form).then(() => {
            this.editPatientHandler()
            // return
          });
        } else {
          // 如果不存在病员id 则发送新增请求
          await this.addPatient(this.form).then((res) => {
            this.editPatientHandler()
            resData = res
          });
        }
        this.reload()
        return resData
      }
    },
    // 病员登记&修改后的回调
    editPatientHandler () {
      this.queryPatientData(this.params)
      // 请求工作台数据
      this.queryWorkBenchData({ id: getWardsId(), param: { bed_status: 1 } })
      // 关闭抽屉
      this.changeBingyuandengjiDrawer(false);
      // 关闭抽屉清空表单
      this.form = {}
      // 设置表单默认值
      this.setThisFormDefault()
      this.queryPatientData(this.params)
      // 请求工作台数据
      this.queryWorkBenchData({ id: getWardsId(), param: { bed_status: 1 } })
      // 关闭抽屉
      this.changeBingyuandengjiDrawer(false);
      // 关闭抽屉清空表单
      this.form = {}
      // 设置表单默认值
      this.setThisFormDefault()
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
    toPatientHandler () {
      this.formValidate()
      if (this.formFlag) {
        // 病员登记-陪护登记中转抽屉
        this.printPatientDrawer = true;
        // 进行病员登记 相当于点击确定按钮
        this.onSubmitPatient().then
          (
            // 登记成功打开陪护登记抽屉
            (res) => {
              // res是当前病员信息
              this.active = 2;
              // 打开陪护人员登记抽屉
              this.changePeihudengjiDrawer(true);
              // 设置病员信息
              console.log("病员登记成功");
              console.log(res);
              this.setPatientId(res.data)
              this.escortDrawer = true;
            }
          ).catch(() => {
            // 病员登记失败-关闭陪护登记中转抽屉-展示病员登记抽屉
            this.printPatientDrawer = true;
          })
      }

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
      if (video.srcObject) {
        try {
          var tracks = video.srcObject.getTracks();
          for (var track of tracks) {
            track.stop();
          }
        } catch (e) {
          alert(e);
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
      context.drawImage(video, 0, 0, video.videoWidth / 3, video.videoHeight / 3);
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
    // 保存
    onSubmit () {
      if (this.flag == 0) {
        // 编辑保存
        let form = Object.assign({}, this.form)
        // form.patient = form.patient
        form.ward_id = getWardsId();
        form.state = Number(form.state)
        form.start_time = moment(form.start_time).format('YYYY-MM-DD hh:mm:ss')
        console.log(typeof (form.start_time));


        this.updateAccompany(form).then((res) => {
          this.changeBingyuandengjiDrawer(false)
          this.$message({
            message: '保存成功',
            type: 'success'
          })
          this.queryAccompany(this.params)
          // 关闭抽屉清空表单
          this.form = {}
          // 设置表单默认值
          this.setThisFormDefault()
          this.close()
        })
      } else {
        // 新增保存
        let form = Object.assign({}, this.form)
        // form.patient = form.patient
        form.start_time = moment(form.start_time).format('YYYY-MM-DD hh:mm:ss')
        this.saveAccompany(form).then((res) => {
          this.changeBingyuandengjiDrawer(false)
          // 关闭抽屉清空表单
          this.form = {}
          // 设置表单默认值
          this.setThisFormDefault()
          this.close()
          if (res.code == 200) {
            this.$message({
              message: res.msg,
              type: 'success'
            })
          }
        })
          .catch((error) => {
            console.log(error);
            this.$message({
              // message: error.data.msg,
              type: 'warning'
            })
          })
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.br_div_list {
  width: 189px;
  font-size: 12px;
  padding: 0px 5px 5px 1px;
  border-bottom: 0.5px solid #dfdfdf;
  margin-bottom: 0px;
  &:hover {
    background-color: rgba(209, 231, 255);
  }
  .el-button--text {
    padding: 0px;
  }
  .el-col-4 {
    max-width: 220px;
  }
  .el-col {
    border-radius: 4px;
  }
}
</style>
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