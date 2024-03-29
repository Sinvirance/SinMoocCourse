import Vue from "vue"
import Router from "vue-router"
import Login from "./views/login.vue"
import Admin from "./views/admin.vue"
import Welcome from "./views/admin/welcome"
import Category from "./views/admin/category";
import Course from "./views/admin/course";
import Content from "./views/admin/content";
import Teacher from "./views/admin/teacher";
import Member from "./views/admin/member";
import Sms from "./views/admin/sms";
import Chapter from "./views/admin/chapter"
import Section from "./views/admin/section"
import File from "./views/admin/file"
import User from "./views/admin/user"
import Resource from "./views/admin/resource"
import Role from "./views/admin/role"

Vue.use(Router);

// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

export default new Router({
  mode: "history",
  base: process.env.BASE_URL,

  routes: [{
    path: "*",
    redirect: "/login",
  }, {
    path: "",
    redirect: "/login",
  }, {
    path: "/login",
    component: Login,
  }, {
    path: "/",
    name: "admin",
    component: Admin,
    meta: {
      loginRequire: true
    },
    // 配置 admin 子路由
    children: [{
      path: "welcome",
      name: "welcome",
      component: Welcome
    }, {
      path: "business/category",
      name: "business/category",
      component: Category
    }, {
      path: "business/course",
      name: "business/course",
      component: Course
    }, {
      path: "business/content",
      name: "business/content",
      component: Content
    }, {
      path: "business/teacher",
      name: "business/teacher",
      component: Teacher
    }, {
      path: "business/member",
      name: "business/member",
      component: Member
    }, {
      path: "business/sms",
      name: "business/sms",
      component: Sms,
    }, {
      path: "business/chapter",
      name: "business/chapter",
      component: Chapter
    }, {
      path: "business/section",
      name: "business/section",
      component: Section
    }, {
      path: "file/file",
      name: "file/file",
      component: File
    }, {
      path: "system/user",
      name: "system/user",
      component: User
    }, {
      path: "system/resource",
      name: "system/resource",
      component: Resource
    }, {
      path: "system/role",
      name: "system/role",
      component: Role
    }]
  }]
})
