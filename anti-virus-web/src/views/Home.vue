<template>
<div>
  <div>
    <div style="display: block; margin: 20px auto 20px auto">
      <el-button v-if="userStatus !== 1" type="success" icon="el-icon-check" circle style="display: block; height: 200px; width: 200px; font-size: 30px; margin: 0 auto" @click="check" v-show="!isChecked">打卡</el-button>
      <el-button v-if="userStatus === 1" type="danger" icon="el-icon-close" circle style="display: block; height: 200px; width: 200px; font-size: 30px; margin: 0 auto">禁止打卡</el-button>
    </div>
    <div class="health-code" v-if="userStatus !== 1">
      <h1 style="font-size: 50px; text-align: center">{{ timeText }}</h1>
      <h1 v-show="isChecked" style="font-size: 30px">{{ userName + " - " + location}}</h1>
      <div id="qrcode" style="margin-top: 20px"></div>
    </div>
  </div>

  <el-dialog title="选择打卡点" :visible.sync="DialogFormVisible">
    <el-form :model="form" label-width="80px" size="small">
      <el-form-item label="打卡点">
        <el-select v-model="value" placeholder="请选择" @change="getCurrentPositionDescription(value)">
          <el-option
              v-for="item in options"
              :key="item.pid"
              :label="item.name"
              :value="item.pid">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="简介">
        <el-input type="textarea" v-model="description" disabled></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancel">取 消</el-button>
      <el-button type="primary" @click="save">打卡</el-button>
    </div>
  </el-dialog>

</div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      timeText: "请先打卡后查看健康码",
      timer: "",
      form: {},
      options: [],
      record: {},
      value: "",
      DialogFormVisible: false,
      description: "",
      qrcode: {},
      timeElapsed: 0,
      isChecked: false,
      userName: "",
      location: "",
      userStatus: 0,
    }
  },
  created() {
    this.record.userNum = JSON.parse(localStorage.getItem("user")).userNum
    this.request.get("/status/" + this.record.userNum).then(res => {
      if (res.code === '200') {
        console.log(res.data)
        this.userStatus = res.data
      } else {
        this.$message.error(res.msg)
      }
    })
    this.request.get("/position/record/checked/" + this.record.userNum).then(res => {
      // console.log(this.record)
      // console.log(res)
      if (res.code === '200') {
        this.location = res.data.name
        this.checkedCode(res.data)
        this.setTimer()
        return false
      }
    })
  },
  methods: {
    darkCode() {
      this.qrcode.text = "用户未打卡"
      this.qrcode.colorDark = "#000000"
    },
    setTimer() {
      this.time()
      this.timer = setInterval(() => {
        this.time()
        this.timeElapsed++
        if (this.timeElapsed >= 100) {
          this.clear()
        }
      }, 1000);
    },
    checkedCode(hid) {
      this.isChecked = true
      this.userName = JSON.parse(localStorage.getItem("user")).userName
      if (this.qrcode.width === undefined) {
        this.qrcode = new QRCode(document.getElementById("qrcode"), {
          text: "已打卡",
          width: 400,
          height: 400,
          colorDark : "#00AA00",
          colorLight : "#FFFFFF",
          correctLevel : QRCode.CorrectLevel.H,
        });
        this.setTimer()
      } else {
        console.log(123)
      }
    },
    check() {
      this.DialogFormVisible = true
      this.request.get("/position").then(res => {
        if (res.code === '200') {
          this.options = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    save() {
      this.record.pid = this.value
      console.log(this.record)
      this.request.post("/position/check", this.record).then(res => {
        if (res.code === '200') {
          this.$message.success("打卡成功")
          this.checkedCode(res.data)
          this.DialogFormVisible = false
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    getCurrentPositionDescription(pid) {
      const that = this
      this.request.get("/position/"+pid).then(res => {
        if (res.code === '200') {
          that.description = res.data.description
          that.location = res.data.name
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    cancel() {
      this.form = {}
      this.DialogFormVisible = false
    },
    clear() {
      clearInterval(this.timer);
      this.timer = null
      this.qrcode.clear()
      this.timeText = "请先打卡后查看健康码"
      this.timeElapsed = 0
      this.$router.go(0)
    },
    fix(s) {
      s = s.toString();
      while(s.length < 2) s = '0' + s;
      return s;
    },
    time(){
      let date =  new Date();
      let month = date.getMonth() + 1;
      let day = date.getDate();
      let hours = this.fix(date.getHours());
      let minutes = this.fix(date.getMinutes());
      let seconds = this.fix(date.getSeconds());
      this.timeText = month + "月" + day + "日" + "\t" + hours + ":" + minutes +":" + seconds + "\t";
    }
  }
}
</script>

<style scoped>
.health-code {
  display: flex;
  flex-flow: column wrap;
  justify-content: flex-start;
  align-items: center;
}
</style>