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
            width="180">
          </el-table-column>
          <el-table-column
            prop="RaceName"
            label="赛事名称"
            width="180">
          </el-table-column>
          <el-table-column
            prop="RaceMessage"
            label="赛事信息">
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
            width="220">
          </el-table-column>
          <el-table-column label="操作" width="240">
            <template slot-scope="scope">
              <el-button @click="editRace(scope.row)">编辑</el-button>
              <el-button @click="deleteRace(scope.row.RaceName)">删除</el-button>
              <el-button @click="submitResult(scope.row)">提交比赛结果</el-button> <!-- 在这里添加 -->
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
    <div class="add-event-button">
      <el-button class="send-button" type="primary" plain @click="dialogVisible = true">添加赛事</el-button>
    </div>
    <el-dialog :visible.sync="dialogVisible" title="添加赛事">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px" class="demo-ruleForm">
        <el-form-item label="赛事开始日期" prop="Time">
          <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.Time" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="赛事名称" prop="RaceName">
          <el-input v-model="ruleForm.RaceName"></el-input>
        </el-form-item>
        <el-form-item label="赛事信息" prop="RaceMessage">
          <el-input v-model="ruleForm.RaceMessage"></el-input>
        </el-form-item>
        <el-form-item label="最大报名人数" prop="Number">
          <el-input v-model.number="ruleForm.Number" type="number" placeholder="请输入最大报名人数"></el-input>
        </el-form-item>
        <el-form-item label="发起人" prop="Boss">
          <el-input v-model="ruleForm.Boss"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog :visible.sync="editDialogVisible" title="编辑赛事">
    <el-form :model="editForm" :rules="editRules" ref="editForm" label-width="120px" class="edit-form">
      <!-- <el-form-item label="赛事开始日期" prop="Time">
        <el-date-picker type="date" placeholder="选择日期" v-model="editForm.Time" style="width: 100%;"></el-date-picker>
      </el-form-item> -->
      <el-form-item label="赛事名称">
        <el-input v-model="editForm.RaceName" disabled></el-input>
      </el-form-item>
      <el-form-item label="赛事信息" prop="RaceMessage">
        <el-input v-model="editForm.RaceMessage"></el-input>
      </el-form-item>
      <el-form-item label="最大报名人数" prop="Number">
        <el-input v-model.number="editForm.Number" type="number" placeholder="请输入最大报名人数"></el-input>
      </el-form-item>
      <el-form-item label="发起人" prop="Boss">
        <el-input v-model="editForm.Boss"></el-input>
      </el-form-item>
      <el-form-item label="赛事状态" prop="Status">
        <el-button-group>
          <el-button :type="editForm.Status === '报名中' ? 'primary' : ''" @click="editForm.Status = '1'">报名中</el-button>
          <el-button :type="editForm.Status === '进行中' ? 'primary' : ''" @click="editForm.Status = '2'">进行中</el-button>
          <el-button :type="editForm.Status === '已结束' ? 'primary' : ''" @click="editForm.Status = '0'">已结束</el-button>
        </el-button-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitEditForm('editForm')">保存修改</el-button>
        <el-button @click="cancelEditForm">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
  <el-dialog :visible.sync="resultDialogVisible" title="填写比赛结果">
  <el-form :model="resultForm" :rules="resultRules" ref="resultForm" label-width="120px" class="result-form">
    <el-form-item label="第一名" prop="firstPlace">
      <el-input v-model="resultForm.firstPlace"></el-input>
    </el-form-item>
    <el-form-item label="第二名" prop="secondPlace">
      <el-input v-model="resultForm.secondPlace"></el-input>
    </el-form-item>
    <el-form-item label="第三名" prop="thirdPlace">
      <el-input v-model="resultForm.thirdPlace"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitResultForm">提交</el-button>
      <el-button @click="cancelResultForm">取消</el-button>
    </el-form-item>
  </el-form>
</el-dialog>
  </div>
  
</template>

