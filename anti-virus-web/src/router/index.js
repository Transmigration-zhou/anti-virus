import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Manage',
    component: () => import('../views/Manage.vue'),
    redirect: "/home",
    children: [
      { path: 'home', name: 'Home',  component: () => import('../views/Home.vue')},
      { path: 'user', name: 'Users',  component: () => import('../views/Users.vue')},
      { path: 'userinfo', name: 'UserInfo',  component: () => import('../views/UserInfo.vue')},
      { path: 'map', name: 'Map',  component: () => import('../views/Map.vue')},
      { path: 'vacation', name: 'Vacation',  component: () => import('../views/Vacation.vue')},
      { path: 'status', name: 'Status',  component: () => import('../views/Status.vue')},
      { path: 'vacationmanage', name: 'Status',  component: () => import('../views/VacationManage.vue')},
    ]

  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue'),
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  const isLogin = !!localStorage.getItem("user")
  if (to.path === '/login' || isLogin) {
    next()
  } else {
    next('/login')
  }
})

export default router
