<template>
  <div>
    <category-bar @categorySelect="getNotes" @categoryDefault="getNotes" ref="categoryBar"></category-bar>
    <notes ref="notes" @editInfo="handleEditNote" @updateInfo="getNotes" @addNote="handleAddNote" @editCategory="handleEditCategory"></notes>
    <note-edit-form ref="noteEditForm" @updateInfo="getNotes"></note-edit-form>
    <pagination ref="pagination" @changePageSize="getNotes" @changePageNo="getNotes"></pagination>
  </div>
</template>

<script>
  import CategoryBar from "./CategoryBar";
  import Notes from "./Notes";
  import NoteEditForm from "./NoteEditForm";
  import Pagination from "./Pagination";
    export default {
      name: "BookShelf",
      components:{Notes, CategoryBar, NoteEditForm, Pagination},
      data(){
        return{

        }
      },
      methods:{
        getNotes(){
          var currentCid=this.$refs.categoryBar.currentCid
          var _this=this;
          this.axios.get("/categories/"+currentCid+'/notes/length')
          .then(function(response){
            if(response.status===200){
              _this.$refs.pagination.totalCount=response.data
            }
          })
          var currentPage=_this.$refs.pagination.currentPage;
          var pageSize=_this.$refs.pagination.PageSize;
          this.axios.get("/categories/"+currentCid.toString()+'/notes/'+currentPage.toString()+'/'+pageSize.toString())
            .then(function(response){
              if(response.status===200){
                _this.$refs.notes.notes=response.data
                // console.log(response)
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
          // console.log("inBookShelf")
        },

      }
    }
</script>

<style scoped>
</style>
