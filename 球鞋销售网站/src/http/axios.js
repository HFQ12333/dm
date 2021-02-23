import axios from 'axios';
import qs from 'qs'
import {Message} from 'element-ui'
axios.defaults.baseURL = 'http://47.102.210.174:6677/'
axios.defaults.headers.post['Content-Type']='application/x-www-form-urlencoded;charset=UTF-8'
// 请求拦截器
axios.interceptors.request.use((config)=>{
	if(config.method == 'post'){
		config.data = qs.stringify(config.data,{arrayFormat:'repeat'});
	}
	return config;
})
// 响应拦截器
axios.interceptors.response.use((response)=>{
	if(response.data.status===200){
		// Message({
		// 	message:response.data.message,
		// 	type:"error"
		// })
		return response.data;
	}
	else{
		Message({
			message:response.data.message,
			type:"error"
		})
		return response.data;
	}
	
})

export default axios;