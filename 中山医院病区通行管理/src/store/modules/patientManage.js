import {
  getWardsId,
  getWardsName
} from '@/utils/auth';
import {
  get,
  patch,
  post,
} from '@/utils/axios'
import axios from "axios";
import moment from "moment"
export default {
  namespaced: true,
  state: {
    patientData: [],
    patientDetail: [],
    attendantDetail: [],
    attendantThrough: [],
    emptyBed: [],
  },
  mutations: {
    // 病员信息列表
    refreshQueryPatientData (state, data) {
      state.patientData = data
    },
    // 病员详情列表
    setpatientDetail (state, data) {
      state.patientDetail = data
    },
    // 陪护详情列表
    attendantDetail (state, data) {
      state.attendantDetail = data
    },
    // 陪护个人的通行记录
    attendantThrough (state, data) {
      state.attendantThrough = data
    },
    // 空余床位
    emptyBed (state, data) {
      state.emptyBed = data
    },
  },
  actions: {
    // // 新增陪护通行记录
    // async saveTraffcRecord ({ dispatch }, params) {
    //   const res = await post_json('/personnelmgt/attendant/through', params)
    //   return res
    // },

    // 查询病员信息
    async queryPatientData ({
      commit
    }, params) {
      const res = await get('/personnelmgt/patient', params)
      commit('refreshQueryPatientData', res.data)
    },
    // 查询病员详情
    async queryPatientDetail ({
      commit
    }, params) {
      // const res = await get('/personnelmgt/patient/11?ward_id=2', params)
      const res = await get('/personnelmgt/patient/' + params.id, params.param)
      commit('setpatientDetail', res.data)
    },
    // 查询陪护详情
    async queryAttendantDetail ({
      commit
    }, params) {
      const res = await get('personnelmgt/attendant/' + params.id, params.param)
      commit('attendantDetail', res.data)
    },
    // 查看陪护个人的通行记录
    async queryAttendantThrough ({
      commit
    }, params) {
      const res = await get('personnelmgt/attendant/through', params)
      commit('attendantThrough', res.data)
    },
    // 查看空床信息
    async queryEmptyBed ({
      commit
    }, params) {
      const res = await get('wardmgt/ward/bed/empty', params)
      commit('emptyBed', res.data)
    },
    // 修改床位信息
    async changeBed ({
      dispatch
    }, params) {
      await patch("/personnelmgt/patient/" + params.id + '?ward_id=' + params.ward, params);
    },
    // 病员出院
    async patientLeave ({ dispatch }, params) {
      const res = await post('/personnelmgt/patient/leave/' + params.id + "?ward_id=" + params.ward_id);
      return res
    },
    async addPatient ({ dispatch }, params) {
      const res = await post('/personnelmgt/patient', params);
      return res
    },
    async editPatient ({ dispatch }, params) {
      await patch("/personnelmgt/patient/" + params.id + '?ward_id=' + params.ward, params);
    },
    // 导出
    exportList () {
      axios({
        url: '/personnelmgt/export',
        responseType: 'blob',
        params: {
          ward_id: getWardsId()
        }
      }).then((res) => {
        let blob = res;
        const fileReader = new FileReader(); // FileReader 对象允许Web应用程序异步读取存储在用户计算机上的文件的内容
        fileReader.readAsDataURL(blob); // 开始读取指定的Blob中的内容。一旦完成，result属性中将包含一个data: URL格式的Base64字符串以表示所读取文件的内容
        fileReader.onload = (event) => { // 处理load事件。该事件在读取操作完成时触发
          console.log(event);
          // 新建个下载的a标签，完成后移除。
          let a = document.createElement('a');
          let _fileName = getWardsName() + '住院病员陪护人员信息登记表-' + moment(new Date()).format('YYYY年MM月DD日') + '.xls';
          a.download = _fileName;
          a.href = event.target.result;
          document.body.appendChild(a);
          a.click();
          document.body.removeChild(a);
        }
      })
    },
  }
}
