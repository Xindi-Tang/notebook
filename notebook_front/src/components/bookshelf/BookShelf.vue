<template>
  <div>
    <category-bar @categorySelect="getNotes" @categoryDefault="getNotes" ref="categoryBar"></category-bar>
    <notes ref="notes" @editInfo="handleEditNote" @updateInfo="getNotes" @addNote="handleAddNote" @editCategory="handleEditCategory"></notes>
    <note-edit-form ref="noteEditForm" @updateInfo="getNotes"></note-edit-form>
  </div>
</template>

<script>
  import CategoryBar from "./CategoryBar";
  import Notes from "./Notes";
  import NoteEditForm from "./NoteEditForm";
    export default {
      name: "BookShelf",
      components:{Notes, CategoryBar, NoteEditForm},
      data(){
        return{

        }
      },
      methods:{
        getNotes(){
          var currentCid=this.$refs.categoryBar.currentCid
          var _this=this;
          // this.axios.get("/categories/"+currentCid+'/notes')
          // .then(function(response){
          //   if(response.status===200){
          //     _this.$refs.notes.notes=response.data
          //     console.log(response)
          //   }
          // })
          this.axios.get("/categories/"+currentCid+'/notes')
            .then(function(response){
              if(response.status===200){
                _this.$refs.notes.notes=response.data
                console.log(response)
              }
            })
        },
        handleEditNote(noteContent){
          this.$refs.noteEditForm.dialogFormVisible=true;
          this.$refs.noteEditForm.isCreate = false;
          this.$refs.noteEditForm.form=noteContent;
          this.$refs.noteEditForm.noteDialogTitle='编辑笔记标题与简介';
        },
        handleAddNote(){
          var currentCid = this.$refs.categoryBar.currentCid;
          this.$refs.noteEditForm.dialogFormVisible = true;
          this.$refs.noteEditForm.isCreate = true;
          this.$refs.noteEditForm.form = {};
          this.$refs.noteEditForm.cid = currentCid;
          this.$refs.noteEditForm.noteDialogTitle='新增笔记(标题与简介)';
        },
        handleEditCategory(){
          this.$refs.categoryBar.editCategory();
          console.log("inBookShelf")
        },

      }
    }
</script>

<style scoped>
</style>
