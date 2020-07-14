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
        @save="saveNote"
      >
      </mavon-editor>
        </el-card>
      </el-col>
    </div>
</template>

<script>
    export default {
      name: "NoteEdit",
      data(){
          return{
            note:{
              // contentMd:''
            }
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
        }
      }
    }
</script>

<style scoped>

</style>
