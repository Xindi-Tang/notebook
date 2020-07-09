<template>
  <div>
    <navi-menu></navi-menu>
    <router-view/>
  </div>
</template>

<script>
  import NaviMenu from "./common/NaviMenu";
    export default {
      name: "Index",
      components:{NaviMenu}
    }
</script>

<style scoped>

</style>
