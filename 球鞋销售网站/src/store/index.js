import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import user from './modules/user'
// 导入customer的index.js
import customer from './customer/customer'
import product from './product/product'
// import category from './categroy/categroy'
import comment from './comment/comment'
import waiter from './waiter/waiter'
import order from './order/order'
import address from './address/address'



Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    settings,
    user,
    // customer模块
    customer,
    //product模块
    product,
    // category,
    comment,
    waiter,
    order,
    address
  },
  getters
})

export default store
