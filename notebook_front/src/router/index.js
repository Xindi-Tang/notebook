import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Register from "../components/Register";
import Login from "../components/Login";
import Index from "../components/Index";
import Home from "../components/home/Home";
import BookShelf from "../components/bookshelf/BookShelf";

Vue.use(Router)
//解决ElementUI导航栏的vue-router重复点击菜单报错问题
const originalPush = Router.prototype.push
Router.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

export default new Router({
  routes: [
    // {
    //   path: '/',
    //   name: 'HelloWorld',
    //   component: HelloWorld
    // },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/',
      name: 'Index',
      component: Index,
      redirect:"/home",
      children:[
        {
          path: '/home',
          name:'Home',
          component:Home
        },
        {
          path: '/bookshelf',
          name: 'BookShelf',
          component: BookShelf
        }
      ]
    }
  ]
})
