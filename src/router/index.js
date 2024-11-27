import {createRouter,createWebHashHistory} from 'vue-router'
import index from '@/views/layout/index.vue'
import home from '@/views/home/index.vue'
import category from '@/views/category/index.vue'
import login from '@/views/login/index.vue'
import personalCenter from '@/views/personalCenter/index.vue'
import writeBlog from '@/views/writeBlog/index.vue'
import test from '@/views/test/index.vue'
import blogDetail from '@/views/blogDetail/index.vue'

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      name: 'index',
      component: index,
      redirect:'/home',
      children:[
        {
          path: 'home',
          name: 'home',
          component: home
        },

        {
          path: '/category',
          name: 'category',
          component: category
        },
        {
          path: '/personalCenter',
          name: 'personalCenter',
          component: personalCenter
        },
        {
          path: '/writeBlog',
          name: 'writeBlog',
          component: writeBlog
        },
        {
          path: '/test',
          name: 'test',
          component: test
        },
        {
          path: '/blogDetail',
          name: 'blogDetail',
          component: blogDetail
        }
      ]
      
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
   
  ]
})

export default router
