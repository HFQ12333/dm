import { get, post } from '@/utils/request'
export default {
    namespaced:true,
    state: {
        signs:[]
    },
    mutations: {
        refreshPageQuerySign(state,signs){
            state.signs = signs
        }
    },
    actions: {
        async PageQuerySign({commit},params){
            let response = await get('/sign/pageQuery',params)
            commit('refreshPageQuerySign',response.data)
        }
    }
}