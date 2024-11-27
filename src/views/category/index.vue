<template>
    <div class="container">
        <el-card style="width: 900px; margin-top: 30px;"   >
            <!-- 标签页 -->
            <el-tabs v-model="activeTab" type="card"  @tab-click="handleClick">
                <el-tab-pane label="前端技术" name="3" >
                    <div class="chapter">
                        <a-card v-for="(article, index) in articles" :key="article.id" class="card-demo"
                            :title="article.title" hoverable @click="viewDetail(article)">
                            <!-- 文章内容 -->
                            <p class="summary">{{ article.content }}</p>
                        </a-card>
                    </div>
                </el-tab-pane>

                <el-tab-pane label="后端技术" name="4">
                    <div class="content">
                        <!-- <h3>后端技术</h3> -->
                        <a-card v-for="(article, index) in articles" :key="article.id" class="card-demo"
                            :title="article.title" hoverable @click="viewDetail(article)">
                            <!-- 文章内容 -->
                            <p class="summary">{{ article.content }}</p>
                        </a-card>
                    </div>
                </el-tab-pane>

                <el-tab-pane label="教程" name="5">
                    <div class="content">
                        <!-- <h3>教程</h3> -->
                        <a-card v-for="(article, index) in articles" :key="article.id" class="card-demo"
                            :title="article.title" hoverable @click="viewDetail(article)">
                            <!-- 文章内容 -->
                            <p class="summary">{{ article.content }}</p>
                        </a-card>
                    </div>
                </el-tab-pane>

                <el-tab-pane label="其它" name="6">
                    <div class="content">
                        <!-- <h3>其它</h3> -->
                        <a-card v-for="(article, index) in articles" :key="index" class="card-demo"
                            :title="article.title" hoverable @click="viewDetail(article)">
                            <!-- 文章内容 -->
                            <p class="summary">{{ article.content }}</p>
                        </a-card>
                    </div>
                </el-tab-pane>
            </el-tabs>
               <!-- 分页 -->
    <el-pagination
    v-model:current-page="pageNo"
     v-model:page-size="pageSize"
        :page-sizes="[3, 5, 7, ]"
      :background="true"
         layout="prev, pager, next, jumper,->,total,sizes"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      style="margin-top: 10px;"
    />
        </el-card>
     
    </div>
</template>

<script setup>
import {  nextTick, onMounted, reactive, ref } from 'vue';
import { ElCard, ElTabs, ElTabPane } from 'element-plus';
import { reqCategoryBlog } from '@/api/category';
import { useBlogStore } from '@/stores/blog';
import { useRouter } from 'vue-router';
const activeTab = ref('3');  // 初始选中“前端技术”标签
const total=ref(1)
const pageNo=ref(1)
const pageSize=ref(3)
const blogStore=useBlogStore()
const router=useRouter()
const articles = ref([
    {
        title: 'AJAX原理是什么?如何实现?',
        content: `
      AJAX全称(Async Javascript and XML)，即异步的JavaScript 和XML...
      它是一种创建交互式网页应用的技术，可以在不重新加载网页的情况下与服务器交换数据。
      使用XMLHttpRequest对象向服务器发送异步请求，从服务器获取数据后更新页面。
      实现步骤包括：创建XMLHttpRequest对象、与服务器建立连接、发送请求、监听状态变化，并更新页面。
    `,
    },
    {
        title: 'Vue的响应式原理',
        content: `
      Vue使用了Object.defineProperty和Proxy来实现响应式。
      当数据变化时，Vue会监听到getter和setter的调用并自动更新DOM。
    `,
    },
]);
const categoryParmas=reactive({
    current:'1',
    size:'3',
    category:{
        id:3
    }
})
// 点击分类
const handleClick=async(tab)=>{
nextTick(async()=>{
    categoryParmas.current=pageNo.value
    categoryParmas.size=pageSize.value
    
    // activeTab.value = tab.props.name; // 更新 activeTab 的值为点击的标签名
    console.log('activeTab.value:',activeTab.value);
    
    categoryParmas.category.id=  activeTab.value
    console.log(categoryParmas.category.id);

    const res=await reqCategoryBlog(categoryParmas)
    articles.value=res.data.records
    total.value=res.data.total
})
}
const handleSizeChange=()=>{
    handleClick()
}
const handleCurrentChange=()=>{
    handleClick()
}
// 点击文章
const viewDetail = (data) => {
  blogStore.saveArticle(data)
  router.push('/blogDetail')
}
onMounted(async()=>{
    const res=await reqCategoryBlog(categoryParmas)
    articles.value=res.data.records
    console.log(res);
    
})
</script>

<style scoped lang="scss">
.container {
    display: flex;
    justify-content: center;
}

.chapter {
    position: relative;
    left: 0;
    width: 900px;
}

.content {
    margin-top: 20px;
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
</style>
