<template>
  <div class="box">
    <div class="block">
      <div class="shop" v-for="(item,index) in orderData" :key="index">
        <div class="line">
          <div class="pic" @click="selectShop(index)">
            <img
              :src="item.isShop == 1 ? 'https://sucai.suoluomei.cn/sucai_zs/images/20191121093322-1.png':'https://sucai.suoluomei.cn/sucai_zs/images/20191121093322-2.png'"
              alt
            />
          </div>
          <div>{{item.shopName}}</div>
        </div>
        <div class="row" v-for="(row,i) in item.goodsList" :key="i">
          <div class="pic" @click="selectGoods(index,i)">
            <img
              :src="row.isGoods == 1 ? 'http://sucai.suoluomei.cn/sucai_zs/images/20191121093322-1.png' :'https://sucai.suoluomei.cn/sucai_zs/images/20191121093322-2.png'"
              alt
            />
          </div>
          <div class="detail">
            <div class="photo">
              <img :src="row.defaultPic" alt />
            </div>
            <div class="info">
              <div class="title">{{row.productName}}</div>
              <div class="norm">{{row.format}}</div>
              <div class="rol">
                <span class="amount">￥{{row.price}}</span>
                <van-stepper v-model="row.count" disable-input @change="countNum()" />
                <div class="del" @click="delGoods(index,i)">
                  <img src="https://sucai.suoluomei.cn/sucai_zs/images/20191121100950-3.png" alt />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="close">
        <div class="all" @click="selectAll()">
          <div class="pic">
            <img
              :src="isAll == 1 ? 'https://sucai.suoluomei.cn/sucai_zs/images/20191121093322-1.png' : 'https://sucai.suoluomei.cn/sucai_zs/images/20191121093322-2.png'"
              alt
            />
          </div>
          <span>全选</span>
        </div>
        <div class="refer">
          <div class="total">
            <span>合计:</span>
            <span>￥{{allPrice}}</span>
          </div>
          <div class="settlement" @click="jiesuan">结算</div>
        </div>
      </div>
    </div>
  </div>
  <!-- 支付 -->
    
</template>

