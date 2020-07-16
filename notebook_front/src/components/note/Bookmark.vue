<template>
  <div style="margin-top: 100px;margin-left: 10px">
    <el-scrollbar style="height:100%">
      <el-menu class="el-menu-vertical-demo bookmark-area">
          <el-menu-item v-for="(item,i) in titles" :key="i" class="bookmark-item" @click="goTo(item.href)">
            <a :class="'title-index title-h'+item.type"  >{{item.name}}</a>
<!--            <span slot="title" style="vertical-align: center"></span>-->
          </el-menu-item>
        </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
    export default {
      name: "Bookmark",
      data(){
        return{
          titles:[]
        }
      },
      methods: {
        goTo(id){
          // console.log(id);
          // console.log(document.getElementById(id));
          document.getElementById(id).scrollIntoView(true)
        }
      }
    }
</script>

<style scoped>
  body .el-scrollbar__wrap {
    overflow-x: hidden;
  }
  body.el-scrollbar{
    overflow-x: hidden!important;
  }
</style>
