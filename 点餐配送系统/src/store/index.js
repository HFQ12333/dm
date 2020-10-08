import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import user from './modules/user'
// 导入customer的index.js
import customer from './customer/index'
import product from './product/product'
import category from './category/index'
import order from './order/index'
import waiter from './waiter/index'
import comment from './comment/index'
import address from './address/index'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    settings,
    user,
    // customer模块
    customer,
    // product模块
    product,
    category,
    order,
    waiter,
    comment,
    address
  },
  getters
})

export default store
