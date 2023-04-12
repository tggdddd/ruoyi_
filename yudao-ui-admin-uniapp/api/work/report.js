import request from '@/utils/request'


// 查询业绩信息列表
export const getPerformReportPageApi = async (params) => {
  return await request({ url: '/c/perform-report/page', params })
}

// 查询业绩信息详情
export const getPerformReportApi = async (id) => {
  return await request({ url: '/c/perform-report/get?id=' + id })
}

// 新增业绩信息
export const createPerformReportApi = async (data) => {
  return await request({ url: '/c/perform-report/create', data ,method:"post"})
}

// 修改业绩信息
export const updatePerformReportApi = async (data) => {
  return await request({ url: '/c/perform-report/update', data,method:"put" })
}

// 删除业绩信息
export const deletePerformReportApi = async (id) => {
  return await request({ url: '/c/perform-report/delete?id=' + id,method:"delete" })
}

// 导出业绩信息 Excel
export const exportPerformReportApi = async (params) => {
  return await request.download({ url: '/c/perform-report/export-excel', params })
}

// 代理业绩的流程创建
export const createProcessInstanceApi = async (data) => {
  return await request({ url: '/c/perform-report/process-instance', data: data,method:"post" })
}
// 获得个人业绩报告统计数据
export const getReportStatisticsApi = async () => {
  return await request({ url: '/c/perform-report/getStatistics' })
}
// 获得所有业绩报告统计数据
export const getReportAllStatisticsApi = async () => {
  return await request({ url: '/c/perform-report/getAllStatistics' })
}
