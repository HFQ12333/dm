import { get, put, post, my_delete } from '@/utils/axios'
export default {
  namespaced: true,
  state: {
    sickroom: []
  },
  mutations: {
    refreshQuerySickRoom(state, data) {
      state.sickroom = data
    }
  },
  actions: {
    // 修改病区
    async updateSickRoom({ dispatch }, params) {
      const res = await put('/wardmgt/ward/' + params.id, params)
      return res
    },
    // 删除病区
    async deleteSickRoom({ dispatch }, params) {
      const res = await my_delete('/wardmgt/ward/' + params.id)
      return res
    },
    // 新增病区
    async saveSickRoom({ dispatch }, params) {
      const res = await post('/wardmgt/ward', params)
      return res
    },
    // 分页查询所有病区
    async querySickRoom({ commit }, params) {
      const res = await get('/wardmgt/ward', params)
      commit('refreshQuerySickRoom', res.data)
    }
  }
}
