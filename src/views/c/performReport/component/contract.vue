<template>
  <div>
    <!-- 弹窗 -->
    <XModal id="ontractModel" :loading="modelLoading" v-model="modelVisible" :title="modelTitle">
      <!-- 表单：详情 -->
      <Descriptions :schema="allSchemas.detailSchema" :data="detailData">
        <template #userId="{ row }">
          <el-button type="text">{{ row.userId }}</el-button>
        </template>
        <template #postId="{ row }">
          <el-tag> {{ getPostName(row.postId) }} </el-tag>
        </template>
        <template #attach="{ row }">
          <XTextButton
            preIcon="ep:view"
            :title="t('action.detail')"
            v-hasPermi="['c:ontract-template:query']"
            @click="previewAttachById(row.id)"
          />
        </template>
        <template #defaultClause="{ row }">
          <div v-html="row.defaultClause"></div>
        </template>
        <template #performanceRequirements="{ row }">
          <div v-html="row.performanceRequirements"></div>
        </template>
      </Descriptions>
      <template #footer>
        <!-- 按钮：预览 -->
        <XButton
          v-if="['create', 'update'].includes(actionType)"
          type="warning"
          :title="t('action.preview')"
          :loading="actionLoading"
          @click="previewForm()"
        />
        <!-- 按钮：关闭 -->
        <XButton :loading="actionLoading" :title="t('dialog.close')" @click="close" />
      </template>
    </XModal>

    <!-- 附件弹窗 -->
    <XModal
      id="attachModel"
      :loading="attachModelLoading"
      v-model="attachModelVisible"
      title="合同"
      :height="800"
      @show="setShadow"
    >
      <div v-html="attachRef" id="attachContent"> </div>
    </XModal>
  </div>
</template>
<script lang="ts" setup name="ContractDetail">
import { FormExpose } from '@/components/Form'
// 业务相关的 import
import { ontractVO } from '@/api/c/ontract'
import XButton from '@/components/XButton/src/XButton.vue'
import { rules, allSchemas } from '@/views/c/ontract/ontract.data'
import { postPackageOption } from '@/views/c/ontractTemplate/ontractTemplate.data'
import { getontractApi, getAttachByIdApi } from '@/api/c/ontract/index'
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const attachModalRef = ref()
// 传递属性
const props = defineProps({
  modelVisible: {
    type: Boolean,
    default: false
  },
  contractId: {
    type: Number,
    default: 0
  }
})
// 弹窗相关的变量
const modelVisible = toRef(props, 'modelVisible') // 是否显示弹出层
const contractId = toRef(props, 'contractId') //合同id
const modelTitle = ref('edit') // 弹出层标题
const modelLoading = ref(false) // 弹出层loading
const actionType = ref('') // 操作按钮的类型
const actionLoading = ref(false) // 按钮 Loading
const detailData = ref() // 详情 Ref

//合同附件相关变量
const attachModelVisible = ref(false) // 是否显示弹出层
const attachModelLoading = ref(false) // 弹出层loading
const attachActionLoading = ref(false) // 按钮 Loading
const attachRef = ref<string>() // 表单 Ref

const emit = defineEmits(['update:formTemplateData'])

const previewAttachById = async (id: number) => {
  actionLoading.value = true
  // 提交请求
  try {
    const res = await getAttachByIdApi(id)
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
// 修改表单数据
watch(contractId, (val) => {
  setFormDetail(val)
})
const setFormDetail = async (val: number) => {
  detailData.value = await getontractApi(val)
}
// 关闭窗口
const close = () => {
  emit('update:modelVisible', false)
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
