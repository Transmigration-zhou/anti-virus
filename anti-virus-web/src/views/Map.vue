<template>
  <div>
    <el-button type="primary" @click="positionAdd" style="margin-bottom: 5px">添加点位</el-button>
    <div id="container" style="width: 100%; height: calc(100vh - 120px)" ></div>
    <el-dialog title="添加打卡点" :visible.sync="addDialogFormVisible">
      <el-form :model="form" label-width="80px" size="small">
        <el-form-item label="经度">
          <el-input v-model="form.longitude" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="维度">
          <el-input v-model="form.latitude" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="简介">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="编辑打卡点" :visible.sync="modifyDialogFormVisible">
      <el-form :model="form" label-width="80px" size="small">
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="简介">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>

      <el-table v-loading="loading" :data="tableData" stripe >
        <el-table-column prop="checkTime" label="打卡时间" width="150"></el-table-column>
        <el-table-column prop="userNum" label="学号" width="80"></el-table-column>
        <el-table-column prop="userName" label="姓名" width="80"></el-table-column>
<!--        <el-table-column prop="collegeNum" label="学院" width="80"></el-table-column>-->
<!--        <el-table-column prop="sex" label="性别" width="75"></el-table-column>-->
        <el-table-column prop="telephone" label="电话"></el-table-column>
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


      <div slot="footer" class="dialog-footer">
        <el-button @click="modifyCancel">取 消</el-button>
        <el-popconfirm
            class="ml-5"
            title="确定删除吗？"
            @confirm="deletePosition"
        >
          <el-button type="danger" plain slot="reference" >删除</el-button>
        </el-popconfirm>
<!--        <el-button type="danger" plain @click="deletePosition">删除</el-button>-->
        <el-button type="primary" @click="modifySave" class="ml-5">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Map",
  data() {
    return {
      form: {
        isOk: 0,
        pid: 0,
        latitude: 0,
        longitude: 0,
        name: "",
        description: "",
        map: {},
      },
      pageNum: 1,
      pageSize: 5,
      total: 0,
      tableData: [],
      points: [],
      addDialogFormVisible: false,
      modifyDialogFormVisible: false,
      user: {},
      pid: "",
      loading: true,
      college: [
        "ADMIN",
        "信息学院",
        "艺术学院",
        "生化学院",
        "电气学院",
        "经管学院",
      ],
    }
  },
  mounted() {
    const that = this
    let map = new BMapGL.Map('container') // 创建Map实例
    this.map = map
    map.centerAndZoom(new BMapGL.Point(120.03231, 30.22491), 17) // 初始化地图,设置中心点坐标和地图级别
    map.enableScrollWheelZoom(true) // 开启鼠标滚轮缩放
    map.addEventListener('click', function (e) {
      that.form.longitude = e.latlng.lng
      that.form.latitude = e.latlng.lat
      that.$message.success("已经将点位坐标填充至表单")
    });

    this.request.get("/position").then(res => {
      if (res.code === '200') {
        for(let i in res.data) {
          console.log(res)
          this.addMark(res.data[i].longitude, res.data[i].latitude, res.data[i].pid)
        }
      } else {
        this.$message.error(res.msg)
      }
    })
  },
  methods: {
    positionAdd() {
      this.addDialogFormVisible = true
    },
    cancel() {
      this.addDialogFormVisible = false
      this.form = {}
    },
    save() {
      const that = this
      this.request.post("/position", this.form).then(res => {
        if (res.code === '200') {
          this.addMark(res.data.longitude, res.data.latitude, res.data.pid)
          this.$message.success("添加成功")
        } else {
          this.$message.error(res.msg)
        }
      })
      this.addDialogFormVisible = false
      this.form = {}
    },
    deletePosition() {
      this.request.delete("/position/" + this.form.pid).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.$router.go(0)
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    load(pid) {
      const that = this
      this.loading = true
      this.request.get("/position/" + pid,).then(res => {
        if (res.code === '200') {
          that.form = res.data
        } else {
          this.$message.error(res.msg)
          return false
        }
      })
      this.request.get("/position/record/" + pid, {
        params: {
          pageNum: that.pageNum,
          pageSize: that.pageSize,
        }
      }).then(res => {
        if (res.code === '200') {
          let data = res.data.records
          that.total = res.data.total
          let tableData = []
          for(let i in data) {
            let userNum = data[i].userNum
            this.request.get("/user/" + userNum).then(res => {
              if (res.code === '200') {
                that.user = res.data
                let obj = {
                  checkTime: data[i].checkTime,
                  userNum: that.user.userNum,
                  userName: that.user.userName,
                  collegeNum: that.user.collegeNum,
                  sex: that.user.sex,
                  telephone: that.user.telephone
                }
                tableData.push(obj)
              } else {
                this.$message.error(res.msg)
              }
            })
          }
          console.log(this.trim(tableData))
          this.tableData = tableData
        } else {
          this.$message.error(res.msg)
          return false
        }
        this.loading = false
      })
    },
    positionModify(pid) {
      this.pid = pid
      this.load(pid)
      this.modifyDialogFormVisible = true
    },
    modifyCancel() {
      this.modifyDialogFormVisible = false
      this.form = {}
    },
    modifySave() {
      const that = this
      this.request.put("/position/", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("修改成功")
          this.modifyDialogFormVisible = false
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    addMark(longitude, latitude, pid) {
      let point = new BMapGL.Point(longitude, latitude);
      let marker = new BMapGL.Marker(point);  // 创建标注
      this.map.addOverlay(marker);              // 将标注添加到地图中
      let that = this
      marker.addEventListener("click", function(){
        that.positionModify(pid)
      });
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load(this.pid)
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load(this.pid)
    },

    getSex(sex) {
      return sex ? "男":"女"
    },
    getCollege(cid) {
      return this.college[cid]
    },
    trim(data) {
      // console.log("out")
      // for (let i in data) {
      //   data[i].sex = this.getSex(data[i].sex)
      //   data[i].collegeNum = this.getCollege(data[i].collegeNum)
      // }
      return data
    },
  }
}
</script>

<style scoped>

</style>