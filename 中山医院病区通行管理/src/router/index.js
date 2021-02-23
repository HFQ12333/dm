import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    noCache: true                if set true, the page will no be cached(default is false)
    affix: true                  if set true, the tag will affix in the tags-view
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  },
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    meta: {
      roles: ['editor']

    },
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index'),
        name: 'Dashboard',
        meta: {
          title: '工作台',
          icon: 'dashboard',
          roles: ['editor'],
        }
      }
    ]
  },
  {
    path: '/personManage/patientManage',
    component: Layout,
    redirect: '/personManage/pages',
    meta: {
      title: '人员管理',
      icon: 'user',
      roles: ['editor']
    },
    children: [
      {
        path: 'patientManage',
        component: () => import('@/pages/personManage/patientManage/index'),
        name: 'patientManage',
        meta: {
          title: '病员管理',
          roles: ['editor'] // or you can only set roles in sub nav
        }
      },
    ]
  },
  {
    path: '/personManage/accompaniesManage',
    component: Layout,
    redirect: '/personManage/pages',
    // alwaysShow: true, // will always show the root menu
    // name: 'personManage',
    meta: {
      title: '人员管理',
      icon: 'peoples',
      roles: ['editor']
    },
    children: [
      {
        path: 'accompaniesManage',
        component: () => import('@/pages/personManage/accompaniesManage'),
        name: 'accompaniesManage',
        meta: {
          title: '陪护人员管理'
          // if do not set roles, means: this page does not require permissiond
        }
      },
    ]
  },
  {
    path: '/personManage/traffacRecordManage',
    component: Layout,
    redirect: '/personManage/pages',
    // alwaysShow: true, // will always show the root menu
    // name: 'personManage',
    meta: {
      title: '人员管理',
      icon: 'peihu',
      roles: ['editor']
    },
    children: [
      {
        path: 'traffacRecordManage',
        component: () => import('@/pages/personManage/traffacRecordManage'),
        name: 'traffacRecordManage',
        meta: {
          title: '陪护通行记录管理',
        }
      }
    ]
  },
  {
    path: '/sickRoomManage',
    component: Layout,
    children: [
      {
        path: 'sickRoomManage',
        component: () => import('@/pages/sickRoomManage/index'),
        name: 'sickRoomManage',
        meta: {
          title: '病区管理', icon: 'component', noCache: true,
          roles: ['admin',] // or you can only set roles in sub nav
        }
      }
    ]
  },
  {
    path: '/deviceManage',
    component: Layout,
    children: [
      {
        path: 'deviceManage',
        component: () => import('@/pages/deviceManage/index'),
        name: 'deviceManage',
        meta: { title: '设备管理', icon: 'tools', noCache: true, roles: ['admin',] }
      }
    ]
  },
  {
    path: '/logManage',
    component: Layout,
    name: 'logManage',
    meta: {
      title: '系统管理',
      icon: 'log',
      roles: ['admin',]
    },
    children: [
      {
        path: 'logManage',
        component: () => import('@/pages/systemManage/logManage/index'),
        name: 'logManage',
        meta: {
          title: '日志管理',
        }
      },
    ]
  },
  {
    path: '/systemManage',
    component: Layout,
    name: 'systemManage',
    meta: {
      title: '系统管理',
      icon: 'settings',
      roles: ['admin',]
    },
    children: [
      {
        path: 'accountManage',
        component: () => import('@/pages/systemManage/accountManage'),
        name: 'accountManage',
        meta: {
          title: '账户管理'
        }
      },
    ]
  },


  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter () {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
