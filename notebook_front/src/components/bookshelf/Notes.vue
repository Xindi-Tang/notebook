<template>
  <div>
    <el-row :gutter="10">
      <el-col :xl="6" :lg="6" :sm="24" v-for="(item,i) in notes" :key="i">
        <el-card shadow="hover" class="box-card">
          <div slot="header" class="clearfix">
            <span class="note-title">{{item.name}}</span>
            <el-tooltip class="button-tip" transition="0s" effect="dark" content="删除此笔记" placement="top-start">
              <el-button class="note-operation" type="text" icon="el-icon-delete" @click="deleteNote(item.id)"></el-button>
            </el-tooltip>
            <el-tooltip class="button-tip" transition="0s" effect="dark" content="编辑笔记内容" placement="top-start">
              <el-button class="note-operation" type="text" icon="el-icon-edit"></el-button>
            </el-tooltip>
            <el-tooltip class="button-tip" transition="0s" effect="dark" content="查看笔记内容" placement="top-start">
              <el-button class="note-operation" type="text" icon="el-icon-more"></el-button>
            </el-tooltip>
            <el-tooltip class="button-tip" transition="0s" effect="dark" content="编辑笔记标题与简介" placement="top-start">
              <el-button class="note-operation" type="text" icon="el-icon-edit-outline" @click="editInfo(item)"></el-button>
            </el-tooltip>
          </div>
          <div class="text item note-abs">
            {{restrict(item.abs)}}
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row style="position: fixed;bottom: 10px;right: 10px">
      <el-tooltip class="button-tip" transition="0s" effect="dark" content="编辑笔记分类" placement="top-start">
        <el-button type="success" circle @click="editCategory">
        <i class="el-icon-edit"></i>
      </el-button>
      </el-tooltip>
    </el-row>
    <el-row style="position: fixed;bottom: 70px;right: 10px">
      <el-tooltip class="button-tip" transition="0s" effect="dark" content="新增笔记" placement="top-start">
        <el-button type="primary" circle @click="addNote">
          <i class="el-icon-document-add"></i>
        </el-button>
      </el-tooltip>
    </el-row>
  </div>

</template>

<script>
    export default {
      name: "Notes",
      data(){
        return{
          notes:[]
        }
      },
      methods:
        {
          restrict(abs){
            if(abs === "" || abs == null){
              return "";
            }
            if(abs.length<10){
              return abs;
            }
            else {
              return abs.substring(0,10) + '...';
            }
          },
          editInfo(note){
            var noteInfo={
              id:note.id,
              name:note.name,
              abs:note.abs,
            }
            this.$emit('editInfo',noteInfo)
          },
          deleteNote(id){
            var _this=this;
            _this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              _this.axios.get('/note/delete/'+id.toString())
              .then(function (response) {
                if(response.data.status === 200){
                  _this.$message({
                    type: 'success',
                    message: '删除成功!'
                  });
                  _this.$emit('updateInfo');
                }
              })
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消删除'
              });
            });
          },
          addNote(){
            this.$emit('addNote')
          },
          editCategory(){
            this.$emit('editCategory')
          },

        }
    }
</script>

<style scoped>
  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }
  .note-operation{
    float: right;
    padding: 3px 0;
    margin-right: 2px;
    margin-left: 2px!important;
  }
  .box-card>>>.el-card__header {
    padding: 9px 20px!important;
    height: 40px!important;
  }
  .box-card{
    display: inline-block;
    width: 95%;
    margin-left: 5px;
    margin-right: 5px;
  }
  .note-title{
    font-weight: bolder;
  }
  .note-abs{
    font-size: 12px;
    height: 50px;
    cursor: pointer;
  }
</style>
