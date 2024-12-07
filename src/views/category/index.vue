<template>
    <div class="container">
        <el-card style="width: 900px; margin-top: 30px;" >
            <!-- 标签页 -->
            <el-tabs v-model="activeTab" type="card"  @tab-click="handleClick">
                <el-tab-pane label="前端技术" name="3" >
                    <div class="content">
                        <a-card v-for="(article, index) in articles" :key="article.id" class="card-demo"
                            :title="article.title" hoverable  @click="viewDetail(article)">
                            <template #extra>
                                more
                                <!-- <el-popover :visible="visibleArticleId === article.id" placement="top" :width="160">
                                    <p>确定删除?{{ article.content }}</p>
                                    <div style="text-align: right; margin: 0">
                                        <el-button size="small" type="danger"
                                            @click="removeArticle(article.id)">确定</el-button>
                                        <el-button size="small" @click="visibleArticleId = null">
                                            取消
                                        </el-button>
                                    </div>
                                    <template #reference>
                                        <el-button @click.stop="visibleArticleId = article.id">x</el-button>
                                    </template>
                                </el-popover> -->
                            </template>
                            <!-- 文章内容 -->
                            <p class="summary" >{{ article.content }}</p>
                        </a-card>
                    </div>
                </el-tab-pane>

                <el-tab-pane label="后端技术" name="4">
                    <div class="content">
                        <!-- <h3>后端技术</h3> -->
                        <a-card v-for="(article, index) in articles" :key="article.id" class="card-demo"
                            :title="article.title" hoverable  @click="viewDetail(article)">
                            <template #extra>
                                more
                                <!-- <el-popover :visible="visibleArticleId === article.id" placement="top" :width="160">
                                    <p>确定删除?{{ article.content }}</p>
                                    <div style="text-align: right; margin: 0">
                                        <el-button size="small" type="danger"
                                            @click="removeArticle(article.id)">确定</el-button>
                                        <el-button size="small" @click="visibleArticleId = null">
                                            取消
                                        </el-button>
                                    </div>
                                    <template #reference>
                                        <el-button @click.stop="visibleArticleId = article.id">x</el-button>
                                    </template>
                                </el-popover> -->
                            </template>
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
                            <template #extra>
                                more
                                <!-- <el-popover :visible="visibleArticleId === article.id" placement="top" :width="160">
                                    <p>确定删除?{{ article.content }}</p>
                                    <div style="text-align: right; margin: 0">
                                        <el-button size="small" type="danger"
                                            @click="removeArticle(article.id)">确定</el-button>
                                        <el-button size="small" @click="visibleArticleId = null">
                                            取消
                                        </el-button>
                                    </div>
                                    <template #reference>
                                        <el-button @click.stop="visibleArticleId = article.id">x</el-button>
                                    </template>
                                </el-popover> -->
                            </template>
                            <!-- 文章内容 -->
                            <p class="summary" >{{ article.content }}</p>
                        </a-card>
                    </div>
                </el-tab-pane>

                <el-tab-pane label="其它" name="6">
                    <div class="content">
                        <!-- <h3>其它</h3> -->
                        <a-card v-for="(article, index) in articles" :key="index" class="card-demo"
                            :title="article.title" hoverable  @click="viewDetail(article)">
                            <template #extra>
                                more
                                <!-- <el-popover :visible="visibleArticleId === article.id" placement="top" :width="160" :append-to-body="false">
                                    <p>确定删除?{{ article.content }}</p>
                                    <div style="text-align: right; margin: 0">
                                        <el-button size="small" type="danger"
                                            @click="removeArticle(article.id)">确定</el-button>
                                        <el-button size="small" @click="visibleArticleId = null">
                                            取消
                                        </el-button>
                                    </div>
                                    <template #reference>
                                        <el-button @click.stop="visibleArticleId = article.id">x</el-button>
                                    </template>
                                </el-popover> -->
                            </template>
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
import { reqDeleteBlog } from '@/api/user';
const activeTab = ref('3');  // 初始选中“前端技术”标签
const total=ref(1)
const pageNo=ref(1)
const pageSize=ref(3)
const blogStore=useBlogStore()
const router=useRouter()
const articles = ref([]);
const visibleArticleId = ref(null); // 存储当前弹窗的文章 ID// 个人博客展示
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
  router.push({
    path:'/blogDetail',
    query: { blogId: data?.id }
  })
}
// 删除文章
const removeArticle = async (blogId) => {
    const res = await reqDeleteBlog(blogId)
    console.log(res);
    if (res.code == 0) {
        ElMessage({
            type: 'success',
            message: '删除成功'
        })
        getAllBlog()
    } else {
        ElMessage({
            type: 'error',
            message: '删除失败'
        })
    }

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
