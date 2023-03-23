import type { VxeCrudSchema } from '@/hooks/web/useVxeCrudSchemas'
import { ComponentOptions } from '@/types/components'
import { BpmProcessDefinitionRespVO, getProcessDefinitionListApi } from '@/api/bpm/definition'
const { t } = useI18n() // 国际化
// 表单校验
export const rules = reactive({
  contractId: [required],
  formIds: [required],
  startTime: [required]
})

// 获得流程定义字典
export const processDefinedOption: ComponentOptions[] = []
export const processDefineds = ref()
const getProcessDefinedOptions = async () => {
  const res = await getProcessDefinitionListApi({
    suspensionState: 1, //激活的流程
    category: 3 //分类
  })
  processDefineds.value = res
  res.forEach((item: BpmProcessDefinitionRespVO) => {
    processDefinedOption.push({
      key: item.id,
      label: item.name,
      value: item.id
    })
  })
  return processDefinedOption
}
getProcessDefinedOptions()

// CrudSchema
const crudSchemas = reactive<VxeCrudSchema>({
  primaryKey: 'id', // 默认的主键ID
  primaryTitle: t('common.index'), // 默认显示的值
  primaryType: 'seq', // 默认为seq，序号模式
  action: true,
  actionWidth: '200', // 3个按钮默认200，如有删减对应增减即可
  columns: [
    {
      title: '用户',
      field: 'userId',
      isForm: false
    },
    {
      title: '流程定义',
      field: 'processDefitionId',
      form: {
        component: 'Select',
        componentProps: {
          options: processDefinedOption,
          filterable: true,
          autocomplete: true
        }
      },
      table: {
        slots: {
          default: 'processDefitionId_default'
        }
      }
    },
    {
      title: '合同',
      field: 'contractId',
      form: {
        component: 'InputNumber',
        value: 0,
        hidden: true
      },
      table: {
        slots: {
          default: 'contractId_default'
        }
      }
    },
    {
      title: '部门',
      field: 'deptId',
      isSearch: false,
      isTable: false,
      isForm: false
    },
    {
      title: '提交开始开始时间',
      field: 'startTime',
      form: {
        component: 'Crontab'
      },
      isSearch: false
    },
    {
      title: '提交终止时间',
      field: 'endTime',
      form: {
        component: 'Crontab'
      },
      isSearch: false
    },
    {
      title: '业绩提交通知时间',
      field: 'notifyTime',
      form: {
        component: 'Crontab'
      },
      isSearch: false
    },
    {
      title: '未交提醒时间',
      field: 'urgeTime',
      form: {
        component: 'Crontab'
      },
      isSearch: false
    },
    {
      title: '创建时间',
      field: 'createTime',
      isForm: false,
      formatter: 'formatDate',
      search: {
        show: true,
        itemRender: {
          name: 'XDataTimePicker'
        }
      }
    }
  ]
})
export const { allSchemas } = useVxeCrudSchemas(crudSchemas)
