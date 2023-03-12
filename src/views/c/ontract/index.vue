<template>
  <ContentWrap>
    <div class="flex">
      <el-card class="w-1/5 user" :gutter="12" shadow="always">
        <template #header>
          <div class="card-header">
            <span>部门列表</span>
            <!--            <XTextButton title="修改部门" @click="handleDeptEdit()" />-->
          </div>
        </template>
        <el-input v-model="filterText" placeholder="搜索部门" />
        <el-scrollbar height="650">
          <el-tree
            ref="treeRef"
            node-key="id"
            default-expand-all
            :data="deptOptions"
            :props="defaultProps"
            :highlight-current="true"
            :filter-node-method="filterNode"
            :expand-on-click-node="false"
            @node-click="handleDeptNodeClick"
          />
        </el-scrollbar>
      </el-card>
      <!-- 列表 -->
      <el-card class="w-4/5 user" style="margin-left: 10px" :gutter="12" shadow="hover">
        <XTable @register="registerTable">
          <template #userId_default="{ row }">
            <!--            <el-button text type="primary">查看详情</el-button>-->
            {{ row.userId }}
          </template>
          <template #postId_default="{ row }">
            <el-tag type="success"> {{ getPostName(row.postId) }} </el-tag>
          </template>
          <template #attach_default="{ row }">
            <vxe-button content="查看" @click="previewAttachById(row.id)" />
          </template>
          <template #toolbar_buttons>
            <!-- 操作：新增 -->
            <XButton
              type="primary"
              preIcon="ep:zoom-in"
              :title="t('action.add')"
              v-hasPermi="['c:ontract:create']"
              @click="handleCreate()"
            />
            <!-- 操作：导出 -->
            <XButton
              type="warning"
              preIcon="ep:download"
              :title="t('action.export')"
              v-hasPermi="['c:ontract:export']"
              @click="exportList('合同表单.xls')"
            />
          </template>
          <template #actionbtns_default="{ row }">
            <!-- 操作：修改 -->
            <XTextButton
              preIcon="ep:edit"
              :title="t('action.edit')"
              v-hasPermi="['c:ontract:update']"
              @click="handleUpdate(row.id)"
            />
            <!-- 操作：详情 -->
            <XTextButton
              preIcon="ep:view"
              :title="t('action.detail')"
              v-hasPermi="['c:ontract:query']"
              @click="handleDetail(row.id)"
            />
            <!-- 操作：详情 -->
            <!--            <XTextButton-->
            <!--              preIcon="ep:view"-->
            <!--              :title="t('action.detail')"-->
            <!--              v-hasPermi="['c:ontract:query']"-->
            <!--              @click="handleDetail(row.id)"-->
            <!--            />-->
            <!-- 操作：删除 -->
            <XTextButton
              preIcon="ep:delete"
              :title="t('action.del')"
              v-hasPermi="['c:ontract:delete']"
              @click="deleteData(row.id)"
            />
          </template>
        </XTable>
      </el-card>
    </div>
  </ContentWrap>
  <!-- 弹窗 -->
  <XModal
    id="ontractModel"
    :loading="modelLoading"
    v-model="modelVisible"
    :title="modelTitle"
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
    <div v-html="attachRef" id="attachContent"> </div>
  </XModal>

  <!-- 选择模板弹窗 -->
  <XModal
    id="contractTemplateModel"
    :loading="modelTemplateLoading"
    v-model="modelTemplateVisible"
    title="选择合同模板"
  >
    <TemplateSelect @update:form-template-data="formTemplateData = $event" />
    <template #footer>
      <!-- 按钮：关闭 -->
      <XButton
        :loading="actionLoading"
        :title="t('dialog.close')"
        @click="modelTemplateVisible = false"
      />
    </template>
  </XModal>
