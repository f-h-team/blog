import request from '@/utils/request.js'
const API={
    reqLogin_API:'/user/login',
    reqRegister_API:'/user/register',
    reqUserInfo_API:'/user/show',
    reqUserBlog_API:'/blog/showByUser?',
    reqDeleteBlog_API:'/blog/del/'
}
export const reqLogin=(data)=>{
    return request.post(API.reqLogin_API,null, {
        params: data  // 使用params传递数据
      })
}
export const reqRegister=(data)=>{
    return request.post(API.reqRegister_API, null,{
        params: data  // 使用params传递数据
      })
}
export const reqUserInfo=()=>{
    return request.post(API.reqUserInfo_API,null)
}
export const reqUserBlog=(current=1,size=2)=>{
    return request.get(API.reqUserBlog_API+`current=${current}&size=${size}`)
}
export const reqDeleteBlog=(blogId)=>{
    return request.delete(API.reqDeleteBlog_API+`${blogId}`)
}