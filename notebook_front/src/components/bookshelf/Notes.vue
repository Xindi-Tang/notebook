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
            <router-link :to="{path:'/note/edit',query:{noteId:item.id}}">
              <el-tooltip class="button-tip" transition="0s" effect="dark" content="编辑笔记内容" placement="top-start">
                <el-button class="note-operation" type="text" icon="el-icon-edit"></el-button>
              </el-tooltip>
            </router-link>
            <el-tooltip class="button-tip" transition="0s" effect="dark" content="查看笔记内容" placement="top-start">
                <el-button class="note-operation" type="text" icon="el-icon-more" @click="readNote(item.id)"></el-button>
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
    <el-row class="page">
      <el-pagination @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"
                     :current-page="currentPage"
                     :page-sizes="pageSizes"
                     :page-size="PageSize" layout="total, sizes, prev, pager, next, jumper"
                     :total="totalCount">
      </el-pagination>
    </el-row>
  </div>

</template>

<script>
    import NoteEdit from "../note/NoteEdit";

    export default {
      name: "Notes",
      data(){
        return{
          notes:[],
          // 默认显示第几页
          currentPage:1,
          // 总条数，根据接口获取数据长度(注意：这里不能为空)
          totalCount:1,
          // 个数选择器（可修改）
          pageSizes:[1,2,3,4],
          // 默认每页显示的条数（可修改）
          PageSize:2,
        }
      },
      created:function(){
        this.getData(this.PageSize,this.currentPage)
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
          readNote(id){
            this.$router.push({
              path:'/note/detail',
              // name:'NoteEdit',
              query:{
                noteId:id
              }
            })
          },
          getData(n1,n2){
            var _this=this;
            //pagesize没用上呢 url: pagesize=?
            _this.axios.get('categories/2/notes/'+n2.toString())
              .then(function (response) {
                if(response.status === 200){
                  console.log(response)
                  _this.notes=response.data
                  // 将数据的长度赋值给totalCount
                  _this.totalCount=response.data.length
                }
              })
              .catch(function (error) {
                console.log(error)
              })

            // axios.post(url,{
            //     orgCode:1,
            //     // 每页显示的条数
            //     PageSize:n1,
            //     // 显示第几页
            //     currentPage:n2,
            //   },{emulateJSON: true},
            //   {
            //     headers:{"Content-Type": "application/x-www-form-urlencoded;charset=utf-8",}
            //   }
            // ).then(reponse=>{
            //   console.log(reponse)
            //   // 将数据赋值给tableData
            //   this.tableData=response.data
            //   // 将数据的长度赋值给totalCount
            //   this.totalCount=data.data.body.length
            // })
          },
          // 分页
          // 每页显示的条数
          handleSizeChange(val) {
            // 改变每页显示的条数
            this.PageSize=val
            // 点击每页显示的条数时，显示第一页
            this.getData(val,1)
            // 注意：在改变每页显示的条数时，要将页码显示到第一页
            this.currentPage=1
          },
          // 显示第几页
          handleCurrentChange(val) {
            // 改变默认的页数
            console.log(val,(val)*(this.pageSize))
            this.currentPage=val
            // 切换页码时，要获取每页显示的条数
            this.getData(this.PageSize,(val)*(this.PageSize))
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
  .page{
    text-align: center;
    position:fixed;
    margin:auto;
    left:0;
    right:0;
    bottom:0;
    width:200px;
    height:50px;
  }
</style>