<script>
export default {
  data() {
    return {
      isAll: 0,
      allPrice: 0,
      orderData: [
        {
          shopId: 1,
          shopName: "乔丹官方旗舰店",
          isShop: 0,
          goodsList: [
            {
              id: 1,
              shopId: 1,
              shopName: "嘉恒体育x专注篮球鞋",
              isGoods: 0,
              defaultPic:
                "//gd2.alicdn.com/imgextra/i4/3893328982/O1CN01QEdM1i2GDo4zCkziS_!!3893328982.jpg_400x400.jpg",
              productId: 1,
              productName: "透气防滑耐磨加棉加绒运动鞋女学生战靴毒液球鞋",
              format: "花色42.5",
              price: 699,
              count: 1
            },
            {
              id: 2,
              shopId: 1,
              shopName: "嘉恒体育x专注篮球鞋",
              isGoods: 0,
              defaultPic:
                "https://img.alicdn.com/imgextra/i1/94504595/O1CN01qiJd5a1joYYNjpUfl-94504595.jpg_430x430q90.jpg",
              productId: 2,
              productName: "匹克态极闪现2代篮球鞋耐磨防滑实战球鞋低帮",
              format: "白绿色44",
              price: 288,
              count: 2
            }
          ]
        },
        {
          shopId: 2,
          shopName: "嘉恒体育x专注篮球鞋",
          isShop: 0,
          goodsList: [
            {
              id: 1,
              shopId: 2,
              shopName: "嘉恒体育x专注篮球鞋",
              isGoods: 0,
              defaultPic:
                "//gd2.alicdn.com/imgextra/i1/1109816712/O1CN01wue3p61zS91ctWrz0_!!1109816712.jpg_400x400.jpg",
              productId: 1,
              productName: "战靴防滑耐磨学生运动鞋鸳鸯鞋毒液实战球鞋",
              format: "白色43",
              price: 389,
              count: 1
            }
          ]
        },
        {
          shopId: 3,
          shopName: "JORDAN官方旗舰店",
          isShop: 0,
          goodsList: [
            {
              id: 1,
              shopId: 3,
              shopName: "JORDAN官方旗舰店",
              isGoods: 0,
              defaultPic:
                "https://img.alicdn.com/imgextra/i3/458694874/O1CN01Nc1fn81lsL1OvrX7F_!!458694874-0-lubanu-s.jpg_430x430q90.jpg",
              productId: 1,
              productName: "Jordan官方AJ34男子篮球鞋缓震DA1897",
              format: "彩色42",
              price: 1699,
              count: 1
            },
            {
              id: 2,
              shopId: 3,
              shopName: "JORDAN官方旗舰店",
              isGoods: 0,
              defaultPic:
                "https://img.alicdn.com/imgextra/i4/165520130/O1CN01yG2wUT1Cpa6s3XIRn_!!0-saturn_solar.jpg_220x220.jpg_.webp",
              productId: 1,
              productName: "Jordan官方AIR JORDAN男子篮球鞋缓震DA1897",
              format: "黑色39",
              price: 198,
              count: 1
            },
            {
              id: 2,
              shopId: 3,
              shopName: "JORDAN官方旗舰店",
              isGoods: 0,
              defaultPic:
                "https://img.alicdn.com/imgextra/i4/1361440200/O1CN01VWfEz91DLdqYZVu8l_!!0-saturn_solar.jpg_220x220.jpg_.webp",
              productId: 1,
              productName: "Jordan官方AIR JORDAN男子篮球鞋缓震DA1897",
              format: "黑色41",
              price: 369,
              count: 1
            },
            {
              id: 2,
              shopId: 3,
              shopName: "JORDAN官方旗舰店",
              isGoods: 0,
              defaultPic:
                "https://img.alicdn.com/imgextra/i1/211150168/O1CN01NU1gq91D6z9z85xsV_!!0-saturn_solar.jpg_220x220.jpg_.webp",
              productId: 1,
              productName: "Jordan官方AIR JORDAN男子篮球鞋缓震DA1897",
              format: "黑色42",
              price: 288,
              count: 2
            },
            {
              id: 3,
              shopId: 4,
              shopName: "JORDAN官方旗舰店",
              isGoods: 0,
              defaultPic:
                "https://img.alicdn.com/imgextra/i1/31591003/O1CN01ejNXTo1JHPt6YXMFu_!!0-saturn_solar.jpg_220x220.jpg",
              productId: 1,
              productName: "Jordan官方AIR JORDAN男子篮球鞋缓震DA1897",
              format: "绿色43",
              price: 645,
              count: 1
            },
            {
              id: 4,
              shopId: 5,
              shopName: "JORDAN官方旗舰店",
              isGoods: 0,
              defaultPic:
                "//img.alicdn.com/imgextra/i1/2170063091/O1CN01Jspfdr1YhioLt5coF_!!0-item_pic.jpg_430x430q90.jpg",
              productId: 1,
              productName: "Jordan官方AIR JORDAN男子篮球鞋缓震DA1897",
              format: "粉色44",
              price: 399,
              count: 1
            }
          ]
        }
      ],
      dialogVisible: false
    };
  },

  mounted() {},
  methods: {
    // 删除商品
    delGoods(index, i) {
      var list = this.orderData[index].goodsList;
      list.splice(i, 1);
      if (list.length == 0) {
        this.orderData.splice(index, 1);
      }
      this.watchAll();
      this.countPrice();
    },

    // 选择商品
    selectGoods(index, i) {
      var list = this.orderData[index].goodsList;
      if (list[i].isGoods == 0) {
        list[i].isGoods = 1;
      } else {
        list[i].isGoods = 0;
      }
      var anti = list.map(item => item.isGoods).indexOf(0);
      if (anti == -1) {
        this.orderData[index].isShop = 1;
      } else {
        this.orderData[index].isShop = 0;
      }
      this.watchAll();
      this.countPrice();
    },

    // 选择店铺
    selectShop(index) {
      var list = this.orderData;
      var goodsList = list[index].goodsList;
      if (list[index].isShop == 0) {
        list[index].isShop = 1;
        for (let i in goodsList) {
          goodsList[i].isGoods = 1;
        }
      } else {
        list[index].isShop = 0;
        for (let i in goodsList) {
          goodsList[i].isGoods = 0;
        }
      }
      this.watchAll();
      this.countPrice();
    },

    // 全选
    selectAll() {
      var list = this.orderData;
      if (list.length == 0) {
        this.isAll = 0;
        return;
      }
      if (this.isAll == 0) {
        this.isAll = 1;
        for (let i in list) {
          list[i].isShop = 0;
          this.selectShop(i);
        }
      } else {
        this.isAll = 0;
        for (let i in list) {
          list[i].isShop = 1;
          this.selectShop(i);
        }
      }
      this.countPrice();
    },

    // 监听全选
    watchAll() {
      var list = this.orderData;
      if (list.length == 0) {
        this.isAll = 0;
        return;
      }
      var result = [];
      for (let i in list) {
        for (let j in list[i].goodsList) {
          result.push(list[i].goodsList[j].isGoods);
        }
      }
      var anti = result.map(item => item).indexOf(0);
      if (anti == -1) {
        this.isAll = 1;
      } else {
        this.isAll = 0;
      }
    },

    // 更改商品数量
    countNum() {
      this.countPrice();
    },

    // 计算总价格
    countPrice() {
      let count = 0;
      var list = this.orderData;
      for (let i in list) {
        for (let j in list[i].goodsList) {
          if (list[i].goodsList[j].isGoods == 1) {
            count += list[i].goodsList[j].price * list[i].goodsList[j].count;
          }
        }
      }
      this.allPrice = count;
    },
    jiesuan(){
      alert('等待跳转支付页面... ')
    },
    handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      }
  }
};
</script>


