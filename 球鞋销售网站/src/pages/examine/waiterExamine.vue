<template>
	<div>
<el-progress type="circle" :percentage="0"></el-progress>
<el-progress type="circle" :percentage="25"></el-progress>
<el-progress type="circle" :percentage="100" status="success"></el-progress>
<el-progress type="circle" :percentage="70" status="warning"></el-progress>
<el-progress type="circle" :percentage="50" status="exception"></el-progress>
<br>
<p>日收益&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月收益&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	年收益&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;好评比例&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
	</div>
</template>
<style scoped>
div{
	text-align: center;
}
</style>