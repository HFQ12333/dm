<template>
  <!-- 病区管理 -->
  <div v-if="flag == 1" class="my_div div_one class_one">
    <el-button type="text" @click="flag = 2">+ 添加</el-button>
  </div>
  <div v-else-if="flag == 2" class="one div_two">
    <div class="one-mini-input">
      <el-form label-position="left" label-width="120px" :model="data">
        <el-form-item label="病区名称：">
          <el-input
            size="mini"
            placeholder="请输入病区名称"
            v-model="data.name"
          ></el-input>
        </el-form-item>
        <el-form-item label="床位数：">
          <el-input
            size="mini"
            placeholder="请输入床位数"
            v-model="data.numberOfBeds"
          ></el-input>
        </el-form-item>
        <el-form-item label="加床数：">
          <el-input
            size="mini"
            placeholder="请输入加床数"
            v-model="data.type"
          ></el-input>
        </el-form-item>
        <el-form-item label="陪护人员上限：">
          <el-input
            size="mini"
            placeholder="请输入人次"
            v-model="data.type"
          ></el-input>
        </el-form-item>
        <el-form-item label="病区描述：">
          <el-input
            size="mini"
            placeholder="请输入病区描述信息"
            v-model="data.type"
          ></el-input>
        </el-form-item>
      </el-form>
    </div>
    <div class="one-mini-select">
      <el-form label-position="top" label-width="120px" :model="data">
        <el-form-item label="设备名称：">
          <el-select
            size="mini"
            v-model="value1"
            filterable
            multiple
            collapse-tags
            placeholder="请点击或者搜索选择已有设备"
          >
            <el-option
              v-for="item in data.options1"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="工作账号：">
          <el-select
            size="mini"
            v-model="value2"
            filterable
            multiple
            collapse-tags
            placeholder="请点击或者搜索选择已有工作账号"
          >
            <el-option
              v-for="item in data.options2"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </div>
    <div class="one-button">
      <el-button-group>
        <el-button type="primary" plain size="mini" @click="changeFlag"
          >取消</el-button
        >
        <el-button type="primary" plain size="mini" @click="changeMyFlag"
          >确定</el-button
        >
      </el-button-group>
    </div>
  </div>
  <div v-else class="my_div div_two">
    <div style="padding-top: 8px; padding-bottom: 8px; font-weight: bold">
      A楼三层日间区
    </div>
    <div>床位数：50个</div>
    <div>加床数：5个</div>
    <div>陪护人员上限：3人次</div>
    <br />
    <div>病区描述：描述信息，描述信息，描述信息，描述信息</div>
    <div style="padding-top: 8px; padding-bottom: 8px; font-weight: bold">
      设备名称：
    </div>
    <el-tag type="info" size="mini">上海明略06</el-tag>
    <el-tag type="info" size="mini">上海明略07</el-tag>
    <div style="padding-top: 8px; padding-bottom: 8px; font-weight: bold">
      工作账号：
    </div>
    <el-tag type="info" size="mini">裴芳</el-tag>
    <el-tag type="info" size="mini">任轻松</el-tag>
    <div class="one-button">
      <el-button-group>
        <el-button type="primary" plain size="mini" @click="deleteSickRoom"
          >删除</el-button
        >
        <el-button type="primary" plain size="mini" @click="changeMyFlag"
          >编辑</el-button
        >
      </el-button-group>
    </div>
  </div>
</template>

<script>
export default {
  props: ["data", "model"],
  data() {
    return {
      flag: 1,
      radio: "1",
      value1: "",
      value2: "",
    };
  },
  created() {
    // 如果组件是模式2，关闭编辑框，展示信息
    if (this.model == 2) {
      this.flag = 3;
    }
  },
  methods: {
    changeFlag() {
      if (this.model == 2) {
        this.flag = 3;
      } else {
        this.flag = 1;
        // this.$emit("change-flag", flag);
      }
    },
    deleteSickRoom() {
      this.$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$message({
            type: "success",
            message: "删除成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    changeMyFlag() {
      if (this.model == 2) {
        // 关闭编辑框，展示信息
        if (this.flag == 2) {
          this.flag = 3
        } else {
          this.flag = 2
          // 在已有病区信息上编辑后保存信息到后台
        }
      } else {
        // 关闭模态框，显示添加
        this.flag = 1;
        // var flag = true;
        // this.$emit("change-flag", flag);
        // 提交信息到后台
      }
    },
  },
};
</script>

<style lang="scss">
.one {
  float: left;
  margin: 20px;
  padding: 5px;
  width: 255px;
  border: solid 1px rgb(24, 121, 255);
  border-radius: 1%;
  // 修改表单元素默认样式
  .el-form-item--medium {
    margin-bottom: 0px;
  }
  .el-form-item__label {
    padding-bottom: 0px;
    padding-right: 0px;
    height: 25px;
  }
  .one-mini-select {
    .el-input {
      float: right;
      width: 240px;
      margin: 3px;
    }
    .el-input__inner {
      width: 240px;
    }
    .el-tag--info {
      width: 98px;
    }
  }
  .one-mini-input {
    .el-input {
      float: right;
      width: 120px;
      margin: 3px;
    }
    .el-input__inner {
      width: 120px;
    }
  }
}
.one-button {
  position: absolute;
  bottom: 0;
  // border: solid 1px red;
  padding-bottom: 0px;
  margin-top: 5px;
  margin-left: -5px;
  .el-button-group {
    width: 255px;
  }
  .el-button--mini {
    width: 127px;
    height: 40px;
    border: 0;
  }
}
.my_div {
  // display: inline-block;
  float: left;
  margin: 20px;
  padding: 5px;
  width: 255px;
  height: 420px;
  border-radius: 1%;
}
.class_one {
  line-height: 420px;
}
.div_one {
  border: dashed 1px rgb(24, 121, 255);
  text-align: center;
  // line-height: 420px;
}
.div_two {
  overflow: scroll;
  min-height: 420px;
  border: solid 1px rgb(24, 121, 255);
  text-align: left;
  color: rgb(78, 79, 83);
  font-size: 14px;
  position: relative;
}
.el-tag--mini {
  margin-right: 10px;
}
</style>