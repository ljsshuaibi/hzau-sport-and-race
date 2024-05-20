<template>
  <div>
    <el-menu 
    :default-active="activeIndex" 
    class="el-menu-demo menu-container" 
    mode="horizontal" 
    router="true" 
    @select="handleSelect">
    <el-menu-item index="/AdminHome/BookMessage">用户预约管理</el-menu-item>
    <el-submenu index="2">
      <template slot="title">场地管理</template>
      <el-menu-item index="/AdminHome/AdminPPQ">乒乓球场</el-menu-item>
      <el-menu-item index="/AdminHome/AdminYMQ">羽毛球场</el-menu-item>
      <el-menu-item index="/AdminHome/AdminWQ">网球场</el-menu-item>
    </el-submenu>
    <el-menu-item index="/AdminHome/AdminEvent" >赛事管理</el-menu-item>
    </el-menu>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name:'AdminHomeComponent',
  data() {
    return {
      activeIndex:'/AdminHome/BookMessage'
    }
  },
}
</script>

<style scpode>
.menu-container {
  display: flex;
  justify-content: space-around;
}
</style>