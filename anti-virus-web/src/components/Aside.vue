<template>
  <div>
    <el-menu
        :default-openeds="['1']"
        router
        v-if="userType===0"
    >
      <el-menu-item index="/">
        <template slot="title">
          <i class="el-icon-house"></i>
          <span slot="title">主页</span>
        </template>
      </el-menu-item>
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span slot="title">管理</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="/user">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span slot="title">用户管理</span>
            </template>
          </el-menu-item>
          <el-menu-item index="/map">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span slot="title">打卡点管理</span>
            </template>
          </el-menu-item>
          <el-menu-item index="/status">
            <template slot="title">
              <i class="el-icon-warning-outline"></i>
              <span slot="title">风险管控</span>
            </template>
          </el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
    <el-menu
        :default-openeds="['1']"
        router
        v-if="userType===1"
    >
      <el-menu-item index="/">
        <template slot="title">
          <i class="el-icon-house"></i>
          <span slot="title">主页</span>
        </template>
      </el-menu-item>
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span slot="title">功能</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="/vacation">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span slot="title">请假</span>
            </template>
          </el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
    <el-menu
        :default-openeds="['1']"
        router
        v-if="userType===2"
    >
      <el-menu-item index="/">
        <template slot="title">
          <i class="el-icon-house"></i>
          <span slot="title">主页</span>
        </template>
      </el-menu-item>
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span slot="title">管理</span>
        </template>
        <el-menu-item-group>
<!--          <el-menu-item index="/user">-->
<!--            <template slot="title">-->
<!--              <i class="el-icon-setting"></i>-->
<!--              <span slot="title">打卡管理</span>-->
<!--            </template>-->
<!--          </el-menu-item>-->
          <el-menu-item index="/vacationmanage">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span slot="title">请假管理</span>
            </template>
          </el-menu-item>
          <el-menu-item index="/status">
            <template slot="title">
              <i class="el-icon-warning-outline"></i>
              <span slot="title">风险管控</span>
            </template>
          </el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>


export default {
  name: "Aside",
  props: {
    userType: Number
  },
}
</script>

<style scoped>
.el-menu {
  border: none;
  z-index: 0;
}

.el-submenu {
  z-index: 0;
}
</style>