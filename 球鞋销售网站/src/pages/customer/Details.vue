<template>
    <div class="details">
        <el-link type="primary" @click="backHandle">返回</el-link>
        <!--tab开始-->
        <div>
            <el-tabs v-model="activeName">
                <el-tab-pane label="基本信息" name="first">
                <div style="text-align:center;width:500px;height:550px;border-radius:1px;border:1px dashed;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)"><div>
      <br><el-avatar style="height:150px;width:150px;" src="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1584500416,39847924&fm=26&gp=0.jpg"></el-avatar>
    </div>
    <el-divider><i class="el-icon-view"></i></el-divider>
                    <div>昵称:物以类聚 ,‘{{this.customer.realname}}
                        <br>
                        密码:5435342efd{{this.password}}
                        <br>
                        <el-divider><i class="el-icon-mobile-phone"></i></el-divider>
                        手机号:13223543537{{this.customer.telephone}}
                        <br>
                        <el-divider><i class="el-icon-money"></i></el-divider>
                        实名认证:认证成功{{this.customer.money}}
                        <br>
                        <br>
                    </div></div>
                </el-tab-pane>
                <el-tab-pane label="订单信息" name="second">
                        <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603876325804&di=eb20d84173b6db21c00f78258d64856c&imgtype=0&src=http%3A%2F%2Fgss0.baidu.com%2F-4o3dSag_xI4khGko9WTAnF6hhy%2Fzhidao%2Fpic%2Fitem%2Fcf1b9d16fdfaaf510f33265d8e5494eef11f7a04.jpg" alt="">
                </el-tab-pane>
                <el-tab-pane label="地址信息" name="third">
                   商品配送住址:</br>
                   山西省晋中市太谷区山西农业大学新北门18号楼206宿舍{{this.customer.province}}{{this.customer.city}}
                </el-tab-pane>
            </el-tabs>
        </div>
        <!--tab结束-->

    </div>
</template>
<script>
import {mapState,mapActions} from "vuex";
export default{
    data(){
        return{
            activeName:"first",
        };
    },
    created(){
        this.findCustomerById(this.$route.params.id);
    },
    computed:{
        ...mapState("customer",["customer"]),
    },
    methods:{
        ...mapActions("customer",["findCustomerById"]),
        backHandle(){
            this.$router.push({name:"customer"})
        }
    }
}
</script>
