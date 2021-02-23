import { get, post_json, put, my_delete } from '@/utils/axios'
export default {
  namespaced: true,
  state: {
    device: [],
    ward: []
  },
  mutations: {
    refreshQueryDevices(state, data) {
      state.device = data
    },
    refreshQueryWard(state, data) {
      state.ward = data
    }
  },
  actions: {
    // 删除设备信息
    async deleteDevice({ dispatch }, params) {
      const res = await my_delete('/devicemgt/device/' + params.id, params.data)
      return res
    },
    // 修改设备信息
    async updateDevice({ dispatch }, params) {
      const res = await put('/devicemgt/device/' + params.id, params)
      return res
    },
    // 新增设备记录
    async saveDevice({ dispatch }, params) {
      const res = await post_json('/devicemgt/device', params)
      return res
    },
    // 查询设备信息
    async queryDeivces({ commit }, params) {
      const res = await get('/devicemgt/device', params)
      commit('refreshQueryDevices', res.data)
    },
    // 查询所有的病区
    async queryWards({ commit }, params) {
      const res = await get('/wardmgt/ward', params)
      commit('refreshQueryWard', res.data.results)
    }
  }
}
