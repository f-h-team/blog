import globals from "globals";
import pluginJs from "@eslint/js";
import pluginVue from "eslint-plugin-vue";


/** @type {import('eslint').Linter.Config[]} */
export default [
  {files: ["**/*.{js,mjs,cjs,vue}"]},
  {languageOptions: { globals: globals.browser },
  rules: {
    'space-before-function-paren': ['error', 'never'],
    "vue/multi-word-component-names": "off", // 禁用组件多单词命名规则
  }},
  pluginJs.configs.recommended,
  ...pluginVue.configs["flat/essential"],
  
];