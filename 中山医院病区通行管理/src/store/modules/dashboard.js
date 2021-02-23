import {
  get,
} from '@/utils/axios'

export default {
  namespaced: true,
  state: {
    workBenchData: [],
    changeBedDrawer: false,
    changeDetailDrawer: false,
    setBedDrawer: false,
    bedNum: '',
    setAttendantDetailDrawre: false,
    peihudengjiDrawer: false,
    bingyuandengjiDrawer: false,
    changePerDrawer: false,
    attendantData: {},
    patientDetailDrawer: false,
    genghuanpeihuDrawer: false,
    patientId: '',
  },
  mutations: {
    // 病员信息列表
    workBenchData (state, data) {
      state.workBenchData = data
    },
    changeBedDrawer (state, data) {
      state.changeBedDrawer = data
    },
    changeDetailDrawer (state, data) {
      state.changeDetailDrawer = data
      console.log(state.changeDetailDrawer);
    },
    setBedDrawer (state, data) {
      state.setBedDrawer = data
    },
    setBedNum (state, data) {
      state.bedNum = data
    },
    setAttendantDetailDrawre (state, data) {
      state.setAttendantDetailDrawre = data
    },
    attendantData (state, data) {
      state.attendantData = data
    },
    peihudengjiDrawer (state, data) {
      state.peihudengjiDrawer = data
    },
    bingyuandengjiDrawer (state, data) {
      state.bingyuandengjiDrawer = data
    },
    patientId (state, data) {
      state.patientId = data
    },
    changePerDrawer (state, data) {
      state.changePerDrawer = data
    },
    patientDetailDrawer (state, data) {
      state.patientDetailDrawer = data
    },
    genghuanpeihuDrawer (state, data) {
      state.genghuanpeihuDrawer = data
    }
  },
  actions: {
    // 查询病员信息
    async queryWorkBenchData ({
      commit
    }, params) {
      const res = await get('/workbench/index/info/' + params.id, params.param)
      commit('workBenchData', res.data)
    },
    // 换床抽屉开关
    changeBedDrawerHandle ({
      commit
    }, params) {
      commit('changeBedDrawer', params)
    },
    // 病员详情抽屉开关
    changeDetailDrawerHandle ({
      commit
    }, params) {
      commit('changeDetailDrawer', params)
    },
    // 分配床位抽屉开关
    setBedDrawerHandle ({
      commit
    }, params) {
      commit('setBedDrawer', params)
    },
    // 分配床位时床号
    setBedNumHandle ({
      commit
    }, params) {
      commit('setBedNum', params)
    },
    // 陪护通行异常抽屉
    changeSetAttendantDetailDrawre ({
      commit
    }, params) {
      commit('setAttendantDetailDrawre', params)
    },
    // 打开通行异常抽屉时传递参数
    setAttendantData ({
      commit
    }, params) {
      commit('attendantData', params)
    },
    // 打开陪护登记抽屉
    changePeihudengjiDrawer ({
      commit
    }, params) {
      commit('peihudengjiDrawer', params)
    },
    // 打开病员登记抽屉
    changeBingyuandengjiDrawer ({
      commit
    }, params) {
      commit('bingyuandengjiDrawer', params)
    },
    // 打开陪护人员登记/更换时抽屉时传递参数
    setPatientId ({
      commit
    }, params) {
      commit('patientId', params)
    },
    setChangePerDrawer ({
      commit
    }, params) {
      commit('changePerDrawer', params)
    },
    // 打开病员详情抽屉
    changePatientDetailDrawer ({
      commit
    }, params) {
      commit('patientDetailDrawer', params)
    },
    // 打开更换陪护抽屉
    changeGenghuanpeihuDrawer ({
      commit
    }, params) {
      commit('genghuanpeihuDrawer', params)
    },

  }
}
