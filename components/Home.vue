<template>
  <el-container>
    <el-aside width="200px"></el-aside>
    <el-main>
      <div class="image-container">
        <div class="image-item" @click="navigateTo('PPQ')">
          <img src="https://api.sjtu.edu.cn/v1/file/aaf8848f-ccb5-46ff-ab4e-25695365ed7b" alt="乒乓球" style="width: 100px; height: auto;">
          <p>乒乓球</p>
        </div>
        <div class="image-item" @click="navigateTo('YMQ')">
          <img src="https://api.sjtu.edu.cn/v1/file/38693031-0202-40ad-9374-c4b416091798" alt="羽毛球" style="width: 100px; height: auto;">
          <p>羽毛球</p>
        </div>
        <div class="image-item" @click="navigateTo('WQ')">
          <img src="https://api.sjtu.edu.cn/v1/file/922bdb8f-f0ee-44e8-97d9-e2a42a888f69" alt="网球" style="width: 100px; height: auto;">
          <p>网球</p>
        </div>
      </div>
      <el-divider></el-divider>
      <span>精品推荐</span>
      <div class="grid-container">
        <div class="grid-item"  @click="navigateTo('PPQ')">
          <img src="https://cdn.pixabay.com/photo/2019/03/10/14/08/table-tennis-4046307_960_720.jpg" alt="Grid Image 1">
          <p>乒乓球场</p>
          <p>场地数量: 6</p>
          <p>开放时间: 8:00-20:00</p>
        </div>
        <div class="grid-item" @click="navigateTo('YMQ')">
          <img src="https://cdn.pixabay.com/photo/2017/11/18/18/41/sports-2960839_1280.jpg" alt="Grid Image 5">
          <p>羽毛球场</p>
          <p>场地数量: 6</p>
          <p>开放时间: 8:00-20:00</p>
        </div>
        <div class="grid-item" @click="navigateTo('WQ')">
          <img src="https://cdn.pixabay.com/photo/2021/06/04/06/54/racket-6308994_1280.jpg" alt="Grid Image 9">
          <p>网球场</p>
          <p>场地数量: 6</p>
          <p>开放时间: 8:00-20:00</p>
        </div>
      </div>
    </el-main>
    <el-aside width="200px"></el-aside>
  </el-container>
</template>

<script>
export default {
  name: "HomeComponent",
  methods: {
    navigateTo(routeName) {
      this.$router.push({ name: routeName });
    }
  }
};
</script>

<style scoped>
.image-container {
  display: flex;
  justify-content: center;
  gap: 150px;
}

.image-item {
  text-align: center;
  cursor: pointer;
}

.grid-container {
  display: flex;
  justify-content: space-between;
  gap: 100px; 
  margin-top: 100px;
}

.grid-item {
  flex: 1; 
  overflow: hidden;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  height: 300px; /* 增加高度 */
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px; /* 增加内边距 */
}

.grid-item img {
  width: 100%;
  height: auto;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.grid-item:hover img {
  transform: scale(1.05);
}

.grid-item p {
  margin-top: 5px; /* 减少间距 */
  text-align: center;
  font-size: 12px; /* 减小字体 */
  color: #555;
}
</style>
