import { get } from '@/utils/axios'
export default {
  namespaced: true,
  state: {
    log: []
  },
  mutations: {
    refreshQueryLog(state, data) {
      state.log = data
    }
  },
  actions: {
  // 查询日志信息
    async queryLogs({ commit }, params) {
      const res = await get('/systemmgt/logs', params)
      commit('refreshQueryLog', res.data)
    }
  }
}
