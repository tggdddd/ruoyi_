import type { VxeCrudSchema } from '@/hooks/web/useVxeCrudSchemas'
const { t } = useI18n() // 国际化
// 表单校验
export const rules = reactive({
  type: [required],
  result: [required],
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
      title: '类型',
      field: 'type',
      form: {
        component: 'InputNumber',
        value: 0
      },
    },
    {
      title: '审核结果',
      field: 'result',
      form: {
        component: 'InputNumber',
        value: 0
      },
      isSearch: true,
    },
    {
      title: '流程实例的编号',
      field: 'processInstanceId',
      isSearch: true,
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
      },
    },
  ]
})
export const { allSchemas } = useVxeCrudSchemas(crudSchemas)