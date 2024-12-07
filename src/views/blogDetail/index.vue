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

    <!-- 评论区 -->
    <div class="comment-section">
      <h3 class="comment-title">评论区</h3>

      <!-- 评论列表 -->
      <div class="comment-list">
        <div v-for="(comment, index) in comments" :key="comment.id" class="comment-item">
          <div class="comment-header">
            <span class="comment-author">用户-{{ comment.userName }}</span>
            <span class="comment-time">{{ comment.createTime }}</span>
          </div>
          <div class="comment-content">{{ comment.content }}</div>

          <!-- 只有根评论才显示回复按钮 -->
          <div v-if="!comment.parentId" class="reply-button">
            <el-button @click="toggleReplyBox(comment.id)" type="text">回复</el-button>
          </div>

          <!-- 回复框 -->
          <div v-if="showReplyBox[comment.id]" class="reply-form">
            <el-input
              v-model="newReply[comment.id]"
              placeholder="写下你的回复..."
              type="textarea"
              :rows="2"
            />
            <el-button
              type="primary"
              @click="submitReply(comment.id)"
              style="margin-top: 10px; width: 100%;"
            >
              提交回复
            </el-button>
          </div>

          <!-- 展示该评论的所有回复 -->
          <div v-if="comment.child && comment.child.length" class="replies">
            <div v-for="(reply, idx) in comment.child" :key="idx" class="reply-item">
              <div class="reply-author">用户-{{ reply.userName }}:</div>
              <div class="reply-content">{{ reply.content }}</div>
            </div>
          </div>
        </div>
      </div>

      <!-- 根评论的评论输入框 -->
      <div class="comment-form">
        <el-input
          v-model="newComment"
          placeholder="写下你的评论..."
          type="textarea"
          :rows="3"
        />
        <el-button type="primary" @click="submitComment" style="margin-top: 10px; width: 100%;">提交评论</el-button>
      </div>
    </div>

</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useBlogStore } from '@/stores/blog';
import { reqLikeBlog, reqBlogComment,reqCommnentBlog,reqBlog,reqReplyCommnent } from '@/api/blog';
import { useRoute } from 'vue-router';
const blogStore = useBlogStore()
const route=useRoute()
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
  const res=await reqBlogComment(route.query.blogId)
  console.log(res);
  comments.value=res.data
}
// 新评论内容
const newComment = ref('');
// 新回复内容，存储每个评论的回复
const newReply = ref({});

// 控制回复框显示/隐藏
const showReplyBox = ref({});
// 点击回复
const toggleReplyBox=(commentId)=>{
  showReplyBox.value[commentId] = !showReplyBox.value[commentId];
}
// 提交回复
const submitReply=async(commentId)=>{
  if (!newReply.value[commentId].trim()) {
    return; // 如果评论为空，则不提交
  }
  const content=newReply.value[commentId]
  const res=await reqReplyCommnent(route.query.blogId,content,commentId)
  if(res.code==0){
  ElMessage({
        type: 'success',
        message: '回复成功'
    });
    getComments()
 }
 showReplyBox.value[commentId] = !showReplyBox.value[commentId];
 newReply.value[commentId]=''
}
// 获取文章
const getBlog=async()=>{
  const res=await reqBlog(route.query.blogId  )
  console.log(res);
  
  if(res.code==0){
    article.value=res.data
  }
  
}
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
    getComments()
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
  getBlog()
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


.good {
  display: flex;
  width: 30px;
  align-items: center;
  cursor: pointer;
}

.comment-card {
  margin-top: 20px;
}

.comment-section {
  padding: 20px;
  background: #ffffff;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.comment-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 10px;
}

.comment-list {
  margin-bottom: 20px;
}

.comment-item {
  padding: 15px;
  border-radius: 8px;
  background: #f7f7f7;
  margin-bottom: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.comment-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

.comment-author {
  font-weight: bold;
}

.comment-time {
  font-size: 12px;
  color: #aaa;
}

.comment-content {
  font-size: 14px;
  color: #555;
}

.reply-button {
  margin-top: 10px;
}

.reply-form {
  margin-top: 10px;
}

.replies {
  margin-top: 15px;
  padding-left: 20px;
}

.reply-item {
  margin-top: 10px;
  padding: 10px;
  background-color: #f1f1f1;
  border-radius: 6px;
}

.reply-author {
  font-weight: bold;
}

.comment-form {
  margin-top: 20px;
}
</style>