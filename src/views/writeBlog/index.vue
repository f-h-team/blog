<template>
  <div class="blog-editor">
    <!-- 标题输入框 -->
    <input 
      v-model="title" 
      placeholder="请输入博客标题" 
      class="title-input" 
    />
  
    <!-- 纯文本编辑区域 -->
    <textarea 
      v-model="editorContent" 
      placeholder="请开始您的编辑..." 
      class="text-editor">
    </textarea>
    
    <!-- 分类选择 -->
    <el-radio-group v-model="categoryId">
      <el-radio value="3" size="large">前端技术</el-radio>
      <el-radio value="4" size="large">后端技术</el-radio>
      <el-radio value="5" size="large">教程</el-radio>
      <el-radio value="6" size="large">其它(想写啥就写啥)</el-radio>
    </el-radio-group>
    
    <!-- 按钮区域 -->
    <div class="button-group">
      <button @click="publishBlog">发表</button>
      <button @click="clear">清空</button>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { reqAddBlog } from '@/api/blog';
import { ElMessage } from 'element-plus';

// 定义响应式数据
const title = ref(''); // 标题
const editorContent = ref(''); // 编辑器内容
const categoryId = ref(''); // 文章分类

// 发表博客
const publishBlog = async () => {
  if (!title.value || !editorContent.value) {
    ElMessage({
      type: 'error',
      message: '标题或内容不能为空',
    });
    return;
  }
  if (!categoryId.value) {
    ElMessage({
      type: 'error',
      message: '请选择文章的类型',
    });
    return;
  }

  // 组装文章数据
  const blogData = reactive({
    title: title.value,
    content: editorContent.value,
    category: { id: Number(categoryId.value) },
    status:1,
    description:''
  });

  // 调用接口
  const res = await reqAddBlog(blogData);
  if (res.code == 0) {
    ElMessage({
      type: 'success',
      message: '发布成功',
    });
    clear();
  } else {
    ElMessage({
      type: 'error',
      message: '发布失败',
    });
  }
};

// 清空数据
const clear = () => {
  title.value = '';
  editorContent.value = '';
};
</script>

<style scoped>
.blog-editor {
  width: 800px;
  margin: auto;
  padding: 20px;
}
.title-input {
  width: 100%;
  padding: 10px;
  font-size: 18px;
  margin-bottom: 10px;
  box-sizing: border-box;
}
.text-editor {
  width: 100%;
  height: 300px;
  padding: 10px;
  font-size: 16px;
  margin-bottom: 10px;
  box-sizing: border-box;
  resize: none; /* 禁止拖动改变大小 */
}
.button-group {
  display: flex;
  justify-content: center;
  gap: 10px;
}
</style>
