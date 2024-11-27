<template>
    <div>
        <el-card style="width: 800px; margin-top: 50px;">
            <el-tabs type="card" v-model="activeTab">
                <el-tab-pane label="个人资料" name="1">
                    <el-form v-show="!isEdit">
                        <el-form-item>
                            <img :src="userStore.userInfo.avatar" alt="" style="width: 60px;">
                        </el-form-item>
                        <el-form-item>
                            用户名：{{ userStore.userInfo.username }}
                        </el-form-item>
                        <el-form-item>
                            邮箱：{{ userStore.userInfo.email }}
                        </el-form-item>
                        <!-- <el-form-item>
                            性别：{{ userStore.userInfo.gender }}
                        </el-form-item> -->
                        <el-form-item>
                            个人简介：{{ userStore.userInfo.introduction }}
                        </el-form-item>
                        <button type="button" @click="Edit" class="mybutton">修改</button>
                        <button type="button" @click="exitLogin" class="mybutton">退出登录</button>
                    </el-form>
                    <el-form v-show="isEdit" label-width="100px">
                        <el-form-item :label="userStore.userInfo.avatar ? `修改头像` : `添加头像`">
                            <img :src="userStore.userInfo.avatar" alt="" style="width: 60px; margin-right: 10px;">
                        </el-form-item>
                        <el-form-item>
                            <el-upload class="avatar-uploader" action="http://8.134.138.157:8110/user/update"
                                :data="uploadData" :method="'PATCH'" :before-upload="beforeUpload"
                                :on-success="handleSuccess" :on-error="handleError" :headers="headers"
                                :show-file-list="false">
                                <el-button size="small" type="primary">上传头像</el-button>
                            </el-upload>
                        </el-form-item>
                        <!-- <el-form-item label="用户名：">
                            <el-input v-model="newUserInfo.username">
                            </el-input>
                        </el-form-item> -->
                        <el-form-item label="您的邮箱">
                            <el-input v-model="newUserInfo.email">
                            </el-input>
                        </el-form-item>
                        <!-- <el-form-item label="性别：">
                            <div>
                                <el-radio-group v-model="newUserInfo.gender">
                                    <el-radio value="男" size="large">男</el-radio>
                                    <el-radio value="女" size="large">女</el-radio>
                                    <el-radio value="" size="large">未知</el-radio>
                                </el-radio-group>
                            </div>
                        </el-form-item> -->
                        <el-form-item label="个性简介：">
                            <el-input v-model="newUserInfo.introduction">
                            </el-input>
                        </el-form-item>
                        <button @click="confirmEdit" class="mybutton" type="button">确认修改</button>
                        <button @click="isEdit = false" type="button" class="mybutton">取消</button>
                    </el-form>
                </el-tab-pane>
                <el-tab-pane label="我的博客" name="2">
                    <div class="chapter">
                        <a-card v-for="(article) in articles" :key="article.id" class="card-demo" :title="article.title"
                            hoverable>
                            <!-- 删除-->
                            <template #extra>
                                <el-popover :visible="visibleArticleId === article.id" placement="top" :width="160">
                                    <p>确定删除?</p>
                                    <div style="text-align: right; margin: 0">
                                        <el-button size="small" type="danger"
                                            @click="removeArticle(article.id)">确定</el-button>
                                        <el-button size="small" @click="visibleArticleId = null">
                                            取消
                                        </el-button>
                                    </div>
                                    <template #reference>
                                        <el-button @click="visibleArticleId = article.id">x</el-button>
                                    </template>
                                </el-popover>
                                <!-- <a-popover title="确定删除?" trigger="click">
                                    <template slot="content">
                                        <a-button type="danger" @click="removeArticle(article.id)">
                                            确定
                                        </a-button>
                                        <a-button>取消</a-button>
                                    </template>
                                    <a-button>
                                        <span class="close-icon" >×</span>
                                    </a-button>
                                </a-popover> -->
                            </template>
                            <!-- 文章内容 -->
                            <p class="summary" @click="viewDetail(article)">{{ article.content }}</p>
                        </a-card>
                        <el-pagination v-model:current-page="pageNo" v-model:page-size="pageSize"
                            :page-sizes="[3, 5, 7,]" :background="true"
                            layout="prev, pager, next, jumper,->,total,sizes" :total="total"
                            @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            style="margin-top: 10px;" />
                    </div>
                </el-tab-pane>
            </el-tabs>
            <!-- <div class="block">
                <el-avatar shape="square" :size="50" :src="userStore.avatar" />
            </div> -->
        </el-card>
    </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user';
