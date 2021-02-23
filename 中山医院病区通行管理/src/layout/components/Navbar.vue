<template>
  <div class="navbar">
    <!-- 打开/关闭sidebar的按钮 -->
    <!-- <hamburger
      id="hamburger-container"
      :is-active="sidebar.opened"
      class="hamburger-container"
      @toggleClick="toggleSideBar"
    /> -->
    <!-- 面包屑 -->
    <!-- <breadcrumb id="breadcrumb-container" class="breadcrumb-container" /> -->
    <!-- 添加顶部导航栏Sidebar -->
    <logo :collapse="false" />
    <div style="display: inline-block" class="mysidebar-container">
      <el-menu
        class="el-menu-demo"
        mode="horizontal"
        :default-active="activeMenu"
        :background-color="variables.menuBg"
        :text-color="variables.menuText"
        :unique-opened="false"
        :active-text-color="variables.menuActiveText"
        :collapse-transition="false"
      >
        <sidebar-item
          v-for="route in permission_routes"
          :key="route.path"
          :item="route"
          :base-path="route.path"
        />
      </el-menu>
    </div>
    <!-- 顶部导航栏结束 -->

    <div class="right-menu">
      <div class="now-time">{{ nowTime }}</div>
      <template v-if="device !== 'mobile'">
        <!-- <search id="header-search" class="right-menu-item" /> -->
        <!-- <error-log class="errLog-container right-menu-item hover-effect" /> -->
        <!-- <screenfull id="screenfull" class="right-menu-item hover-effect" /> -->
        <!-- <el-tooltip content="Global Size" effect="dark" placement="bottom">
          <size-select id="size-select" class="right-menu-item hover-effect" />
        </el-tooltip> -->
      </template>

      <el-dropdown
        class="avatar-container right-menu-item hover-effect"
        trigger="click"
      >
        <div class="avatar-wrapper">
          <img :src="avatar + '?imageView2/1/w/80/h/80'" class="user-avatar" />
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item divided @click.native="logout">
            <span style="display: block">退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Breadcrumb from "@/components/Breadcrumb";
import Hamburger from "@/components/Hamburger";
import ErrorLog from "@/components/ErrorLog";
import Screenfull from "@/components/Screenfull";
import SizeSelect from "@/components/SizeSelect";
import Search from "@/components/HeaderSearch";
// 顶部导航栏依赖
import Logo from "@/layout/components/Sidebar/Logo";
import SidebarItem from "@/layout/components/Sidebar/SidebarItem";
import variables from "@/styles/variables.scss";

export default {
  created () {
    this.nowTimes();
  },
  data () {
    return {
      nowTime: "",
    };
  },
  components: {
    Breadcrumb,
    Hamburger,
    ErrorLog,
    Screenfull,
    SizeSelect,
    Search,
    SidebarItem,
    Logo,
  },
  computed: {
    ...mapGetters([
      "sidebar",
      "avatar",
      "device",
      "permission_routes",
      "sidebar",
    ]),
    activeMenu () {
      const route = this.$route;
      const { meta, path } = route;
      // if set path, the sidebar will highlight the path you set
      if (meta.activeMenu) {
        return meta.activeMenu;
      }
      return path;
    },
    showLogo () {
      return this.$store.state.settings.sidebarLogo;
    },
    variables () {
      return variables;
    },
  },
  methods: {
    toggleSideBar () {
      this.$store.dispatch("app/toggleSideBar");
    },
    async logout () {
      await this.$store.dispatch("user/logout");
      this.$router.push(`/login?redirect=${this.$route.fullPath}`);
    },
    timeFormate (timeStamp) {
      let year = new Date(timeStamp).getFullYear();
      let month =
        new Date(timeStamp).getMonth() + 1 < 10
          ? "0" + (new Date(timeStamp).getMonth() + 1)
          : new Date(timeStamp).getMonth() + 1;
      let date =
        new Date(timeStamp).getDate() < 10
          ? "0" + new Date(timeStamp).getDate()
          : new Date(timeStamp).getDate();
      let hh =
        new Date(timeStamp).getHours() < 10
          ? "0" + new Date(timeStamp).getHours()
          : new Date(timeStamp).getHours();
      let mm =
        new Date(timeStamp).getMinutes() < 10
          ? "0" + new Date(timeStamp).getMinutes()
          : new Date(timeStamp).getMinutes();
      let ss =
        new Date(timeStamp).getSeconds() < 10
          ? "0" + new Date(timeStamp).getSeconds()
          : new Date(timeStamp).getSeconds();
      this.nowTime =
        year +
        "年" +
        month +
        "月" +
        date +
        "日" +
        " " +
        hh +
        ":" +
        mm +
        ":" +
        ss;
    },
    nowTimes () {
      this.timeFormate(new Date());
      setInterval(this.nowTimes, 1000);
      this.clear();
    },
    clear () {
      clearInterval(this.nowTimes);
      this.nowTimes = null;
    },
  },
};
</script>
<style  scoped>
.el-icon-caret-bottom {
  display: none;
}
</style>
<style lang="scss" scoped>
.el-dropdown {
  margin: 0 !important;
}
.el-dropdown-menu__item {
  border-top-width: 0px;
  margin-top: 0px;
}
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }
  .now-time {
    font-size: 15px;
    float: left;
    display: inline-block;
  }
  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background 0.3s;

        &:hover {
          background: rgba(0, 0, 0, 0.025);
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
