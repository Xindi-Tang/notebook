<template>
    <div>
      <li v-for="(item,i) in titles">{{item}}</li>
    </div>
</template>

<script>
    export default {
      name: "Bookmark",
      data(){
        return{
          titles:[]
        }
      }
    }
</script>

<style scoped>

</style>
