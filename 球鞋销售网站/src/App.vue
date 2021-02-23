<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>
<style type="text/css">
	#app{
		overflow: hidden;
	}
	#app .main-container:nth-child(2){
		background-color: rgb(240, 231, 240);
	}
	.app-main{
		padding:1em;
		background-color: white;
	}
	.sidebar-logo-container[data-v-6494804b]{
		background-color: rgb(218, 205, 218) !important;
		border-bottom: 1px solid rgb(255, 255, 255);
		
	}
	.sidebar-logo-container .sidebar-logo-link .sidebar-title[data-v-6494804b] {
		color: rgb(255, 255, 255) !important;
	}
	#app .sidebar-container {
		border-right: 1px solid rgb(255, 255, 255);
	}
</style>