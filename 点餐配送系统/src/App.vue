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
		background-color: #CFCFCF;
	}
	.app-main{
		padding:1em;
		background-color: #E8E8E8;
	}
	.sidebar-logo-container[data-v-6494804b]{
		background-color: #BCD2EE	 !important;
		border-bottom: 1px solid rgb(129, 143, 221);
		
	}
	.sidebar-logo-container .sidebar-logo-link .sidebar-title[data-v-6494804b] {
		color: #1b3977 !important;
	}
	#app .sidebar-container {
		border-right: 1px solid #ddd;
	}
</style>