<template>
    <div>
      <el-col :span="24">
        <el-card class="note" style="width: 100%;text-align: center" shadow="never" v-model="note">
          <div slot="header" class="clearfix">
            <span>{{ note.name }}</span>
            <el-button style="float: right; padding: 3px 0" type="text"><i class="el-icon-more" @click="read(note.id)"></i></el-button>
          </div>
            <mavon-editor
              v-model="note.contentMd"
              @save="saveNote">
              <button
                type="button"
                class="op-icon el-icon-s-management"
                :title="'链接笔记'"
                slot="left-toolbar-after"
                @click="getNoteList"
              ></button>
            </mavon-editor>
        </el-card>
      </el-col>
      <el-dialog
        :visible.sync="dialogFormVisible"
        width="40%"
        center
        :title="'设置链接内容'">
        <el-row>
          <el-form>
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
        </el-row>
      </el-dialog>
    </div>
</template>

<script>
    export default {
      name: "NoteEdit",
      data(){
          return{
            note:{
              // contentMd:''
            },
            noteLink: {
              noteIndex:'',
              titleID:'',
              link:''
            },
            dialogFormVisible:false,
            noteList:[],
            titleList:[]
          }
      },
      mounted() {
        if(this.$route.query.noteId){
          this.loadNote(this.$route.query.noteId);
          // console.log(this.$route.query.noteId)
        }
      },
      methods:{
        loadNote(id){
          var _this=this;
          this.axios.get("note/"+id.toString())
          .then(function (response) {
            if(response.data.status===200){
              _this.note=response.data.object;
            }
            console.log(response.data.object)
          })
          .catch(function (error){
            console.log(error)
          })
        },
        saveNote(value,render){
          var url='/update/content/note/'+this.note.id;
          var _this=this;
          this.note.contentMd=value;
          this.note.contentHtml=render;
          this.axios.post(url,this.note)
          .then(function (response) {
            if(response.data.status === 200){
              _this.$message({
                type:'success',
                message:'保存成功'
              })
            }
          })
          .catch(function (error) {
            console.log(error)

          })
        },
        read(id){
          this.$router.push({
            path:'/note/detail',
            query:{
              noteId:id
            }
          })
        },
        getNoteList(){
          var _this = this
          this.axios.get('/notes')
            .then(function (response) {
              if(response.status === 200){
                _this.noteList = response.data
                _this.dialogFormVisible = true
                console.log(_this.noteList)
              }
            })
            .catch(function (error) {
              console.log(error)
            })
        },
        getTitleList(){
          this.titleList = [];
          var content = this.noteList[this.noteLink.noteIndex].contentHtml
          let div = document.createElement("div")
          div.innerHTML = content
          let doc = div.children
          for(var i=0;i<doc.length;i++){
            if(doc[i].nodeName.indexOf("H") !== -1){
              this.titleList.push({
                id:doc[i].children[0].getAttribute("id"),
                name:doc[i].innerText,
              })
            }
          }
        },
        generateLink(){
          this.noteLink.link = ''
          var prefix = "notelink://"
          this.noteLink.link = prefix+(this.noteList[this.noteLink.noteIndex].id)+"&"+this.noteLink.titleID
        },

      }
    }
</script>

<style scoped>

</style>
