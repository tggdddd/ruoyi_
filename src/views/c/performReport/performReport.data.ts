import type { VxeCrudSchema } from '@/hooks/web/useVxeCrudSchemas'
import { listSimplePostsApi, SimplePostVO } from '@/api/system/post'
import { ComponentOptions } from '@/types/components'
const { t } = useI18n() // 国际化
// 表单校验
export const rules = reactive({
  userId: [required],
  contractId: [required]
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
      form: {
        component: 'InputNumber',
        value: 0
      },
      isSearch: true
    },
    {
      title: '部门',
      field: 'deptId',
      isSearch: false,
      isTable: false,
      isForm: false
    },
    {
      title: '合同',
      field: 'contractId',
      form: {
        component: 'InputNumber',
        value: 0
      },
      table: {
        slots: {
          default: 'contractId_default'
        }
      }
    },
    {
      title: '岗位',
      field: 'postId',
      form: {
        component: 'InputNumber',
        value: 0
      },
      table: {
        slots: {
          default: 'postId_default'
        }
      }
    },
    {
      title: '流程',
      field: 'bpmProcessDefinitionId',
      form: {
        component: 'InputNumber',
        value: 0
      },
      table: {
        slots: {
          default: 'bpmProcessDefinition'
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
    },
    {
      title: '提交的流程',
      field: 'processInstanceId',
      table: {
        slots: {
          default: 'processInstanceId'
        }
      }
    },
    {
      title: '流程实例的名称',
      field: 'processDefinedName',
      isForm: false,
      isTable: false,
      isSearch: false
    },
    {
      title: '状态',
      field: 'status',
      dictType: DICT_TYPE.REPORT_STATUS,
      dictClass: 'string',
      isSearch: true,
      isForm: false
    }
  ]
})
export const { allSchemas } = useVxeCrudSchemas(crudSchemas)
