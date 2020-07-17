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
          document.getElementById(id).scrollIntoView(true);
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
  .bookmark-item{
    padding-left: 5px!important;
    padding-right: 5px!important;
    height: 20px!important;
    line-height: 20px!important;
    text-align: left!important;
  }
  .bookmark-item:hover{
    background-color: rgba(0,0,0,0.1);
  }
  .title-index{
    color: #2c3e50;
    font-style:normal;
    font-weight: lighter;
    font-size: 14px;
    text-decoration: none;
  }
  .title-h1 {
    padding-left: 0px;
  }
  .title-h2 {
    padding-left: 10px;
  }
  .title-h3 {
    padding-left: 20px;
  }
  .title-h4 {
    padding-left: 30px;
  }
  .title-h5 {
    padding-left: 40px;
  }
  .title-h6 {
    padding-left: 50px;
  }
</style>
