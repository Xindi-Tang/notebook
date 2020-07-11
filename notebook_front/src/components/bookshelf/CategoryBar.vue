<template>
  <div>
    <el-tabs type="card" v-model="currentCid" @tab-click="handleClick" class="categories-tabs" editable @edit="editCategory">
      <el-tab-pane v-for="(item,i) in categories" :label="item.name" :key="i" :name="item.id.toString()"></el-tab-pane>
    </el-tabs>
    <category-edit-form ref="editForm" @update="update"></category-edit-form>
  </div>
</template>

<script>
  import CategoryEditForm from "./CategoryEditForm";
    export default {
      name: "CategoryBar",
      components:{CategoryEditForm},
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
        this.update()
      },
      methods: {
        update(){
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
        handleClick(tab, event) {
          this.$emit('categorySelect')
        },
        editCategory(targetName,action) {
          if (action === 'remove') {
            var _this = this
            this.$confirm('是否永久删除该笔记分类?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              _this.axios.get('category/delete/'+targetName.toString())
                .then(function (response) {
                  if(response.data.status === 200){
                    _this.$message({
                      type: 'warning',
                      message: '删除成功!'
                    });
                    _this.update();
                    // let tabs = _this.categories;
                    // let activeName = _this.currentCid;
                    // if (activeName === targetName) {
                    //   tabs.forEach((tab, index) => {
                    //     if (tab.id.toString() === targetName) {
                    //       let nextTab = tabs[index + 1] || tabs[index - 1];
                    //       if (nextTab) {
                    //         activeName = nextTab.id.toString();
                    //       }
                    //     }
                    //   });
                    // }
                    // _this.currentCid = activeName;
                    // _this.categories = tabs.filter(tab => tab.id.toString() !== targetName);
                  }
                })
                .catch(function (error) {
                  console.log(error)
                })
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消删除'
              });
            });
          }
          if(action === 'add'){
            this.$refs.editForm.dialogFormVisible = true
            this.$refs.editForm.isCreate = true
          }
        },
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
  .categories-tabs >>> .el-tabs__header {
    margin: 5px 0px 10px 0px;
    height: 30px !important;
    line-height: 20px !important;
    font-size: 13px;
  }
  .categories-tabs >>> .el-tabs__new-tab {
    margin: 5px 0px !important;
  }
</style>
