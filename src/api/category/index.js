import request from '@/utils/request.js'
const API={
    reqCategoryBlog_API:'/blog/showByCategory?',
}
export const  reqCategoryBlog=(data)=>{
    return request.post(API.reqCategoryBlog_API + `current=${data.current}&size=${data.size}`, {
            id: data.category.id
        
    })
}
