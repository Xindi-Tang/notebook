<template>
  <el-menu
    :default-active="$route.path"
    class="navi-box"
    mode="horizontal"
    @select="handleSelect"
    background-color="#4169E1"
    text-color="#ffffff"
    active-text-color="#ffd04b"
    router
  >
    <el-menu-item v-for="(item,i) in naviList" :index="item.url":key="i">
      {{item.name}}
    </el-menu-item>

    <li class="full-screen">
      <i class="el-icon-full-screen" style="height: 35px;line-height: 35px;" @click="fullScreen"></i>
    </li>

    <el-submenu index="2" style="float: right">
      <template slot="title">{{userFlag.name}}</template>

      <el-menu-item v-for="(item,i) in userFlag.menuList" :index="item.url" :key="i">{{item.name}}</el-menu-item>
      <el-menu-item :style="{display:isLogin}"
                    @click="logout">注销</el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<script>
  export default {
    data() {
      return {
        isLogin:'none',
        userFlag: {
          name: '',
          menuList: []
        },
        naviList:[
          {name:'首页',url:'/home'},
          {name:'书架',url:'/bookshelf'},
        ]
      };
    },

    mounted() {
      if (window.localStorage.getItem("user") != null) {
        this.userFlag.name = JSON.parse(window.localStorage.getItem("user")).username
        this.userFlag.menuList = [
          {url: '/home', name: '用户中心'},
          {url: '/bookshelf', name: '笔记管理'},
        ]
        this.isLogin = 'inline'
      } else {
        this.userFlag.name = "未登录"
        this.userFlag.menuList = [
          {url: '/register', name: '注册'},
          {url: '/login', name: '登录'},
        ]
        this.isLogin = 'none'
      }
    },
    methods: {
      handleSelect(key, keyPath) {
        // console.log(key, keyPath);
      },
      logout(){
        var _this = this
        this.axios.get('/logout')
          .then(function (response) {
            if(response.data.status === 200){
              _this.$store.commit('logout')
              _this.$router.replace('/login')
            }
          })
          .catch(function (error) {
            console.log(error)
          })
      },
      fullScreen(ev) {
        const isFull=!!(document.webkitIsFullScreen || document.mozFullScreen ||
          document.msFullscreenElement || document.fullscreenElement
        )
        if(!isFull){
          var element = document.documentElement;
          if (element.requestFullscreen) {
            element.requestFullscreen()
          } else if (element.msRequestFullscreen) {
            element.msRequestFullscreen()
          } else if (element.mozRequestFullScreen) {
            element.mozRequestFullScreen()
          } else if (element.webkitRequestFullscreen) {
            element.webkitRequestFullscreen()
          }
        }
        else{
          if (document.exitFullscreen) {
            document.exitFullscreen();
          } else if (document.msExitFullscreen) {
            document.msExitFullscreen();
          } else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen();
          } else if (document.webkitExitFullscreen) {
            document.webkitExitFullscreen();
          }
        }
      }
    }
  }
</script>

<style scoped>
.navi-box{
  width: 100%;
  left: 0px;
  top: 0px;
}
.el-menu-item{
  font-weight: bolder!important;
}
.el-menu--horizontal>>> .el-menu-item {
  height: 35px!important;
  line-height: 35px!important;
}
.el-menu--horizontal>>> .el-submenu .el-submenu__title {
  height: 35px!important;
  line-height: 35px!important;
}
  .full-screen{
    float: right;
    outline: none;
    cursor: pointer;
    padding: 0 10px 0 0;
  }
</style>
