<template>
    <div class="logo">
        <img src="@/assets/OIP-C.jpg" alt="">
        <h1>博客园</h1>
    </div>
     <div class="menu-demo">
    <a-menu mode="pop" showCollapseButton>
      <router-link to="/home">
        <a-menu-item key="1">
        <template #icon><icon-home></icon-home></template>
        首页
      </a-menu-item>
      </router-link>
      <router-link to="/category">
        <a-menu-item key="2"  >
        <template #icon><icon-apps /></template>
        分类
      </a-menu-item>
      </router-link>
      <router-link to="/writeBlog">
        <a-menu-item key="3" >
        <template #icon><icon-pen-fill /></template>
        发表博客
      </a-menu-item>
      </router-link>
      <router-link to="/personalCenter">
        <a-menu-item key="4" >
        <template #icon><icon-user /></template>
        个人资料
      </a-menu-item>
      </router-link>
    </a-menu>
  </div>

</template>

<script setup>
import {
    IconHome,
    IconUser,
  IconApps,
  IconPenFill
} from '@arco-design/web-vue/es/icon';


</script>

<style  scoped>
.logo{
    margin-bottom: 0;
    display: flex;
}
img {
    display:block ;
    padding-left: 10px;
    width: 60px;
    height: 60px;
    border-radius: 10px;
}
.menu-demo {
  width: auto;
  height: 600px;
  padding: 10px;
  padding-top: 0;
  background-color: var(--color-neutral-2);
}

.menu-demo .arco-menu {
  width: 250px;
  height: 100%;
  box-shadow: 0 0 1px rgba(0, 0, 0, 0.3);
}

.menu-demo .arco-menu :deep(.arco-menu-collapse-button) {
  width: 32px;
  height: 32px;
  border-radius: 50%;
}

.menu-demo .arco-menu:not(.arco-menu-collapsed) :deep(.arco-menu-collapse-button) {
  right: 0;
  bottom: 8px;
  transform: translateX(50%);
}

.menu-demo .arco-menu:not(.arco-menu-collapsed)::before {
  content: '';
  position: absolute;
  right: 0;
  bottom: 0;
  width: 48px;
  height: 48px;
  background-color: inherit;
  border-radius: 50%;
  box-shadow: -4px 0 2px var(--color-bg-2), 0 0 1px rgba(0, 0, 0, 0.3);
  transform: translateX(50%);
}

.menu-demo .arco-menu.arco-menu-collapsed {
  width: 48px;
  height: auto;
  padding-top: 24px;
  padding-bottom: 138px;
  border-radius: 22px;
}

.menu-demo .arco-menu.arco-menu-collapsed :deep(.arco-menu-collapse-button) {
  right: 8px;
  bottom: 8px;
}
</style>