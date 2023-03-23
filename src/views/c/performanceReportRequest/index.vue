<template>
  <div>
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
            <template #processDefitionId_default="{ row }">
              <el-tag type="success">
                {{ getProcessDefinedName(row.processDefitionId) }}
              </el-tag>
            </template>
            <template #contractId_default="{ row }">
              <el-button text type="primary" @click="viewContract(row.contractId)"
                >查看详情</el-button
              >
            </template>
            <template #search_formIds="{ data }">
              <ElSelect v-model="data.formId" clearable filterable>
                <el-option
                  v-for="item in processDefinedOption"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </ElSelect>
            </template>
            <template #toolbar_buttons>
              <!-- 操作：新增 -->
              <XButton
                type="primary"
                preIcon="ep:zoom-in"
                :title="t('action.add')"
                v-hasPermi="['c:performance-report-request:create']"
                @click="handleCreate()"
              />
              <!-- 操作：导出 -->
              <XButton
                type="warning"
                preIcon="ep:download"
                :title="t('action.export')"
                v-hasPermi="['c:performance-report-request:export']"
                @click="exportList('业绩定义.xls')"
              />
            </template>
            <template #actionbtns_default="{ row }">
              <!-- 操作：修改 -->
              <XTextButton
                preIcon="ep:edit"
                :title="t('action.edit')"
                v-hasPermi="['c:performance-report-request:update']"
                @click="handleUpdate(row.id)"
              />
              <!-- 操作：详情 -->
              <!-- <XTextButton
                preIcon="ep:view"
                :title="t('action.detail')"
                v-hasPermi="['c:performance-report-request:query']"
                @click="handleDetail(row.id)"
              /> -->
              <!-- 操作：删除 -->
              <XTextButton
                preIcon="ep:delete"
                :title="t('action.del')"
                v-hasPermi="['c:performance-report-request:delete']"
                @click="deleteData(row.id)"
              />
            </template>
          </XTable>
        </el-card>
      </div>
    </ContentWrap>
    <!-- 弹窗 -->
    <XModal
      id="performanceReportRequestModel"
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
      >
        <template #startTime_form>
          <XButton>22</XButton>
          <!--        <Crontab />-->
        </template>
      </Form>
      <!-- 表单：详情 -->
      <Descriptions
        v-if="actionType === 'detail'"
        :schema="allSchemas.detailSchema"
        :data="detailData"
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
        <XButton
          :loading="actionLoading"
          :title="t('dialog.close')"
          @click="modelVisible = false"
        />
      </template>
    </XModal>

    <!-- 选择合同弹窗 -->
    <XModal
      id="contractTemplateModel"
      :loading="modelTemplateLoading"
      v-model="modelTemplateVisible"
      title="选择合同"
    >
      <contract-select @update:contractId="contractId = $event" />
      <template #footer>
        <!-- 按钮：关闭 -->
        <XButton
          :loading="actionLoading"
          :title="t('dialog.close')"
          @click="modelTemplateVisible = false"
        />
      </template>
    </XModal>
    <ContractDetail v-model:contractId="contractIdView" v-model:modelVisible="viewContractFlag" />
  </div>
</template>
<script setup lang="ts" name="PerformanceReportRequest">
import { FormExpose } from '@/components/Form'
// 业务相关的 import
import {
  rules,
  allSchemas,
  processDefinedOption,
  processDefineds
} from './performanceReportRequest.data'
import * as PerformanceReportRequestApi from '@/api/c/performanceReportRequest'
import { listSimpleDeptApi } from '@/api/system/dept'
import type { ElTree, UploadRawFile, UploadInstance } from 'element-plus'
import { handleTree, defaultProps } from '@/utils/tree'
import TemplateSelect from '@/views/c/ontract/Component/templateSelect.vue'
import Crontab from '@/components/Crontab/src/Crontab.vue'
import XButton from '@/components/XButton/src/XButton.vue'
import ContractSelect from '@/views/c/performanceReportRequest/Component/contractSelect.vue'
import { PerformReportVO } from '@/api/c/performReport'
import ContractDetail from '@/views/c/performReport/component/contract.vue'
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

// 列表相关的变量
const queryParams = reactive({
  deptId: null
})

const [registerTable, { reload, deleteData, exportList }] = useXTable({
  allSchemas: allSchemas,
  getListApi: PerformanceReportRequestApi.getPerformanceReportRequestPageApi,
  deleteApi: PerformanceReportRequestApi.deletePerformanceReportRequestApi,
  exportListApi: PerformanceReportRequestApi.exportPerformanceReportRequestApi,
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
// 模板选择弹窗
const modelTemplateVisible = ref(false) // 是否显示弹出层
const modelTemplateLoading = ref(false) // 弹出层loading
const contractId = ref() // 表单 Ref
// 设置标题
const setDialogTile = (type: string) => {
  modelLoading.value = true
  modelTitle.value = t('action.' + type)
  actionType.value = type
  modelVisible.value = true
}
// 合同详情的弹窗
const viewContractFlag = ref(false) //合同弹窗
const contractIdView = ref() //合同的id
// 新增操作
const handleCreate = () => {
  modelTemplateVisible.value = true
}
watch(contractId, (n, o) => {
  modelTemplateVisible.value = false
  setTimeout(() => {
    setDialogTile('create')
    modelLoading.value = false
    nextTick(() => {
      const date = unref(formRef)?.formModel as PerformReportVO
      date.contractId = n
      unref(formRef)?.setValues(date)
    }),
      250
  })
})

// 修改操作
const handleUpdate = async (rowId: number) => {
  setDialogTile('update')
  // 设置数据
  const res = await PerformanceReportRequestApi.getPerformanceReportRequestApi(rowId)
  unref(formRef)?.setValues(res)
  modelLoading.value = false
}

// 详情操作
const handleDetail = async (rowId: number) => {
  setDialogTile('detail')
  const res = await PerformanceReportRequestApi.getPerformanceReportRequestApi(rowId)
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
        const data = unref(formRef)
          ?.formModel as PerformanceReportRequestApi.PerformanceReportRequestVO
        if (actionType.value === 'create') {
          await PerformanceReportRequestApi.createPerformanceReportRequestApi(data)
          message.success(t('common.createSuccess'))
        } else {
          await PerformanceReportRequestApi.updatePerformanceReportRequestApi(data)
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

// 获得流程定义数据
const getProcessDefinedName = (id: number) => {
  for (let item of processDefinedOption) {
    if (item.value == id) {
      return item.label
    }
  }
  return '无'
}

// 查看合同
const viewContract = async (id: number) => {
  contractIdView.value = id
  viewContractFlag.value = true
}
// ========== 初始化 ==========
onMounted(async () => {
  await getTree()
})
</script>
