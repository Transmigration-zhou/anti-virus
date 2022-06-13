<template>
  <div>
    <el-button style="margin-bottom: 10px" @click="handleAdd">申请离校</el-button>
    <el-table v-loading="loading" :data="tableData" stripe style="margin-top: 10px">
      <el-table-column type="selection" width="55" ></el-table-column>
      <el-table-column prop="startTime" label="开始时间" width="150"></el-table-column>
      <el-table-column prop="endTime" label="结束时间" width="150"></el-table-column>
      <el-table-column prop="requestTime" label="申请时间" width="150"></el-table-column>
      <el-table-column prop="reason" label="申请理由" width="120"></el-table-column>
      <el-table-column prop="way" label="出行方式" width="120"></el-table-column>
      <el-table-column prop="state" label="状态"></el-table-column>
      <el-table-column
          label="操作"
          width="100">
        <template slot-scope="scope">
          <el-popconfirm
              class="mr-5"
              title="确定撤回吗？"
              @confirm="revokeVacation(scope.row.vacationNum)"
              v-if="scope.row.state === '申请中'"
          >
            <el-button slot="reference" type="text" size="small">撤回</el-button>
          </el-popconfirm>
          <el-button type="text" size="small" v-if="scope.row.state === ''">销假</el-button>
        </template>
      </el-table-column>
    </el-table>
<!--    <div class="block" style="padding: 10px 0">-->
<!--      <el-pagination-->
<!--          @size-change="handleSizeChange"-->
<!--          @current-change="handleCurrentChange"-->
<!--          :page-sizes="[5, 10, 15, 20]"-->
<!--          :current-page="pageNum"-->
<!--          :page-size="pageSize"-->
<!--          layout="total, sizes, prev, pager, next, jumper"-->
<!--          :total="total">-->
<!--      </el-pagination>-->
<!--    </div>-->

    <el-dialog title="申请离校" :visible.sync="dialogFormVisible">
      <el-form :model="form" label-width="80px" size="small" :rules="rules">
        <el-form-item label="申请理由" prop="reason">
          <el-input v-model="form.reason" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="出行方式" prop="way">
          <el-input v-model="form.way" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="开始时间">
          <el-col :span="11">
            <el-date-picker type="datetime" placeholder="选择日期时间" v-model="form.startTime" style="width: 100%;"></el-date-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="结束时间">
          <el-col :span="11">
            <el-date-picker type="datetime" placeholder="选择日期时间" v-model="form.endTime" style="width: 100%;"></el-date-picker>
          </el-col>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveVacation">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "vacation",
  data() {
    return {
      tableData: [],
      pageNum: 1,
      pageSize: 5,
      total: 0,
      loading: true,
      dialogFormVisible: false,
      state: [
          "申请中",
          "已拒绝",
          "已通过",
      ],
      form: {
        reason: "",
        way: "",
        startTime: "",
        endTime: "",
      },
      rules: {
        reason: [
          { required: true, message: '请输入申请理由', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        way: [
          { required: true, message: '请输入出行方式', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      // this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      // this.load()
    },
    handleAdd() {
      this.dialogFormVisible = true
    },
    saveVacation() {
      this.form.userNum = JSON.parse(localStorage.getItem("user")).userNum
      this.request.post("/vacation", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("添加成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    revokeVacation(vacationNum) {
      console.log(vacationNum)
      this.request.delete("/vacation/" + vacationNum).then(res => {
        if (res.code === '200') {
          this.$message.success("撤回成功")
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    load() {
      this.loading = true
      let userNum = JSON.parse(localStorage.getItem("user")).userNum
      this.request.get("/vacation/user/" + userNum).then(res => {
        if (res.code === '200') {
          this.tableData = this.trim(res.data)
        } else {
          this.$message.error(res.msg)
        }
        this.loading = false
      })
    },
    getState(state) {
      return this.state[state]
    },
    trim(data) {
      for (let i in data) {
        data[i].state = this.getState(data[i].state)
      }
      return data
    }
  }
}
</script>

<style scoped>

</style>