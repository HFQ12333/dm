<template>
  <!-- 陪护通行异常 -->
  <div class="br br_div_list" @click="changeData(data)">
    <el-row>
      <!-- 左侧照片 -->
      <el-col :span="6">
        <img
          class="el-avatar--circle"
          style="height: 40px; width: 40px"
          :src="data.image"
        />
      </el-col>
      <!-- 右侧 日期 体温 -->
      <el-col :span="18"
        ><el-row>
          <el-col style="text-align: left">{{
            data.access_time | dateFMT
          }}</el-col>
        </el-row>
        <el-row>
          <el-col :span="10"
            ><div class="simpleContent">
              {{ data.attendant_info.name }}
            </div></el-col
          >
          <el-col :span="7" style="color: red">{{ data.temperature }}</el-col>
          <el-col :span="7"
            ><el-button type="text" size="mini" @click="changeData(data)"
              >修正</el-button
            ></el-col
          >
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import moment from 'moment'
import { mapActions } from "vuex";
export default {
  props: ["data", "model"],
  data () {
    return {
      flag: 2,
    };
  },
  created () {
    // 如果组件是模式2，关闭编辑框，展示信息
    if (this.model == 2) {
      this.flag = 3;
    }
  },
  filters: {
    dateFMT (str) {
      return moment(str).format('MM-DD hh:mm:ss')
    }
  },
  methods: {
    ...mapActions("dashboard", [
      "changeSetAttendantDetailDrawre",
      "setAttendantData",
    ]),
    ...mapActions("patientManage", [
      "queryAttendantDetail",
    ]),
    changeData (data) {
      console.log("打开修正抽屉");
      this.changeSetAttendantDetailDrawre(true)
      this.setAttendantData(data);
      // 根据陪护id查询陪护详情
      // var params = {
      //   id: attendantId,
      //   param: { ward_id: getWardsId() },
      // };
      // this.queryAttendantDetail(params);
    },
  },
};
</script>

<style lang="scss" scoped>
.br_div_list {
  width: 181px;
  font-size: 12px;
  padding-left: 2px;
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
  .el-avatar--circle {
    margin-top: 12px;
  }
}
/*控制显示文本的长度，超过长度使用省略号*/
.simpleContent {
  width: 50px;
  white-space: nowrap;
  word-break: keep-all;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>