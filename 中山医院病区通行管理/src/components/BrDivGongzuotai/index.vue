<template>
  <!-- 工作台 -->
  <div v-if="flag == 1" class="div_one">
    <el-row
      ><div
        class="grid-content"
        style="
          font-weight: bold;
          text-align: left;
          color: rgb(78, 79, 83);
          font-size: 25px;
        "
      >
        {{ data.bed_num }}
      </div></el-row
    >
    <el-row type="flex" align="middle">
      <el-col
        ><el-button type="primary" size="mini" @click="fengPei(data.bed_num)"
          >+ 分配</el-button
        ></el-col
      >
    </el-row>
    <el-row type="flex" align="middle">
      <el-col
        ><el-button type="primary" size="mini" @click="dengji(data.bed_num)"
          >+ 登记</el-button
        ></el-col
      >
    </el-row>
  </div>
  <div v-else-if="flag == 2" class="div_two">
    <div class="div_three">
      <el-row>
        <el-col :span="12"
          ><div class="grid-content" style="font-weight: bold; font-size: 25px">
            {{ data.bed_num == "" ? "-" : data.bed_num }}
          </div></el-col
        >
        <el-col :span="12"
          ><div class="grid-content" style="float: right">
            <el-button type="text" size="mini" @click="changeBad(data)">{{
              data.bed_num == "" ? "分配" : "换床"
            }}</el-button>
          </div></el-col
        >
      </el-row>
      <el-row>
        <el-col :span="8"
          ><div class="grid-content">
            <el-tag size="mini" @click="PatientDetails(data)">
              {{
                data.name.length > 4
                  ? data.name.substring(0, 3) + "..."
                  : data.name
              }}
            </el-tag>
          </div></el-col
        >
        <el-col :span="8"
          ><div class="grid-content" style="text-align: center">
            {{ data.gender }}
          </div></el-col
        >
        <el-col :span="8"
          ><div class="grid-content" style="float: right">
            {{ data.age }}岁
          </div></el-col
        >
      </el-row>
      <el-row>
        <el-col :span="20"
          ><div class="grid-content">
            陪护:
            <el-tag
              class="simpleContent"
              v-if="data.attendant_info"
              type="success"
              size="mini"
              @click="AccompanyinDetails(data)"
            >
              {{
                data.attendant_info[0].name.length > 5
                  ? data.attendant_info[0].name.substring(0, 4) + "..."
                  : data.attendant_info[0].name
              }}
            </el-tag>
            <el-tag v-else type="info" size="mini">---</el-tag>
          </div>
        </el-col>
        <el-col :span="4"
          ><div class="grid-content" style="float: right">
            <el-button type="text" size="mini" @click="changePeople(data)">{{
              data.attendant_info ? "换人" : "登记"
            }}</el-button>
          </div></el-col
        >
      </el-row>
    </div>
    <div class="one-button">
      <el-button-group>
        <el-button type="primary" plain size="mini" @click="changeFlag(data)"
          >编辑</el-button
        >
        <el-button type="primary" plain size="mini" @click="changeMyFlag(data)"
          >出院</el-button
        >
      </el-button-group>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import { getWardsId } from "@/utils/auth";
