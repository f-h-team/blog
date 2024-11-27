// 封装axios
import axios from "axios";
// 拿到用户登录后的token，封装起来
import { useUserStore} from '@/stores/user.js'
import { ElMessage } from "element-plus";

const request = axios.create({
    baseURL: 'http://8.134.138.157:8110', // 设置后端基础地址
    timeout: 5000
})
// 请求拦截器
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json';
    config.headers['Accept'] = 'application/json';
    // 将token携带在请求头
    // 该声明不能写在请求拦截器外面
    const userStore = useUserStore()
    if (userStore.userInfo.token) {
        config.headers.Authorization = `${userStore.userInfo.token}`;
    }

    return config
})
// 响应拦截器
request.interceptors.response.use((response) => {
    return response.data
}, (error) => {
    ElMessage({
        type: 'error',
        message: '请求错误'
    })
    return Promise.reject(error);
})
export default request