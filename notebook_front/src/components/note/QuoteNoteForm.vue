<template>
  <el-dialog
    :visible.sync="dialogFormVisible"
    width="40%"
    center
    :title="'设置链接内容'"
    :before-close="handleClose">
    <el-form>
      <el-form-item>
        <el-input placeholder="请输入链接描述" v-model="noteLink.input" clearable>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="noteLink.noteIndex" placeholder="请选择链接笔记" @change="getTitleList" style="width: 100%">
          <el-option v-for="(item,i) in noteList" :label="item.name" :value="i" :key="item.id" ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="noteLink.titleID" placeholder="请选择链接标题" @change="generateLink" style="width: 100%">
          <el-option v-for="item in titleList" :label="item.name" :value="item.id" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="closeForm('')">取 消</el-button>
      <el-button type="primary" @click="addLink">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
    export default {
      name: "QuoteNoteForm",
      data(){
        return{
          noteLink:{
            input:'',
            noteIndex:'',
            titleID:'',
            link:''
          },
          noteList:[],
          titleList:[],
          dialogFormVisible:false
        }
      },
      methods:{
        getTitleList(){
          this.titleList = []; //eliminate the content of last undetermined choice
          this.noteLink.titleID='';
          this.noteLink.link='';
          var content = this.noteList[this.noteLink.noteIndex].contentHtml;
          let div = document.createElement("div");
          div.innerHTML = content;
          let doc = div.children;
          for(var i=0;i<doc.length;i++){
            if(doc[i].nodeName.indexOf("H") !== -1){
              this.titleList.push({
                id:doc[i].children[0].getAttribute("id"),
                name:doc[i].innerText,
              });
            }
          }
        },
        generateLink(){
          this.noteLink.link = '';
          var prefix = "notelink://";
          this.noteLink.link = prefix+(this.noteList[this.noteLink.noteIndex].id)+"&"+this.noteLink.titleID;
        },
        closeForm(from){
          console.log(from);
          if(from==="noteEdit") {
            this.$message({
              message: '添加链接成功',
              type: 'success'
            });
          }
          this.dialogFormVisible = false;
          this.noteLink = {};
        },
        addLink() {
          console.log(this.noteLink.input, this.noteLink.link);
          this.$emit("addLink", "link", this.noteLink.input, this.noteLink.link);
        },
          // this.$refs.md.toolbar_left_addlink('link',this.noteLink.input,this.noteLink.link);
        handleClose(done) {
          var _this=this;
          this.$confirm('确认不保存修改并关闭?')
            .then(_ => {
              this.noteLink={};
              this.$message('未添加链接');
              done();
            })
            .catch(_ => {});
        }
      }
    }
</script>

<style scoped>

</style>
