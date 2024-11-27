import request from '@/utils/request.js'
const API={
    reqAllBlog_API:'/blog/showAll/?',
    reqAddBlog_API:'/blog/add',
    reqSearchBlog_API:'/blog/search?',
    reqLikeBlog_API:'/blog/great/',
    reqBlogComment_API:'comments/show?blogId=',
    reqCommnentBlog_API:'/comments/add?',
}
export const reqAddBlog=(data)=>{
    return request.post(API.reqAddBlog_API,data)
}
export const reqAllBlog=(current=1,size=2)=>{
    return request.get(API.reqAllBlog_API+`current=${current}&size=${size}`)
}
export const reqSearchBlog=(keyword,current,size)=>{
    return request.get(API.reqSearchBlog_API+`str=${keyword}&current=${current||1}&size=${size||3}`)
}
export const reqLikeBlog=(blogId)=>{
    return request.get(API.reqLikeBlog_API+`${blogId}`)
}
export const reqBlogComment=(blogId)=>{
    return request.get(API.reqBlogComment_API+`${blogId}`)
}
export const reqCommnentBlog=(blogId,content,parentId=null)=>{
    return request.post(API.reqCommnentBlog_API+`blogId=${blogId}&content=${content}&parentId=${parentId}`)
}