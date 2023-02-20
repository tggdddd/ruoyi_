import request from '@/utils/request'

// 创建合同表单
export function createontract(data) {
  return request({
    url: '/c/ontract/create',
    method: 'post',
    data: data
  })
}

// 更新合同表单
export function updateontract(data) {
  return request({
    url: '/c/ontract/update',
    method: 'put',
    data: data
  })
}

// 删除合同表单
export function deleteontract(id) {
  return request({
    url: '/c/ontract/delete?id=' + id,
    method: 'delete'
  })
}

// 获得合同表单
export function getontract(id) {
  return request({
    url: '/c/ontract/get?id=' + id,
    method: 'get'
  })
}

// 获得合同表单分页
export function getontractPage(query) {
  return request({
    url: '/c/ontract/page',
    method: 'get',
    params: query
  })
}

// 导出合同表单 Excel
export function exportontractExcel(query) {
  return request({
    url: '/c/ontract/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
