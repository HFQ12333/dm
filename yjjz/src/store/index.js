import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import user from './modules/user'

Vue.use(Vuex)
import waiters from './waiters/index'
import customer from './customer/index'
const store = new Vuex.Store({
  modules: {
    app,
    settings,
    user,
    waiters,
    customer
  },
  getters
})

export default store
