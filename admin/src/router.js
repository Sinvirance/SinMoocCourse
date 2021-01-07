import Vue from 'vue'
import Router from 'vue-router'
import Login from './components/login.vue'
import Admin from './components/admin.vue'

Vue.use(Router);

export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [{
        path: '*',
        redirect: "/login",
    }, {
        path: '/login',
        component: Login,
    }, {
      path: '/admin',
      component: Admin,
    }]
})