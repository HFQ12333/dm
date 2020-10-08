<template>
    <div class="details">
        <el-link type="primary" @click="backHandle">返回</el-link>
        <!--tab开始-->
        <div>
            <el-tabs v-model="activeName">
                <el-tab-pane label="基本信息" name="first">
                <div style="text-align:center;width:400px;height:450px;border-radius:30px;border:1px solid;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)"><div>
      <br><el-avatar style="height:100px;width:100px;" src="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2646949702,3001420430&fm=26&gp=0.jpg"></el-avatar>
    </div>
    <el-divider><i class="el-icon-view"></i></el-divider>
                    <div>商品名称:{{this.customer.username}}
                        <br>
                        商品描述:{{this.customer.realname}}
                        <br>
                        <el-divider><i class="el-icon-mobile-phone"></i></el-divider>
                        电话号码:{{this.customer.telephone}}
                        <br>
                        <el-divider><i class="el-icon-money"></i></el-divider>
                        商品价格:{{this.customer.money}}RMB
                        <br>
                        <el-divider><i class="el-icon-office-building"></i></el-divider>
                        家庭住址:{{this.customer.province}}{{this.customer.city}}
                        <br>
                    </div></div>
                </el-tab-pane>
                <el-tab-pane label="订单信息" name="second">
                    订单信息
                </el-tab-pane>
                <el-tab-pane label="地址信息" name="third">
                    家庭住址:{{this.customer.province}}{{this.customer.city}}
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
