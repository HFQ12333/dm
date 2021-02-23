<script>
export default {
  data() {
    return { }
  },
  mounted() {
    this.init()
  },
  props:["data"],
  methods: {
    init() {
      // 初始化表格
      let main_chart = document.getElementById('main_chart')
      main_chart.style.height = '420px'
      var myChart = echarts.init(main_chart);
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption({
          tooltip: {},
          legend: {
              data:['价格']
          },
          xAxis: {
              type: 'category',
              data: this.data.dates
          },
          yAxis: {
              type: 'value'
          },
          series: [{
              data: this.data.prices,
              type: 'line'
          }]
      });
    },
  },
  render(createElement) {
    return createElement('div', {
      attrs: {
        id: 'main_chart'
      }
    })
  }
}
</script>
