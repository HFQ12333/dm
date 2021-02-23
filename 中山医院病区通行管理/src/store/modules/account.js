import { get, put, my_delete, post_json } from '@/utils/axios'
export default {
  namespaced: true,
  state: {
    account:[],
    accountB:[],
    ward:[]
  },
  mutations: {
    refreshQueryAccount(state, data) {
      state.account = data
    },
    refreshQueryAccountB(state, data) {
      state.accountB = data
    },
    refreshQueryWard(state, data) {
      state.ward = data
    }
  },
  actions: {
 // 查询账号
  async queryAccount({ commit }, paramsA) {
    const res = await get('/systemmgt/account', paramsA)
    commit('refreshQueryAccount', res.data)
    },
  async queryAccountB({ commit }, paramsB) {
    const res = await get('/systemmgt/account', paramsB)
    commit('refreshQueryAccountB', res.data)
    },
  // 删除账号
  async deleteAccount({ dispatch }, params) {
    const res = await my_delete('/systemmgt/account/' + params.id, params.data)
    return res
  },
  // 修改账号
  async updateAccount({ dispatch }, params) {
    const res = await put('/systemmgt/account/' + params.id, params)
    return res
  },
  // 新增账号
  async saveAccount({ dispatch }, params) {
    const res = await post_json('/systemmgt/account', params)
    return res
  },
  // 查询所有病区
  async queryWards({ commit }, params) {
  const res = await get('/wardmgt/ward', params)
  commit('refreshQueryWard', res.data.results)
  },
}
}