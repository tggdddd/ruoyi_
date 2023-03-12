import type { VxeCrudSchema } from '@/hooks/web/useVxeCrudSchemas'
import { listSimplePostsApi, SimplePostVO } from '@/api/system/post'
import { ComponentOptions } from '@/types/components'
import { getListSimpleUsersApi, SimpleUserVO } from '@/api/system/user'

const { t } = useI18n() // 国际化
// 表单校验
export const rules = reactive({
  userId: [required],
  name: [required],
  identityCard: [required],
  salary: [required],
  status: [required]
})
// 获得post字段字典
export const postPackageOption: ComponentOptions[] = []
const getPostPackageOptions = async () => {
  const res = await listSimplePostsApi()
  res.forEach((postPackage: SimplePostVO) => {
    postPackageOption.push({
      key: postPackage.id,
      label: postPackage.name,
      value: postPackage.id
    })
  })
  return postPackageOption
}
getPostPackageOptions()

// 获得userId字段字典
export const userIdOption: ComponentOptions[] = []
const getUserIdOptions = async () => {
  const res = await getListSimpleUsersApi()
  res.forEach((postPackage: SimpleUserVO) => {
    userIdOption.push({
      key: postPackage.id,
      label: postPackage.nickname,
      value: postPackage.id
    })
  })
  return userIdOption
}
getUserIdOptions()

// CrudSchema
const crudSchemas = reactive<VxeCrudSchema>({
  primaryKey: 'id', // 默认的主键ID
  primaryTitle: t('common.index'), // 默认显示的值
  // primaryType: 'seq', // 默认为seq，序号模式
  action: true,
  actionWidth: '200', // 3个按钮默认200，如有删减对应增减即可
  columns: [
    {
      title: '姓名',
      field: 'name',
      isSearch: true
    },
    {
      title: '身份证号',
      field: 'identityCard'
    },
    {
      title: '岗位',
      field: 'postId',
      isSearch: false,
      table: {
        slots: {
          default: 'postId_default'
        }
      },
      form: {
        component: 'Select',
        componentProps: {
          options: postPackageOption,
          filterable: true,
          autocomplete: true
        }
      }
    },
    {
      title: '甲方',
      field: 'firstParty'
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
      title: '附件模板',
      field: 'attach',
      form: {
        component: 'Editor',
        colProps: {
          span: 24
        },
        componentProps: {
          valueHtml: ''
        }
      },
      table: {
        slots: {
          default: 'attach_default'
        }
      }
    }
  ]
})
export const { allSchemas } = useVxeCrudSchemas(crudSchemas)
