import request from '@/utils/request'

// 创建业绩信息
export function createPerformReport(data) {
  return request({
    url: '/c/perform-report/create',
    method: 'post',
    data: data
  })
}

// 更新业绩信息
export function updatePerformReport(data) {
  return request({
    url: '/c/perform-report/update',
    method: 'put',
    data: data
  })
}

// 删除业绩信息
export function deletePerformReport(id) {
  return request({
    url: '/c/perform-report/delete?id=' + id,
    method: 'delete'
  })
}

// 获得业绩信息
export function getPerformReport(id) {
  return request({
    url: '/c/perform-report/get?id=' + id,
    method: 'get'
  })
}

// 获得业绩信息分页
export function getPerformReportPage(query) {
  return request({
    url: '/c/perform-report/page',
    method: 'get',
    params: query
  })
}

// 导出业绩信息 Excel
export function exportPerformReportExcel(query) {
  return request({
    url: '/c/perform-report/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
