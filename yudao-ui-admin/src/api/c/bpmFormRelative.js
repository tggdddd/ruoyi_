import request from '@/utils/request'

// 创建bpm和业务表单之间的关联
export function createBpmFormRelative(data) {
  return request({
    url: '/c/bpm-form-relative/create',
    method: 'post',
    data: data
  })
}

// 更新bpm和业务表单之间的关联
export function updateBpmFormRelative(data) {
  return request({
    url: '/c/bpm-form-relative/update',
    method: 'put',
    data: data
  })
}

// 删除bpm和业务表单之间的关联
export function deleteBpmFormRelative(id) {
  return request({
    url: '/c/bpm-form-relative/delete?id=' + id,
    method: 'delete'
  })
}

// 获得bpm和业务表单之间的关联
export function getBpmFormRelative(id) {
  return request({
    url: '/c/bpm-form-relative/get?id=' + id,
    method: 'get'
  })
}

// 获得bpm和业务表单之间的关联分页
export function getBpmFormRelativePage(query) {
  return request({
    url: '/c/bpm-form-relative/page',
    method: 'get',
    params: query
  })
}

// 导出bpm和业务表单之间的关联 Excel
export function exportBpmFormRelativeExcel(query) {
  return request({
    url: '/c/bpm-form-relative/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
