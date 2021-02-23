<script>
export default {
  data() {
    return {
      myplace: '', // 我的位置
      mycity: ''// 我所在的城市
    }
  },
  mounted() {
    this.initMap()
    
  },
  methods: {
    initMap() {
      let vm = this;
      let container = document.getElementById('container');
      container.style.height = '300px';
      var map = new BMap.Map(container);
      var point = new BMap.Point(116.331398,39.897445);
      map.centerAndZoom(point,16);

      var geolocation = new BMap.Geolocation();
      // 开启SDK辅助定位
      geolocation.enableSDKLocation();
      geolocation.getCurrentPosition(function(r){
        if(this.getStatus() == BMAP_STATUS_SUCCESS){
          var mk = new BMap.Marker(r.point);
          map.addOverlay(mk);
          map.panTo(r.point);
          console.log(r);
          let location = {
            lng:r.point.lng,
            lat:r.point.lat,
            province:r.address.province,
            city:r.address.city,
            district:r.address.district,
            street:r.address.street
          }
          // alert(JSON.stringify(location));
          vm.$emit('loaded', location) 
        }
        else {
          // alert('failed'+this.getStatus());
          vm.$emit('onError','设备异常，定位失败')
        }        
      },{enableHighAccuracy: true})
    }
  },
  render(createElement) {
    return createElement('div', {
      attrs: {
        id: 'container'
      }
    })
  }
}
</script>
