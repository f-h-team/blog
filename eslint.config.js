import globals from "globals";
import pluginJs from "@eslint/js";
import pluginVue from "eslint-plugin-vue";

/** @type {import('eslint').Linter.Config[]} */
export default [
  {
    files: ["**/*.{js,mjs,cjs,vue}"],
    languageOptions: { globals: globals.browser },
    plugins: { vue: pluginVue }, // 显式声明 Vue 插件
    rules: {
      "space-before-function-paren": ["error", "never"],
      "vue/multi-word-component-names": "off", // 禁用组件多单词命名规则
    },
  },
  pluginJs.configs.recommended,
  pluginVue.configs.recommended, // 推荐使用 Vue 的全局配置
];
