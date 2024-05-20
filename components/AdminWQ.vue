<template>
    <div class="container">
    
    <div class="main">
      <el-divider />
      <!-- 这里是主要内容 -->
      <div class="info">
        <el-descriptions  border="true" column="2">
          <el-descriptions-item label="运动场名称">网球</el-descriptions-item>
          <el-descriptions-item label="运动场状态">
            <el-button type="success">可预约</el-button>
          </el-descriptions-item>
          <el-descriptions-item label="运动场状态">
            <el-button type="danger">已占用</el-button>
          </el-descriptions-item>
          <el-descriptions-item label="运动场状态">
            <el-button type="info">不可用</el-button>
          </el-descriptions-item>
        </el-descriptions>
      </div>
      <el-divider />
      <div class="book">
        <table>
          <thead>
            <tr>
              <th></th> <!-- 空白单元格 -->
              <th colspan="6">网球场</th> <!-- 跨越6列 -->
            </tr>
          </thead>
          <tbody>
            <!-- 时间段 -->
            <tr>
              <th>时间段</th>
              <th v-for="(field, fieldIndex) in fields" :key="fieldIndex">{{  field.id +' 号场 '}}</th>
            </tr>
            <tr v-for="(timeSlot, index) in timeSlots" :key="index">
              <td>{{ timeSlot.time }}</td>
              <td v-for="(field, fieldIndex) in timeSlot.fields" :key="fieldIndex">
                <button :id="'field' + field.id + '_button' + (index + 1)" class="field-button" 
                  :style="{ backgroundColor: field.color }" @click="toggleReservation(field.id, index + 1)">
                </button>
              </td>
            </tr>
            <tr>
            <th>操作</th>
            <th v-for="index in 6" :key="index">
              <el-button type="primary" size="mini" @click="toggleDisable(index)">
                {{ disableButtonFlag ? '解除禁用' : '禁用' }}
              </el-button>
            </th>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    
  </div>
  </template>
  
  <script>
  import axios from 'axios';
  export default {
   name:'AdminWQComponent',
   data() {
    return {
      responseData: null,
      fields: [
        { id: 1 },
        { id: 2 },
        { id: 3 },
        { id: 4 },
        { id: 5 },
        { id: 6 }
      ],
      timeSlots: [],
      
    };
    },
    mounted() {
        // 生成时间段
        this.generateTimeSlots();
        this.getData();
    },
    methods: {
        getData() {
      axios.post('http://127.0.0.1:80/api/userInfo/wq').then(response => {
        this.responseData = response.data;
        // 根据后端数据更新按钮状态
        this.updateButtonStatus(this.responseData);
      }).catch(error => {
        console.error('获取数据时出错:', error);
      });
    },

    generateTimeSlots() {
      const times = ['8:00-9:00', '9:00-10:00', '10:00-11:00', '11:00-12:00', '12:00-13:00', '13:00-14:00', '14:00-15:00', '15:00-16:00', '16:00-17:00', '17:00-18:00', '18:00-19:00', '19:00-20:00']; // 你可以根据需要生成更多时间段
      this.timeSlots = times.map(time => {
        return {
          time: time,
          fields: this.fields.map(field => ({ id: field.id, color: '#67C23A' })) // 生成初始状态为绿色的按钮
        };
      });
    },
    toggleDisable(index) {
      console.log('启动')
      if (this.disableButtonFlag) {
        this.enable(index); 
        console.log('@@@',index)
      } else {
        this.disable(index); 
        console.log('@@@@@@',index)
      }
    },
    disable(index) {
      // 执行禁用操作
      const Info={
        type:'网球',
        fieldId:index,
      }
      console.log("sssss",Info),
      axios.post('http://127.0.0.1:80/api/adminInfo/disenableColumn/wq',Info,{
        headers: {
          'Content-Type': 'application/json'
        }}).then(response=>{
          if(response.data===1){
            this.generateTimeSlots();
            this.getData();
          }
        }).catch(error=>{
          console.log('禁用出错:',error)
        })
      this.disableButtonFlag = true;
    },
    enable(index) {
      // 执行解除禁用操作
      const Info={
        type:'网球',
        fieldId:index,
      }
      axios.post('http://127.0.0.1:80/api/adminInfo/enableColumn/wq',Info,{
        headers: {
          'Content-Type': 'application/json'
        }}).then(response=>{
          if(response.data===1){
            this.generateTimeSlots();
            this.getData();
          }
        }).catch(error=>{
          console.$error('解除禁用出错:',error)
        })
      this.disableButtonFlag = false;
    },
    updateButtonStatus(backendData) {
      // 根据后端数据更新按钮状态
      backendData.forEach(data => {
        const fieldIndex = this.fields.findIndex(field => field.id === data.id);
        if (fieldIndex !== -1) {
          const columnIndex = fieldIndex + 1; // 计算列索引，从1开始
          const status = data.status;
          if (status === 1) {
            // 整列按钮变成灰色
            this.timeSlots.forEach(slot => {
              const button = slot.fields.find(field => field.id === data.id);
              if (button) button.color = '#909399';
            });
          } else {
            // 根据时间段的状态值更新按钮颜色
            Object.keys(data).forEach(key => {
              if (key.startsWith('time')) {
                const timeIndex = parseInt(key.slice(4), 10) - 1; // 获取时间段索引
                if (data[key] === 1) {
                  if (data.userName !== this.$store.state.userName) {
                    // 如果被其他用户预订，将按钮颜色更改为黄色
                    this.timeSlots[timeIndex].fields[columnIndex - 1].color = '#F56C6C';
                  } else {
                    // 如果被当前用户预订，将按钮颜色更改为红色
                    this.timeSlots[timeIndex].fields[columnIndex - 1].color = '#E6A23C';
                  }
                }
              }
            });
          }
        }
      });
    }
    }
  }
  </script>
  
  <style>
  .container {
  height: 100%;
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: column;
}



.main {
  flex: 3;
  padding: 20px 100px;
  display: flex;
  flex-direction: column;
}

/* 可根据需要自行添加样式 */
.info {
  flex: 2;
  margin-bottom: 20px;
}

.book {
  flex: 3;
  background-color: #eee;
}

.book table {
  width: 100%;
  border-collapse: collapse;
}

.book th,
.book td {
  border: 1px solid #3d3737;
  padding: 0; 
  text-align: center;
}

.book th {
  background-color: #ffffff;
}

.book button {
  width: calc(100% - 3px); /* 留出一定边距 */
  height: calc(100% - 3px); /* 留出一定边距 */
  padding: 15px;
  box-sizing: border-box;
  border: none;
}
.book button.green:hover {
  background-color: lightgreen; /* 绿色按钮悬浮时的背景颜色 */
}
.el-row {
  margin-bottom: 20px;
}
.el-row:last-child {
  margin-bottom: 0;
}
.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
  </style>