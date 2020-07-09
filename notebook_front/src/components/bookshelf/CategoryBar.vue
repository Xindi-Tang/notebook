<template>
  <div>
    <el-tabs v-model="currentCid" @tab-click="handleClick" class="categories-tabs">
      <el-tab-pane v-for="(item,i) in categories" :label="item.name" :key="i" :name="item.id.toString()"></el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
    export default {
      name: "CategoryBar",
      data(){
        return{
          currentCid:'',
          categories:[]
        }
      },
      // created() {
      //
      //   this.currentCid = categories[0].id;
      // },
      // mounted(){
      //   var _this=this;
      //   this.axios.get("/categories")
      //     .then(function (response){
      //       if(response.status===200){
      //         _this.categories=response.data
      //         _this.currentCid = _this.categories[0].id.toString();
      //         _this.$emit('categoryDefault')
      //         console.log(response)
      //       }
      //       console.log(response)
      //     })
      // },
      mounted(){
        var _this=this;
        this.axios.get("/categories")
          .then(function (response){
            if(response.status===200){
                      _this.categories=response.data
                      _this.currentCid = _this.categories[0].id.toString();
                      _this.$emit('categoryDefault')
                      console.log(response)
                    }
          })
      },
      methods: {
        handleClick(tab, event) {
          this.$emit('categorySelect')
        }
      }
    }
</script>

<style scoped>
  .categories-tabs >>> .el-tabs__item {
    padding: 0 10px !important;
    height: 30px !important;
    line-height: 20px !important;
    font-size: 13px;
  }
</style>
