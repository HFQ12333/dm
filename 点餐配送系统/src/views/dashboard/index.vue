<template>
  <el-table
    :data="tableData"
    style="width: 100%"
    :row-class-name="tableRowClassName"
  >
    <el-table-column
      prop="date"
      label="下单时间"
      width="180"
    />
    <el-table-column
      prop="name"
      label="顾客姓名"
      width="180"
    />
    <el-table-column
      prop="address"
      label="配送地址"
    />
    <el-table-column
      prop="product"
      label="下单菜品"
    />
    <el-table-column
      prop="telephone"
      label="联系方式"
    />
    <el-table-column
      prop="price"
      label="价格"
    />
    <el-table-column
      prop="qname"
      label="骑手姓名"
    />
    <el-table-column
      prop="phone"
      label="骑手电话"
    />
  </el-table>
</template>

<style>
  .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>

<script>
export default {
  data() {
    return {
      tableData: [{
        date: '2019-05-02',
        name: '王小虎',
        address: '苏州市禁品大道 1143 号',
        product: '宫保鸡丁',
        telephone: '1234565432',
        price: '33',
        qname: '王美团',
        phone: '13223543637'
      }, {
        date: '2019-07-04',
        name: '张强',
        address: '上海市普陀区金沙江路 1518 弄',
        product: '宫保鸡丁',
        telephone: '1234565432',
        price: '33',
        qname: '王美团',
        phone: '13223543637'
      }, {
        date: '2019-09-01',
        name: '小白',
        address: '苏州市昆明区金沙江路 1122 弄',
        product: '宫保鸡丁',
        telephone: '1234565432',
        price: '33',
        qname: '王美团',
        phone: '13223543637'
      }, {
        date: '2019-05-13',
        name: '杰克',
        address: '杭州市浦东区 1312 弄',
        product: '宫保鸡丁',
        telephone: '1234565432',
        price: '33',
        qname: '王美团',
        phone: '13223543637'
      },
      {
        date: '2019-05-13',
        name: '布朗',
        address: '云南浦东区 1312 弄',
        product: '宫保鸡丁',
        telephone: '1234565432',
        price: '33',
        qname: '王美团',
        phone: '13223543637'
      },
      {
        date: '2019-05-13',
        name: '阿东',
        address: '阿里市浦东区 212 弄',
        product: '宫保鸡丁',
        telephone: '1234565432',
        price: '33',
        qname: '王美团',
        phone: '13223543637'
      },
      {
        date: '2019-06-13',
        name: '小张',
        address: '浙江浦东区 122212 弄',
        product: '宫保鸡丁',
        telephone: '1234565432',
        price: '33',
        qname: '王美团',
        phone: '13223543637'
      },
      {
        date: '2019-01-13',
        name: '顾客1',
        address: '张家口市浦东区 11号',
        product: '宫保鸡丁',
        telephone: '1234565432',
        price: '33',
        qname: '王美团',
        phone: '13223543637'
      },
      {
        date: '2019-09-13',
        name: '客户2',
        address: '徐州市浦东区 452号',
        product: '宫保鸡丁',
        telephone: '1234565432',
        price: '33',
        qname: '王美团',
        phone: '13223543637'
      },
      {
        date: '2019-11-13',
        name: '王红',
        address: '南京市浦东区 121号',
        product: '宫保鸡丁',
        telephone: '1234565432',
        price: '33',
        qname: '王美团',
        phone: '13223543637'
      },
      {
        date: '2019-12-13',
        name: '小胡',
        address: '苏州市浦东区 011号',
        product: '宫保鸡丁',
        telephone: '1234565432',
        price: '33',
        qname: '王美团',
        phone: '13223543637'
      }]
    }
  },
  methods: {
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex === 1) {
        return 'warning-row'
      } else if (rowIndex === 3) {
        return 'success-row'
      }
      return ''
    }
  }
}
</script>
