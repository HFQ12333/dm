<template>
  <div class="briup_drawer">
    <el-drawer :size="width" :title="title" :visible.sync="visible">
      <el-divider />
      <!-- 中间插槽 -->
      <div class="briup_drawer_content">
        <slot name="content" />
      </div>
      <!-- 底部插槽 -->
      <div class="briup_drawer_footer">
        <div class="btns"><slot name="footer" /></div>
      </div>
    </el-drawer>
  </div>
</template>
<script>
export default {
  // 期待接受的参数
  props: ['drawerVisible', 'title', 'subtitle', 'width'],
  computed: {
    visible: {
      get() {
        return this.drawerVisible
      },
      set(val) {
        this.$emit('on-change-visible', val)
      }
    }
  },
  methods: {

  }
}
</script>
<style>
.briup_drawer_content .sub_title {
  color: rgb(139, 133, 133);
  font-size: 12px;
  /* border-bottom: 1px solid #ccc; */
  background-color: #ededed;
  padding: 1em 0.5em;
  border-radius: 3px;
  margin-bottom: 1em;
}
</style>
<style scoped>
.briup_drawer_header {
  position: absolute;
  top: 0;
  width: 100%;
  height: 50px;
  line-height: 50px;
  padding: 0 2em;
}
.subtitle {
  font-size: 10px;
}
.briup_drawer_content {
  padding: 1em 1em 3em;
  overflow: scroll;
  position: absolute;
  width: 100%;
  top: 3em;
  bottom: 3em;
}
.briup_drawer_footer {
  position: absolute;
  bottom: 0;
  height: 50px;
  line-height: 50px;
  text-align: center;
  background-color: #ffffff;
  width: 100%;
  border-top: 1px solid #ededed;
}
/* .briup_drawer_footer {
  height: 50px;
  position: fixed;
  bottom: 0;
  width: 100%;
  border-top: 1px solid #ededed;
  background-color: #ffffff;
}
.btns {
  position: absolute;
  bottom: 0;
  right: 0;
  height: 50px;
  line-height: 50px;
  text-align: center;
  width: 47%;
} */
</style>
