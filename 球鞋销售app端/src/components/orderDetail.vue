<template>
  <div>
    <div>
      <q-item>
        <q-item-side>
          配送地址
        </q-item-side>
        <q-item-main>
          <q-item-tile>{{orderDetail.addressInfo.recipient}}/{{orderDetail.addressInfo.phone}}</q-item-tile>
          <q-item-tile>{{orderDetail.addressInfo.streetName}} {{orderDetail.addressInfo.postCode}}</q-item-tile>
        </q-item-main>
      </q-item>
      <q-item-separator/>
      <q-item>
        <q-item-side>
          配送方式
        </q-item-side>
        <q-item-main>
          <q-item-tile>{{orderDetail.shipInfo.ship}}</q-item-tile>
          <q-item-tile>{{orderDetail.shipInfo.cost}}元</q-item-tile>
        </q-item-main>
      </q-item>
      <q-item-separator/>
      <q-list-header>商品列表</q-list-header>
      <q-table
        :data="orderDetail.goodsList"
        :columns="columns"
        row-key="name"
        color="primary"
        hide-bottom
      >
        <q-td slot="body-cell-name" slot-scope="props" :props="props">
          <q-item-main class="text-center">
            <img :src="'statics/images/'+ props.row.goods.productImage" alt="poster" width="120" height="120">
            <div class="q-subheading">{{props.row.goods.productName}}</div>
          </q-item-main>
        </q-td>
        <q-td slot="body-cell-price" slot-scope="props" :props="props">
          <div class="q-subheading">￥{{props.row.goods.salePrice}}</div>
        </q-td>
        <q-td slot="body-cell-quantity" slot-scope="props" :props="props">
          <div class="q-subheading">{{props.row.goodsNum}}</div>
        </q-td>
        <q-td slot="body-cell-totalPrice" slot-scope="props" :props="props">
          <div class="q-subheading">￥{{props.row.goods.salePrice * props.row.goodsNum}}</div>
        </q-td>
      </q-table>
      <q-item-separator/>
      <q-item>
        <q-item-side>订单号：</q-item-side>
        <q-item-main>{{orderDetail.orderId}}</q-item-main>
      </q-item>
      <q-item>
        <q-item-side>订单总价：</q-item-side>
        <q-item-main>{{orderDetail.orderTotalPrice}}元</q-item-main>
      </q-item>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'OrderDetail',
  data() {
    return {
      columns: [
        { name: 'name', field: 'name', label: '商品', align: 'center' },
        { name: 'price', field: 'price', label: '单价', align: 'center' },
        { name: 'quantity', field: 'quantity', label: '数量', align: 'center' },
        { name: 'totalPrice', field: 'totalPrice', label: '总价', align: 'center' }
      ]
    }
  },
  created() {
    this.getOrderDetail()
    setTimeout(() => {
      this.getCartCount()
    }, 200)
  },
  computed: {
    ...mapGetters('user', ['orderDetail'])
  },
  methods: {
    ...mapActions('user', ['getOrderDetail', 'getCartCount'])
  }
}
</script>

<style>
</style>