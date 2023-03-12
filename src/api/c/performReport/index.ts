import request from '@/config/axios'

export interface PerformReportVO {
  id: number
  userId: number
  contractId: number
  postId: number
  bpmProcessInstanceExtId: number
  bpmProcessDefinitionId: number
  processInstanceId: string
}

export interface PerformReportPageReqVO extends PageParam {
  userId?: number
  contractId?: number
  postId?: number
  bpmProcessInstanceExtId?: number
  bpmProcessDefinitionId?: number
  createTime?: Date[]
  processInstanceId?: string
}

export interface PerformReportExcelReqVO {
  userId?: number
  contractId?: number
  postId?: number
  bpmProcessInstanceExtId?: number
  bpmProcessDefinitionId?: number
  createTime?: Date[]
  processInstanceId?: string
}

// 查询业绩信息列表
export const getPerformReportPageApi = async (params: PerformReportPageReqVO) => {
  return await request.get({ url: '/c/perform-report/page', params })
}

// 查询业绩信息详情
export const getPerformReportApi = async (id: number) => {
  return await request.get({ url: '/c/perform-report/get?id=' + id })
}

// 新增业绩信息
export const createPerformReportApi = async (data: PerformReportVO) => {
  return await request.post({ url: '/c/perform-report/create', data })
}

// 修改业绩信息
export const updatePerformReportApi = async (data: PerformReportVO) => {
  return await request.put({ url: '/c/perform-report/update', data })
}

// 删除业绩信息
export const deletePerformReportApi = async (id: number) => {
  return await request.delete({ url: '/c/perform-report/delete?id=' + id })
}

// 导出业绩信息 Excel
export const exportPerformReportApi = async (params: PerformReportExcelReqVO) => {
  return await request.download({ url: '/c/perform-report/export-excel', params })
}

// 代理业绩的流程创建
export const createProcessInstanceApi = async (data) => {
  return await request.post({ url: '/c/perform-report/process-instance', data: data })
}
