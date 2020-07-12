<template>
  <div>
    <el-dialog :title="categoryDialogTitle" :visible.sync="dialogFormVisible" :before-close="handleClose">
      <el-form :model="form">
        <el-form-item label="分类名" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelEditingCategory">取 消</el-button>
        <el-button type="primary" @click="updateCategory">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    export default {
      name: "CategoryEditForm",
      data(){
        return {
          categoryDialogTitle: '',
          isCreate: false,
          dialogFormVisible: false,
          formLabelWidth: '120px',
          form:{
            id:'',
            name:'',
          },
        }
      },
      methods:{
        handleClose(done) {
          var _this=this;
          this.$confirm('保存修改并关闭？')
            .then(_ => {
              _this.updateCategory();
            })
            .catch(_ => {});
        },
        updateCategory(){
          var _this = this
          if (_this.isCreate) {
            this.axios.post('category/add', _this.form)
              .then(function (response) {
                if(response.data.status === 200){
                  _this.dialogFormVisible = false
                  _this.$message({
                    type: 'success',
                    message: '新增成功!'
                  });
                  _this.form = {
                    id:'',
                    name:''
                  }
                  _this.$emit('updateCategoryBar')
                }
              })
              .catch(function (error) {
                console.log(error)
              })
          }
          else{
            this.axios.post('category/update', _this.form)
              .then(function (response) {
                if(response.data.status === 200){
                  _this.dialogFormVisible = false
                  _this.$message({
                    type: 'success',
                    message: '更新成功!'
                  });
                  _this.form = {
                    id:'',
                    name:''
                  }
                  _this.$emit('update')
                }
              })
              .catch(function (error) {
                console.log(error)
              })
          }
        },
        cancelEditingCategory(){
          this.dialogFormVisible=false;
          this.form = {
            id:'',
            name:''
          }
          this.$emit('updateCategoryBar')
        }

      }
    }
</script>

<style scoped>

</style>
