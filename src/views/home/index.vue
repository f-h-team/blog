<template>
  <div style="height: auto;">
    <!-- 搜索 -->
    <div class="top">
      <div class="search">
            <a-space size="large">
                <a-input-search class="SearchInput"
                v-model="searchInfo.keyword"
                @search="search"
                :style="{ width: '740px' ,height:'50px', }" 
                placeholder="请输入你想搜索的内容" />
            </a-space>
        </div>
    </div>
    <!-- 文章部分 -->
    <div class="chapter">
      <a-card v-for="(article, index) in articles"
       :key="article.id" class="card-demo"
        :title="article.title" hoverable
        @click="viewDetail(article)">
          <!-- More 链接 -->
          <template #extra>
            <a-link>More</a-link>
          </template>
          <!-- 文章内容 -->
          <p class="summary">{{ article.content }}</p>
      </a-card>
      <!-- 分页 -->
      <el-pagination v-model:current-page="pageNo" v-model:page-size="pageSize" :page-sizes="[3, 5, 7,]"
        :background="true" layout="prev, pager, next, jumper,->,total,sizes" :total="total"
        @size-change="handleSizeChange" @current-change="handleCurrentChange" style="margin-top: 10px;" />
    </div>

  </div>

</template>

<script setup>
import { onMounted, reactive, ref } from 'vue';
import { reqAllBlog } from '@/api/blog';
import { useBlogStore } from '@/stores/blog';
import { useRouter } from 'vue-router';
import { reqSearchBlog } from '@/api/blog';
import { ElMessage } from 'element-plus';
const blogStore=useBlogStore()
const total = ref()
const pageNo = ref(1)
const pageSize = ref(3)
const searchInfo=reactive({
  keyword:'',
  pageNo:'',
  pageSize:''
})
const articles = ref([
  {
    id: '',
    title: '',
    created: '',
    userName: '',
    content: '',
  },
]);
const router=useRouter()
// 获取所有文章
const getAllBlog = async () => {
  if (blogStore.page&&blogStore.size){  
        pageNo.value=blogStore.page
        pageSize.value=blogStore.size
        blogStore.clear()
    }
  const res = await reqAllBlog(pageNo.value, pageSize.value)
  console.log(res);
  articles.value = res.data.records
  total.value = res.data.total

}
const handleSizeChange = async() => {
  // 判断是否是正在浏览关键字信息
  if(searchInfo.keyword){
    searchInfo.pageNo=pageNo.value
    searchInfo.pageSize=pageSize.value
    const res=await reqSearchBlog(searchInfo.keyword,searchInfo.pageNo,searchInfo.pageSize)
    articles.value = res.data.records
    total.value = res.data.total

  }else{
    getAllBlog()
  }
}
const handleCurrentChange =async () => {
    // 判断是否是正在浏览关键字信息
    if(searchInfo.keyword){
    searchInfo.pageNo=pageNo.value
    searchInfo.pageSize=pageSize.value
    const res=await reqSearchBlog(searchInfo.keyword,searchInfo.pageNo,searchInfo.pageSize)
    articles.value = res.data.records
    total.value = res.data.total

  }else{
    getAllBlog()
  }
}
// 搜索
const search=async()=>{
  const res=await reqSearchBlog(searchInfo.keyword)
  // 重置到第一页
  pageNo.value=1
  pageSize.value=3
  articles.value = res.data.records
  console.log(res);
  
  console.log(res.data.total);
  total.value = res.data.total
  
}
// 点击文章
const viewDetail = (data) => {  
  console.log(data);
  
  blogStore.saveArticle(data)
  blogStore.savePage(pageNo.value,pageSize.value)

  router.push('/blogDetail')
}
onMounted(() => {
  getAllBlog()
})
</script>

<style lang="scss" scoped>
// 搜索

.top {
  position: fixed;
  width: 100%;
  height: 50px;
  top: 0;
  /* 与菜单距离一致 */
  left: 380px;
  z-index: 100;
}

.search{
    width: 100%;
}
.SearchInput{
border-radius:30px;
z-index: 100;
}
// 文章部分样式
.chapter {
  position: relative;
  top: 55px;
  width: 740px;
  margin: 0;
}

// Card 样式
.card-demo {
  width: 100%;
  height: auto;
  overflow: hidden;
  transition: transform 0.2s;
  cursor: pointer;
  margin-top: 16px;
}

.card-demo:hover {
  transform: translateY(-4px);
}

// 控制内容省略的样式
.summary {
  font-size: 16px;
  line-height: 1.5;
  max-height: 4.5em;
  /* 控制显示行数，比如这里为3行 */
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  /* 设置显示的行数 */
  -webkit-box-orient: vertical;
  white-space: normal;
}
</style>