<script>
import axios from 'axios';
export default {
  name: 'AdminEventComponent',
  data() {
    return {
      tableData: null,
      dialogVisible: false,
      
      ruleForm: {
        Time: '',
        RaceName: '',
        RaceMessage: '',
        Number: '',
        Boss: '',
        Status: 1
      },
      
      rules: {
        Time: [
          { type: 'date', required: true, message: '请选择赛事开始日期', trigger: 'change' }
        ],
        RaceName: [
          { required: true, message: '请输入赛事名称', trigger: 'blur' }
        ],
        RaceMessage: [
          { required: true, message: '请输入赛事信息', trigger: 'blur' }
        ],
        Number: [
          { type: 'number', required: true, message: '请输入最大报名人数', trigger: 'blur' }
        ],
        Boss: [
          { required: true, message: '请输入发起人', trigger: 'blur' }
        ]
      },
      editDialogVisible: false,
      editForm: {
        Time: '',
        RaceName: '', //编辑时此处将被填充
        RaceMessage: '',
        Number: '',
        Boss: '',
        Status: ''
      },
      editRules: {
        Time: [
          { type: 'date', required: true, message: '请选择赛事开始日期', trigger: 'change' }
        ],
        RaceMessage: [
          { required: true, message: '请输入赛事信息', trigger: 'blur' }
        ],
        Number: [
          { type: 'number', required: true, message: '请输入最大报名人数', trigger: 'blur' }
        ],
        Boss: [
          { required: true, message: '请输入发起人', trigger: 'blur' }
        ],
        Status: [
          { required: true, message: '请选择赛事状态', trigger: 'change' }
        ]
      },
      resultDialogVisible: false,
      resultForm: {
        firstPlace: '',
        secondPlace: '',
        thirdPlace: ''
      },
      resultRules: {
        firstPlace: [
          { required: true, message: '请输入第一名', trigger: 'blur' }
        ],
        secondPlace: [
          { required: true, message: '请输入第二名', trigger: 'blur' }
        ],
        thirdPlace: [
          { required: true, message: '请输入第三名', trigger: 'blur' }
        ]
      }


    };
  },

  mounted() {
    this.getData();
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 将表单数据发送到后端进行保存
          this.saveEvent();
        } else {
          console.log('表单验证失败');
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    saveEvent() {
      // 将表单数据发送到后端进行保存
      const Info = {
        Data: {
          Time: this.ruleForm.Time,
          RaceName: this.ruleForm.RaceName,
          RaceMessage: this.ruleForm.RaceMessage,
          Number: this.ruleForm.Number,
          Boss: this.ruleForm.Boss,
          Status: this.ruleForm.Status
        }
      }
      // 在这里编写发送数据到后端的逻辑
      console.log("发送的数据：", Info)
      axios.post('http://127.0.0.1:80/api/admin/insert/race', Info.Data).then(response => {
        if (response.data == null) console.error('发送数据失败')
      })
      console.log('保存赛事:', Info.Data);
      // 关闭对话框
      this.dialogVisible = false;
      // 清空表单
      this.resetForm('ruleForm');
    },
    editRace(row) {
      this.editForm = { ...row }; //用行数据填充表单
      this.editDialogVisible = true; //打开编辑对话框
    },

    //提交编辑后的赛事详情
    submitEditForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //发送编辑后的赛事详情至后端
          this.updateRace();
        } else {
          console.log('表单验证失败');
        }
      });
    },

    //发送编辑后的赛事详情至后端
    updateRace() {
      const updatedInfo = {
        Data: {
          Time: this.editForm.Time,
          RaceName: this.editForm.RaceName, //为确保标识唯一性，赛事名称保持不变
          RaceMessage: this.editForm.RaceMessage,
          Number: this.editForm.Number,
          Boss: this.editForm.Boss,
          Status: this.editForm.Status
        }
      };  
      console.log('123465',updatedInfo.Data)

      axios.post('http://127.0.0.1:80/api/admin/update/race', updatedInfo.Data)
        .then(response => {
          if (response.data === 1) {
            this.$message.success('赛事修改成功');
            this.getData(); //成功更新后刷新表格数据
          } else {
            this.$message.error('赛事修改失败');
          }
        })
        .catch(error => {
          this.$message.error('网络问题:', error);
        });

      //关闭编辑对话框并重置表单
      this.editDialogVisible = false;
      this.$refs.editForm.resetFields();
    },

    //取消编辑并关闭编辑对话框
    cancelEditForm() {
      this.editDialogVisible = false;
      this.$refs.editForm.resetFields();
    },
  
    deleteRace(RaceName) {
      // 弹出确认对话框，确认是否删除
      this.$confirm('确定删除该赛事吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log(RaceName)
        // 确认删除，向后端发送删除请求
        axios.post('http://127.0.0.1:80/api/admin/delete/race', { RaceName: RaceName })
          .then(response => {
            if (response.data === 1) {
              this.$message.success('赛事删除成功');
              this.getData();
            } else {
              this.$message.error('赛事删除失败');
            }
          })
          .catch(error => {
            this.$message.error('网络问题:', error);
          });
      }).catch(() => {
        // 取消删除
        this.$message.info('已取消删除');
      });
    },
    submitResult(row) {
      // 用选定比赛的数据填充resultForm
      this.resultForm = {
        first: '',
        second: '',
        third: '',
        RaceName: row.RaceName // 假设你的赛事数据中有一个ID字段
      };
      // 打开结果对话框
      this.resultDialogVisible = true;
    },
    submitResultForm() {
      this.$refs.resultForm.validate((valid) => {
        if (valid) {
          // 创建一个数据对象发送到后端
          const resultData = {
            first: this.resultForm.firstPlace,
            second: this.resultForm.secondPlace,
            third: this.resultForm.thirdPlace,
            RaceName: this.resultForm.RaceName// 将赛事ID与结果一起传递
          };

          // 使用Axios发送数据到后端
          axios.post('http://127.0.0.1:80/api/adminInfo/race/result', resultData)
            .then(response => {
              console.log('服务器响应:', response.data);
              this.$message.success('比赛结果提交成功');
              this.resultDialogVisible = false;
              // 可选地在此处重置表单
              // this.$refs.resultForm.resetFields();
            })
            .catch(error => {
              console.error('提交结果时出错:', error);
              this.$message.error('比赛结果提交失败');
            });
        } else {
          console.log('表单验证失败');
        }
      });
    },
    cancelResultForm() {
      // 取消填写比赛结果并关闭对话框
      this.resultDialogVisible = false;
      // 可选地在此处重置表单数据
      // this.$refs.resultForm.resetFields();
    },
    
    getData() {
      axios.post('http://127.0.0.1:80/api/userInfo/sport/Race').then(response => {
        this.tableData = response.data.map(item => {
          item.Time = new Date(item.Time).toLocaleDateString();
          switch (item.Status) {
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
    filterTag(value, row) {
      return row.Status === value;
    },
    filterHandler(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    },
  }
}
</script>

<style scoped>
.add-event-button {
  position: fixed;
  bottom: 20px; /* 根据需要调整 */
  right: 20px; /* 根据需要调整 */
}
</style>
