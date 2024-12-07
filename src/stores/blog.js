import { reactive,ref } from 'vue'
import { defineStore } from 'pinia'



export const useBlogStore = defineStore('blog', () => {

    let page=ref(1)
    let size=ref(3)

      const savePage=(pageNo,pageSize)=>{
        page.value=pageNo
        size.value=pageSize
      }
      const clear=()=>{
        page.value=null
        size.value=null
      }
  return { page,size ,savePage,clear}
})
