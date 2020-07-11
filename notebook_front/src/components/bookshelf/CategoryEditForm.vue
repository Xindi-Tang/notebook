<template>
  <div>
    <el-dialog title="编辑笔记分类" :visible.sync="dialogFormVisible" :before-close="handleClose">
      <el-form :model="form">
        <el-form-item label="分类名" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="">取 消</el-button>
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
              _this.updateNote();
            })
            .catch(_ => {});
        },
        updateCategory(){}
      }
    }
</script>

<style scoped>

</style>
