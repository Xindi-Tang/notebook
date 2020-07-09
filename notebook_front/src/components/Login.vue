<template>
  <div>
    <el-form ref="form" :model="loginForm" label-width="0px" class="login-box">
      <h3 class="login-title">登录</h3>
      <el-form-item>
        <el-input placeholder="请输入用户名" v-model="loginForm.username"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input placeholder="请输入密码" v-model="loginForm.password" show-password></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit" class="buttonRegister">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    name: "Login",
    data(){
      return{
        loginForm:{
          username:'txd',
          password:'123',
        }
      }
    },
    methods:{
      onSubmit(){
        var _this=this
        this.axios.post("/login", {
          username:this.loginForm.username,
          password:this.loginForm.password
        })
          .then(function (response) {
            console.log(response.data.status)
            if(response.data.status==200){
              _this.$store.commit('login',response.data.object),
              _this.$router.push({path:"/"})
            }
            else{
              alert("账号或密码错误")
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
  .login-box {
    border: 1px solid #DCDFE6;
    width: 350px;
    background-color: #C0C0C0;
    margin: 160px auto;
    padding: 35px 20px 10px 20px;
    border-radius: 5px;
    -webkit-border-radius: 5px; /*为了兼容别的不同的浏览器*/
    -moz-border-radius: 5px;
    box-shadow: 0 0 25px #909399;
    text-align: center;
    vertical-align: middle;
  }

  .login-title {
    text-align: center;
    margin: 0 auto 40px auto;
    color: #303133;
  }

  .buttonRegister{
    width:350px;
  }
</style>