import { onMounted, reactive, ref, watch } from 'vue';
import { reqUserBlog, reqDeleteBlog } from '@/api/user';
import { useRouter } from 'vue-router';
import { useBlogStore } from '@/stores/blog';
import { ElMessage } from 'element-plus';
import axios from 'axios';
const userStore = useUserStore()
const router = useRouter()
const isEdit = ref(false)
const newUserInfo = reactive({
    avatar: '',
    introduction: '',
    email: ''
})
const activeTab = ref('1')
const visibleArticleId = ref(null); // 存储当前弹窗的文章 ID// 个人博客展示
const total = ref(1)
const pageNo = ref(1)
const pageSize = ref(2)
const blogStore = useBlogStore()
const articles = ref([
    {
        id: '',
        title: '',
        created: '',
        userName: '',
        content: '',
    },
]);

// 上传头像
const headers = {
    Authorization: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiYWJjZGUiLCJhdXRob3JpdGllcyI6InVzZXIifQ.BsmjOV4reasuK8OBRbm_OA9FxlmUtFznvn07rrHQHhM", // 如果需要认证，添加 token
};

const uploadData = ref({
    user: new Blob(
        [JSON.stringify({
            avatar: userStore.userInfo.avatar,
            email: userStore.userInfo.email,
            introduction: userStore.userInfo.introduction
        })], {
        type: 'application/json'
    }
    )
});

// 上传前的处理函数
const beforeUpload = (rawFile) => {
    if (rawFile.type === 'image/png' || rawFile.type === 'image/jpeg' || rawFile.type === 'image/gif') {
        if (rawFile.size / 1024 / 1024 < 5) {
            return true;
        } else {
            ElMessage({
                type: 'error',
                message: '图片大小不能超过5MB'
            });
            return false;
        }
    } else {
        ElMessage({
            type: 'error',
            message: '只支持 PNG/JPEG/GIF 格式'
        });
        return false;
    }
};

// 上传成功后的处理
const handleSuccess = (response, file) => {
    console.log('上传成功');
    console.log(response);
    console.log(userStore.userInfo);

    userStore.userInfo.avatar = response.data;
    userStore.getUserInfo()
};

// 上传失败的处理
const handleError = (error, file, fileList) => {
    ElMessage({
        type: 'error',
        message: '上传失败'
    });
};


// 获取文章
const loadArticles = async () => {
    const res = await reqUserBlog(pageNo.value, pageSize.value)
    articles.value = res.data.records
    total.value = res.data.total
}
const handleSizeChange = () => {
    loadArticles()
}
const handleCurrentChange = () => {
    loadArticles()
}
watch(() => activeTab.value, (newValue) => {
    console.log('watchNewValue', newValue);
    if (newValue == '2') {
        loadArticles()
    }
})
// 点击文章
const viewDetail = (data) => {
    blogStore.saveArticle(data)
    router.push('/blogDetail')
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
        loadArticles()
    } else {
        ElMessage({
            type: 'error',
            message: '删除失败'
        })
    }

}


// 初始化加载
onMounted(() => {
    loadArticles()
});



// 修改
const Edit = () => {
    Object.assign(newUserInfo, userStore.userInfo)
    isEdit.value = true
}
// 确认修改
const confirmEdit = async() => {
    const emptyFile = new File([], '');  // 创建一个空文件对象
    let formData = new FormData()
    Object.assign(userStore.userInfo, newUserInfo)
    formData.append('file', emptyFile)
    formData.append('user', new Blob([JSON.stringify(newUserInfo)], {
        type: "application/json"
    }))
   await axios({
        method: 'PATCH',
        url: 'http://8.134.138.157:8110/user/update',
        data: formData,
        headers: { Authorization: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiYWJjZGUiLCJhdXRob3JpdGllcyI6InVzZXIifQ.BsmjOV4reasuK8OBRbm_OA9FxlmUtFznvn07rrHQHhM",},
    }).then(res => {
        console.log(res.data);
        
        if (res.data.code == 0) {
        ElMessage({
            type: 'success',
            message: '修改成功'
        })
        isEdit.value = false
        userStore.getUserInfo()
        loadArticles()
    } else {
        ElMessage({
            type: 'error',
            message: '修改失败'
        })
    }

    })
        .catch(error => {
            console.log(error);
        });
        

}
// 退出登录
const exitLogin = () => {
    localStorage.removeItem('TOKEN')
    userStore.userInfo.token = ''
    router.push('/login')
}

</script>

<style lang="scss" scoped>
.mybutton {
    background-color: #4CAF50;
    color: white;
    padding: 10px 10px;
    border: none;
    border-radius: 5px;
    margin-left: 10px;
    cursor: pointer;
}

.mybutton:hover {
    background-color: #45a049;
}

// 文章部分样式
.chapter {
    position: relative;
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

.close-icon {
    cursor: pointer;
    font-size: 15px;
    color: #999;
    transition: color 0.3s ease;
}

.close-icon:hover {
    color: #ff4d4f;
    /* 鼠标悬停时变为红色 */
}
</style>