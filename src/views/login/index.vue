<template>
    <div class="login_contain">
        <el-row>
            <el-col :span="6"></el-col>
            <el-col :span="10">
                <!-- 登录 -->
                <el-form class="form" :model="formData" :rules="rules" v-if="!isAssign" label-width="auto" ref="form1">
                    <h1 style="font-size: 40px;">欢迎!</h1>
                    <h2 style="margin: 20px 0px; ">请输入账号和密码</h2>
                    <el-form-item label="账号" prop="username">
                        <el-input type="username" placeholder="请输入账号" v-model="formData.username"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input type="password" :show-password="true" placeholder="请输入密码"
                            v-model="formData.password"></el-input>
                    </el-form-item>
                    <div class="buttons-container">
                        <el-button style="left: 50px;" @click="login">登录</el-button>
                        <el-button @click="assign">注册</el-button>
                    </div>
                </el-form>
                <!-- 注册 -->
                <el-form class="form" :model="formData" v-if="isAssign" :rules="rules" label-width="auto" ref="form2">
                    <h1 style="font-size: 40px;">欢迎!</h1>
                    <h2 style="margin: 20px 0px; ">请注册您的账号和密码</h2>
                    <el-form-item label="账号" prop="username">
                        <el-input type="username" placeholder="请创建账号" v-model="formData.username"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input type="password" :show-password="true" v-model="formData.password"
                            placeholder="请添加密码"></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" prop="password">
                        <el-input type="password" :show-password="true" v-model="formData.confirmPassword"
                            placeholder="请再次确认密码"></el-input>
                    </el-form-item>
                    <div class="buttons-container">
                        <el-button @click="comfirmAssign">注册</el-button>
                        <el-button @click="back">返回</el-button>
                    </div>
                </el-form>
            </el-col>
            <el-col :span="8"></el-col>
        </el-row>
    </div>
</template>

<script setup>
import { ElMessage } from 'element-plus';
import { reactive, ref } from 'vue';
import { reqLogin, reqRegister } from '@/api/user';
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';
const userStore = useUserStore()
const router = useRouter()
let formData = reactive({
    username: 'abcde',
    password: '123456',
    confirmPassword: ''
})
let isAssign = ref(false)
const form1 = ref()
const form2 = ref()

const rules = {
    username: [
        { required: true, message: '请输入你的账号', trigger: 'blur' },
        { min: 4, max: 6, message: '请输入4-6个字符', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入你的用户密码', trigger: 'blur' },
        { min: 4, max: 6, message: '密码为4-6个字符', trigger: 'blur' }
    ]
}
// 注册
const assign = () => {
    Object.assign(formData, {
        username: '',
        password: '',
        confirmPassword: ''
    })
    isAssign.value = true
}
// 返回
const back = () => {
    Object.assign(formData, {
        username: '',
        password: '',
        confirmPassword: ''
    })
    isAssign.value = false
}
// 确认注册
const comfirmAssign = async () => {
    if (formData.password != formData.confirmPassword) {
        ElMessage({
            type: 'error',
            message: '两次密码不一致'
        })
        return
    }
    else {
        await form2.value.validate()
        // 注册请求
        const res = await reqRegister(formData)
        console.log(res);
        if (res.code == 0) {
            ElMessage({
                type: 'success',
                message: '注册成功'
            })
        } else {
            ElMessage({
                type: 'success',
                message: '注册失败'
            })
        }
        isAssign.value = false
    }
}

// 登录
const login = async () => {
    await form1.value.validate()
    // 发送请求
    // 发送 POST 请求
    await userStore.userLogin(formData)
    await userStore.getUserInfo()
    // 跳转
    console.log('跳转之前',userStore.userInfo.token);
    
    router.push('/home')
}
</script>

<style lang="scss" scoped>
.login_contain {
    width: 100%;
    height: 100vh;
    background-image: url('@/assets/img/5744f0e663a1da262186218b531603a.png');
    background-size: cover;
}

.form {
    position: relative;
    top: 180px;
    width: 100%;
    height: 100%;
    padding: 10px;
    background-image: url(@/assets/img/9e7ab688db3099c990a40139420f668.png);
    background-size: cover;
}

.buttons-container {
    display: flex;
    justify-content: center;
    /* 水平居中 */
    align-items: center;
    /* 垂直居中（如果需要的话） */
    gap: 10px;
    /* 可选：按钮之间的间距 */
}
</style>