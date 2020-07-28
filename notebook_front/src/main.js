// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios';
import VueAxios from "vue-axios";
import store from "./store";
import mavonEditor from 'mavon-editor';
import 'mavon-editor/dist/css/index.css';
require('./filer.js');
axios.defaults.baseURL="/api";
axios.defaults.withCredentials=true;
Vue.use(VueAxios,axios);
// require('./mock/mock.js')
Vue.config.productionTip = false
Vue.use(ElementUI);
Vue.use(mavonEditor);
// router.beforeEach((to, from, next) => {
//   //根据字段判断是否路由过滤
//   if (to.matched.some(record => record.meta.requireAuth)) {
//     console.log("ing");
//     if(store.state.currentUser !== null && store.state.currentUser){
//       next()
//     }
//     else {
//       //防止无限循环
//       if (to.name === 'Login') {
//         next();
//         return
//       }
//       next({
//         path: '/login',
//       });
//     }
//   } else {
//     next()//若点击的是不需要验证的页面,则进行正常的路由跳转
//   }
// });
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
