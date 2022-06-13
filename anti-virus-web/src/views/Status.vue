<template>
<div>
  <div class="state" style="width: 50%">
    <div class="wrapper bgcolor-yellow">
      <div class="info-box">
        <div class="sub-title">
          <h1>{{ riskNum }}</h1>
          风险人数
        </div>
      </div>
      <div class="bottom-link bgcolor-darkyellow">
        <a href="#" class="btn" @click="getAllRiskUser">
          <span style="display: none" class="url"></span>
          More info <i class="fa fa-arrow-right"></i>
        </a>
      </div>
    </div>

    <div class="wrapper bgcolor-red">
      <div class="info-box">
        <div class="sub-title">
          <h1>{{ dangerNum }}</h1>
          高危人数
        </div>
      </div>
      <div class="bottom-link bgcolor-darkred">
        <a href="#" class="btn" @click="getAllDangerUser">
          <span style="display: none" class="url"></span>
          More info <i class="fa fa-arrow-right"></i>
        </a>
      </div>
    </div>
  </div>

  <el-button type="danger" style="height: 40px">发送核酸检测提醒</el-button>
  <el-button type="primary" style="height: 40px" @click="importDialog">导入核酸检测信息</el-button>
  <el-table v-loading="loading" :data="tableData" stripe @selection-change="handleSelectionChange" style="margin-top: 20px">
    <el-table-column type="selection" width="55" ></el-table-column>
    <el-table-column prop="userNum" label="学号" width="140"></el-table-column>
    <el-table-column prop="userName" label="姓名" width="120"></el-table-column>
    <el-table-column prop="collegeNum" label="学院" width="120"></el-table-column>
    <el-table-column prop="sex" label="性别" width="120"></el-table-column>
    <el-table-column prop="telephone" label="电话"></el-table-column>
  </el-table>

<!--  导入核酸检测信息-->
  <el-dialog title="添加核酸报告" :visible.sync="dialogFormVisible">
    <el-form :model="form" label-width="80px" size="small">
      <el-form-item label="学号">
        <el-input v-model="form.userNum" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="检测结果">
        <el-select v-model="form.state" placeholder="请选择" @change="itemOnChange($event)">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="importCancel">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>

</div>
</template>

<script>
export default {
  name: "State",
  data() {
    return {
      dialogFormVisible: false,
      form: {},
      tableData: [],
      userNum: "",
      riskNum: "",
      dangerNum: "",
      loading: false,
      options: [
        {
          value: "0",
          label: "阴性"
        },
        {
          value: "1",
          label: "阳性"
        }
      ],
      college: [
          "ADMIN",
          "信息学院",
          "艺术学院",
          "生化学院",
          "电气学院",
          "经管学院",
      ]
    }
  },
  created() {
    this.getDangerUserNum()
    this.getRiskUserNum()
  },
  methods: {
    importDialog() {
      this.dialogFormVisible = true
    },
    /**
     * 提交检测结果
     * uri:/state
     * method: GET
     */
    save() {
      this.request.post("/status", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("添加成功")
          this.getDangerUserNum()
          this.getRiskUserNum()
          this.importCancel()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    itemOnChange(result) {
      this.form.state = result
    },
    getDangerUserNum() {
      let collegeNum = JSON.parse(localStorage.getItem("user")).collegeNum
      this.request.get("/status/danger/" + collegeNum).then(res => {
        if (res.code === '200') {
          this.dangerNum = res.data
        } else {
          this.$message.error(res.msg)
        }

      })
    },
    getRiskUserNum() {
      let collegeNum = JSON.parse(localStorage.getItem("user")).collegeNum
      this.request.get("status/risk/"+ collegeNum).then(res => {
        if (res.code === '200') {
          this.riskNum = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    getAllRiskUser() {
      this.loading = true
      let collegeNum = JSON.parse(localStorage.getItem("user")).collegeNum
      this.request.get("status/risk/"+collegeNum+"/all/").then(res => {
        if (res.code === '200') {
          console.log(res.data)
          this.tableData = this.trim(res.data)
        } else {
          this.$message.error(res.msg)
        }
        this.loading = false
      })
    },
    getAllDangerUser() {
      this.loading = true
      let collegeNum = JSON.parse(localStorage.getItem("user")).collegeNum
      this.request.get("status/danger/"+collegeNum+"/all/").then(res => {
        if (res.code === '200') {
          console.log(res.data)
          this.tableData = this.trim(res.data)
        } else {
          this.$message.error(res.msg)
        }
        this.loading = false
      })
    },
    handleSelectionChange() {

    },
    importCancel() {
      this.dialogFormVisible = false
      this.form = {userNum: "", state: ""}
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
    }
  }
}
</script>

<style scoped>
.wrapper {
  margin-top: 0.7rem;
  font-weight: 300;
  padding: 1rem 1rem 0 1rem;
  width: 16rem;
  height: 8rem;
  display: flex;
  flex-flow: column nowrap;
  justify-content: space-between;
  border-radius: 0.5rem;
  color: #FFF;
  box-shadow: 0 0.2rem 0.1rem 0 #ccc;
}

.sub-title h1 {
  font-size: 30px;
}

.bottom-link {
  margin: 0 -1rem 0 -1rem;
  height: 2rem;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 0 0 0.5rem 0.5rem;
}

.wrapper .btn {
  text-decoration: none;
  color: #FFF;
}

.state {
  margin: 1rem auto;
  padding: 1rem;
  display: flex;
  flex-flow: row wrap;
  justify-content: space-between;
  flex-shrink: 1;
}

.bgcolor-blue {
  background: #3D84B8;
}

.bgcolor-darkblue {
  background: #344FA1;
}

.bgcolor-green {
  background: #5E8B7E;
}

.bgcolor-darkgreen {
  background: #2F5D62;
}

.bgcolor-red {
  background: #FF4646;
}

.bgcolor-darkred {
  background: #E40017;
}

.bgcolor-yellow {
  background: #FFA900;
}

.bgcolor-darkyellow {
  background: #FF7600;
}
</style>