export default {
  props: ["data",],
  data () {
    return {
      flag: 2,
    };
  },
  created () {
    // console.log(this.data.name);
    if (this.data.name) {
      this.flag = 2
    } else {
      this.flag = 1
    }
  },
  updated () {
    if (this.data.name) {
      this.flag = 2
    } else {
      this.flag = 1
    }
  },
  computed: {
    ...mapState("patientManage", [
      "emptyBed",
      "patientDetail",
    ]),
    ...mapState("dashboard", ["changeBedDrawer"]),
  },
  methods: {
    ...mapActions("dashboard", ["changeBedDrawerHandle", "changeDetailDrawerHandle", "setBedDrawerHandle",
      "setBedNumHandle",
      "queryWorkBenchData",
      "changePeihudengjiDrawer", "setPatientId", "setChangePerDrawer",
      "changePatientDetailDrawer",
      "changeBingyuandengjiDrawer",
      "changeGenghuanpeihuDrawer",
    ]),
    ...mapActions("patientManage", [
      "queryEmptyBed",
      "queryPatientData",
      "queryPatientDetail",
      "queryAttendantDetail",
      "queryAttendantThrough",
      "patientLeave",
    ]),
    fengPei (bed_num) {
      console.log("打开分配抽屉");
      // this.flag = 2;
      this.setBedDrawerHandle(true)
      // 查询住院中（已分配床位、未分配床位）病员
      this.queryPatientData({ ward_id: getWardsId(), hospitalization_status: 0, ordering: 'bed_num' })
      this.setBedNumHandle(bed_num)
    },
    dengji (bed_num) {
      console.log("打开病员登记抽屉");
      this.changeBingyuandengjiDrawer(true);
      // 传递床位号
      console.log(bed_num);
      this.setBedNumHandle(bed_num)
    },
    changeFlag (data) {
      console.log("打开编辑抽屉");
      // this.flag = 1;
      // 传递病员id
      console.log(data.id);
      // 根据病员id查询病员详情 传给抽屉
      Promise.all([this.queryPatientDetail({
        id: data.id,
        param: { ward_id: getWardsId() },
      }),
      this.queryEmptyBed({ ward_id: getWardsId() })]).then(
        () => {
          this.changeBingyuandengjiDrawer(true);
        }
      )
    },
    changeMyFlag (patientData) {
      // this.flag = 1; // 改当前组件状态
      console.log("提交出院信息到后台");
      var that = this;
      this.$confirm('已回收陪护证和发放出院小结等工作，确认出院？', patientData.bed_num + "床" + patientData.name + "出院", {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        that.patientLeave({ id: patientData.id, ward_id: getWardsId() }).then(() => {
          // 出院后重新查找工作台内容
          this.queryWorkBenchData({ id: getWardsId(), param: { bed_status: 1 } })
          this.$message({
            type: 'success',
            message: '出院成功!'
          });
        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });
    },
    changePeople (data) {
      // console.log("打开换人/陪护登记抽屉");
      // 将病员id传到抽屉中
      this.setPatientId(data)
      var obj = data.attendant_info
      console.log(obj);
      if (obj !== null) {
        //有陪护 打开更换陪护
        console.log('陪护人员更换');
        // 查询当前病员项详情
        // 查询病员详情
        var params = {
          // ordering:"bed_num",
          id: data.id,
          param: { ward_id: getWardsId() },
        };
        this.queryPatientDetail(params).then(() => {
          this.changeGenghuanpeihuDrawer(true)
        });
      } else {
        // 无陪护 打开陪护登记
        console.log('无陪护');
        console.log(data.id);
        // 将病员id传到抽屉中
        this.setPatientId(data)
        this.changePeihudengjiDrawer(true)
      }
      // 获取病人id
    },
    changeBad (row) {
      console.log("工作台小组件点击换床");
      // 更改状态机中的换床抽屉flag
      this.changeBedDrawerHandle(true);
      // 根据病员id查询病员详情 传给抽屉
      this.queryPatientDetail({
        id: row.id,
        param: { ward_id: getWardsId() },
      })
      this.queryEmptyBed({ ward_id: getWardsId() });
    },
    PatientDetails (row) {
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
      // 查询病员的所有陪护通行记录
      this.queryAttendantThrough({ patient_id: row.id, ward_id: getWardsId() });
    },
    AccompanyinDetails (row) {
      console.log("打开陪护详情抽屉");
      // 更改状态机中陪护详情抽屉flag
      this.changeDetailDrawerHandle(true);
      // 根据陪护id查询陪护详情
      var params = {
        id: row.attendant_info[0].id,
        param: { ward_id: getWardsId() },
      };
      this.queryAttendantDetail(params);
      this.queryAttendantThrough({ page: 1, page_size: 10, attendant__id: row.attendant_info[0].id, ward_id: getWardsId() });
      // this.queryEmptyBed({ ward_id: getWardsId() });
    },
  },
};
</script>

<style lang="scss" scoped>
.el-row {
  margin-bottom: 10px;
  &:last-child {
    margin-bottom: 0;
  }
}

.grid-content {
  border-radius: 4px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}

.one-button {
  position: absolute;
  bottom: 0;
  margin-top: 20px;
  padding-bottom: 0px;
  margin-top: 5px;
  margin-left: -5px;
  .el-button-group {
    width: 168px;
  }
  .el-button--mini {
    width: 84.5px;
    height: 2em;
    border: 0;
    border-radius: 0;
  }
}
// 工作台小卡片高度
$height: 130px;
.div_one,
.div_two {
  float: left;
  margin: 5px;
  padding: 5px;
  width: 170px;
  border-radius: 1%;
  position: relative;
  border: solid 1px rgb(228, 228, 228);
}
.div_one {
  height: $height;
  text-align: center;
  // line-height: 108px;
}
.div_two {
  overflow: scroll;
  min-height: $height;
  border: solid 1px rgb(228, 228, 228);
  text-align: left;
  color: rgb(78, 79, 83);
  font-size: 14px;
}
// .div_three div {
//   display: inline-block;
// }
.el-tag--mini {
  margin-right: 10px;
  margin-left: 5px;
  &:hover {
    cursor: pointer;
  }
}
.el-button--text {
  padding: 0px;
}
</style>