import type { VxeCrudSchema } from '@/hooks/web/useVxeCrudSchemas'
const { t } = useI18n() // 国际化
// 表单校验
export const rules = reactive({
  contractId: [required],
  formIds: [required],
  startTime: [required]
})
// CrudSchema
const crudSchemas = reactive<VxeCrudSchema>({
  primaryKey: 'id', // 默认的主键ID
  primaryTitle: t('common.index'), // 默认显示的值
  primaryType: 'seq', // 默认为seq，序号模式
  action: true,
  actionWidth: '200', // 3个按钮默认200，如有删减对应增减即可
  columns: [
    {
      title: '关联的合同表ID',
      field: 'contractId',
      form: {
        component: 'InputNumber',
        value: 0
      }
    },
    {
      title: '业绩提交表单ids',
      field: 'formIds',
      isSearch: true
    },
    {
      title: '业绩表单提交开始开始时间',
      field: 'startTime',
      form: {
        component: 'DatePicker',
        componentProps: {
          type: 'datetime',
          valueFormat: 'x'
        }
      },
      formatter: 'formatDate',
      search: {
        show: true,
        itemRender: {
          name: 'XDataTimePicker'
        }
      }
    },
    {
      title: '业绩表单提交终止时间',
      field: 'endTime',
      form: {
        component: 'DatePicker',
        componentProps: {
          type: 'datetime',
          valueFormat: 'x'
        }
      },
      formatter: 'formatDate',
      search: {
        show: true,
        itemRender: {
          name: 'XDataTimePicker'
        }
      }
    },
    {
      title: '业绩表单提交通知时间',
      field: 'notifyTime',
      form: {
        component: 'DatePicker',
        componentProps: {
          type: 'datetime',
          valueFormat: 'x'
        }
      },
      formatter: 'formatDate',
      search: {
        show: true,
        itemRender: {
          name: 'XDataTimePicker'
        }
      }
    },
    {
      title: '业绩表单提交通知频率',
      field: 'notifyDuration',
      form: {
        component: 'DatePicker',
        componentProps: {
          type: 'datetime',
          valueFormat: 'x'
        }
      },
      formatter: 'formatDate',
      search: {
        show: true,
        itemRender: {
          name: 'XDataTimePicker'
        }
      }
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
