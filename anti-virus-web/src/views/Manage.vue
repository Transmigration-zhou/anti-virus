<template>
  <div>
    <el-container style="height: 100vh; border: 1px solid #eee;">

      <el-aside width="sideWidth+'px'" style="background-color: #FFF">
        <div class="logo-box">
<!--            <span class="logo">-->
<!--          </span>-->
<!--          <div style="width: 200px; height: 60px; line-height: 60px; text-align: center;">-->
<!--            管理员-->
<!--          </div>-->
        </div>
        <div class="submenus">
          <Aside :userType="userType"/>
        </div>
      </el-aside>
      <el-container>
        <el-header>
          <Header/>
        </el-header>

        <el-main >
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
// @ is an alias to /src

import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: 'Manage',
  components: {
    Aside,
    Header
  },
  created() {
    let user =  JSON.parse(localStorage.getItem("user"))
    this.userType = user.userType

  },
  data() {
    return {
      sideWidth: 200,
      boxWidth: 60,
      userType: -1
    }
  },
  methods: {

  }
}
</script>

<style>
.el-header {
  background-color: #FFFFFF;
  color: #333;
  line-height: 60px;
  border-bottom: 1px solid #EEE
;
}

.el-aside {
  color: #333;
  box-shadow: 2px 0 4px #EEE;
  min-width: 200px;
}

::-webkit-scrollbar {
  display:none
}

.logo-box {
  position: fixed;
  z-index: 100;
  background: rgba(255,255,255,1);
}

.logo {
  display: block;
  width:60px;
  height: 60px;
  text-align: center;
  background: url('../assets/logo.png') no-repeat;
  background-size:cover;
  margin: auto;
}

.submenus {
  display: block;
  margin-top: 120px;
}
</style>
