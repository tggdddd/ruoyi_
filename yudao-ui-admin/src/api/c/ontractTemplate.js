import request from '@/utils/request'

// 创建合同表单模板
export function createontractTemplate(data) {
  return request({
    url: '/c/ontract-template/create',
    method: 'post',
    data: data
  })
}

// 更新合同表单模板
export function updateontractTemplate(data) {
  return request({
    url: '/c/ontract-template/update',
    method: 'put',
    data: data
  })
}

// 删除合同表单模板
export function deleteontractTemplate(id) {
  return request({
    url: '/c/ontract-template/delete?id=' + id,
    method: 'delete'
  })
}

// 获得合同表单模板
export function getontractTemplate(id) {
  return request({
    url: '/c/ontract-template/get?id=' + id,
    method: 'get'
  })
}

// 获得合同表单模板分页
export function getontractTemplatePage(query) {
  return request({
    url: '/c/ontract-template/page',
    method: 'get',
    params: query
  })
}

// 导出合同表单模板 Excel
export function exportontractTemplateExcel(query) {
  return request({
    url: '/c/ontract-template/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
