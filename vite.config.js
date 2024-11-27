
import * as path from 'path'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite';
import { ArcoResolver } from 'unplugin-vue-components/resolvers';
import { vitePluginForArco } from '@arco-plugins/vite-vue'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
// https://vite.dev/config/
export default defineConfig({
  // server: {
  //   proxy: {
  //     '/api': {
  //       target: 'http://8.134.138.157:8110', // 后端地址
  //       changeOrigin: true, // 修改源，解决跨域
  //       rewrite: (path) => path.replace(/^\/api/, ''), // 重写路径
  //     },
  //   },
  // },
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ArcoResolver(),ElementPlusResolver()],
    }),
    vitePluginForArco({
      style: 'css'
    }),
    Components({
      resolvers: [
        ArcoResolver({
          sideEffect: true
        }),
        ElementPlusResolver()
      ]
      
    })
  ],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src')
    },
    extensions: ['.js', '.json',  '.vue']
  }
})



