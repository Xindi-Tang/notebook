<template>
  <div>
    <el-form ref="form" :model="registerForm" status-icon :rules="rules" label-width="0px" class="login-box">
      <h3 class="login-title">注册账户</h3>
      <el-form-item prop="username">
        <el-input placeholder="请输入用户名" v-model="registerForm.username"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input placeholder="请输入密码" v-model="registerForm.password" show-password></el-input>
      </el-form-item>
      <el-form-item prop="confirmedPsw">
        <el-input placeholder="再次输入密码" v-model="registerForm.confirmedPsw" show-password></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('form')" class="buttonRegister">注册</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onReset('form')" class="buttonRegister">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
    export default {
      name: "Register",
      data(){
        var validatePass = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请输入密码'));
          } else {
            if (this.registerForm.confirmedPsw !== '') {
              this.$refs.form.validateField('confirmedPsw');
            }
            callback();
          }
        };
        var validatePass2 = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请再次输入密码'));
          } else if (value !== this.registerForm.password) {
            callback(new Error('两次输入密码不一致!'));
          } else {
            callback();
          }
        };
        return{
          registerForm:{
            username:'',
            password:'',
            confirmedPsw:'',
          },
          rules: {
            password: [
              { validator: validatePass, trigger: 'change' }
            ],
            confirmedPsw: [
              { validator: validatePass2, trigger: 'change' }
            ],
          }

        }
      },
      methods:{
        onSubmit(formName){
          this.$refs[formName].validate((valid) => {
            if (valid) {
              var _this=this;
              this.axios.post("/register", {
                username:this.registerForm.username,
                password:this.registerForm.password
              })
                .then(function (response) {
                  _this.$router.push({
                    path:'/login',
                    query:{
                      username:_this.registerForm.username
                    }
                  });
                })
                .catch(function (error) {
                  console.log(error)
                });
              alert('submit!');
            } else {
              console.log('error submit!!');
              return false;
            }
          });
          // this.axios.post("/register", {
          //   username:this.registerForm.username,
          //   password:this.registerForm.password
          // })
          // .then(function (response) {
          //   console.log(response)
          // })
          // .catch(function (error) {
          //   console.log(error)
          // })
        },
        onReset(formName) {
          this.$refs[formName].resetFields();
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
