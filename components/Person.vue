<template>
  <el-container>
    <el-header height="30px"></el-header>
  <el-container>
    <el-aside width="100px"></el-aside>
    <el-main>
        <el-descriptions title="用户信息" direction="vertical" :column="2" border>
        <el-descriptions-item label="学号">{{ personData.userId }}</el-descriptions-item>
        <el-descriptions-item label="姓名">{{ personData.userName}}</el-descriptions-item>
        <el-descriptions-item label="详细信息">{{ personData.userMessage }} </el-descriptions-item>
        <el-descriptions-item label="违约信息">{{ personData.defaultsMessage }} </el-descriptions-item>
        </el-descriptions>
        <el-divider></el-divider>
        <el-descriptions title="场地信息" direction="vertical" :column="2" border></el-descriptions>
        <el-table
        ref="filterTable"
        :data="fieldData"
        style="width: 100%">
            <el-table-column
            prop="type"
            label="场地类型"
            width="220"
            >
            </el-table-column>
            <el-table-column
            prop="sportId"
            label="场地号"
            width="220"
            >
            </el-table-column>
            <el-table-column
            prop="time"
            label="预约时段"
            width="220"
            >
            </el-table-column>
            <el-table-column
            label="操作"
            >
            <template slot-scope="scope">
                <el-button  slot="reference" @click="cancelBook(scope.row.type,scope.row.sportId,scope.row.time)">取消预约</el-button>
            </template>
            </el-table-column>
        </el-table>
        <el-divider></el-divider>
        <el-descriptions title="赛事信息" direction="vertical" :column="2" border></el-descriptions>
        <el-table
        ref="filterTable"
        :data="eventData"
        style="width: 100%">
        <el-table-column
        prop="RaceTime"
        label="比赛日期"
        width="300"
        >
        </el-table-column>
        <el-table-column
        prop="RaceName"
        label="赛事名称"
        width="300">
        </el-table-column>
        <el-table-column
          label="操作"
          >
          <template slot-scope="scope">
              <el-button  slot="reference" @click="cancelSignUp(scope.row.RaceName)">取消报名</el-button>
          </template>
        </el-table-column>
    </el-table>
    </el-main>
        <el-aside width="100px"></el-aside>
  </el-container>
  
</el-container>

</template>

<script>
import axios from 'axios';
export default {
    name:"PersonComponent",
    data() {
        return {
            personData:{
                userId:null,
                userName:null,
                userMessage:null,
                defaultsMessage:null,
            },
            eventData:[],
                
            fieldData:[],
        }
    },
    mounted() {
        this.getPersonData();
        this.getFieldData();
        this.getEventData();
    },
    methods:{
        getPersonData()
        { 
            const Info={
              userId:this.$store.state.userName
            }
            axios.post('http://127.0.0.1:80/api/person',Info).then(response=>{
                this.personData=response.data
                console.log(this.personData.userId)
                if(this.personData.userId===null)this.$message.error("获取用户信息失败，请稍后重试")
            }).catch(error=>{
                console.error('出现错误',error)
            })
        },
        getFieldData() {
        const Info = {
          userId: this.$store.state.userName
        };
        axios.post('http://127.0.0.1:80/api/admin/sport', Info)
          .then(response => {
            // 处理后端返回的数据
            if (response.data !== null) {
              this.fieldData = response.data.map(item => {
                // 对每个场地的时间进行处理
                item.time = this.convertTime(item.time); // 转换时间
                return item;
              });
            } else {
              this.$message.error("获取场地信息失败，请稍后重试");
            }
          })
          .catch(error => {
            console.error('出现错误', error);
          });
        },
        convertTime(time) {
          // 根据时间数据转换为对应的时间段
          switch (time) {
            case 8:
              return '8:00-9:00';
            case 9:
              return '9:00-10:00';
            case 10:
              return '10:00-11:00';
            case 11:
              return '11:00-12:00';
            case 12:
              return '12:00-13:00';
            case 13:
              return '13:00-14:00';
            case 14:
              return '14:00-15:00';
            case 15:
              return '15:00-16:00';
            case 16:
              return '16:00-17:00'; 
            case 17:
              return '17:00-18:00';
            case 18:
              return '18:00-19:00';
            case 19:
              return '19:00-20:00';          
            // 添加更多的时间段转换规则
            default:
              return '未知时间';
          }
        },
        getEventData()
        {
          const Info={
              userId:this.$store.state.userName
            }
            axios.post('http://127.0.0.1:80/api/person/race',Info).then(response=>{
                this.eventData=response.data
                console.log('ooo',response.data)
                if(response.data ===null)this.$message.error("获取赛事信息失败，请稍后重试")
            }).catch(error=>{
                console.error('出现错误',error)
            })
        },
        formatter(row) {
        return row.RaceMessage;
        },
        filterStatus(value, row) {
          return row.Status === value;
        },
        
        cancelSignUp(value){
          const Info={
            RaceName:value,
            userId:this.$store.state.userName
          }
          console.log('@@@',Info)
          axios.post('http://127.0.0.1:80/api/userInfo/sport/Race/QuXiaoBaoMing', Info, {
          headers: {
            'Content-Type': 'application/json'
          }}).then(response=> {
          this.eventData = response.data;
          if (response.data === 1) {
            this.getEventData()
            this.$message.success('取消成功');
          }else{
            this.$message.error('取消失败，请稍后重试');
          }
          })
          .catch(error => {
            this.$message.error('网络问题:',error);
          });
        },
        cancelBook(type,sportId,time) {
        // 显示确认对话框
        this.$confirm('是否取消预约当前时段？', '取消预约确认', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          // 用户确认取消预约，向后端发送取消预约数据
          const cancelData = {
            type: type,
            SportId: sportId,
            time: time[0],
            Status: false,
            Userid: this.$store.state.userName
          };
          console.log("cancelData",cancelData)
          // 发送取消预约数据到后端
          axios.post('http://127.0.0.1:80/api/admin/delete', cancelData)
            .then(response => {
              if (response.data === 1) {
                this.getFieldData();
                this.$message.success('取消预约成功！');
              } else {
                this.$message.error('取消预约失败，请稍后重试！');
              }
            })
            .catch(error => {
              console.error('取消预约失败:', error);
              this.$message.error('取消预约失败，请稍后重试！');
            });
        }).catch(() => {
          // 用户取消取消预约
          this.$message.info('已取消取消预约！');
        });
      },
      }
}

</script>

<style>

</style>