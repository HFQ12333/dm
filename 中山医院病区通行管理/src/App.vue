<template>
  <div id="app">
    <router-view v-if="isRouterAlive" />
  </div>
</template>

<script>
export default {
  name: 'App',
  provide () {
    //父组件中通过provide来提供变量，在子组件中通过inject来注入变量                                       
    return {
      reload: this.reload
    }
  },
  data () {
    return {
      isRouterAlive: true
    }
  },
  methods: {
    reload () {
      this.isRouterAlive = false;
      this.$nextTick(function () {
        this.isRouterAlive = true;
      })
    }
  }

}
</script>

<style>
.changePage {
  width: 700px;
  position: absolute;
  bottom: 1rem;
  left: 50%;
  margin: 1rem 0 0 -330px;
  text-align: center;
}
</style>