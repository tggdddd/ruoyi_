<template>
  <div class="loading-overlay">
    <div v-for="i in 10" :key="i" class="cloud" @animationstart="setCloudFlow"></div>
    <div class="inner-container">
      <div class="chicken"></div>
      <div class="loading-text">{{ loadingText }}</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { socialAuthRedirectToAdminApi } from '@/api/login'
import * as authUtil from '@/utils/auth'
import { ElLoading } from 'element-plus'
const router = useRouter()

const isLoading = ref(true)
const loadingText = ref('加载中...')
const pos = ref(0)
setInterval(() => {
  loadingText.value = '加载中' + '.'.repeat(pos.value++)
  if (pos.value == 7) {
    pos.value = 0
  }
}, 200)

const setCloudFlow = (event) => {
  const animationDelay = Math.floor(Math.random() * 10) + 1 + 's'
  const top = Math.floor(Math.random() * 20) + 1 + '%'
  event.target.style.top = top
  event.target.style.animationDelay = animationDelay
  event.target.style.animationPlayState = 'paused' // 暂停
  event.target.style.animationPlayState = 'running' // 播放
}
onMounted(() => {
  socialAuthRedirectToAdminApi(router.currentRoute.value.query)
    .then((res) => {
      if (!res) {
        return
      }
      ElLoading.service({
        lock: true,
        text: '正在加载系统中...',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      authUtil.setToken(res)
      router.push({ path: String(router?.currentRoute?.value?.query?.redirect) || '/' })
      setTimeout(() => {
        const loadingInstance = ElLoading.service()
        loadingInstance.close()
      }, 400)
    })
    .catch(() => {
      router.push({ name: 'Login' })
    })
})
</script>
<style scoped>
.loading-overlay {
  position: relative;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgb(73, 190, 254);
}
.cloud {
  background: url('/cloud.png');
  height: 70px;
  width: 133px;
  left: -133px;
  position: absolute;
  animation: cloud 10s infinite linear;
}
@keyframes cloud {
  0% {
    left: 100%;
  }
  100% {
    left: -133px;
  }
}
.loading-text {
  font-size: 3em;
  color: rgb(190, 65, 1);
}
.chicken {
  width: 349px;
  height: 382px;
  background-image: url('/chick.gif');
  background-repeat: no-repeat;
}
.inner-container {
  top: 25%;
  display: flex;
  position: relative;
  justify-content: center;
  flex-direction: column;
  text-align: center;
  vertical-align: middle;
  align-content: center;
  align-items: center;
}
</style>
