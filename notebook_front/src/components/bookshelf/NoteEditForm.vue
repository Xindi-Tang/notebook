<template>
    <div>
      <el-dialog :title="noteDialogTitle" :visible.sync="dialogFormVisible" :before-close="handleClose">
        <el-form :model="form">
          <el-form-item label="笔记名称" :label-width="formLabelWidth">
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="笔记简介" :label-width="formLabelWidth">
            <el-input type="textarea":rows="3" v-model="form.abs" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible=false">取 消</el-button>
          <el-button type="primary" @click="updateNote">确 定</el-button>
        </div>
      </el-dialog>
    </div>
</template>

<script>
    export default {
      name: "NoteEditForm",
      data(){
        return {
          noteDialogTitle: '',
          isCreate: false,
          dialogFormVisible: false,
          formLabelWidth: '120px',
          form:{
            id:'',
            name:'',
            abs:'',
          },
          cid : '',
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
        updateNote(){
          var _this = this;
          var url;
          if(!_this.isCreate){
            url='/update/note/'+this.form.id.toString()+'/info';
          }
          else{
            url = '/update/category/'+this.cid.toString()+'/note/add';
          }
          console.log(url)
          this.axios.post(url,this.form)
            .then(function (response) {
              console.log(response)
              if(response.data.status === 200){
                _this.dialogFormVisible = false;
                _this.$message({
                  message: '更新成功',
                  type: 'success'
                });
                _this.$emit('updateInfo')
              }
              else {
                _this.$message.error('更新失败');
              }
            })
            .catch(function (error) {
              _this.$message.error(error);
            })
        },
      },
    }
</script>

<style scoped>

</style>
