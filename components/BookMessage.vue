<template>
  <el-container>
    <el-aside width="120px"></el-aside>
    <el-container style="width: 100%;">
      <el-header style="width: 100%;">
        <div class="search-container">
          <i class="el-icon-search" :style="{ 'font-size': '24px' }"></i>
          <el-input placeholder="请输入内容" v-model="searchTerm" clearable :style="{ width: '400px', margin: '7px' }"></el-input>
          <el-button type="primary" @click="search(Userid)">搜索</el-button>
        </div>
      </el-header>
      <el-container style="width: 100%;">
        <div @keyup.enter="search" style="width: 100%;">
          <el-table :data="users" stripe style="width: 100%;">
            <el-table-column prop="type" label="场地类型" width="180"></el-table-column>
            <el-table-column prop="sportId" label="场地号" width="180"></el-table-column>
            <el-table-column prop="Userid" label="姓名" width="180"></el-table-column>
            <el-table-column label="预约时段">
              <template slot-scope="scope">
                {{ timeMapping[scope.row.time] }}
              </template>
            </el-table-column>
            <el-table-column label="状态" width="100">
              <template slot-scope="scope">
                <el-tag :type="scope.row.isDisabled ? 'danger' : 'success'">
                  {{ scope.row.isDisabled ? '禁用' : '正常' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <div v-if="!scope.row.isPaid">
                  <el-popconfirm
                    title="确认支付此项？"
                    @confirm="handleEdit(scope.$index, scope.row)"
                  >
                    <template #reference>
                      <el-button size="mini">确认支付</el-button>
                    </template>
                  </el-popconfirm>
                </div>
                <el-button v-else size="mini" type="success" disabled>已支付</el-button>
                <el-popconfirm
                  title="确认禁用此账号？"
                  @confirm="handleDisable(scope.$index, scope.row)"
                >
                  <template #reference>
                    <el-button size="mini" type="danger">禁用账号</el-button>
                  </template>
                </el-popconfirm>
              </template>
            </el-table-column>
          </el-table>

          <div v-if="errorMessage" style="color: red;">{{ errorMessage }}</div>

          <el-button class="send-button" type="primary" plain @click="showConfirmDialog">一键清除当日数据</el-button>

          <el-dialog title="确认" :visible.sync="dialogVisible" width="30%" center>
            <span>是否确认清除当日数据？</span>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible = false">取消</el-button>
              <el-button type="primary" @click="clearData">确定</el-button>
            </div>
          </el-dialog>
        </div>
      </el-container>
    </el-container>
    <el-aside width="120px"></el-aside>
  </el-container>
</template>

<script>
import axios from 'axios';

export default {
  name: "BookMessageComponent",
  data() {
    return {
      searchTerm: '',
      users: [],
      dialogVisible: false,
      errorMessage: '',
      timeMapping: {
        '8': '8:00-9:00',
        '9': '9:00-10:00',
        '10': '10:00-11:00',
        '11': '11:00-12:00',
        '12': '12:00-13:00',
        '13': '13:00-14:00',
        '14': '14:00-15:00',
        '15': '15:00-16:00',
        '16': '16:00-17:00',
        '17': '17:00-18:00',
        '18': '18:00-19:00',
        '19': '19:00-20:00'
      }
    };
  },
  mounted() {
    this.fetchAllUsers();
  },
  methods: {
    handleEdit(index, row) {
      const paymentData = {
        type: row.type,
        sportId: row.sportId,
        Userid: row.Userid,
        time: row.time
      };

      axios.post('http://localhost:80/api/admin/payment', paymentData)
        .then(response => {
          if (response.data === 1) {
            this.$set(this.users, index, {
              ...row,
              isPaid: true
            });
            this.$message({
              type: 'success',
              message: '支付成功'
            });
          } else {
            this.$message.error('支付失败，请重试');
          }
        })
        .catch(error => {
          console.error('支付请求失败:', error);
          this.$message.error('支付请求失败，请稍后重试');
        });
    },

    handleDisable(index, row) {
      const disableData = {
        Userid: row.Userid
      };

      axios.post('http://localhost:80/api/admin/disable', disableData)
        .then(response => {
          if (response.data === 1) {
            this.$set(this.users, index, {
              ...row,
              isDisabled: true
            });
            this.$message({
              type: 'success',
              message: '账号已禁用'
            });
          } else {
            this.$message.error('禁用失败，请重试');
          }
        })
        .catch(error => {
          console.error('禁用请求失败:', error);
          this.$message.error('禁用请求失败，请稍后重试');
        });
    },

    /*fetchAllUsers() {
      axios.get('http://localhost:80/api/admin/all-users')
        .then(response => {
          console.log(response.data);
          this.users = response.data.map(user => user);
          this.fetchDisabledStatus();
        })
        .catch(error => {
          console.error('获取用户数据失败:', error);
          this.errorMessage = '获取用户数据失败，请稍后重试。';
          this.users = [];
        });
    },*/

    fetchDisabledStatus(Userid) {
      const UserId = { UserId: Userid };
      console.log("@@@",UserId)
      axios.post('http://localhost:80/api/admin/disabled-status', UserId)
        .then(response => {
          console.log(response.data)
          const disabledStatus = response.data;
          this.users = this.users.map(user => ({
            ...user,
            isDisabled: disabledStatus[user.Userid] === 0,
            
          }));
          console.log(this.users.isDisabled)
          this.errorMessage = '';
        })
        .catch(error => {
          console.error('获取禁用状态失败:', error);
          this.errorMessage = '获取禁用状态失败，请稍后重试。';
        });
    },


    search() {
      const searchData = {
        UserId: this.searchTerm
      };

      axios.post('http://localhost:80/api/admin/sport', searchData)
        .then(response => {
          console.log(response.data);
          this.users = response.data.map(user => user);
          this.fetchDisabledStatus(this.searchTerm);
        })
        .catch(error => {
          console.error('未搜索到该用户:', error);
          this.errorMessage = '未搜索到用户，请稍后重试。';
          this.users = [];
        });
    },

    showConfirmDialog() {
      this.dialogVisible = true;
    },

    clearData() {
      const deleteData = { delete: true };

      axios.post('http://localhost:80/api/admin/delete', JSON.stringify(deleteData))
        .then(response => {
          if (response.data === 1) {
            console.log('数据已清除');
            this.$message({
              type: 'success',
              message: '数据已清除'
            });
          } else {
            console.error('清除数据失败');
            this.$message.error('清除数据失败');
          }
          this.dialogVisible = false;
        })
        .catch(error => {
          console.error('清除数据失败:', error);
          this.$message.error('清除数据失败');
        });
    }
  }
};
</script>

<style scoped>
.el-container, .el-header, .el-table {
  width: 100%;
}

.search-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.send-button {
  position: absolute;
  bottom: 10px;
  right: 10px;
}

.el-header, .el-footer {
  background-color: #f0f3f5;
  color: #444444;
  text-align: center;
  line-height: 60px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  line-height: 160px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>
