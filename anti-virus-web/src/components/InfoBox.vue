<template>
  <div class="wrapper bgcolor-yellow">
    <div class="info-box">
      <div class="sub-title">
        <h1>${param.number}</h1>
        ${param.title}
      </div>
    </div>
    <div class="bottom-link bgcolor-darkyellow">
      <a href="#" class="btn">
        <span style="display: none" class="url">${param.url}</span>
        More info <i class="fa fa-arrow-right"></i>
      </a>
    </div>
  </div>
</template>

<script>
export default {
  name: "InfoBox"
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