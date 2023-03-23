<template>
  <div>
    <el-card shadow="never" class="mt-5px">
      <el-row justify="center">
        <el-col :span="24">
          <el-skeleton :loading="loading" animated>
            <transition name="fade">
              <p class="text-center text-3xl" style="line-height: 3.2em">
                欢迎您：
                <span style="color: rgb(103 99 47); font-size: 1.15em">
                  {{ information.userName }}</span
                >
              </p>
            </transition>
          </el-skeleton>
          <el-skeleton :loading="loading" animated>
            <transition name="scale">
              <p class="text-center text-2xl" style="line-height: 2.1em">
                您的资历为
                <span style="color: rgb(167, 119, 48); font-size: 1.15em">
                  {{ information.dateTime }}</span
                >
                ，已超过
                <span style="color: rgb(89, 220, 207); font-size: 1.15em"
                  >{{ information.rank }}%</span
                >
                的人了。
              </p>
            </transition>
          </el-skeleton>
        </el-col>
      </el-row>
      <el-row class="mt-5px" :gutter="60" justify="space-between">
        <el-col :span="1" />
        <el-skeleton :loading="loading" animated>
          <transition name="slide">
            <el-col :xl="11" :lg="11" :md="22" :sm="22" :xs="22">
              <el-card shadow="never" class="mt-5px">
                <Echart :options="reportSelf" />
              </el-card>
            </el-col>
          </transition>
        </el-skeleton>
        <el-skeleton :loading="loading" animated>
          <transition name="collapse">
            <el-col :xl="11" :lg="11" :md="23" :sm="23" :xs="23">
              <el-card shadow="never" class="mt-5px">
                <Echart :options="reportTotal" />
              </el-card>
            </el-col>
          </transition>
        </el-skeleton>
        <el-col :span="1" />
      </el-row>
      <el-divider />
      <el-skeleton :loading="loading" animated>
        <el-row class="mt-5px" :gutter="20" justify="space-between">
          <el-col :span="22" :offset="1">
            <el-card shadow="never">
              <div class="text-center text-2xl">{{ chickenSoup }} </div>
            </el-card>
          </el-col>
        </el-row>
      </el-skeleton>
    </el-card>
    <el-skeleton :loading="loading" animated>
      <div style="position: relative">
        <div id="amity"></div>
        <div id="amity2"></div>
      </div>
    </el-skeleton>
  </div>
</template>
<script setup lang="ts" name="Home">
import { EChartsOption } from 'echarts'
import { useUserStore } from '@/store/modules/user'
import { useWatermark } from '@/hooks/web/useWatermark'
import avatarImg from '@/assets/imgs/avatar.gif'
import type { WorkplaceTotal, Project, Notice, Shortcut } from './types'
import { pieOptions, barOptions } from './echarts-data'
import Echart from '@/components/Echart/src/Echart.vue'
import { getUserInfoApi, getChickenSoupApi } from '@/api/system/home'
import { getReportAllStatisticsApi, getReportStatisticsApi } from '@/api/c/performReport'

const { t } = useI18n()
const userStore = useUserStore()
const { setWatermark } = useWatermark()
const loading = ref(true)
const avatar = userStore.getUser.avatar ? userStore.getUser.avatar : avatarImg
const username = userStore.getUser.nickname
const pieOptionsData = reactive<EChartsOption>(pieOptions) as EChartsOption
const chickenSoup = ref()
// 获取统计数
let totalSate = reactive<WorkplaceTotal>({
  project: 0,
  access: 0,
  todo: 0
})

const information = reactive({
  userName: '',
  dateTime: '',
  rank: ''
})
// 获得首页用户信息
const getUserInfo = async () => {
  await getUserInfoApi().then((res) => {
    information.userName = res.userName
    information.dateTime = res.dateTime
    information.rank = res.rank
  })
}
// 获得首页用户信息
const getReportStatistics = async () => {
  await getReportStatisticsApi().then((res) => (reportSelf.series[0].data = res))
}
const getReportAllStatistics = async () => {
  await getReportAllStatisticsApi().then((res) => (reportTotal.series[0].data = res))
}
// EChart的业绩报告个人统计
const reportSelf = reactive({
  title: {
    text: '个人统计',
    subtext: '业绩报告',
    left: 'center',
    top: '5%'
  },
  tooltip: {
    trigger: 'item'
  },
  series: [
    {
      name: '报告提交',
      type: 'pie',
      radius: '50%',
      data: [
        { value: 1048, name: '未提交' },
        { value: 735, name: '已提交' },
        { value: 580, name: '已超时' },
        { value: 484, name: '未通过' }
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
})

// EChart的业绩报告总统计
const reportTotal = reactive({
  title: {
    text: '全员统计',
    subtext: '业绩报告',
    left: 'center',
    top: '5%'
  },
  tooltip: {
    trigger: 'item'
  },
  series: [
    {
      name: '报告提交',
      type: 'pie',
      radius: '50%',
      data: [
        { value: 1048, name: '未提交' },
        { value: 735, name: '已提交' },
        { value: 580, name: '已超时' },
        { value: 484, name: '未通过' }
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
})
const getChickenSoup = () => {
  getChickenSoupApi().then((res) => {
    chickenSoup.value = res
  })
}
onMounted(async () => {
  getChickenSoup()
  await Promise.all([getUserInfo(), getReportStatistics(), getReportAllStatistics()])
  loading.value = false
})
</script>

<style scoped>
#amity {
  width: 0;
  height: 0;
  left: -100px;
  border-top: 50px solid transparent;
  border-left: 100px solid #ffae00;
  border-bottom: 50px solid transparent;
  position: absolute;
  animation: slide-in1 2s cubic-bezier(0, -0.47, 0.41, 1.16) infinite;
}

#amity2 {
  width: 0;
  height: 0;
  left: calc(100% + 100px);
  border-top: 50px solid transparent;
  border-right: 100px solid #ffae00;
  border-bottom: 50px solid transparent;
  position: absolute;
  animation: slide-in2 2s cubic-bezier(0, -0.47, 0.41, 1.16) infinite;
}

@keyframes slide-in1 {
  0% {
    left: -100px;
    opacity: 0.5;
  }
  100% {
    left: calc(50% - 100px);
    transform: scaleY(0) scaleX(0.5);
    opacity: 0.1;
  }
}

@keyframes slide-in2 {
  0% {
    left: calc(100% + 100px);
    opacity: 0.5;
  }
  100% {
    left: calc(50%);
    transform: scaleY(0) scaleX(0.5);
    opacity: 0.1;
  }
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}
.scale-enter-active,
.scale-leave-active {
  transition: transform 0.5s;
}

.scale-enter {
  transform: scale(0);
}

.scale-leave-to {
  transform: scale(0);
}
.slide-enter-active,
.slide-leave-active {
  transition: transform 0.5s;
}

.slide-enter,
.slide-leave-to {
  transform: translateX(100%);
}
.collapse-enter-active,
.collapse-leave-active {
  transition: max-height 0.5s;
}

.collapse-enter,
.collapse-leave-to {
  max-height: 0;
  overflow: hidden;
}
.list-enter-active,
.list-leave-active {
  transition: opacity 0.5s, transform 0.5s;
}

.list-enter,
.list-leave-to {
  opacity: 0;
  transform: translateY(30px);
}
</style>
