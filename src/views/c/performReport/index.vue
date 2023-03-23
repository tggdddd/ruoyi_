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
        <!-- 列表 -->
        <XTable @register="registerTable">
          <template #bpmProcessDefinition="{ row }">
            {{ row.processDefinedName }}
          </template>
          <template #contractId_default="{ row }">
            <el-button text type="primary" @click="viewContract(row.contractId)"
              >查看详情</el-button
            >
          </template>
          <template #postId_default="{ row }">
            <el-tag> {{ getPostName(row.postId) }} </el-tag>
          </template>
          <template #processInstanceId="{ row }">
            <el-button
              v-if="row.processInstanceId"
              text
              type="primary"
              v-hasPermi="['c:perform-report:query']"
              @click="processInstanceDetail(row.processInstanceId)"
              >查看详情</el-button
            >
            <div v-else> 未提交 </div>
          </template>
          <template #toolbar_buttons>
            <!-- 操作：新增 -->
            <XButton
              type="primary"
              preIcon="ep:zoom-in"
              :title="t('action.submit')"
              v-hasPermi="['c:perform-report:create']"
              @click="handleCreate()"
            />
            <!-- 操作：导出 -->
            <XButton
              type="warning"
              preIcon="ep:download"
              :title="t('action.export')"
              v-hasPermi="['c:perform-report:export']"
              @click="exportList('业绩信息.xls')"
            />
          </template>
          <template #actionbtns_default="{ row }">
            <!-- 操作：提交 -->
            <XTextButton
              v-if="row.status === 0"
              preIcon="ep:edit"
              :title="t('action.submit')"
              v-hasPermi="['c:perform-report:update']"
              @click="handleUpdate(row)"
            />
            <XTextButton
              v-else
              preIcon="ep:view"
              :title="t('action.detail')"
              v-hasPermi="['c:perform-report:query']"
              @click="handleUpdate(row)"
            />
            <!-- 操作：详情 -->
            <!-- <XTextButton
              preIcon="ep:view"
              :title="t('action.detail')"
              v-hasPermi="['c:perform-report:query']"
              @click="handleDetail(row.id)"
            /> -->
          </template>
        </XTable>
      </el-card>
    </div>
  </ContentWrap>
  <!-- 弹窗 -->
  <XModal
    id="performReportModel"
    :loading="modelLoading"
    v-model="modelVisible"
    :title="modelTitle"
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
    />
    <!-- 表单：详情 -->
    <Descriptions
      v-if="actionType === 'contractDetail'"
      :schema="allSchemas.detailSchema"
      :data="contractDetailData"
    />
    <template #footer>
      <!-- 按钮：保存 -->
      <XButton
        v-if="['create', 'update'].includes(actionType)"
        type="primary"
        :title="t('action.save')"
        :loading="actionLoading"
        @click="submitForm()"
      />
      <!-- 按钮：关闭 -->
      <XButton :loading="actionLoading" :title="t('dialog.close')" @click="modelVisible = false" />
    </template>
  </XModal>
  <ContractDetail v-model:contractId="contractId" v-model:modelVisible="viewContractFlag" />
</template>
<script setup lang="ts" name="PerformReport">
import { FormExpose } from '@/components/Form'
// 业务相关的 import
import { rules, allSchemas, postPackageOption } from './performReport.data'
import * as PerformReportApi from '@/api/c/performReport'
import { listSimpleDeptApi } from '@/api/system/dept'
import type { ElTree, UploadRawFile, UploadInstance } from 'element-plus'
import { handleTree, defaultProps } from '@/utils/tree'
import Crontab from '@/components/Crontab/src/Crontab.vue'
import XButton from '@/components/XButton/src/XButton.vue'
import { PerformReportVO } from '@/api/c/performReport'
import * as ontractApi from '@/api/c/ontract'
import ContractDetail from './component/contract.vue'
import XTextButton from '@/components/XButton/src/XTextButton.vue'
const router = useRouter() // 路由
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
// 列表相关的变量
const queryParams = reactive({
  deptId: null
})
// 列表相关的变量
const [registerTable, { reload, deleteData, exportList }] = useXTable({
  allSchemas: allSchemas,
  getListApi: PerformReportApi.getPerformReportPageApi,
  deleteApi: PerformReportApi.deletePerformReportApi,
  exportListApi: PerformReportApi.exportPerformReportApi,
  params: queryParams
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
  queryParams.deptId = row.id
  await reload()
}
const { push } = useRouter()
const handleDeptEdit = () => {
  push('/system/dept')
}
watch(filterText, (val) => {
  treeRef.value!.filter(val)
})

// 弹窗相关的变量
const modelVisible = ref(false) // 是否显示弹出层
const modelTitle = ref('edit') // 弹出层标题
const modelLoading = ref(false) // 弹出层loading
const actionType = ref('') // 操作按钮的类型
const actionLoading = ref(false) // 按钮 Loading
const formRef = ref<FormExpose>() // 表单 Ref
const detailData = ref() // 详情 Ref
const contractDetailData = ref()
// 设置标题
const setDialogTile = (type: string) => {
  modelLoading.value = true
  modelTitle.value = t('action.' + type)
  actionType.value = type
  modelVisible.value = true
}

// 合同详情的弹窗
const contractId = ref() //合同id
const viewContractFlag = ref(false) //合同弹窗

// 新增操作

const handleCreate = () => {
  router.push({
    name: 'ReportBpmProcessInstanceCreate'
  })
}

// 修改操作
const handleUpdate = async (row) => {
  router.push({
    name: 'ReportBpmProcessInstanceCreate',
    query: {
      reportId: row.id,
      key: row.bpmProcessDefinitionId
    }
  })
}

// 详情操作
const handleDetail = async (rowId: number) => {
  setDialogTile('detail')
  const res = await PerformReportApi.getPerformReportApi(rowId)
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
        const data = unref(formRef)?.formModel as PerformReportApi.PerformReportVO
        if (actionType.value === 'create') {
          await PerformReportApi.createPerformReportApi(data)
          message.success(t('common.createSuccess'))
        } else {
          await PerformReportApi.updatePerformReportApi(data)
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

// 详情操作
const contractDetail = async (id: number) => {
  setDialogTile('contractDetail')
  const res = await ontractApi.getontractApi(id)
  contractDetailData.value = res
  modelLoading.value = false
}
const processInstanceDetail = (processInstanceId) => {
  router.push({
    name: 'BpmProcessInstanceDetail',
    query: {
      id: processInstanceId
    }
  })
}
// 查看合同
const viewContract = async (id: number) => {
  contractId.value = id
  viewContractFlag.value = true
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
// ========== 初始化 ==========
onMounted(async () => {
  await getTree()
})
</script>
