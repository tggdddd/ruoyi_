<template>
  <!-- 列表 -->
  <XTable @register="registerTable">
    <template #userId_default="{}">
      <el-button text type="primary">查看详情</el-button>
    </template>
    <template #postId_default="{ row }">
      <el-tag type="success"> {{ getPostName(row.postId) }}</el-tag>
    </template>
    <template #attach_default="{ row }">
      <vxe-button content="查看" @click="previewAttachById(row.id)" />
    </template>
    <template #actionbtns_default="{ row }">
      <!-- 操作：选择 -->
      <XTextButton preIcon="ep:select" :title="t('action.select')" @click="selectDate(row)" />
    </template>
    <template #toolbar_buttons></template>
  </XTable>

  <!-- 附件弹窗 -->
  <XModal
    id="attachModel"
    :loading="attachModelLoading"
    v-model="attachModelVisible"
    title="合同样式"
    :height="800"
    @show="setShadow"
  >
    <div v-html="attachRef" id="attachContent"> </div>
  </XModal>
</template>
<script lang="ts" setup>
import { FormExpose } from '@/components/Form'
// 业务相关的 import
import { allSchemas, postPackageOption } from './ontract.data'
import * as ontractApi from '@/api/c/ontract'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const attachModalRef = ref()
// 列表相关的变量
const [registerTable, { reload, deleteData, exportList }] = useXTable({
  allSchemas: allSchemas,
  getListApi: ontractApi.getAcceptContractPageApi
})

// 弹窗相关的变量
const modelVisible = ref(false) // 是否显示弹出层
const modelTitle = ref('edit') // 弹出层标题
const modelLoading = ref(false) // 弹出层loading
const actionType = ref('') // 操作按钮的类型
const actionLoading = ref(false) // 按钮 Loading
const formRef = ref<FormExpose>() // 表单 Ref
const detailData = ref() // 详情 Ref

//合同附件相关变量
const attachModelVisible = ref(false) // 是否显示弹出层
const attachModelLoading = ref(false) // 弹出层loading
const attachActionLoading = ref(false) // 按钮 Loading
const attachRef = ref<string>() // 表单 Ref

const emit = defineEmits(['update:contractId'])
const selectDate = (row) => {
  emit('update:contractId', row.id)
}
const previewAttachById = async (id: number) => {
  actionLoading.value = true
  // 提交请求
  try {
    const res = await ontractApi.getAttachByIdApi(id)
    attachRef.value = res
    attachModelVisible.value = true
  } finally {
    actionLoading.value = false
  }
}

// 获得岗位数据
const getPostName = (id: number) => {
  for (let item of postPackageOption) {
    if (item.value == id) {
      return item.label
    }
  }
  return '无'
}
// 避免主题样式的影响
const setShadow = (type) => {
  const container = type.$modal.getBox().querySelector('#attachContent')
  const inner = container.innerHTML
  container.innerHTML = ''
  container.attachShadow({ mode: 'open' })
  container.shadowRoot.innerHTML = inner
}
</script>
