<template>
<div>
  <div style="padding: 10px 0">
    <el-input style="width: 200px" placeholder="请输入查询内容"
              suffix-icon="el-icon-search" v-model="keyWord">
    </el-input>
    <el-button style="margin-left: 5px;"  type="primary" @click="load">搜索</el-button>
  </div>
  <el-button type="primary" @click="handleAdd">添加用户</el-button>
  <el-popconfirm
      class="ml-5"
      title="确定删除吗？"
      @confirm="delBatch"
  >
    <el-button type="danger" slot="reference" plain >批量删除</el-button>
  </el-popconfirm>
  <el-table v-loading="loading" :data="tableData" stripe @selection-change="handleSelectionChange" style="margin-top: 10px">
    <el-table-column type="selection" width="55" ></el-table-column>
    <el-table-column prop="userNum" label="学号" width="140"></el-table-column>
    <el-table-column prop="userName" label="姓名" width="120"></el-table-column>
    <el-table-column prop="collegeNum" label="学院" width="120"></el-table-column>
    <el-table-column prop="sex" label="性别" width="120"></el-table-column>
    <el-table-column prop="telephone" label="电话"></el-table-column>
    <el-table-column
        fixed="right"
        label="操作"
        width="100">
      <template slot-scope="scope">
        <el-button slot="reference" type="text" size="small" @click="openDialogCheck(scope.row.userNum)">查看</el-button>
        <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
      </template>
    </el-table-column>
  </el-table>
  <div class="block" style="padding: 10px 0">
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :page-sizes="[5, 10, 15, 20]"
        :current-page="pageNum"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
  </div>

  <el-dialog title="添加用户" :visible.sync="dialogFormVisible">
    <el-form :model="form" label-width="80px" size="small">
      <el-form-item label="用户名">
        <el-input v-model="form.userNum" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.password" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="form.userName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="类型">
        <el-input v-model="form.userType" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>

  <el-dialog title="查看打卡记录" :visible.sync="checkDialogFormVisible">
    <el-table :data="checkData" stripe >
      <el-table-column prop="checkTime" label="打卡时间" width="170"></el-table-column>
      <el-table-column prop="name" label="打卡地点"></el-table-column>
    </el-table>
    <div class="block" style="padding: 10px 0">
      <el-pagination
          @size-change="checkHandleSizeChange"
          @current-change="checkHandleCurrentChange"
          :page-sizes="[5, 10, 15, 20]"
          :current-page="checkPageNum"
          :page-size="checkPageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="checkTotal">
      </el-pagination>
    </div>

    <div slot="footer" class="dialog-footer">
      <el-button @click="checkDialogCancel">取 消</el-button>
      <el-button type="primary" @click="checkDialogCancel" class="ml-5">确 定</el-button>
    </div>
  </el-dialog>
</div>
</template>

<script>
export default {
  name: "Users",
  data() {
    return {
      multipleSelection: [],
      tableData: [],
      checkData: [],
      userNum: "",
      pageNum: 1,
      pageSize: 5,
      checkPageNum: 1,
      checkPageSize: 5,
      keyWord: "",
      total: 0,
      checkTotal: 0,
      dialogFormVisible: false,
      checkDialogFormVisible: false,
      form: {
        userNum: "",
        password: "",
        userName: "",
        collegeNum: "",
        sex: 0,
        telephone: "",
        userType: "",
      },
      college: [
        "ADMIN",
        "信息学院",
        "艺术学院",
        "生化学院",
        "电气学院",
        "经管学院",
      ],
      loading: true,
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.loading = true
      this.request.get("/user/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          keyWord: this.keyWord
        }
      }).then(res => {
        console.log(res.data.records)
        this.tableData = this.trim(res.data.records)
        this.total = res.data.total
        this.loading = false
      })
    },
    save() {
      this.request.post("/user", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("success")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("no!")
        }
      })
    },
    del(id) {
      this.request.delete("/user/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("success")
          this.load()
        } else {
          this.$message.error("no!")
        }
      })
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.userNum)
      this.request.post("/user/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("success")
          this.load()
        } else {
          this.$message.error("no!")
        }
      })
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    handleEdit(row) {
      this.form = row
      this.dialogFormVisible = true
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    checkDialogCancel() {
      this.checkDialogFormVisible = false
    },
    openDialogCheck(userNum) {
      this.checkDialogFormVisible = true
      this.userNum = userNum
      this.checkLoad()
    },
    checkHandleSizeChange(pageSize) {
      this.checkPageSize = pageSize
      this.checkLoad()
    },
    checkHandleCurrentChange(pageNum) {
      this.checkPageNum = pageNum
      this.checkLoad()
    },
    getSex(sex) {
      return sex ? "男":"女"
    },
    getCollege(cid) {
      return this.college[cid]
    },
    trim(data) {
      for (let i in data) {
        data[i].sex = this.getSex(data[i].sex)
        data[i].collegeNum = this.getCollege(data[i].collegeNum)
      }
      return data
    },
    checkLoad() {
      const that = this
      const uid = this.userNum
      this.request.get("/position/record/u/" + uid, {
        params: {
          pageNum: that.checkPageNum,
          pageSize: that.checkPageSize,
        }
      }).then(res => {
        if (res.code === '200') {
          let data = res.data.records
          that.checkTotal = res.data.total
          let tableData = []
          for(let i in data) {
            let pid = data[i].pid
            this.request.get("/position/" + pid).then(res => {
              if (res.code === '200') {
                let position = res.data
                let obj = {
                  checkTime: data[i].checkTime,
                  name: position.name
                }
                tableData.push(obj)
              } else {
                this.$message.error(res.msg)
              }
            })
          }
          this.checkData = tableData
        } else {
          this.$message.error(res.msg)
          return false
        }
      })
    }
  }
}
</script>

<style scoped>

</style>