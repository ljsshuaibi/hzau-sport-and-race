<template>
  <div>
    <el-container>
      <el-aside></el-aside>
    <div >  
      <img :src="require('@/assets/img/logo2018.png')" alt="logo1">
      <img :src="require('@/assets/img/QQ截图20240511151454.png')" alt="logo2">
    </div>
    <el-aside></el-aside>
  </el-container>
    <el-menu 
      :default-active="activeIndex" 
      class="el-menu-demo menu-container" 
      mode="horizontal" 
      router="true" 
      @select="handleSelect">
      <el-menu-item index="/UserHome/Home">主页</el-menu-item>
      <el-submenu index="2">
        <template slot="title">预约中心</template>
        <el-menu-item index="/UserHome/PPQ">乒乓球场</el-menu-item>
        <el-menu-item index="/UserHome/YMQ">羽毛球场</el-menu-item>
        <el-menu-item index="/UserHome/WQ">网球场</el-menu-item>
      </el-submenu>
      <el-menu-item index="/UserHome/EVENT">赛事中心</el-menu-item>
      <el-menu-item index="/UserHome/Person">个人中心</el-menu-item>
    </el-menu>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "UserHomeComponent",
  data() {
    return {
      activeIndex: "" // 设置默认值为空字符串
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  }
};
</script>

<style scoped>
.menu-container {
  display: flex;
  justify-content: space-around;
}
</style>
