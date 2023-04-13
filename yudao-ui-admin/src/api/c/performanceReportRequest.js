import request from '@/utils/request'

// 创建业绩定义
export function createPerformanceReportRequest(data) {
  return request({
    url: '/c/performance-report-request/create',
    method: 'post',
    data: data
  })
}

// 更新业绩定义
export function updatePerformanceReportRequest(data) {
  return request({
    url: '/c/performance-report-request/update',
    method: 'put',
    data: data
  })
}

// 删除业绩定义
export function deletePerformanceReportRequest(id) {
  return request({
    url: '/c/performance-report-request/delete?id=' + id,
    method: 'delete'
  })
}

// 获得业绩定义
export function getPerformanceReportRequest(id) {
  return request({
    url: '/c/performance-report-request/get?id=' + id,
    method: 'get'
  })
}

// 获得业绩定义分页
export function getPerformanceReportRequestPage(query) {
  return request({
    url: '/c/performance-report-request/page',
    method: 'get',
    params: query
  })
}

// 导出业绩定义 Excel
export function exportPerformanceReportRequestExcel(query) {
  return request({
    url: '/c/performance-report-request/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
