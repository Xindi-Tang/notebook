<template>
  <div class="pagination">
    <el-pagination @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"
                   :current-page="currentPage"
                   :page-sizes="pageSizes"
                   :page-size="PageSize" layout="total, sizes, prev, pager, next, jumper"
                   :total="totalCount">
    </el-pagination>
  </div>
</template>

<script>
    export default {
      name: "Pagination",
      data(){
        return{
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
      methods:{
        // getPageNum(){
        //   var _this=this;
        //   _this.axios.get('categories/2/notes/length')
        //     .then(function (response) {
        //       if(response.status === 200){
        //         console.log(response)
        //         _this.totalCount=response.data
        //         // 将数据的长度赋值给totalCount
        //       }
        //     })
        //     .catch(function (error) {
        //       console.log(error)
        //     })
        // },
        // getPageNotes(n1,n2){
        //   var _this=this;
        //   //pagesize没用上呢 url: pagesize=?
        //   console.log(n2.toString());
        //   _this.axios.get('categories/2/notes/'+n2.toString())
        //     .then(function (response) {
        //       if(response.status === 200){
        //         console.log(response)
        //         _this.notes=response.data
        //         // 将数据的长度赋值给totalCount
        //       }
        //     })
        //     .catch(function (error) {
        //       console.log(error)
        //     })
        //
        //   // axios.post(url,{
        //   //     orgCode:1,
        //   //     // 每页显示的条数
        //   //     PageSize:n1,
        //   //     // 显示第几页
        //   //     currentPage:n2,
        //   //   },{emulateJSON: true},
        //   //   {
        //   //     headers:{"Content-Type": "application/x-www-form-urlencoded;charset=utf-8",}
        //   //   }
        //   // ).then(reponse=>{
        //   //   console.log(reponse)
        //   //   // 将数据赋值给tableData
        //   //   this.tableData=response.data
        //   //   // 将数据的长度赋值给totalCount
        //   //   this.totalCount=data.data.body.length
        //   // })
        // },
        // 分页
        // 每页显示的条数
        handleSizeChange(val) {
          // 改变每页显示的条数
          this.PageSize=val;
          // 注意：在改变每页显示的条数时，要将页码显示到第一页
          this.currentPage=1;
          this.$emit('changePageSize');
        },
        // 显示第几页
        handleCurrentChange(val) {
          // 改变默认的页数
          this.currentPage=val
          // 切换页码时，要获取每页显示的条数
          this.$emit('changePageNo');
        },
      }

    }
</script>

<style scoped>
  .pagination{
    text-align: left;
    position:fixed;
    margin:0px 0px 0px 50px;
    left:0;
    right:0;
    bottom:0;
    width:200px;
    height:50px;
  }
</style>
