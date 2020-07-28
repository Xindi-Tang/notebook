import router from "./router";
import store from "./store";
  router.beforeEach((to, from, next) => {
    //根据字段判断是否路由过滤
    console.log(to.matched);
    if (to.matched.some(record => record.meta.requireAuth)) {
      if(store.state.currentUser !== null && store.state.currentUser){
        next()
      }
      else {
        console.log(to.matched);
        console.log("in");
        next({
          path: '/login',
          query: {redirect: to.fullPath}
        });
      }
    }
    else {
      next()//若点击的是不需要验证的页面,则进行正常的路由跳转
    }
  });