</template>
<script setup lang="ts" name="ontract">
import { FormExpose } from '@/components/Form'
// 业务相关的 import
import { rules, allSchemas } from './ontract.data'
import * as ontractApi from '@/api/c/ontract'
import type { ElTree, UploadRawFile, UploadInstance } from 'element-plus'
import { handleTree, defaultProps } from '@/utils/tree'
import TemplateSelect from './Component/templateSelect.vue'
import download from '@/utils/download'
import { CommonStatusEnum } from '@/utils/constants'
import { getAccessToken, getTenantId } from '@/utils/auth'
import * as UserApi from '@/api/system/user'
import { listSimpleDeptApi } from '@/api/system/dept'
import { listSimpleRolesApi } from '@/api/system/role'
import { listSimplePostsApi, PostVO } from '@/api/system/post'
import {
  aassignUserRoleApi,
  listUserRolesApi,
  PermissionAssignUserRoleReqVO
} from '@/api/system/permission'
import XModal from '@/components/XModal/src/XModal.vue'
import { ontractVO } from '@/api/c/ontract'
import { getAttachApi } from '@/api/c/util'
import * as ontractTemplateApi from '@/api/c/ontractTemplate'
import { postPackageOption } from '@/views/c/ontractTemplate/ontractTemplate.data'
import XButton from '@/components/XButton/src/XButton.vue'
import { ElMessageBox } from 'element-plus'
// ========== 列表相关 ==========
const tableTitle = ref('用户列表')
const queryParams = reactive({
  postId: null
})

// ========== 创建部门树结构 ==========
const filterText = ref('')
const deptOptions = ref<Tree[]>([]) // 树形结构
const treeRef = ref<InstanceType<typeof ElTree>>()
const getTree = async () => {
  const res = await listSimpleDeptApi()
  deptOptions.value.push(...handleTree(res))
}
const filterNode = (value: string, data: Tree) => {
  if (!value) return true
  return data.name.includes(value)
}
const handleDeptNodeClick = async (row: { [key: string]: any }) => {
  queryParams.postId = row.id
  await reload()
}
const { push } = useRouter()
const handleDeptEdit = () => {
  push('/system/dept')
}
watch(filterText, (val) => {
  treeRef.value!.filter(val)
})

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

// 列表相关的变量
const [registerTable, { reload, exportList }] = useXTable({
  allSchemas: allSchemas,
  getListApi: ontractApi.getontractPageApi,
  deleteApi: ontractApi.deleteontractApi,
  exportListApi: ontractApi.exportontractApi,
  params: queryParams
})
const deleteData = (id: number) => {
  if (!id) {
    return
  }
  delConfirm().then(async () => {
    const result = await ontractApi.deleteontractApi(id)
    message.success(result == true ? t('common.delSuccess') : result)
    // 刷新列表
    reload()
  })
}

// 删除窗体
const delConfirm = (content?: string, tip?: string) => {
  return ElMessageBox.confirm(
    content ? content : t('common.delMessage'),
    tip ? tip : t('common.confirmTitle'),
    {
      confirmButtonText: t('common.ok'),
      cancelButtonText: t('common.cancel'),
      type: 'warning'
    }
  )
}
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
// 模板选择弹窗
const modelTemplateVisible = ref(false) // 是否显示弹出层
const modelTemplateLoading = ref(false) // 弹出层loading
const formTemplateData = ref() // 表单 Ref
// 设置标题
const setDialogTile = (type: string) => {
  modelLoading.value = true
  modelTitle.value = t('action.' + type)
  actionType.value = type
  modelVisible.value = true
}

// 新增操作
const handleCreate = () => {
  modelTemplateVisible.value = true
  // setDialogTile('create')
  // modelLoading.value = false
}
watch(formTemplateData, (n, o) => {
  modelTemplateVisible.value = false
  setTimeout(() => {
    setDialogTile('create')
    modelLoading.value = false
    nextTick(() => {
      unref(formRef)?.setValues(formTemplateData.value)
    }),
      250
  })
})
const realCreate = () => {
  modelTemplateVisible.value = false
  // setTimeout(() => {
  setDialogTile('create')
  modelLoading.value = false
  unref(formRef)?.setValues(formTemplateData)
  // }, 150)
}

// 修改操作
const handleUpdate = async (rowId: number) => {
  setDialogTile('update')
  // 设置数据
  const res = await ontractApi.getontractApi(rowId)
  unref(formRef)?.setValues(res)
  modelLoading.value = false
}

// 详情操作
const handleDetail = async (rowId: number) => {
  setDialogTile('detail')
  const res = await ontractApi.getontractApi(rowId)
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
        const data = unref(formRef)?.formModel as ontractApi.ontractVO
        if (actionType.value === 'create') {
          await ontractApi.createontractApi(data)
          message.success(t('common.createSuccess'))
        } else {
          await ontractApi.updateontractApi(data)
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

// ========== 初始化 ==========
onMounted(async () => {
  await getTree()
})
</script>