<style lang="less" scoped>
  .box {
  position: relative;
  width: 100%;
  height: 100%;
  padding: 15px;
  box-sizing: border-box;
  background: #fff;
  /* margin-bottom: 104px; */
  padding-top: 10px;
}

.shop {
  margin-bottom: 60px;
  box-shadow: 0px 8px 20px 0px rgba(97, 97, 97, 0.19);
  border-radius: 12px;
  padding: 10px;
  box-sizing: border-box;
}

.line {
  display: flex;
  align-items: center;
}

.pic {
  width: 30px;
  height: 30px;
}

img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.line div:nth-child(2) {
  font-size: 18px;
  margin-left: 16px;
}

.row {
  display: flex;
  align-items: center;
}

.detail {
  width: 630px;
  height: 160px;
  margin-left: -36px;
  padding: 20px;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.photo {
  width: 100px;
  height: 100px;
}

.info {
  width: 74%;
  height: 100%;
  display: flex;
  justify-content: space-around;
  flex-direction: column;
  text-align: left;
}

.title {
  font-size: 16px;
}

.norm {
  font-size: 14px;
  color: #999999;
}

.rol {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.amount {
  font-size: 24px;
  font-weight: 600;
  color: #ff3b30;
}

.del {
  width: 30px;
  display: flex;
  align-items: center;
}

.close {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 54px;
  border-top: 1px solid #eeeeee;
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 50px;
}

.all {
  display: flex;
  align-items: center;
  margin-left: 15px;
}

.all span {
  color: #999999;
  font-size: 14px;
  margin-left: 5px;
}

.refer {
  display: flex;
  align-items: center;
  height: 100%;
}

.total {
  display: flex;
  align-items: center;
}

.total span:nth-child(1) {
  font-size: 18px;
  margin-right: 10px;
}

.total span:nth-child(2) {
  font-size: 28px;
  font-weight: 600;
  color: #ff3b30;
}

.settlement {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 120px;
  height: 100%;
  background: #ff3b30;
  color: #fff;
  font-size: 25px;
  margin-left: 15px;
}
</style>