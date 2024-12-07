import { reactive } from 'vue'
import { defineStore } from 'pinia'
import { reqUserInfo,reqLogin } from '@/api/user/index.js'
import { ElMessage } from 'element-plus';


export const useUserStore = defineStore('user', () => {
  let userInfo = reactive({
    id:'',
    username: '哒哒哒',
    avatar:'',
    introduction: '长风破浪会有时',
    role: '管理员',
    email:'',
    token: localStorage.getItem('TOKEN')||''
  })

  // 用户登录
  const userLogin=async(userForm)=>{
    // 发送请求
    const res = await reqLogin(userForm)
    if (res.code == 0) {
        ElMessage({
            type: 'success',
            message: '登录成功'
        })
        // 存储数据
        console.log(res.data);
        
        userInfo.token = res.data
        localStorage.setItem('TOKEN',res.data)
        console.log('点击登录',userInfo.token );
        
    } else {
        ElMessage({
            type: 'error',
            message: '登录失败'
        })
    }
  }

  // 获取用户信息
  const getUserInfo = async () => {
    const res = await reqUserInfo()
    if (res.code == 0) {
      ElMessage({
        type: 'success',
        message: '获取用户信息成功'
      })
    } else {
      ElMessage({
        type: 'error',
        message: '获取用户信息失败'
      })
    }
    userInfo.id=res.data.id
    userInfo.avatar=res.data.avatar
    userInfo.role = res.data.role.name
    userInfo.username = res.data.userName
    userInfo.introduction=res.data.introduction
    userInfo.email=res.data.email
    console.log('获取用户信息',userInfo.token);
    
  }
  // 退出登录
  const userLogout=()=>{
    userInfo=null
    localStorage.removeItem('TOKEN')
  }
  return { userInfo, getUserInfo,userLogin,userLogout }
})
