import type { VxeCrudSchema } from '@/hooks/web/useVxeCrudSchemas'
import type { ComponentOptions } from 'vue'
import { getSimpleSmsChannels } from '@/api/system/sms/smsChannel'
import type { simpleChannelVO } from '@/api/system/sms/smsChannel'
import { getDictObj } from '@/utils/dict'
const { t } = useI18n() // 国际化

// 表单校验
export const rules = reactive({
  type: [required],
  status: [required],
  code: [required],
  name: [required],
  content: [required],
  apiTemplateId: [required],
  channelId: [required]
})
// 获得短信渠道字典
export const simpleChannelOption: ComponentOptions[] = []
const getsimpleChannelOptions = async () => {
  const res = await getSimpleSmsChannels()
  res.forEach((simpleChannel: simpleChannelVO) => {
    simpleChannelOption.push({
      key: simpleChannel.id,
      label:
        getDictObj(DICT_TYPE.SYSTEM_SMS_CHANNEL_CODE, simpleChannel.code)?.label +
        ':' +
        simpleChannel.signature,
      value: simpleChannel.id
    })
  })
  return simpleChannelOption
}
getsimpleChannelOptions()
// CrudSchema
const crudSchemas = reactive<VxeCrudSchema>({
  primaryKey: 'id',
  primaryType: 'id',
  primaryTitle: '模板编号',
  action: true,
  actionWidth: '280',
  columns: [
    {
      field: 'channelId',
      title: '短信渠道',
      isSearch: true,
      table: {
        slots: {
          default: 'channelId_default'
        }
      },
      form: {
        component: 'Select',
        componentProps: {
          options: simpleChannelOption
        }
      }
    },
    {
      title: '模板编码',
      field: 'code',
      isSearch: true
    },
    {
      title: '模板名称',
      field: 'name',
      isSearch: true
    },
    {
      title: '模板内容',
      field: 'content'
    },
    {
      title: '短信 API 的模板编号',
      field: 'apiTemplateId',
      isSearch: true
    },
    {
      title: '短信类型',
      field: 'type',
      dictType: DICT_TYPE.SYSTEM_SMS_TEMPLATE_TYPE,
      dictClass: 'number',
      isSearch: true,
      table: {
        width: 80
      }
    },
    {
      title: t('common.status'),
      field: 'status',
      dictType: DICT_TYPE.COMMON_STATUS,
      dictClass: 'number',
      isSearch: true,
      table: {
        width: 80
      }
    },
    {
      title: t('form.remark'),
      field: 'remark',
      isTable: false
    },
    {
      title: t('common.createTime'),
      field: 'createTime',
      formatter: 'formatDate',
      isForm: false,
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
