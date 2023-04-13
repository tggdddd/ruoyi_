<template>
  <ContentWrap>
    <!-- 列表 -->
    <XTable @register="registerTable">
      <template #toolbar_buttons>
        <!-- 操作：新增 -->
        <XButton
          type="primary"
          preIcon="ep:zoom-in"
          :title="t('action.add')"
          v-hasPermi="['c:ontract-template:create']"
          @click="handleCreate()"
        />
        <!-- 操作：导出 -->
        <XButton
          type="warning"
          preIcon="ep:download"
          :title="t('action.export')"
          v-hasPermi="['c:ontract-template:export']"
          @click="exportList('合同表单模板.xls')"
        />
      </template>
      <template #post_default="{ row }">
        <el-tag type="success"> {{ getPostName(row.postId) }} </el-tag>
      </template>
      <template #attach_default="{ row }">
        <vxe-button content="查看" @click="previewAttachById(row.id)" />
      </template>
      <template #actionbtns_default="{ row }">
        <!-- 操作：修改 -->
        <XTextButton
          preIcon="ep:edit"
          :title="t('action.edit')"
          v-hasPermi="['c:ontract-template:update']"
          @click="handleUpdate(row.id)"
        />
        <!-- 操作：详情 -->
        <XTextButton
          preIcon="ep:view"
          :title="t('action.detail')"
          v-hasPermi="['c:ontract-template:query']"
          @click="handleDetail(row.id)"
        />
        <!-- 操作：删除 -->
        <XTextButton
          preIcon="ep:delete"
          :title="t('action.del')"
          v-hasPermi="['c:ontract-template:delete']"
          @click="deleteData(row.id)"
        />
      </template>
    </XTable>
  </ContentWrap>
  <!-- 弹窗 -->
  <XModal
    id="ontractTemplateModel"
    :loading="modelLoading"
    v-model="modelVisible"
    :title="modelTitle"
    :height="800"
    :fullscreen="true"
  >
    <!-- 表单：添加/修改 -->
    <Form
      ref="formRef"
      v-if="['create', 'update'].includes(actionType)"
      :schema="allSchemas.formSchema"
      :rules="rules"
    />
    <!-- 表单：详情 -->
    <Descriptions
      v-if="actionType === 'detail'"
      :schema="allSchemas.detailSchema"
      :data="detailData"
    >
      <template #post="{ row }">
        <el-tag type="success"> {{ getPostName(row.post) }} </el-tag>
      </template>
    </Descriptions>
    <template #footer>
      <!-- 按钮：保存 -->
      <XButton
        v-if="['create', 'update'].includes(actionType)"
        type="primary"
        :title="t('action.save')"
        :loading="actionLoading"
        @click="submitForm()"
      />
      <!-- 按钮：预览 -->
      <XButton
        v-if="['create', 'update'].includes(actionType)"
        type="warning"
        :title="t('action.preview')"
        :loading="actionLoading"
        @click="previewForm()"
      />
      <!-- 按钮：关闭 -->
      <XButton :loading="actionLoading" :title="t('dialog.close')" @click="modelVisible = false" />
    </template>
  </XModal>

  <!-- 附件弹窗 -->
  <XModal
    id="attachModel"
    :loading="attachModelLoading"
    v-model="attachModelVisible"
    :title="attachModelTitle"
    :height="800"
    @show="setShadow"
  >
    <div v-html="attachRef" ref="attachContent"></div>
  </XModal>
</template>
<style lang="scss">
// #attachContent * {
//   all: revert;
// }
</style>
<script lang="ts" setup>
import { FormExpose } from '@/components/Form'
// 业务相关的 import
import { rules, allSchemas, postPackageOption } from './ontractTemplate.data'
import * as ontractTemplateApi from '@/api/c/ontractTemplate'
import { getAttachApi, ontractVO } from '@/api/c/util'
import { listSimplePostsApi, PostVO } from '@/api/system/post'
import { VxeColumnPropTypes } from 'vxe-table'
import * as attach from './attach.data'
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const attachModalRef = ref()
// 列表相关的变量
const [registerTable, { reload, deleteData, exportList }] = useXTable({
  allSchemas: allSchemas,
  getListApi: ontractTemplateApi.getontractTemplatePageApi,
  deleteApi: ontractTemplateApi.deleteontractTemplateApi,
  exportListApi: ontractTemplateApi.exportontractTemplateApi
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
const attachModelTitle = ref('edit') // 弹出层标题
const attachModelLoading = ref(false) // 弹出层loading
const attachActionLoading = ref(false) // 按钮 Loading
const attachRef = ref<string>() // 表单 Ref
const attachContent = ref(null)
// 设置标题
const setDialogTile = (type: string) => {
  modelLoading.value = true
  modelTitle.value = t('action.' + type)
  actionType.value = type
  modelVisible.value = true
}

// 新增操作
const handleCreate = () => {
  setDialogTile('create')
  modelLoading.value = false
}

// 修改操作
const handleUpdate = async (rowId: number) => {
  setDialogTile('update')
  // 设置数据
  const res = await ontractTemplateApi.getontractTemplateApi(rowId)
  unref(formRef)?.setValues(res)
  modelLoading.value = false
}

// 详情操作
const handleDetail = async (rowId: number) => {
  setDialogTile('detail')
  const res = await ontractTemplateApi.getontractTemplateApi(rowId)
  detailData.value = res
  modelLoading.value = false
}

// 提交按钮
const submitForm = async () => {
  const elForm = unref(formRef)?.getElFormRef()
  if (!elForm) return
  elForm.validate(async (valid) => {
    if (valid) {
      actionLoading.value = true
      // 提交请求
      try {
        const data = unref(formRef)?.formModel as ontractTemplateApi.ontractTemplateVO
        console.log(data)
        if (actionType.value === 'create') {
          await ontractTemplateApi.createontractTemplateApi(data)
          message.success(t('common.createSuccess'))
        } else {
          await ontractTemplateApi.updateontractTemplateApi(data).then()
          message.success(t('common.updateSuccess'))
        }
        modelVisible.value = false
      } finally {
        actionLoading.value = false
        // 刷新列表
        await reload()
      }
    }
  })
}
// 查看合同附件
const previewForm = async () => {
  const elForm = unref(formRef)?.getElFormRef()
  if (!elForm) return
  elForm.validate(async (valid) => {
    if (valid) {
      actionLoading.value = true
      // 发送请求
      try {
        const data = unref(formRef)?.formModel as ontractVO
        const res = await getAttachApi(data)
        attachRef.value = res
        attachModelVisible.value = true
      } finally {
        actionLoading.value = false
      }
    }
  })
}
const previewAttachById = async (id: number) => {
  let xmodal = require('xmodal')
  // 调用xmodal中的open()方法
  xmodal.open({
    title: '提示',
    content: '您确定要提交吗？',
    okText: '确定',
    cancelText: '取消',
    onOk() {
      console.log('点击了确定')
    },
    onCancel() {
      console.log('点击了取消')
    }
  })
  // actionLoading.value = true
  // // 提交请求
  // try {
  //   const res = await ontractTemplateApi.getAttachByIdApi(id)
  //   attachRef.value = res
  //   attachModelVisible.value = true
  // } finally {
  //   actionLoading.value = false
  // }
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
const setShadow = (e: ModalOptions) => {
  // e.target.attachShadow({ mode: 'open' })
}
</script>
