<template>
    <div>
      <el-card class="box-card note" shadow="never" v-model="note">
        <div slot="header" class="clearfix">
          <span>{{note.name}}</span>
          <el-button style="float: right; padding: 3px 0" type="text">
            <i class="el-icon-edit"></i>
          </el-button>
          <el-button style="float: right; padding: 3px 0;margin-right: 5px" type="text">
            <i class="el-icon-tickets"></i>
          </el-button>
        </div>
        <el-scrollbar style="height: 100%">
          <div v-html="note.contentHtml" class="text note-html markdown-body"></div>
        </el-scrollbar>
      </el-card>
    </div>
</template>

<script>
    export default {
        name: "NoteDetail",
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
        }
      }
    }
</script>

<style scoped>

</style>
