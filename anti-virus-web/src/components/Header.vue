<template>
  <div style="display: flex">
    <div style="flex: 1; font-size: 18px">
      <i class="el-icon-s-fold" style="cursor: pointer"></i>
<!--      <el-breadcrumb separator="/" style="display: inline-block;" class="ml-5">-->
<!--        <el-breadcrumb-item :to="{ path: '/' }" v-for="item in paths">首页</el-breadcrumb-item>-->
<!--      </el-breadcrumb>-->
    </div>
<!--    <el-dropdown style="cursor: pointer">-->
<!--      <i class="el-icon-arrow-down" style="margin-left: 5px"></i>-->
<!--      <el-dropdown-menu slot="dropdown">-->
<!--        <el-dropdown-item >个人信息</el-dropdown-item>-->
<!--        <el-dropdown-item>-->
<!--          <span @click="logout">退出</span>-->
<!--        </el-dropdown-item>-->
<!--      </el-dropdown-menu>-->
    <el-dropdown trigger="click" @command="handleCommand">
    <span class="el-dropdown-link" style="cursor: pointer">
      {{ user.userName }}<i class="el-icon-arrow-down el-icon--right"></i>
    </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
        <el-dropdown-item command="out">退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
  },
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {

  },
  methods: {
    handleCommand(command) {
      if (command === 'out') {
        this.logout()
      } else if (command === 'userinfo') {
        this.$router.push("/userinfo")
      }
    },
    logout() {
      this.$router.push("/login")
      localStorage.removeItem("user")
    }
  }
}
</script>

<style scoped>

</style>