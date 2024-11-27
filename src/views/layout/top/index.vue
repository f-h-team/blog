<template>
        <div class="search">
            <a-space size="large">
                <a-input-search class="SearchInput"
                @search="search"
                :style="{ width: '740px' ,height:'50px', }" 
                placeholder="请输入你想搜索的内容" />
            </a-space>
        </div>
</template>

<script setup>
const search=()=>{
    console.log('搜索');
    
}
</script>

<style lang="scss" scoped>



.search{
    width: 100%;
}
.SearchInput{
border-radius:30px;
z-index: 100;
}
</style>