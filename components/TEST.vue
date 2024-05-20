<template>
  <div>
    <div class="navbar">
      <ul>
        <li :class="{ active: currentTab === 'home' }">
          <button @click="changeTab('home')">主页</button>
        </li>
        <li :class="{ active: currentTab === 'WQ' }">
          <button @click="changeTab('Event')">赛事中心</button>
        </li>
        <li :class="{ active: currentTab === 'PPQ' }">
          <button @click="changeTab('PPQ')">乒乓球</button>
        </li>
        <li :class="{ active: currentTab === 'YMQ' }">
          <button @click="changeTab('YMQ')">羽毛球</button>
        </li>
        <li :class="{ active: currentTab === 'WQ' }">
          <button @click="changeTab('WQ')">网球</button>
        </li>
        
        <!-- 根据需要添加其他选项卡 -->
      </ul>
    </div>

    <div v-if="currentTab === 'home'">
      <!-- 主页信息 -->
      <h1>主页信息</h1>
      <!-- 在这里显示主页信息 -->
    </div>
    <div v-else-if="currentTab === 'Event'">
      <Event />
    </div>
    <div v-else-if="currentTab === 'PPQ'">
      <PPQ />
    </div>
    <div v-else-if="currentTab === 'YMQ'">
      <YMQ />
    </div>
    <div v-else-if="currentTab === 'WQ'">
      <WQ />
    </div>
    <!-- 如有必要，可以添加类似的div来显示其他选项卡 -->
  </div>
</template>


<script>
import PPQ from './PPQ.vue';
import WQ from './WQ.vue';
import YMQ from './YMQ.vue';
import Event from './Event.vue';

export default {
  components: {
    PPQ,WQ,YMQ,Event
  },
  data() {
    return {
      currentTab: 'home' // 默认显示主页选项卡
    };
  },
  methods: {
    changeTab(tab) {
      this.currentTab = tab;
    }
  },
  mounted() {
    // 检查路由以确定显示哪个选项卡
    if (this.$route.name === 'UserHome') {
      // 如果路由是UserHome，则显示主页选项卡
      this.currentTab = 'home';
    }
  },
  watch: {
    // 监听路由变化，并根据情况更新当前选项卡
    $route(to) {
      if (to.name === 'UserHome') {
        this.currentTab = 'home';
      }
    }
  }
}
</script>
<style scoped>
.navbar {
  background-color: #333;
  overflow: hidden;
}

.navbar ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  text-align: center;
}

.navbar ul li {
  display: inline;
}

.navbar ul li button {
  background-color: transparent;
  border: none;
  color: white;
  padding: 14px 20px;
  text-decoration: none;
  cursor: pointer;
  font-size: 16px;
}

.navbar ul li button:hover {
  background-color: #ddd;
  color: black;
}

.navbar ul li.active button {
  background-color: #ddd;
  color: black;
}
</style>

