import type { VxeCrudSchema } from '@/hooks/web/useVxeCrudSchemas'
const { t } = useI18n() // 国际化
// 表单校验
export const rules = reactive({
  userId: [required],
  name: [required],
  identityCard: [required],
  salary: [required],
  status: [required]
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
      title: 'system_user表用户ID',
      field: 'userId',
      form: {
        component: 'InputNumber',
        value: 0
      },
      isSearch: true
    },
    {
      title: '用户的真实姓名',
      field: 'name',
      isSearch: true
    },
    {
      title: '用户的身份证号',
      field: 'identityCard',
      isSearch: true
    },
    {
      title: '薪资',
      field: 'salary',
      isSearch: true
    },
    {
      title: '岗位',
      field: 'post',
      isSearch: true
    },
    {
      title: '附件',
      field: 'attach',
      isSearch: true
    },
    {
      title: '业绩要求',
      field: 'performanceRequirements',
      form: {
        component: 'Input',
        componentProps: {
          type: 'textarea',
          rows: 4
        },
        colProps: {
          span: 24
        }
      }
    },
    {
      title: '违约条款',
      field: 'defaultClause',
      form: {
        component: 'Input',
        componentProps: {
          type: 'textarea',
          rows: 4
        },
        colProps: {
          span: 24
        }
      }
    },
    {
      title: '合同状态 0未签订 1签订 2到期 3终止',
      field: 'status',
      dictType: DICT_TYPE.CONTRACT_STATUS,
      dictClass: 'string',
      isSearch: true
    },
    {
      title: '甲方',
      field: 'firstParty',
      isSearch: true
    },
    {
      title: '签约时间',
      field: 'signedTime',
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
      title: '合同开始时间',
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
      title: '合同结束时间',
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
