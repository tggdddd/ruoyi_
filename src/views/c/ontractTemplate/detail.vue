<template>
  <ContentWrap v-loading="actionLoading">
    <div>
      <div ref="attachContent"> </div>
    </div>
  </ContentWrap>
</template>
<script lang="ts" setup>
// 业务相关的 import
import * as ontractTemplateApi from '@/api/c/ontractTemplate'

const { query } = useRoute() // 查询参数
const message = useMessage() // 消息弹窗
const id = ref() // 表单编号
const actionLoading = ref(false)
const attachRef = ref<string>() // 表单 Ref
const attachContent = ref(null)
const previewAttachById = async (id: number) => {
  actionLoading.value = true
  // 提交请求
  try {
    const res = await ontractTemplateApi.getAttachByIdApi(id)
    attachContent.value.attachShadow({ mode: 'open' })
    attachContent.value.shadowRoot.innerHTML = res
  } finally {
    actionLoading.value = false
  }
}
onMounted(() => {
  id.value = query.id
  if (!id.value) {
    message.error('未传递 id 参数，无法查看 OA 详情信息')
    return
  }
  previewAttachById(id.value)
})
</script>
