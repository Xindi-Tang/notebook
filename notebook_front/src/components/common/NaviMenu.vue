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
  </el-menu>
</template>

<script>
  export default {
    data() {
      return {
        naviList:[
          {name:'首页',url:'/home'},
          {name:'书架',url:'/bookshelf'},
        ]
      };
    },
    methods: {
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
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
</style>
