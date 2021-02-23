<template>
  <!-- 病员详细信息 -->
  <div class="drawer_content drawer_content_detail">
    <el-row>
      <!-- {{ detailForm }} -->
      <el-col :span="10" class="div_center">
        <div>病区: {{ getWardsName }}</div>
        <div>身份证号: {{ detailForm.IDCard }}</div>
        <div>
          住院日期: {{ detailForm.enter_time
          }}{{ detailForm.leave_time ? "至" + detailForm.leave_time : "" }}
        </div>
        <div>
          入院体温:
          <span style="color: #67c23a"> {{ detailForm.temperature }}</span>
          °C
        </div>
        <div>
          <el-row>
            <el-col :span="14"><span>核酸检测报告状态:</span></el-col>
            <el-col :span="10">
              <div v-if="detailForm.nuclein == '2'">
                <svg-icon icon-class="br-select-error" />
                <span class="svg_icon_text">未出</span>
              </div>
              <div v-if="detailForm.nuclein == '1'">
                <svg-icon icon-class="br-select-right" />
                <span class="svg_icon_text">已出</span>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-col>

      <el-col :span="7" class="div_center">
        <div>病员姓名: {{ detailForm.name }}</div>
        <div>年龄: {{ detailForm.age }} 岁</div>
        <div>
          住院状态:
          <el-tag plain size="small">{{
            detailForm.hospitalization_status_text
          }}</el-tag>
        </div>
        <el-row style="margin-bottom: 0">
          <el-col :span="13"><span>地区风险等级:</span></el-col>
          <el-col :span="10">
            <div v-if="detailForm.risk_level == '1'">
              <svg-icon icon-class="br-select-di" />
              <span class="svg_icon_text">低风险</span>
            </div>
            <div v-if="detailForm.risk_level == '2'">
              <svg-icon icon-class="br-select-zhong" />
              <span class="svg_icon_text">中风险</span>
            </div>
            <div v-if="detailForm.risk_level == '3'">
              <svg-icon icon-class="br-select-gao" />
              <span class="svg_icon_text">高风险</span>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10"><span>流调信息:</span></el-col>
          <el-col :span="10">
            <div v-if="detailForm.survey == '2'">
              <svg-icon icon-class="br-select-error" />
              <span class="svg_icon_text">未出</span>
            </div>
            <div v-if="detailForm.survey == '1'">
              <svg-icon icon-class="br-select-right" />
              <span class="svg_icon_text">已出</span>
            </div>
          </el-col>
        </el-row>
      </el-col>

      <el-col :span="7" class="div_center">
        <div>性别: {{ detailForm.gender == 1 ? "男" : "女" }}</div>
        <div>住院号: {{ detailForm.addmission_num }}</div>
        <div>床位信息: {{ detailForm.bed_num }}</div>
        <div>
          <el-row>
            <el-col :span="14"><span>14天行程信息:</span></el-col>
            <el-col :span="10">
              <div v-if="detailForm.trip == '1'">
                <svg-icon icon-class="br-select-right" />
                <span class="svg_icon_text">绿色</span>
              </div>
              <div v-if="detailForm.trip == '2'">
                <svg-icon icon-class="br-select-error" />
                <span class="svg_icon_text">红色</span>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getWardsName } from "@/utils/auth";

export default {
  props: ["detailForm"],
  data () {
    return {
      getWardsName: getWardsName(),
    };
  },
  created () {

  },
  methods: {
    changeData () {
    },
  },
};
</script>

<style lang="scss" scoped>
.drawer_content {
  padding: 0 30px;
}
.drawer_content_detail >>> .svg-icon {
  float: left;
  height: 18px;
}
.svg-icon {
  margin-top: 0; // 适配不同屏幕
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
  // margin-top: 0.5em;
  margin-top: 0; // 适配不同屏幕
}
.div_center div {
  height: 32px;
  // line-height: 32px;
}
.drawer_content_detail {
  padding: 0 !important;
}
</style>