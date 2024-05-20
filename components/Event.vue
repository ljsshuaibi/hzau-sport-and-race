<template>
  <div>
    <el-container>
      <el-header height="30px"></el-header>
      <el-container>
        <el-aside width="50px"></el-aside>
    <el-table
        ref="filterTable"
        :data="tableData"
        style="width: 100%">
        <el-table-column
        prop="Time"
        label="开始日期"
        sortable
        width="180"
        >
        </el-table-column>
        <el-table-column
        prop="RaceName"
        label="赛事名称"
        width="180">
        </el-table-column>
        <el-table-column
        prop="RaceMessage"
        label="赛事信息"
        :formatter="formatter">
        </el-table-column>
        <el-table-column
          prop="SignUpInfo"
          label="报名人数"
          width="180">
          <template slot-scope="scope">
            <span>{{scope.row.Total}}/{{scope.row.Number}}</span>
          </template>
        </el-table-column>
        <el-table-column
        prop="Boss"
        label="发起人"
        width="180">
        </el-table-column>
        <el-table-column
          label="操作"
          width="300">
          <template slot-scope="scope">
            <el-button :disabled="scope.row.Status === '已结束'" slot="reference" @click="scope.row.Status === '报名中' ? signUp(scope.row.Id) : null">报名</el-button>
            <el-button :disabled="scope.row.Status === '报名中'" @click="scope.row.Status === '已结束' ? viewResults(scope.row.RaceName) : null">查看比赛结果</el-button>
          </template>
        </el-table-column>
        <el-table-column
        prop="Status"
        label="赛事状态"
        width="100"
        :filters="[{ text: '报名中', value: '报名中' }, { text: '进行中', value: '进行中' },{ text: '已结束', value: '已结束' }]"
        :filter-method="filterTag"
        filter-placement="bottom-end">
        <template slot-scope="scope">
            <el-tag
            :type="scope.row.Status === '报名中' ? 'primary' : (scope.row.Status=== '进行中' ? 'warning' : 'success')"
            disable-transitions>{{scope.row.Status}}</el-tag>
        </template>
        </el-table-column>
   
    </el-table>
    <el-aside width="50px"></el-aside>
  </el-container>
  </el-container>
  <el-dialog
      title="比赛结果"
      :visible.sync="dialogVisible"
      width="30%">
      <div v-if="selectedRace">
        <h3>{{ selectedRace.RaceName }}</h3>
        <div v-if="selectedRaceResults">
          <h4>第一名: {{ selectedRaceResults.first }}</h4>
          <h4>第二名: {{ selectedRaceResults.second }}</h4>
          <h4>第三名: {{ selectedRaceResults.third }}</h4>
        </div>
        <div v-else>
          <p>暂无结果</p>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';

export default {
    name:"EventComponent",
    data() {
      return {
        tableData: null,
        dialogVisible: false,
        selectedRace: null,
        selectedRaceResults: null
      }
    },
    
    mounted(){
        this.getData();
    },
    methods: {
        getData() {
        axios.post('http://127.0.0.1:80/api/userInfo/sport/Race').then(response => {
            this.tableData = response.data.map(item => {
            switch(item.Status) {
                case 1:
                item.Status = '报名中';
                break;
                case 2:
                item.Status = '进行中';
                break;
                case 0:
                item.Status = '已结束';
                break;
            }
            item.SignUpInfo = item.Total + '/' + item.Number;
            return item;
        });
        console.log(this.tableData)

        }).catch(error => {
          console.error('获取数据时出错:', error);
        });
      },
      
      formatter(row) {
        return row.RaceMessage;
      },
      filterTag(value, row) {
        return row.Status === value;
      },
      filterHandler(value, row, column) {
        const property = column['property'];
        return row[property] === value;
      },
      signUp(value){
        const selectedEvent = this.tableData.find(item => item.Id === value);
        if (selectedEvent.Number === selectedEvent.Total) {
          this.$message.error('人数已满，无法预约');
          return;
        }
        const Info={
          eventId:value,
          userId:this.$store.state.userName
        }
        axios.post('http://127.0.0.1:80/api/userInfo/sport/Race/BaoMing', Info, {
        headers: {
          'Content-Type': 'application/json'
        }}).then(response=> {
        this.responseData = response.data;
        if (this.responseData === 1) {
          this.$message.success('报名成功');
        }else{
          this.$message.error('报名失败，请稍后重试');
        }
        })
        .catch(error => {
          this.$message.error('网络问题:',error);
        });
      },
      viewResults(raceName) {
        this.selectedRace = this.tableData.find(item => item.RaceName === raceName);
        const requestData = { raceName: raceName }; // 构造要发送的 JSON 格式数据
        axios.post('http://127.0.0.1:80/api/UserInfo/race/results', requestData, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          .then(response => {
            this.selectedRaceResults = response.data; // 直接赋值给 selectedRaceResults
            this.dialogVisible = true;
          })
          .catch(error => {
            console.error('获取比赛结果时出错:', error);
            this.$message.error('获取比赛结果时出错，请稍后重试');
          });
      },

      }
    }


</script>

<style>

</style>