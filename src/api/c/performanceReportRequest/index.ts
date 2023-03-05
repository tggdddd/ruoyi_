import request from '@/config/axios'

export interface PerformanceReportRequestVO {
  id: number
  contractId: number
  startTime: Date
  endTime: Date
  notifyTime: Date
  processDefitionId: number
  urgeTime: Date
  userId: number
  processInstanceId: number
}

export interface PerformanceReportRequestPageReqVO extends PageParam {
  contractId?: number
  startTime?: Date[]
  endTime?: Date[]
  notifyTime?: Date[]
  createTime?: Date[]
  processDefitionId?: number
  urgeTime?: Date[]
  userId?: number
  processInstanceId?: number
}

export interface PerformanceReportRequestExcelReqVO {
  contractId?: number
  startTime?: Date[]
  endTime?: Date[]
  notifyTime?: Date[]
  createTime?: Date[]
  processDefitionId?: number
  urgeTime?: Date[]
  userId?: number
  processInstanceId?: number
}

// 查询业绩定义列表
export const getPerformanceReportRequestPageApi = async (
  params: PerformanceReportRequestPageReqVO
) => {
  return await request.get({ url: '/c/performance-report-request/page', params })
}

// 查询业绩定义详情
export const getPerformanceReportRequestApi = async (id: number) => {
  return await request.get({ url: '/c/performance-report-request/get?id=' + id })
}

// 新增业绩定义
export const createPerformanceReportRequestApi = async (data: PerformanceReportRequestVO) => {
  return await request.post({ url: '/c/performance-report-request/create', data })
}

// 修改业绩定义
export const updatePerformanceReportRequestApi = async (data: PerformanceReportRequestVO) => {
  return await request.put({ url: '/c/performance-report-request/update', data })
}

// 删除业绩定义
export const deletePerformanceReportRequestApi = async (id: number) => {
  return await request.delete({ url: '/c/performance-report-request/delete?id=' + id })
}

// 导出业绩定义 Excel
export const exportPerformanceReportRequestApi = async (
  params: PerformanceReportRequestExcelReqVO
) => {
  return await request.download({ url: '/c/performance-report-request/export-excel', params })
}
