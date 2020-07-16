<template>
    <el-row style="margin-top:10px" >
      <el-col :span="4">
        <bookmark ref="bookmark" style="height: 450px"></bookmark>
      </el-col>
      <el-col :span="20">
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
      </el-col>
    </el-row>
</template>

<script>
  import Bookmark from "./Bookmark";
    export default {
      name: "NoteDetail",
      components: {Bookmark},
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
                _this.getTitles(_this.note.contentHtml);
              }
              console.log(response.data.object)
            })
            .catch(function (error){
              console.log(error)
            })
        },
        getTitles(contentHtml) {
          let div = document.createElement("div"); //get a div element
          div.innerHTML = contentHtml; //set its html content
          console.log(div);
          let doc = div.children;
          let titles = [];
          for (var i = 0; i < doc.length; i++) {
            if (doc[i].nodeName.indexOf("H") !== -1) {
              console.log(doc[i].nodeName[0]);
              const level = parseInt(doc[i].nodeName[1]);
              let stars = "";
              for (var j = 0; j < level; j++) {
                stars += " * ";
              };
              console.log(doc[i].children[0]);
              titles.push({
                href: doc[i].children[0].getAttribute("id"),
                name: stars + doc[i].innerText,
                type: doc[i].nodeName[1]
              })
            }
          }
          this.$refs.bookmark.titles = titles;
        }
      }
    }
</script>

<style scoped>
  .note {
    border: 2px solid #EBEEF5!important;
    width: 95%;
    text-align: left;
    margin: auto;
    padding: 0px;
  }
  .note >>> .el-card__body{
    padding: 0px 0px 0px 40px;
  }
  .note >>> .clearfix{
    padding: 0px 0px 0px 20px;
  }
  .note-html{
    height: 450px;
  }
  .back{
    background-color: #585858!important;
    border: 0px;
    position: absolute;
    margin-top: 10px;
    margin-bottom: 5px;
    left: 10px;
    padding: 5px 5px;
    z-index: 99;
  }
</style>
