import request from '@/utils/request'


// 查询业绩定义列表
export const getPerformanceReportRequestPageApi = async (
  params
) => {
  return await request({ url: '/c/performance-report-request/page', params })
}

// 查询业绩定义详情
export const getPerformanceReportRequestApi = async (id) => {
  return await request({ url: '/c/performance-report-request/get?id=' + id })
}

// 新增业绩定义
export const createPerformanceReportRequestApi = async (data) => {
  return await request({ url: '/c/performance-report-request/create', data,method:"post" })
}

// 修改业绩定义
export const updatePerformanceReportRequestApi = async (data) => {
  return await request({ url: '/c/performance-report-request/update', data ,method:"put"})
}

// 删除业绩定义
export const deletePerformanceReportRequestApi = async (id) => {
  return await request({ url: '/c/performance-report-request/delete?id=' + id,method:"delete" })
}

// 导出业绩定义 Excel
export const exportPerformanceReportRequestApi = async (
  params
) => {
  return await request.download({ url: '/c/performance-report-request/export-excel', params })
}
