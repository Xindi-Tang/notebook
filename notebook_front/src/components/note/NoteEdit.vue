<template>
    <div>
      <mavon-editor v-model="note.contentMd"></mavon-editor>
    </div>
</template>

<script>
    export default {
      name: "NoteEdit",
      data(){
          return{
            note:{
              contentMd:''
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
