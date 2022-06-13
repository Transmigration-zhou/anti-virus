<template>
  <div>
    <el-card style="padding: 10px">
      <el-form :model="form" label-width="80px" size="small">
        <el-form-item label="用户名">
          <el-input v-model="form.userNum" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="密码" v-if="false">
          <el-input v-model="form.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.userName" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.telephone" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="save" style="float: right; margin-bottom: 5px">确 定</el-button>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "UserInfo",
  data() {
    return {
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.request.post("/user/info", this.user).then(res => {
      if (res.code === '200') {
        this.form = res.data;
      } else {
        this.$message.error("获取信息出错!")
      }
    })
  },
  methods: {
    save() {
      console.log(this.form)
      this.request.post("/user", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("更新成功")
        } else {
          this.$message.error(res.msg)
        }
      })
    },
  }
}
</script>

<style scoped>

</style>