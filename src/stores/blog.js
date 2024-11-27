import { reactive,ref } from 'vue'
import { defineStore } from 'pinia'



export const useBlogStore = defineStore('blog', () => {
    let article=reactive({
        id: '',
        title: '',
        created: '',
        userName: '',
        content: '',
        good:'',
        category:{
          createTime:'',
          updateTime:'',
        },
      })
    let page=ref(1)
    let size=ref(3)
      const saveArticle=(data)=>{
        console.log('=========');
        
        console.log(data);
        console.log(article);
        
        Object.assign(article,data)
        
      }
      const getArticle=()=>{
        return article
      }
      const savePage=(pageNo,pageSize)=>{
        page.value=pageNo
        size.value=pageSize
      }
      const clear=()=>{
        Object.assign(article,{
          id: '',
          title: '',
          created: '',
          userName: '',
          content: '',
          good:'',
          category:{
            createTime:'',
            updateTime:'',
          },
        })
        page.value=null
        size.value=null
      }
  return { article,page,size,saveArticle,getArticle ,savePage,clear}
})
