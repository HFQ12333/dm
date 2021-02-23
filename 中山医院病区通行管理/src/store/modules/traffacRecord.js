import { get, post_media, patch } from '@/utils/axios'

export default {
  namespaced: true,
  state: {
    traffacRecord: [],
    traffacRecord_id: []
  },
  mutations: {
    // 陪护信息列表
    refreshQueryPersonlmgtThought (state, data) {
      state.traffacRecord = data
    },
    refreshQueryPersonlmgtId (state, data) {
      state.traffacRecord_id = data
    }
  },
  actions: {
    // 新增陪护通行记录
    async saveTraffcRecord ({ dispatch }, params) {
      var data_form = new FormData();
      for (var key in params) {
        data_form.append(key, params[key])
      }
      const res = await post_media('/personnelmgt/attendant/through', data_form)
      return res
    },
    // 修改陪护通行信息
    async updateTraffcRecord ({ dispatch }, params) {
      let res = await patch("/personnelmgt/attendant/through/" + params.id + '?ward_id=' + params.ward_id, params.data);
      return res;
    },
    // 查询陪护通行信息列表
    async queryPersonlmgtThought ({ commit }, params) {
      const res = await get('/personnelmgt/attendant/through', params)
      commit('refreshQueryPersonlmgtThought', res.data)
    },
    // 查询陪护通行信息列表详情
    async queryPersonlmgtThoughtId ({ commit }, params) {
      const res = await get('/personnelmgt/attendant/through', params)
      commit('refreshQueryPersonlmgtId', res.data)
    }
  }
}
