<template>
  <!-- 地方风险等级 -->
  <div>
    <div id="brSelect">
      <el-radio-group :radio="radio" v-model="radio_new" @change="changeRadio">
        <svg-icon
          v-if="radio == 1"
          icon-class="br-select-di"
          @click.stop="click"
        />
        <svg-icon v-else icon-class="br-select-di-h" @click.stop="click" />
        <el-radio label="1">低风险</el-radio>
        <svg-icon
          v-if="radio == 2"
          icon-class="br-select-zhong"
          @click.stop="click"
        />
        <svg-icon v-else icon-class="br-select-zhong-h" @click.stop="click" />
        <el-radio label="2">中风险</el-radio>
        <svg-icon
          v-if="radio == 3"
          icon-class="br-select-gao"
          @click.stop="click"
        />
        <svg-icon v-else icon-class="br-select-gao-h" @click.stop="click" />
        <el-radio label="3">高风险</el-radio>
      </el-radio-group>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      radio_new: this.radio
    };
  },
  props: ['radio'],
  watch: {
    radio_new: {
      handler (val) {
        this.$emit("change-radio", val);
      },
      deep: true
    }
  },
  methods: {
    changeRadio (a) {
      // 将选中的label发送给父组件
      this.$emit("change-radio", a);
    },
  }
};
</script>

<style>
/* #brSelect >>> .el-radio__input.is-checked {
  color: #333 !important;
} */
#brSelect >>> .el-radio__input.is-checked + .el-radio__label {
  color: #606266 !important;
}
</style>