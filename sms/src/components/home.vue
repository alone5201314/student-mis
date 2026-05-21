<template>
  <div class="home">
    <el-container>
      <el-header class="headColor">
        <Header @click_collapse="clickCollapse"></Header>
      </el-header>
      <el-container class="header">
        <el-aside class="asideWidth">
          <Aside @collapse="isCollapse" ref="aside"></Aside>
        </el-aside>
        <el-main class="mainContent">
          <Tabs></Tabs>
          <!--内容区-->
          <div class="pageContent">
            <router-view />
          </div>
          <div class="copyright">
            Copyrght@2026 苏振豪
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Tabs from '../common/tabs'
import Aside from '../common/aside'
import Header from '../common/header'

export default {
    name: "home",
    data () {
      return {
        width: '240px',
        collapse: false
      }
    },
    methods: {
      clickCollapse () {
        this.$refs.aside.isCollapse();
      },
      isCollapse (collapse) {
        this.collapse = collapse
      },
    },
    mounted() {
      // 初始化导航栏宽度
      let collapseWidth = document.getElementsByClassName("asideWidth")[0];
      collapseWidth.style.width = '240px';
      let collapseInfo = {
        state: this.collapse,
        width: collapseWidth.style.width
      };
      this.$store.commit('SAVE_COLLAPSE', collapseInfo);
    },
    components: {
        Aside, Header, Tabs
    },
    watch: {
      collapse () {
        this.width = '240px';
        let collapseWidth = document.getElementsByClassName("asideWidth")[0];
        // 点击导航栏收缩
        if(!this.collapse) {
          collapseWidth.style.width = '240px';
        } else {
          collapseWidth.style.width = '58px'
        }
        let collapseInfo = {
          state: this.collapse,
          width: collapseWidth.style.width
        };
        this.width = collapseWidth.style.width;
        // 把collapse信息传入vuex
        this.$store.commit('SAVE_COLLAPSE', collapseInfo)
      }
    }
}
</script>

<style>
  html, body, #app, .home, .el-container {
    margin: 0 !important;
    padding: 0 !important;
    height: 100% !important;
  }
</style>
<style scoped>
  .headColor {
    height: 70px;
    background-color: #5a8bff;
    padding: 0;
    margin: 0;
  }
  .asideWidth {
    /*background-image: linear-gradient(rgba(90,139,255,1), white);*/
    border-right: 1px solid #e6e6e6;
    width: 240px;
    min-width: 58px;
    transition: 0.2s;
    height: 100%
  }
  .header {
    height: calc(100vh - 70px);
    min-height: calc(100vh - 70px);
    min-width: 960px;
  }
  .mainContent {
    background-color: #ededed;
    padding: 0;
    height: calc(100vh - 70px);
    overflow-y: auto;
    overflow-x: auto;
  }
  .pageContent {
    min-height: calc(100vh - 140px);
    padding-bottom: 8px;
    box-sizing: border-box;
    min-width: 900px;
  }
  ::v-deep .el-card__body {
    padding: 0;
  }
  .copyright {
    position: static;
    height: 24px;
    line-height: 20px;
    text-align: center;
    width: 100%;
    font-size: 12px;
    color: #666;
  }

  @media (max-width: 1366px), (max-height: 760px) {
    .headColor {
      height: 60px;
    }
    .header,
    .mainContent {
      height: calc(100vh - 60px);
      min-height: calc(100vh - 60px);
    }
    .pageContent {
      min-height: calc(100vh - 126px);
    }
  }
</style>
