<template>
    <el-row style="margin-top:10px" >
      <el-col :span="bookmarkCol">
        <bookmark ref="bookmark" style="height: 450px"></bookmark>
      </el-col>
      <el-col :span="noteCol">
        <el-card class="box-card note" shadow="never" v-model="note">
          <div slot="header" class="clearfix">
            <span>{{note.name}}</span>
            <el-button style="float: right; padding: 3px 0" type="text" :title="'前往编辑'">
              <i class="el-icon-edit" @click="edit(note.id)"></i>
            </el-button>
            <el-button style="float: right; padding: 3px 0;margin-right: 5px" type="text" :title="'打开/关闭左侧书签栏'">
              <i class="el-icon-tickets" @click="bookmarkSwitch"></i>
            </el-button>
          </div>
          <el-scrollbar style="height: 100%">
            <div v-html="note.contentHtml" class="text note-html markdown-body" @click="handleHtmlClick"></div>
          </el-scrollbar>
        </el-card>
      </el-col>

      <el-col :span="quoteCol" style="border-left: 1px solid #585858;">
        <el-card class="box-card note" shadow="never" v-model="quote">
          <div slot="header" class="clearfix">
            <span>{{quote.name}}</span>
            <el-button style="float: right; padding: 3px 0" type="text" :title="'前往编辑'">
              <i class="el-icon-edit" @click="edit(quote.id)"></i>
            </el-button>
            <el-button style="float: right; padding: 3px 0" type="text" :title="'关闭该引用笔记'">
              <i class="el-icon-close" @click="closeQuote"></i>
            </el-button>
          </div>
          <el-scrollbar style="height: 100%" id="quoteArea">
            <div v-html="quote.contentHtml" class="text note-html markdown-body"></div>
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
          },
          quote:{
          },
          bookmarkStatus:true,
          quoteStatus:false,
          bookmarkCol:3,
          noteCol:21,
          quoteCol:0
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
                let newHtml = _this.appendArrowButton(_this.note.contentHtml);
                _this.note.contentHtml = _this.removeTarget(newHtml)
                _this.getTitles(_this.note.contentHtml);
              }
              console.log(response.data.object)
            })
            .catch(function (error){
              console.log(error)
            })
        },
        appendArrowButton(contentHtml){
          let div = document.createElement("div")
          div.innerHTML = contentHtml
          let doc = div.children
          for(var i=0;i<doc.length;i++){
            if(doc[i].nodeName.indexOf("H") !== -1){
              let operationBtn = document.createElement("i")
              operationBtn.setAttribute("class","el-icon-arrow-down")
              operationBtn.style.cursor = "pointer"
              operationBtn.style.marginLeft = "5px"
              operationBtn.style.fontSize = "20px"
              doc[i].appendChild(operationBtn)
            }
          }
          console.log(div.innerHTML);
          return div.innerHTML;
        },
        getTitles(contentHtml) {
          let div = document.createElement("div"); //get a div element
          div.innerHTML = contentHtml; //set its html content
          console.log(div);
          let doc = div.children;
          let titles = [];
          for (var i = 0; i < doc.length; i++) {
            if (doc[i].nodeName.indexOf("H") !== -1) {
              const level = parseInt(doc[i].nodeName[1]);
              let stars = "";
              for (var j = 0; j < level; j++) {
                stars += " ";
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
        },
        edit(id){
          this.$router.push({
            path:'/note/edit',
            query:{
              noteId:id
            }
          })
        },
        removeTarget(contentHtml){
          let div = document.createElement("div")
          div.innerHTML = contentHtml
          let doc = div.children
          for(var i=0;i<doc.length;i++){
            if(doc[i].nodeName === "P"){
              for(var j=0;j<doc[i].children.length;j++){
                if(doc[i].children[j].nodeName === "A"){
                  if(doc[i].children[j].getAttribute("href").indexOf("notelink://") !== -1){
                    console.log(doc[i].children[j]);
                    doc[i].children[j].removeAttribute("target");
                  }
                }
              }
            }
          }
          return div.innerHTML
        },
        handleHtmlClick(ev){
          console.log(ev);
          if(ev.target.nodeName === "A" && ev.target.getAttribute("href").indexOf("notelink://") !== -1){
            this.quoteNote(ev)
          }
          else if(ev.target.nodeName === "I"){
            this.foldSwitch(ev)
          }
        },
        bookmarkSwitch(){
          if(this.bookmarkStatus){
            if(this.quoteStatus){
              this.colSwitch(0,14,10)
            }
            else{
              this.colSwitch(0,24,0)
            }
          }
          else{
            if(this.quoteStatus){
              this.colSwitch(3,11,10)
            }
            else{
              this.colSwitch(3,21,0)
            }
          }
          this.bookmarkStatus = !this.bookmarkStatus
        },
        closeQuote() {
          if(this.bookmarkStatus){
            this.colSwitch(3,21,0)
          }
          else{
            this.colSwitch(0,24,0)
          }
          this.quoteStatus = false
          this.quote = {}
        },
        colSwitch(b,n,q){
          this.bookmarkCol = b!=null?b:this.bookmarkCol
          this.noteCol = n!=null?n:this.noteCol
          this.quoteCol = q!=null?q:this.quoteCol
        },
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
