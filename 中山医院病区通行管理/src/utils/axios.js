import axios from 'axios';
import qs from 'qs'
import { getToken } from '@/utils/auth'
import { Message } from 'element-ui';
// 全局配置
// axios.defaults.baseURL = 'http://10.0.0.220:8000/api/';
axios.defaults.baseURL = 'http://172.16.0.9:20000/api/';

// 添加请求拦截器
axios.interceptors.request.use(function (config) {
  // 在发送请求之前做些什么
  config.headers['Authorization'] = getToken();
  return config;
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error);
});


// 添加响应拦截器
axios.interceptors.response.use(function (response) {
  // 对响应数据做点什么
  var res = response.data;
  var code = response.data.code;
  if (code == 200) {
    // 响应正常
    // Message({
    //   message: res.msg,
    //   type: 'success'
    // });
  } else if (code == 202) {
    Message({
      message: res.msg,
      type: 'warning'
    });
  } else if (code == 400) {
    Message({
      message: res.msg,
      type: 'error'
    });
  }
  return res;
}, function (error) {
  // 对响应错误做点什么
  console.log(error.response);
  if (error.response.data.code == 400) {
    Message({
      message: error.response.data.msg,
      type: 'error'
    });
  }
  return Promise.reject(error.response);
});

export function patch (url, data) {
  return axios({
    method: 'patch',
    url,
    data,
    timeout: 10000
  })
};

export function patch_media (url, data) {
  return axios({
    method: 'patch',
    url,
    data,
    timeout: 10000,
    headers: {
      'Content-Type': 'multipart/form-data;boundary=<calcalated when request is sent;>'
    }
  })
};

/**
  get方式请求
*/
export function get (url, params) {
  return axios({
    method: 'get',
    url,
    params, // get 请求时带的参数
    timeout: 10000,
    headers: {
      'X-Requested-With': 'XMLHttpRequest'
    }
  })
};

/**
  delete方式请求
*/
export function my_delete (url, params) {
  return axios({
    method: 'delete',
    url,
    params,
    timeout: 10000,
    headers: {
      'X-Requested-With': 'XMLHttpRequest'
    }
  })
}
/**
  delete方式请求
*/
export function put (url, data) {
  return axios({
    method: 'put',
    url,
    data,
    timeout: 10000,
    headers: {
      'X-Requested-With': 'XMLHttpRequest',
      'Content-Type': 'application/json'

    }
  })
}
/**
 * 提交post请求 发送的数据为查询字符串，key=val&key=val
*/
export function post (url, data) {
  return axios({
    method: "post",
    url,
    data: qs.stringify(data),
    timeout: 10000,
    headers: {
      'X-Requested-With': 'XMLHttpRequest',
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
    }
  })
}

export function post_media (url, data) {
  return axios({
    method: "post",
    url,
    data: data,
    timeout: 10000,
    headers: {
      'Content-Type': 'multipart/form-data;boundary=<calcalated when request is sent;>'
    }
  })
}
/**
 * 提交post请求 ,查询字符串，对象中嵌套数组的格式
*/
export function post_obj_array (url, data) {
  return axios({
    method: "post",
    url,
    data: qs.stringify(data, { allowDots: true }),
    timeout: 10000,
    headers: {
      'X-Requested-With': 'XMLHttpRequest',
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
    }
  })
}

/**
 * 提交post请求 发送的数据为查询字符串，当参数为数组的时候适用该方法
 * ids=1&ids=2
*/
export function post_array (url, data) {
  return axios({
    method: "post",
    url,
    data: qs.stringify(data, { arrayFormat: "repeat" }),
    timeout: 10000,
    headers: {
      'X-Requested-With': 'XMLHttpRequest',
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
    }
  })
}
/**
 * 提交post请求 发送的数据为json字符串
*/
export function post_json (url, data) {
  return axios({
    method: "post",
    url,
    data,
    timeout: 10000
  })
}


export default axios