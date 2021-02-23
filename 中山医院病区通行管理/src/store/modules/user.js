import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken, setWardsName, getWardsName, setWardsId, getWardsId, setRole, setUserName } from '@/utils/auth'
import { resetRouter } from '@/router'

const state = {
  token: getToken(),
  name: '',
  avatar: '',
  introduction: '',
  roles: [],
  wardsName: getWardsName(),
  wardsId: getWardsId(),
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_WARDS_NAME: (state, wardsName) => {
    state.wardsName = wardsName
  },
  SET_INTRODUCTION: (state, introduction) => {
    state.introduction = introduction
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  }
}

const actions = {
  // user login
  login ({ commit }, userInfo) {
    const { account, password } = userInfo
    setUserName(account.trim())
    return new Promise((resolve, reject) => {
      login({ account: account.trim(), password: password }).then(response => {
        const { data } = response
        commit('SET_TOKEN', data.token)
        setToken(data.token)
        // 存放当前用户的病区信息
        // commit('SET_WARDS_NAME', data.wards[0].name)
        console.log(data);
        setWardsName(data.wards[0].name)
        setWardsId(data.wards[0].id)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo ({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(
        response => {
          const { data } = response

          if (!data) {
            reject('Verification failed, please Login again.')
          }
          const { username, first_name, is_manager, headimg, introduction } = data;
          var roles = [];
          if (is_manager) {
            roles = ['admin']
          }
          else {
            roles = ['editor']
          }
          setRole(roles)
          // roles must be a non-empty array
          if (!roles || roles.length <= 0) {
            reject('getInfo: roles must be a non-null array!')
          }

          commit('SET_ROLES', roles)
          commit('SET_NAME', username)
          commit('SET_AVATAR', headimg)
          // commit('SET_INTRODUCTION', introduction)
          resolve(data)
        }).catch(error => {
          reject(error)
        })
    })
  },

  // user logout
  logout ({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        commit('SET_TOKEN', '')
        commit('SET_ROLES', [])
        removeToken()
        resetRouter()

        // reset visited views and cached views
        // to fixed https://github.com/PanJiaChen/vue-element-admin/issues/2485
        dispatch('tagsView/delAllViews', null, { root: true })

        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken ({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      commit('SET_ROLES', [])
      removeToken()
      resolve()
    })
  },

  // dynamically modify permissions
  // async changeRoles({ commit, dispatch }, role) {
  //   const token = role + '-token'

  //   commit('SET_TOKEN', token)
  //   setToken(token)

  //   const { roles } = await dispatch('getInfo')

  //   resetRouter()

  //   // generate accessible routes map based on roles
  //   const accessRoutes = await dispatch('permission/generateRoutes', roles, { root: true })
  //   // dynamically add accessible routes
  //   router.addRoutes(accessRoutes)

  //   // reset visited views and cached views
  //   dispatch('tagsView/delAllViews', null, { root: true })
  // }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
