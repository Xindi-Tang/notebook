<template>
    <div>
      <el-col :span="24">
        <el-card class="note" style="width: 100%;text-align: center" shadow="never" v-model="note">
          <div slot="header" class="clearfix">
            <span>{{ note.name }}</span>
            <el-button style="float: right; padding: 3px 0" type="text"><i class="el-icon-more" @click="read(note.id)"></i></el-button>
          </div>
            <mavon-editor
              ref="md"
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
      <quote-note-form ref="quoteNote" @addLink="addLink"></quote-note-form>
    </div>
</template>

<script>
  import QuoteNoteForm from "./QuoteNoteForm";
    export default {
      name: "NoteEdit",
      components:{QuoteNoteForm},
      data(){
          return{
            note:{
              // contentMd:''
            },
            noteLink: {
              input:'',
              noteIndex:'',
              titleID:'',
              link:''
            },
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
          var _this = this;
          this.axios.get('/notes')
            .then(function (response) {
              if(response.status === 200){
                _this.$refs.quoteNote.noteList = response.data;
                _this.$refs.quoteNote.dialogFormVisible = true;
                console.log(_this.noteList);
              }
            })
            .catch(function (error) {
              console.log(error);
            })
        },
        // getTitleList(){
        //   this.titleList = []; //eliminate the content of last undetermined choice
        //   this.noteLink.titleID='';
        //   this.noteLink.link='';
        //   var content = this.noteList[this.noteLink.noteIndex].contentHtml;
        //   let div = document.createElement("div");
        //   div.innerHTML = content;
        //   let doc = div.children;
        //   for(var i=0;i<doc.length;i++){
        //     if(doc[i].nodeName.indexOf("H") !== -1){
        //       this.titleList.push({
        //         id:doc[i].children[0].getAttribute("id"),
        //         name:doc[i].innerText,
        //       });
        //     }
        //   }
        // },
        // generateLink(){
        //   this.noteLink.link = '';
        //   var prefix = "notelink://";
        //   this.noteLink.link = prefix+(this.noteList[this.noteLink.noteIndex].id)+"&"+this.noteLink.titleID;
        // },
        // closeForm(){
        //   this.dialogFormVisible=false;
        //   this.noteLink={};
        // },
        addLink(type,title,link){
          console.log(type,title,link);
          this.$refs.md.toolbar_left_addlink(type,title,link);
          this.$refs.quoteNote.closeForm('noteEdit');
        }

      }
    }
</script>

<style scoped>

</style>
