<template>
    <div >

            <div class="menu">
                <Menu></Menu>
            </div>
            <div class="main">
                <RouterView></RouterView>
            </div>
            <div>不知道</div>
    </div>
  </template>
  
  <script setup>
    import Menu from '@/views/layout/menu/index.vue'
</script>
  <style scoped>
  .menu{
    position: fixed;
    left: 0;
    top: 0px;
    height: 80vh;
    width: 300px;
    background-color: var(--color-neutral-2);
  }
  .main{
    position: absolute;
    height: 100vh;

    left: 380px  ;
  }
  </style>
  