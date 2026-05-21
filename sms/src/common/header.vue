<template>
  <div class="topHeader">
    <img class="logo" src="../assets/header.png" alt="" width="40" height="40" />
    <i class="el-icon-menu collapse" @click="clickCollapse"></i>
    <div class="misName">瀚海大学学生成绩管理系统</div>
    <el-switch v-model="silent"
               v-if="userInfo.level === 0"
               @change="switchChange"
               :active-color="'#67c23a'"
               :inactive-color="'#ccc'"
               inactive-text="默哀模式"
               class="silentSwitch"></el-switch>
    <div class="userArea">
      <!--日期-->
      <div class="font" v-html="day"></div>
      <!--头像-->
      <div class="headerStr">
        {{firstStr}}
      </div>
      <!--用户名-->
      <div class="userName">
        <el-dropdown trigger="click">
          <span class="el-dropdown-link">
            {{userInfo.realName}}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item icon="el-icon-setting" @click.native="editPassword"> 修改密码</el-dropdown-item>
            <el-dropdown-item icon="el-icon-switch-button" @click.native="exit"> 退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <password ref="edit_password"></password>
  </div>
</template>

<script>
  import password from './password/password'
    export default {
        name: "Header",
        data() {
            return {
                userInfo: {},
                day: '',
                firstStr: '',
            }
        },
        methods: {
          clickCollapse () {
            this.$emit('click_collapse')
          },
          exit() {
            this.$confirm('是否注销当前用户?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.$router.push("/login");
              // 退出登录时，清除身份信息
              localStorage.clear();
            })
          },
          editPassword () {
            this.$refs.edit_password.init();
          },
          switchChange (data) {
            const value = data ? 1: 0;
            this.axiosHelper.put('/api/sms/user/setSilent/' + value).then(response => {
              this.silent = response.data;
            });
          }
        },
        created() {
            let today = new Date();
            let weekday = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
            let date = today.getFullYear() + '年 ' + (today.getMonth() + 1) + '月 ' + today.getDate() + '日';
            let week = weekday[today.getDay()];
            this.day = date + "&#x3000;" + week;
        },
        computed: {
          silent: {
            set (newValue) {
              this.$store.commit('SAVE_SILENT', newValue);
            },
            get () {
              return this.$store.state.silent;
            }
          }
        },
        mounted() {
          this.userInfo = JSON.parse(localStorage.userInfo);
          this.firstStr = this.userInfo.realName.substr(0, 1);
        },
        components: {
          password
        }
    }
</script>

<style scoped>
  .topHeader {
    height: 70px;
    color: white;
    display: flex;
    align-items: center;
    min-width: 960px;
    box-sizing: border-box;
    padding: 0 16px 0 36px;
    overflow: hidden;
  }
  .logo {
    flex: 0 0 auto;
    margin-right: clamp(20px, 5vw, 90px);
  }
  /* 旋转的关闭按钮*/
  .collapse {
    font-size: 25px;
    flex: 0 0 auto;
    line-height: 1;
    margin-right: 20px;
    transition: 0.2s;
    transform: rotate(-180deg);
    cursor: pointer;
  }
  /* 旋转的关闭按钮*/
  .collapse:hover{
    transition: 0.2s;
    transform: rotate(180deg)
  }
  .font {
    font-size: 18px;
    margin-right: clamp(16px, 4vw, 80px);
    line-height: 60px;
    white-space: nowrap;
  }
  .image {
    border-radius: 50%;
    vertical-align: top;
    margin-top: 5px
  }
  .misName {
    flex: 0 1 auto;
    min-width: 0;
    margin-right: 20px;
    font-size: 24px;
    line-height: 60px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  .username {
    margin-right: 10px;
    line-height: 62px;
    float: right;
  }
  .el-dropdown-link {
    font-size: 17px;
    cursor: pointer;
    color: white;
    white-space: nowrap;
  }
  .el-icon-arrow-down {
    font-size: 17px;
  }
  .headerStr {
    flex: 0 0 auto;
    margin-right: 12px;
    border: 3px solid white;
    border-radius: 50px;
    width: 45px;
    height: 45px;
    line-height: 45px;
    text-align: center;
    font-size: 35px;
    color: black;
    font-family: cursive;
    margin-top: 4px;
    background-color: white;
  }
  .silentSwitch {
    flex: 0 0 auto;
    margin-right: 20px;
    color: white;
  }
  .userArea {
    margin-left: auto;
    display: flex;
    align-items: center;
    min-width: 0;
  }
  .userName {
    color: white;
    min-width: 0;
  }
  /*打开默哀模式时*/
  /deep/ .el-switch__label.is-active {
   /*filter:progid:DXImageTransform.Microsoft. BasicImage(grayscale=1); -webkit-filter: grayscale(100%);*/
    color: #fff !important;
  }
  /*关闭默哀模式时*/
  /deep/ .el-switch__label--left {
    color: white !important;
  }

  @media (max-width: 1366px), (max-height: 760px) {
    .topHeader {
      height: 60px;
      padding-left: 24px;
    }
    .logo {
      width: 36px;
      height: 36px;
      margin-right: 28px;
    }
    .misName {
      font-size: 20px;
      margin-right: 12px;
    }
    .font {
      display: none;
    }
    .headerStr {
      width: 38px;
      height: 38px;
      line-height: 38px;
      font-size: 28px;
      margin-top: 0;
    }
  }

  @media (max-width: 1120px) {
    .silentSwitch {
      display: none;
    }
  }
</style>
