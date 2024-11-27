<template>
  <div class="article-detail">
    <!-- 如果文章数据存在则渲染 -->
    <div v-if="article && article.userName">
      <h1 class="title">{{ article.title }}</h1>
      <div class="meta">
        <span class="author">作者: {{ article.userName }}</span>
        <span class="created">发布时间: {{ article.category.createTime }}</span>
        <span class="update">最后更新: {{ article.category.updateTime }}</span>
      </div>
      <div class="content">
        <p>{{ article.content }}</p>
      </div>
      <!-- 点赞 -->
      <div class="good"  @click="likeArticle" >
        <icon-heart-fill :style="{ fontSize: '20px',color: isLiked ? '#ff3366' : '#ccc' }" />
        {{ article.good }}
      </div>
    </div>

    <!-- 如果文章数据为空则提示 -->
    <div v-else>
      <p>
        这里没东西...............................................................................................................................................
      </p>
    </div>
      
  </div>
  <el-card>
      <!-- 评论区 -->
      <div class="comment-section">
      <h3>评论区</h3>
      <!-- 评论列表 -->
      <div class="comment-list">
        <div v-for="(comment, index) in comments" :key="comment.id" class="comment-item">
          <div class="comment-author">来自：{{ comment.userName }}</div>
          <div class="comment-content">{{ comment.content }}</div>
        </div>
      </div>

      <!-- 评论表单 -->
      <div class="comment-form">
        <el-input
          v-model="newComment"
          placeholder="写下你的评论..."
          type="textarea"
          :rows=3
        />
        <el-button type="primary" @click="submitComment" style="margin-top: 10px;">提交评论</el-button>
      </div>
    </div>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useBlogStore } from '@/stores/blog';
import { reqLikeBlog, reqBlogComment,reqCommnentBlog } from '@/api/blog';
const blogStore = useBlogStore()
// 模拟文章数据
const article = ref({
  id: '',
  title: '',
  created: '',
  userName: '',
  content: '',
});
let isLiked=ref(false)
// 评论数据
const comments = ref([]);
// 获取评论
const getComments=async()=>{
  const res=await reqBlogComment(article.value.id)
  console.log(res);
  comments.value=res.data
}
// 新评论内容
const newComment = ref('');
// 提交评论
const submitComment = async() => {
  if (!newComment.value.trim()) {
    return; // 如果评论为空，则不提交
  }

 const res=await reqCommnentBlog(article.value.id,newComment.value)
 console.log(res);
 if(res.code==0){
  ElMessage({
        type: 'success',
        message: '评论成功'
    });
 }
  newComment.value = ''; // 提交后清空评论内容
};


// 点赞
const likeArticle=async()=>{
  if (isLiked.value) return; // 如果已经点赞，则不执行任何操作
  const res=await reqLikeBlog(article.value.id)
  console.log(res);
  if(res.code==0){
    article.value.good++
    isLiked.value = true; // 标记为已点赞
  }
  
}

// 页面加载时获取文章
onMounted(() => {
  article.value = blogStore.getArticle()
  getComments()
});
</script>

<style scoped>
.article-detail {
  width: 800px;
  margin: 20px auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.title {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 10px;
}

.meta {
  display: flex;
  /* 开启弹性布局 */
  align-items: center;
  /* 垂直居中 */
  font-size: 14px;
  color: #888;
  margin-bottom: 20px;
}

.author {
  margin-right: 10px;
}

.created {
  margin-left: 10px;
}

.update {
  margin-left: auto;
  /* 将更新信息推到最右边 */
}

.content {
  font-size: 16px;
  line-height: 1.6;
  white-space: pre-wrap;
  /* 保持换行 */
}

/* 点赞 */
.good {
  display: flex;
  width: 30px;
  align-items: center;
  cursor: pointer; /* 鼠标悬停在整个 div 时变为手型 */
}
.comment-section h3 {
  font-size: 20px;
  font-weight: bold;
  margin: 0px;
}

.comment-list {
  margin-bottom: 20px;
}

.comment-item {
  padding: 10px;
  border-bottom: 1px solid #eee;
}

.comment-author {
  font-weight: bold;
  margin-bottom: 5px;
}

.comment-content {
  font-size: 14px;
  color: #555;
}

.comment-form {
  display: flex;
  flex-direction: column;
}

.comment-form .el-input {
  margin-bottom: 10px;
}

.comment-form .el-button {
  align-self: flex-start;
}
</style>