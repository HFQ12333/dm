(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d225f5b"],{e72e:function(e,t,i){"use strict";i.r(t);var s=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"details"},[i("el-link",{attrs:{type:"primary"},on:{click:e.backHandle}},[e._v("返回")]),e._v(" "),i("div",[i("el-tabs",{model:{value:e.activeName,callback:function(t){e.activeName=t},expression:"activeName"}},[i("el-tab-pane",{attrs:{label:"基本信息",name:"first"}},[i("div",{staticStyle:{"text-align":"center",width:"400px",height:"450px","border-radius":"30px",border:"1px solid","box-shadow":"0 2px 12px 0 rgba(0, 0, 0, 0.1)"}},[i("div",[i("br"),i("el-avatar",{staticStyle:{height:"100px",width:"100px"},attrs:{src:"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2646949702,3001420430&fm=26&gp=0.jpg"}})],1),e._v(" "),i("el-divider",[i("i",{staticClass:"el-icon-view"})]),e._v(" "),i("div",[e._v("商品名称:"+e._s(this.customer.username)+"\n                    "),i("br"),e._v("\n                    商品描述:"+e._s(this.customer.realname)+"\n                    "),i("br"),e._v(" "),i("el-divider",[i("i",{staticClass:"el-icon-mobile-phone"})]),e._v("\n                    电话号码:"+e._s(this.customer.telephone)+"\n                    "),i("br"),e._v(" "),i("el-divider",[i("i",{staticClass:"el-icon-money"})]),e._v("\n                    商品价格:"+e._s(this.customer.money)+"RMB\n                    "),i("br"),e._v(" "),i("el-divider",[i("i",{staticClass:"el-icon-office-building"})]),e._v("\n                    家庭住址:"+e._s(this.customer.province)+e._s(this.customer.city)+"\n                    "),i("br")],1)],1)]),e._v(" "),i("el-tab-pane",{attrs:{label:"订单信息",name:"second"}},[e._v("\n                订单信息\n            ")]),e._v(" "),i("el-tab-pane",{attrs:{label:"地址信息",name:"third"}},[e._v("\n                家庭住址:"+e._s(this.customer.province)+e._s(this.customer.city)+"\n            ")])],1)],1)],1)},a=[],n=i("5530"),c=i("2f62"),r={data:function(){return{activeName:"first"}},created:function(){this.findCustomerById(this.$route.params.id)},computed:Object(n["a"])({},Object(c["d"])("customer",["customer"])),methods:Object(n["a"])(Object(n["a"])({},Object(c["b"])("customer",["findCustomerById"])),{},{backHandle:function(){this.$router.push({name:"customer"})}})},o=r,l=i("2877"),d=Object(l["a"])(o,s,a,!1,null,null,null);t["default"]=d.exports}}]);