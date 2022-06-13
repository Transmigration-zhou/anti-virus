<template>
  <div>
    <el-table v-loading="loading" :data="tableData" stripe style="margin-top: 10px">
      <el-table-column type="selection" width="55" ></el-table-column>
      <el-table-column prop="userNum" label="学号" width="120"></el-table-column>
      <el-table-column prop="startTime" label="开始时间" width="150"></el-table-column>
      <el-table-column prop="endTime" label="结束时间" width="150"></el-table-column>
      <el-table-column prop="requestTime" label="申请时间" width="150"></el-table-column>
      <el-table-column prop="reason" label="申请理由" width="120"></el-table-column>
      <el-table-column prop="way" label="出行方式" width="120"></el-table-column>
      <el-table-column prop="state" label="状态"></el-table-column>
      <el-table-column
          label="操作"
          width="100">
        <template slot-scope="scope" v-if="scope.row.state === '申请中'">
          <el-button slot="reference" type="text" size="small" @click="refuseChoice(scope.row.vacationNum)">拒绝</el-button>
          <el-button type="text" size="small" @click="acceptChoice(scope.row.vacationNum)">同意</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: "VacationManage",
  data() {
    return {
      tableData: [],
      loading: true,
      pageNum: 1,
      pageSize: 5,
      total: 0,
      userName: "",
      state: [
        "申请中",
        "已拒绝",
        "已通过",
      ],
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
    load() {
      this.loading = true
      let collegeNum = JSON.parse(localStorage.getItem("user")).collegeNum
      this.request.get("/vacation/" + collegeNum).then(res => {
        if (res.code === '200') {
          this.tableData = this.trim(res.data)
        } else {
          this.$message.error(res.msg)
        }
      }).then(() => {
        this.loading = false
      })
    },
    refuseChoice(vacationNum) {
      this.request.put("/vacation/refuse/" + vacationNum).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功")
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    acceptChoice(vacationNum) {
      this.request.put("/vacation/accept/" + vacationNum).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功")
          this.load()
        } else {
          this.$message.error(res.msg)
        }
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