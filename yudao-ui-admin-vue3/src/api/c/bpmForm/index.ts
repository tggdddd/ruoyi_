import request from '@/config/axios'

export interface BpmFormVO {
  id: number
  type: number
  result: number
  processInstanceId: string
}

export interface BpmFormPageReqVO extends PageParam {
  type?: number
  result?: number
  processInstanceId?: string
  createTime?: Date[]
}

export interface BpmFormExcelReqVO {
  type?: number
  result?: number
  processInstanceId?: string
  createTime?: Date[]
}

// 查询bpm和业务表单之间的关联列表
export const getBpmFormPageApi = async (params: BpmFormPageReqVO) => {
  return await request.get({ url: '/c/bpm-form/page', params })
}

// 查询bpm和业务表单之间的关联详情
export const getBpmFormApi = async (id: number) => {
  return await request.get({ url: '/c/bpm-form/get?id=' + id })
}

// 新增bpm和业务表单之间的关联
export const createBpmFormApi = async (data: BpmFormVO) => {
  return await request.post({ url: '/c/bpm-form/create', data })
}

// 修改bpm和业务表单之间的关联
export const updateBpmFormApi = async (data: BpmFormVO) => {
  return await request.put({ url: '/c/bpm-form/update', data })
}

// 删除bpm和业务表单之间的关联
export const deleteBpmFormApi = async (id: number) => {
  return await request.delete({ url: '/c/bpm-form/delete?id=' + id })
}

// 导出bpm和业务表单之间的关联 Excel
export const exportBpmFormApi = async (params: BpmFormExcelReqVO) => {
  return await request.download({ url: '/c/bpm-form/export-excel', params })
}
