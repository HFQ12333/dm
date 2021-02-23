import { get, post_media, patch, patch_media } from '@/utils/axios'

export default {
  namespaced: true,
  state: {
    accompany: [],
    accompany_id: []
  },
  mutations: {
    refreshQueryAccompany (state, data) {
      state.accompany = data
    },
    refreshDetailsAccompanyById (state, data) {
      state.accompany_id = data
    }
  },
  actions: {
    // 修改陪护通行信息
    async updateAccompany ({ dispatch }, params) {
      var data_form = new FormData();
      for (var key in params) {
        data_form.append(key, params[key])
      }
      let res = await patch_media("/personnelmgt/attendant/" + params.id + '?ward_id=' + params.ward_id, data_form);
      return res;
    },
    // 新增陪护信息
    async saveAccompany ({ dispatch }, params) {
      var data_form = new FormData();
      for (var key in params) {
        data_form.append(key, params[key])
      }
      let res = await post_media("/personnelmgt/attendant?ward_id=" + params.ward_id, data_form)
      return res
    },
    // 陪护信息详情
    async detailsAccompanyById ({ commit }, params) {
      let res = await get("/personnelmgt/attendant/" + params.id + '?ward_id=' + params.ward_id)
      commit("refreshDetailsAccompanyById", res.data)
    },

    // 查询陪护人员信息
    async queryAccompany ({ commit }, params) {
      let res = await get("/personnelmgt/attendant", params);
      commit("refreshQueryAccompany", res.data)
    }
  }
}