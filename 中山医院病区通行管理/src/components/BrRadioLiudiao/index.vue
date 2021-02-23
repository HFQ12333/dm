<template>
  <!-- 地方风险等级 -->
  <div>
    <div id="brSelect">
      <el-radio-group :radio="radio" v-model="radio_new" @change="changeRadio">
        <svg-icon v-if="radio == 1" icon-class="br-select-right" />
        <svg-icon v-else icon-class="br-select-right-h" />
        <el-radio label="1">已出&#12288;</el-radio>
        <svg-icon v-if="radio == 2" icon-class="br-select-error" />
        <svg-icon v-else icon-class="br-select-error-h" />
        <el-radio label="2">未出&#12288;</el-radio>
      </el-radio-group>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      radio_new: this.radio,
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
  },
};
</script>

